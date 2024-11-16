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
    
        Document doc = indexes[docID];
        String content = doc.getDocumentContent();
    
        if (content.isEmpty()) {
            System.out.println("Empty Document");
        } else {
            System.out.println("Document " + docID + " words: [" + content + "]");
        }
    }    

}