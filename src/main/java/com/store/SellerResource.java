package com.store;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import com.dao.Session;
import com.dao.seller.SellerDAO;
import com.dao.seller.SellerDAOCSVImpl;
import com.dao.seller.SellerDAOH2Impl;
import com.model.Seller;

@Path("seller")
public class SellerResource {

	SellerDAO sellerDAO = new SellerDAOCSVImpl();

	@Path("signin")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response signInSeller(Seller s) {
		try {
			if (sellerDAO.signIn(s)) {
				return Response.ok(Session.getSignedInUser()).build();
			}
			return Response.status(Status.NOT_ACCEPTABLE).build();
		} catch (Exception e) {
			e.printStackTrace();
			return Response.status(Status.INTERNAL_SERVER_ERROR).build();
		}
	}

	@Path("signup")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response signUpSeller(Seller s) {
		try {
			if (sellerDAO.signUp(s)) {
				return Response.ok("Signed up successfully").build();
			}
			return Response.status(Status.NOT_ACCEPTABLE).build();
		} catch (Exception e) {
			e.printStackTrace();
			return Response.status(Status.INTERNAL_SERVER_ERROR).build();
		}
	}

	@Path("signout")
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	public Response signOutSeller() {
		try {
			if (sellerDAO.signOut()) {
				return Response.ok("Signed out successfully!").build();
			}
			return Response.status(Status.NOT_ACCEPTABLE).build();
		} catch (Exception e) {
			e.printStackTrace();
			return Response.status(Status.INTERNAL_SERVER_ERROR).build();
		}
	}
}
