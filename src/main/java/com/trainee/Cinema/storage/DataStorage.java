package com.trainee.Cinema.storage;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.trainee.Cinema.models.Movieshow;
import com.trainee.Cinema.models.Order;
import com.trainee.Cinema.utils.DateUtil;
import com.trainee.Cinema.utils.PropertyUtil;

public class DataStorage {
	public static final String SCHEDULE_FILE_NAME = PropertyUtil.getProperty("schedule_file_name");
	public static final String ORDERS_FILE_NAME = PropertyUtil.getProperty("orders_file_name");
	public static final String STORAGE_PATH = PropertyUtil.getProperty("storage_path");

	private static Map<Long, Movieshow> movieshows;
	private static Map<Long, Order> orders;

	static {
		String jsonString = readFile(SCHEDULE_FILE_NAME);
		movieshows = parseSchedule(jsonString);
		jsonString = readFile(ORDERS_FILE_NAME);
		orders = parseOrders(jsonString);
	}

	public static Map<Long, Movieshow> getSchedule() {
		return movieshows;
	}

	public static Map<Long, Order> getOrders() {
		return orders;
	}

	public static void addOrder(Order order) {
		orders.put(order.getId(), order);
		updateOrders();
	}
	
	public static Order removeOrder(long id) {
		Order order = orders.remove(id);
		updateOrders();
		return order;
	}

	@SuppressWarnings("unchecked")
	private static void updateOrders() {
		JSONArray jsonOrders = new JSONArray();
		for (Order order : orders.values()) {
			JSONObject jsonOrder = new JSONObject();
			jsonOrder.put("tickets", order.getTickets());
			jsonOrder.put("movieId", order.getMovieId());
			jsonOrder.put("showDate", DateUtil.dateToStorageFormat(order.getShowDate()));
			jsonOrder.put("id", order.getId());
			jsonOrders.add(jsonOrder);
		}
		writeFile(ORDERS_FILE_NAME, jsonOrders.toJSONString());
		String jsonString = readFile(ORDERS_FILE_NAME);
		orders = parseOrders(jsonString);
	}

	private static Map<Long, Order> parseOrders(String jsonString) {
		Map<Long, Order> orders = new HashMap<Long, Order>();

		JSONParser parser = new JSONParser();
		JSONArray jsonOrders;
		try {
			jsonOrders = (JSONArray) parser.parse(jsonString);
			for (Object objOrder : jsonOrders) {
				Order order = new Order();
				JSONObject jsonOrder = (JSONObject) objOrder;
				order.setId(Long.parseUnsignedLong(jsonOrder.get("id").toString()));
				order.setMovieId(Long.parseUnsignedLong(jsonOrder.get("movieId").toString()));
				order.setShowDate(DateUtil.parseStorageDate(jsonOrder.get("showDate").toString()));
				order.setTickets(Integer.parseInt((jsonOrder.get("tickets").toString())));
				orders.put(order.getId(), order);
			}
		} catch (ParseException e) {
			throw new RuntimeException("Invalid JSON-data format", e);
		}
		return orders;
	}

	private static Map<Long, Movieshow> parseSchedule(String jsonString) {
		Map<Long, Movieshow> movieshows = new HashMap<Long, Movieshow>();

		JSONParser parser = new JSONParser();
		JSONArray jsonMovies;
		try {
			jsonMovies = (JSONArray) parser.parse(jsonString);

			for (Object objMovie : jsonMovies) {
				Movieshow movieshow = new Movieshow();
				JSONObject jsonMovie = (JSONObject) objMovie;
				JSONArray jsonDates = (JSONArray) parser.parse(jsonMovie.get("shows").toString());

				movieshow.setId(Long.parseUnsignedLong(jsonMovie.get("id").toString()));
				movieshow.setTitle(jsonMovie.get("title").toString());

				Set<Date> dates = new HashSet<Date>();
				for (Object objDate : jsonDates) {
					Date date = DateUtil.parseStorageDate(objDate.toString());
					dates.add(date);
				}
				movieshow.setShows(dates);
				movieshows.put(movieshow.getId(), movieshow);
			}
		} catch (ParseException e) {
			throw new RuntimeException("Invalid JSON-data format", e);
		}
		return movieshows;
	}

	private static String readFile(String fileName) {
		StringBuilder stringBuilder = new StringBuilder();
		try {
			Reader reader = new InputStreamReader(new FileInputStream(STORAGE_PATH + fileName), "UTF-8");
			BufferedReader br = new BufferedReader(reader);
			String line;
			while ((line = br.readLine()) != null) {
				stringBuilder.append(line);
			}
			br.close();
			reader.close();
		} catch (IOException e) {
			throw new RuntimeException("Can not load " + fileName, e);
		}
		return stringBuilder.toString();
	}

	private static void writeFile(String fileName, String data) {
		try {
			FileWriter fw = new FileWriter(STORAGE_PATH + fileName);
			fw.write(data);
			fw.close();
		} catch (IOException e) {
			throw new RuntimeException("Can not write to " + fileName, e);
		}
	}

}