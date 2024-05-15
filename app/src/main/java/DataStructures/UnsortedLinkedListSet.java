package DataStructures;

import java.util.Iterator;


/**
 * Implementation of an unsorted set of elements using a Linked List.
 *
 * @param <E> Generic element.
 */
public class UnsortedLinkedListSet<E> implements SetInterface<E>
{

    private class Node
    {
        private final E elem;
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


    @Override
    public boolean isEmpty()
    {
        return first == null;
    }


    @Override
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


    @Override
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


    @Override
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



    @Override
    public Iterator iterator()
    {
        Iterator it = new IteratorUnsortedLinkedListSet();
        return it;
    }


    private class IteratorUnsortedLinkedListSet implements Iterator
    {
        private Node idxIterator;

        private IteratorUnsortedLinkedListSet() {idxIterator = first;}

        @Override
        public boolean hasNext() {return idxIterator != null;}

        @Override
        public Object next()
        {
            E elem = idxIterator.elem;
            idxIterator = idxIterator.next;
            return elem;
        }
    }

}
