package org.opencompare;

import static org.junit.Assert.*;

import java.util.Iterator;
import java.util.Map;

import org.junit.Test;

public class PCMManagerTest {

	@Test
	public void mostFrequentFeaturesTest() {
		PCMManager manager = new PCMManager("pcms2/");
		 Map<String, Long> mostFrenquents = Tools.mostFrequentFeatures(manager.PcmList);
		 
		 Iterator it = mostFrenquents.entrySet().iterator();
		 long occurences = 0;
			while (it.hasNext()) {
				Map.Entry pair = (Map.Entry) it.next();
				occurences += (long)pair.getValue();
				System.out.println(Tools.mostFrequentFeatures(manager.PcmList));
			}			
		assertEquals(occurences, Tools.mostFrequentFeatures(manager.PcmList).size());
	}
	
	@Test
	public void mostFrequentProduitTest() {
		PCMManager manager = new PCMManager("pcms2/");
		 Map<String, Long> mostFrenquents = Tools.mostFrequentProducts(manager.PcmList);
		 
		 Iterator it = mostFrenquents.entrySet().iterator();
		 long occurences = 0;
			while (it.hasNext()) {
				Map.Entry pair = (Map.Entry) it.next();
				occurences += (long)pair.getValue();
				System.out.println(Tools.mostFrequentFeatures(manager.PcmList));
			}			
		assertEquals(2, 2);
	}

}
