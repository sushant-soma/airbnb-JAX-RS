package com.store;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import com.dao.product.ProductDAO;
import com.dao.product.ProductDAOCSVImpl;
import com.dao.product.ProductDAOH2Impl;
import com.model.Product;

@Path("products")
public class ProductResource {

	ProductDAO productDAO = new ProductDAOCSVImpl();

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getProducts() {
		try {
			List<Product> products = productDAO.getProducts();
			if (products.isEmpty()) {
				System.out.println("No products found!");
				return Response.noContent().build();
			}
			GenericEntity<List<Product>> productWrapper = new GenericEntity<List<Product>>(products) {
			};
			return Response.ok(productWrapper).build();
		} catch (Exception e) {
			e.printStackTrace();
			return Response.status(Status.INTERNAL_SERVER_ERROR).build();
		}
	}

	@Path("manage")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getMyProducts() {
		try {
			List<Product> products = productDAO.getMyProducts();
			if (products.isEmpty()) {
				System.out.println("No products found!");
				return Response.noContent().build();
			}
			GenericEntity<List<Product>> productWrapper = new GenericEntity<List<Product>>(products) {
			};
			return Response.ok(productWrapper).build();
		} catch (Exception e) {
			e.printStackTrace();
			return Response.status(Status.INTERNAL_SERVER_ERROR).build();
		}
	}
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response addProduct(Product p) {
		try {
			if (productDAO.addProduct(p)) {
				return Response.ok("Product created successfully!").build();
			}
			return Response.status(Status.BAD_REQUEST).build();
		} catch (Exception e) {
			e.printStackTrace();
			return Response.status(Status.INTERNAL_SERVER_ERROR).build();
		}
	}
	
	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response updateProduct(Product p) {
		try {
			if (productDAO.updateProduct(p)) {
				return Response.ok(p).build();
			}
			return Response.status(Status.BAD_REQUEST).build();
		} catch (Exception e) {
			e.printStackTrace();
			return Response.status(Status.INTERNAL_SERVER_ERROR).build();
		}
	}
	
	@Path("{id}")
	@DELETE
	@Produces(MediaType.APPLICATION_JSON)
	public Response deleteProduct(@PathParam("id")int id) {
		try {
			if (productDAO.deleteProduct(id)) {
				return Response.ok("Product was deleted.").build();
			}
			return Response.status(Status.BAD_REQUEST).build();
		} catch (Exception e) {
			e.printStackTrace();
			return Response.status(Status.INTERNAL_SERVER_ERROR).build();
		}
	}
	
	
	@Path("{id}")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getProduct(@PathParam("id") int id) {
		try {
			Product p = productDAO.getProduct(id);
			if (p == null) {
				System.out.println("No product found!");
				return Response.noContent().build();
			}
			System.out.println("Products fetched.");
			return Response.ok(p).build();
		} catch (Exception e) {
			e.printStackTrace();
			return Response.status(Status.INTERNAL_SERVER_ERROR).build();
		}
	}

}
