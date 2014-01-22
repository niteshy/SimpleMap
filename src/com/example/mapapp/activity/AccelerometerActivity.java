package com.example.mapapp.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.Handler;
import android.widget.TextView;

import com.example.mapapp.Globals;
import com.example.mapapp.R;
import com.example.mapapp.SensorData;
import com.example.mapapp.charts.SensorChart;

public class AccelerometerActivity extends Activity implements
		SensorEventListener {

	private SensorManager sensorManager;
	private Sensor mSensor;

	private long timeStart = 0;
	private long counter = 0;

	SensorEvent lastEvent;

	private TextView xtv, ytv, ztv;
	private Handler handler = new Handler();

	private Runnable showSensorData = new Runnable() {

		@Override
		public void run() {
			SensorData sd = new SensorData(counter,
					Globals.linear_acceleration[0],
					Globals.linear_acceleration[1],
					Globals.linear_acceleration[2]);
			Globals.sensorData.add(sd);
			Intent intent = new SensorChart().execute(AccelerometerActivity.this);
			startActivity(intent);
			counter++;
			handler.postDelayed(this, 1000);
		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_accelerometer);

		sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
		mSensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

		xtv = (TextView) findViewById(R.id.xaxis);
		ytv = (TextView) findViewById(R.id.yaxis);
		ztv = (TextView) findViewById(R.id.zaxis);

		sensorManager.registerListener(this, mSensor,
				SensorManager.SENSOR_DELAY_NORMAL);
		handler.post(showSensorData);
	}

	@Override
	public void onResume() {
		super.onResume();
		// Register ourselves as an event listener for accelerometer events
		sensorManager.registerListener(this, mSensor,
				SensorManager.SENSOR_DELAY_NORMAL);
		counter = 0;
	}

	@Override
	public void onPause() {
		super.onPause();
		// Unregister the event listener for accelerometer events
		sensorManager.unregisterListener(this);
	}

	@Override
	public void onAccuracyChanged(Sensor sensor, int accuracy) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onSensorChanged(SensorEvent event) {

		Globals.linear_acceleration[0] = event.values[0];
		Globals.linear_acceleration[1] = event.values[1];
		Globals.linear_acceleration[2] = event.values[2];

	}

}
