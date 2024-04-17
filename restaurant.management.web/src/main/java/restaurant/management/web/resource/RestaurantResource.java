package restaurant.management.web.resource;

import java.net.*;
import java.util.Collection;

import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Link;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import restaurant.management.web.data.Category;
import restaurant.management.web.data.Restaurant;
import restaurant.management.web.service.RestaurantRESTFulService;


@Path("/")
public class RestaurantResource {
	RestaurantRESTFulService service = new RestaurantRESTFulService();
	
	@Context
	UriInfo uriInfo;

	@POST
	@Path("/restaurants")
	@Consumes(MediaType.APPLICATION_XML)
	@Produces(MediaType.APPLICATION_XML)
	public Response addRestaurant(Restaurant r) {
		Restaurant restaurant = service.addRestaurant(r);
		if (restaurant == null) {
			return Response.status(Response.Status.BAD_REQUEST).build();
		}
		URI uri = uriInfo.getRequestUri();
		String newUri = uri.getPath();
		return Response.status(Response.Status.CREATED).entity(restaurant).contentLocation(uri.resolve(newUri)).build();
	}


	@GET
	@Path("/restaurants/id/{id}")
	@Produces(MediaType.APPLICATION_XML)
	public Response getRestaurant_BD_Id(@PathParam("id") String id) {
		Restaurant restaurant = service.getRestaurant_BD_Id(id);
		if (restaurant == null) {
			return Response.status(Response.Status.NOT_FOUND).build();
		}
		Link link = Link.fromUri(uriInfo.getRequestUri()).rel("self").type("application/xml").build();
		return Response.status(Response.Status.OK).entity(restaurant).links(link).build();
	}
	
	
	@GET
	@Path("/restaurants")
	@Produces(MediaType.APPLICATION_XML)
	public Response getAllRestaurants() {
		Restaurant[] restaurants = service.getAllRestaurants();
		
		if (restaurants == null) {
			return Response.status(Response.Status.NOT_FOUND).build();
		}

		Link link = Link.fromUri(uriInfo.getRequestUri()).rel("self").type("application/xml").build();
		return Response.status(Response.Status.OK).entity(restaurants).links(link).build();
	}

	@GET
	@Path("/restaurants/name/{name}")
	@Produces(MediaType.APPLICATION_XML)
	public Response getRestaurant_BD_Name(@PathParam("name") String name) {
		
		
		Restaurant restaurant = service.getRestaurant_BD_Name(name);
		
		if (restaurant == null) {
			return Response.status(Response.Status.NOT_FOUND).build();
		}
		
		Link link = Link.fromUri(uriInfo.getRequestUri()).rel("self").type("application/xml").build();
		return Response.status(Response.Status.OK).entity(restaurant).links(link).build();
	}
	
	@GET
	@Path("/categories/category/{cate}")
	@Produces(MediaType.APPLICATION_XML)
	public Response getRestaurantsByCate(@PathParam("cate") String cate) {
		Collection<Restaurant> restaurants = service.searchRestaurantsByCate(cate);	
		if(restaurants.isEmpty()) {
			return Response.status(Response.Status.NO_CONTENT).build();
		}
		
		Restaurant[] restaurants2 = restaurants.toArray(new Restaurant[restaurants.size()]);
		Link link = Link.fromUri(uriInfo.getRequestUri()).rel("self").type("application/xml").build();
		return Response.status(Response.Status.OK).entity(restaurants2).links(link).build();
	}
	
	@POST
	@Path("/categories/category")
	@Consumes(MediaType.APPLICATION_XML)
	@Produces(MediaType.APPLICATION_XML)
	public Response addCategory(Category category) {
		Category c = service.addCategory(category);
		if(c == null) {
			return Response.status(Response.Status.BAD_REQUEST).build();
		}
		URI uri = uriInfo.getRequestUri();
		String newUri = uri.getPath();
		return Response.status(Response.Status.CREATED).entity(c).contentLocation(uri.resolve(newUri)).build();
	}

}
