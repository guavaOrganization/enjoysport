package com.example.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;

public class SampleDelegate {
	public ModelAndView retrieveIndex(HttpServletRequest request, HttpServletResponse response) {
		return new ModelAndView("retrieveIndex");
	}
}
