package DataStructures;

import java.util.Iterator;
import java.util.Stack;


/**
 * Implementation of a set of elements using a Binary Search Tree.
 * @author Dani
 *
 * @param <E> Generic element.
 */
public class BSTSet<E extends Comparable<E>>
{

    private class Node
    {
        private E elem;
        private Node left, right;

        public Node(E elem, Node left, Node right)
        {
            this.elem = elem;
            this.left = left;
            this.right = right;
        }
    }

    private class Cerca
    {
        private boolean trobat;

        public Cerca(boolean trobat)
        {
            this.trobat = trobat;
        }
    }


    private Node root;


    /**
     * Constructor of the set.
     * O(1).
     */
    public BSTSet()
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
     * Iterates through the set to check if it's containing the element.
     * O(log2(n)).
     *
     * @param elem Generic element to check.
     * @return If the element is contained in the set.
     */
    public boolean contains(E elem)
    {
        return contains(elem, root);
    }

    private boolean contains(E elem, Node current)
    {
        if (current == null) return false;


        if (elem.compareTo(current.elem) < 0)
        {
            return contains(elem, current.left); // cercar al fill esquerra
        }
        else if (elem.compareTo(current.elem) > 0)
        {
            return contains(elem, current.right); // cercar al fill dret
        }

        return true;
    }


    /**
     * Inserts the element in the set if it's not included yet.
     * O(log2(n)).
     *
     * @param elem Generic element to be added.
     * @return True if it has been added, false otherwise.
     */
    public boolean add(E elem)
    {
        Cerca cerca = new Cerca(false);
        this.root = add(elem, root, cerca);
        return !cerca.trobat;
    }

    private Node add(E elem, Node current, Cerca cerca)
    {
        if (current == null)
        {
            return new Node(elem, null, null);
        }

        if (elem.compareTo(current.elem) < 0)
        {
            current.left = add(elem, current.left, cerca);
            return current;
        }
        else if (elem.compareTo(current.elem) > 0)
        {
            current.right = add(elem, current.right, cerca);
            return current;
        }

        cerca.trobat = true;
        return current;
    }


    /**
     * Removes the element from the set.
     * O(log2(n)).
     *
     * @param elem Generic element to be removed.
     * @return True if it has been removed, false otherwise.
     */
    public boolean remove(E elem)
    {
        Cerca cerca = new Cerca(false);
        this.root = remove(elem, root, cerca);
        return !cerca.trobat;
    }

    private Node remove(E elem, Node current, Cerca cerca)
    {
        if (current == null)
        {
            cerca.trobat = false;
            return null;
        }

        if (elem.compareTo(current.elem) < 0)
        {
            current.left = remove(elem, current.left, cerca);
            return current;
        }
        else if (elem.compareTo(current.elem) > 0)
        {
            current.right = remove(elem, current.right, cerca);
            return current;
        }


        cerca.trobat = true;

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
            E elem = p.elem;
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
            return elem;
        }
    }

}
