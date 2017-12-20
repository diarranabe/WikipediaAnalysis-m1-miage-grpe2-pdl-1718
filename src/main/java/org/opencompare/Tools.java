package org.opencompare;

import org.apache.commons.lang3.StringUtils;
import org.opencompare.api.java.*;
import org.opencompare.api.java.impl.io.KMFJSONLoader;
import org.opencompare.api.java.io.PCMLoader;
import org.opencompare.api.java.util.Pair;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.util.*;
import java.util.Map.Entry;
import java.util.stream.Collectors;

/**
 * Toutes les methodes de traitement
 */
public class Tools {

    /**
     * Le ratio de tolérance des features / products
     * Le taux d'element correcte doit être supérieur ou égal à ce ratio
     * pour qu'un pcm soit considéré comme apte à être analyser
     */
    public static int PCM_CONFORM_RATIO = 100; // Default

    /**
     * Le ratio pour affirmer deux pcm sont simillaires
     */
    public static int PCM_SIMILLARITY_RATIO = 100; // Default

    /**
     * Prends un pcm en param�tre et retourne un Map de ses cellules
     *
     * @param pcm pcm
     * @return map de cellules et occurrences
     */
    public static Map<String, Long> cellsFrequencies(PCM pcm) {

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
     * Prends un pcm en param�tre et retourne un Map de ses cellules
     *
     * @param pcmList liste de pcm
     * @return map de cellules et occurrences
     */
    public static Map<String, Long> cellsFrequencies(List<PCM> pcmList) {
        Map<String, Long> map = new HashMap<>();

        for (PCM pcm : pcmList) {
            map = appendMap(map, cellsFrequencies(pcm));
        }
        return sortByValue(map);
    }

    /**
     * Fusionne 2 map
     *
     * @param map1 map1
     * @param map2 map2
     * @return map
     */
    public static Map<String, Long> appendMap(Map<String, Long> map1, Map<String, Long> map2) {
        for (Map.Entry<String, Long> entry : map2.entrySet()) {
            if (map1.containsKey(entry.getKey())) {
                map1.put(entry.getKey(), map1.get(entry.getKey()) + entry.getValue());
            } else {
                map1.put(entry.getKey(), entry.getValue());
            }
        }
        return map1;
    }

    /**
     * Prends une liste de PCM et retourne la liste en vrac de tous les features
     *
     * @param pcmList list de pcm
     * @return Map String, Long
     */
    public static List<String> pcmListFeatures(List<PCM> pcmList) {

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
     * @param pcmList liste de pcm
     * @return Map String, Long
     */
    public static List<String> pcmListProducts(List<PCM> pcmList) {

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
     * Homogénéité des colonnes d'un pcm
     *
     * @param pcm le pcm
     * @return List String  une liste de Matrice;Feature;TypePredominant;taux pour chaque feature
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
                if (name.length() > 50) {
                    name = name.substring(0, 50);
                }
                result.add(String.format("%s;%s;%s;%s", format(pcm.getName()), format(name), format(value._1), value._2));
            }
        }
        return result;
    }

    /**
     * Retire les caractères non désirés d'un String pour être stocké en csv
     * @param value le string
     * @return un string
     */
    public static String format(String value) {
        if (value != null) {
            value = value.replaceAll("([^ ()éôèçà_a-zA-Z0-9'])", " ");
        }
        return value;
    }

    /**
     * Homogénéité d'une colonne
     * @param listType les differents types
     * @return le type récurrent et sa fréquence
     */
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
     * Occurences des features
     *
     * @param pcmList liste de pcm
     * @return Map des features et leurs occurences
     */

    public static Map<String, Long> featuresFrequencies(List<PCM> pcmList) {
        List<String> listFeatures = new ArrayList<>();
        for (PCM pcm : pcmList) {
            List<AbstractFeature> features = pcm.getFeatures().stream()
                    .filter(f -> StringUtils.isNoneBlank(f.getName())).collect(Collectors.toList());
            for (AbstractFeature f : features) {

                String feat = f.getName();
                if (feat.length() > 0) listFeatures.add(feat);
            }
        }
        Map<String, Long> occurrences = listFeatures.stream()
                .collect(Collectors.groupingBy(w -> w, Collectors.counting()));

        return sortByValue(occurrences);
    }

