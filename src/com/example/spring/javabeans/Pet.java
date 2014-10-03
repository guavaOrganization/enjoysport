package com.example.spring.javabeans;

public class Pet {
	private int petId;
	private String petName;

	public int getPetId() {
		return petId;
	}

	public void setPetId(int petId) {
		this.petId = petId;
	}

	public String getPetName() {
		return petName;
	}

	public void setPetName(String petName) {
		this.petName = petName;
	}

	@Override
	public String toString() {
		return "Pet [petId=" + petId + ", petName=" + petName + "]";
	}
}
