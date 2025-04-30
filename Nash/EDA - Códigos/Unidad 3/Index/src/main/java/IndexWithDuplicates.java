import java.util.Arrays;

public class IndexWithDuplicates implements IndexService {

    private int[] indexes = new int[BLOCK];
    private int size = 0;
    private static int BLOCK = 10;

    @Override
    public void initialize(int[] elements) {
        //@TODO: No usar sort, cambiarlo por el insert.
        Arrays.sort(elements);
        this.indexes = elements;
        size = elements.length;
    }


    @Override
    public boolean search(int key) {
        int pos = getClosestPosition(key);
        return indexes[pos] == key;
    }


    @Override
    public void insert(int key) {
        if (size >= indexes.length) {
            indexes = Arrays.copyOf(indexes, indexes.length + BLOCK);
        }
        int pos = getClosestPosition(key);
        for(int i = size; i > pos; i--) {
            indexes[i] = indexes[i-1];
        }
        size++;
        indexes[pos] = key;
        System.out.print("[ ");
        for(int i = 0; i<size; i++) System.out.print(indexes[i] + " ");
        System.out.println("]");
    }



    @Override
    public void delete(int key) {
        if(search(key)) {
            int pos = getClosestPosition(key);
            size--;
            for(int i = pos; i<size; i++) {
                indexes[i] = indexes[i+1];
            }
        }
    }

    @Override
    public int occurrences(int key) {
        return getClosestPosition(key+1) - getClosestPosition(key-1);
    }

    private int getClosestPosition(int key) {
        int l = 0, r = size;
        while(l < r) {
            int mid = l + (r - l) / 2;
            if (indexes[mid] >= key) {
                r = mid;
            } else {
                l = mid+1;
            }
        }
        return l;
    }

    public int[] range(int leftKey, int rightKey, boolean leftIncluded, boolean rightIncluded){
        //int[] tgt = new int[BLOCK];

        int posL = leftIncluded ? getClosestPosition(leftKey) : getClosestPosition(leftKey + 1);
        int posR = rightIncluded ? getClosestPosition(rightKey) : getClosestPosition(rightKey - 1);

        if(posR - posL < 1){
            return new int[0];
        }

        int[] tgt= Arrays.copyOfRange(indexes, posL, posR);
        //System.arraycopy(indexes, posL, tgt , 0 ,  posR - posL);

        System.out.print("[ ");
        for(int i = 0; i< tgt.length; i++) System.out.print(tgt[i] + " ");
        System.out.println("]");

        return tgt;
    }

    public void sortedPrint(){
        System.out.print("[ ");
        for(int i = 0; i< size; i++) System.out.print(indexes[i] + " ");
        System.out.println("]");
    }

    public int getMax(){
        if(size < 1)
            throw new RuntimeException("No indexes saved");
        return indexes[size - 1];
    }

    public int getMin(){
        if(size < 1)
            throw new RuntimeException("No indexes saved");
        return indexes[0];
    }

    /*
    public static void main(String[] args) {
        IndexService  myIndex= new IndexWithDuplicates();

        System.out.println (myIndex.occurrences( 10 ) );  // se obtiene 0

        myIndex.delete( 10 );  // ignora

        System.out.println (myIndex.search( 10 ) );  // se obtiene false

        myIndex.insert( 80 );  // almacena [80]

        myIndex.insert( 20 );  // almacena [20, 80]

        myIndex.insert( 80 );  // almacena [20, 80, 80]

        try
        {
            myIndex.initialize( new int[] {100, 50, 30, 50, 80, 100, 100, 30} );
        }
        catch(Exception e)
        {
        }

        // el índice posee [30, 30, 50, 50, 80, 100, 100, 100]
        System.out.println( myIndex.search( 20 ));   // se obtiene false

        System.out.println( myIndex.search( 80 ));   // se obtiene true

        System.out.println (myIndex.occurrences( 50 ) );  // se obtiene 2

        myIndex.delete( 50 );

        System.out.println (myIndex.occurrences( 50 ) );  // se obtiene 1

        IndexService  myIndex2= new IndexWithDuplicates();
        try
        {
            myIndex2.initialize( new int[] {100, 50, 30, 50, 80, 100, 100, 30} ); // guarda 30 30 50 50 80 100 100 100
        }
        catch(Exception e)
        {
        }

        int[] rta= myIndex2.range(50, 100, false, false); // [80]

        rta= myIndex2.range(30, 50, true, false); // [30, 30]

        rta= myIndex2.range(45, 100, false, false); // [50, 50, 80]

        rta= myIndex2.range(45, 100, true, false); // [50, 50, 80]

        rta= myIndex2.range(10, 50, true, false); // [30, 30]

        rta= myIndex2.range(10, 20, false, false); // []
        System.out.println(rta.length);

        myIndex2.sortedPrint(); // [ 30 30 50 50 80 100 100 100 ]

        int max = 0;
        try{
            max = myIndex2.getMax();
        }
        catch(Exception e){
            System.out.println(e);
        }
        System.out.println(max); // 100

        int min = 0;
        try{
            min = myIndex2.getMin();
        }
        catch(Exception e){
            System.out.println(e);
        }
        System.out.println(min); // 30

        IndexService  myIndex3 = new IndexWithDuplicates();

        max = 0;
        try{
            max = myIndex3.getMax();
        }
        catch(Exception e){
            System.out.println(e);  //No indexes saved
        }
        System.out.println(max); // 0

        min = 0;
        try{
            min = myIndex3.getMin();
        }
        catch(Exception e){
            System.out.println(e);  //No indexes saved
        }
        System.out.println(min); // 0
    }
    */
}
