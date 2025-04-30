import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import static org.junit.jupiter.api.Assertions.*;

class EvaluatorPostijaTest {

    @Test
    public void test3() {
        // invecto entrada estandard
        String input = "-9  -1  - 10   2   *   /   1   5  -  2  -3  /   /  *";
        InputStream inputstream = new ByteArrayInputStream(input.getBytes());
        System.setIn(inputstream);

        Double rta = new EvaluatorPostija().evaluate();
        assertEquals(-2.4, rta);
    }

}