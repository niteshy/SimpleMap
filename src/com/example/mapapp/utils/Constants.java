package com.example.mapapp.utils;
public final class Constants {

    // Used to track what type of request is in process
    public enum REQUEST_TYPE {ADD, REMOVE}
    public final static String APPTAG = "MapApp";
    /*
     * Define a request code to send to Google Play services
     * This code is returned in Activity.onActivityResult
     */
    public final static int CONNECTION_FAILURE_RESOLUTION_REQUEST = 9000;

    // Intent actions and extras for sending information from the IntentService to the Activity
    public static final String ACTION_CONNECTION_ERROR =
            "com.example.mapapp.activity.ACTION_CONNECTION_ERROR";

    public static final String ACTION_REFRESH_STATUS_LIST =
                    "com.example.mapapp.activity.ACTION_REFRESH_STATUS_LIST";

    public static final String CATEGORY_LOCATION_SERVICES =
            "com.example.mapapp.activity.CATEGORY_LOCATION_SERVICES";

    public static final String EXTRA_CONNECTION_ERROR_CODE =
            "com.example.mapapp.activity.EXTRA_CONNECTION_ERROR_CODE";

    public static final String EXTRA_CONNECTION_ERROR_MESSAGE =
            "com.example.mapapp.activity.EXTRA_CONNECTION_ERROR_MESSAGE";

    // Constants used to establish the activity update interval
    public static final int MILLISECONDS_PER_SECOND = 1000;

    public static final int DETECTION_INTERVAL_SECONDS = 1;

    public static final int DETECTION_INTERVAL_MILLISECONDS =
            MILLISECONDS_PER_SECOND * DETECTION_INTERVAL_SECONDS;

    // Key in the repository for the previous activity
    public static final String KEY_PREVIOUS_ACTIVITY_TYPE =
            "com.example.mapapp.activity.KEY_PREVIOUS_ACTIVITY_TYPE";
    

	public static final int TEXT_SIZE_XHDPI = 24;
	public static final int TEXT_SIZE_HDPI = 20;
	public static final int TEXT_SIZE_MDPI = 18;
	public static final int TEXT_SIZE_LDPI = 13;

}
