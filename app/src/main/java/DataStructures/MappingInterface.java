package DataStructures;

import java.util.Iterator;
import java.util.Map;
import java.util.Random;


/**
 * Interface for the implementation of a Mapping data structure.
 *
 * @param <K> Generic key.
 * @param <V> Generic value.
 */
public interface MappingInterface<K, V>
{

    /**
     * @return If the set is empty.
     */
    public boolean isEmpty();


    /**
     * Finds the value of a given key.
     *
     * @param key Generic key to search.
     * @return The associated value of the key, null otherwise.
     */
    public V get(K key);


    /**
     * Inserts a value with it's key. If the key has already a value associated,
     * it replaces de previous one.
     *
     * @param key Generic key to search.
     * @param value Generic value to insert.
     * @return The previous associated value of the key, null otherwise.
     */
    public V put(K key, V value);


    /**
     * Removes the given key and it's value.
     *
     * @param key Generic key to search.
     * @return The previous associated value of the key, null otherwise.
     */
    public V remove(K key);


    /**
     * Creates an iterator for the elements of the Set.
     * @return Iterator object.
     */
    public Iterator iterator();


    /**
     * Pair of objects assigned to the Keys and the Values of the MappingInterface.
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


    /**
     * Given the iterator from a mapping implementation, returns a random pair
     * from it.
     *
     * @param it Iterator from the mapping.
     * @return Random pair.
     */
    public static Pair random(Iterator it)
    {
        if ((it == null) || !it.hasNext()) return null;

        Map.Entry p = (Map.Entry) it.next();
        Random ran = new Random();

        for (int i=2; it.hasNext(); i++)
        {
            if ((ran.nextInt(i) % i) == 0)
            {
                p = (Map.Entry) it.next();
            }
            else it.next();
        }

        return new Pair(p.getKey(), p.getValue());
    }


    /**
     * Iterates the mapping implementation and returns a random pair.
     * @return Random pair.
     */
    public default Pair random()
    {
        Iterator it = iterator();
        if ((it == null) || !it.hasNext()) return null;

        Pair p = (Pair) it.next();
        Random ran = new Random();

        for (int i=2; it.hasNext(); i++)
        {
            if ((ran.nextInt(i) % i) == 0)
            {
                p = (Pair) it.next();
            }
            else it.next();
        }

        return p;
    }

}
