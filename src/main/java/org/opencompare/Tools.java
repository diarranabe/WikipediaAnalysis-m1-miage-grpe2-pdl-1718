package org.opencompare;

import org.apache.commons.lang3.StringUtils;
import org.opencompare.api.java.*;
import org.opencompare.api.java.impl.io.KMFJSONLoader;
import org.opencompare.api.java.io.PCMLoader;
import org.opencompare.api.java.util.Pair;
import org.supercsv.io.CsvListWriter;
import org.supercsv.io.ICsvListWriter;
import org.supercsv.prefs.CsvPreference;

import java.io.*;
import java.util.*;
import java.util.Map.Entry;
import java.util.stream.Collectors;

public class Tools {

    /**
     * Le ratio de tolérance des features / products
     * Le taux d'element correcte doit être >= à ce ratio
     * pour qu'un pcm soit considéré comme apte à être analyser
     */
    public static int PCM_CONFORM_RATIO = 80; // Default

    /**
     * Le ratio pour affirmer deux pcm sont simillaires
     */
    public static int PCM_SIMILLARITY_RATIO = 100; // Default


    /**
     * Get PCM matrix size
     *
     * @param pcm
     * @return
     */
    public static Integer getMatrixSize(PCM pcm) {
        return pcm.getProducts().size() * pcm.getFeatures().size();
    }

    public static Map<String, Integer> mapDesCellules(PCM pcm) {

        Map<String, Integer> map = new HashMap<>();

        List<Product> listProduit = pcm.getProducts();

        for (Product p : listProduit) {

            List<Cell> listCell = p.getCells();
            for (Cell c : listCell) {

                map.put(c.getContent(), 1);
            }

        }
        return map;
    }

    /**
     * Affiche un pcm en console
     *
     * @param pcm
     */
    public static void printMatrix(PCM pcm) {

        Map<String, Integer> map = new HashMap<>();

        System.out.println(pcm.getFeatures());
        List<Product> listProduit = pcm.getProducts();

        for (Product p : listProduit) {

            List<Cell> cells = p.getCells();
            for (Cell cell : cells) {
                System.out.print(cell.getContent().toString() + ",");
            }
            System.out.println();
        }
    }

    /**
     * Prends un pcm en param�tre et retourne un Map de ses cellules
     *
     * @param pcm
     * @return
     */
    public static Map<String, Long> celluleFrequences(PCM pcm) {

        List<String> listeRet = new ArrayList();

        List<Product> listProduit = pcm.getProducts();

        for (Product p : listProduit) {

            List<Cell> cells = p.getCells();
            for (Cell cell : cells) {
                System.out.println(cell.getContent());
                listeRet.add(cell.getContent());
            }
        }

        Map<String, Long> counts = listeRet.stream().collect(Collectors.groupingBy(e -> e, Collectors.counting()));

        return counts;
    }

    /**
     * Prends un pcm en param�tre et retourne la liste de ses feature
     *
     * @param p pcm
     * @return List<String>
     */
    public static List<String> getFeatures(PCM p) {
        List<String> result = new ArrayList<>();
        List<AbstractFeature> listF = p.getFeatures();
        for (AbstractFeature f : listF) {
            result.add(f.getName());
        }
        return result;
    }

    /**
     * Prends une liste de PCM et retourne la liste en vrac de tous les features
     *
     * @param pcmList
     * @return Map<String, Long>
     */
    public static List<String> allFeatures(List<PCM> pcmList) {

        List<String> listFeatures = new ArrayList<>();
        for (PCM pcm : pcmList) {
            List<AbstractFeature> features = pcm.getFeatures();
            for (AbstractFeature f : features) {
                listFeatures.add(f.getName());
            }
        }
        return listFeatures;
    }

    /**
     * Prends une liste de PCM et retourne la liste en vrac de tous les products
     *
     * @param pcmList
     * @return Map<String, Long>
     */
    public static List<String> allProducts(List<PCM> pcmList) {

        List<String> listProducts = new ArrayList<>();
        for (PCM pcm : pcmList) {
            List<Product> products = pcm.getProducts();
            for (Product p : products) {
                listProducts.add(p.getKeyContent());
            }
        }
        return listProducts;
    }

