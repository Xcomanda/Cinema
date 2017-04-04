package com.trainee.Cinema.resources;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.trainee.Cinema.models.Movieshow;
import com.trainee.Cinema.services.ScheduleService;

@Path("/schedule")
@Produces(MediaType.APPLICATION_JSON)
public class ScheduleResource {

	private ScheduleService scheduleService = new ScheduleService();

	@GET
	public List<Movieshow> getSchedule() {
		return scheduleService.getSchedule();
	}

}