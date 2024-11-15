public class InvertedIndexBST {
    private class Node {
        Word wordData;
        Node left, right;

        public Node(Word wordData) {
            this.wordData = wordData;
            this.left = null;
            this.right = null;
        }
    }

    private Node root;

    // Constructor
    public InvertedIndexBST() {
        root = null;
    }

    // Public method to check if a word is in the inverted index
    public boolean containsWord(String word) {
        return containsWordRecursive(root, word.toLowerCase());
    }

    // Recursive helper method for containsWord
    private boolean containsWordRecursive(Node node, String word) {
        if (node == null) {
            return false;
        }
        
        int comparison = word.compareTo(node.wordData.getWord());

        if (comparison == 0) {
            return true; // Word found
        } else if (comparison < 0) {
            return containsWordRecursive(node.left, word); // Search in the left subtree
        } else {
            return containsWordRecursive(node.right, word); // Search in the right subtree
        }
    }

    // Public method to add a word and document ID
    public void add(String word, int docId) {
        root = addRecursive(root, word.toLowerCase(), docId);
    }

    // Recursive helper method for add
    private Node addRecursive(Node node, String word, int docId) {
        if (node == null) {
            Word newWord = new Word();
            newWord.word = word;
            newWord.addDoc(docId);
            return new Node(newWord); // Create a new node if the word is not found
        }

        int comparison = word.compareTo(node.wordData.getWord());

        if (comparison == 0) {
            // If the word exists, add the document ID if it's not already there
            if (!node.wordData.getDocIDs().search(docId)) {
                node.wordData.addDoc(docId);
            }
        } else if (comparison < 0) {
            node.left = addRecursive(node.left, word, docId); // Add to the left subtree
        } else {
            node.right = addRecursive(node.right, word, docId); // Add to the right subtree
        }

        return node;
    }

    // Display the inverted index
    public void displayInvertedIndex() {
        displayInOrder(root);
    }

    // In-order traversal to display the BST in alphabetical order
    private void displayInOrder(Node node) {
        if (node != null) {
            displayInOrder(node.left); // Visit left subtree
            System.out.print("Word: " + node.wordData.getWord() + ", Doc IDs: ");
            node.wordData.docIDs.display(); // Ensure this method correctly displays the doc IDs
            displayInOrder(node.right); // Visit right subtree
        }
    }
}