    /**
     * Homogénéité d'un pcm
     *
     * @param pcm le pcm
     * @return List<String>  une liste de Matrice;Feature;TypePredominant;taux pour chaque feature
     */
    public static List<String> homogeneitePCM(PCM pcm) {

        List<String> result = new ArrayList<>();
        CellTypePCMVisitor visitor = new CellTypePCMVisitor();

        visitor.visit(pcm);
        Map<String, List<String>> resultVisit = visitor.getResult();

        for (AbstractFeature f : pcm.getFeatures()) {
            if (!StringUtils.isBlank(f.getName())) {
                Pair<String, Double> value = homogeneiteColumn(resultVisit.get(f.getName()));
                String name = f.getName();
                if (name.length() > 50) {// TODO - ???
                    name = name.substring(0, 50);
                }
                result.add(String.format("%s;%s;%s;%s", format(pcm.getName()), format(name), value._1, value._2));
            }
        }
        return result;
    }

    private static String format(String value) {
        if (value != null) {
            value = value.replaceAll("\\s+", " ");
        }
        return value;
    }

    private static Pair<String, Double> homogeneiteColumn(List<String> listType) {
        if (listType == null) {
            return new Pair<String, Double>("NA", 1.0);
        }
        Map<String, Long> mapGroup = listType.stream().collect(Collectors.groupingBy(t -> t, Collectors.counting()));

        Entry<String, Long> maxEntry = mapGroup.entrySet().stream().max(Map.Entry.comparingByValue()).get();

        double h = (double) maxEntry.getValue() / (double) listType.size();
        return new Pair<String, Double>(maxEntry.getKey(), h);
    }

    /**
     * Prends une liste de PCM et retourne les features et leurs occurences
     *
     * @param pcmList
     * @return Map<String, Long>
     */

    public static Map<String, Long> featuresFrequencies(List<PCM> pcmList) {
        List<String> listFeatures = new ArrayList<>();
        for (PCM pcm : pcmList) {
            List<AbstractFeature> features = pcm.getFeatures().stream()
                    .filter(f -> StringUtils.isNoneBlank(f.getName())).collect(Collectors.toList());
            for (AbstractFeature f : features) {
                listFeatures.add(f.getName());
            }
        }
        Map<String, Long> occurrences = listFeatures.stream()
                .collect(Collectors.groupingBy(w -> w, Collectors.counting()));

        return sortByValue(occurrences);
    }

    /**
     * Prends une liste de PCM et retourne les products et leurs occurences
     *
     * @param pcmList
     * @return
     */
    public static Map<String, Long> productsFrequencies(List<PCM> pcmList) {
        List<String> listProduit = new ArrayList<>();
        for (PCM pcm : pcmList) {
            List<Product> products = pcm.getProducts().stream().filter(p -> StringUtils.isNoneBlank(p.getKeyContent()))
                    .collect(Collectors.toList());

            for (Product p : products) {
                listProduit.add(p.getKeyContent());
            }
        }
        Map<String, Long> occurrences = listProduit.stream()
                .collect(Collectors.groupingBy(w -> w, Collectors.counting()));
        return sortByValue(occurrences);
    }

    /**
     * Affiches les differentes tailles des pcm
     */
    public static String printSizes(List<PCM> pcmList) {
        StringBuilder sb = new StringBuilder();
        sb.append("\"sep=,\"\n");
        sb.append("namePCM,nbLigne,nbColone,tailleMatrice\n");
        for (PCM p : pcmList) {
            String line = "\"" + p.getName() + "\"" + "," + p.getProducts().size() + "," + p.getFeatures().size() + ","
                    + p.getProducts().size() * p.getFeatures().size();
            sb.append(line).append("\n");
        }

        try {
            convertStringToFile(sb.toString(), "matrixSize.csv");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return sb.toString();
    }

    /**
     * Trie un Map dans l'aodre croissant des valeurs
     *
     * @param map
     * @return
     */
    public static <K, V extends Comparable<? super V>> Map<K, V> sortByValue(Map<K, V> map) {
        return map.entrySet().stream().sorted(Map.Entry.comparingByValue(/* Collections.reverseOrder() */))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new));
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

    public static void convertStringToFile(String content, String fileName) throws FileNotFoundException {
        PrintWriter out = new PrintWriter("outputCSV/" + fileName);
        out.write(content);
        out.close();
    }

