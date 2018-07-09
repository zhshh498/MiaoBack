package edu.lut.gson.response;

public class LoginWithCodeRes {
	private String openid;

	public LoginWithCodeRes(String openid) {
		super();
		this.openid = openid;
	}

	public String getOpenid() {
		return openid;
	}

	public void setOpenid(String openid) {
		this.openid = openid;
	}

}
