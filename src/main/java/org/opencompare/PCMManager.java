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