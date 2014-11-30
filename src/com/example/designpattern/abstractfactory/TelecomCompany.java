package com.example.designpattern.abstractfactory;

public class TelecomCompany implements Company {
	@Override
	public Product createProduct() {
		return new TelecomProduct();
	}
}
