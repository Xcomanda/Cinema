package com.trainee.Cinema.resources;

import java.util.Date;

import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import com.trainee.Cinema.models.Movieshow;
import com.trainee.Cinema.models.Order;
import com.trainee.Cinema.services.OrderService;
import com.trainee.Cinema.services.ScheduleService;
import com.trainee.Cinema.utils.DateUtil;

@Path("/order")
@Produces(MediaType.APPLICATION_JSON)
public class OrderResource {

	private OrderService orderService = new OrderService();

	@GET
	@Path("/{orderId}")
	public Order getOrder(@PathParam("orderId") long id) {
		return orderService.getOrder(id);
	}

	@POST
	public Response addOrder(@QueryParam("movieId") long movieId,
							@QueryParam("tickets") int tickets,
							@QueryParam("date") String dateString) {
		Date date = DateUtil.parseQueryDate(dateString);
		if (movieId > 0 && tickets > 0) {
			Movieshow movieshow = new ScheduleService().getScheduleById(movieId);
			if (movieshow != null && movieshow.getShows().contains(date)) {
				return Response.status(Status.CREATED).entity(orderService.addOrder(movieId, tickets, date)).build();
			}
		}
		return Response.status(Status.BAD_REQUEST).build();
	}

	@DELETE
	@Path("/{orderId}")
	public Response removeOrder(@PathParam("orderId") long id) {
		if (id > 0) {
			if (orderService.removeOrder(id) != null) {
				return Response.status(Status.OK).build();
			}
		}
		return Response.status(Status.NOT_FOUND).build();
	}

}
