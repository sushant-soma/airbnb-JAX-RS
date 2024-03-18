package com.dao.product;

import java.util.List;

import com.model.Product;

public interface ProductDAO {
	public List<Product> getProducts() throws Exception;
	public List<Product> getMyProducts() throws Exception;
	public Product getProduct(int id) throws Exception;
	public boolean addProduct(Product p) throws Exception;
	public boolean deleteProduct(int id) throws Exception;
	public boolean deleteProduct(Product p) throws Exception;
	public boolean updateProduct(Product p) throws Exception;
}
