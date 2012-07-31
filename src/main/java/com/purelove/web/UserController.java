package com.purelove.web;

import java.io.IOException;
import java.net.URLDecoder;
import java.sql.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("user")
public class UserController  extends BaseController{
	
	private Log log =  LogFactory.getLog(UserController.class);
	/**
	 * 主页面跳转
	 * @param request
	 * @return
	 */
	@RequestMapping("index")
	public String userpage(HttpServletRequest request){
		return "user/user";
	}
}
