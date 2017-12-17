package org.opencompare;

import org.junit.Test;
import org.opencompare.api.java.PCM;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;

public class PCMManagerTest {
    private List<PCM> list = Tools.conformsPCM(Tools.loadAllPcmFromDirectory("pcms2/"));

	@Test
    public void productsFrequenciesTest() {
        Map<String, Long> mostFrenquents = Tools.productsFrequencies(list);
        Iterator it = mostFrenquents.entrySet().iterator();
        int occurences = 1;
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


	@Test
    public void featuresFrenquenciesTest() {
        List<PCM> list1 = new ArrayList<>();
        list1.add(list.get(0));
        list1.add(list.get(0));

        Map<String, Long> mostFrenquents = Tools.featuresFrequencies(Tools.conformsPCM(list1));

        Iterator it = mostFrenquents.entrySet().iterator();
        long occurences = 0;
        while (it.hasNext()) {
            Map.Entry pair = (Map.Entry) it.next();
            occurences += (long) pair.getValue();
        }

        int nbDeProd = 0;
        for (PCM p : Tools.conformsPCM(list1)) {
            nbDeProd += p.getConcreteFeatures().size();
        }
        assertEquals(occurences, nbDeProd);
    }

    @Test
    public void similarityTest() {
        PCM pcm = list.get(0);
        assertEquals(100, (int) Tools.pcmSimilarities(pcm, pcm));
    }

    @Test
    public void featuresSimilarityTest() {
        PCM pcm = list.get(0);
        assertEquals(100, (int) Tools.pcmFeaturesSimilarityRatio(pcm, pcm));
    }


    @Test
    public void productsSimilarityTest() {
        PCM pcm = list.get(0);
        assertEquals(100, (int) Tools.pcmProductsSimilarityRatio(pcm, pcm));
    }

}
