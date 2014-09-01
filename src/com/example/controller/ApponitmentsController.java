package com.example.controller;

import java.sql.Date;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.example.javabeans.AppointmentForm;
import com.example.javabeans.Apponitment;
import com.example.javabeans.ApponitmentBook;

@Controller
@RequestMapping("/appointments")
public class ApponitmentsController {
	private final ApponitmentBook apponitmentBook;

	@Autowired
	public ApponitmentsController(ApponitmentBook apponitmentBook) {
		this.apponitmentBook = apponitmentBook;
	}

	@RequestMapping(method = RequestMethod.GET)
	public Map<String, Apponitment> get() { // 此方法处理“/appointments”空间下的GET请求
		return apponitmentBook.getAppointmentsForToday();
	}

	// 可以使用@ParhVariable来绑定方法参数（day）和URI中的模版变量
	@RequestMapping(value = "/{day}", method = RequestMethod.GET)
	public Map<String, Apponitment> getForDay(
			@PathVariable @DateTimeFormat(iso = ISO.DATE) Date day, Model model) {
		return apponitmentBook.getAppointmentsForToday(day);
	}

	@RequestMapping(value = "/new", method = RequestMethod.GET)
	public AppointmentForm getNewForm() {// 只有“appointments/new”下的GET请求会被此方法处理
		return new AppointmentForm();
	}

	// 映射Content_type为application/json的请求
	@RequestMapping(value = "/pets", method = RequestMethod.POST, consumes = "application/json")
	public void addPet(@RequestBody Object obj, Model model) {

	}
}
