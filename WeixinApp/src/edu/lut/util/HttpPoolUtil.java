package edu.lut.util;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.net.ssl.SSLContext;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.HttpRequest;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.routing.HttpRoute;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicHeader;
import org.apache.http.util.EntityUtils;

public class HttpPoolUtil {
	//���ӳع������
    private static PoolingHttpClientConnectionManager cm = null;
    
    //���ӳ����ò���
    private static final int MAX_TOTAL = 200;//Ĭ�����������
    private static final int MAX_PER_ROUTE = 20;//ÿ��·�����������
    
    static{
		try {
			//��Ӷ�https��֧�֣���sslContextû�м��ؿͻ���֤��
	        //�����Ҫ���ؿͻ���֤�飬��ʹ������sslContext,����KEYSTORE_FILE��KEYSTORE_PASSWORD�ֱ������֤��·����֤������
			//KeyStore keyStore  =  KeyStore.getInstance(KeyStore.getDefaultType()
			//FileInputStream instream =   new FileInputStream(new File(KEYSTORE_FILE));
	        //keyStore.load(instream, KEYSTORE_PASSWORD.toCharArray());
	        //SSLContext sslContext = SSLContexts.custom().loadKeyMaterial(keyStore,KEYSTORE_PASSWORD.toCharArray())
	        //		.loadTrustMaterial(null, new TrustSelfSignedStrategy())
	        //		.build();
			
			SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(SSLContext.getDefault());
			
			Registry<ConnectionSocketFactory> socketFactoryRegistry = RegistryBuilder.<ConnectionSocketFactory>create()
					.register("https", sslsf).register("http", new PlainConnectionSocketFactory()).build();
			cm = new PoolingHttpClientConnectionManager(socketFactoryRegistry);
			cm.setMaxTotal(MAX_TOTAL);
			cm.setDefaultMaxPerRoute(MAX_PER_ROUTE);
		} catch (Exception e) {
			e.printStackTrace();
		}
    }
    
    private HttpPoolUtil(){
    }
        
    public static void setMaxForRoute(String hostName,int port,int max){
    	cm.setMaxPerRoute(new HttpRoute(new HttpHost(hostName,port)), max);
    }
    
    public static CloseableHttpClient getHttpClient() {       
        CloseableHttpClient httpClient = HttpClients.custom()
                .setConnectionManager(cm)
                .build();
        return httpClient;
    }
    
    /**
	 * get����
	 * 
	 * @param url
	 * @param headers
	 * @return HttpEntity
	 * @throws Exception
	 */
	public static HttpEntity httpGet(String url, Map<String, Object> headers) throws Exception {
		CloseableHttpClient httpClient = getHttpClient();
		HttpRequest httpGet = new HttpGet(url);
		if (headers != null && !headers.isEmpty()) {
			httpGet = setHeaders(headers, httpGet);
		}
		CloseableHttpResponse response = httpClient.execute((HttpGet) httpGet);
		HttpEntity entity = response.getEntity();
		return entity;

	}

    /**
     * post����,ʹ��json��ʽ����
     * @param url
     * @param headers
     * @param data
     * @return HttpEntity
     * @throws Exception
     */
    public static HttpEntity httpPostJson(String url,Map<String,Object> headers,String json) throws Exception{
		CloseableHttpClient httpClient = getHttpClient();
		HttpRequest request = new HttpPost(url);
		if (headers != null && !headers.isEmpty()) {
			request = setHeaders(headers, request);
		}

		HttpPost httpPost = (HttpPost) request;
		httpPost.setEntity(new StringEntity(json, ContentType.create("application/json", "UTF-8")));
		CloseableHttpResponse response = httpClient.execute(httpPost);
		HttpEntity entity = response.getEntity();
		return entity;
    }
    

    /**
     * ʹ�ñ���ֵ�Դ���
     * @param url
     * @param headers
     * @param data
     * @return HttpEntity
     * @throws Exception
     */
    public static HttpEntity PostForm(String url,Map<String,Object> headers,List<NameValuePair> data) throws Exception{
		CloseableHttpClient httpClient = getHttpClient();
		HttpRequest request = new HttpPost(url);
		if (headers != null && !headers.isEmpty()) {
			request = setHeaders(headers, request);
		}
		HttpPost httpPost = (HttpPost) request;
		UrlEncodedFormEntity uefEntity = new UrlEncodedFormEntity(data, "UTF-8");
		httpPost.setEntity(uefEntity);
		CloseableHttpResponse response = httpClient.execute(httpPost);
		HttpEntity entity = response.getEntity();
		return entity;
    }
    
    /**
     * Get����  �����ַ���
     * @param url
     * @param headers
     * @return
     * @throws Exception
     */
    public static String httpGetReturnStr(String url,Map<String, Object> headers) throws Exception{
    	HttpEntity httpGet = HttpPoolUtil.httpGet(url, headers);
    	return EntityUtils.toString(httpGet);
    }
    
    /**
     * ��������ͷ��Ϣ
     * @param headers
     * @param request
     * @return
     */
    private static HttpRequest setHeaders(Map<String,Object> headers, HttpRequest request) {
        for (Map.Entry entry : headers.entrySet()) {
            if (!entry.getKey().equals("Cookie")) {
                request.addHeader((String) entry.getKey(), (String) entry.getValue());
            } else {
                Map<String, Object> Cookies = (Map<String, Object>) entry.getValue();
                for (Map.Entry entry1 : Cookies.entrySet()) {
                    request.addHeader(new BasicHeader("Cookie", (String) entry1.getValue()));
                }
            }
        }
        return request;
    }

    public static Map<String,String> getCookie(String url){
        CloseableHttpClient httpClient = getHttpClient();
        HttpRequest httpGet = new HttpGet(url);
        CloseableHttpResponse response = null;
        try{
            response =httpClient.execute((HttpGet)httpGet);
            Header[] headers = response.getAllHeaders();
            Map<String,String> cookies=new HashMap<String, String>();
            for(Header header:headers){
                cookies.put(header.getName(),header.getValue());
            }
            return cookies;
        }catch (Exception e){
            e.printStackTrace();

        }
        return null;
    }

}
