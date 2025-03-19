package ej2Test;

import ej2.Soundex;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class SoundexTest {

    @Test
    public void testThreshold() {
        Soundex soundex = new Soundex("threshold");
        assertEquals("T624", soundex.toString());
    }

    @Test
    public void testHold() {
        Soundex soundex = new Soundex("hold");
        assertEquals("H430", soundex.toString());
    }

    @Test
    public void testPhone() {
        Soundex soundex = new Soundex("phone");
        assertEquals("P500", soundex.toString());
    }

    @Test
    public void testFound() {
        Soundex soundex = new Soundex("foun");
        assertEquals("F500", soundex.toString());
    }

    @Test
    public void testEmptyString() {
        Soundex soundex = new Soundex("");
        assertEquals("", soundex.toString());
    }

    @Test
    public void testNullInput() {
        // Este test fallará si no manejamos correctamente el caso null
        // en el constructor, por lo que implementaríamos una verificación adicional
        try {
            Soundex soundex = new Soundex(null);
            fail("Debería lanzar NullPointerException");
        } catch (NullPointerException e) {
            // Esperado
        }
    }

    @Test
    public void testCaseSensitivity() {
        // Verificar que la codificación es insensible a mayúsculas/minúsculas
        assertEquals(new Soundex("THRESHOLD").toString(), new Soundex("threshold").toString());
    }

    @Test
    public void testSimilarSounds() {
        // Verificar palabras que suenan similar
        assertEquals(new Soundex("Robert").toString(), new Soundex("Rupert").toString());
        assertEquals(new Soundex("Smith").toString(), new Soundex("Smyth").toString());
    }
}