public class Word { 
    String word; 
    LinkedList<Integer> docIDs;

    public Word(LinkedList<Integer> docIDs, String word) {
        this.docIDs = docIDs;
        this.word = word != null ? word : ""; 
    }

    public Word() {
        this.docIDs = new LinkedList<>(); 
        this.word = "";
    }

    public boolean searchDocId(int docId) { 
        return docIDs.search(docId);
    }

    public void addDoc(int docId) {
        // Check if the document ID already exists
        if (searchDocId(docId) == false) {
            // Document ID not found, proceed to add
            docIDs.insert(docId); 
            
        } 
    }
   

    public String getWord() {
        return word.toLowerCase();
    }
    @Override
    public String toString() {
    String docs=docIDs.displaydocs();

        return word+"\nducment id : "+docs+"\n";// Return the word itself
    }

    public LinkedList<Integer> getDocIDs() {
        return docIDs;
    }
}
