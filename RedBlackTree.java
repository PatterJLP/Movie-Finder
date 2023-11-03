import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Red Black Tree implementation that extends BinarySearchTree class. Has an insert method to insert
 * nodes into Red Black Tree as well as a method that enforces the properties of a red black tree
 * and updates them. Also includes JUnit test methods.
 */
public class RedBlackTree<T extends Comparable<T>> extends BinarySearchTree<T> {
    /**
     * This class represents an extended node class that, stores the color for each node in addition
     * to the node's parent, children and data.
     */
    protected static class RBTNode<T> extends Node<T> {
        public int blackHeight = 0;

        public RBTNode(T data) {
            super(data);
        }

        public RBTNode<T> getUp() {
            return (RBTNode<T>) this.up;
        }

        public RBTNode<T> getDownLeft() {
            return (RBTNode<T>) this.down[0];
        }

        public RBTNode<T> getDownRight() {
            return (RBTNode<T>) this.down[1];
        }
    }

    /**
     * Method that recognizes and handles each of the possible red-black tree property violation
     * cases.
     */
    protected void enforceRBTreePropertiesAfterInsert(RBTNode<T> node) {
        //Creates an uncle node and grandparent node
        RBTNode<T> uncleNode = null;
        RBTNode<T> grandParentNode = null;

        //Initializes uncle and grandparent node if they exist
        if (node.getUp() != null && node.getUp().getUp() != null) {
            grandParentNode = node.getUp().getUp();
            if (node.getUp() == grandParentNode.getDownLeft()) {
                uncleNode = grandParentNode.getDownRight();
            } else {
                uncleNode = grandParentNode.getDownLeft();
            }
        }

        //If parent is null, then node is the root and updates root to be black color
        if (node.getUp() == null) {
            node.blackHeight = 1;
            return;
        }

        //Then check color of parent node
        //If black do nothing - no violations introduced
        if (node.getUp().blackHeight == 1) {
            return;
        }


        //Case 1: Parent node and its sibling is red.
        if (uncleNode != null) {
            if (node.getUp().blackHeight == 0 && uncleNode.blackHeight == 0) {
                //Change parent and uncle node to black
                node.getUp().blackHeight = 1;
                uncleNode.blackHeight = 1;
                //Change grandparent node to red
                grandParentNode.blackHeight = 0;

                //Recurse to make sure violation hasn't moved up the tree
                enforceRBTreePropertiesAfterInsert(node.getUp().getUp());
                return;
            }
        }


        //Case 2: Parent node is red and uncle is black. This case is for two violating nodes
        //that are on the same side
        //Checks both violating nodes are left children
        if (!node.isRightChild() && !node.getUp().isRightChild()) {
            //Right rotation of grandfather node
            rotate(node.getUp(), grandParentNode);
            //Swap colors of parent and grandfather node
            int colorSwap = node.getUp().blackHeight;
            node.getUp().blackHeight = grandParentNode.blackHeight;
            grandParentNode.blackHeight = colorSwap;
            return;

        }
        //Checks both violating nodes are right children
        if (node.isRightChild() && node.getUp().isRightChild()) {
            //Left rotation of grandfather node
            rotate(node.getUp(), grandParentNode);
            //Swap colors of parent and grandfather node
            int colorSwap = node.getUp().blackHeight;
            node.getUp().blackHeight = grandParentNode.blackHeight;
            grandParentNode.blackHeight = colorSwap;
            return;
        }


        //Case 3 Parent node is red and uncle is black. This case is for two violating nodes that
        // are on different sides
        //Checks node is right child and parent is left child
        if (node.isRightChild() && !node.getUp().isRightChild()) {
            //Left rotation of parent node
            rotate(node, node.getUp());

            //Applies case two, both are on same side now
            rotate(node, grandParentNode);
            int colorSwap = node.blackHeight;
            node.blackHeight = grandParentNode.blackHeight;
            grandParentNode.blackHeight = colorSwap;
            return;
        }
        //Checks node is left child and parent is right child
        if (!node.isRightChild() && node.getUp().isRightChild()) {
            //Right rotation of parent node
            rotate(node, node.getUp());
            //Applies case two, both are on same side now
            rotate(node, grandParentNode);
            int colorSwap = node.blackHeight;
            node.blackHeight = grandParentNode.blackHeight;
            grandParentNode.blackHeight = colorSwap;
            return;
        }
    }

