package com.example.mapapp;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Utils {

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
		Date date = new Date(timestamp * 1000);
		StringBuilder sb = new StringBuilder();

		SimpleDateFormat time_format = new SimpleDateFormat("hh:mm a ");
		SimpleDateFormat date_format = new SimpleDateFormat("EEE, dd MMM");

		sb.append(time_format.format(date) + ", ");

		String pickupdaytext = null;
		int pickuptimestatus = (int) Utils.diff(date.getTime(),
				Calendar.DAY_OF_YEAR);

		if (pickuptimestatus == 0)
			pickupdaytext = "Today";
		else if (pickuptimestatus == -1)
			pickupdaytext = "Yesterday";
		else if (pickuptimestatus == 1)
			pickupdaytext = "Tomorrow";
		else {
			pickupdaytext = date_format.format(date);
		}

		sb.append(pickupdaytext);

		return sb.toString();
	}

}
