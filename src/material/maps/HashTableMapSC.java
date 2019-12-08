package material.maps;

import java.util.*;

/**
 * Separate chaining table implementation of hash tables. Note that all
 * "matching" is based on the equals method.
 *
 * @author A. Duarte, J. Vélez, J. Sánchez-Oro, JD. Quintana
 */
public class HashTableMapSC<K, V> implements Map<K, V> {
    //TODO: Practica 4 Ejercicio 2

    private class HashEntry<T, U> implements Entry<T, U> {

        protected T key;
        protected U value;

        public HashEntry(T k, U v) {
            throw new RuntimeException("Not yet implemented.");
        }

        @Override
        public U getValue() {
            return this.value;
        }

        @Override
        public T getKey() {
            return this.key;
        }

        public U setValue(U val) {
            U old = this.value;
            this.value = val;
            return old;
        }

        @Override
        public boolean equals(Object o)
        {
            if (o.getClass() != this.getClass()) {
                return false;
            }
            HashEntry<T, U> ent;
            try {
                ent = (HashEntry<T, U>) o;
            } catch (ClassCastException ex) {
                return false;
            }
            return (ent.getKey().equals(this.key))
                    && (ent.getValue().equals(this.value));
        }
    }

    private class HashTableMapIterator<T, U> implements Iterator<Entry<T, U>> {
        List<HashEntry<T, U>>[] array;
        ArrayDeque<HashEntry<T, U>> queue;
        int pos;

        public HashTableMapIterator(List<HashEntry<T, U>>[] map, int numElems)
        {
            array = map;
            pos = 0;
            queue = new ArrayDeque<HashEntry<T, U>>();

            if (numElems == 0)
            {
                this.pos = array.length;
            }
            else
            {
                this.pos = 0;
                goToNextElement(0);
            }
        }
        public void goToNextElement(int start)
        {
            this.pos = start;
            while ((this.pos < array.length) && ((array[this.pos] == null))) {
                this.pos++;
            }
        }


        @Override
        public boolean hasNext()
        {
            boolean moreElements = false;
            if(!this.queue.isEmpty())
            {
                return true;
            }
            else
            {
                if(pos < array.length)
                {
                    for(int i=pos; i<array.length; i++)
                    {
                        if(array[i] != null)
                        {
                            moreElements = true;
                            break;
                        }
                    }
                }
                return moreElements;
            }
        }

        @Override
        public Entry<T, U> next() {
            if(hasNext())
            {
                if(queue.isEmpty())
                {
                    int currentPos = this.pos;
                    if(array[currentPos] == null)
                    {
                        goToNextElement(this.pos+1);
                    }
                    queue.addAll(array[this.pos]);
                    pos++;
                }
                return queue.remove();
            }
            else
            {
                throw new IllegalStateException("The map has not more elements");
            }
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException("Not implemented.");
        }
    }

    private class HashTableMapKeyIterator<T, U> implements Iterator<T> {
        protected HashTableMapIterator<T, U> it;

        public HashTableMapKeyIterator(HashTableMapIterator<T, U> it)
        {
            this.it = it;
        }

        @Override
        public T next()
        {
            return it.next().getKey();
        }

        @Override
        public boolean hasNext()
        {
            return it.hasNext();
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException("Not implemented.");
        }
    }

    private class HashTableMapValueIterator<T, U> implements Iterator<U> {
        protected HashTableMapIterator<T, U> it;

        public HashTableMapValueIterator(HashTableMapIterator<T, U> it) {
            this.it = it;
        }

        @Override
        public U next()
        {
            return it.next().getValue();
        }

        @Override
        public boolean hasNext()
        {
            return it.hasNext();
        }

        @Override
        public void remove() {
            //NO HAY QUE IMPLEMENTARLO
            throw new UnsupportedOperationException("Not implemented.");
        }
    }

    protected int n = 0; //entries in the map
    protected int prime, capacity;
    protected List<HashEntry<K,V>>[] map;


    /**
     * Creates a hash table with prime factor 109345121 and capacity 1000.
     */
    public HashTableMapSC()
    {
        this.n = 0;
        this.capacity = 1000;
        this.prime = 109345121;
        this.map = new List[capacity];
    }

    /**
     * Creates a hash table with prime factor 109345121 and given capacity.
     *
     * @param cap initial capacity
     */
    public HashTableMapSC(int cap)
    {
        this.n = 0;
        this.capacity = cap;
        this.prime = 109345121;
        this.map = new List[cap];
    }

