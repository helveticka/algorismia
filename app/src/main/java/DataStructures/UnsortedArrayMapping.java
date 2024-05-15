package DataStructures;

import java.util.Iterator;


/**
 * Implementation of an unsorted mapping of elements using an Array.
 *
 * @param <K> Generic key.
 * @param <V> Generic value.
 */
public class UnsortedArrayMapping<K, V> implements MappingInterface<K, V>
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


    @Override
    public boolean isEmpty()
    {
        return n == 0;
    }


    @Override
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


    @Override
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


    @Override
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



    @Override
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

}
