import java.util.ArrayList;
import java.util.List;

public class Levenshtein {

    public static int distance(String str1, String str2){
        str1 =  str1.toUpperCase();
        str2 =  str2.toUpperCase();
        int len1 = str1.length();
        int len2 = str2.length();

        int[][] mat = new int[len1 + 1][len2 + 1];

        for(int i = 0; i < len1 + 1; i++){
            mat[i][0] = i;
        }
        for(int i = 0; i < len2 + 1; i++){
            mat[0][i] = i;
        }

        for(int i = 1; i < len1 + 1; i ++){
            for(int j = 1; j < len2 + 1; j++){
                mat[i][j] = Math.min(mat[i-1][j-1] + (str1.charAt(i-1) == str2.charAt(j-1) ? 0 : 1),
                        Math.min(mat[i][j-1], mat[i-1][j]) + 1);
            }
        }

        return mat[len1][len2];
    }

    public static int normalizedSimilarity(String s1, String s2){
        return 1 - (distance(s1, s2)/Math.max(s1.length(), s2.length()));
    }

    public static List<String> findOperations(int[][] matrix, String str1, String str2) {
        List<String> operations = new ArrayList<>();
        int i = str1.length();
        int j = str2.length();

        while (i > 0 || j > 0) {
            if (i > 0 && j > 0 && str1.charAt(i - 1) == str2.charAt(j - 1)) {
                i--;
                j--;
            } else if (i > 0 && j > 0 && matrix[i][j] == matrix[i - 1][j - 1] + 1) {
                operations.add("Sustitución de " + str1.charAt(i - 1) + " por " + str2.charAt(j - 1));
                i--;
                j--;
            } else if (i > 0 && matrix[i][j] == matrix[i - 1][j] + 1) {
                operations.add("Eliminación de " + str1.charAt(i - 1));
                i--;
            } else if (j > 0 && matrix[i][j] == matrix[i][j - 1] + 1) {
                operations.add("Inserción de " + str2.charAt(j - 1));
                j--;
            }
        }

        return operations;
    }

    /**
     * Cumple espacio temporal n. Va haciendo las filas y la ultima
     * calculada la mueve a la primera para preparar el proximo calculo.
     * Reserva espacio segun la longitud del string mas chico.
     */
    public static int distance_space_n(String str1, String str2){
        int len1 = str1.length(), len2 = str2.length();
        if(len1 > len2){
            int iaux = len1;
            len1 = len2;
            len2 = iaux;
            String aux = str1;
            str1 = str2;
            str2 = aux;
        }
        int mat[][] = new int[2][len1+1];
        for(int i = 0; i < len1+1; i++) {
            mat[0][i] = i;
        }

        for(int j = 1; j < len1+1; j++) {
            mat[1][0] = j-1;
            for(int i = 1; i < len1+1; i++) {
                mat[1][i] = Math.min(mat[0][i-1] + (str1.charAt(i-1) == str2.charAt(j-1) ? 0 : 1), Math.min(mat[1][i-1], mat[0][i]) + 1);
            }
            for(int i = 0; i < len1+1; i++) {
                mat[0][i] = mat[1][i];
            }
        }
        return mat[1][len1] + len2 - len1;
    }

    public static void main(String[] args) {
        System.out.println(Levenshtein.distance("big data", "bigdata"));
    }
}
