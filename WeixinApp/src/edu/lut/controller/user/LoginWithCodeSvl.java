package edu.lut.controller.user;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import edu.lut.biz.user.IUserBiz;
import edu.lut.biz.user.UserBiz;
import edu.lut.exception.ExceuteApiCanReturnException;
import edu.lut.exception.InputNullException;
import edu.lut.gson.response.ErrorRes;
import edu.lut.gson.response.LoginWithCodeRes;

/**
 * Servlet implementation class LoginWithCode
 */
public class LoginWithCodeSvl extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public LoginWithCodeSvl() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//		String json = new Gson().toJson(new ErrorRes(ErrorRes.IllegalRequestException.class));
//		response.getWriter().print(json);
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String resultJson = null;
		
		String code = request.getParameter("code");
		IUserBiz userBiz = new UserBiz();
		try {
			String openid = userBiz.loginWithCode(code);
			resultJson = new Gson().toJson(new LoginWithCodeRes(openid));
		} catch (ExceuteApiCanReturnException e) {
			resultJson = new Gson().toJson(new ErrorRes(ErrorRes.WeiXinReturnException.class,e.getErrcode()+" "+e.getErrmsg()));
		}catch (InputNullException e) {
			resultJson = new Gson().toJson(new ErrorRes(ErrorRes.BadRequestException.class));
		} catch (Exception e) {
			resultJson = new Gson().toJson(new ErrorRes(ErrorRes.UnknowException.class));
		}
		response.getWriter().print(resultJson);
	}
	
}
