class DocumentRank {
    int id;   // Document ID
    int rank; // Document rank/score

    // Constructor to initialize id and rank
    public DocumentRank(int i, int r) {
        id = i;
        rank = r;
    }

    // Method to display id and rank in a formatted way
    public void display() {
        // Print formatted output (aligned columns)
        System.out.printf("%-8d%-8d%n", id, rank);
    }
}

public class Ranking {

    // Initialize variables
    int DocID = 0; // ID of the document
    int Score = 0; // Rank/Score of the document
    String query = ""; // The query input

    // Initialize objects
    InvertedIndexBST invertedBST = new InvertedIndexBST(); // Object to access all documents related to a term
    Index index = new Index(); // Object to access all terms in a document

    // Initialize linked lists
    LinkedList<Integer> queryDocID = new LinkedList<>(); // To store all document IDs appearing as terms in the query
    LinkedList<DocumentRank> doc_ranked = new LinkedList<>(); // To store Doc_rank objects in sorted order



    public Document getDocById(int id) {
        // Validate the document ID
        if (id < 0 || id >= index.indexes.length) {
            return null; // Return null for invalid IDs
        }
        return index.indexes[id]; // Retrieve the document directly from the `indexes` array
    }
    
    public int getTFInDoc(Document doc, String term) {
        int count = 0;
    
        if (doc.index.empty()) {
            return count; // Return 0 if the document is empty
        }
    
        doc.index.findfirst(); // Start traversing the linked list
    
        while (!doc.index.last()) {
            if (doc.index.retrieve().getWord().equalsIgnoreCase(term)) {
                count++; // Increment the count if the term matches
            }
            doc.index.findnext(); // Move to the next word
        }
    
        // Check the last word in the list
        if (doc.index.retrieve().getWord().equalsIgnoreCase(term)) {
            count++;
        }
    
        return count; // Return the term frequency
    }
    
    public int calculateDocRankScore(Document doc, String query) {
        int score = 0;
    
        // Split the query into terms
        String[] terms = query.split("\\s+");
    
        for (String term : terms) {
            // Use `getTFInDoc` to get the term frequency in the document
            score += getTFInDoc(doc, term);
        }
        return score; // Return the total score
    }
    
    
    
    
}
    
