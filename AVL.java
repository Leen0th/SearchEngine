public class AVL<T> {

    private class AVLNode {
        int key;
        T data;
        int balance;
        AVLNode left, right;

        AVLNode(int key, T data) {
            this.key = key;
            this.data = data;
            this.balance = 0; 
            this.left = this.right = null;
        }
    }

    private AVLNode root;

    // Method to calculate the height(longest path from the node to a leaf) of a node
    private int height(AVLNode node) {
        return node == null ? 0 : Math.max(height(node.left), height(node.right)) + 1;
    }

    // Method to calculate the balance factor of a node
    private int getBalance(AVLNode node) {
        return node == null ? 0 : height(node.right) - height(node.left);
    }

    // Method to perform a right rotation (to restore balance when its left-heavy)
    private AVLNode rotateRight(AVLNode y) {
        AVLNode x = y.left;
        AVLNode T2 = x.right;

        x.right = y;
        y.left = T2;

        y.balance = getBalance(y);
        x.balance = getBalance(x);

        return x;
    }

    // Method to perform a left rotation (to restore balance when its right-heavy)
    private AVLNode rotateLeft(AVLNode x) {
        AVLNode y = x.right;
        AVLNode T2 = y.left;

        y.left = x;
        x.right = T2;

        x.balance = getBalance(x);
        y.balance = getBalance(y);

        return y;
    }

    // Public method to insert a key-value pair into the AVL tree
    public void insert(int key, T data) {
        root = insert(root, key, data);
    }

    // Recursive method to insert a key-value pair
    private AVLNode insert(AVLNode node, int key, T data) {
        if (node == null) {
            return new AVLNode(key, data);
        }

        if (key < node.key) {
            node.left = insert(node.left, key, data);
        } else if (key > node.key) {
            node.right = insert(node.right, key, data);
        } else {
            return node; 
        }

        node.balance = getBalance(node);

        // Left-Left Case
        if (node.balance < -1 && key < node.left.key) {
            return rotateRight(node);
        }

        // Right-Right Case
        if (node.balance > 1 && key > node.right.key) {
            return rotateLeft(node);
        }

        // Left-Right Case
        if (node.balance < -1 && key > node.left.key) {
            node.left = rotateLeft(node.left);
            return rotateRight(node);
        }

        // Right-Left Case
        if (node.balance > 1 && key < node.right.key) {
            node.right = rotateRight(node.right);
            return rotateLeft(node);
        }

        return node;
    }

    // Public method to search for a node by key
    public T search(int key) {
        AVLNode node = search(root, key);
        return node != null ? node.data : null;
    }

    // Recursive method to search for a node by key
    private AVLNode search(AVLNode node, int key) {
        if (node == null || node.key == key) {
            return node;
        }

        if (key < node.key) {
            return search(node.left, key);
        } else {
            return search(node.right, key);
        }
    }

    // Public method to display the AVL tree using in-order traversal
    public void inOrder() {
        inOrder(root);
    }

    // Recursive method to perform in-order traversal
    private void inOrder(AVLNode node) {
        if (node != null) {
            inOrder(node.left);
            System.out.println("Key: " + node.key + ", Data: " + node.data + ", Balance: " + node.balance);
            inOrder(node.right);
        }
    }
}
