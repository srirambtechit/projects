package com.ctm.processor;

import com.ctm.utils.DateUtils;

/**
 * Singleton class for loading timing configruation
 * 
 * @author Sriram
 *
 */
public final class TimeConfiguration {

    private TimeConfiguration() {
    }

    public static final String morningStartTime = "09:00 AM";

    public static final String lunchStartTime = "12:00 PM";

    public static final String afternoonStartTime = "01:00 PM";

    public static final String networkSessionMinStartTime = "04:00 PM";

    public static final String networkSessionMaxStartTime = "05:00 PM";

    public int getMorningSessionDurationInMinutes() {
	return DateUtils.differenceOf(morningStartTime, lunchStartTime);
    }

    public int getAfternoonSessionDurationInMinutes() {
	return DateUtils.differenceOf(afternoonStartTime, networkSessionMaxStartTime);
    }

    public static TimeConfiguration getInstance() {
	return TimeConfig.INSTANCE;
    }

    private static class TimeConfig {
	private static final TimeConfiguration INSTANCE = new TimeConfiguration();
    }

}
