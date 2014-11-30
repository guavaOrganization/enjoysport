package com.example.designpattern.abstractfactory;

public class InternetCompany implements Company {
	@Override
	public Product createProduct() {
		return new InternetProduct();
	}
}
