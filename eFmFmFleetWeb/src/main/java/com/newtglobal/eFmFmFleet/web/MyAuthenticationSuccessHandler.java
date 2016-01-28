package com.newtglobal.eFmFmFleet.web;
import java.io.IOException;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import com.newtglobal.eFmFmFleet.business.bo.IUserMasterBO;
import com.newtglobal.eFmFmFleet.model.PersistentLoginPO;


@Component
public class MyAuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

	private static Log log = LogFactory.getLog(MyAuthenticationSuccessHandler.class);
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
        Authentication authentication) throws IOException, ServletException {
    	log.info(request.getParameter("j_username")+" Authenticated Successfully !!");
    	IUserMasterBO userMasterBO = (IUserMasterBO) ContextLoader.getContext().getBean("IUserMasterBO");
    	if(userMasterBO.isAleradyLoggedin(request.getParameter("j_username"))==1)
    			{
    		setDefaultTargetUrl("/home");   		
    			}
    		else
    			{
    			log.info(request.getParameter("j_username")+" tried again to login");
    			HttpSession session =request.getSession();
    			session.setAttribute("access", "User Already Logged In");
    			List<PersistentLoginPO> persistentLoginPO = userMasterBO.getUserLoggedInDetail(request.getParameter("j_username"));
    			String targetUrl = null ;
    			for(int i=1;i<persistentLoginPO.size();i++)
    			{
    				PersistentLoginPO persistentLogin =persistentLoginPO.get(i);
    				long tetst  =new Date().getTime();
    				long test2  = persistentLogin.getLastUsed().getTime();
    				
    				if((tetst-test2)/1000>=Integer.parseInt(ContextLoader.getContext().getMessage("session.time_in_second", null, "300", null)))
    				{
    					userMasterBO.delteRecord(persistentLogin.getIpAddress());
    					targetUrl="/home";
    				}
    				else
    				{
    					if(persistentLogin.getIpAddress().equals(request.getRemoteAddr()))
    					{
    						
    						targetUrl="/home";
    					}
    					else
    					{
    					targetUrl="/alreadylogin";
    					}
    				}
    			}
    			targetUrl="/home";
    			setDefaultTargetUrl(targetUrl);	
    			}
    	
        super.onAuthenticationSuccess(request, response, authentication);
    }
}
