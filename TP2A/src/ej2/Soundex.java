package ej2;

public class Soundex {
    private static final int MAX_CODE_LENGTH = 4;
    private final char[] IN;
    private char[] OUT = {'0', '0', '0', '0'};

    public Soundex(String input) {
        this.IN = clean(input);
        encode();
    }

    private char[] clean(String input) {
        if(input == null) return new char[0];
        char[] in = input.toUpperCase().toCharArray();
        /*System.out.println(in);
        System.out.println("---");*/
        int j = 0;
        for (int i = 0; i < in.length; i++) {
            if ((in[i] >= 'A' && in[i] <= 'Z')) {
                in[j] = in[i];
                j++;
            }
        }
        char[] cleanIn = new char[j];
        for (int i = 0; i < j; i++) {
            cleanIn[i] = in[i];
        }

        /*System.out.println(cleanIn);
        System.out.println("---");*/

        return cleanIn;
    }

    public char[] encode(){
        if(IN.length == 0) {OUT = new char[0];
            return OUT;}

        OUT[0] = IN[0];
        int index = 1;
        char current = getMapping(IN[0]), last = getMapping(IN[0]);

        for (int i = 1; i < IN.length && index < MAX_CODE_LENGTH; i++, last = current) {
            current = getMapping(IN[i]);
            if (current != last && current != '0') {
                OUT[index++] = current;
            }
        }
        while (index < MAX_CODE_LENGTH) {
            OUT[index++] = '0';
        }
        return OUT;
    }

    public char getMapping(char c) {
        switch (c) {
            case 'B': case 'F': case 'P': case 'V':
                return '1';
            case 'C': case 'G': case 'J': case 'K': case 'Q': case 'S': case 'X': case 'Z':
                return '2';
            case 'D': case 'T':
                return '3';
            case 'L':
                return '4';
            case 'M': case 'N':
                return '5';
            case 'R':
                return '6';
            default:
                return '0';
        }
    }

    public String toString() {
        return new String(OUT);
    }

    public static double similarity(String s1, String s2) {
        Soundex soundex1 = new Soundex(s1);
        Soundex soundex2 = new Soundex(s2);
        char[] s1Code = soundex1.encode();
        char[] s2Code = soundex2.encode();
        int count = 0;
        for (int i = 0; i < s1Code.length; i++) {
            if (s1Code[i] == s2Code[i]) count++;
        }
        return (double) count / MAX_CODE_LENGTH;
    }
}
