/**
 * 
 */
package org.opencompare;

import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
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
	 * Listes des objets java PCM maninipulés
	 */
	List<PCM> PcmList = new ArrayList<>();

	HashMap<String, Long> featuresFrequencies = new HashMap<String, Long>();

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
		Map<String, Long> mostFrenquents = Tools.mostFrequentFeatures(manager.PcmList);
		Map<String, Long> mostFrenquentsP = Tools.mostFrequentProducts(manager.PcmList);
		Iterator it = mostFrenquents.entrySet().iterator();
		int i = 0;
		
		manager.setAlphabet();
		//manager.printPcms();
//		while (it.hasNext()) {
//			Map.Entry pair = (Map.Entry) it.next();
//			// addFeatureFrenquency(pair.getKey().toString(), (Long) pair.getValue());
//			System.out.println(i++ + "// " + pair.getKey().toString() + " " + pair.getValue() + "\\");
//		}

		// Map<String, Long> f = Tools.celluleFrequences(manager.PcmList.get(0));
		// manager.setFeaturesFrequencies();
		// Iterator it = manager.featuresFrequencies.entrySet().iterator();
		//
		// while (it.hasNext()) {
		// Map.Entry pair = (Map.Entry)it.next();
		// System.out.println("key: "+pair.getKey() + " = num: " + pair.getValue());
		// }

		// Tools.printMatrix(manager.PcmList.get(0));

		// manager.printSizes();
		// manager.printPcms();
		System.out.println("end");
	}

	// public void setFeaturesFrequencies() {
	// System.out.println("frequence start");
	// Map<String, Long> frequences = new HashMap<String, Long>();
	// for (PCM p : PcmList) {
	// System.out.println("frequence p");
	// HashMap<String, Long> freq = (HashMap<String, Long>)
	// Tools.celluleFrequences(p);
	// Iterator it = frequences.entrySet().iterator();
	// while (it.hasNext()) {
	// Map.Entry pair = (Map.Entry) it.next();
	// addFeatureFrenquency(pair.getKey().toString(), (Long) pair.getValue());
	// }
	// }
	// System.out.println("frequence end");
	// }

	// public void addFeatureFrenquency(String label, long number) {
	// if(featuresFrequencies.containsKey(label)) {
	// featuresFrequencies.put(label, featuresFrequencies.get(label) + 1);
	// }else {
	// featuresFrequencies.put(label, number);
	// }
	// }

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
	 * Permet de génerer le pcm dans la liste de pcm(PcmList) à partir de la liste
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
	 * Charger un pcm à partir d'un fichier .pcm
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
	 * Ajoute un pcm à la liste de PCM (PcmList)
	 * 
	 * @param pcm
	 */
	public void addPcm(PCM pcm) {
		this.PcmList.add(pcm);
	}

	/**
	 * Methode qui permet de passer un repertoire et d'ajouter ses fichiers .pcm à
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
