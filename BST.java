public class BST<T> {

    // Inner class for BST nodes
    private class BSTNode {
        String key; // Change key from int to String
        T data;
        BSTNode left, right;

        // Constructor for BSTNode
        public BSTNode(String key, T data) {
            this.key = key;
            this.data = data;
            this.left = null;
            this.right = null;
        }
    }

    // Root and current node pointers
    private BSTNode root, current;

    // Constructor for BST
    public BST() {
        root = current = null;
    }

    // Method to check if the tree is empty
    public boolean empty() {
        return root == null;
    }

    // Method to check if the tree is full (always returns false for linked structure)
    public boolean full() {
        return false;
    }

    // Retrieve the data of the current node
    public T retrieve() {
        return current != null ? current.data : null;
    }

    // Method to find a key in the BST
    public boolean findkey(String tkey) {
        BSTNode p = root;
        BSTNode q = root;

        if (empty())
            return false;

        while (p != null) {
            q = p;
            int cmp = tkey.compareTo(p.key);
            if (cmp == 0) {
                current = p;
                return true;
            } else if (cmp < 0) {
                p = p.left;
            } else {
                p = p.right;
            }
        }
        current = q;
        return false;
    }

    // Method to insert a new node in the BST
    public boolean insert(String k, T val) {
        BSTNode p, q = current;

        // Check if the key already exists
        if (findkey(k)) {
            current = q; // findkey() modified current
            return false; // Key already in the BST
        }

        // Create a new node
        p = new BSTNode(k, val);

        // If the tree is empty, set the root to the new node
        if (empty()) {
            root = current = p;
            return true;
        } else {
            // Insert the new node in the correct position
            if (k.compareTo(current.key) < 0) {
                current.left = p;
            } else {
                current.right = p;
            }
            current = p;
            return true;
        }
    }

    // In-order traversal: Left, Node, Right
    public void inOrder() {
        inOrderRec(root);
    }

    private void inOrderRec(BSTNode node) {
        if (node != null) {
            inOrderRec(node.left);
            System.out.println("Word : " + node.data.toString());
            inOrderRec(node.right);
        }
    }

    // Pre-order traversal: Node, Left, Right
    public void preOrder() {
        preOrderRec(root);
    }

    private void preOrderRec(BSTNode node) {
        if (node != null) {
            System.out.println("Key: " + node.key + ", Data: " + node.data);
            preOrderRec(node.left);
            preOrderRec(node.right);
        }
    }
}
