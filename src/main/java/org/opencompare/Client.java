package org.opencompare;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.opencompare.api.java.PCM;
import org.opencompare.api.java.PCMContainer;
import org.opencompare.api.java.impl.io.KMFJSONLoader;
import org.opencompare.api.java.io.PCMLoader;

public class Client {

	public static void main(String[] args) throws IOException {

		File pcmFile = new File("pcms/Comparison_of_Adobe_Flex_charts_0.pcm");

		PCMLoader loader = new KMFJSONLoader();
		List<PCMContainer> pcmContainers = loader.load(pcmFile);
		PCM pcm = pcmContainers.get(0).getPcm();

		Integer matrixSize = Tools.getMatrixSize(pcm);

		System.out.println("La taille de la matrice est "+matrixSize);


		System.out.println(Tools.celluleFrequences(pcm));

		
	
	}
}
