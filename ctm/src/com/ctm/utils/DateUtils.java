package com.ctm.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtils {

    private static final String timeFormat = "hh:mm a";

    private static Calendar cal;

    static {
	init();
    }

    private DateUtils() {
    }

    private static void init() {
	cal = Calendar.getInstance();
	cal.set(Calendar.HOUR, 9);
	cal.set(Calendar.MINUTE, 0);
    }

    public static String formatDate(Date date) {
	SimpleDateFormat sdf = new SimpleDateFormat(timeFormat);
	return sdf.format(date);
    }

    public static Date formatDate(String date) {
	SimpleDateFormat sdf = new SimpleDateFormat(timeFormat);
	try {
	    return sdf.parse(date);
	} catch (ParseException e) {
	    System.out.println("Exception : " + e.getMessage());
	}
	return null;
    }

    public static String addTime(int minutes) {
	cal.add(Calendar.MINUTE, minutes);
	return formatDate(cal.getTime());
    }

    public static String minusTime(int minutes) {
	cal.add(Calendar.MINUTE, -minutes);
	return formatDate(cal.getTime());
    }

    public static int differenceOf(String timeOne, String timeTwo) {
	Date dateOne = formatDate(timeOne);
	Date dateTwo = formatDate(timeTwo);
	long milliesOne = dateOne.getTime();
	long milliesTwo = dateTwo.getTime();
	long diff = 0;
	if (milliesOne > milliesTwo) {
	    diff = milliesOne - milliesTwo;
	} else {
	    diff = milliesTwo - milliesOne;
	}
	return convertToMinuteFromMillies(diff);
    }

    private static int convertToMinuteFromMillies(long millies) {
	return (int) (millies / (1000 * 60));
    }

    public static void main(String[] args) {
	Date date = new Date();
	System.out.println(DateUtils.formatDate(date));
	System.out.println(addTime(30));
	System.out.println(addTime(40));
	System.out.println(minusTime(30));

	String s1 = "09:00 AM";
	String s2 = "12:00 PM";
	System.out.println(differenceOf(s1, s2));
    }

}
