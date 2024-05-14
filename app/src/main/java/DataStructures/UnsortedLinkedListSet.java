package DataStructures;

import java.util.Iterator;


/**
 * Implementation of an unsorted set of elements using a Linked List.
 * @author Dani
 *
 * @param <E> Generic element.
 */
public class UnsortedLinkedListSet<E extends Comparable<E>>
{

    private class Node
    {
        private E elem;
        private Node next;

        public Node(E elem, Node next)
        {
            this.elem = elem;
            this.next = next;
        }
    }


    private Node first;


    /**
     * Constructor of the unsorted set.
     * O(1).
     */
    public UnsortedLinkedListSet()
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
     * Iterates through the set to check if it's containing the element.
     * O(n).
     *
     * @param elem Generic element to check.
     * @return If the element is contained in the set.
     */
    public boolean contains(E elem)
    {
        Node p = first;
        boolean found = false;

        while ((p != null) && !found)
        {
            found = p.elem.equals(elem);
            p = p.next;
        }

        return found;
    }


    /**
     * Inserts the element in the set if it's not included yet.
     * O(n).
     *
     * @param elem Generic element to be added.
     * @return True if it has been added, false otherwise.
     */
    public boolean add(E elem)
    {
        boolean found = contains(elem);
        if (!found)
        {
            Node n = new Node(elem, first);
            first = n;
        }
        return !found;
    }


    /**
     * Removes the element from the set.
     * O(n).
     *
     * @param elem Generic element to be removed.
     * @return True if it has been removed, false otherwise.
     */
    public boolean remove(E elem)
    {
        Node p = first, pp = null;
        boolean found = false;

        while ((p != null) && !found)
        {
            found = p.elem.equals(elem);
            if (!found)
            {
                pp = p;
                p = p.next;
            }
        }

        if (found)
        {
            if (pp == null) first = p.next;
            else pp.next = p.next;
        }

        return found;
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
        Iterator it = new IteratorUnsortedLinkedListSet();
        return it;
    }


    private class IteratorUnsortedLinkedListSet implements Iterator
    {
        private Node idxIterator;

        private IteratorUnsortedLinkedListSet() {idxIterator = first;}

        public boolean hasNext() {return idxIterator != null;}

        public Object next()
        {
            E elem = idxIterator.elem;
            idxIterator = idxIterator.next;
            return elem;
        }
    }

}
