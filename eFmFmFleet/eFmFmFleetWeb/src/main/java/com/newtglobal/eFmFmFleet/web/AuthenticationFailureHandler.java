package com.newtglobal.eFmFmFleet.web;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;

public class AuthenticationFailureHandler  extends SimpleUrlAuthenticationFailureHandler {
	
	private static Log log = LogFactory.getLog(AuthenticationFailureHandler.class);
	public void	onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,AuthenticationException exception) throws IOException, ServletException
	{
		log.info("Authentication fail of "+request.getParameter("j_username")+" due to wrong password  !!");
		setDefaultFailureUrl("/loginFailed");
		super.onAuthenticationFailure(request, response, exception);
	}
}
