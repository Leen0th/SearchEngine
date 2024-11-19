public class Index {

    Document[] indexes; // Array of documents to serve as the index

    // Default constructor that initializes the index with 50 empty documents
    public Index() {
        indexes = new Document[50];
        for (int i = 0; i < indexes.length; i++) {
            indexes[i] = new Document();
            indexes[i].documentID = i;
        }
    }

    // Method to add multiple words to a specific document in the index
    public void addAllDocument(int docID, String[] data) {
        for (int i = 0; i < data.length; i++)
            indexes[docID].addWord(data[i]); // Add each word from the array to the document
    }

    // Method to add a single word to a specific document in the index
    public void addDocument(int docID, String data) {
        indexes[docID].addWord(data);
    }

    // Method to print the content of a specific document
    public void printDocument(int docID) {
        if (docID < 0 || docID >= indexes.length) {
            System.out.println("Invalid Document ID.");
            return;
        }
    
        Document doc = indexes[docID];
        String content = doc.getDocumentContent();
    
        if (content.isEmpty()) {
            System.out.println("Empty Document");
        } else {
            System.out.println("Document " + docID + " words: [" + content + "]");
        }
    }
    
    
    // Method to retrieve all document IDs where a specific term appears
    public LinkedList<Integer> getDocumentIDs(String term) {
        LinkedList<Integer> result = new LinkedList<>();
        
        for (int i = 0; i < indexes.length; i++) {
            if (indexes[i].findWord(term)) {
                result.insert(i); 
            }
        }
        
        return result;
    }

}