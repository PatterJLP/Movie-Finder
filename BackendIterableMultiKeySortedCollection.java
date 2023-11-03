import java.util.Iterator;
import java.util.ArrayList;
public class BackendIterableMultiKeySortedCollection <T extends Comparable<T>> implements IterableMultiKeySortedCollectionInterface<T>{
    private ArrayList<T> placeHolder = new ArrayList<>();
    /**
     * Inserts value into tree that can store multiple objects per key by keeping
     * lists of objects in each node of the tree.
     * @param key object to insert
     * @return true if obj was inserted
     */
    @Override
    public boolean insertSingleKey(T key) {
        placeHolder.add(key);
        return true;
    }

    /**
     * @return the number of values in the tree.
     */
    @Override
    public int numKeys() {
        return placeHolder.size();
    }

    /**
     * Returns an iterator that does an in-order iteration over the tree.
     */
    @Override
    public Iterator<T> iterator() {
        return placeHolder.iterator();
    }

    /**
     * Sets the starting point for iterations. Future iterations will start at the
     * starting point or the key closest to it in the tree. This setting is remembered
     * until it is reset. Passing in null disables the starting point.
     * @param startPoint the start point to set for iterations
     */
    @Override
    public void setIterationStartPoint(Comparable<T> startPoint) {
    }

    /**
     * Inserts data
     * @param data the data to be inserted
     */
    public boolean insert(KeyListInterface<T> data) throws NullPointerException,
            IllegalArgumentException {
        return false;
    }

    /**
     * Checks if certain data is in the tree
     * @param data the data we're looking for
     */
    public boolean contains(Comparable<KeyListInterface<T>> data) {
        if (placeHolder.contains(data)){
            return true;
        }else {
            return false;
        }
    }

    /**
     * Size of the tree
     */
    public int size() {
        return placeHolder.size();
    }
    /**
     * Checks if tree is empty
     */
    public boolean isEmpty() {
        return placeHolder.isEmpty();
    }

    /**
     * Clears the tree
     */
    public void clear() {
        placeHolder.clear();

    }
}

