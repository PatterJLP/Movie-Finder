import org.junit.jupiter.api.Test;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Stack;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * IterableMultiKey class that uses an iterator to iterate through a keyList. Creates its own stack
 * to iterate through.
 */
public class IterableMultiKeyRBT<T extends Comparable<T>> extends BinarySearchTree<KeyListInterface<T>> implements IterableMultiKeySortedCollectionInterface<T> {
    private Comparable<T> iterationStartPoint;
    private int numKeys;

    /**
     * Inserts value into tree that can store multiple objects per key by keeping lists of objects
     * in each node of the tree.
     *
     * @param key object to insert
     * @return true if a new node was inserted, false if the key was added into an existing node
     */
    @Override
    public boolean insertSingleKey(T key) {
        //A null key should not be inserted
        if (key == null) {
            throw new NullPointerException("Key is null");
        }
        //Creates new keyList with key to be inserted
        KeyList<T> keyList = new KeyList<>(key);

        //Check if the tree already contains a node with a duplicate key
        if (findNode(keyList) != null) {
            //Inserts key to the node with the duplicate value
            this.findNode(keyList).data.addKey(key);
            numKeys++;
            return false;
            //Else there are no duplicates
        } else {
            //Inserts key into a new node of the tree
            this.insert(keyList);
            numKeys++;
            return true;
        }
    }

    /**
     * @return the number of values in the tree.
     */
    @Override
    public int numKeys() {
        return numKeys;
    }

    /**
     * Returns an iterator that does an in-order iteration over the tree.
     */
    @Override
    public Iterator<T> iterator() {
        //Anonymous  class
        return new Iterator<T>() {
            //Create the initial stack for the iterator.
            private Stack<Node<KeyListInterface<T>>> stack = getStackStart();
            //keyIterator to iterate through duplicate values
            private Iterator<T> keyIterator = null;

            /**
             * Returns true if there are more keys to iterate through
             * @return true or false depending on if there are more keys in the tree
             */
            @Override
            public boolean hasNext() {
                return !stack.isEmpty() || (keyIterator != null && keyIterator.hasNext());
            }

            /**
             * Gets the next in order value of key
             * @return next key value
             */
            @Override
            public T next() {
                //Checks if there are more values in the tree
                if (!hasNext()) {
                    throw new NoSuchElementException("No elements left");
                }
                //Iterator of node checks if there are duplicate values, and if so returns
                // that value
                if (keyIterator != null && keyIterator.hasNext()) {
                    return keyIterator.next();
                    //Else iterates next node
                } else {
                    //Pops node in stack
                    Node<KeyListInterface<T>> currentNode = stack.pop();
                    keyIterator = currentNode.data.iterator();

                    //Returns nodes in ascending values
                    while (currentNode != null && currentNode.down[1] != null) {
                        stack.push(currentNode.down[1]);
                        currentNode = currentNode.down[0];
                    }

                }
                return keyIterator.next();
            }
        };
    }

    /**
     * Sets the starting point for iterations. Future iterations will start at the starting point or
     * the key closest to it in the tree. This setting is remembered until it is reset. Passing in
     * null disables the starting point.
     *
     * @param startPoint the start point to set for iterations
     */
    @Override
    public void setIterationStartPoint(Comparable startPoint) {
        iterationStartPoint = startPoint;
    }

    /**
     * Clears the tree
     */
    @Override
    public void clear() {
        super.clear();
        this.numKeys = 0;
    }

    /**
     * Protected helper method called getStartStack that returns an instance of java.util. Stack
     * containing nodes after initialization.
     */
    protected java.util.Stack<Node<KeyListInterface<T>>> getStackStart() {
        java.util.Stack<Node<KeyListInterface<T>>> stack = new Stack<>();
        Comparable<T> startPoint = iterationStartPoint;

        //No start point is set, the stack is initialized with the nodes
        // on the path from the root node to smallest key.
        if (iterationStartPoint == null) {
            Node<KeyListInterface<T>> currentNode = root;
            //Iterates down left subtree
            while (currentNode != null) {
                stack.push(currentNode);
                currentNode = currentNode.down[0];
            }

        } else {
            //the iteration start point is set, then the stack is
            //initialized with all the nodes with keys equal to or larger than the start point
            Node<KeyListInterface<T>> currentNode = root;
            while (currentNode != null) {
                //Compares startNode to next node
                int compareNodes =
                        iterationStartPoint.compareTo(currentNode.data.iterator().next());
                //Iterator visits the nodes that are equal to or greater than the start point
                if (compareNodes <= 0) {
                    stack.push(currentNode);
                    currentNode = currentNode.down[0];
                } else {
                    currentNode = currentNode.down[1];
                }
            }

        }

        return stack;
    }

