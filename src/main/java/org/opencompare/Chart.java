package org.opencompare;

import java.awt.Color;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.knowm.xchart.BitmapEncoder;
import org.knowm.xchart.CategoryChart;
import org.knowm.xchart.CategoryChartBuilder;
import org.knowm.xchart.SwingWrapper;
import org.knowm.xchart.BitmapEncoder.BitmapFormat;
import org.knowm.xchart.style.Styler.LegendPosition;

/**
 * Basic Bar Chart
 * <p>
 * Demonstrates the following:
 * <ul>
 * <li>Integer categories as List
 * <li>All positive values
 * <li>Single series
 * <li>Place legend at Inside-NW position
 * <li>Bar Chart Annotations
 */
public class Chart implements PCMChart<CategoryChart> {

	public static void main(String[] args)  {

		PCMManager manager = new PCMManager("pcms2/");
		Map<String, Long> feature = Tools.mostFrequentFeatures(manager.PcmList);
		Map<String, Long> products = Tools.mostFrequentProducts(manager.PcmList);
		// showChart(feature);
		showChart(loadCsvData("outputCSV/products.csv"), "", "");
		showChart(products, "Products", "10 most frequents");
		Map<String, Long> hf = loadCsvData("outputCSV/features.csv");
		showChart(hf, "Features", "10 most frequents");
	}

	public CategoryChart getChart() {
		// Create Chart
		CategoryChart chart = new CategoryChartBuilder().width(800).height(600).title("Score Histogram")
				.xAxisTitle("Score").yAxisTitle("Number").build();

		// Customize Chart
		chart.getStyler().setLegendPosition(LegendPosition.InsideNW);
		chart.getStyler().setHasAnnotations(true);
		chart.getStyler().setPlotGridLinesVisible(false);

		 chart.getStyler().setChartBackgroundColor(Color.RED); // background
		 chart.getStyler().setPlotBackgroundColor(Color.RED); // back of all bars
		 chart.getStyler().setAxisTickLabelsColor(Color.RED); // x and y labels color
		 chart.getStyler().setChartFontColor(Color.RED); // labels around

		PCMManager manager = new PCMManager("pcms2/");
		Map<String, Long> feature = Tools.mostFrequentFeatures(manager.PcmList);
		Map<String, Long> products = Tools.mostFrequentProducts(manager.PcmList);
		// Series
		ArrayList<String> keysList = new ArrayList<>();
		ArrayList<Integer> valuesList = new ArrayList<>();

		int min = 0;
		int max = 0;
		if (feature.size() < 10) {
			max = feature.size();
		} else {
			max = feature.size();
			min = max - 10;
		}
		int i = 0;
		for (Map.Entry<String, Long> entry : feature.entrySet()) {
			if ((min <= i) && (i <= max)) {
				String key = "-";
				if (entry.getKey().length() > 0) {
					key = entry.getKey();
				}
				int value = entry.getValue().intValue();
				keysList.add(key);
				valuesList.add(value);
			}
			System.out.println("pos: " + i + ", key:" + entry.getKey() + ", val:" + entry.getValue());
			i++;
		}
		chart.addSeries("My chart", keysList, valuesList);
		return chart;
	}

	public static void showChart(Map<String, Long> hashMap, String title, String legend)  {
		CategoryChart chart = new CategoryChartBuilder().width(800).height(600).title(title + " occurences Histogram")
				.xAxisTitle(legend).yAxisTitle("Values").build();
		// Customize Chart
		chart.getStyler().setLegendPosition(LegendPosition.InsideNW);
		chart.getStyler().setHasAnnotations(true);
		// chart.getStyler().setPlotGridLinesVisible(false);
		// Series
		ArrayList<String> keysList = new ArrayList<>();
		ArrayList<Integer> allValuesList = new ArrayList<>();
		ArrayList<Integer> valuesList = new ArrayList<>();

		int min = 0;
		int max = 0;
		if (hashMap.size() < 10) {
			max = hashMap.size();
		} else {
			max = hashMap.size();
			min = max - 10;
		}
		int i = 0;
		for (Map.Entry<String, Long> entry : hashMap.entrySet()) {
			if ((min <= i) && (i <= max)) {
				String key = "-";
				if (entry.getKey().length() > 0) {
					key = entry.getKey();
				}
				int value = entry.getValue().intValue();
				keysList.add(key);
				valuesList.add(value);
			}
			allValuesList.add(entry.getValue().intValue());
			i++;
		}
		chart.addSeries(
				"All data ==> Avg: " + (int) getAverage(allValuesList) + ", Sd:" + Math.sqrt(getAverage(allValuesList)),
				keysList, valuesList);
		new SwingWrapper<CategoryChart>(chart).displayChart();
		try {
			BitmapEncoder.saveBitmap(chart, "./charts/"+title, BitmapFormat.JPG);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static double getAverage(ArrayList<Integer> marks) {
		Integer sum = 0;
		if (!marks.isEmpty()) {
			for (Integer mark : marks) {
				sum += mark;
			}
			return sum.doubleValue() / marks.size();
		}
		return sum;
	}

	public static double getVariance(ArrayList<Integer> marks) {
		double mean = getAverage(marks);
		double temp = 0;
		for (Integer a : marks)
			temp += (a - mean) * (a - mean);
		return temp / (marks.size() - 1);
	}

	public static Map<String, Long> loadCsvData(String path) {
		HashMap<String, Long> data = new HashMap<String, Long>();
		FileReader file = null;
		BufferedReader buffer = null;

		try {
			file = new FileReader(new File(path));
			buffer = new BufferedReader(file);
			String line = "";
			while ((line = buffer.readLine()) != null) {
				String key;
				Integer myVal;
				line = line.replaceAll("([^,a-zA-Z0-9])", "");
				line = line.replaceAll("\\s*[\\r\\n]+\\s*", "").trim();
				int lastComma = line.lastIndexOf(',');
				try {
					myVal = Integer.parseInt(line.substring(lastComma + 1));
				} catch (NumberFormatException e) {
					myVal = 1;
					// System.err.println(e.getMessage());
				}
				if (lastComma == 0) {
					key = "-";
				} else {
					key = line.substring(0, lastComma - 1);
				}
				long v = Long.parseLong("" + myVal);
				data.put(key, Long.parseLong("" + v));
			}
		} catch (NumberFormatException | IOException e) {
			e.printStackTrace();
		}
		data = (HashMap<String, Long>) sortByValue(data);
		return (HashMap<String, Long>) data;
	}

	private static <K, V> Map<K, V> sortByValue(Map<K, V> map) {
		List<Entry<K, V>> list = new LinkedList<>(map.entrySet());
		Collections.sort(list, new Comparator<Object>() {
			@SuppressWarnings("unchecked")
			public int compare(Object o1, Object o2) {
				return ((Comparable<V>) ((Map.Entry<K, V>) (o1)).getValue())
						.compareTo(((Map.Entry<K, V>) (o2)).getValue());
			}
		});

		Map<K, V> result = new LinkedHashMap<>();
		for (Iterator<Entry<K, V>> it = list.iterator(); it.hasNext();) {
			Map.Entry<K, V> entry = (Map.Entry<K, V>) it.next();
			result.put(entry.getKey(), entry.getValue());
		}

		return result;
	}

}
