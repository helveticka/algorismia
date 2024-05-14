package DataStructures;

import java.util.Iterator;


/**
 * Implementation of an unsorted set of elements using an Array.
 * @author Dani
 *
 * @param <E> Generic element.
 */
public class UnsortedArraySet<E>
{

    private final E[] array;
    private int n;


    /**
     * Constructor of the unsorted set.
     * O(1).
     *
     * @param max Initial capacity of the set.
     */
    public UnsortedArraySet(int max)
    {
        array = (E[]) new Object[max];
        n = 0;
    }


    /**
     * O(1).
     * @return If the set is empty.
     */
    public boolean isEmpty()
    {
        return n == 0;
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
        int i = 0;
        boolean found = false;

        while (!found && (i < n))
        {
            found = elem.equals(array[i]);
            i++;
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
        if ((n == array.length) || contains(elem)) return false;

        array[n] = elem;
        n++;
        return true;
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
        int i = 0;
        boolean found = false;

        while (!found && (i < n)) found = elem.equals(array[i++]);

        if (found)
        {
            array[i-1] = array[n-1];
            n--;
        }

        return found;
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
        return new IteratorUnsortedArraySet();
    }


    private class IteratorUnsortedArraySet implements Iterator
    {
        private int idxIterator;

        private IteratorUnsortedArraySet() {idxIterator = 0;}

        @Override
        public boolean hasNext() {return idxIterator < n;}

        @Override
        public Object next() {return array[idxIterator++];}
    }

}
