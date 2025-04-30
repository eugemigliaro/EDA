package P1C12024.ej2;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.Arrays;

class IndexWithDuplicatesTest {
    @Test
    void testCase1() {
        // Caso de Uso 1 - Índices Ordenados sin Elementos Duplicados
        IndexWithDuplicates index1 = new IndexWithDuplicates();
        index1.initialize(new int[] {1, 3, 5, 7});
        IndexWithDuplicates index2 = new IndexWithDuplicates();
        index2.initialize(new int[] {2, 4, 6, 8});
        index1.merge(index2);

        // Resultado esperado: [1, 2, 3, 4, 5, 6, 7, 8]
        int[] expected = {1, 2, 3, 4, 5, 6, 7, 8};
        int[] actual = Arrays.copyOf(index1.getIndexedData(), 8);
        assertArrayEquals(expected, actual);
    }

    @Test
    void testCase2() {
        // Caso de Uso 2 - Índices Ordenados con Elementos Duplicados
        IndexWithDuplicates index1 = new IndexWithDuplicates();
        index1.initialize(new int[] {1, 1, 3, 5, 7});
        IndexWithDuplicates index2 = new IndexWithDuplicates();
        index2.initialize(new int[] {2, 4, 4, 6, 8});
        index1.merge(index2);

        // Resultado esperado: [1, 1, 2, 3, 4, 4, 5, 6, 7, 8]
        int[] expected = {1, 1, 2, 3, 4, 4, 5, 6, 7, 8};
        int[] actual = Arrays.copyOf(index1.getIndexedData(), 10);
        assertArrayEquals(expected, actual);
    }

    @Test
    void testCase3() {
        // Caso de Uso 3 - Índices Ordenados con Diferentes Cantidades de Elementos
        IndexWithDuplicates index1 = new IndexWithDuplicates();
        index1.initialize(new int[] {1, 3, 5});
        IndexWithDuplicates index2 = new IndexWithDuplicates();
        index2.initialize(new int[] {2, 4, 6, 8, 10});
        index1.merge(index2);

        // Resultado esperado: [1, 2, 3, 4, 5, 6, 8, 10]
        int[] expected = {1, 2, 3, 4, 5, 6, 8, 10};
        int[] actual = Arrays.copyOf(index1.getIndexedData(), 8);
        assertArrayEquals(expected, actual);
    }
}
