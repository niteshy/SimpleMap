package com.example.mapapp.charts;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.achartengine.ChartFactory;
import org.achartengine.chart.PointStyle;
import org.achartengine.renderer.XYMultipleSeriesRenderer;
import org.achartengine.renderer.XYSeriesRenderer;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint.Align;

import com.example.mapapp.Globals;
import com.example.mapapp.Utils;

/**
 * Temperature sensor demo chart.
 */
public class SensorChart extends AbstractDemoChart {
	private static final long HOUR = 3600 * 1000;

	private static final long DAY = HOUR * 24;

	private static final int HOURS = 24;

	/**
	 * Returns the chart name.
	 * 
	 * @return the chart name
	 */
	public String getName() {
		return "Accelerometer Sensor Chart";
	}

	/**
	 * Returns the chart description.
	 * 
	 * @return the chart description
	 */
	public String getDesc() {
		return "Provide Acceleromet sensors data";
	}

	/**
	 * Executes the chart demo.
	 * 
	 * @param context
	 *            the context
	 * @return the built intent
	 */
	public Intent execute(Context context) {
		String[] titles = new String[] { "X-axis", "Y-axis", "Z-axis" };

		List<Date[]> x = new ArrayList<Date[]>();
		
		List<double[]> values = new ArrayList<double[]>();

		List<Double> xd = new ArrayList<Double>();
		List<Double> yd = new ArrayList<Double>();
		List<Double> zd = new ArrayList<Double>();
		List<Date> dates = new ArrayList<Date>(); 
 		for (int i = 0; i < Globals.sensorData.size(); i++) {
			xd.add(Globals.sensorData.get(i).getX());
			yd.add(Globals.sensorData.get(i).getY());
			zd.add(Globals.sensorData.get(i).getZ());
			dates.add(new Date(Globals.sensorData.get(i).getTimestamp())); 
		}
		values.add(Utils.toDoubleArray(xd));
		values.add(Utils.toDoubleArray(yd));
		values.add(Utils.toDoubleArray(zd));
		x.add(Utils.toDateArray(dates)); 
		
		
		int[] colors = new int[] { Color.GREEN, Color.BLUE, Color.RED };
		PointStyle[] styles = new PointStyle[] { PointStyle.CIRCLE,
				PointStyle.CIRCLE, PointStyle.CIRCLE };
		XYMultipleSeriesRenderer renderer = buildRenderer(colors, styles);
		int length = renderer.getSeriesRendererCount();
		for (int i = 0; i < length; i++) {
			((XYSeriesRenderer) renderer.getSeriesRendererAt(i))
					.setFillPoints(true);
		}
		setChartSettings(renderer, "Accelerometer Data", "Seconds",
				"Meters Per Seconds Squared", x.get(0)[0].getTime(),
				x.get(0)[x.size() - 1].getTime(), -5, 30, Color.LTGRAY,
				Color.LTGRAY);
		renderer.setXLabels(20);
		renderer.setYLabels(20);
		renderer.setShowGrid(true);
		renderer.setXLabelsAlign(Align.CENTER);
		renderer.setYLabelsAlign(Align.RIGHT);
		Intent intent = ChartFactory.getTimeChartIntent(context,
				buildDateDataset(titles, x, values), renderer, "h:mm a");
		return intent;
	}

}
