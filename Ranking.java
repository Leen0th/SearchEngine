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
    
    public void rank_query(String query) {
        this.query = query; // Reserve the query

        // Split the query into terms
        String[] terms = query.split("\\s+");
        for (String term : terms) {
            // Get document IDs linked to the term using the inverted index
             LinkedList<Integer> docIDs = invertedBST.getDocumentIDsForWord(term.toLowerCase());
             add_in_one_list_sorted(docIDs); // Add doc IDs to queryDocID
        }
    }

    // Method to insert an ID into the sorted position in queryDocID
    public void insert_sorted_ids_list(Integer id) {
        // Reserve the ID and add it into the right position (low to high)
        queryDocID.findfirst(); // Start from the head

        if (queryDocID.empty() || (queryDocID.last() && queryDocID.retrieve() < id)) {
            queryDocID.insert(id); // Add to the end if empty or greater than last
        } else {
            while (!queryDocID.last()) {
                if (queryDocID.retrieve() >= id) {
                    queryDocID.insert(id); // Insert at the right position
                    return; // Exit after insertion
                }
                queryDocID.findnext(); // Move to the next node
            }
            // Handle the last node case
            if (!queryDocID.last()) {
                queryDocID.insert(id);
            }
        }
    }

    // Method to add all elements of list A of doc IDs related to a term to queryDocID
    public void add_in_one_list_sorted(LinkedList<Integer> A) {
        if (A != null && !A.empty()) { // Check if the list is not null and not empty
            A.findfirst(); // Start from the head of list A
            while (!A.last()) { // Iterate through list A
                Integer id = A.retrieve(); // Get the current element
                insert_sorted_ids_list(id); // Insert the ID in sorted order
                A.findnext(); // Move to the next element
            }
            // Handle the last element in A
            if (!A.empty()) {
                Integer id = A.retrieve();
                insert_sorted_ids_list(id);
            }
        }
    }

}
    
