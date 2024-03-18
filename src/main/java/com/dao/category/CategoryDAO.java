package com.dao.category;

import java.util.List;

import com.model.Category;

public interface CategoryDAO {
	public List<Category> getCategories() throws Exception;

	public Category getCategory(int id) throws Exception;
	
	public boolean createCategory(Category c) throws Exception;

	public boolean updateCategory(Category c) throws Exception;

	public boolean deleteCategory(Category c) throws Exception;
}
