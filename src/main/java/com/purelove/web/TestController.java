package com.purelove.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.purelove.service.TestService;

@Controller
public class TestController extends BaseController{
	
	private Logger log = LoggerFactory.getLogger(TestController.class);
	
	@Autowired
	private TestService service;
	
	@RequestMapping("aa")
	public String test(HttpServletRequest request,HttpServletResponse response){
		System.out.println("test service:"+ service.check());
		return "index";
	}
	
	@RequestMapping("testsave")
	public String save(Model model){
		model.addAttribute("hello", "test Save~");
		log.debug("save "+service.save());
		return "index";
	}
	/*
	@RequestMapping("testupdate")
	public ModelAndView update(Model model){
		ModelAndView mv = new ModelAndView("index");
		mv.addObject("hello",service.update());
		return mv;
	}
	
	@RequestMapping("testdel")
	public ModelAndView delete(Model model){
		ModelAndView mv = new ModelAndView("index");
		mv.addObject("hello",service.delete());
		return mv;
	}
	*/
}
