package ej1;

public class ExactSearch {
    public static int indexOf(char[] query, char[] target){
        int i = 0, j = 0;
        for(i = 0; i<target.length && j < query.length; i++){
            j = 0;
            for(int k = i; k<target.length && j<query.length && query[j] == target[k]; k++){
                j++;
            }
        }

        if(j == query.length){
            return i;
        }

        return -1;
    }
}
