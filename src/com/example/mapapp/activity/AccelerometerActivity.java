package com.example.mapapp.activity;

import java.util.ArrayList;
import java.util.List;

import org.achartengine.GraphicalView;

import android.app.Activity;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.Handler;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.mapapp.R;
import com.example.mapapp.R.layout;
import com.example.mapapp.charts.Graph;
import com.example.mapapp.utils.Globals;

public class AccelerometerActivity extends Activity implements
		SensorEventListener {

	private SensorManager mSensorManager;
	private Sensor mSensor;
	
	private static ArrayList<Double> xd = new ArrayList<Double>();
	private static ArrayList<Double> yd = new ArrayList<Double>();
	private static ArrayList<Double> zd = new ArrayList<Double>();
	
	private LinearLayout ll;

	private Handler handler = new Handler();
    public boolean init = false;

	Graph mGraph;
    GraphicalView view;
    Graph graph;


	private Runnable showSensorData = new Runnable() {

		@Override
		public void run() {
			
            xd.add(Globals.linear_acceleration[0]);
            yd.add(Globals.linear_acceleration[1]);
            zd.add(Globals.linear_acceleration[2]);

            mGraph = new Graph(AccelerometerActivity.this);
            mGraph.initData(xd,yd,zd);
            mGraph.setProperties();
            if(!init){
                view = mGraph.getGraph();
                ll.addView(view);
                init = true;
            }else{
                ll.removeView(view);
                view = mGraph.getGraph();
                ll.addView(view);
            }
            
			handler.postDelayed(this, 1000);
		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_accelerometer);

		mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
		mSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

		ll = (LinearLayout) findViewById(R.id.GraphicalView);
		mSensorManager.registerListener(this, mSensor,
				SensorManager.SENSOR_DELAY_NORMAL);
		handler.post(showSensorData);
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
