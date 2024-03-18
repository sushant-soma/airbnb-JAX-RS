package com.dao.seller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.dao.DBUtil;
import com.dao.Session;
import com.model.Seller;

public class SellerDAOH2Impl implements SellerDAO {

	private Connection connection;

	public SellerDAOH2Impl() {
		connection = DBUtil.getConnection();
	}

	public Seller getSeller(int id) throws Exception {
		String query = "SELECT * FROM SELLERS WHERE id = ?";
		PreparedStatement pst = connection.prepareStatement(query);
		pst.setInt(1, id);
		ResultSet rs = pst.executeQuery();
		if (rs.next()) {
			Seller seller = new Seller(rs.getInt(1), rs.getString(2), "", rs.getString(4),
					rs.getString(5));
			return seller;
		}
		return null;
	}
	
	@Override
	public boolean signIn(Seller s) throws Exception {
		String query = "SELECT * FROM SELLERS WHERE email = ? AND password = ?";
		PreparedStatement pst = connection.prepareStatement(query);
		pst.setString(1, s.getEmail());
		pst.setString(2, s.getPassword());
		ResultSet rs = pst.executeQuery();
		if (rs.next()) {
			Seller signedIn = new Seller(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4),
					rs.getString(5));
			Session.setSignedInUser(signedIn);
			return true;
		}
		return false;
	}

	@Override
	public boolean signOut() {
		Session.signOutUser();
		return true;
	}

	@Override
	public boolean signUp(Seller s) throws Exception {
		String query = "INSERT INTO SELLERS (name, email, password, description) VALUES (?,?,?,?)";
		PreparedStatement pst = connection.prepareStatement(query);
		pst.setString(1, s.getName());
		pst.setString(2, s.getEmail());
		pst.setString(3, s.getPassword());
		pst.setString(4, s.getDescription());
		int res = pst.executeUpdate();
		if (res > 0) {
			return true;
		}
		return false;
	}

	@Override
	public boolean update(Seller s) throws Exception {
		String query = "UPDATE SELLERS SET name = ?, email = ?, password = ?, description = ? WHERE id = ?";
		PreparedStatement pst = connection.prepareStatement(query);
		pst.setString(1, s.getName());
		pst.setString(2, s.getEmail());
		pst.setString(3, s.getPassword());
		pst.setString(4, s.getDescription());
		pst.setInt(5, s.getId());
		int res = pst.executeUpdate();
		if (res > 0) {
			return true;
		}
		return false;
	}

}
