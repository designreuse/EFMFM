package com.newtglobal.eFmFmFleet.controllers;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.security.Principal;
import java.util.Date;
import java.util.Hashtable;
import java.util.List;

import javax.naming.Context;
import javax.naming.NamingException;
import javax.naming.ldap.InitialLdapContext;
import javax.naming.ldap.LdapContext;
import javax.servlet.ServletContext;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.io.IOUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.security.crypto.codec.Base64;
import org.springframework.security.web.authentication.rememberme.InvalidCookieException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.newtglobal.eFmFmFleet.business.bo.IUserMasterBO;
import com.newtglobal.eFmFmFleet.model.EFmFmClientUserRolePO;
import com.newtglobal.eFmFmFleet.model.EFmFmUserMasterPO;
import com.newtglobal.eFmFmFleet.model.PersistentLoginPO;
import com.newtglobal.eFmFmFleet.web.ContextLoader;
import com.newtglobal.eFmFmFleet.web.CustomAutoLogin;

@Controller
public class LoginController {

	private static Log log = LogFactory.getLog(LoginController.class);
	public static final String cookieName = "SPRING_SECURITY_REMEMBER_ME_COOKIE";
	private static final String SERVER_UPLOAD_WINDOWS_LOCATION_FOLDER = "C:/ExcelExport/";
	private static final String SERVER_UPLOAD_LINUX_LOCATION_FOLDER = "/home/tripDownloadDocs/";
	
	IUserMasterBO iUserMasterBO = (IUserMasterBO) ContextLoader.getContext().getBean("IUserMasterBO");
	@RequestMapping(value = "/home", method = RequestMethod.GET)	
	public String manageProfile(ModelMap model, Principal principle,
			HttpSession session, HttpServletRequest request) throws SocketException, NamingException {
		try {	
			session.setAttribute("failedFromLdap", "success");  
			//Servion LDAP login code end
			
			log.info("User Logged In"+principle.getName());
			if (principle.getName() == null || principle.getName().equals(null)) {
				return "index";
			} else {				
				IUserMasterBO iUserMasterBO = (IUserMasterBO) ContextLoader.getContext().getBean("IUserMasterBO");
				EFmFmUserMasterPO userMasterPO = iUserMasterBO.getUserDetailByUserName(principle.getName());
				List<EFmFmClientUserRolePO> roleMasterPO = iUserMasterBO.getUserRolesFromUserIdAndBranchId(userMasterPO.getUserId(),userMasterPO.geteFmFmClientBranchPO().getBranchId());
				log.info("goint to set All the information of " +principle.getName() +"to session");
				Cookie[] cookies = (Cookie[]) request.getCookies();
		    	String[] cookieTokens=null;
				for (int i = 0; i < cookies.length; i++) {
			           if (cookieName.equals(cookies[i].getName())) {
			               cookieTokens = decodeCookie(cookies[i].getValue());
			   }
			}
				
				if(cookieTokens!=null)
				{
					PersistentLoginPO persistentLoginPO =  iUserMasterBO.PersistentLoginPODettail(cookieTokens[0]);		
					if(persistentLoginPO!=null){
					persistentLoginPO.setIpAddress(request.getRemoteAddr());
					iUserMasterBO.updatePersistentPO(persistentLoginPO);
					}
					else
					{
						return "index";
					}
				
				}
				setValuetoSession(session, roleMasterPO.get(0).getEfmFmRoleMaster().getRole(),
						userMasterPO.getFirstName(),
						userMasterPO.getLastName(),userMasterPO.geteFmFmClientBranchPO().getBranchId(),
						userMasterPO.getUserId(),principle.getName());
			}
		} catch (Exception e) {
			session.setAttribute("failedFromLdap", "failed");
			return "index";
		}
		return "home";
	}

	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String login(ModelMap modelMap,HttpSession session , HttpServletRequest request ) throws SocketException {
		Cookie[] cookies = (Cookie[]) request.getCookies();
		if (cookies != null) {
			CustomAutoLogin customAutoLogin = new CustomAutoLogin();
			return customAutoLogin.autoLogin(cookies,session,request);
		}
		else
		{
			return "login";
		}
		
		
	}
	@RequestMapping(value = "/error500", method = RequestMethod.GET)
	public String errorPage500(ModelMap modelMap) {
		return "error_500";
	}
	@RequestMapping(value = "/comingsoon", method = RequestMethod.GET)
	public String comingSoon(ModelMap modelMap) {

		return "coming_soon";
	}
	
