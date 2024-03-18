package com.model;

public class Seller extends Customer {
	private String description;

	public Seller() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Seller(String email, String password) {
		super(0, "", email, password);
		// TODO Auto-generated constructor stub
	}
	
	public Seller(int id, String name, String email, String password) {
		super(id, name, email, password);
		// TODO Auto-generated constructor stub
	}

	public Seller(String name, String email, String password) {
		super(name, email, password);
		// TODO Auto-generated constructor stub
	}

	public Seller(String description) {
		super();
		this.description = description;
	}

	public Seller(int id, String name, String email, String password, String description) {
		super(id, name, email, password);
		this.description = description;
	}

	public Seller(String name, String email, String password, String description) {
		super(name, email, password);
		this.description = description;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Override
	public String toString() {
		return super.toString() + "Seller [description=" + description + "]";
	}
	
}
