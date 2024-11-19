public class Document {
    int documentID; // the ID of the document 
    LinkedList<Word> index; // the list of terms related to that ID

    // Default constructor that initializes an empty document
    public Document() {
        documentID = 0;
        index = new LinkedList<Word>();
    }

    // Constructor that initializes the document with an ID and an array of words
    public Document(int id, String[] words) {
        documentID = id;
        index = new LinkedList<Word>();
        for (String word : words) {
            index.insert(new Word(new LinkedList<>(), word));
        }
    }

    // Method to add a word to the document's index
    public void addWord(String word) {
        index.insert(new Word(new LinkedList<>(), word));
    }

    // Method to check if a specific word exists in the document's index
    public boolean findWord(String word) {
        if (index.empty()) {
            return false; 
        }

        index.findfirst(); 
        
        do {
            if (index.retrieve().getWord().equals(word)) {
                return true; 
            }
            index.findnext(); 
        } while (!index.last()); 

        if(index.last()){
            if (index.retrieve().getWord().equals(word)) {
                return true; 
            }
        }

        return false; 
    }

    // Method to get the document's ID
    public int getId() {
        return documentID;
    }

    // Method to get the document's content as a single string
    public String getDocumentContent() {
        if (index.empty()) {
            return ""; 
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

    // Method to count the number of occurrences of a specific word in the document
    public int countTermOccurrences(String word) {
        int count = 0; 
    
        if (index.empty()) {
            return count; 
        }
    
        index.findfirst(); 
    
        do {
            if (index.retrieve().getWord().equals(word)) {
                count++; 
            }
            index.findnext(); 
        } while (!index.last()); 
    
        if (index.last()) {
            if (index.retrieve().getWord().equals(word)) {
                count++; 
            }
        }
    
        return count; 
    }
}
