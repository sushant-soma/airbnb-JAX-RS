package com.dao.product;

import com.dao.category.*;
import com.dao.seller.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.dao.DBUtil;
import com.dao.Session;
import com.model.Product;
import com.model.Seller;

public class ProductDAOH2Impl implements ProductDAO {

	private Connection connection;

	public ProductDAOH2Impl() {
		connection = DBUtil.getConnection();
	}

	@Override
	public List<Product> getProducts() throws Exception {
		//TODO: Use join queries to prevent initializing others
		List<Product> products = new ArrayList<Product>();
		String query = "SELECT * FROM PRODUCTS";
		Statement st = connection.createStatement();
		ResultSet rs = st.executeQuery(query);
		CategoryDAO categoryDAO = new CategoryDAOH2Impl();
		SellerDAO sellerDAO = new SellerDAOH2Impl();
		int categoryId = 0;
		int sellerId = 0;
		Product result = null;
		while (rs.next()) {
			result = new Product(rs.getInt(1), rs.getString(2), rs.getString(5), rs.getBoolean(6), rs.getFloat(3),
					rs.getString(4));
			categoryId = rs.getInt(8);
			if (categoryId != 0) {
				result.setCategory(categoryDAO.getCategory(categoryId));
			}
			sellerId = rs.getInt(7);
			if (sellerId != 0) {
				result.setSeller(sellerDAO.getSeller(sellerId));
			}
			products.add(result);
		}
		return products;
	}

	@Override
	public List<Product> getMyProducts() throws Exception {
		//TODO: Use join queries to prevent initializing others
		List<Product> products = new ArrayList<Product>();
		if(Session.getSignedInUser() == null || !(Session.getSignedInUser() instanceof Seller)) {
			return products;
		}
		String query = "SELECT * FROM PRODUCTS WHERE s_id = ?";
		PreparedStatement pst = connection.prepareStatement(query);
		pst.setInt(1, Session.getSignedInUser().getId());
		ResultSet rs = pst.executeQuery();
		CategoryDAO categoryDAO = new CategoryDAOH2Impl();
		SellerDAO sellerDAO = new SellerDAOH2Impl();
		int categoryId = 0;
		int sellerId = 0;
		Product result = null;
		while (rs.next()) {
			result = new Product(rs.getInt(1), rs.getString(2), rs.getString(5), rs.getBoolean(6), rs.getFloat(3),
					rs.getString(4));
			categoryId = rs.getInt(8);
			if (categoryId != 0) {
				result.setCategory(categoryDAO.getCategory(categoryId));
			}
			sellerId = rs.getInt(7);
			if (sellerId != 0) {
				result.setSeller(sellerDAO.getSeller(sellerId));
			}
			products.add(result);
		}
		return products;
	}
	
	@Override
	public Product getProduct(int id) throws Exception {
		String query = "SELECT * FROM PRODUCTS WHERE id = ?";
		PreparedStatement pst = connection.prepareStatement(query);
		pst.setInt(1, id);
		ResultSet rs = pst.executeQuery();
		if (rs.next()) {
			CategoryDAO categoryDAO = new CategoryDAOH2Impl();
			SellerDAO sellerDAO = new SellerDAOH2Impl();
			int categoryId = 0;
			int sellerId = 0;
			Product result = new Product(rs.getInt(1), rs.getString(2), rs.getString(5), rs.getBoolean(6), rs.getFloat(3),
					rs.getString(4));
			categoryId = rs.getInt(8);
			if (categoryId != 0) {
				result.setCategory(categoryDAO.getCategory(categoryId));
			}
			sellerId = rs.getInt(7);
			if (sellerId != 0) {
				result.setSeller(sellerDAO.getSeller(sellerId));
			}
			return result;
		}
		return null;
	}

	@Override
	public boolean addProduct(Product p) throws Exception {
		if (p.getSeller() == null) {
			if (Session.getSignedInUser() instanceof Seller) {
				p.setSeller((Seller) Session.getSignedInUser());
				System.out.println("Seller set.");
			} else {
				System.out.println("Couldn't find seller.");
				return false;
			}
		}
		String query = "INSERT INTO PRODUCTS (name, description, available, price, cover, c_id, s_id) VALUES (?,?,?,?,?,?,?)";
		PreparedStatement pst = connection.prepareStatement(query);
		pst.setString(1, p.getName());
		pst.setString(2, p.getDescription());
		pst.setBoolean(3, p.isAvailable());
		pst.setFloat(4, p.getPrice());
		pst.setString(5, p.getCover());
		if (p.getCategory() != null) {
			pst.setInt(6, p.getCategory().getId());
		} else {
			pst.setInt(6, 0);
		}
		pst.setInt(7, p.getSeller().getId());
		int res = pst.executeUpdate();
		if (res > 0) {
			return true;
		}
		return false;
	}

	@Override
	public boolean deleteProduct(int id) throws Exception {
		Seller s = (Seller)Session.getSignedInUser();
		String query = "DELETE FROM PRODUCTS WHERE id = ? AND s_id = ?";
		PreparedStatement pst = connection.prepareStatement(query);
		pst.setInt(1, id);
		pst.setInt(2, s.getId());
		int res = pst.executeUpdate();
		if (res > 0) {
			return true;
		}
		return false;
	}

	@Override
	public boolean deleteProduct(Product p) throws Exception {
		Seller s = (Seller)Session.getSignedInUser();
		String query = "DELETE FROM PRODUCTS WHERE id = ? AND s_id = ?";
		PreparedStatement pst = connection.prepareStatement(query);
		pst.setInt(1, p.getId());
		pst.setInt(2, s.getId());
		int res = pst.executeUpdate();
		if (res > 0) {
			return true;
		}
		return false;
	}

	@Override
	public boolean updateProduct(Product p) throws Exception {
		if (p.getSeller() == null) {
			if (Session.getSignedInUser() instanceof Seller) {
				p.setSeller((Seller) Session.getSignedInUser());
				System.out.println("Seller set.");
			} else {
				System.out.println("Couldn't find seller.");
				return false;
			}
		}
		String query = "UPDATE PRODUCTS SET name = ?, description = ?, available = ?, price = ?, cover = ?, c_id = ? WHERE id = ?  AND s_id = ?";
		PreparedStatement pst = connection.prepareStatement(query);
		pst.setString(1, p.getName());
		pst.setString(2, p.getDescription());
		pst.setBoolean(3, p.isAvailable());
		pst.setFloat(4, p.getPrice());
		pst.setString(5, p.getCover());
		if (p.getCategory() != null) {
			pst.setInt(6, p.getCategory().getId());
		} else {
			pst.setInt(6, 0);
		}
		pst.setInt(7, p.getId());
		pst.setInt(8, ((Seller)Session.getSignedInUser()).getId());
		int res = pst.executeUpdate();
		if (res > 0) {
			return true;
		}
		return false;
	}

}
