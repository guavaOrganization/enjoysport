package com.example.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractController;

// 当从AbstractController继承时，只需要实现handleRequestInternal抽象方法
public class SampleController extends AbstractController {

	@Override
	protected ModelAndView handleRequestInternal(
			HttpServletRequest paramHttpServletRequest,
			HttpServletResponse paramHttpServletResponse) throws Exception {
		return null;
	}

}
