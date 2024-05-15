package DataStructures;

import java.util.Iterator;
import java.util.Random;


/**
 * Interface for the implementation of a Mapping data structure.
 *
 * @param <E> Generic element.
 */
public interface SetInterface<E>
{

    /**
     * O(1).
     * @return If the set is empty.
     */
    public boolean isEmpty();


    /**
     * Iterates through the set to check if it's containing the element.
     * O(n).
     *
     * @param elem Generic element to check.
     * @return If the element is contained in the set.
     */
    public boolean contains(E elem);


    /**
     * Inserts the element in the set if it's not included yet.
     * O(n).
     *
     * @param elem Generic element to be added.
     * @return True if it has been added, false otherwise.
     */
    public boolean add(E elem);


    /**
     * Removes the element from the set.
     * O(n).
     *
     * @param elem Generic element to be removed.
     * @return True if it has been removed, false otherwise.
     */
    public boolean remove(E elem);


    /**
     * Creates an iterator for the elements of the Set.
     * @return Iterator object.
     */
    public Iterator iterator();


    /**
     * Given the iterator from a mapping implementation, returns a random pair
     * from it.
     *
     * @param it Iterator from the mapping.
     * @return Random pair.
     */
    public static Object random(Iterator it)
    {
        if ((it == null) || !it.hasNext()) return null;

        Object elem = (Object) it.next();
        Random ran = new Random();

        for (int i=2; it.hasNext(); i++)
        {
            if ((ran.nextInt(i) % i) == 0)
            {
                elem = (Object) it.next();
            }
            else it.next();
        }

        return elem;
    }


    /**
     * Iterates the mapping implementation and returns a random pair.
     * @return Random pair.
     */
    public default Object random()
    {
        Iterator it = iterator();
        if ((it == null) || !it.hasNext()) return null;

        Object elem = (Object) it.next();
        Random ran = new Random();

        for (int i=2; it.hasNext(); i++)
        {
            if ((ran.nextInt(i) % i) == 0)
            {
                elem = (Object) it.next();
            }
            else it.next();
        }

        return elem;
    }

}
