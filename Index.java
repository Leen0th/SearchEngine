

public class Index {

    Document[] indexes;

    public Index() {
        indexes = new Document[100];
        for (int i = 0; i < indexes.length; i++) {
            indexes[i] = new Document();
            indexes[i].documentID = i;
        }
    }

    public void addAllDocument(int docID, String[] data) {
        for (int i = 0; i < data.length; i++)
            indexes[docID].addWord(data[i]);
    }

    public void addDocument(int docID, String data) {
        indexes[docID].addWord(data);
    }

public void printDocument(int docID) {
    if (docID < 0 || docID >= indexes.length) {
        System.out.println("Invalid Document ID.");
        return;
    }

    // Check if the document has no words (empty list)
    if (indexes[docID].index.empty()) {
        System.out.println("Empty Document");
    } else {
        System.out.print("Document " + docID + " words: ");
        
        // Start at the first word in the document
        indexes[docID].index.findfirst();
        System.out.print("[");
        // Traverse the linked list and print each word
        while (!indexes[docID].index.last()) {  // Continue until last word
            System.out.print(indexes[docID].index.retrieve().getWord() + " ,");
            indexes[docID].index.findnext();  // Move to the next word
        }
        System.out.print("]");

        // Print the last word
        System.out.print(indexes[docID].index.retrieve().getWord() + " ");
        
        System.out.println();  // Print newline after all words are displayed
    }
}

}