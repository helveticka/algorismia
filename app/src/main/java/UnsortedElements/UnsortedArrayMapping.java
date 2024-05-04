package UnsortedElements;

import java.util.Iterator;


/**
 * Implementation of an unsorted mapping using arrays.
 *
 * @param <K> Generic key.
 * @param <V> Generic value.
 */
public class UnsortedArrayMapping<K, V>
{

    private final K[] keys;
    private final V[] values;
    private int n;


    /**
     * Constructor of the unsorted mapping.
     * O(1).
     *
     * @param max Initial capacity of the set.
     */
    public UnsortedArrayMapping(int max)
    {
        keys = (K[]) new Object[max];
        values = (V[]) new Object[max];
        n = 0;
    }


    /**
     * Finds the value of a given key.
     * O(n).
     *
     * @param key Generic key to search.
     * @return The associated value of the key, null otherwise.
     */
    public V get(K key)
    {
        int i = 0;
        while (i < n)
        {
            if (keys[i].equals(key)) return values[i];
            i++;
        }

        return null;
    }


    /**
     * Inserts a value with it's key. If the key has already a value associated,
     * it replaces de previous one.
     * O(n).
     *
     * @param key Generic key to search.
     * @param value Generic value to insert.
     * @return The previous associated value of the key, null otherwise.
     */
    public V put(K key, V value)
    {
        int i = 0;
        boolean found = false;

        while (!found && (i < n))
        {
            found = keys[i].equals(key);
            i++;
        }

        if (found)
        {
            V res = values[i-1];
            values[i-1] = value;
            return res;
        }

        keys[i] = key;
        values[i] = value;
        n++;
        return null;
    }


    /**
     * Removes the given key and it's value.
     * O(n).
     *
     * @param key Generic key to search.
     * @return The previous associated value of the key, null otherwise.
     */
    public V remove(K key)
    {
        int i = 0;
        boolean found = false;

        while (!found && (i < n))
        {
            found = keys[i].equals(key);
            i++;
        }

        if (found)
        {
            n--;
            V res = values[i-1];
            values[i-1] = values[n];
            keys[i-1] = keys[n];
            return res;
        }

        return null;
    }


    /**
     * O(1).
     * @return If the set is empty.
     */
    public boolean isEmpty()
    {
        return n == 0;
    }





    /**************************************************************************
     *                                ITERATOR                                *
     **************************************************************************/

    /**
     * Creates an iterator for the elements of the Mapping.
     * @return Iterator object.
     */
    public Iterator iterator()
    {
        Iterator it = new IteratorUnsortedArrayMapping();
        return it;
    }


    private class IteratorUnsortedArrayMapping implements Iterator
    {
        private int idxIterator;

        private IteratorUnsortedArrayMapping() {idxIterator = 0;}

        @Override
        public boolean hasNext() {return idxIterator < n;}

        @Override
        public Pair next()
        {
            Pair p = new Pair(keys[idxIterator], values[idxIterator]);
            idxIterator++;
            return p;
        }
    }


    /**
     * Pair of objects assigned to the Keys and the Values of the Mapping.
     *
     * @param <K> Generic key.
     * @param <V> Generic value.
     */
    public static class Pair<K, V>
    {
        private final K key;
        private final V value;

        public Pair(K key, V value)
        {
            this.key = key;
            this.value = value;
        }

        public K getKey() {return key;}
        public V getValue() {return value;}
    }

}
