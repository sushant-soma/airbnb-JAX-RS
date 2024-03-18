package com.store;

import com.model.Customer;

public class AuthResponse {
	private Customer user;
	private boolean isSeller;
	
	public AuthResponse(Customer user) {
		super();
		this.user = user;
		this.isSeller = false;
	}
	
	public AuthResponse(Customer user, boolean isSeller) {
		super();
		this.user = user;
		this.isSeller = isSeller;
	}
	
	public Customer getUser() {
		return user;
	}
	public void setUser(Customer user) {
		this.user = user;
	}
	public boolean isSeller() {
		return isSeller;
	}
	public void setSeller(boolean isSeller) {
		this.isSeller = isSeller;
	}
	
}
