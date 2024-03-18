package com.dao.customer;

import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;

import com.dao.Session;
import com.model.Customer;
import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;

public class CustomerDAOCSVImpl implements CustomerDAO {

	private List<Customer> customers;
	private int lastIndex;
	private String csvLocation;

	public CustomerDAOCSVImpl() {
		try {
			csvLocation = "C:\\Users\\Prajwal Patil\\Desktop\\Store\\csv\\customers.csv";
			int tempIndex = 0;
			lastIndex = 0;
			customers = new ArrayList<Customer>();
			CSVReader reader = new CSVReader(new FileReader(csvLocation));
			String[] nextLine;
			while ((nextLine = reader.readNext()) != null) {
				tempIndex = Integer.parseInt(nextLine[0]);
				if (tempIndex > lastIndex) {
					lastIndex = tempIndex;
				}
				customers.add(new Customer(tempIndex, nextLine[1], nextLine[2], nextLine[3]));
			}
			reader.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public boolean signIn(Customer c) throws Exception {
		for (Customer customer : customers) {
			if (customer.getEmail().equals(c.getEmail()) && customer.getPassword().equals(c.getPassword())) {
				Session.setSignedInUser((Customer) customer);
				return true;
			}
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
		try {
			c.setId(++lastIndex);
			customers.add(c);
			CSVWriter csvWriter = new CSVWriter(new FileWriter(csvLocation));
			for (Customer customer : customers) {
				String[] rowData = { String.valueOf(customer.getId()), customer.getName(), customer.getEmail(),
						customer.getPassword() };
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
	public boolean update(Customer c) throws Exception {
		try {
			CSVWriter csvWriter = new CSVWriter(new FileWriter(csvLocation));
			for (Customer customer : customers) {
				if (customer.getId() == c.getId()) {
					customer.setName(c.getName());
					customer.setEmail(c.getEmail());
					customer.setPassword(c.getPassword());
				}
				String[] rowData = { String.valueOf(customer.getId()), customer.getName(), customer.getEmail(),
						customer.getPassword() };
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