    /**
     * Method that creates a new node to be inserted and calls the method to enforce Red Black Tree
     * properties
     */
    @Override
    public boolean insert(T data) throws NullPointerException {
        //Create new node
        RBTNode<T> newNode = new RBTNode<>(data);
        //Insert into BST
        super.insertHelper(newNode);
        //Enforce red black tree properties
        enforceRBTreePropertiesAfterInsert(newNode);

        //Root is always black in a RBT
        ((RBTNode<T>) root).blackHeight = 1;
        //Insert was successful
        return true;
    }


    /**
     * This first test models inserting a red node where its parent's violating node, and it's
     * sibling are both red. This should result in a color swap only.
     */
    @Test
    public void testCase1() {
        //Creates an empty RedBlackTree and tests that it is empty
        RedBlackTree<Integer> rbtCaseOne = new RedBlackTree<>();
        assertEquals("[  ]", rbtCaseOne.toLevelOrderString(), "Newly created rbt is not empty");

        //Inserts 3,2,4 where 3 is root and black. Rest are red
        rbtCaseOne.insert(3);
        rbtCaseOne.insert(2);
        rbtCaseOne.insert(4);

        //Checks nodes are in right order before insertion
        String actual = rbtCaseOne.toLevelOrderString();
        assertEquals("[ 3, 2, 4 ]", actual, "Not in correct order");

        //Checks nodes are right color before insertion
        RBTNode<Integer> root = (RedBlackTree.RBTNode<Integer>) rbtCaseOne.root;
        assertEquals(1, root.blackHeight, "Root is not black");
        assertEquals(0, root.getDownLeft().blackHeight, "Left child is not red");
        assertEquals(0, root.getDownRight().blackHeight, "Right child is not red");

        //Inserts one, a red node that will introduce a violation
        rbtCaseOne.insert(1);

        //Checks tree is in correct order after insertion
        actual = rbtCaseOne.toLevelOrderString();
        assertEquals("[ 3, 2, 4, 1 ]", actual, "Not in correct order");

        //Checks trees nodes are the right color after insertion
        assertEquals(1, root.blackHeight, "Root is not black");
        assertEquals(1, root.getDownLeft().blackHeight, "Left child is not black");
        assertEquals(1, root.getDownRight().blackHeight, "Right child is not black");
        assertEquals(0, root.getDownLeft().getDownLeft().blackHeight, "Newly inserted node is not" +
                " " + "red");
    }

    /**
     * This second test models inserting a red node where its parent's violating node, and it's
     * sibling are different colors. The violating nodes are on the same side. This should result in
     * a color swap and rotation.
     */
    @Test
    public void testCase2() {
        //Creates an empty RedBlackTree and tests that it is empty
        RedBlackTree<Integer> rbtCaseTwo = new RedBlackTree<>();
        assertEquals("[  ]", rbtCaseTwo.toLevelOrderString(), "Newly created rbt is not empty");

        //Inserts 3,2,4 where 3 and 4 are black. 2 is red.
        rbtCaseTwo.insert(3);
        rbtCaseTwo.insert(2);
        rbtCaseTwo.insert(4);

        //Changes 4's color to black for this test case
        RBTNode<Integer> root = (RedBlackTree.RBTNode<Integer>) rbtCaseTwo.root;
        root.getDownRight().blackHeight = 1;

        //Checks nodes are in right order before insertion
        String actual = rbtCaseTwo.toLevelOrderString();
        assertEquals("[ 3, 2, 4 ]", actual, "Not in correct order");

        //Check nodes color's before insertion
        assertEquals(1, root.blackHeight, "Root is not black");
        assertEquals(1, root.getDownRight().blackHeight, "Right child is not black");
        assertEquals(0, root.getDownLeft().blackHeight, "Left child is not red");

        //Inserts one, a red node that will introduce a violation
        rbtCaseTwo.insert(1);
        root = (RedBlackTree.RBTNode<Integer>) rbtCaseTwo.root;

        //Tests nodes are in right order after insertion.
        actual = rbtCaseTwo.toLevelOrderString();
        assertEquals("[ 2, 1, 3, 4 ]", actual, "Not in correct order");

        //Checks that root got updated since this violation introduces a rotation
        int actualRoot = root.data;
        assertEquals(2, actualRoot, "Root is not two");

        //Checks color of nodes after insertion
        assertEquals(1, root.blackHeight, "Root is not black");
        assertEquals(0, root.getDownLeft().blackHeight, "Left child is not red");
        assertEquals(0, root.getDownRight().blackHeight, "Right child is not red");
        assertEquals(1, root.getDownRight().getDownRight().blackHeight, "Node is not black");

    }

