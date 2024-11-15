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

}