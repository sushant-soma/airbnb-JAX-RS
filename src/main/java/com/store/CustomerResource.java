package com.store;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import com.dao.Session;
import com.dao.customer.CustomerDAO;
import com.dao.customer.CustomerDAOCSVImpl;
import com.dao.customer.CustomerDAOH2Impl;
import com.model.Customer;

@Path("customer")
public class CustomerResource {
	CustomerDAO customerDAO = new CustomerDAOCSVImpl();

	@Path("signin")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response signInCustomer(Customer c) {
		try {
			if (customerDAO.signIn(c)) {
				return Response.ok((Customer)Session.getSignedInUser()).build();
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
	public Response signUpCustomer(Customer c) {
		try {
			if (customerDAO.signUp(c)) {
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
	public Response signOutCustomer() {
		try {
			if (customerDAO.signOut()) {
				return Response.ok("Signed out successfully!").build();
			}
			return Response.status(Status.NOT_ACCEPTABLE).build();
		} catch (Exception e) {
			e.printStackTrace();
			return Response.status(Status.INTERNAL_SERVER_ERROR).build();
		}
	}
}
