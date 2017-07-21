package com.msrm.jdk8.features;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;

public class DateTimeDemo {

	public static void main(String[] args) {
		LocalDateTime timePoint = LocalDateTime.now(); // The current date and
														// time
		System.out.println(timePoint);

		LocalDate.of(2012, Month.DECEMBER, 12); // from values
		LocalDate.ofEpochDay(150); // middle of 1970
		LocalTime.of(17, 18); // the train I took home today
		LocalTime.parse("10:15:30"); // From a String
		
		LocalDate theDate = timePoint.toLocalDate();
		System.out.println("LocalDate : " + theDate);
		Month month = timePoint.getMonth();
		int day = timePoint.getDayOfMonth();
		int second = timePoint.getSecond();
		System.out.printf("Date time extraction Month: %s, Day: %d, Second: %d%n", month, day, second);

		// Set the value, returning a new object
		LocalDateTime thePast = timePoint.withDayOfMonth(10).withYear(2010);
		System.out.println("Past date : " + thePast);

		/*
		 * You can use direct manipulation methods, or pass a value and field
		 * pair
		 */
		LocalDateTime yetAnother = thePast.plusWeeks(3).plus(3, ChronoUnit.WEEKS);
		System.out.println("Date after three week from " + thePast + " is " + yetAnother);

		// Truncation
		LocalTime time = LocalTime.of(1, 30);
		LocalTime truncatedTime = time.truncatedTo(ChronoUnit.SECONDS);
		System.out.println("TruncatedTime : " + truncatedTime);

		// You can specify the zone id when creating a zoned date time
		ZoneId id = ZoneId.of("Europe/Paris");
		ZonedDateTime zoned = ZonedDateTime.of(timePoint, id);
		System.out.println("ZonedDateTime : " + zoned);
	}

}
