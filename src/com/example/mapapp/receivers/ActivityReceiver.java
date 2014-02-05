package com.example.mapapp.receivers;

import com.example.mapapp.activity.MapActivity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class ActivityReceiver extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent) {
		if (MapActivity.textView1 != null)
			MapActivity.textView1.setText(MapActivity.currentActivity);
	}

}