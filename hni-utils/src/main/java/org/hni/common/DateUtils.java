package org.hni.common;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class DateUtils {

	public static Date asDate(LocalDate localDate) {
		return Date.from(localDate.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
	}

	public static Date asDate(LocalDateTime localDateTime) {
		return Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
	}

	public static LocalDate asLocalDate(Date date) {
		return Instant.ofEpochMilli(date.getTime()).atZone(ZoneId.systemDefault()).toLocalDate();
	}

	public static LocalDateTime asLocalDateTime(Date date) {
		return Instant.ofEpochMilli(date.getTime()).atZone(ZoneId.systemDefault()).toLocalDateTime();
	}

	public static LocalDate parseDate(String inputString) {
		DateTimeFormatter formatter = DateTimeFormatter
				.ofPattern("" + "[yyyy-MM-dd]" + "[yyyy/MM/dd]" + "[MM-dd-yyyy]" + "[MM/dd/yyyy]");
		LocalDate theDate = LocalDate.parse(inputString, formatter);
		return theDate;
	}
	
	public static LocalDateTime parseDateTime(String inputString) {
		DateTimeFormatter formatter = DateTimeFormatter
				.ofPattern("" + "[yyyy-MM-dd HH:mm:ss]" + "[yyyy/MM/dd HH:mm:ss]" + "[MM-dd-yyyy HH:mm:ss]" + "[MM/dd/yyyy HH:mm:ss]");
		LocalDateTime theDate = LocalDateTime.parse(inputString, formatter);
		return theDate;
	}
}
