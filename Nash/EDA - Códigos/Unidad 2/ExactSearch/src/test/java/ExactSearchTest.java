import org.junit.jupiter.api.Test;

class ExactSearchTest {

    @Test
    void indexOf() {
        char[] target = "abracadabra".toCharArray();
        char[] query = "ra".toCharArray();
        System.out.println(ExactSearch.indexOf(query, target));  //2

        query = "abra".toCharArray();
        System.out.println(ExactSearch.indexOf(query, target));  //0

        query = "aaaa".toCharArray();
        System.out.println(ExactSearch.indexOf(query, target));  //-1

        query = "ra".toCharArray();
        System.out.println(ExactSearch.indexOfv2(query, target));  //2

        query = "abra".toCharArray();
        System.out.println(ExactSearch.indexOfv2(query, target));  //0

        query = "aaaa".toCharArray();
        System.out.println(ExactSearch.indexOfv2(query, target));  //-1
    }
}