	@RequestMapping(value = "/error404", method = RequestMethod.GET)
	public String errorPage404(ModelMap modelMap) {
		return "error_404";
	}

	@RequestMapping(value = "/loginFailed", method = RequestMethod.GET)
	public String loginError(ModelMap modelMap, HttpSession session) {
		session.setAttribute("access", "");
		return "index";
	}
	
	@RequestMapping(value = "/alreadylogin", method = RequestMethod.GET)
	public String alreadyloggedin(ModelMap modelMap, HttpSession session ,HttpServletRequest request ,Principal principle) {
		try {
			if(session.getAttribute("access").equals("User Already Logged In")){
				IUserMasterBO iUserMasterBO = (IUserMasterBO) ContextLoader.getContext().getBean("IUserMasterBO");
					Cookie[] cookies = (Cookie[]) request.getCookies();
			    	String[] cookieTokens=null;
					for (int i = 0; i < cookies.length; i++) {
				           if (cookieName.equals(cookies[i].getName())) {
				               cookieTokens = decodeCookie(cookies[i].getValue());
				   }
				}
					if(cookieTokens!=null)
					{
						PersistentLoginPO persistentLoginPO =  iUserMasterBO.PersistentLoginPODettail(cookieTokens[0]);
						if(persistentLoginPO!=null)
						{	
							
							
							persistentLoginPO.setIpAddress(request.getRemoteAddr());
							iUserMasterBO.updatePersistentPO(persistentLoginPO);
						}	
					}
					iUserMasterBO.delteRecord(request.getRemoteAddr());
					return "login";
			}
			else
			{
			session.setAttribute("access", "");
			return "index";
			}
		}
		catch(Exception e){
			session.setAttribute("access", "");
			return "index";
		}
	}

//	 All Module 

	
	@RequestMapping(value = "/accessdenied", method = RequestMethod.GET)
	public String accessdenied(ModelMap modelMap, HttpSession session) {
		return "accessdenied";
	}

	@RequestMapping(value = "/fleetmanagement", method = RequestMethod.GET)
	public String fleetmanagement(ModelMap modelMap,HttpServletRequest request,HttpSession session) throws UnknownHostException, SocketException {
		session.setAttribute("access", "");
		Cookie[] cookies = (Cookie[]) request.getCookies();
		if (cookies != null) {
			CustomAutoLogin customAutoLogin = new CustomAutoLogin();
			return customAutoLogin.autoLogin(cookies,session,request);
		}
		else
		{

			return "login";
		}
		
			
	}

	
	
	
	@RequestMapping(value = "/pagenotfound", method = RequestMethod.GET)
	public String pagenotfound(ModelMap modelMap) {
		modelMap.addAttribute("_productName", "efmfm COURIER");
		return "pagenotfound";
	}
	
	
	@RequestMapping(value = "/sessionExpier", method = RequestMethod.GET)
	public String sessionExpier(ModelMap modelMap, HttpSession session) {
		session.invalidate();
		
		return "sessionExpier";
	}
	
	

	@RequestMapping(value = "/roadsideassistance", method = RequestMethod.GET)
	public String roadsideassistance(ModelMap modelMap,HttpServletRequest request,HttpSession session) {
		session.setAttribute("access", "");
			return "index";
				
	}
	
	@RequestMapping(value = "/index", method = RequestMethod.GET)
	public String index(ModelMap modelMap,HttpSession session) {
		return "index";
	}
	
	@RequestMapping(value = "/routehstory", method = RequestMethod.GET)
	public String routehstory(ModelMap modelMap) {
		return "routehstory";
	}
	
