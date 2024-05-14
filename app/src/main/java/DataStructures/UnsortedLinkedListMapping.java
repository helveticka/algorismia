package DataStructures;

import java.util.Iterator;


/**
 * Implementation of an unsorted mapping of elements using a Linked List.
 * @author Dani
 *
 * @param <K> Generic key.
 * @param <V> Generic value.
 */
public class UnsortedLinkedListMapping<K, V>
{

    private class Node
    {
        private K key;
        private V value;
        private Node next;

        public Node(K key, V value, Node next)
        {
            this.key = key;
            this.value = value;
            this.next = next;
        }
    }


    private Node first;


    /**
     * Constructor of the unsorted mapping.
     * O(1).
     */
    public UnsortedLinkedListMapping()
    {
        this.first = null;
    }


    /**
     * O(1).
     * @return If the set is empty.
     */
    public boolean isEmpty()
    {
        return first == null;
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
        Node p = first;
        boolean found = false;

        while ((p != null) && !found)
        {
            found = p.key.equals(key);
            if (!found)
            {
                p = p.next;
            }
        }

        if (found) return p.value;
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
        Node p = first;
        boolean found = false;

        while ((p != null) && !found)
        {
            found = p.key.equals(key);
            if (!found)
            {
                p = p.next;
            }
        }

        if (found)
        {
            V valueAnt = p.value;
            p.value = value;
            return valueAnt;
        }

        first = new Node(key, value, first);
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
        Node p = first, pp = null;
        boolean found = false;

        while (p != null && !found)
        {
            found = p.key.equals(key);
            if (!found)
            {
                pp = p;
                p = p.next;
            }
        }

        if (found)
        {
            V valueAnt = p.value;

            if (pp == null) first = p.next;
            else pp.next = p.next;

            return valueAnt;
        }

        return null;
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
        Iterator it = new IteratorUnsortedLinkedListMapping();
        return it;
    }


    private class IteratorUnsortedLinkedListMapping implements Iterator
    {
        private Node idxIterator;

        private IteratorUnsortedLinkedListMapping() {idxIterator = first;}

        public boolean hasNext() {return idxIterator != null;}

        public Pair next()
        {
            Pair p = new Pair(idxIterator.key, idxIterator.value);
            idxIterator = idxIterator.next;
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
