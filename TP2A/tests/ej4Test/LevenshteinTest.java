package ej4Test;

import ej4.Levenshtein;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class LevenshteinTest {

    @Test
    public void testDistance() {
        int distance = Levenshtein.distance("kitten", "sitting");
        assertEquals(3, distance);
    }

    @Test
    public void testNormalizedDistance() {
        double normalizedDistance = Levenshtein.normalizedDistance("kitten", "sitting");
        assertEquals(0.42857142857142855, normalizedDistance);
    }

    @Test
    public void testDistanceWithEmptyStrings() {
        int distance = Levenshtein.distance("", "");
        assertEquals(0, distance);
    }

    @Test
    public void testDistanceWithOneEmptyString() {
        int distance = Levenshtein.distance("abc", "");
        assertEquals(3, distance);
    }

    @Test
    public void testDistanceWithSameStrings() {
        int distance = Levenshtein.distance("same", "same");
        assertEquals(0, distance);
    }

    @Test
    public void testNormalizedDistanceWithEmptyStrings() {
        double normalizedDistance = Levenshtein.normalizedDistance("", "");
        assertEquals(0.0, normalizedDistance);
    }

    @Test
    public void testNormalizedDistanceWithOneEmptyString() {
        double normalizedDistance = Levenshtein.normalizedDistance("abc", "");
        assertEquals(1.0, normalizedDistance);
    }

    @Test
    public void testNormalizedDistanceWithSameStrings() {
        double normalizedDistance = Levenshtein.normalizedDistance("same", "same");
        assertEquals(0.0, normalizedDistance);
    }
}
