package org.opencompare;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.opencompare.api.java.PCM;
import org.opencompare.api.java.PCMContainer;
import org.opencompare.api.java.impl.io.KMFJSONLoader;
import org.opencompare.api.java.io.PCMLoader;

/**
 * @author diarranabe
 *
 */
public class PCMManager {

	/**
	 * Lists des fichiers .pcm sur le disque
	 */
	List<File> pcmFiles = new ArrayList<File>();

	/**
	 * Listes des objets java PCM maninipul�s
	 */
	List<PCM> PcmList = new ArrayList<>();

	public PCMManager(String path) {
		findPcmFilesRecursively(path);
		syncFiles();
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		System.out.println("start");
		// TODO Auto-generated method stub
		PCMManager manager = new PCMManager("pcms2/");
		Map<String, Long> features = Tools.mostFrequentFeatures(manager.PcmList);
		Map<String, Long> products = Tools.mostFrequentProducts(manager.PcmList);
		Iterator it = features.entrySet().iterator();
		int i = 0;
		Chart.showChart(products, "Products", "");
		// Chart.showChart(Chart.loadCsvData("outputCSV/products.csv"),"Products 2","");
		// // csv file is created at the end of this function
		Chart.showChart(features, "Features", "");
		// Chart.showChart(Chart.loadCsvData("outputCSV/features.csv"),"Features 2","");
		try {
			Tools.convertMapToCsv(products, "products.csv");
			Tools.convertMapToCsv(features, "features.csv");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Tools.printSizes(manager.PcmList);

		System.out.println("\n\n\nHOMOGENEITE");
		List<String> homogeneites = new ArrayList<>();
		homogeneites.add("Matrice;Feature;TypePredominante;taux");
		for (PCM pcm : manager.PcmList) {
			homogeneites.addAll(Tools.homogeneitePCM(pcm));
		}
		// ecriture en csv
		try {
			Files.write(Paths.get("outputCSV/homogeneite.csv"), homogeneites, Charset.defaultCharset());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		System.out.println("end");
	}

	/**
	 * Affiches les differentes tailles des pcm
	 */
	public void printPcms() {
		int i = 0;
		for (PCM p : PcmList) {
			System.out.println(i + " : " + p);
			i++;
		}
	}

	/**
	 * Affiches les differentes tailles des pcm
	 */
	public void printSizes() {
		int i = 0;
		for (PCM p : PcmList) {
			System.out.println(i + " : " + Tools.getMatrixSize(p));
			i++;
		}
	}

	/**
	 * Permet de g�nerer le pcm dans la liste de pcm(PcmList) � partir de la liste
	 * de fichiers (PcmFiles)
	 */
	public void syncFiles() {
		for (File file : pcmFiles) {
			// System.out.println(file.getName());
			PCM p = loadPcm(file);
			addPcm(p);
		}
	}

	/**
	 * Charger un pcm � partir d'un fichier .pcm
	 * 
	 * @param path
	 * @return
	 */

	public static PCM loadPcm(File file) {
		PCMLoader loader = new KMFJSONLoader();
		List<PCMContainer> pcmContainers = null;
		try {
			pcmContainers = loader.load(file);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		PCM pcm = pcmContainers.get(0).getPcm();
		return pcm;
	}

	public static PCM loadPcm(String path) {
		File pcmFile = new File(path);
		return loadPcm(pcmFile);
	}

	/**
	 * Ajoute un pcm � la liste de PCM (PcmList)
	 * 
	 * @param pcm
	 */
	public void addPcm(PCM pcm) {
		this.PcmList.add(pcm);
	}

	/**
	 * Methode qui permet de passer un repertoire et d'ajouter ses fichiers .pcm �
	 * la liste de fichiers PcmFiles
	 * 
	 * @param path
	 */
	private void findPcmFilesRecursively(String path) {
		File file = new File(path);
		// Liste des fichiers correspondant a l'extension souhaitee
		final File[] children = file.listFiles(new FileFilter() {
			public boolean accept(File f) {
				return f.getName().endsWith("pcm");
			}
		});
		if (children != null) {
			// Pour chaque fichier recupere, on appelle a nouveau la methode
			for (File child : children) {
				pcmFiles.add(child);
			}
		}
	}

	public void printFeatures() {
		int i = 0;
		for (PCM p : PcmList) {
			System.out.println(i + ":" + p.getName() + ": " + Tools.getFeatures(p));
			i++;
		}
	}

	// Afficher les nom des PCM par ordre alphabetique
	public void setAlphabet() {
		Collections.sort(PcmList, new Comparator<PCM>() {
			public int compare(PCM pcm1, PCM pcm2) {
				return pcm1.getName().compareTo(pcm2.getName());
			}
		});
		printPcms();
	}
}