    /**
     * Occurences des products
     *
     * @param pcmList liste de pcm
     * @return Map des products et leurs occurences
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
     * Trie un Map dans l'aodre croissant des valeurs
     *
     * @param map map
     * @return map ordonné
     */
    public static <K, V extends Comparable<? super V>> Map<K, V> sortByValue(Map<K, V> map) {
        return map.entrySet().stream().sorted(Map.Entry.comparingByValue(/* Collections.reverseOrder() */))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new));
    }

    /**
     *  Charger les fichiers .pcm  d'un répertoire et les retournes tous en objet PCM
     *
     * @param path le chemin du répertoire contenant les pcm
     * @return liste de pcm ou null
     */
    public static List<PCM> loadAllPcmFromDirectory(String path) {
        if (path == null) return null;
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
     * @return PCM ou null
     */

    public static PCM loadPcmFromFile(File file) {
        if (file == null) return null;
        PCMLoader loader = new KMFJSONLoader();
        List<PCMContainer> pcmContainers = null;
        try {
            pcmContainers = loader.load(file);
        } catch (IOException e) {
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
    public static void printPcmsList(List<PCM> pcmList) {
        int i = 1;
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
     * @return int le ratio
     */
    public static int pcmFeaturesConformRatio(PCM pcm) {
        if (pcm == null) return 0;
        int ratio = 0;
        List<AbstractFeature> features = pcm.getFeatures();
        if (features.size() == 0) return 0;
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
        if (pcm == null) return 0;
        int ratio = 0;
        List<Product> products = pcm.getProducts();
        for (Product p : products) {
            if (productNameConform(p.getKeyContent())) {
                ratio++;
            } else {

            }
        }
        int r = (int) (((float) ratio / (float) products.size()) * 100);
        return r;
    }

    /**
     * Verifie si un feature est conforme
     *
     * @param feature le nom du feature
     * @return true en cas de succès
     */
    public static boolean featureNameConform(String feature) {
        if (feature == null) return false;
        String str = format(feature);
        return str.equals(feature) && feature.length() > 0;
    }

    /**
     * Verifie si un product est conforme
     *
     * @param product le nom du product
     * @return true en cas de succès
     */
    public static boolean productNameConform(String product) {
        if (product == null) return false;
        String str = format(product);
        return str.equals(product) && product.length() > 0;
    }

    /**
     * Verifie si un pcm est conforme
     *
     * @param pcm le pcm
     * @return true en cas de succès
     */
    public static boolean conformPCM(PCM pcm) {
        return (pcmProductsConformRatio(pcm) >= PCM_CONFORM_RATIO) && (pcmFeaturesConformRatio(pcm) >= PCM_CONFORM_RATIO);
    }

    /**
     * Retourne la liste de pcm conformes d'une liste
     *
     * @param allPcm liste de pcm
     * @return liste de pcm conformes
     */
    public static List<PCM> conformsPCM(List<PCM> allPcm) {
        List<PCM> conformPcms = new ArrayList<>();
        for (PCM pcm : allPcm) {
            if (conformPCM(pcm)) {
                conformPcms.add(pcm);
            }
        }
        return conformPcms;
    }


    /**
     * Ratio de similarité des features de deux pcm
     *
     * @param pcm1 pcm 1
     * @param pcm2 pcm2
     * @return float
     */
    public static float pcmFeaturesSimilarityRatio(PCM pcm1, PCM pcm2) {
        if (pcm1 == null || pcm2 == null) return 0;
        float ratiof = 0;
        List<AbstractFeature> features1 = pcm1.getFeatures();
        List<AbstractFeature> features2 = pcm2.getFeatures();
        if (features1.size() > features2.size()) {
            for (AbstractFeature f1 : features1) {
                for (AbstractFeature f2 : features2) {
                    if (f1.getName().equals(f2.getName())) {
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
     * Ratio de similarité des features de deux pcm
     *
     * @param pcm1 pcm 1
     * @param pcm2 pcm 2
     * @return float
     */
    public static float pcmProductsSimilarityRatio(PCM pcm1, PCM pcm2) {
        if (pcm1 == null || pcm2 == null) return 0;
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
     * similarité entre deux pcm
     *
     * @param pcm1 pcm1
     * @param pcm2 pcm2
     * @return float
     */
    public static float pcmSimilarities(PCM pcm1, PCM pcm2) {
        if (pcm1 == null || pcm2 == null) return 0;
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
     * @return map nom de pcm,nombre de pcm simmilaires
     */
    public static Map<String, Long> pcmSimilarities(List<PCM> pcmList) {
        Map<String, Long> map = new HashMap<String, Long>();
        for (PCM p : pcmList) {
            map.put(p.getName(), Long.valueOf(pcmSimillarityList(p, pcmList).size()));
        }
        return map;
    }
}
/**
	 * Get PCM matrix size
	 * @param pcm
	 * @return
	 */
	public static Integer getMatrixSize(PCM pcm){
		return pcm.getProducts().size() * pcm.getFeatures().size();
	}

	public static Map<String,Integer> mapDesCellules(PCM pcm) {
		
		Map<String, Integer> map = new HashMap<>();

		List<Product> listProduit = pcm.getProducts();

		for (Product p : listProduit) {

			List<Cell> listCell= p.getCells();
			for (Cell c : listCell) {
				
				map.put(c.getContent(),1);
			}
			
		}
		return map;
	}
	

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

