package edu.lut.biz.user;

import com.google.gson.Gson;

import edu.lut.constant.OpenConstant;
import edu.lut.exception.ExceuteApiCanReturnException;
import edu.lut.exception.ExceuteApiException;
import edu.lut.exception.InputNullException;
import edu.lut.gson.api.Jscode2SessionApi;
import edu.lut.util.HttpPoolUtil;
import edu.lut.util.TextUtil;

public class UserBiz implements IUserBiz{

	@Override
	public String loginWithCode(String code) throws ExceuteApiCanReturnException,InputNullException,Exception{
		if(TextUtil.notNull(code)){
			//΢�Ż�ȡ�û�openid��session_key��unionid�Ľӿ�
			String apiUrl = "https://api.weixin.qq.com/sns/jscode2session?appid=" + OpenConstant.APPID 
					+ "&secret=" + OpenConstant.SECRET + "&js_code=" + code + "&grant_type=authorization_code";
			 
			try {
				String res = HttpPoolUtil.httpGetReturnStr(apiUrl, null);
				Jscode2SessionApi api = new Gson().fromJson(res, Jscode2SessionApi.class);
				if(api.getErrcode() == -1){
					String openid = api.getOpenid();
					String sessionKey = api.getSession_key();
					String unionid = api.getUnionid();
					
					// dao��ȡ�û�
					
					// �û������ڣ�΢�Ų�ѯ�û���Ϣ
					
					// �û����ڣ�ԭ������unionid����ԣ��������unionid
					
					// �����û�
					
					
					return openid;
				}else if(api.getErrcode() == 40029){
					throw new ExceuteApiCanReturnException(api.getErrcode(), api.getErrmsg());
				}else{
					throw new ExceuteApiException("jscode2session",apiUrl,null,api.getErrcode(),api.getErrmsg());
				}
			} catch (Exception e) {
				e.printStackTrace();
				throw e;
			}
		}else{
			throw new InputNullException("��β���Ϊ��");
		}
	}

}
