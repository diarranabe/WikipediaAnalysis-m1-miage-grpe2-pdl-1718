package org.opencompare;

import org.knowm.xchart.BitmapEncoder;
import org.knowm.xchart.BitmapEncoder.BitmapFormat;
import org.knowm.xchart.CategoryChart;
import org.knowm.xchart.CategoryChartBuilder;
import org.knowm.xchart.SwingWrapper;
import org.knowm.xchart.style.Styler.LegendPosition;
import org.opencompare.api.java.PCM;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.Map.Entry;

/**
 * Chart
 * Sauvegarder les diagramme au format JPG
 */
public class Chart implements PCMChart<CategoryChart> {

	public static void main(String[] args)  {

		List<PCM> mlist = Tools.conformsPCM(Tools.loadAllPcmFromDirectory("pcms2/"));
		Map<String, Long> feature = Tools.featuresFrequencies(mlist);
		Map<String, Long> products = Tools.productsFrequencies(mlist);
		Map<String, Long> products2 = loadCsvData("outputCSV/conformFeaturesDatasetConform.csv");
		getChart(products2, "Products", "10 most frequents");
	}

	/**
	 * Afficher et sauvegarder un diagramme
	 *
	 * @param hashMap un map
	 * @param title   le titre
	 * @param legend  la legende
	 */
	public static void getChart(Map<String, Long> hashMap, String title, String legend) {
		Date dNow = new Date();
		SimpleDateFormat ft = new SimpleDateFormat("yyMMddhhmmssMs");
		String datetime = ft.format(dNow);

		String path = "./charts/";
		int width = 1200;
		int height = 600;
		String avgLabel = "Avg: ";
		String sdLabel = "Sd:";
		String label = "All data : ";
		int toShow = 20;

		CategoryChart chart = new CategoryChartBuilder().width(width).height(height).title(title)
				.xAxisTitle(legend).yAxisTitle("Values").build();

		chart.getStyler().setLegendPosition(LegendPosition.InsideNW);
		chart.getStyler().setHasAnnotations(true);

		// Series
		ArrayList<String> keysList = new ArrayList<>();
		ArrayList<Integer> allValuesList = new ArrayList<>();
		ArrayList<Integer> valuesList = new ArrayList<>();

		if (hashMap.size() < toShow) {
			toShow = hashMap.size();
		}
		int min = 0;
		int max = 0;
		if (hashMap.size() < toShow) {
			max = hashMap.size();
		} else {
			max = hashMap.size();
			min = max - toShow;
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
				label + hashMap.size() + " | " + avgLabel + (int) getAverage(allValuesList) + " | " + sdLabel + Math.sqrt(getAverage(allValuesList)),
				keysList, valuesList);
		new SwingWrapper<CategoryChart>(chart).displayChart();
		try {
			BitmapEncoder.saveBitmap(chart, path + title + datetime, BitmapFormat.JPG);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Afficher et sauvegarder un diagramme
	 *
	 * @param hashMap un map
	 * @param toShow  nombre de données à afficher
	 * @param title   le titre
	 * @param legend  la legende
	 * @param width   la largeur de l'image en pixel
	 * @param height  la hauteur de l'image en pixel
	 * @param path    le chemin de sauvegarde
	 */
	public static void getChart(Map<String, Long> hashMap, int toShow, String title, String legend, int width, int height, String path) {
		Date dNow = new Date();
		SimpleDateFormat ft = new SimpleDateFormat("yyMMddhhmmssMs");
		String datetime = ft.format(dNow);

		String avgLabel = "Avg: ";
		String sdLabel = "Sd:";
		String label = "All data : ";

		CategoryChart chart = new CategoryChartBuilder().width(width).height(height).title(title)
				.xAxisTitle(legend).yAxisTitle("Values").build();

		chart.getStyler().setLegendPosition(LegendPosition.InsideNW);
		chart.getStyler().setHasAnnotations(true);

		// Series
		ArrayList<String> keysList = new ArrayList<>();
		ArrayList<Integer> allValuesList = new ArrayList<>();
		ArrayList<Integer> valuesList = new ArrayList<>();

		if (hashMap.size()<toShow) {
			toShow = hashMap.size();
		}
		int min = 0;
		int max = 0;
		if (hashMap.size() < toShow) {
			max = hashMap.size();
		} else {
			max = hashMap.size();
			min = max - toShow;
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
				label + hashMap.size() + " | " + avgLabel + (int) getAverage(allValuesList) +" | "+ sdLabel + Math.sqrt(getAverage(allValuesList)),
				keysList, valuesList);
		new SwingWrapper<CategoryChart>(chart).displayChart();
		try {
			BitmapEncoder.saveBitmap(chart, path+title+datetime, BitmapFormat.JPG);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Afficher et sauvegarder un diagramme
	 *
	 * @param hashMap un map
	 * @param toShow  nombre de données à afficher
	 * @param title   le titre
	 * @param legend  la legende
	 * @param width   la largeur de l'image en pixel
	 * @param height  la hauteur de l'image en pixel
	 * @param path    le chemin de sauvegarde
	 * @param show    afficher l'image après la sauvegarde ou non
	 */
	public static void getChart(Map<String, Long> hashMap, int toShow, String title, String legend, int width, int height, String path, boolean show) {
		Date dNow = new Date();
		SimpleDateFormat ft = new SimpleDateFormat("yyMMddhhmmssMs");
		String datetime = ft.format(dNow);

		String avgLabel = "Avg: ";
		String sdLabel = "Sd:";
		String label = "All data : ";

		CategoryChart chart = new CategoryChartBuilder().width(width).height(height).title(title)
				.xAxisTitle(legend).yAxisTitle("Values").build();

		chart.getStyler().setLegendPosition(LegendPosition.InsideNW);
		chart.getStyler().setHasAnnotations(true);

		// Series
		ArrayList<String> keysList = new ArrayList<>();
		ArrayList<Integer> allValuesList = new ArrayList<>();
		ArrayList<Integer> valuesList = new ArrayList<>();

		if (hashMap.size() < toShow) {
			toShow = hashMap.size();
		}
		int min = 0;
		int max = 0;
		if (hashMap.size() < toShow) {
			max = hashMap.size();
		} else {
			max = hashMap.size();
			min = max - toShow;
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
				label + hashMap.size() + " | " + avgLabel + (int) getAverage(allValuesList) + " | " + sdLabel + Math.sqrt(getAverage(allValuesList)),
				keysList, valuesList);
		if (show) {
			new SwingWrapper<CategoryChart>(chart).displayChart();
		}
		try {
			BitmapEncoder.saveBitmap(chart, path + title + datetime, BitmapFormat.JPG);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static Map<String, Long> loadCsvData(String path) {
		HashMap<String, Long> data = new HashMap<String, Long>();
		HashMap<String, Integer> keyMap = new HashMap<String, Integer>();
		FileReader file = null;
		BufferedReader buffer = null;

		try {
			file = new FileReader(new File(path));
			buffer = new BufferedReader(file);
			String line = "";
			int i = 0;
			while ((line = buffer.readLine()) != null) {
				if (i != 0) {
					String key;
					Integer myVal;
					line = line.replaceAll("([^,;a-zA-Z0-9])", "");
					line = line.replaceAll("\\s*[\\r\\n]+\\s*", "").trim();
					System.err.println(i + ": " + line);
					int lastComma = line.lastIndexOf(',');
					System.err.println(": last c " + lastComma);
					if (lastComma == -1) {
						lastComma = line.lastIndexOf(';');
						System.err.println(": last c " + lastComma);
					}
					try {
						myVal = Integer.parseInt(line.substring(lastComma + 1));
					} catch (NumberFormatException e) {
						myVal = 1;
						System.err.println(e.getMessage());
					}
					if (lastComma == 0) {
						key = "-";
					} else {
						key = line.substring(0, lastComma - 1);
					}
					long v = Long.parseLong("" + myVal);
					if (key.length() > 10) {
						key = key.substring(0, 5) + "...";
					}
					if (data.containsKey(key)) {
						if (keyMap.containsKey(key)) {
							keyMap.put(key, keyMap.get(key) + 1);
						} else {
							keyMap.put(key, 2);
						}
						key += keyMap.get(key);
					}
					System.out.println("set : " + key + ", " + v);
					data.put(key, Long.parseLong("" + v));
				}
				i++;
			}
		} catch (NumberFormatException | IOException e) {
			e.printStackTrace();
		}
		data = (HashMap<String, Long>) sortByValue(data);
		return (HashMap<String, Long>) data;
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

	public CategoryChart getChart() {
		return null;
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
