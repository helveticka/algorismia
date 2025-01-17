package DataStructures;

import java.util.Iterator;


/**
 * Implementation of an unsorted set of elements using an Array.
 *
 * @param <E> Generic element.
 */
public class UnsortedArraySet<E> implements SetInterface<E>
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


    @Override
    public boolean isEmpty()
    {
        return n == 0;
    }


    @Override
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


    @Override
    public boolean add(E elem)
    {
        if ((n == array.length) || contains(elem)) return false;

        array[n] = elem;
        n++;
        return true;
    }


    @Override
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



    @Override
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
