import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;

import static org.junit.jupiter.api.Assertions.assertEquals;

class IndexWithDuplicatesTest {

    //TERMINAR TESTS

    private IndexService myIndex;

    @BeforeEach
    void initIndex() {
        IndexService myIndex = new IndexWithDuplicates();
    }

    @Test
    void search() {
        try
        {
            myIndex.initialize( new int[] {100, 50, 30, 50, 80, 100, 100, 30} );
        }
        catch(Exception e)
        {
        }
        // el índice posee [30, 30, 50, 50, 80, 100, 100, 100]

    }

    @Test
    void occurrences() {
        assertEquals(myIndex.occurrences( 10 ), 0, "No deberia encontrar nada");
        myIndex.insert( 80 );  // almacena [80]
        myIndex.insert( 20 );  // almacena [20, 80]
        myIndex.insert( 80 );  // almacena [20, 80, 80]
        assertEquals(myIndex.occurrences( 80 ), 2, "Deberia encontrar los dos 80");
    }

    @Test
    void range() {
    }

    @Test
    void sortedPrint() {
    }

    @Test
    void getMax() {
    }

    @Test
    void getMin() {
    }
}