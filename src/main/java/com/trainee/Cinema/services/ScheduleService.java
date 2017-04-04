package com.trainee.Cinema.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.trainee.Cinema.models.Movieshow;
import com.trainee.Cinema.storage.DataStorage;

public class ScheduleService {
	private Map<Long, Movieshow> movieshows = DataStorage.getSchedule();

	public List<Movieshow> getSchedule() {
		return new ArrayList<Movieshow>(movieshows.values());
	}
	
	public Movieshow getScheduleById(long id) {
		return movieshows.get(id);
	}

}