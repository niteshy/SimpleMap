package com.example.mapapp;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

import android.util.Log;

public class Utils {
	private final String TAG = "Utils";

	public static long diff(long time, int field) {
		long fieldTime = getFieldInMillis(field);
		Calendar cal = Calendar.getInstance();
		long now = cal.getTimeInMillis();
		return (time / fieldTime - now / fieldTime);
	}

	private static final long getFieldInMillis(int field) {
		final Calendar cal = Calendar.getInstance();
		long now = cal.getTimeInMillis();
		cal.add(field, 1);
		long after = cal.getTimeInMillis();
		return after - now;
	}

	public static String getHumanReadableTime(long timestamp) {
		Date date = new Date(timestamp);
		Calendar cal = Calendar.getInstance();
		TimeZone tz = cal.getTimeZone();
		
		
		
		StringBuilder sb = new StringBuilder();

		SimpleDateFormat time_format = new SimpleDateFormat("hh:mm a ");
		time_format.setTimeZone(tz);
		SimpleDateFormat date_format = new SimpleDateFormat("EEE, dd MMM");
		date_format.setTimeZone(tz);

		sb.append(time_format.format(date) + ", ");

		String daytext = null;
		int timestatus = (int) Utils.diff(date.getTime(),
				Calendar.DAY_OF_YEAR);

		if (timestatus == 0)
			daytext = "Today";
		else if (timestatus == -1)
			daytext = "Yesterday";
		else if (timestatus == 1)
			daytext = "Tomorrow";
		else {
			daytext = date_format.format(date);
		}

		sb.append(daytext);

		return sb.toString();
	}

}
