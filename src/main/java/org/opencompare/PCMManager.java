/**
 * 
 */
package org.opencompare;

import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.opencompare.api.java.PCM;
import org.opencompare.api.java.PCMContainer;
import org.opencompare.api.java.impl.io.KMFJSONLoader;
import org.opencompare.api.java.io.PCMLoader;

/**
 * @author diarranabe
 *
 */
public class PCMManager {

	List<PCM> PcmList = new ArrayList<>();
	List<File> pcmFiles = new ArrayList<File>();
	
	public PCMManager(String path) {
		findPcmFilesRecursively(path);
		syncFiles();
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		PCMManager manager = new PCMManager("pcms2/");
		
		manager.printSizes();
		manager.printPcms();
	}
	
	/**
	 * Affiches les differentes tailles des pcm
	 */
	public void printPcms() {
		int i=0;
		for(PCM p : PcmList) {
			System.out.println(i+" : "+p);
			i++;
		}
	}
	
	
	
	/**
	 * Affiches les differentes tailles des pcm
	 */
	public void printSizes() {
		int i=0;
		for(PCM p : PcmList) {
			System.out.println(i+" : "+Tools.getMatrixSize(p));
			i++;
		}
	}
	
	
	/**
	 * Permet de génerer le pcm dans la liste de pcm(PcmList) à partir de la liste de fichiers (PcmFiles)
	 */
	public void syncFiles() {
		for (File file : pcmFiles) {
//			System.out.println(file.getName());
			PCM p = loadPcm(file);
			addPcm(p);
		}
	}
	
	/**
	 * Charger un pcm à partir d'un fichier .pcm
	 * @param path
	 * @return
	 */
	public static PCM loadPcm(String path) {
		File pcmFile = new File(path);

		PCMLoader loader = new KMFJSONLoader();
		List<PCMContainer> pcmContainers = null;
		try {
			pcmContainers = loader.load(pcmFile);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		PCM pcm = pcmContainers.get(0).getPcm();
		return pcm;
	}
	
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

	/**
	 * Ajoute un pcm à la liste de PCM (PcmList)
	 * @param pcm
	 */
	public void addPcm(PCM pcm) {
		this.PcmList.add(pcm);
	}
	
	/**
	 * Methode qui permet de passer un repertoire et d'ajouter ses fichiers .pcm à la liste de fichiers PcmFiles
	 * @param path
	 */
	private void findPcmFilesRecursively(String path) {
		File file = new File(path);
	    //Liste des fichiers correspondant a l'extension souhaitee
	    final File[] children = file.listFiles(new FileFilter() {
	    	public boolean accept(File f) {
	                return f.getName().endsWith("pcm") ;
	            }}
	    );
	    if (children != null) {
	        //Pour chaque fichier recupere, on appelle a nouveau la methode
	        for (File child : children) {
	            pcmFiles.add(child);
	        }
	    }
	}

}