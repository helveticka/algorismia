package UnsortedElements;

import java.util.Iterator;


/**
 * Implementation of an unsorted set of elements using a linked list.
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
     * Iteates through the set to check if it's containing the element.
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


    /**
     * O(1).
     * @return If the set is empty.
     */
    public boolean isEmpty()
    {
        return first == null;
    }



    private Node middle(Node firstNode)
    {
        Node slow = firstNode, fast = firstNode;

        while ((fast.next != null) && (fast.next.next != null))
        {
            slow = slow.next;
            fast = fast.next.next;
        }

        return slow;
    }


    /**
     * Sorts the LinkedList using MergeSort.
     * O(n·log2(n)).
     */
    public void mergeSort()
    {
        first = mergeSortAlfabetically(first);
        first = mergeSortLength(first);
    }


    private Node mergeSortAlfabetically(Node n)
    {
        if ((n == null) || (n.next == null)) return n;

        Node mid = middle(n);
        Node nextToMid = mid.next;
        mid.next = null;

        Node left = mergeSortAlfabetically(n);
        Node right = mergeSortAlfabetically(nextToMid);

        return mergeAlfabetically(left, right);
    }


    private Node mergeAlfabetically(Node a, Node b)
    {
        if (a == null) return b;
        if (b == null) return a;

        Node result;
        String s1 = (String) a.elem;
        String s2 = (String) b.elem;
        if (s1.compareTo(s2) <= 0)
        {
            result = a;
            result.next = mergeAlfabetically(a.next, b);
        }
        else
        {
            result = b;
            result.next = mergeAlfabetically(a, b.next);
        }

        return result;
    }


    private Node mergeSortLength(Node n)
    {
        if ((n == null) || (n.next == null)) return n;

        Node mid = middle(n);
        Node nextToMid = mid.next;
        mid.next = null;

        Node left = mergeSortLength(n);
        Node right = mergeSortLength(nextToMid);

        return mergeLength(left, right);
    }


    private Node mergeLength(Node a, Node b)
    {
        if (a == null) return b;
        if (b == null) return a;

        Node result;
        String s1 = (String) a.elem;
        String s2 = (String) b.elem;
        if (s1.length() <= s2.length())
        {
            result = a;
            result.next = mergeLength(a.next, b);
        }
        else
        {
            result = b;
            result.next = mergeLength(a, b.next);
        }

        return result;
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
