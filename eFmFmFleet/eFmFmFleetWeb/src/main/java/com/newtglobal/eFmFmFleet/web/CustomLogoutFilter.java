package com.newtglobal.eFmFmFleet.web;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.SimpleUrlLogoutSuccessHandler;


public class CustomLogoutFilter extends SimpleUrlLogoutSuccessHandler {

	private static Log log = LogFactory.getLog(CustomLogoutFilter.class);
	@Override
	public void onLogoutSuccess(HttpServletRequest request,
			HttpServletResponse response, Authentication authentication)
			throws IOException, ServletException {
		log.info("CustomLogoutFilter called");
		if (authentication != null) {
			request.getSession().invalidate();
		}
		 request.getSession().invalidate();
		 response.setHeader("Cache-Control", "no-store, no-cache, must-revalidate, max-age=0, post-check=0, pre-check=0");
		 response.setHeader("Pragma", "no-cache");
		 log.info("index");
		 setDefaultTargetUrl("/index");
		super.onLogoutSuccess(request, response, authentication);
	}

}
