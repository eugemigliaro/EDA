import java.util.LinkedList;
import java.util.function.Function;

public class OpenHashing<K, V> implements IndexParametricService<K, V>{
    final private int initialLookupSize= 10;
    private final static double loadFactor= 0.75;
    private int size;

    private LinkedList<Slot<K,V>>[] lookup = (LinkedList<Slot<K,V>>[]) new LinkedList[initialLookupSize];

    private Function<? super K, Integer> prehash;

    public OpenHashing( Function<? super K, Integer> mappingFn) {
        if (mappingFn == null)
            throw new RuntimeException("fn not provided");

        prehash= mappingFn;
    }

    private int hash(K key) {
        if (key == null)
            throw new IllegalArgumentException("key cannot be null");

        return prehash.apply(key) % lookup.length;
    }

    @Override
    public void insertOrUpdate(K key, V data) {
        if (key == null || data == null) {
            String msg= String.format("inserting or updating (%s,%s). ", key, data);
            if (key==null)
                msg+= "Key cannot be null. ";
            if (data==null)
                msg+= "Data cannot be null.";

            throw new IllegalArgumentException(msg);
        }

        if(size >= lookup.length * loadFactor){
            resize();
        }

        int i = hash(key);

        if(lookup[i] == null){
            lookup[i] = new LinkedList<>();
        }else{
            for(Slot<K,V> slot : lookup[i]){
                if(slot.key.equals(key)){
                    slot.value = data;
                    return;
                }
            }
        }
        lookup[i].add(new Slot<>(key, data));
        size++;
    }

    private void resize(){
        LinkedList<Slot<K,V>>[] newLookup = (LinkedList<Slot<K,V>>[]) new LinkedList[lookup.length * 2];
        for (LinkedList<Slot<K,V>> entry : lookup) {
            if (entry != null) {
                for (Slot<K, V> slot : entry) {
                    int newIndex = prehash.apply(slot.key) % newLookup.length;
                    if (newLookup[newIndex] == null) {
                        newLookup[newIndex] = new LinkedList<>();
                    }
                    newLookup[newIndex].add(slot);
                }
            }
        }
    }

    @Override
    public V find(K key) {
        if (key == null)
            return null;

        int i = hash(key);

        if(lookup[i] != null) {
            for (Slot<K, V> slot : lookup[i]) {
                if (slot.key.equals(key)) {
                    return slot.value;
                }
            }
        }

        return null;
    }

    @Override
    public boolean remove(K key) {
        if(key == null)
            return false;

        int i = hash(key);

        if(lookup[i] != null){
            for(Slot<K, V> slot : lookup[i]){
                if(slot.key.equals(key)){
                    lookup[i].remove(slot);
                    size--;
                    if(lookup[i].isEmpty()){
                        lookup[i] = null;
                    }
                    return true;
                }
            }
        }

        return false;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public void dump() {
        for(int rec = 0; rec < lookup.length; rec++) {
            if (lookup[rec] == null)
                System.out.println(String.format("slot %d is empty", rec));
            else
                System.out.printf("slot %d contains %s",rec, lookup[rec]);
        }
    }

    static private final class Slot<K, V>	{
        private final K key;
        private V value;

        private Slot(K theKey, V theValue){
            key = theKey;
            value = theValue;
        }

        public String toString() {
            return String.format("(key=%s, value=%s)", key, value );
        }
    }
}
