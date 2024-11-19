public class InvertedIndex {
    private LinkedList<Word> index;

    public InvertedIndex() {
        index = new LinkedList<>();
    }

    // Checks if a word exists in the inverted index
    public boolean containsWord(String word) {
        index.findfirst();
        while (index.retrieve() != null) {
            Word currentWord = index.retrieve();
            if (currentWord.getWord().equals(word)) {
                return true; 
            }
            if (!index.last()) {
                index.findnext();
            } else {
                break;
            }
        }
        return false; 
    }

    // Retrieves the document IDs associated with a given word
    public LinkedList<Integer> getDocumentIDsForWord(String word) {
        index.findfirst();
        while (index.retrieve() != null) {
            Word currentWord = index.retrieve();
            if (currentWord.getWord().equals(word)) {
                return currentWord.getDocIDs(); 
            }
            if (!index.last()) {
                index.findnext();
            } else {
                break;
            }
        }
        return null;
    }

    // Adds a word and its associated document ID to the index
    public void add(String word1, int docId) {
        String word = word1.toLowerCase(); 
        if (index.empty()) {
            Word newWord = new Word();
            newWord.word = word;
            newWord.addDoc(docId);
            index.insert(newWord);
            return;
        }

        index.findfirst();
        boolean found = false;
        while (index.retrieve() != null) {
            Word currentWord = index.retrieve();
            if (currentWord.getWord().equals(word)) {
                if (!currentWord.getDocIDs().search(docId)) {
                    currentWord.addDoc(docId); 
                }
                found = true;
                break; 
            }
            if (!index.last()) {
                index.findnext();
            } else {
                break; 
            }
        }

        if (!found) {
            Word newWord = new Word();
            newWord.word = word;
            newWord.addDoc(docId);
            index.insert(newWord);
        }
    }

    // Displays the contents of the inverted index
    public void displayInvertedIndex() {
        index.findfirst();
        while (index.retrieve() != null) {
            Word currentWord = index.retrieve();
            System.out.print("Word: " + currentWord.getWord() + ", Doc IDs: ");
            currentWord.docIDs.display(); 
            if (!index.last()) {
                index.findnext();
            } else {
                break;
            }
        }
    }
}
