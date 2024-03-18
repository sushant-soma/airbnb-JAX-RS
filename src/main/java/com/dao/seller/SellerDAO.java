package com.dao.seller;

import com.model.Seller;

public interface SellerDAO {
	public Seller getSeller(int id) throws Exception;

	public boolean signIn(Seller s) throws Exception;

	public boolean signOut();

	public boolean signUp(Seller s) throws Exception;

	public boolean update(Seller s) throws Exception;
}
