package org.opencompare;

import org.opencompare.api.java.PCM;

import java.util.List;

import static org.opencompare.Chart.getChart;
import static org.opencompare.Chart.loadCsvData;

/**
 * @author diarranabe
 */
public class PCMManager {

    /**
     * @param args args
     */
    public static void main(String[] args) {
        System.out.println("start");

        List<PCM> mlist = Tools.loadAllPcmFromDirectory("pcms/");

        /*CsvTools.convertMapToCsv(Tools.featuresFrequencies(mlist), "outPutCSV/featuresFrenquenciesDataset.csv", "Feature", "Occurrences");
        CsvTools.convertMapToCsv(Tools.featuresFrequencies(Tools.conformsPCM(mlist)), "outPutCSV/featuresFrenquenciesDatasetConform.csv", "Feature", "Occurrences");

        CsvTools.convertMapToCsv(Tools.productsFrequencies(mlist), "outPutCSV/productsFrenquenciesDataset.csv", "Product", "Occurrences");
        CsvTools.convertMapToCsv(Tools.productsFrequencies(Tools.conformsPCM(mlist)), "outPutCSV/productsFrenquenciesDatasetConform.csv", "Product", "Occurrences");

        CsvTools.productsConformRatioToCsv(mlist, "outPutCSV/conformProductsDataset.csv");
        CsvTools.productsConformRatioToCsv(Tools.conformsPCM(mlist), "outPutCSV/conformProductsDatasetConform.csv");

        CsvTools.featuresConformRatioToCsv(mlist, "outPutCSV/conformFeaturesDataset.csv");
        CsvTools.featuresConformRatioToCsv(Tools.conformsPCM(mlist), "outPutCSV/conformFeaturesDatasetConform.csv");

        CsvTools.pcmHomogeneityToCsv(mlist.get(1), "outPutCSV/homogeneite1.csv");
*/
//        CsvTools.convertMapToCsv(Tools.pcmSimilarities(Tools.conformsPCM(mlist)), "outPutCSV/similaritiesDatasetConforms.csv", "PCM", "NbMaticesSimilaires");


// Ne passe pas avec tout le dataset
//        CsvTools.pcmHomogeneityToCsv(Tools.conformsPCM(mlist), "outPutCSV/homogeneiteDatasetConform.csv");

//        CsvTools.convertMapToCsv(Tools.pcmSimilarities(mlist), "outPutCSV/similaritiesDataset.csv", "PCM", "NbMaticesSimilaires");


//        CsvTools.pcmHomogeneityToCsv(mlist, "outPutCSV/homogeneiteDataset.csv");

//        CsvTools.pcmHomogeneityToCsv(mlist.get(1), "outPutCSV/homogeneite1b.csv");

       /* getChart(loadCsvData("outputCSV/conformFeaturesDataset.csv"),2000,"Conformité des features","",1300,900,"./charts/");
        getChart(loadCsvData("outputCSV/conformFeaturesDatasetConform.csv"),2000,"Conformité des features des matrices conformes","",1300,900,"./charts/");

        getChart(loadCsvData("outputCSV/conformProductsDataset.csv"),2000,"Conformité des products","",1300,900,"./charts/");
        getChart(loadCsvData("outputCSV/conformProductsDatasetConform.csv"),2000,"Conformité des products des matrices conformes","",1300,900,"./charts/");

        getChart(loadCsvData("outputCSV/featuresFrenquenciesDataset.csv"),20,"Occurrences des features","Les 20 plus élevées",1300,900,"./charts/");
        getChart(loadCsvData("outputCSV/featuresFrenquenciesDatasetConform.csv"),20,"Occurrences des products","Les 20 plus élevées",1300,900,"./charts/");

        getChart(loadCsvData("outputCSV/productsFrenquenciesDatasetConform.csv"),20,"Occurrences des products des matrices conformes","Les 20 plus élevées",1300,900,"./charts/");
        getChart(loadCsvData("outputCSV/productsFrenquenciesDataset.csv"),20,"Occurrences des products","Les 20 plus élevées",1300,900,"./charts/");

        getChart(loadCsvData("outputCSV/similaritiesDataset.csv"),20,"Similarités entre matrices","Les 20 plus élevées",1300,900,"./charts/");
      */
        getChart(loadCsvData("outputCSV/similaritiesDatasetConforms.csv"), 20, "Similarités entre matrices conformes", "Les 20 plus élevées", 1300, 900, "./charts/");


//        getChart(loadCsvData("outputCSV/featuresFrenquenciesDataset.csv"), "Products", "10 most frequents");
        System.out.println("end");
        
        
        
    }
}

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
	 * @throws IOException
	 */
	public static void main(String[] args) throws IOException {
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
		Files.write(Paths.get("outputCSV/homogeneite.csv"), homogeneites, Charset.defaultCharset());

		System.out.println("Cellules");
		List<String> celluleFrequences = new ArrayList<>();
		celluleFrequences.add("Matrice;CelluleValue;Frequence");
		celluleFrequences.addAll(Tools.celluleFrequences(manager.PcmList));
		// ecriture en csv
		Files.write(Paths.get("outputCSV/celluleFrequences.csv"), celluleFrequences, Charset.defaultCharset());
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

