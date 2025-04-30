import java.util.function.Function;

public class ClosedHashing<K, V> implements IndexParametricService<K, V> {
    final private int initialLookupSize= 10;
    private final static double loadFactor= 0.75;
    private int size;

    // estática. No crece. Espacio suficiente...
    @SuppressWarnings({"unchecked"})
    private Slot<K,V>[] lookup = (Slot<K,V>[]) new Slot[initialLookupSize];

    private Function<? super K, Integer> prehash;

    public ClosedHashing( Function<? super K, Integer> mappingFn) {
        if (mappingFn == null)
            throw new RuntimeException("fn not provided");

        prehash = mappingFn;
    }

    // ajuste al tamaño de la tabla
    private int hash(K key) {
        if (key == null)
            throw new IllegalArgumentException("key cannot be null");

        return prehash.apply(key) % lookup.length;
    }

    private void resize(){
        Slot<K, V>[] newLookup = (Slot<K, V>[]) new Slot[lookup.length * 2];
        for (Slot<K, V> entry : lookup) {
            if (entry != null && !entry.isDeleted) {
                int newIndex = prehash.apply(entry.key) % newLookup.length;

                // Find the next available slot using linear probing
                while (newLookup[newIndex] != null) {
                    newIndex = (newIndex + 1) % newLookup.length;
                }

                newLookup[newIndex] = entry;
            }
        }
        lookup = newLookup;
    }

    public void insertOrUpdate(K key, V data) {
        if (key == null || data == null) {
            String msg= String.format("inserting or updating (%s,%s). ", key, data);
            if (key==null)
                msg+= "Key cannot be null. ";

            if (data==null)
                msg+= "Data cannot be null.";

            throw new IllegalArgumentException(msg);
        }

        if (size >= lookup.length * loadFactor)
            resize();

        int i = hash(key);
        int firstDeletedIndex = -1;

        while (lookup[i] != null && !lookup[i].key.equals(key)) {
            if (firstDeletedIndex == -1 && lookup[i].isDeleted) {
                firstDeletedIndex = i;
            }
            i = (i + 1) % lookup.length;
        }

        if (lookup[i] == null) {
            // If we found a deleted slot during our search, use that instead
            if (firstDeletedIndex != -1) {
                lookup[firstDeletedIndex] = new Slot<>(key, data);
            } else {
                lookup[i] = new Slot<>(key, data);
            }
            size++;
        } else {
            lookup[i].value = data;
        }
    }

    // find or get
    public V find(K key) {
        if (key == null)
            return null;

        int i = hash(key);
        while (lookup[i] != null) {
            if (!lookup[i].isDeleted && lookup[i].key.equals(key)) {
                return lookup[i].value;
            }
            i = (i + 1) % lookup.length;
        }

        return null;
    }

    public boolean remove(K key) {
        if (key == null)
            return false;

        int i = hash(key);
        while (lookup[i] != null) {
            if (!lookup[i].isDeleted && lookup[i].key.equals(key)) {
                if(lookup[(i + 1) % lookup.length] == null){
                    lookup[i] = null;
                }else{
                    lookup[i].isDeleted = true;
                }
                size--;
                return true;
            }
            i = (i + 1) % lookup.length;
        }

        return false;
    }


    public void dump()  {
        for(int rec = 0; rec < lookup.length; rec++) {
            if (lookup[rec] == null)
                System.out.println(String.format("slot %d is empty", rec));
            else
                System.out.println(String.format("slot %d contains %s",rec, lookup[rec]));
        }
    }


    public int size() {
        return size;
    }



    static private final class Slot<K, V>	{
        private final K key;
        private V value;
        boolean isDeleted;

        private Slot(K theKey, V theValue){
            key = theKey;
            value = theValue;
            isDeleted = false;
        }


        public String toString() {
            return String.format("(key=%s, value=%s)", key, value );
        }
    }


    public static void main(String[] args) {
        ClosedHashing<Integer, String> myHash= new ClosedHashing<>(f->f);
        myHash.insertOrUpdate(55, "Ana");
        myHash.insertOrUpdate(44, "Juan");
        myHash.insertOrUpdate(18, "Paula");
        myHash.insertOrUpdate(19, "Lucas");
        myHash.insertOrUpdate(21, "Sol");
        myHash.dump();

    }

/*
	public static void main(String[] args) {
		ClosedHashing<Integer, String> myHash= new ClosedHashing<>(f->f);
		myHash.insertOrUpdate(55, "Ana");
		myHash.insertOrUpdate(29, "Victor");
		myHash.insertOrUpdate(25, "Tomas");
		myHash.insertOrUpdate(19, "Lucas");
		myHash.insertOrUpdate(21, "Sol");
		myHash.dump();
	}
*/
}
