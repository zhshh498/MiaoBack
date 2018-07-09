package edu.lut.exception;

public class ExceuteApiException extends Exception{

	private static final long serialVersionUID = 1L;

	public ExceuteApiException(String apiName,String url,String data,int errCode,String errMsg) {
		super("ִ��api��" + apiName + "�������쳣:   URL = " + url + "\tData = " + data + "\terrCode = " + errCode + "\terrMsg = " + errMsg);
	}
}
