package org.opencompare;

import org.opencompare.api.java.PCM;

import java.util.List;

public class PCMManager {

    /**
     * @param args args
     */
    public static void main(String[] args) {
        System.out.println("start");
        List<PCM> mlist = Tools.loadAllPcmFromDirectory("pcms/");
        List<PCM> confPcmList = Tools.conformsPCM(mlist);
//        CsvTools.pcmHomogeneityToCsv(confPcmList, "outPutCSV/homogeneiteDataset.csv");
//        getChart(loadCsvData("outputCSV/homogeneiteDataset.csv"), 2000, "Homogénéité des matrices", "", 1300, 900, "./charts/");
//
//        System.out.println("size all : " + mlist.size());
//        System.out.println("size conf : " + confPcmList.size());
//        CsvTools.convertMapToCsv(Tools.featuresFrequencies(confPcmList), "outPutCSV/featuresFrenquenciesDatasetConform.csv", "Feature", "Occurrences");
//        getChart(loadCsvData("outputCSV/similaritiesDatasetConforms.csv"), 20, "Similarité des matrices", "Les 20 plus élevées", 1300, 900, "./charts/");
//        CsvTools.convertMapToCsv(Tools.pcmSimilarities(confPcmList), "outPutCSV/similaritiesDatasetConforms.csv", "PCM", "NbMaticesSimilaires");


//        getChart(loadCsvData("outputCSV/productsFrenquenciesDatasetConform.csv"),20,"Occurrences des products des matrices conformes","Les 20 plus élevées",1300,900,"./charts/");
//        CsvTools.convertMapToCsv(Tools.productsFrequencies(Tools.conformsPCM(mlist)), "outPutCSV/productsFrenquenciesDatasetConform.csv", "Product", "Occurrences");
/*
        CsvTools.convertMapToCsv(Tools.cellsFrequencies(mlist), "outPutCSV/cellsFrequenciesDataset.csv", "Cell", "Occurrences");
        getChart(loadCsvData("outputCSV/cellsFrequenciesDatasetConforms.csv"), 20, "Occurrences des cells des matrices conformes", "Les 20 plus élevées", 1300, 900, "./charts/");

*/

//                CsvTools.productsConformRatioToCsv(mlist, "outPutCSV/conformProductsDataset.csv");

//        CsvTools.productsConformRatioToCsv(Tools.conformsPCM(mlist), "outPutCSV/conformProductsDatasetConform.csv");

//                getChart(loadCsvData("outputCSV/conformProductsDataset.csv"),2000,"Conformité des products","",1300,900,"./charts/");
//                getChart(loadCsvData("outputCSV/conformProductsDatasetConform.csv"),2000,"Conformité des products des matrices conformes","",1300,900,"./charts/");


       /* CsvTools.featuresConformRatioToCsv(mlist, "outPutCSV/conformFeaturesDataset.csv");
        CsvTools.featuresConformRatioToCsv(Tools.conformsPCM(mlist), "outPutCSV/conformFeaturesDatasetConform.csv");

        CsvTools.convertMapToCsv(Tools.featuresFrequencies(mlist), "outPutCSV/featuresFrenquenciesDataset.csv", "Feature", "Occurrences");
        CsvTools.convertMapToCsv(Tools.featuresFrequencies(Tools.conformsPCM(mlist)), "outPutCSV/featuresFrenquenciesDatasetConform.csv", "Feature", "Occurrences");

        CsvTools.convertMapToCsv(Tools.productsFrequencies(mlist), "outPutCSV/productsFrenquenciesDataset.csv", "Product", "Occurrences");
        CsvTools.convertMapToCsv(Tools.productsFrequencies(Tools.conformsPCM(mlist)), "outPutCSV/productsFrenquenciesDatasetConform.csv", "Product", "Occurrences");

        CsvTools.pcmHomogeneityToCsv(mlist.get(1), "outPutCSV/homogeneite1.csv");
        CsvTools.convertMapToCsv(Tools.pcmSimilarities(Tools.conformsPCM(mlist)), "outPutCSV/similaritiesDatasetConforms.csv", "PCM", "NbMaticesSimilaires");

        CsvTools.convertMapToCsv(Tools.cellsFrequencies(Tools.conformsPCM(mlist)), "outPutCSV/cellsFrequenciesDatasetConforms.csv", "Cell", "Occurrences");
        CsvTools.convertMapToCsv(Tools.cellsFrequencies(mlist), "outPutCSV/cellsFrequenciesDataset.csv", "Cell", "Occurrences");
*/

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
        getChart(loadCsvData("outputCSV/featuresFrenquenciesDatasetConform.csv"),20,"Occurrences des features des matrices conformes","Les 20 plus élevées",1300,900,"./charts/");

        getChart(loadCsvData("outputCSV/productsFrenquenciesDatasetConform.csv"),20,"Occurrences des products des matrices conformes","Les 20 plus élevées",1300,900,"./charts/");
        getChart(loadCsvData("outputCSV/productsFrenquenciesDataset.csv"),20,"Occurrences des products","Les 20 plus élevées",1300,900,"./charts/");

        getChart(loadCsvData("outputCSV/similaritiesDataset.csv"),20,"Similarités entre matrices","Les 20 plus élevées",1300,900,"./charts/");
      */
//        getChart(loadCsvData("outputCSV/similaritiesDatasetConforms.csv"), 20, "Similarités entre matrices conformes", "Les 20 plus élevées", 1300, 900, "./charts/");

//        getChart(loadCsvData("outputCSV/cellsFrequenciesDataset.csv"), 20, "Occurrences des cells", "Les 20 plus élevées", 1300, 900, "./charts/");
//        getChart(loadCsvData("outputCSV/cellsFrequenciesDatasetConforms.csv"), 20, "Occurrences des cells des matrices conformes", "Les 20 plus élevées", 1300, 900, "./charts/");


//        getChart(loadCsvData("outputCSV/featuresFrenquenciesDataset.csv"), "Products", "10 most frequents");
        System.out.println("size all : " + mlist.size());
        System.out.println("size conf : " + confPcmList.size());
        System.out.println("end");
    }
}