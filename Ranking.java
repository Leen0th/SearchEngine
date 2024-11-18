class DocumentRank {
    int id; // Document ID
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
    
    public Ranking() {
    }
    
    
    
    
    
    public Ranking(InvertedIndexBST invertedBST, Index index) {
        this.invertedBST = invertedBST;
        this.index = index;
    }
    
    
    
    
    
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
    
        // Clear previous results
        queryDocID = new LinkedList<>(); 
        doc_ranked = new LinkedList<>(); 
    
        // Split the query into terms
        String[] terms = query.split("\\s+");
        for (String term : terms) {
            // Get document IDs linked to the term using the inverted index
            LinkedList<Integer> docIDs = invertedBST.getDocumentIDsForWord(term.toLowerCase());
            add_in_one_list_sorted(docIDs); // Ensure no duplicates
        }
    
        // Calculate scores for each document ID found in queryDocID
        queryDocID.findfirst(); // Start from the head of the list
        while (!queryDocID.last()) {
            Integer docID = queryDocID.retrieve();
            Document doc = getDocById(docID);
            if (doc != null) {
                int score = calculateDocRankScore(doc, query);
                doc_ranked.insert(new DocumentRank(docID, score)); // Insert directly into the linked list
            }
            queryDocID.findnext(); // Move to the next document ID
        }
    
        // Handle the last element in queryDocID
        if (!queryDocID.empty()) {
            Integer docID = queryDocID.retrieve();
            Document doc = getDocById(docID);
            if (doc != null) {
                int score = calculateDocRankScore(doc, query);
                doc_ranked.insert(new DocumentRank(docID, score)); // Insert into the linked list
            }
        }
    
        // Convert linked list to array for sorting
        DocumentRank[] rankedArray = new DocumentRank[doc_ranked.size()];
        doc_ranked.findfirst();
        for (int i = 0; i < rankedArray.length; i++) {
            rankedArray[i] = doc_ranked.retrieve();
            doc_ranked.findnext();
        }
    
        // Sort the array using merge sort
        mergeSortDocumentRank(rankedArray, 0, rankedArray.length - 1);
    
        // Now display the sorted results
        displaySortedRankedResults(rankedArray); // New method to display sorted results
    }
    
    public void displaySortedRankedResults(DocumentRank[] rankedArray) {
        System.out.println("DocID   Score");
        for (DocumentRank docRank : rankedArray) {
            docRank.display(); // Display the document ID and score
        }
    }
    
    // Method to add all elements of list A of doc IDs related to a term to queryDocID
    public void add_in_one_list_sorted(LinkedList<Integer> A) {
        if (A != null && !A.empty()) { // Check if the list is not null and not empty
            A.findfirst(); // Start from the head of list A
            while (!A.last()) { // Iterate through list A
                Integer id = A.retrieve(); // Get the current element
                if (!queryDocID.search(id)) { // Check if id is already in queryDocID
                    queryDocID.insert(id); // Insert only if it's not already there
                }
                A.findnext(); // Move to the next element
            }
            // Handle the last element in A
            if (!A.empty()) {
                Integer id = A.retrieve();
                if (!queryDocID.search(id)) { // Check for the last element
                    queryDocID.insert(id);
                }
            }
        }
    }
    
    
  
    
    public static void mergeSortDocumentRank(DocumentRank[] A, int l, int r) {
        if (l >= r)
            return; // Base case
        int m = (l + r) / 2; // Find the middle point
        mergeSortDocumentRank(A, l, m); // Sort first half
        mergeSortDocumentRank(A, m + 1, r); // Sort second half
        mergeDocumentRank(A, l, m, r); // Merge
    }
    
    private static void mergeDocumentRank(DocumentRank[] A, int l, int m, int r) {
        DocumentRank[] B = new DocumentRank[r - l + 1];
        int i = l, j = m + 1, k = 0;
    
        // Merge the two halves
        while (i <= m && j <= r) {
            // Sort primarily by score (descending)
            if (A[i].rank > A[j].rank) {
                B[k++] = A[i++];
            } else if (A[i].rank < A[j].rank) {
                B[k++] = A[j++];
            } else {
                // If scores are equal, sort by DocID (ascending)
                if (A[i].id < A[j].id) {
                    B[k++] = A[i++];
                } else {
                    B[k++] = A[j++];
                }
            }
        }
    
        // Copy remaining elements from the left half, if any
        while (i <= m) B[k++] = A[i++];
        // Copy remaining elements from the right half, if any
        while (j <= r) B[k++] = A[j++];
    
        // Copy back the merged elements to the original array
        for (k = 0; k < B.length; k++)
            A[l + k] = B[k];
    }
    }