    /**
     * Tests inserting a single key into a tree
     */
    @Test
    public void testInsertKey() {
        try {
            //Tests inserting a null node
            IterableMultiKeyRBT<Integer> tree = new IterableMultiKeyRBT<>();
            try {
                tree.insertSingleKey(null);
                fail();
            } catch (NullPointerException e) {
            }

            //Tests inserting keys, with duplicates
            tree.insertSingleKey(2);
            tree.insertSingleKey(-255);
            tree.insertSingleKey(0);
            tree.insertSingleKey(2);

            Iterator<Integer> iterator = tree.iterator();

            //Makes sure all keys are in tree, and returns in ascending order
            Integer key = iterator.next();
            assertEquals(-255, key);
            key = iterator.next();
            assertEquals(0, key);
            key = iterator.next();
            assertEquals(2, key);
            key = iterator.next();
            assertEquals(2, key);

            //Tests size and number of keys in tree
            assertEquals(3, tree.size());
            assertEquals(4, tree.numKeys());

            tree.clear();

            //Tests insert and tree with just one key
            tree.insertSingleKey(100);
            iterator = tree.iterator();
            key = iterator.next();
            assertEquals(100, key);
            assertEquals(1, tree.size());
            assertEquals(1, tree.numKeys());

        } catch (Exception e) {
            fail();
        }
    }

    /**
     * Tests numKeys method
     */
    @Test
    public void testNumKeys() {
        try {
            IterableMultiKeyRBT<Integer> tree = new IterableMultiKeyRBT<>();

            //Num keys in tree should be empty before adding anything
            assertEquals(0, tree.numKeys());
            assertEquals(0, tree.size());

            tree.insertSingleKey(2);
            tree.insertSingleKey(3);
            tree.insertSingleKey(5);

            //Num keys in tree should be 3 after inserting 3
            assertEquals(3, tree.numKeys());
            assertEquals(3, tree.size());

            tree.clear();

            assertEquals(0, tree.numKeys());
            assertEquals(0, tree.size());

            //Tests number of keys and size with tree of duplicate nodes
            tree.insertSingleKey(-1);
            tree.insertSingleKey(-1);
            tree.insertSingleKey(0);
            tree.insertSingleKey(10);
            tree.insertSingleKey(10);
            tree.insertSingleKey(10);

            assertEquals(6, tree.numKeys());
            assertEquals(3, tree.size());

        } catch (Exception e) {
            fail();
        }
    }

    /**
     * Tests starting iteration start point
     */
    @Test
    public void testSetIterationStartPoint() {
        try {
            IterableMultiKeyRBT<Integer> tree = new IterableMultiKeyRBT<>();

            // Insert keys
            tree.insertSingleKey(5);
            tree.insertSingleKey(8);
            tree.insertSingleKey(3);
            //Tests start point at end of node
            tree.setIterationStartPoint(8);

            Iterator<Integer> iterator = tree.iterator();

            // Verify that the first key is 8
            assertTrue(iterator.hasNext());
            Integer key = iterator.next();
            assertEquals(8, key);

            tree.clear();

            //Tests start point in middle of tree
            tree.insertSingleKey(5);
            tree.insertSingleKey(8);
            tree.insertSingleKey(3);
            tree.setIterationStartPoint(5);

            //Verify that the first key is 5
            iterator = tree.iterator();
            key = iterator.next();
            assertEquals(5, key);

            tree.clear();

            //Tests start point at beginning of tree
            tree.insertSingleKey(5);
            tree.insertSingleKey(8);
            tree.insertSingleKey(3);
            tree.setIterationStartPoint(3);

            //Verify first tree is 3
            iterator = tree.iterator();
            key = iterator.next();
            assertEquals(3, key);

        } catch (Exception e) {
            fail();
        }
    }

    /**
     * Tests iterator
     */
    @Test
    public void testIterator() {
        try {
            IterableMultiKeyRBT<Integer> tree = new IterableMultiKeyRBT<>();
            Iterator<Integer> iterator = tree.iterator();
            //Tests iterator throws error when next is called on an empty tree
            try {
                Integer key = iterator.next();
                fail();
            } catch (NoSuchElementException e) {
            }
            //Tests iterator iterates through tree in ascending order.
            IterableMultiKeyRBT<String> secondTree = new IterableMultiKeyRBT<>();
            secondTree.insertSingleKey("a");
            secondTree.insertSingleKey("b");
            secondTree.insertSingleKey("c");

            Iterator<String> stringIterator = secondTree.iterator();
            String key = stringIterator.next();
            assertEquals("a", key);
            key = stringIterator.next();
            assertEquals("b", key);
            key = stringIterator.next();
            assertEquals("c", key);

        } catch (Exception e) {
            fail();
        }


    }


}
