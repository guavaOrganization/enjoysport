package com.example.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.javabeans.Clinic;

@Controller
public class ClinicController {
	private final Clinic clinic;

	public ClinicController(Clinic clinic) {
		this.clinic = clinic;
	}

	@RequestMapping("/")
	public void welcomeHandler() {
	}

	@RequestMapping("/vets")
	public ModelMap vetsHandler() {
		return new ModelMap(this.clinic);
	}
}
