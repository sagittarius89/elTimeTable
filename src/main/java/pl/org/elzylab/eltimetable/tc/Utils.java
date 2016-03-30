/**
 * =====================================================================
 * License
 * =====================================================================
 * Copyright (C) ${date} elZyLab
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License.
 * 
 * @author Piotr Zych
 */

package pl.org.elzylab.eltimetable.tc;

import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Array;
import java.lang.reflect.Type;
import java.security.Principal;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringEscapeUtils;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import pl.org.elzylab.eltimetable.tc.auth.UserData;

/**
 * ====================================================================
 * Description
 * ==================================================================== Class
 * contains helpfull methods that do not match to any other class
 * 
 *
 */
public class Utils {

	public static final String TIMESTAMP_PATTERN = "yyyy/MM/dd hh:mm";

	/**
	 * Converts given string to time stamp
	 * 
	 * @param timestamp
	 *            string contains date with format yyyy/MM/dd hh:mm
	 * @return Timestamp object created on base given timestamp string
	 */
	public static Timestamp stringToTimestamp(String timestamp) {
		try {
			SimpleDateFormat dateFormat =
				new SimpleDateFormat(TIMESTAMP_PATTERN);
			Date date = dateFormat.parse(timestamp);

			return new Timestamp(date.getTime());
		} catch (ParseException e) {
		}

		return null;
	}

	/**
	 * Returns UserData object (which keep usefull data about user) from
	 * request's session
	 * 
	 * @param request
	 * @return UserData object
	 */
	public static UserData getUserData(HttpServletRequest request) {
		Principal principal = request.getUserPrincipal();

		if (principal == null)
			return null;

		return (UserData) principal;
	}

	/**
	 * Get writer stream from response, setups encoding to UTF-8, and content to
	 * JSON and pack it into PriontWriter
	 * 
	 * @param response
	 * @return setuped PrintWriter
	 */
	public static PrintWriter getPrintWriter(HttpServletResponse response) {

		try {
			response.setCharacterEncoding("UTF-8");
			response.setHeader("Content-Type", "text/json; charset=UTF-8");

			return response.getWriter();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Converts given map to json as one solid object and prints it to response
	 * object
	 * 
	 * @param response
	 * @param map
	 */
	public static void returnJson(HttpServletResponse response,
		Map<String, Object> map) {
		StringBuilder buffer = new StringBuilder();
		buffer.append("{");
		for (Entry<String, Object> entry : map.entrySet()) {
			if (buffer.length() > 1)
				buffer.append(",");

			buffer.append("\"");
			buffer.append(StringEscapeUtils.escapeJson(entry.getKey()));
			buffer.append("\": ");

			Object value = entry.getValue();
			boolean isNumber = false;
			boolean isArray = false;

			if (value instanceof Number) {
				isNumber = true;
			} else if (value instanceof Array || value instanceof Collection) {
				isArray = true;
			}

			if(value == null)
			{
				buffer.append("\"null\"");
			} else if (isArray) {
				Gson gson = new Gson();
				buffer.append(gson.toJson(value));
			} else {
				if (!isNumber)
					buffer.append("\"");
				buffer.append(StringEscapeUtils.escapeJson(value.toString()));

				if (!isNumber)
					buffer.append("\"");
			}
		}
		buffer.append("}");

		PrintWriter writer = getPrintWriter(response);

		writer.println(buffer);
		writer.flush();
	}

	/**
	 * check if given string is valid integer value
	 * 
	 * @param s
	 * @return is given string an iteger
	 */
	public static boolean isInteger(String s) {
		return isInteger(s, 10);
	}

	/**
	 * check if given string is valid integer value with radix
	 * 
	 * @param s
	 * @param radix
	 * @return is given string an iteger
	 */
	public static boolean isInteger(String s, int radix) {
		if (s.isEmpty())
			return false;
		for (int i = 0; i < s.length(); i++) {
			if (i == 0 && s.charAt(i) == '-') {
				if (s.length() == 1)
					return false;
				else
					continue;
			}
			if (Character.digit(s.charAt(i), radix) < 0)
				return false;
		}
		return true;
	}
	
	/**
	 * Retrieves list of integer from json
	 * 
	 * @param json
	 * @return
	 */
	public static List<Integer> getListOfIntegerFromJson(String json)
	{
		Type type = new TypeToken<ArrayList<Integer>>() {}.getType();
		Gson gson = new Gson();
		
		return gson.fromJson(json, type);
	}
}
