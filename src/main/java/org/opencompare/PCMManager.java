package org.opencompare;

import org.opencompare.api.java.PCM;

import java.util.List;

import static org.opencompare.Chart.getChart;
import static org.opencompare.Chart.loadCsvData;

/**
 * Classe principale
 */
public class PCMManager {

    /**
     * @param args args
     */
    public static void main(String[] args) {
        System.out.println("start");

        List<PCM> dataset = Tools.loadAllPcmFromDirectory("pcms/");
        List<PCM> datasetConform = Tools.conformsPCM(dataset);

        CsvTools.featuresConformRatioToCsv(dataset, "outPutCSV/featuresConformRatio.csv");
        getChart(loadCsvData("outputCSV/featuresConformRatio.csv"), 200000, "Conformitées des features de tous les pcm", " ", 1300, 900, "./charts/");

        CsvTools.productsConformRatioToCsv(dataset, "outPutCSV/productsConformRatio.csv");
        getChart(loadCsvData("outputCSV/productsConformRatio.csv"), 20000, "Conformitées des products de tous les pcm", " ", 1300, 900, "./charts/");

        CsvTools.convertMapToCsv(Tools.featuresFrequencies(datasetConform), "outPutCSV/featuresFrenquenciesDataset.csv", "Feature", "Occurrences");
        getChart(loadCsvData("outputCSV/featuresFrenquenciesDataset.csv"), 20, "Occurrences des features des matrices conformes", "Les 20 plus élevées", 1300, 900, "./charts/");

        CsvTools.convertMapToCsv(Tools.productsFrequencies(datasetConform), "outPutCSV/productsFrenquenciesDatasetConform.csv", "Product", "Occurrences");
        getChart(loadCsvData("outputCSV/productsFrenquenciesDatasetConform.csv"), 20, "Occurrences des products des matrices conformes", "Les 20 plus élevées", 1300, 900, "./charts/");

        CsvTools.convertMapToCsv(Tools.cellsFrequencies(datasetConform), "outPutCSV/cellsFrequenciesDatasetConforms.csv", "Cell", "Occurrences");
        getChart(loadCsvData("outputCSV/cellsFrequenciesDatasetConforms.csv"), 20, "Occurrences des cells des matrices conformes", "Les 20 plus élevées", 1300, 900, "./charts/");

        CsvTools.convertMapToCsv(Tools.pcmSimilarities(datasetConform), "outPutCSV/similaritiesDatasetConforms.csv", "PCM", "NbMaticesSimilaires");
        getChart(loadCsvData("outputCSV/similaritiesDatasetConforms.csv"), 20, "Similarité des matrices", "Les 20 plus élevées", 1300, 900, "./charts/");

        System.out.println("finish");
    }
}