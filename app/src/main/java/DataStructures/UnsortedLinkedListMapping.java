package DataStructures;

import java.util.Iterator;


/**
 * Implementation of an unsorted mapping of elements using a Linked List.
 *
 * @param <K> Generic key.
 * @param <V> Generic value.
 */
public class UnsortedLinkedListMapping<K, V> implements MappingInterface<K, V>
{

    private class Node
    {
        private final K key;
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


    @Override
    public boolean isEmpty()
    {
        return first == null;
    }


    @Override
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


    @Override
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


    @Override
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



    @Override
    public Iterator iterator()
    {
        Iterator it = new IteratorUnsortedLinkedListMapping();
        return it;
    }


    private class IteratorUnsortedLinkedListMapping implements Iterator
    {
        private Node idxIterator;

        private IteratorUnsortedLinkedListMapping() {idxIterator = first;}

        @Override
        public boolean hasNext() {return idxIterator != null;}

        @Override
        public Pair next()
        {
            Pair p = new Pair(idxIterator.key, idxIterator.value);
            idxIterator = idxIterator.next;
            return p;
        }
    }

}
