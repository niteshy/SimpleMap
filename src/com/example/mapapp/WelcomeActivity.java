package com.example.mapapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class WelcomeActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_welcome);
	}

	public void mapClick(View v) {
		startActivity(new Intent(getApplicationContext(), MapActivity.class));

	}

	public void accelerometerClick(View v) {
		startActivity(new Intent(getApplicationContext(), AccelerometerActivity.class));

	}
}
