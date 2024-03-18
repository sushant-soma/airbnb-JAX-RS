package com.model;

public class Product {
	private int id;
	private String name;
	private String description;
	private boolean available;
	private float price;
	private String cover;
	private Category category;
	private Seller seller;

	public Product() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Product(int id, String name, String description, boolean available, float price, String cover,
			Category category, Seller seller) {
		super();
		this.id = id;
		this.name = name;
		this.description = description;
		this.available = available;
		this.price = price;
		this.cover = cover;
		this.category = category;
		this.seller = seller;
	}

	public Product(String name, String description, boolean available, float price, String cover, Category category,
			Seller seller) {
		this(0, name, description, available, price, cover, category, seller);
	}

	public Product(String name, String description, boolean available, float price, String cover) {
		this(0, name, description, available, price, cover, null, null);
	}

	public Product(int id, String name, String description, boolean available, float price, String cover) {
		this(id, name, description, available, price, cover, null, null);
	}

	public boolean isAvailable() {
		return available;
	}

	public void setAvailable(boolean available) {
		this.available = available;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public float getPrice() {
		return price;
	}

	public void setPrice(float price) {
		this.price = price;
	}

	public String getCover() {
		return cover;
	}

	public void setCover(String cover) {
		this.cover = cover;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	public Seller getSeller() {
		return seller;
	}

	public void setSeller(Seller seller) {
		this.seller = seller;
	}

}
