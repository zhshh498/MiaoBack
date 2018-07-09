package edu.lut.biz.user;

import edu.lut.exception.ExceuteApiCanReturnException;
import edu.lut.exception.InputNullException;

public interface IUserBiz {
	public String loginWithCode(String code) throws ExceuteApiCanReturnException,InputNullException,Exception;
}
