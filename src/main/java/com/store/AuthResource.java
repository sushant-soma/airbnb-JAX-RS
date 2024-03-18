package com.store;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.dao.Session;
import com.model.Seller;

@Path("auth")
public class AuthResource {
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getUser() {
		AuthResponse authResponse;
		if (Session.getSignedInUser() == null) {
			return Response.noContent().build();
		} else if (Session.getSignedInUser() instanceof Seller) {
			authResponse = new AuthResponse(Session.getSignedInUser(), true);
		} else {
			authResponse = new AuthResponse(Session.getSignedInUser());
		}
		return Response.ok(authResponse).build();
	}

	@Path("signout")
	@GET
	@Produces(MediaType.TEXT_PLAIN)
	public Response signOutUser() {
		Session.signOutUser();
		return Response.ok("Signed Out successfully!").build();
	}
}