    /**
     * Creates a hash table with the given prime factor and capacity.
     *
     * @param p   prime number
     * @param cap initial capacity
     */
    public HashTableMapSC(int p, int cap)
    {
        this.n = 0;
        this.capacity = cap;
        this.prime = p;
        this.map = new List[cap];
    }

    /**
     * Hash function applying MAD method to default hash code.
     *
     * @param key Key
     * @return the hash value
     */
    protected int hashValue(K key)
    {
        int hashKey = key.hashCode();
        return hashKey % this.capacity;
    }


    @Override
    public int size()
    {
        return this.n;
    }

    @Override
    public boolean isEmpty()
    {
        return n == 0;
    }

    @Override
    public V get(K key) throws IllegalStateException
    {
        checkKey(key);
        int hashKey = hashValue(key);
        if(this.map[hashKey] == null)
            return null;
        for(HashEntry<K,V> e : map[hashKey])
        {
              if(e.getKey() == key)
                return e.getValue();
        }
        return null;
    }

    @Override
    public V put(K key, V value) throws IllegalStateException
    {
        if(n == capacity)
            throw new RuntimeException("Map full");
        int hashKey = hashValue(key);
        if(map[hashKey] == null)
        {
            HashEntry<K,V> h = new HashEntry<>(key, value);
            map[hashKey] = new LinkedList<>();
            map[hashKey].add(h);
            n++;
            return null;
        }
        else
        {
            V old;
            for(HashEntry<K,V> he : map[hashKey])
            {
                if(he.getKey().equals(key))
                {
                    old = he.getValue();
                    he.setValue(value);
                    n++;
                    return old;
                }
            }
            return null;
        }
    }

    @Override
    public V remove(K key) throws IllegalStateException
    {
        V output = null;
        int hashKey = hashValue(key);
        for(HashEntry<K,V> he : map[hashKey])
        {
            if(he.getKey().equals(key))
            {
                output = he.getValue();
                n--;
                map[hashKey].remove(he);
            }
        }
        return output;
    }


    @Override
    public Iterator<Entry<K, V>> iterator() {
        return new HashTableMapIterator((List[]) this.map, capacity);
    }

    @Override
    public Iterable<K> keys() {
        List<K> keys = new ArrayList<K>();
        HashTableMapIterator<K, V> ite = new HashTableMapIterator<K,V>((LinkedList<HashTableMapSC<K, V>.HashEntry<K, V>>[]) map, n);
        HashTableMapKeyIterator<K, V> it = new HashTableMapKeyIterator<K, V>(ite);
        while(it.hasNext())
        {
            keys.add(it.next());
        }

        return keys;
    }

    @Override
    public Iterable<V> values() {
        List<V> values = new ArrayList<V>();
        HashTableMapIterator<K, V> ite = new HashTableMapIterator<K,V>((LinkedList<HashTableMapSC<K, V>.HashEntry<K, V>>[]) map, n);
        HashTableMapValueIterator<K, V> it = new HashTableMapValueIterator<K, V>(ite);
        while(it.hasNext())
        {
            values.add(it.next());
        }

        return values;
    }

    @Override
    public Iterable<Entry<K, V>> entries() {
        List<Entry<K,V>> entries = new ArrayList<Entry<K,V>>();
        HashTableMapIterator<K, V> it = new HashTableMapIterator<K, V>((LinkedList<HashEntry<K, V>>[]) map, n);
        while(it.hasNext())
        {
            entries.add(it.next());
        }

        return entries;
    }

    /**
     * Determines whether a key is valid.
     *
     * @param k Key
     */
    protected void checkKey(K k) {
        if(k == null)
            throw new IllegalStateException("Invalid key: null.");
    }


    /**
     * Increase/reduce the size of the hash table and rehashes all the entries.
     */
    protected void rehash(int newCap) {
        capacity = newCap;
        HashTableMapSC<K, V> newMap = new HashTableMapSC<K, V>(newCap);

        for(int i=0; i<map.length; i++)
        {
            if(map[i] != null)
            {
                for(int j=0; j<map[i].size(); j++)
                {
                    HashEntry<K,V> e = map[i].get(j);
                    newMap.put(e.getKey(), e.getValue());
                }
            }
        }
        map = newMap.map;

    }
}
