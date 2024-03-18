package com.dao.customer;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.dao.DBUtil;
import com.dao.Session;
import com.model.Customer;

public class CustomerDAOH2Impl implements CustomerDAO {

	private Connection connection;

	public CustomerDAOH2Impl() {
		connection = DBUtil.getConnection();
	}

	@Override
	public boolean signIn(Customer c) throws Exception {
		String query = "SELECT * FROM CUSTOMERS WHERE email = ? AND password = ?";
		PreparedStatement pst = connection.prepareStatement(query);
		pst.setString(1, c.getEmail());
		pst.setString(2, c.getPassword());
		ResultSet rs = pst.executeQuery();
		if (rs.next()) {
			Customer signedIn = new Customer(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4));
			Session.setSignedInUser(signedIn);
			return true;
		}
		return false;
	}

	@Override
	public boolean signOut() throws Exception {
		Session.signOutUser();
		return true;
	}

	@Override
	public boolean signUp(Customer c) throws Exception {
		String query = "INSERT INTO CUSTOMERS (name, email, password) VALUES (?,?,?)";
		PreparedStatement pst = connection.prepareStatement(query);
		pst.setString(1, c.getName());
		pst.setString(2, c.getEmail());
		pst.setString(3, c.getPassword());
		int res = pst.executeUpdate();
		if (res > 0) {
			return true;
		}
		return false;
	}

	@Override
	public boolean update(Customer c) throws Exception {
		String query = "UPDATE CUSTOMERS SET name = ?, email = ?, password = ? WHERE id = ?";
		PreparedStatement pst = connection.prepareStatement(query);
		pst.setString(1, c.getName());
		pst.setString(2, c.getEmail());
		pst.setString(3, c.getPassword());
		pst.setInt(4, c.getId());
		int res = pst.executeUpdate();
		if (res > 0) {
			return true;
		}
		return false;
	}

}
