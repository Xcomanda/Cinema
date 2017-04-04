package com.trainee.Cinema.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtil {
	public static final String STORAGE_DATE_FORMAT = PropertyUtil.getProperty("storage_date_format");
	public static final String QUERY_DATE_FORMAT = PropertyUtil.getProperty("query_date_format");;

	private static SimpleDateFormat storageFormatter = new SimpleDateFormat(STORAGE_DATE_FORMAT);
	private static SimpleDateFormat queryFormatter = new SimpleDateFormat(QUERY_DATE_FORMAT);

	public static Date parseStorageDate(String string) {
		try {
			return storageFormatter.parse(string);
		} catch (ParseException e) {
			throw new RuntimeException("Invalid date format", e);
		}
	}

	public static Date parseQueryDate(String string) {
		try {
			return queryFormatter.parse(string);
		} catch (ParseException e) {
			throw new RuntimeException("Invalid date format", e);
		}
	}
	
	public static String dateToStorageFormat(Date date){
		return storageFormatter.format(date);
	}
}
