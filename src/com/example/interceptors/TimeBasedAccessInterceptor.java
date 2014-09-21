package com.example.interceptors;

import java.util.Calendar;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

public class TimeBasedAccessInterceptor extends HandlerInterceptorAdapter {
	private int openTime;
	private int closeTime;
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		Calendar cal = Calendar.getInstance();
		int hour = cal.get(Calendar.HOUR_OF_DAY);
		if (openTime <= hour && hour < closeTime) {
			return true;
		} else {
			response.sendRedirect("http://www.baidu.com/");
			return false;
		}
	}

	public void setOpenTime(int openTime) {
		this.openTime = openTime;
	}

	public void setCloseTime(int closeTime) {
		this.closeTime = closeTime;
	}
}
