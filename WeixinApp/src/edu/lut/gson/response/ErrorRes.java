package edu.lut.gson.response;

public class ErrorRes {
	
	private int errcode;
	private String errmsg;
	
	public ErrorRes(Class<?> err){
		try {
			errcode = err.getField("ERRCODE").getInt(null);
			errmsg = (String) err.getField("ERRMSG").get(null);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public ErrorRes(Class<?> err, String errMsg) {
		try {
			errcode = err.getField("ERRCODE").getInt(null);
			errmsg = (String) err.getField("ERRMSG").get(null)+", "+errMsg;
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public ErrorRes(int errCode, String errMsg) {
		this.errcode = errCode;
		this.errmsg = errMsg;
	}

	
	public int getErrcode() {
		return errcode;
	}

	public void setErrcode(int errcode) {
		this.errcode = errcode;
	}

	public String getErrmsg() {
		return errmsg;
	}

	public void setErrmsg(String errmsg) {
		this.errmsg = errmsg;
	}
	

	// ***** 模板 ****
	
	public static final class UnknowException {
		public static final int ERRCODE = 1000;
		public static final String ERRMSG = "网络错误";
	}
	
	public static final class IllegalRequestException {
		public static final int ERRCODE = 1001;
		public static final String ERRMSG = "非法请求";
	}

	public static final class BadRequestException {
		public static final int ERRCODE = 1002;
		public static final String ERRMSG = "请求参数异常";
	}
	
	public static final class WeiXinReturnException {
		public static final int ERRCODE = 1003;
		public static final String ERRMSG = "微信返回了异常";
	}
}
