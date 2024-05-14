package DataStructures;

import java.util.Iterator;
import java.util.Stack;


/**
 * Implementation of a mapping of elements using a Binary Search Tree.
 * @author Dani
 *
 * @param <K> Generic key.
 * @param <V> Generic value.
 */
public class BSTMapping<K extends Comparable<K>, V>
{

    private class Node
    {
        private K key;
        private V value;
        private Node left, right;

        public Node(K key, V value, Node left, Node right)
        {
            this.key = key;
            this.value = value;
            this.left = left;
            this.right = right;
        }
    }

    private class Cerca
    {
        private V value;

        public Cerca(V value)
        {
            this.value = value;
        }
    }


    private Node root;


    /**
     * Constructor of the unsorted mapping.
     * O(1).
     */
    public BSTMapping()
    {
        root = null;
    }


    /**
     * O(1).
     * @return If the set is empty.
     */
    public boolean isEmpty()
    {
        return root == null;
    }


    /**
     * Finds the value of a given key.
     * O(log2(n)).
     *
     * @param key Generic key to search.
     * @return The associated value of the key, null otherwise.
     */
    public V get(K key)
    {
        return get(key, root);
    }

    private V get(K key, Node current)
    {
        if (current == null) return null;


        if (key.compareTo(current.key) < 0)
        {
            return get(key, current.left);
        }
        else if (key.compareTo(current.key) > 0)
        {
            return get(key, current.right);
        }

        return current.value;
    }


    /**
     * Inserts a value with it's key. If the key has already a value associated,
     * it replaces de previous one.
     * O(log2(n)).
     *
     * @param key Generic key to search.
     * @param value Generic value to insert.
     * @return The previous associated value of the key, null otherwise.
     */
    public V add(K key, V value)
    {
        Cerca cerca = new Cerca(null);
        this.root = add(key, value, root, cerca);
        return cerca.value;
    }

    private Node add(K key, V value, Node current, Cerca cerca)
    {
        if (current == null)
        {
            return new Node(key, value, null, null);
        }

        if (key.compareTo(current.key) < 0)
        {
            current.left = add(key, value, current.left, cerca);
            return current;
        }
        else if (key.compareTo(current.key) > 0)
        {
            current.right = add(key, value, current.right, cerca);
            return current;
        }

        cerca.value = value;
        current.value = value;
        return current;
    }


    /**
     * Removes the given key and it's value.
     * O(log2(n)).
     *
     * @param key Generic key to search.
     * @return The previous associated value of the key, null otherwise.
     */
    public V remove(K key)
    {
        Cerca cerca = new Cerca(null);
        this.root = remove(key, root, cerca);
        return cerca.value;
    }

    private Node remove(K key, Node current, Cerca cerca)
    {
        if (current == null)
        {
            cerca.value = null;
            return null;
        }

        if (key.compareTo(current.key) < 0)
        {
            current.left = remove(key, current.left, cerca);
            return current;
        }
        else if (key.compareTo(current.key) > 0)
        {
            current.right = remove(key, current.right, cerca);
            return current;
        }


        cerca.value = current.value;

        if ((current.left == null) && (current.right == null))
        {
            return null;
        }
        else if ((current.left == null) && (current.right != null))
        {
            return current.right;
        }
        else if ((current.left != null) && (current.right == null))
        {
            return current.left;
        }

        Node plowest = current.right;
        Node parent = current;
        while (plowest.left != null)
        {
            parent = plowest;
            plowest = plowest.left;
        }

        plowest.left = current.left;
        if (plowest != current.right)
        {
            parent.left = plowest.right;
            plowest.right = current.right;
        }

        return plowest;
    }





    /**************************************************************************
     *                                ITERATOR                                *
     **************************************************************************/

    /**
     * Creates an iterator for the elements of the Set.
     * @return Iterator object.
     */
    public Iterator iterator()
    {
        Iterator it = new IteratorBSTSet();
        return it;
    }


    private class IteratorBSTSet implements Iterator
    {

        private Stack<Node> iterator;

        public IteratorBSTSet()
        {
            Node p;
            iterator = new Stack();
            if (root != null)
            {
                p = root;
                while (p.left != null)
                {
                    iterator.push(p);
                    p = p.left;
                }
                iterator.push(p);
            }
        }

        public boolean hasNext()
        {
            return !iterator.isEmpty();
        }

        public Object next()
        {
            Node p = iterator.pop();
            Pair pair = new Pair(p.key, p.value);
            if (p.right != null)
            {
                p = p.right;
                while (p.left != null)
                {
                    iterator.push(p);
                    p = p.left;
                }
                iterator.push(p);
            }
            return pair;
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
