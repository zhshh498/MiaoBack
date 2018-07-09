package edu.lut.exception;

public class ExceuteApiCanReturnException extends Exception{

	private static final long serialVersionUID = 1L;
	private int errcode;
	private String errmsg;
	
	public ExceuteApiCanReturnException(int errCode,String errMsg) {
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

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	
}
