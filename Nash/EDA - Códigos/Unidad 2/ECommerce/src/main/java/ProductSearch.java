import org.apache.commons.codec.EncoderException;
import org.apache.commons.codec.language.Metaphone;
import org.apache.commons.codec.language.Soundex;
import org.apache.commons.text.similarity.LevenshteinDistance;

import java.io.*;
import java.util.*;

public class ProductSearch {

    private final ArrayList<String> products = new ArrayList<>();
    private static final int MAX_SEARCH = 5;

    public Set<ProductElem> searchProduct(String search) throws EncoderException {

        List<String> productList = getProducts();

        SortedSet<ProductElem> matchingProducts = new TreeSet<>();

        //Pasamos el string a minusculas, sacamos acentos y espacios de mas
        String clearSearch = clearSearch(search);

        for(String prod : productList){
            matchingProducts.add(getProductData(clearSearch, prod));
        }

        SortedSet<ProductElem> toReturn = new TreeSet<>(Comparator.reverseOrder());

        //Tomamos los 5 con mayora valor
        for(int i = 0; i < MAX_SEARCH; i++){
            toReturn.add(matchingProducts.last());
            matchingProducts.remove(matchingProducts.last());
        }

        return toReturn;
    }

    private List<String> getProducts(){

        List<String> products = new ArrayList<>();
        InputStream inputStream = ProductSearch.class.getResourceAsStream("/product.txt");

        try {
            InputStreamReader isr = new InputStreamReader(inputStream);
            BufferedReader br = new BufferedReader(isr);

            String linea;
            while ((linea = br.readLine()) != null) {
                products.add(linea);
            }

            br.close(); // Cerrar el BufferedReader cuando ya no se necesite
        } catch (IOException e) {
            System.err.println("Error al leer el archivo: " + e.getMessage());
        }

        return products;
    }

    private ProductElem getProductData(String product, String element) throws EncoderException {

        //Calculamos el promedio de todos los algoritmos
        Double soundex = getSoundex(product, element);
        Double metaphone = getMetaphone(product, element);
        Double levenshtein = getLevenshtein(product, element);
        Double qgrams = getQgram(product, element);

        //@todo: pensar una formula y darle ponderacion a cada algoritmo
        double value = (soundex + metaphone + levenshtein + qgrams) / 4;

        //Vemos cual es el algoritmo que mejor dio
        String algo = "Soundex";
        Double algoVal = soundex;
        if(algoVal.compareTo(metaphone) < 0){
            algo = "Metaphone";
            algoVal = metaphone;
        }
        if(algoVal.compareTo(levenshtein) < 0){
            algo = "Levenshtein";
            algoVal = levenshtein;
        }
        if(algoVal.compareTo(qgrams) < 0){
            algo = "Qgrams";
            algoVal = qgrams;
        }

        return new ProductElem(element, value, algo, algoVal);
    }

    private Double getSoundex(String product, String element) throws EncoderException {
        Soundex s = new Soundex();
        Double value = (double) s.difference(product, element);
        return value / (double) 4;
    }

    private Double getMetaphone(String product, String element) {
        Metaphone m = new Metaphone();
        String prEncode = m.encode(product);
        String elEncode = m.encode(element);
        int lenMax = Math.max(prEncode.length(), elEncode.length());
        int lenMin = Math.min(prEncode.length(), elEncode.length());
        int coincidencias = 0;

        for (int i = 0; i < lenMin; i++) {
            if (prEncode.charAt(i) == elEncode.charAt(i)) {
                coincidencias++;
            }
        }

        return (double) (coincidencias / lenMax);
    }

    private Double getLevenshtein(String product, String element){
        LevenshteinDistance l = new LevenshteinDistance();
        int leven = l.apply(product, element);
        double coc = (double) (leven) / Math.max(product.length(), element.length());
        return (double) 1 - coc;
    }

    // Para esto uso la clase qgrams que hice yo porque la de la biblioteca no la entiendo
    private Double getQgram(String product, String element){
        Qgram q = new Qgram(3);
        return q.similarity(product, element);
    }

    private String clearSearch(String search){
        return search.toLowerCase()
                .replaceAll("á", "a")
                .replaceAll("é", "e")
                .replaceAll("í", "i")
                .replaceAll("ó", "o")
                .replaceAll("ú", "u")
                .replaceAll("ñ", "n")
                .replaceAll("\\s+", " ");
    }

    public static void main(String[] args) throws EncoderException {
        ProductSearch search = new ProductSearch();
        Set<ProductElem> set = search.searchProduct("TeNder     Plegable");
        System.out.println(set);
    }
}
