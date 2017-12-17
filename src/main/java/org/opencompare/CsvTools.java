package org.opencompare;

import org.opencompare.api.java.PCM;
import org.supercsv.io.CsvListWriter;
import org.supercsv.io.ICsvListWriter;
import org.supercsv.prefs.CsvPreference;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.opencompare.Tools.format;

/**
 * Les traitement csv
 */
public class CsvTools {
    public static void productsConformRatioToCsv(List<PCM> pcmList, String fileNamecsv) {
        List<String> products = new ArrayList<>();
        products.add("Matrice;prodConformRatio");
        for (PCM pcm : pcmList) {
            int ratio = Tools.pcmProductsConformRatio(pcm);
            products.add(format(pcm.getName()) + ";" + ratio);
        }
        try {
            Files.write(Paths.get(fileNamecsv), products, Charset.defaultCharset());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Crée un csv des ratio de conformité des pcm
     *
     * @param pcmList     liste de pcm
     * @param fileNamecsv nom du fichier csv
     */
    public static void featuresConformRatioToCsv(List<PCM> pcmList, String fileNamecsv) {
        List<String> products = new ArrayList<>();
        products.add("Matrice;featConformRatio");
        for (PCM pcm : pcmList) {
            int ratio = Tools.pcmFeaturesConformRatio(pcm);
            products.add(format(pcm.getName()) + ";" + ratio);
            System.out.println("ratio " + ratio);
        }
        try {
            Files.write(Paths.get(fileNamecsv), products, Charset.defaultCharset());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Converti une map en fichier csv
     *
     * @param map            la map
     * @param fileOutputName nom du fichier de sortie
     * @throws Exception exception
     */
    public static void convertMapToCsv(Map<String, Long> map, String fileOutputName) throws Exception {

        StringWriter output = new StringWriter();
        try (ICsvListWriter listWriter = new CsvListWriter(output, CsvPreference.STANDARD_PREFERENCE)) {
            for (Map.Entry<String, Long> entry : map.entrySet()) {
                listWriter.write(entry.getKey().replaceAll("\\s*[\\r\\n]+\\s*", "").trim(), entry.getValue());
            }
        }

        System.out.println("[DEBUG] strin to csv : \n" + output.toString());
        PrintWriter out = new PrintWriter("outputCSV/" + fileOutputName);
        // out.write("sep=,");
        // out.write("\n");
        out.write(output.toString());
        out.close();

    }

    /**
     * * Converti une map en fichier csv
     * @param map map
     * @param fileOutputNamecsv nom du fichier csv
     * @param header1 entête1
     * @param header2 entête1
     */
    public static void convertMapToCsv(Map<String, Long> map, String fileOutputNamecsv, String header1, String header2) {

        try {
            List<String> outputlist = new ArrayList<>();
            for (Map.Entry<String, Long> entry : map.entrySet()) {
                outputlist.add("\r\n" + format(entry.getKey()) + "," + entry.getValue());
            }
            PrintWriter pw = new PrintWriter(new File(fileOutputNamecsv));
            StringBuilder sb = new StringBuilder(header1 + "," + header2);
            for (String str : outputlist) {
                sb.append(str);
            }
            pw.write(sb.toString());
            pw.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Ecrit un string dans un fichier
     * @param content le string
     * @param fileName lefichier
     */
    public static void convertStringToFile(String content, String fileName) throws FileNotFoundException {
        PrintWriter out = new PrintWriter("outputCSV/" + fileName);
        out.write(content);
        out.close();
    }

    /**
     * Affiches les differentes tailles des pcm
     * @param pcmList liste de pcm
     * @param fileNamecsv nom du fichier
     */
    public static void printSizesToCsv(List<PCM> pcmList, String fileNamecsv) {
        StringBuilder sb = new StringBuilder();
        sb.append("\"sep=,\"\n");
        sb.append("namePCM,nbLigne,nbColone,tailleMatrice\n");
        for (PCM p : pcmList) {
            String line = "\"" + p.getName() + "\"" + "," + p.getProducts().size() + "," + p.getFeatures().size() + ","
                    + p.getProducts().size() * p.getFeatures().size();
            sb.append(line).append("\n");
        }

        try {
            convertStringToFile(sb.toString(), fileNamecsv);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * Crée un fichier csv de l'homogénéité des pcm
     * @param list liste de pcm
     * @param fileNamecsv nom du fichier
     */
    public static void pcmHomogeneityToCsv(List<PCM> list, String fileNamecsv) {
        List<String> homogeneites = new ArrayList<>();
        List<PCM> pcmList = new ArrayList<>();
        homogeneites.add("Matrice;Feature;TypePredominant;taux");
        for (PCM pcm : list) {
            homogeneites.addAll(Tools.homogeneitePCM(pcm));
        }
        // ecriture en csv
        try {
            Files.write(Paths.get(fileNamecsv), homogeneites, Charset.defaultCharset());
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    /**
     * Crée un fichier pour l'homogénéité d'un pcm
     * @param pcm le pcm
     * @param fileNamecsv nom du fichier
     */
    public static void pcmHomogeneityToCsv(PCM pcm, String fileNamecsv) {
        List<String> homogeneites = new ArrayList<>();
        homogeneites.add("Matrice;Feature;TypePredominant;taux");
        homogeneites.addAll(Tools.homogeneitePCM(pcm));
        // ecriture en csv
        try {
            Files.write(Paths.get(fileNamecsv), homogeneites, Charset.defaultCharset());
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
