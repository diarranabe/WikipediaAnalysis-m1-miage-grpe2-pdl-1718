package org.opencompare;

import org.junit.Test;

import java.util.Iterator;
import java.util.Map;

import static org.junit.Assert.assertEquals;

public class PCMManagerTest {

	@Test
	public void mostFrequentFeaturesTest() {
		PCMManager manager = new PCMManager("pcms2/");
        Map<String, Long> mostFrenquents = Tools.featuresFrequencies(manager.pcmList);

        Iterator it = mostFrenquents.entrySet().iterator();
		 long occurences = 0;
			while (it.hasNext()) {
				Map.Entry pair = (Map.Entry) it.next();
				occurences += (long)pair.getValue();
                System.out.println(Tools.featuresFrequencies(manager.pcmList));
            }
        assertEquals(occurences, Tools.featuresFrequencies(manager.pcmList).size());
    }
	
	@Test
	public void mostFrequentProduitTest() {
		PCMManager manager = new PCMManager("pcms2/");
        Map<String, Long> mostFrenquents = Tools.productsFrequencies(manager.pcmList);

        Iterator it = mostFrenquents.entrySet().iterator();
		 long occurences = 0;
			while (it.hasNext()) {
				Map.Entry pair = (Map.Entry) it.next();
				occurences += (long)pair.getValue();
                System.out.println(Tools.featuresFrequencies(manager.pcmList));
            }
		assertEquals(2, 2);
	}

}
