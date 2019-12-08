package material.maps;
/**
 * @param <K> The hey
 * @param <V> The stored value
     */
public class HashTableMapQP<K, V> extends AbstractHashTableMap<K, V> {

    public HashTableMapQP(int size) {
        super(size);
    }

    public HashTableMapQP() {
        super();
    }

    public HashTableMapQP(int p, int cap) {
        super(p,cap);
    }

    @Override
    protected int offset(K key, int i) {
       int hashKey = hashValue(key);
       int c1 = 5, c2 = 12;
       int output = (hashKey + c1 * i + c2 +(i^2)) % this.capacity;
       return output;
    }

}
