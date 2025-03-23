package ej2;

public class KMP{
    public static int[] nextComputation(char[] query){
        int[] next = new int[query.length];
        int border = 0;
        next[0] = 0;
        for(int i = 1; i < query.length; i++){
            while(border > 0 && query[i] != query[border]){
                border = next[border - 1];
            }
            if(query[i] == query[border]){
                border++;
            }
            next[i] = border;
        }

        return next;
    }

    public static int indexOf(char[] query, char[] target){
        int[] next = nextComputation(query);
        int pquery = 0, ptarget = 0;
        int ans = -1;
        while(pquery < query.length && ptarget < target.length){
            if(query[pquery] == target[ptarget]){
                pquery++;
                ptarget++;
            } else if(pquery == 0){
                ptarget++;
            } else {
                pquery = next[pquery - 1];
            }
            if(pquery == query.length){
                ans = ptarget - pquery;
            }
        }

        return ans;
    }
}