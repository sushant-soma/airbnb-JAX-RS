package com.dao.category;

import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;

import com.model.Category;
import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;

public class CategoryDAOCSVImpl implements CategoryDAO {

	private List<Category> categories;
	private int lastIndex;
	private String csvLocation;

	public CategoryDAOCSVImpl() {
		try {
			csvLocation = "C:\\Users\\Prajwal Patil\\Desktop\\Store\\csv\\categories.csv";
			int tempIndex = 0;
			lastIndex = 0;
			categories = new ArrayList<Category>();
			CSVReader reader = new CSVReader(new FileReader(csvLocation));
			String[] nextLine;
			while ((nextLine = reader.readNext()) != null) {
				tempIndex = Integer.parseInt(nextLine[0]);
				if (tempIndex > lastIndex) {
					lastIndex = tempIndex;
				}
				categories.add(new Category(tempIndex, nextLine[1]));
			}
			reader.close();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public List<Category> getCategories() throws Exception {
		return categories;
	}

	@Override
	public Category getCategory(int id) throws Exception {
		for (Category c : categories) {
			if (c.getId() == id) {
				return c;
			}
		}
		return null;
	}

	@Override
	public boolean createCategory(Category c) throws Exception {
		try {
			c.setId(++lastIndex);
			categories.add(c);
			CSVWriter csvWriter = new CSVWriter(new FileWriter(csvLocation));
			for (Category category : categories) {
				String[] rowData = { String.valueOf(category.getId()), category.getName() };
				csvWriter.writeNext(rowData);
			}
			csvWriter.close();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public boolean updateCategory(Category c) throws Exception {
		try {
			CSVWriter csvWriter = new CSVWriter(new FileWriter(csvLocation));
			for (Category category : categories) {
				if (category.getId() == c.getId()) {
					category.setName(c.getName());
				}
				String[] rowData = { String.valueOf(category.getId()), category.getName() };
				csvWriter.writeNext(rowData);
			}
			csvWriter.close();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public boolean deleteCategory(Category c) throws Exception {
		try {
			CSVWriter csvWriter = new CSVWriter(new FileWriter(csvLocation));
			for (Category category : categories) {
				if (category.getId() == c.getId()) {
					categories.remove(category);
				}
			}
			for (Category category : categories) {
				String[] rowData = { String.valueOf(category.getId()), category.getName() };
				csvWriter.writeNext(rowData);
			}
			csvWriter.close();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

}
