package org.opencompare;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.opencompare.api.java.AbstractFeature;
import org.opencompare.api.java.Cell;
import org.opencompare.api.java.Feature;
import org.opencompare.api.java.PCM;
import org.opencompare.api.java.PCMContainer;
import org.opencompare.api.java.Product;

public class Tools {


	/**
	 * Get PCM matrix size
	 * @param pcm
	 * @return
	 */
	public static Integer getMatrixSize(PCM pcm){
		return pcm.getProducts().size() * pcm.getFeatures().size();
	}

	public static Map<String,Integer> mapDesCellules(PCM pcm) {
		
		Map<String, Integer> map = new HashMap<>();

		List<Product> listProduit = pcm.getProducts();

		for (Product p : listProduit) {

			List<Cell> listCell= p.getCells();
			for (Cell c : listCell) {
				
				map.put(c.getContent(),1);
			}
			
		}
		return map;
	}
	

	public static void printMatrix(PCM pcm) {
		
		Map<String, Integer> map = new HashMap<>();

		System.out.println(pcm.getFeatures());
		List<Product> listProduit = pcm.getProducts();

		for (Product p : listProduit) {

			List<Cell> cells = p.getCells();
			for (Cell cell : cells) {
				System.out.print(cell.getContent().toString() + ",");
			}
			System.out.println();
		}
	}

	
	public static Map<String, Long> celluleFrequences(PCM pcm) {
		
		List<String> listeRet = new ArrayList();

		List<Product> listProduit = pcm.getProducts();

		for (Product p : listProduit) {

			List<Cell> cells = p.getCells();
			for (Cell cell : cells) {
				System.out.println(cell.getContent());
					listeRet.add(cell.getContent());
			}
		}
		
		Map<String, Long> counts =
				listeRet.stream().collect(Collectors.groupingBy(e -> e, Collectors.counting()));
		
		return counts;
	}

	public static List<String> getFeatures(PCM p) {
		
		List<String> result = new ArrayList<>();
		List<AbstractFeature> listF = p.getFeatures();
		for (AbstractFeature f : listF) {
			result.add(f.getName());
		}
		return result;
	}
	
	public static Map<String, Long> mostFrequentFeature(List<PCM> pcmList){
		
		List<String> listFeatures = new ArrayList<>(); 
		for (PCM pcm : pcmList) {
			List<AbstractFeature> features = pcm.getFeatures();
			for (AbstractFeature f : features) {
				listFeatures.add(f.getName());
			}
		}
		
		Map<String, Long> occurrences = 
				listFeatures.stream().collect(Collectors.groupingBy(w -> w, Collectors.counting()));

		return sortByValue(occurrences);
	}

	private static <K, V extends Comparable<? super V>> Map<K, V> sortByValue(Map<K, V> map) {
	    return map.entrySet()
	              .stream()
	              .sorted(Map.Entry.comparingByValue(/*Collections.reverseOrder()*/))
	              .collect(Collectors.toMap(
	                Map.Entry::getKey, 
	                Map.Entry::getValue, 
	                (e1, e2) -> e1, 
	                LinkedHashMap::new
	              ));
	}

	public static  Map<String, Long> mostFrequentProduit(List<PCM> pcmList) {
		
		List<String> listProduit = new ArrayList<>(); 
		for (PCM pcm : pcmList) {
			List<Product> products = pcm.getProducts();
			
			for (Product p : products) {
				listProduit.add(p.getCells().get(0).getContent());
			}
			
		}
		
		Map<String, Long> occurrences = 
				listProduit.stream().collect(Collectors.groupingBy(w -> w, Collectors.counting()));

		return sortByValue(occurrences);
	}


}
