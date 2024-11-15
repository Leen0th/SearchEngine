public class InvertedIndex {
    private LinkedList<Word> index;

    // Constructor
    public InvertedIndex() {
        index = new LinkedList<>();
    }

    // Checks if a word is in the Inverted Index
    public boolean containsWord(String word) {
        index.findfirst();

        while (index.retrieve() != null) {

            Word currentWord = index.retrieve();

            if (currentWord.getWord().equals(word)) {
                return true; // Word found
            }

            if (!index.last()) {
                index.findnext();
            } else {
                break;
            }
        }

        return false; // Word not found
    }

    // Retrieves the document IDs associated with a word, or returns null if the word is not found
    public LinkedList<Integer> getDocumentIDsForWord(String word) {
        index.findfirst();

        while (index.retrieve() != null) {
            Word currentWord = index.retrieve();

            if (currentWord.getWord().equals(word)) {
                return currentWord.getDocIDs(); // Return the list of document IDs
            }

            if (!index.last()) {
                index.findnext();
            } else {
                break;
            }
        }

        return null; // Word not found
    }

    // Adds the word and doc ID if not exists, or adds only the doc ID if the word exists
    public void add(String word1, int docId) {
        String word = word1.toLowerCase(); // Normalize to lowercase for consistency
    
        // If the index is empty, create a new Word entry directly
        if (index.empty()) {
            Word newWord = new Word();
            newWord.word = word;
            newWord.addDoc(docId);
            index.insert(newWord); 
            System.out.println("Adding word: " + word + ", Document ID: " + docId); // Debugging 
            return;
        }
    
        index.findfirst();
        boolean found = false;
    
        // Iterate through the index to find the word
        while (index.retrieve() != null) {
            Word currentWord = index.retrieve();
            if (currentWord.getWord().equals(word)) {
                // If the word exists, check for the document ID
                if (currentWord.getDocIDs().search(docId) == false) { // Assuming getDocIDs returns a collection
                    currentWord.addDoc(docId); // Add doc ID if it doesn't already exist
                  
                } 
                found = true;
                break; // Exit the loop since the word was found
            }
    
            // Move to the next word in the index
            if (!index.last()) {
                index.findnext();
            } else {
                break; // Exit if at the last item
            }
        }
    
        // If the word was not found, create a new Word entry
        if (!found) {
            Word newWord = new Word();
            newWord.word = word;
            newWord.addDoc(docId); 
            index.insert(newWord);
            
        }
    }
    // Display the Inverted Index
    public void displayInvertedIndex() {
        index.findfirst();
        while (index.retrieve() != null) {
            Word currentWord = index.retrieve();
            System.out.print("Word: " + currentWord.getWord() + ", Doc IDs: ");
            currentWord.docIDs.display(); // Ensure this method correctly displays the doc IDs
            if (!index.last()) {
                index.findnext();
            } else {
                break;
            }
        }
    }

   
}