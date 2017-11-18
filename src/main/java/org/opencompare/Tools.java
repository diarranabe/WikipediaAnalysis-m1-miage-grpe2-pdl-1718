package org.opencompare;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.StringWriter;
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

import org.supercsv.io.CsvListWriter;
import org.supercsv.io.ICsvListWriter;
import org.supercsv.prefs.CsvPreference;

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

	
	/**
	 * Prends un pcm en paramètre et retourne un Map de ses cellules 
	 * @param pcm
	 * @return
	 */
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

	/**
	 * Prends un pcm en paramètre et retourne la liste de ses feature
	 * @param p pcm
	 * @return List<String>
	 */
	public static List<String> getFeatures(PCM p) {
		List<String> result = new ArrayList<>();
		List<AbstractFeature> listF = p.getFeatures();
		for (AbstractFeature f : listF) {
			result.add(f.getName());
		}
		return result;
	}
	
	/**
	 * Prends une liste de PCM et retourne la liste en vrac de tous les features
	 * @param pcmList
	 * @return  Map<String, Long>
	 */
	public static List<String> allFeatures(List<PCM> pcmList){
		
		List<String> listFeatures = new ArrayList<>(); 
		for (PCM pcm : pcmList) {
			List<AbstractFeature> features = pcm.getFeatures();
			for (AbstractFeature f : features) {
				listFeatures.add(f.getName());
			}
		}
		return listFeatures;
	}
	
	
	/**
	 * Prends une liste de PCM et retourne la liste en vrac de tous les products
	 * @param pcmList
	 * @return  Map<String, Long>
	 */
	public static List<String> allProducts(List<PCM> pcmList){
		
		List<String> listProducts = new ArrayList<>(); 
		for (PCM pcm : pcmList) {
			List<Product> products = pcm.getProducts();
			for (Product p : products) {
				listProducts.add(p.getKeyContent());
			}
		}
		return listProducts;
	}
	
	
	/**
	 * Prends une liste de PCM et retourne les features et leurs occurences
	 * @param pcmList
	 * @return  Map<String, Long>
	 */
	
	public static Map<String, Long> mostFrequentFeatures(List<PCM> pcmList) {
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
<<<<<<< HEAD
=======

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
>>>>>>> b58deed4f950dbe1d696e8c7a32c2d37966f5930

	/**
	 * Prends une liste de PCM et retourne les products et leurs occurences
	 * @param pcmList
	 * @return
	 */
	public static  Map<String, Long> mostFrequentProducts(List<PCM> pcmList) {
		
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
	
	
<<<<<<< HEAD
	/**
	 * Trie un Map dans l'aodre croissant des valeurs
	 * @param map
	 * @return
	 */
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


=======
	

	public static void convertMapToCsv(Map<String, Long> map,String fileOutputName) throws Exception {

		StringWriter output = new StringWriter();
		try (ICsvListWriter listWriter = new CsvListWriter(output, 
				CsvPreference.STANDARD_PREFERENCE)){
			for (Map.Entry<String, Long> entry : map.entrySet()){
				listWriter.write(entry.getKey(), entry.getValue());
			}
		}

		System.out.println("[DEBUG] strin to csv : \n"+output.toString());
		PrintWriter out = new PrintWriter("outputCSV/"+fileOutputName);
		out.write("sep=,");
		out.write("\n");
		out.write(output.toString());
		out.close();

	}
	
	public static void convertStringToFile(String content,String fileName) throws FileNotFoundException{
		PrintWriter out = new PrintWriter("outputCSV/"+fileName);
		out.write(content);
		out.close();
	}
>>>>>>> b58deed4f950dbe1d696e8c7a32c2d37966f5930


}
