package com.dao.seller;

import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;

import com.dao.Session;
import com.model.Seller;
import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;

public class SellerDAOCSVImpl implements SellerDAO {

	private List<Seller> sellers;
	private int lastIndex;
	private String csvLocation;

	public SellerDAOCSVImpl() {
		try {
			csvLocation = "C:\\Users\\Prajwal Patil\\Desktop\\Store\\csv\\sellers.csv";
			int tempIndex = 0;
			lastIndex = 0;
			sellers = new ArrayList<Seller>();
			CSVReader reader = new CSVReader(new FileReader(csvLocation));
			String[] nextLine;
			while ((nextLine = reader.readNext()) != null) {
				tempIndex = Integer.parseInt(nextLine[0]);
				if (tempIndex > lastIndex) {
					lastIndex = tempIndex;
				}
				sellers.add(new Seller(tempIndex, nextLine[1], nextLine[2], nextLine[3], nextLine[4]));
			}
			reader.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public Seller getSeller(int id) throws Exception {
		for (Seller s : sellers) {
			if (s.getId() == id) {
				return s;
			}
		}
		return null;
	}

	@Override
	public boolean signIn(Seller s) throws Exception {
		for (Seller seller : sellers) {
			if (seller.getEmail().equals(s.getEmail()) && seller.getPassword().equals(s.getPassword())) {
				Session.setSignedInUser((Seller)seller);
				return true;
			}
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
		try {
			s.setId(++lastIndex);
			sellers.add(s);
			CSVWriter csvWriter = new CSVWriter(new FileWriter(csvLocation));
			for (Seller seller : sellers) {
				String[] rowData = { String.valueOf(seller.getId()), seller.getName(), seller.getEmail(),
						seller.getPassword(), seller.getDescription() };
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
	public boolean update(Seller s) throws Exception {
		try {
			CSVWriter csvWriter = new CSVWriter(new FileWriter(csvLocation));
			for (Seller seller : sellers) {
				if(seller.getId() == s.getId()) {
					seller.setName(s.getName());
					seller.setEmail(s.getEmail());
					seller.setPassword(s.getPassword());
					seller.setDescription(s.getDescription());
				}
				String[] rowData = { String.valueOf(seller.getId()), seller.getName(), seller.getEmail(),
						seller.getPassword(), seller.getDescription() };
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
