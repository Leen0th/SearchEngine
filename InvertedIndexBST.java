public class InvertedIndexBST {
    private BST<Word> index; 
    private int size = 0;

    // Returns the number of unique words in the inverted index.
    public int size() {
        return size;
    }

    // Initializes the inverted index as an empty BST.
    public InvertedIndexBST() {
        index = new BST<>(); 
    }

    // Checks if a specified word is present in the inverted index.
    public boolean containsWord(String word) {
        return index.findkey(word); 
    }

    // Adds a word and its associated document ID to the index.
    // If the word already exists, adds the document ID to the existing entry.
    public void add(String word1, int docId) {
        String word = word1.toLowerCase(); 

        if (index.empty()) {
            Word newWord = new Word();
            newWord.word = word;
            newWord.addDoc(docId);
            index.insert(word, newWord);
            return;
        }

        if (index.findkey(word)) {
            Word existingWord = index.retrieve(); 
            if (!existingWord.searchDocId(docId)) { 
                existingWord.addDoc(docId); 
            }
        } else {
            Word newWord = new Word();
            newWord.word = word;
            newWord.addDoc(docId);
            index.insert(word, newWord);
            size++;
        }
    }

    // Displays all words in the inverted index along with their associated document IDs.
    public void displayInvertedIndex() {
        index.inOrder(); 
    }

    // Retrieves the list of document IDs associated with a specified word.
    // Returns null if the word is not found.
    public LinkedList<Integer> getDocumentIDsForWord(String word) {
        if (index.findkey(word)) {
            Word currentWord = index.retrieve();
            if (currentWord.getWord().equals(word)) {
                return currentWord.getDocIDs(); 
            }
        }
        return null; 
    }
}