    /**
     * Trouve les products/features qui ont des noms ilisibles(cahs speciaux...) �
     * partir de donn�es csv
     *
     * @param path
     * @return
     */
    public static Map<String, Long> itemsNamesCheck(String path) {
        Map<String, Long> data = new HashMap<String, Long>();
        FileReader file = null;
        BufferedReader buffer = null;
        int names = 0;
        int noNames = 0;

        try {
            file = new FileReader(new File(path));
            buffer = new BufferedReader(file);
            String line = "";
            while ((line = buffer.readLine()) != null) {
                String key;
                Integer myVal;
                line = line.replaceAll("([^,a-zA-Z0-9])", "");
                line = line.replaceAll("\\s*[\\r\\n]+\\s*", "").trim();
                int lastComma = line.lastIndexOf(',');
                try {
                    myVal = Integer.parseInt(line.substring(lastComma + 1));
                } catch (NumberFormatException e) {
                    myVal = 1;
                    // System.err.println(e.getMessage());
                }
                if (lastComma == 0) {
                    noNames++;
                } else {
                    names++;
                }
            }
        } catch (NumberFormatException | IOException e) {
            e.printStackTrace();
        }
        data.put("names", Long.valueOf("" + names));
        data.put("noNames", Long.valueOf(noNames));
        return data;
    }

    /**
     * Charger les ses fichiers .pcm  d'un répertoire et les retournes tous en objet PCM
     *
     * @param path le chemin du répertoire
     */
    public static List<PCM> loadAllPcmFromDirectory(String path) {
        List<File> pcmFiles = new ArrayList<File>();
        List<PCM> pcmList = new ArrayList<PCM>();
        File file = new File(path);
        // Liste des fichiers correspondant a l'extension souhaitee
        final File[] children = file.listFiles(new FileFilter() {
            public boolean accept(File f) {
                return f.getName().endsWith("pcm");
            }
        });
        if (children != null) {
            // Pour chaque fichier recupere, on appelle a nouveau la methode
            for (File child : children) {
                pcmFiles.add(child);
            }
        }

        for (File pcmfile : pcmFiles) {
            pcmList.add(loadPcmFromFile(pcmfile));
        }
        return pcmList;
    }

    /**
     * Converti un fichier .pcm déja chargé en objet PCM
     *
     * @param file le fichier
     * @return PCM
     */

