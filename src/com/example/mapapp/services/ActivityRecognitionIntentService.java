/*
 * Copyright (C) 2013 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.mapapp.services;

import android.app.IntentService;
import android.content.Intent;
import android.util.Log;

import com.example.mapapp.activity.MapActivity;
import com.google.android.gms.location.ActivityRecognitionResult;
import com.google.android.gms.location.DetectedActivity;

/**
 * Service that receives ActivityRecognition updates. It receives updates in the
 * background, even if the main Activity is not visible.
 */
public class ActivityRecognitionIntentService extends IntentService {

	public ActivityRecognitionIntentService() {
		super("ActivityRecognitionIntentService");
	}

	/**
	 * Called when a new activity detection update is available.
	 */
	@Override
	protected void onHandleIntent(Intent intent) {

		// If the intent contains an update
		if (ActivityRecognitionResult.hasResult(intent)) {

			// Get the update
			ActivityRecognitionResult result = ActivityRecognitionResult
					.extractResult(intent);

			// Log the update
			logActivityRecognitionResult(result);

		}
	}


	/**
	 * Write the activity recognition update to the log file
	 * 
	 * @param result
	 *            The result extracted from the incoming Intent
	 */
	private void logActivityRecognitionResult(ActivityRecognitionResult result) {
		// Get all the probably activities from the updated result
		for (DetectedActivity detectedActivity : result.getProbableActivities()) {

			// Get the activity type, confidence level, and human-readable name
			int activityType = detectedActivity.getType();
			int confidence = detectedActivity.getConfidence();
			String activityName = getNameFromType(activityType);

			// Get the current log file or create a new one, then log the
			// activity
			String str = "Type : " + activityType + " Name : "
					+ activityName + " Confidence : " + confidence + "\n";
			Log.d("UPDATE", str);
			MapActivity.currentActivity = str;
		}
	}
	
	private String getNameFromType(int activityType) {
        switch(activityType) {
            case DetectedActivity.IN_VEHICLE:
                return "in_vehicle";
            case DetectedActivity.ON_BICYCLE:
                return "on_bicycle";
            case DetectedActivity.ON_FOOT:
                return "on_foot";
            case DetectedActivity.STILL:
                return "still";
            case DetectedActivity.UNKNOWN:
                return "unknown";
            case DetectedActivity.TILTING:
                return "tilting";
        }
        return "unknown";
    }

}
