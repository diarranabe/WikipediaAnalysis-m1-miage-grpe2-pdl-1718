package org.opencompare;

import org.junit.Test;
import org.opencompare.api.java.PCM;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;

public class MatrixAnalysisTest {
    private List<PCM> list = Tools.conformsPCM(Tools.loadAllPcmFromDirectory("pcms2/"));

    /**
     * Test de la fréquence des products
     * * La somme des occurrences doit être = au nombre de products
     */
    @Test
    public void productsFrequenciesTest() {
        Map<String, Long> mostFrenquents = Tools.productsFrequencies(list);
        Iterator it = mostFrenquents.entrySet().iterator();
        int occurences = 0;
        while (it.hasNext()) {
            Map.Entry pair = (Map.Entry) it.next();
            occurences += (long) pair.getValue();
        }
        int nbDeProd = 0;
        for (PCM p : list) {
            nbDeProd += p.getProducts().size();
        }
        assertEquals(occurences, nbDeProd);
    }

    /**
     * Test de la fréquence des features
     * La somme des occurrences doit être = au nombre de features
     */
    @Test
    public void featuresFrenquenciesTest() {
        List<PCM> list1 = new ArrayList<>();
        list1.add(list.get(5));
        list1.add(list.get(5));

        Map<String, Long> mostFrenquents = Tools.featuresFrequencies(list1);

        Iterator it = mostFrenquents.entrySet().iterator();
        long occurences = 0;
        while (it.hasNext()) {
            Map.Entry pair = (Map.Entry) it.next();
            occurences += (long) pair.getValue();
        }

        int nbDeProd = 0;
        for (PCM p : list1) {
            nbDeProd += p.getFeatures().size();
        }
        assertEquals(occurences, nbDeProd);
    }

    /**
     * Comparaison d'un même pcm
     * Il doit être similaire à 100 % à lui même
     */
    @Test
    public void similarityTest() {
        PCM pcm = list.get(0);
        assertEquals(100, (int) Tools.pcmSimilarities(pcm, pcm));
    }

    /**
     * Comparaison des fetaures d'un même pcm
     * Les features doivent être similaires à 100
     */
    @Test
    public void featuresSimilarityTest() {
        PCM pcm = list.get(0);
        assertEquals(100, (int) Tools.pcmFeaturesSimilarityRatio(pcm, pcm));
    }


    /**
     * Comparaison des products d'un même pcm
     * Les features doivent être similaires à 100
     */
    @Test
    public void productsSimilarityTest() {
        PCM pcm = list.get(0);
        assertEquals(100, (int) Tools.pcmProductsSimilarityRatio(pcm, pcm));
    }

}
