package com.store;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import com.dao.category.CategoryDAO;
import com.dao.category.CategoryDAOCSVImpl;
import com.dao.category.CategoryDAOH2Impl;
import com.model.Category;

@Path("category")
public class CategoryResource {

	CategoryDAO categoryDAO = new CategoryDAOCSVImpl();

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getCategories() {
		try {
			List<Category> categories = categoryDAO.getCategories();
			if (categories.isEmpty()) {
				return Response.noContent().build();
			}
			GenericEntity<List<Category>> categoryWrapper = new GenericEntity<List<Category>>(categories) {
			};
			return Response.ok(categoryWrapper).build();
		} catch (Exception e) {
			e.printStackTrace();
			return Response.status(Status.INTERNAL_SERVER_ERROR).build();
		}
	}

}
