public class Word { 
    String word; // the term
    LinkedList<Integer> docIDs; // the list of IDs related to that term

    // Default constructor that initializes an empty Word object
    public Word() {
        this.docIDs = new LinkedList<>(); 
        this.word = "";
    }

    // Constructor that initializes the Word object with a given word and a list of document IDs
    public Word(LinkedList<Integer> docIDs, String word) {
        this.docIDs = docIDs;
        this.word = word != null ? word : ""; 
    }

    // Method to search for a specific document ID in the list
    public boolean searchDocId(int docId) { 
        return docIDs.search(docId);
    }

    // Method to add a document ID to the list if it doesn't already exist
    public void addDoc(int docId) {
        if (searchDocId(docId) == false) {
            docIDs.insert(docId); 
            
        } 
    }
   
    // Method to retrieve the word in lowercase
    public String getWord() {
        return word.toLowerCase();
    }

    // Method to represent the Word object as a string
    @Override
    public String toString() {
    String docs=docIDs.displaydocs();

        return word+"\nducment id : "+docs+"\n";
    }

    // Method to retrieve the list of document IDs
    public LinkedList<Integer> getDocIDs() {
        return docIDs;
    }
}
