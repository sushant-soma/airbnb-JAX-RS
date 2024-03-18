package com.dao;

import com.model.Customer;

public class Session {
	private static Customer signedInUser;

	private Session() {
	}

	public static Customer getSignedInUser() {
		return signedInUser;
	}

	public static void setSignedInUser(Customer signedInUser) {
		Session.signedInUser = signedInUser;
	}

	public static void signOutUser() {
		Session.signedInUser = null;
	}
}
