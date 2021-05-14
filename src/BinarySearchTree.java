import java.util.ArrayList;
import java.util.Random;

/**
 * Binary Search Tree Class
 * 
 * @author apoc
 */

public class BinarySearchTree {
    
    // no duplicates allowed
    // no values (not needed for demonstration)

    Node rootNode;
    
    /**
     * Constructor for BST Class
     * 
     * @param rootKey The root key of whole BST
     */
    public BinarySearchTree(int rootKey){
        this.rootNode = new Node(rootKey);
    }

    public class Node {

        int key;
        Node left;
        Node right;

        /**
         * Constuctor of Node class
         * @param key key value of node
         */
        Node(int key){
            this.key = key;
        }

        /**
         * Getter for key
         * @return
         */
        public int getKey() {
            return key;
        }

        /**
         * setter for key
         * @param key
         */
        public void setKey(int key) {
            this.key = key;
        }

    }

    /**
     * Inserts a new node
     * 
     * @param root the current node
     * @param key the node to insert
     * @return  returns recursively the current node
     */
    public Node insert(Node root, int key){
        if(root == null){
            root = new Node(key);
            return root;
        }

        if(root.getKey() == key){
            root.key = key;
        }

        if(root.getKey() > key){
            if(root.left == null){
                root.left = new Node(key);
            }else{
                insert(root.left, key);
            }
        }

        if(root.getKey() < key){
            if(root.right == null){
                root.right = new Node(key);
            }else{
                insert(root.right, key);
            }
        }
        return root;
    }
    
    /**
     * Search operation / searches for key
     * 
     * @param root the current node
     * @param key the key to search
     * @return Return search result (recursively)
     */
    public boolean search(Node root, int key){
        // if leaf node is reached aka bottom return false not found
        if(root == null){
            return false;
        }

        //flag
        boolean result = false;
        
        // if key found
        if (root.getKey() == key){
            result = true;
        }

        // search left
        if(root.getKey() > key){
            result = search(root.left, key);
        }

        // search right
        if(root.getKey() < key){
            result = search(root.right, key);
        }

        return result;
    }

    /**
     * Print whole tree traversal ordered
     * @param root node to start
     */
    public void printTraversal(Node root){
        if(root == null){
            return;
        }
        printTraversal(root.left);
        System.out.println(root.key);
        printTraversal(root.right);
    }
    
    /**
     * Main delete function
     * @param key - key to delete
     */
    public void delete(int key){

        // set the root node if nessecary
        rootNode = deletion(rootNode, key);

    }

    /**
     * Recursive function to delete a key
     * 
     * @param root current node
     * @param key key to delete
     * @return current node (recursively)
     */
    private Node deletion(Node root, int key){

        // if empty / leave empty
        if(root == null){
            return root;
        }

        //travel to key direction
        if(root.getKey() > key){
            root.left = deletion(root.left, key);
        }
        else if(root.getKey() < key){
            root.right = deletion(root.right, key);
        }

        //fun beginns / deletion beginns
        else{

            //return left if right is empty (move the left one up and delete the upper)
            if(root.right == null){

                return root.left;
            }

            //return right if left is empty (move the right one up and delete the upper)
            if(root.left == null){
    
                return root.right;
            }


            // two child nodes exists
            if(root.left != null && root.right != null){
                
                //find the min key in right sub tree set the current key to it
                root.key = findMin(root.right);

                //delete the now duplicate key
                root.right = deletion(root.right, root.key);
            }
        }

        // return current node
        return root;

    }

    /**
     * Find min key
     * @param root start from here
     * @return  min key
     */
    public int findMin(Node root){
        int minValue = root.getKey();

        // Try to reach the bottom of the left tree
        while (root.left != null) {
            if(root.left.getKey() < minValue){
                minValue = root.left.getKey();
            }

            root = root.left;
        }

        return minValue;
    }

    /**
     * Verify if this is a BST
     * 
     * Works recursively, basically start from max Integer values and
     * makes the treshhold smaller per recursive call
     * 
     * @see This is copied! (see comment after this)
     * @see https://en.wikipedia.org/wiki/Binary_search_tree#Verification
     * 
     * @param root start from this node
     * @param min current min possible value
     * @param max current max possible value
     * @return true if it is bst starting from root node
     */
    public boolean verify(Node root, int min, int max){
        if (root == null){
            return true;
        }

        if(root.getKey() > max || root.getKey() < min){
            return false;
        }

        
        
        return verify(root.left, min, root.getKey()-1) && verify(root.right, root.getKey()+1, max);

    }

    // Main
    public static void main(String[] args) throws Exception {
        
        //create BST
        BinarySearchTree bst = new BinarySearchTree(50);
        Random random = new Random();

        int numberOfNodes = 10000000;

        long startt = System.nanoTime();

        for (int i = 0; i < numberOfNodes; i++) {
            int x = random.nextInt(numberOfNodes);
            bst.insert(bst.rootNode, x);
        }

        long endt = System.nanoTime();
        long totalt = endt - startt;

        System.out.println("Inserting " + numberOfNodes +" Nodes took: " + totalt/1000000000 +" seconds");
        System.out.println();
        //delete a value
        startt = System.nanoTime();

        bst.delete(5);
        endt = System.nanoTime();
        totalt = endt - startt;

        System.out.println("Deletion in " + numberOfNodes +" Nodes took: " + totalt +" Nanoseconds");

        //search a value
        startt = System.nanoTime();

        bst.search(bst.rootNode,5);
        endt = System.nanoTime();
        totalt = endt - startt;

        System.out.println("Searching in " + numberOfNodes +" Nodes took: " + totalt +" Nanoseconds");


        // check if still BST
        System.out.println();
        System.out.println("Is this a BST? " + bst.verify(bst.rootNode, Integer.MIN_VALUE, Integer.MAX_VALUE));


        // Now compare search with normal array and arraylist
        System.out.println();
        System.out.println("Now compare to array and arraylist: ");
        int[] array = new int[numberOfNodes];
        ArrayList<Integer> arrayList = new ArrayList();
        for (int i = 0; i < numberOfNodes; i++) {
            int x = random.nextInt(numberOfNodes);
            array[i] = x;
            arrayList.add(x);
        }
        

        //search a value
        startt = System.nanoTime();

        for (int i = 0; i < array.length; i++) {
            if(array[i] == 5){
                break;
            }
        }

        endt = System.nanoTime();
        totalt = endt - startt;

        System.out.println("Searching in array of " + numberOfNodes +" Values took: " + totalt +" Nanoseconds");

        //search a value in list
        startt = System.nanoTime();

        arrayList.contains(5);
        
        endt = System.nanoTime();
        totalt = endt - startt;

        System.out.println("Searching in arrayList of " + numberOfNodes +" Values took: " + totalt +" Nanoseconds");

        

    }
}
