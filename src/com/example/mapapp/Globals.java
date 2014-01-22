package com.example.mapapp;

import java.util.ArrayList;

import android.location.Location;

public class Globals {

	public static Location myLocation; 
	
	public static double[] linear_acceleration = new double[3];
	
	public static ArrayList<SensorData> sensorData = new ArrayList<SensorData>(); 
}
