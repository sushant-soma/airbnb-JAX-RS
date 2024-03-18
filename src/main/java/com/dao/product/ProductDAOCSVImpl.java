package com.dao.product;

import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;

import com.dao.Session;
import com.dao.category.CategoryDAO;
import com.dao.category.CategoryDAOCSVImpl;
import com.dao.seller.SellerDAO;
import com.dao.seller.SellerDAOCSVImpl;
import com.model.Product;
import com.model.Seller;
import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;

public class ProductDAOCSVImpl implements ProductDAO {
	private List<Product> products;
	private int lastIndex;
	private String csvLocation;
	private CategoryDAO categoryDAO;
	private SellerDAO sellerDAO;

	public ProductDAOCSVImpl() {
		categoryDAO = new CategoryDAOCSVImpl();
		sellerDAO = new SellerDAOCSVImpl();
		int tempIndex = 0;
		lastIndex = 0;
		csvLocation = "C:\\Users\\Prajwal Patil\\Desktop\\Store\\csv\\products.csv";
		products = new ArrayList<Product>();
		try {
			CSVReader reader = new CSVReader(new FileReader(csvLocation));
			String[] nextLine;
			while ((nextLine = reader.readNext()) != null) {
				tempIndex = Integer.parseInt(nextLine[0]);
				if (tempIndex > lastIndex) {
					lastIndex = tempIndex;
				}

				products.add(new Product(tempIndex, nextLine[1], nextLine[2], Boolean.parseBoolean(nextLine[3]),
						Float.parseFloat(nextLine[4]), nextLine[5],
						categoryDAO.getCategory(Integer.parseInt(nextLine[6])),
						sellerDAO.getSeller(Integer.parseInt(nextLine[7]))));

			}
			reader.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public List<Product> getProducts() throws Exception {
		return products;
	}

	@Override
	public List<Product> getMyProducts() throws Exception {
		if (Session.getSignedInUser() == null || !(Session.getSignedInUser() instanceof Seller)) {
			return null;
		}
		List<Product> myProducts = new ArrayList<Product>();
		for (Product p : products) {
			if (p.getSeller().getId() == Session.getSignedInUser().getId()) {
				myProducts.add(p);
			}
		}
		return myProducts;
	}

	@Override
	public Product getProduct(int id) throws Exception {
		for (Product p : products) {
			if (p.getId() == id) {
				return p;
			}
		}
		return null;
	}

	@Override
	public boolean addProduct(Product p) throws Exception {
		if (Session.getSignedInUser() == null || !(Session.getSignedInUser() instanceof Seller)) {
			return false;
		}
		try {
			p.setId(++lastIndex);
			p.setSeller((Seller) Session.getSignedInUser());
			products.add(p);
			CSVWriter csvWriter = new CSVWriter(new FileWriter(csvLocation));
			for (Product product : products) {
				String[] rowData = { String.valueOf(product.getId()), product.getName(), product.getDescription(),
						String.valueOf(product.isAvailable()), String.valueOf(product.getPrice()), product.getCover(),
						String.valueOf(product.getCategory().getId()), String.valueOf(product.getSeller().getId()) };
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
	public boolean deleteProduct(int id) throws Exception {
//		(int id, String name, String description, boolean available, float price, String cover,
//		Category category, Seller seller) 
		if (Session.getSignedInUser() == null || !(Session.getSignedInUser() instanceof Seller)) {
			return false;
		}
		try {
			for (Product product : products) {
				if (product.getId() == id && product.getSeller().getId() == Session.getSignedInUser().getId()) {
					products.remove(product);
					break;
				}
			}
			CSVWriter csvWriter = new CSVWriter(new FileWriter(csvLocation));
			for (Product product : products) {
				String[] rowData = { String.valueOf(product.getId()), product.getName(), product.getDescription(),
						String.valueOf(product.isAvailable()), String.valueOf(product.getPrice()), product.getCover(),
						String.valueOf(product.getCategory().getId()), String.valueOf(product.getSeller().getId()) };
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
	public boolean deleteProduct(Product p) throws Exception {
		if (Session.getSignedInUser() == null || !(Session.getSignedInUser() instanceof Seller)) {
			return false;
		}
		try {
			for (Product product : products) {
				if (product.getId() == p.getId() && product.getSeller().getId() == Session.getSignedInUser().getId()) {
					products.remove(product);
					break;
				}
			}
			CSVWriter csvWriter = new CSVWriter(new FileWriter(csvLocation));
			for (Product product : products) {
				String[] rowData = { String.valueOf(product.getId()), product.getName(), product.getDescription(),
						String.valueOf(product.isAvailable()), String.valueOf(product.getPrice()), product.getCover(),
						String.valueOf(product.getCategory().getId()), String.valueOf(product.getSeller().getId()) };
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
	public boolean updateProduct(Product p) throws Exception {
		if (Session.getSignedInUser() == null || !(Session.getSignedInUser() instanceof Seller)) {
			return false;
		}
		try {
			for (Product product : products) {
				if (product.getId() == p.getId() && product.getSeller().getId() == Session.getSignedInUser().getId()) {
					product.setName(p.getName());
					product.setDescription(p.getDescription());
					product.setPrice(p.getPrice());
					product.setCover(p.getCover());
					product.setAvailable(p.isAvailable());
					product.setCategory(p.getCategory());
					break;
				}
			}
			CSVWriter csvWriter = new CSVWriter(new FileWriter(csvLocation));
			for (Product product : products) {
				String[] rowData = { String.valueOf(product.getId()), product.getName(), product.getDescription(),
						String.valueOf(product.isAvailable()), String.valueOf(product.getPrice()), product.getCover(),
						String.valueOf(product.getCategory().getId()), String.valueOf(product.getSeller().getId()) };
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
