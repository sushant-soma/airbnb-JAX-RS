package com.dao.category;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.dao.DBUtil;
import com.model.Category;

public class CategoryDAOH2Impl implements CategoryDAO {
	
	private Connection connection;
	
	public CategoryDAOH2Impl() {
		connection = DBUtil.getConnection();
	}

	@Override
	public List<Category> getCategories() throws Exception {
		List<Category> categories = new ArrayList<Category>();
		String query = "SELECT * FROM CATEGORIES";
		Statement st = connection.createStatement();
		ResultSet rs = st.executeQuery(query);
		while(rs.next()) {
			categories.add(new Category(rs.getInt(1),rs.getString(2)));
		}
		return categories;
	}

	@Override
	public Category getCategory(int id) throws Exception {
		String query = "SELECT * FROM CATEGORIES WHERE id = ?";
		PreparedStatement pst = connection.prepareStatement(query);
		pst.setInt(1, id);
		ResultSet rs = pst.executeQuery();
		if(rs.next()) {
			Category category = new Category(rs.getInt(1),rs.getString(2));
			return category;
		}
		return null;
	}
	
	@Override
	public boolean createCategory(Category c) throws Exception{
		String query = "INSERT INTO CATEGORIES (name) VALUES (?)";
		PreparedStatement pst = connection.prepareStatement(query);
		pst.setString(1, c.getName());
		int res = pst.executeUpdate();
		if(res > 0) {
			return true;
		}
		return false;
	}

	@Override
	public boolean updateCategory(Category c) throws Exception{
		String query = "UPDATE CATEGORIES SET name = ? WHERE id = ?";
		PreparedStatement pst = connection.prepareStatement(query);
		pst.setString(1, c.getName());
		pst.setInt(2, c.getId());
		int res = pst.executeUpdate();
		if(res > 0) {
			return true;
		}
		return false;
	}

	@Override
	public boolean deleteCategory(Category c) throws Exception{
		String query = "DELETE FROM CATEGORIES WHERE id = ?";
		PreparedStatement pst = connection.prepareStatement(query);
		pst.setInt(1, c.getId());
		int res = pst.executeUpdate();
		if(res > 0) {
			return true;
		}
		return false;
	}

}
