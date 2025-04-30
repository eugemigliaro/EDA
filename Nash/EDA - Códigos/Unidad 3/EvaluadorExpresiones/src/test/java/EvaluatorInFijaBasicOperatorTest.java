import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import static org.junit.jupiter.api.Assertions.*;

class EvaluatorInFijaBasicOperatorTest {

    @Test
    void testPrivate() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        String input = "3 + 4 * 2";
        InputStream inputstream = new ByteArrayInputStream(input.getBytes());
        System.setIn(inputstream);

        //Testeo de metodos privados
        String expected = "3 4 2 * + ";
        EvaluatorInFijaBasicOperator e = new EvaluatorInFijaBasicOperator();
        Method myMethod = EvaluatorInFijaBasicOperator.class.getDeclaredMethod("infijaToPostfija");
        myMethod.setAccessible(true);
        String result = (String) myMethod.invoke(e);
        assertEquals(expected, result);
    }
}