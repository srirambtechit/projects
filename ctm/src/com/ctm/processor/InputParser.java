package com.ctm.processor;

import java.util.ArrayList;
import java.util.List;

import com.ctm.beans.Talk;
import com.ctm.common.ErrorConstants;
import com.ctm.common.StringConstances;
import com.ctm.exceptions.CTMException;
import com.ctm.exceptions.InvalidInputException;
import com.ctm.exceptions.InvalidTimeDurationException;
import com.ctm.exceptions.InvalidTitleException;
import com.ctm.utils.FileUtils;
import com.ctm.utils.StringUtils;

public class InputParser {

    private List<String> contents;

    public InputParser(List<String> contents) {
	this.contents = contents;
    }

    public static void main(String[] args) {
	try {
	    InputParser parser = new InputParser(FileUtils.readContent("input.txt"));
	    List<Talk> data = parser.parseData();
	    for (Talk talk : data) {
		System.out.println(talk);
	    }

	} catch (CTMException e) {
	    System.out.println(e.getMessage());
	}
    }

    public List<Talk> parseData() throws CTMException {
	List<Talk> talks = new ArrayList<>();
	if (contents != null) {
	    for (String content : contents) {
		if (!validInput(content)) {
		    throw new InvalidInputException(ErrorConstants.ERR_DATA_01);
		}

		String title = StringUtils.getTitle(content);
		if (!validTitleString(title)) {
		    throw new InvalidTitleException(ErrorConstants.ERR_DATA_02);
		}

		String duration = StringUtils.getLastToken(content);
		if (!validDurationString(duration)) {
		    throw new InvalidTimeDurationException(ErrorConstants.ERR_DATA_03);
		}

		Talk talk = new Talk(title, StringUtils.getDuration(duration));
		talks.add(talk);
	    }
	}
	return talks;
    }

    /**
     * <pre>
     * Conditions checked:-
     * 	1. checking input has title part
     *  2. checking input has valid time duration part
     *  3. checking input contains minute word as <Number>min format
     *  4. checking input contains number to represent time
     * </pre>
     * 
     * @param content
     * @return
     */
    private boolean validInput(String content) {
	if (StringUtils.isNotEmpty(content)) {
	    // checking content has valid title and time duration part in it
	    int len = content.trim().split(" ").length;
	    if (len >= 2) {
		if (content.trim().toLowerCase().endsWith(StringConstances.LIGHTNING)) {
		    return true;
		} else if (StringUtils.containsValidDuration(content) && StringUtils.containsMinute(content) && StringUtils.containsNumber(content)) {
		    return true;
		}
	    }
	}
	return false;
    }

    /**
     * <pre>
     * Conditions checked:-
     * 	1. duration should not be null/blank (empty String)
     *  2. time should start from number and end with minute
     *  3. checking time not more than Hour and not less than 5 Minutes(ligtning)
     * </pre>
     * 
     * @param duration
     * @return
     */
    private boolean validDurationString(String duration) {
	if (StringUtils.isNotEmpty(duration)) {
	    if (StringUtils.containsMinute(duration)) {
		// validating minutes
		int minutes = StringUtils.getDuration(duration);
		if (minutes > 60 || minutes < 5) {
		    return false;
		}

		return true;
	    }
	}
	return false;
    }

    /**
     * <pre>
     * Conditions checked:-
     * 	1. title should not be null/blank (empty String)
     * 	2. title should not contain number
     * </pre>
     * 
     * @param title
     * @return
     */
    private boolean validTitleString(String title) {
	if (StringUtils.isNotEmpty(title)) {
	    if (!StringUtils.containsNumber(title)) {
		return true;
	    }
	}
	return false;
    }
}
