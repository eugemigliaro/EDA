import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class QgramTest {

    @Test
    void similarity() {
        Qgram g = new Qgram(2);
        g.printTokens("alal");
        double value = g.similarity("salesal", "alale");
        System.out.println(value);
    }
}