public class Document {
    int documentID;
    LinkedList<Word> index;

    public Document() {
        documentID = 0;
        index = new LinkedList<Word>();
    }

    public Document(int id, String[] words) {
        documentID = id;
        index = new LinkedList<Word>();
        for (String word : words) {
            index.insert(new Word(new LinkedList<>(), word));
        }
    }

    public void addWord(String word) {
        index.insert(new Word(new LinkedList<>(), word));
    }

    public boolean findWord(String word) {
        if (index.empty()) {
            return false; // List is empty, so word cannot be found
        }

        index.findfirst(); // Start from the head of the LinkedList
        do {
            // Retrieve the current Word object and check its word
            if (index.retrieve().getWord().equals(word)) {
                return true; // Word found
            }
            index.findnext(); // Move to the next Word in the list
        } while (!index.last()); // Continue until we reach the end of the list
    if(index.last()){
        if (index.retrieve().getWord().equals(word)) {
            return true; // Word found
        }
    
    }
        return false; // Word not found
    }

    public int getId() {
        return documentID;
    }

    public String getDocumentContent() {
        if (index.empty()) {
            return ""; // Empty document
        }
    
        StringBuilder content = new StringBuilder();
        index.findfirst();
    
        while (!index.last()) {
            content.append(index.retrieve().getWord()).append(" ");
            index.findnext();
        }
    
        content.append(index.retrieve().getWord());
        return content.toString().trim();
    }
    public int countTermOccurrences(String word) {
        int count = 0; // Initialize a count variable
    
        // Check if the index is empty
        if (index.empty()) {
            return count; // Return 0 if the document has no words
        }
    
        index.findfirst(); // Start from the head of the linked list
    
        // Iterate through the linked list of words
        do {
            // Check if the current word matches the input word
            if (index.retrieve().getWord().equals(word)) {
                count++; // Increment the count if a match is found
            }
            index.findnext(); // Move to the next word
        } while (!index.last()); // Continue until we reach the end of the list
    
        // Check the last element (if necessary)
        if (index.last()) {
            if (index.retrieve().getWord().equals(word)) {
                count++; // Increment count for the last word if it matches
            }
        }
    
        return count; // Return the total count of occurrences
    }
}
