package com.example.javalang.cloneable;

public class CloneableUserAddress implements Cloneable{
	private String address;

	public CloneableUserAddress(String address) {
		this.address = address;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}
	
	@Override
	public Object clone() throws CloneNotSupportedException {
		return super.clone();
	}

	@Override
	public String toString() {
		return "CloneableUserAddress [address=" + address + "]";
	}
}
