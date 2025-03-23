package ej4;

public class Levenshtein {
    public static int distance(String s1, String s2) {
        char[] s1Array = s1.toUpperCase().toCharArray();
        char[] s2Array = s2.toUpperCase().toCharArray();

        int[][] matrix = new int[s1Array.length + 1][s2Array.length + 1];
        for (int i = 0; i <= s1Array.length; i++) {
            matrix[i][0] = i;
        }
        for (int j = 0; j <= s2Array.length; j++) {
            matrix[0][j] = j;
        }
        for (int i = 1; i <= s1Array.length; i++) {
            for (int j = 1; j <= s2Array.length; j++) {
                if (s1Array[i - 1] == s2Array[j - 1]) {
                    matrix[i][j] = matrix[i - 1][j - 1];
                } else {
                    matrix[i][j] = Math.min(matrix[i - 1][j] + 1, matrix[i][j - 1] + 1);
                    matrix[i][j] = Math.min(matrix[i][j], matrix[i - 1][j - 1] + 1);
                }
            }
        }

        return matrix[s1Array.length][s2Array.length];
    }

    public static double normalizedDistance(String s1, String s2) {
        return (double) distance(s1, s2) / (Math.max(s1.length(), s2.length()) == 0 ? 1 : Math.max(s1.length(), s2.length()));
    }
}