    /**
     * This third test models inserting a red node where its parent's violating node, and it's
     * sibling are different colors. The violating nodes are on different sides. This should rotate
     * the two violating nodes and then have the same outcome as case 2.
     */
    @Test
    public void testCase3() {
        //Creates an empty RedBlackTree and tests that it is empty
        RedBlackTree<Integer> rbtCaseThree = new RedBlackTree<>();
        assertEquals("[  ]", rbtCaseThree.toLevelOrderString(), "Newly created rbt is not empty");

        //Inserts 3,1,4 where 3 and 4 are black. 1 is red.
        rbtCaseThree.insert(3);
        rbtCaseThree.insert(1);
        rbtCaseThree.insert(4);
        //Sets 4 to black for this test case.
        RBTNode<Integer> root = (RedBlackTree.RBTNode<Integer>) rbtCaseThree.root;
        root.getDownRight().blackHeight = 1;

        //Checks nodes are in right order before insertion
        String actual = rbtCaseThree.toLevelOrderString();
        assertEquals("[ 3, 1, 4 ]", actual, "Not in correct order");

        //Checks nodes colors before insertion
        assertEquals(1, root.blackHeight, "Root is not black");
        assertEquals(1, root.getDownRight().blackHeight, "Right child is not black");
        assertEquals(0, root.getDownLeft().blackHeight, "Left child is not red");

        //Inserts red node two which will introduce a violation that is on different sides.
        rbtCaseThree.insert(2);
        root = (RedBlackTree.RBTNode<Integer>) rbtCaseThree.root;

        //Checks order of nodes after insertion
        actual = rbtCaseThree.toLevelOrderString();
        assertEquals("[ 2, 1, 3, 4 ]", actual, "Not in correct order");

        //Checks root was updated after insertion since this violation caused a rotation
        int actualRoot = root.data;
        assertEquals(2, actualRoot, "Root is not two");

        //Checks colors of nodes are correct after insertion
        assertEquals(1, root.blackHeight, "Root is not black");
        assertEquals(0, root.getDownLeft().blackHeight, "Left child is not red");
        assertEquals(0, root.getDownRight().blackHeight, "Right child is not red");
        assertEquals(1, root.getDownRight().getDownRight().blackHeight, "Node is not black");

    }

    @Test
    public void testingWorks(){
        RedBlackTree<Integer> testTree = new RedBlackTree<>();

        testTree.insert(32);
        System.out.println(testTree.toLevelOrderString());
        testTree.insert(41);
        System.out.println(testTree.toLevelOrderString());
        testTree.insert(57);
        System.out.println(testTree.toLevelOrderString());
        testTree.insert(62);
        System.out.println(testTree.toLevelOrderString());
        testTree.insert(79);
        System.out.println(testTree.toLevelOrderString());
        testTree.insert(81);
        System.out.println(testTree.toLevelOrderString());
        testTree.insert(93);
        System.out.println(testTree.toLevelOrderString());
        testTree.insert(97);
        System.out.println(testTree.toLevelOrderString());

    }

}
