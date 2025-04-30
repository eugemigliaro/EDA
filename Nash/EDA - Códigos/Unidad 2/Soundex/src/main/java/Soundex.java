public class Soundex {

    private static final int[] arr = new int[] {0, 1, 2, 3, 0, 1, 2, 0, 0, 2, 2, 4, 5, 5, 0, 1, 2, 6, 2, 3, 0, 1, 0, 2, 0, 2};

    public static String representation(String s){
        s = s.toUpperCase();
        char[] in = s.toCharArray();
        char[] out = {'0', '0', '0', '0'};
        out[0] = in[0];
        int count = 1;
        int current;
        int last = getMapping(in[0]);
        for(int i = 1; i < in.length && count < 4; i++){
            char iter = in[i];
            if(iter >= 'A' && iter <= 'Z'){
                current = getMapping(iter);
                if(current != 0 && current != last){
                    out[count++] = toChar(current);
                }
                last = current;
            }
        }
        return new String(out);
    }

    public static int getMapping(char letter) {

        int pos = letter - 'A';
        return arr[pos];

    }

    public static char toChar(int curr){
        return (char) (curr + '0');
    }

}