    public static PCM loadPcmFromFile(File file) {
        PCMLoader loader = new KMFJSONLoader();
        List<PCMContainer> pcmContainers = null;
        try {
            pcmContainers = loader.load(file);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        PCM pcm = pcmContainers.get(0).getPcm();
        return pcm;
    }

    /**
     * Affiches les pcm dans la console
     *
     * @param pcmList liste de pcm
     */
    public static void printPcms(List<PCM> pcmList) {
        int i = 0;
        for (PCM p : pcmList) {
            System.out.println(i + " : " + p);
            i++;
        }
    }

    /**
     * Trie une liste de PCM par ordre alphabétique des nom
     *
     * @param pcmList liste de PCM
     */
    public static List<PCM> pcmListAlphabetSort(List<PCM> pcmList) {
        Collections.sort(pcmList, new Comparator<PCM>() {
            public int compare(PCM pcm1, PCM pcm2) {
                return pcm1.getName().compareTo(pcm2.getName());
            }
        });
        return pcmList;
    }


    /**
     * Rétourne le ratio de conformité des features d'un pcm
     *
     * @param pcm le pcm
     * @return int le ration
     */
    public static int pcmFeaturesConformRatio(PCM pcm) {
        int ratio = 0;
        List<AbstractFeature> features = pcm.getFeatures();
        if (features.size() == 0) return 100;
        for (AbstractFeature f : features) {
            if (featureNameConform(f.getName())) {
                ratio++;
            }
        }
        return (int) (((float) ratio / (float) features.size()) * 100);
    }

    /**
     * Rétourne le ratio de conformité des products d'un pcm
     *
     * @param pcm le pcm
     * @return int le ration
     */
    public static int pcmProductsConformRatio(PCM pcm) {
        int ratio = 0;
        List<Product> products = pcm.getProducts();
        if (products.size() == 0) return 100;
        for (Product p : products) {
            if (productNameConform(p.getKeyContent())) {
                System.out.println("good product : " + p.getKeyContent() + ", ratio:" + ratio);
                ratio++;
            } else {
                System.err.println("bad product : " + p.getKeyContent() + ", ratio:" + ratio);
            }
        }
        int r = (int) (((float) ratio / (float) products.size()) * 100);
        System.err.println("Ratio : " + ratio + ", " + products.size() + ", :" + r);
        return r;
    }

    /**
     * Verifie si un feature est conforme
     *
     * @param feature le nom du feature
     * @return true en cas de succès
     */
    public static boolean featureNameConform(String feature) {
        String str = feature.replaceAll("([^ ()éèçà_,a-zA-Z0-9'])", "");
        return str.equals(feature);
    }

    /**
     * Verifie si un product est conforme
     *
     * @param product le nom du product
     * @return true en cas de succès
     */
    public static boolean productNameConform(String product) {
        String str = product.replaceAll("([^ ()éèçà_,a-zA-Z0-9'])", "");
        return str.equals(product);
    }

    /**
     * Ratio de simillarité des features de deux pcm
     *
     * @param pcm1 pcm 1
     * @param pcm2 pcm2
     * @return float
     */
    public static float pcmFeaturesSimilarityRatio(PCM pcm1, PCM pcm2) {
        float ratiof = 0;
        List<AbstractFeature> features1 = pcm1.getFeatures();
        List<AbstractFeature> features2 = pcm2.getFeatures();
        if (features1.size() > features2.size()) {
            for (AbstractFeature f1 : features1) {
                for (AbstractFeature f2 : features2) {
                    if (f1.getName().equals(f2.getName())) {
//                        System.out.println(f1.getName()+" ,,, "+f2.getName());
                        ratiof++;
                    }
                }
            }
            ratiof = ((ratiof / (float) features2.size()) * 100);
        } else {
            for (AbstractFeature f2 : features2) {
                for (AbstractFeature f1 : features1) {
                    if (f1.getName().equals(f2.getName())) {
                        ratiof++;
                    }
                }
            }
            ratiof = ((ratiof / (float) features1.size()) * 100);
        }
        return ratiof;
    }

    /**
     * Ratio de simillarité des features de deux pcm
     *
     * @param pcm1 pcm 1
     * @param pcm2 pcm 2
     * @return float
     */
    public static float pcmProductsSimilarityRatio(PCM pcm1, PCM pcm2) {
        float ratiop = 0;
        List<Product> products1 = pcm1.getProducts();
        List<Product> products2 = pcm2.getProducts();

        if (products1.size() > products2.size()) {
            for (Product p1 : products1) {
                for (Product p2 : products2) {
                    if (p1.getKeyContent().equals(p2.getKeyContent())) {
                        ratiop++;
                    }
                }
            }
            ratiop = ((ratiop / (float) products2.size()) * 100);
        } else {
            for (Product p2 : products2) {
                for (Product p1 : products1) {
                    if (p1.getKeyContent().equals(p2.getKeyContent())) {
                        ratiop++;
                    }
                }
            }
            ratiop = ((ratiop / (float) products1.size()) * 100);
        }
        return ratiop;
    }

    /**
     * Simillarité entre deux pcm
     *
     * @param pcm1 pcm1
     * @param pcm2 pcm2
     * @return float
     */
    public static float pcmSimilarities(PCM pcm1, PCM pcm2) {
        float r = (pcmFeaturesSimilarityRatio(pcm1, pcm2) + pcmProductsSimilarityRatio(pcm1, pcm2)) / 2;
        return r;
    }


    /**
     * Liste des pcm simmilaires à un autre pcm
     *
     * @param pcm     pcm entrée
     * @param pcmList la liste de pcm à comparer
     * @return liste
     */
    public static List<PCM> pcmSimillarityList(PCM pcm, List<PCM> pcmList) {
        List<PCM> simpcm = new ArrayList<>();
        for (PCM p : pcmList) {
            if (!(pcm.equals(p)) && (pcmSimilarities(pcm, p) >= PCM_SIMILLARITY_RATIO)) {
                simpcm.add(p);
            }
        }
        return simpcm;
    }

    /**
     * Les similaritées entre tous les pcm
     *
     * @param pcmList liste de pcm
     * @return map nom de pcm,nombre de pcm simmilaire
     */
    public static Map<String, Long> pcmSimilarities(List<PCM> pcmList) {
        Map<String, Long> map = new HashMap<String, Long>();
        for (PCM p : pcmList) {
            map.put(p.getName(), Long.valueOf(pcmSimillarityList(p, pcmList).size()));
        }
        return map;
    }
}
