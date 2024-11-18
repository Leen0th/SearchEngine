public class InvertedIndexBST {
    private BST<Word> index; // Using BST instead of LinkedList
    private int size=0;

    public int size() {
        return size;
    }
    // Constructor
    public InvertedIndexBST() {
        index = new BST<>(); // Initialize the BST
    }

    // Checks if a word is in the Inverted Index
    public boolean containsWord(String word) {
        return index.findkey(word); // Use BST's find method
    }

    // Adds the word and doc ID if not exists, or adds only the doc ID if the word exists
    public void add(String word1, int docId) {
        String word = word1.toLowerCase(); // Normalize to lowercase for consistency

        // If the index is empty, create a new Word entry directly
        if (index.empty()) {
            Word newWord = new Word();
            newWord.word = word;
            newWord.addDoc(docId);
            index.insert(word, newWord);
            
            return;
        }

        // Check if the word exists in the BST
        if (index.findkey(word)) {
            Word existingWord = index.retrieve(); // Get the existing Word object
            if (existingWord.searchDocId(docId) == false) { // Check if doc ID exists
                existingWord.addDoc(docId); // Add doc ID if it doesn't already exist
                
            }
            
        } else {
            // If the word was not found, create a new Word entry
            Word newWord = new Word();
            newWord.word = word;
            newWord.addDoc(docId);
            index.insert(word, newWord);
            size++;
        }
    }

    // Display the Inverted Index
    public void displayInvertedIndex() {
        index.inOrder(); // Traverse the BST to display words and their document IDs
    }

    public LinkedList<Integer> getDocumentIDsForWord(String word) {
        index.findkey(word);

        while (index.retrieve() != null) {
            Word currentWord = index.retrieve();

            if (currentWord.getWord().equals(word)) {
                return currentWord.getDocIDs(); // Return the list of document IDs
            }

            
        }

        return null; // Word not found
    }
}
