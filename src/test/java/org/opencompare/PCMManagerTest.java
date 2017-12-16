package org.opencompare;

import org.junit.Test;
import org.opencompare.api.java.PCM;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;

public class PCMManagerTest {

	@Test
    public void productsFrequenciesTest() {
        PCMManager manager = new PCMManager("pcms2/");
        Map<String, Long> mostFrenquents = Tools.productsFrequencies(Tools.conformsPCM(manager.pcmList));
        Iterator it = mostFrenquents.entrySet().iterator();
        int occurences = 0;
        while (it.hasNext()) {
            Map.Entry pair = (Map.Entry) it.next();
            occurences += (long) pair.getValue();
        }
        int nbDeProd = 0;
        for (PCM p : Tools.conformsPCM(manager.pcmList)) {
            nbDeProd += p.getProducts().size();
        }
        assertEquals(occurences, nbDeProd);
    }


	@Test
    public void featuresFrenquenciesTest() {
        PCMManager manager = new PCMManager("pcms2/");
        List<PCM> list = manager.pcmList;
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


}
