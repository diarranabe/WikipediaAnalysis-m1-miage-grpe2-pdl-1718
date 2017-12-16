package org.opencompare;

import org.opencompare.api.java.PCM;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * @author diarranabe
 */
public class PCMManager {


    /**
     * Listes des objets java PCM chargés
     * Permet d'avoir tous les pcm du dataset
     */
    public List<PCM> pcmList = new ArrayList<>();

    public PCMManager(String path) {
        this.pcmList = Tools.loadAllPcmFromDirectory(path);
    }

    /**
     * @param args
     */
    public static void main(String[] args) {
        System.out.println("start");
        // TODO Auto-generated method stub
        PCMManager manager = new PCMManager("pcms2/");
        Map<String, Long> features = Tools.featuresFrequencies(manager.pcmList);
        Map<String, Long> products = Tools.productsFrequencies(manager.pcmList);
        List<PCM> pcmList = (manager.pcmList);
        Iterator it = features.entrySet().iterator();

//        Chart.showChart(products, "Products", ""); // Affiche les occurr des products
//        Chart.showChart(features, "Features", "");// Affiche les occurr des features

//        System.out.println("ratio de feat p1 & 2 est "+ Tools.pcmFeaturesSimilarityRatio(pcmList.get(1), pcmList.get(1)));
//        System.out.println("ratio de prod p1 & 2 est "+ Tools.pcmProductsSimilarityRatio(pcmList.get(0), pcmList.get(1)));
        System.out.println("rati de sim p1 & 2 est " + Tools.pcmSimilarities(pcmList.get(0), pcmList.get(0)));
//        System.out.println("nb de sim de p1 est "+ Tools.pcmSimillarityList(pcmList.get(2), pcmList));
        System.out.println("sim de tous est " + Tools.pcmSimilarities(pcmList).values());
        int i = 0;
        for (Map.Entry<String, Long> entry : Tools.pcmSimilarities(pcmList).entrySet()) {
            System.out.println(i++ + "pcm : " + entry.getKey() + ", value: " + entry.getValue());
        }

        System.out.println("end");
    }
}
