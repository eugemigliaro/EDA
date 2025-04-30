import java.util.HashMap;
import java.util.Map;

public class Qgram {

    private final int q;

    public Qgram(int q){
        if(q <= 0){
            throw new IllegalArgumentException("Q debe ser positivo");
        }
        this.q = q;
    }

    public double similarity(String str1, String str2){

        Map<String, Integer> qWord1 = getTokens(str1);
        Map<String, Integer> qWord2 = getTokens(str2);
        int removed = 0;

        for(Map.Entry<String, Integer> entry1 : qWord1.entrySet()) {
            do {
                if (qWord2.getOrDefault(entry1.getKey(), 0) != 0) {
                    qWord2.merge(entry1.getKey(), 1, (oldValue, newValue) -> oldValue - 1);
                    removed++;
                    System.out.println(entry1.getKey());
                }
                entry1.setValue(entry1.getValue() - 1);
            } while (entry1.getValue() != 0);
        }
        return (double)2*removed/(str1.length()+2*(q-1)+str2.length());
    }

    public void printTokens(String str){
        Map<String, Integer> tokens = getTokens(str);
        for(Map.Entry<String, Integer> entry : tokens.entrySet()){
            System.out.println("%s  %d".formatted(entry.getKey(), entry.getValue()));
        }
    }

    private Map<String, Integer> getTokens(String str) {

        StringBuilder s = new StringBuilder();
        s.append("#".repeat(q - 1)).append(str).append("#".repeat(q - 1));
        Map<String, Integer> out = new HashMap<>();
        for(int i = q; i <= s.length(); i++){
            String subString = s.subSequence(i-q, i).toString();
            out.merge(subString, 1, (oldValue, newValue) -> oldValue + 1);
        }
        return out;
    }
}
