public class DamerauLevenshtein {
    public static int[][] matrix;

    public static void printMatrix(int[][] m, int a, int b){
        for (int i = 0; i < b; i++) {
            String line = "";
            for (int j = 0; j < a; j++) {
                line = line.concat(m[i][j] + " ");
            }
            System.out.println(line);
        }
        System.out.println("matrix printed");
    }

    public static int distance(String a, String b){
        matrix = new int[b.length() + 1][a.length() + 1];
        //System.out.println(a + " " + b);

        for (int i=0; i <= a.length(); i++) {
            matrix[0][i] = i;
        }

        for (int j = 0; j <= b.length(); j++) {
            matrix[j][0] = j;
        }

        //printMatrix(matrix, a.length(), b.length());

        for (int j = 1; j <= b.length() ; j++) {
            for (int i = 1; i <= a.length(); i++) {
                int o1 = matrix[j-1][i] + 1;
                int o2 = matrix[j][i-1] + 1;
                int o3 = matrix[j-1][i-1] + ((a.charAt(i-1) == b.charAt(j-1)) ? 0 : 1);

                if(i>1 && j>1 && a.charAt(i)==a.charAt(j-1) && a.charAt(i-1)==a.charAt(j)){
                    int o4 =matrix[i-2][j-2] + ((a.charAt(i-1) == b.charAt(j-1)) ? 0 : 1);
                    matrix[j][i] = Math.min(Math.min(Math.min(o1, o2), o3), o4);
                }
                else {
                    matrix[j][i] = Math.min(Math.min(o1, o2), o3);
                }
            }
        }
        return matrix[b.length()][a.length()];
    }
}