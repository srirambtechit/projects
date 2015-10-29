package com.ctm.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.ctm.common.StringConstances;

public class StringUtils {

    private static List<String> patterns = new ArrayList<>();

    static {
	patterns.add("lightning");
    }

    public static void main(String[] args) {
	String str = "Writing Fast Tests Against Enterprise Rails 60 min";

	System.out.println(containsNumber(str));

	// String title = StringUtils.getTitle(str);
	// System.out.println("Title : " + title);
	// System.out.println(StringUtils.containsNumber(title));
	//
	// String duration = StringUtils.getLastToken(str);
	// System.out.println(duration);
	// System.out.println(":: " + StringUtils.containsMinute(duration));
	// System.out.println(StringUtils.getDuration(duration));
	// System.out.println(containsNumber(str));
    }

    public static String getTitle(String str) {
	if (isNotEmpty(str)) {
	    return str.substring(0, str.lastIndexOf(' '));
	}
	return "";
    }

    public static int getDuration(String duration) {
	if (isNotEmpty(duration)) {
	    duration = duration.toLowerCase();
	    if (StringConstances.LIGHTNING.equals(duration)) {
		return 5;
	    } else {
		String min = StringConstances.MIN.toLowerCase();
		int minuteIndex = duration.indexOf(min);
		if (minuteIndex > -1) {
		    return Integer.valueOf(duration.substring(0, minuteIndex));
		}
	    }
	}
	return -1;
    }

    public static boolean containsMinute(String str) {
	if (isNotEmpty(str)) {
	    if (StringUtils.excludeTimePattern(str) || (str.toLowerCase().endsWith(StringConstances.MIN) && !str.toLowerCase().endsWith(" min"))) {
		return true;
	    }
	}
	return false;
    }

    public static boolean containsNumber(String str) {
	if (isNotEmpty(str)) {
	    Pattern p = Pattern.compile(".*\\d+.*");
	    Matcher m = p.matcher(str);
	    // below condition will return true if the str has number in it
	    return m.matches();
	}
	return false;
    }

    public static boolean containsValidDuration(String str) {
	if (isNotEmpty(str)) {
	    // checking whether input string has lightning in it
	    String token = getLastToken(str);
	    if (isNotEmpty(token)) {
		if (excludeTimePattern(token)) {
		    return true;
		} else {
		    // checking input string has <Number>min format
		    if (containsMinute(str)) {
			Pattern p = Pattern.compile(".*\\d+.*");
			Matcher m = p.matcher(str);
			return m.matches();
		    } else {
			return false;
		    }
		}
	    }
	}
	return false;
    }

    public static String getLastToken(String str) {
	if (isNotEmpty(str)) {
	    int lastIndex = str.lastIndexOf(' ');
	    if (lastIndex > -1) {
		return str.substring(lastIndex + 1);
	    }
	}
	return "";
    }

    public static boolean excludeTimePattern(String str) {
	if (isNotEmpty(str) && patterns.contains(str)) {
	    return true;
	}
	return false;
    }

    public static boolean isNotEmpty(String str) {
	if (str == null) {
	    return false;
	} else if (str.trim().equals(""))
	    return false;
	return !str.isEmpty();
    }
}