	@RequestMapping(value = "/userProfile", method = RequestMethod.GET)
	 public String userProfileInfo(ModelMap modelMap,HttpSession session) {
	  return "userProfile";
	 }


	public void setValuetoSession(HttpSession session, String usrRole,
			String firstName, String lastName,int branchId,
			int profileId,String userName) {

		session.setAttribute("role", usrRole);
		session.setAttribute("firstName",firstName);
		session.setAttribute("lastName", lastName);
		session.setAttribute("branchId", branchId);
		session.setAttribute("profileId",profileId);
		session.setAttribute("userName", userName);
		session.setAttribute("lastRequest", new Date());

	}
	
	@RequestMapping(value = "/downloadRoutes.do", method = RequestMethod.GET)
	public @ResponseBody void downloadFiles(@RequestParam("fileName") String downloadFileName ,HttpServletRequest request,
			HttpServletResponse response) {		
		ServletContext context = request.getServletContext();	
		
		String name="os.name",filePath="";		 	
		boolean  OsName =System.getProperty(name).startsWith("Windows");
		
		if(OsName){			
			filePath =SERVER_UPLOAD_WINDOWS_LOCATION_FOLDER+ downloadFileName;			
		}else{
			filePath =SERVER_UPLOAD_LINUX_LOCATION_FOLDER+ downloadFileName;			
		}		
		File downloadFile = new File(filePath);
		FileInputStream inputStream = null;
		OutputStream outStream = null;
		
		try {
			inputStream = new FileInputStream(downloadFile);

			response.setContentType(context.getMimeType(filePath));
			response.setContentLength((int) downloadFile.length());

			// response header
			String headerKey = "Content-Disposition";
			String headerValue = String.format("attachment; filename=\"%s\"",downloadFile.getName());
			response.setHeader(headerKey, headerValue);

			// Write response
			outStream = response.getOutputStream();
			IOUtils.copy(inputStream, outStream);

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (null != inputStream)
					inputStream.close();
				if (null != inputStream)
					outStream.close();
			} catch (IOException e) {
				e.printStackTrace();
			}

		}

	}
	
	
	 
	 
	 protected String[] decodeCookie(String cookieValue) throws InvalidCookieException {
		         for (int j = 0; j < cookieValue.length() % 4; j++) {
		             cookieValue = cookieValue + "=";
		        }
		 
		        if (!Base64.isBase64(cookieValue.getBytes())) {
		             throw new InvalidCookieException( "Cookie token was not Base64 encoded; value was '" + cookieValue + "'");
		        }
		 
		         String cookieAsPlainText = new String(Base64.decode(cookieValue.getBytes()));
		 
		         String[] tokens = StringUtils.delimitedListToStringArray(cookieAsPlainText, ":");
		 
		         if ((tokens[0].equalsIgnoreCase("http") || tokens[0].equalsIgnoreCase("https")) && tokens[1].startsWith("//")) {
		             // Assume we've accidentally split a URL (OpenID identifier)
		             String[] newTokens = new String[tokens.length - 1];
		             newTokens[0] = tokens[0] + ":" + tokens[1];
		             System.arraycopy(tokens, 2, newTokens, 1, newTokens.length - 1);
		             tokens = newTokens;
		        }
		 
		        return tokens;
		    }
	 
	 public LdapContext getLdapContext(){
	        LdapContext ctx = null;
	        try{
	            Hashtable<String, String> env = new Hashtable<String, String>();
	            env.put(Context.INITIAL_CONTEXT_FACTORY,
	                    "com.sun.jndi.ldap.LdapCtxFactory");
	            env.put(Context.SECURITY_AUTHENTICATION, "Simple");
	            env.put(Context.SECURITY_PRINCIPAL, "traveluser");
	            env.put(Context.SECURITY_CREDENTIALS, "$ervion!23");
	            env.put(Context.PROVIDER_URL, "ldap://172.16.2.53:389");
	            ctx = new InitialLdapContext(env, null);
	        }catch(NamingException nex){
	            nex.printStackTrace();
	        }
	        return ctx;
	    }

}
