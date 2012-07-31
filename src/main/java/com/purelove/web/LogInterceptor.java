package com.purelove.web;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.purelove.util.StringUtils;


public class LogInterceptor extends HandlerInterceptorAdapter {
	private final Logger log = LoggerFactory.getLogger(LogInterceptor.class);
	
	private long start = 0L;
	
	private long warnTime;
	
	private String excludePaths;
	
	private String testid;

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

		boolean handlerOk = super.preHandle(request, response, handler);
		if (handlerOk) {
			
			String url = request.getRequestURI().toString();
			
			if (url.indexOf(".") > -1) {
				//log.debug("静态:" + url + ", 不拦截")
				return true;
			}
			System.out.println( excludePaths);
			System.out.println(("," + excludePaths + ",").indexOf("," + url + ",") > -1);
			if (("," + excludePaths + ",").indexOf("," + url + ",") > -1) {
				log.trace("" + url + ", excludePaths配置的路径不拦截");
				return true;
			}
			start = System.currentTimeMillis();
			
			HttpSession session = request.getSession();
			Integer userid = (Integer) session.getAttribute("userid");
			
			if (userid == null || userid == 0) { //session中无用户信息，则注入测试id
				userid = StringUtils.parseInt(testid);
				session.setAttribute("userid", userid);
				log.info("injection userid for session:" + userid);
			}
			
			
			log.debug("userid:"+userid);

			return true;
		}
		return false;
	}
	
	@Override
	public void afterCompletion(HttpServletRequest request,
			HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		String url = request.getRequestURI().toString();
		
		if(url.indexOf(".") == -1){
			/*
			HttpSession session = request.getSession();
			int userid =(Integer) session.getAttribute("userid");
			*/
			long time = System.currentTimeMillis() - start;
			if(time > warnTime){
				log.warn("[url]:"+url+"; [time:]"+time+"ms; ");
			}else{
				log.info("[url]:"+url+"; [time:]"+time+"ms; ");
			}
			
		}
		
		
	}
	
	public void setWarnTime(long warnTime) {
		this.warnTime = warnTime;
	}
	
	public void setExcludePaths(String excludePaths) {
		this.excludePaths = excludePaths;
	}

	public void setTestid(String testid) {
		this.testid = testid;
	}
}

