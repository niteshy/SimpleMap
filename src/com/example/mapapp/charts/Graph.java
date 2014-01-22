package com.example.mapapp.charts;

import java.util.ArrayList;

import org.achartengine.ChartFactory;
import org.achartengine.GraphicalView;
import org.achartengine.model.XYMultipleSeriesDataset;
import org.achartengine.model.XYSeries;
import org.achartengine.renderer.XYMultipleSeriesRenderer;
import org.achartengine.renderer.XYSeriesRenderer;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Paint.Align;
import android.util.DisplayMetrics;

import com.example.mapapp.Constants;

public class Graph {
	private Context context;
	XYMultipleSeriesDataset dataset;
	XYMultipleSeriesRenderer renderer;
	
	
	int greater;
	public static boolean ClickEnabled = true;

	public Graph(Context context) {
		this.context = context;
	}

	public void initData(ArrayList<Double> x, ArrayList<Double> y,
			ArrayList<Double> z) {
		XYSeries seriesX = new XYSeries("X");
		for (int i = 0; i < x.size(); i++) {
			seriesX.add(i, x.get(i));
		}
		XYSeries seriesY = new XYSeries("Y");
		for (int i = 0; i < y.size(); i++) {
			seriesY.add(i, y.get(i));
		}
		XYSeries seriesZ = new XYSeries("Z");
		for (int i = 0; i < z.size(); i++) {
			seriesZ.add(i, z.get(i));
		}
		greater = x.size();
		if (y.size() > greater) {
			greater = y.size();
		} else if (z.size() > greater) {
			greater = z.size();
		}
		dataset = new XYMultipleSeriesDataset();
		dataset.addSeries(seriesX);
		dataset.addSeries(seriesY);
		dataset.addSeries(seriesZ);
		renderer = new XYMultipleSeriesRenderer();
	}

	public void setProperties() {

		// renderer.setClickEnabled(ClickEnabled);
		renderer.setBackgroundColor(Color.WHITE);
		renderer.setMarginsColor(Color.WHITE);

		renderer.setApplyBackgroundColor(true);
		if (greater < 100) {
			renderer.setXAxisMax(100);
		} else {
			renderer.setXAxisMin(greater - 100);
			renderer.setXAxisMax(greater);
		}
		renderer.setAxesColor(Color.BLACK);
		renderer.setLabelsTextSize(25);
		renderer.setLegendTextSize(25);
		renderer.setLabelsColor(Color.BLACK);
		renderer.setAxesColor(Color.BLACK);
		renderer.setGridColor(Color.BLACK);
		renderer.setXLabelsAlign(Align.CENTER);
		renderer.setYLabelsAlign(Align.RIGHT);
		renderer.setShowGrid(true);
		renderer.setShowLegend(true);
		
		
		switch (context.getResources().getDisplayMetrics().densityDpi) {
        case DisplayMetrics.DENSITY_XHIGH:
            renderer.setMargins(new int[] { 50, 90, 40, 10 });
			renderer.setAxisTitleTextSize(Constants.TEXT_SIZE_XHDPI);
			renderer.setChartTitleTextSize(Constants.TEXT_SIZE_XHDPI);
			renderer.setLabelsTextSize(Constants.TEXT_SIZE_XHDPI);
			renderer.setLegendTextSize(Constants.TEXT_SIZE_XHDPI);
			renderer.setLabelsTextSize(35);
			renderer.setYLabelsPadding(10);
			break;
		case DisplayMetrics.DENSITY_HIGH:
			renderer.setMargins(new int[] { 40, 50, 35, 10 });
			renderer.setAxisTitleTextSize(Constants.TEXT_SIZE_HDPI);
			renderer.setChartTitleTextSize(Constants.TEXT_SIZE_HDPI);
			renderer.setLabelsTextSize(Constants.TEXT_SIZE_HDPI);
			renderer.setLegendTextSize(Constants.TEXT_SIZE_HDPI);
			renderer.setLabelsTextSize(25);
			renderer.setYLabelsPadding(5);
			break;
		default:
			renderer.setMargins(new int[] { 40, 50, 35, 10 });
			renderer.setAxisTitleTextSize(Constants.TEXT_SIZE_LDPI);
			renderer.setChartTitleTextSize(Constants.TEXT_SIZE_LDPI);
			renderer.setLabelsTextSize(Constants.TEXT_SIZE_LDPI);
			renderer.setLegendTextSize(Constants.TEXT_SIZE_LDPI);
			renderer.setLabelsTextSize(25);
			renderer.setYLabelsPadding(5);
			break;
		}
		

		XYSeriesRenderer renderer1 = new XYSeriesRenderer();
		renderer1.setColor(Color.RED);
		renderer.addSeriesRenderer(renderer1);
		XYSeriesRenderer renderer2 = new XYSeriesRenderer();
		renderer2.setColor(Color.GREEN);
		renderer.addSeriesRenderer(renderer2);
		XYSeriesRenderer renderer3 = new XYSeriesRenderer();
		renderer3.setColor(Color.BLUE);

		renderer1.setLineWidth(4);
		renderer2.setLineWidth(4);
		renderer3.setLineWidth(4);
		
		renderer.addSeriesRenderer(renderer3);
	}

	public GraphicalView getGraph() {
		return ChartFactory.getLineChartView(context, dataset, renderer);
	}
}