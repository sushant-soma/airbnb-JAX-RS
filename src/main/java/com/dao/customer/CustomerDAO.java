package com.dao.customer;

import com.model.Customer;

public interface CustomerDAO {
	public boolean signIn(Customer c) throws Exception;

	public boolean signOut() throws Exception;

	public boolean signUp(Customer c) throws Exception;

	public boolean update(Customer c) throws Exception;
}
