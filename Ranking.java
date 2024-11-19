class DocumentRank {
    int id = 0; 
    int rank = 0; 

    // Constructor to initialize DocumentRank object
    public DocumentRank(int id, int rank) {
        this.id = id;
        this.rank = rank;
    }
}

public class Ranking {


    // Objects for managing terms and document retrieval
    private InvertedIndexBST invertedBST = new InvertedIndexBST();
    private Index index = new Index();

    // Linked lists to store relevant document IDs and their ranks
    private LinkedList<Integer> queryDocID = new LinkedList<>();
    private LinkedList<DocumentRank> doc_ranked = new LinkedList<>();

    // Default constructor
    public Ranking() {
    }

    // Constructor to initialize the inverted index and document index
    public Ranking(InvertedIndexBST invertedBST, Index index) {
        this.invertedBST = invertedBST;
        this.index = index;
    }

    // Retrieve a document by its ID
    public Document getDocById(int id) {
        if (id < 0 || id >= index.indexes.length) {
            return null; // Return null if the ID is invalid
        }
        return index.indexes[id];
    }

    // Calculate term frequency of a term in a document
    public int getTFInDoc(Document doc, String term) {
        int count = 0;

        if (doc.index.empty()) {
            return count; 
        }

        doc.index.findfirst();
        while (!doc.index.last()) {
            if (doc.index.retrieve().getWord().equalsIgnoreCase(term)) {
                count++;
            }
            doc.index.findnext();
        }

        // Check the last word in the document
        if (doc.index.retrieve().getWord().equalsIgnoreCase(term)) {
            count++;
        }

        return count;
    }

    // Calculate the rank/score of a document based on the query terms
    public int calculateDocRankScore(Document doc, String query) {
        int score = 0;
        String[] terms = query.split("\\s+"); // Split query into terms

        for (String term : terms) {
            score += getTFInDoc(doc, term); // Add term frequency to the score
        }

        return score;
    }

    // Rank documents based on a query and return the results as a formatted string
    public String rank_query(String query) {

        queryDocID = new LinkedList<>();
        doc_ranked = new LinkedList<>();

        // Extract document IDs for each term in the query
        String[] terms = query.split("\\s+");
        for (String term : terms) {
            LinkedList<Integer> docIDs = invertedBST.getDocumentIDsForWord(term.toLowerCase());
            add_in_one_list_sorted(docIDs); // Add document IDs to the queryDocID list
        }

        // Calculate scores for each document
        queryDocID.findfirst();
        while (!queryDocID.last()) {
            Integer docID = queryDocID.retrieve();
            Document doc = getDocById(docID);
            if (doc != null) {
                int score = calculateDocRankScore(doc, query);
                doc_ranked.insert(new DocumentRank(docID, score));
            }
            queryDocID.findnext();
        }

        // Handle the last document in the list
        if (!queryDocID.empty()) {
            Integer docID = queryDocID.retrieve();
            Document doc = getDocById(docID);
            if (doc != null) {
                int score = calculateDocRankScore(doc, query);
                doc_ranked.insert(new DocumentRank(docID, score));
            }
        }

        // Convert the ranked list to an array for sorting
        DocumentRank[] rankedArray = new DocumentRank[doc_ranked.size()];
        doc_ranked.findfirst();
        for (int i = 0; i < rankedArray.length; i++) {
            rankedArray[i] = doc_ranked.retrieve();
            doc_ranked.findnext();
        }

        // Sort the array based on rank
        mergeSortDocumentRank(rankedArray, 0, rankedArray.length - 1);

        // Display the sorted results
        return displaySortedRankedResults(rankedArray);
    }

    // Display sorted ranked results
    public String displaySortedRankedResults(DocumentRank[] rankedArray) {
        StringBuilder result = new StringBuilder();
        result.append("DocID   Score\n");
        for (DocumentRank docRank : rankedArray) {
            result.append(String.format("%-8s%-8s%n", docRank.id, docRank.rank));
        }
        return result.toString();
    }

    // Add document IDs from one list to another, ensuring no duplicates
    public void add_in_one_list_sorted(LinkedList<Integer> A) {
        if (A != null && !A.empty()) {
            A.findfirst();
            while (!A.last()) {
                Integer id = A.retrieve();
                if (!queryDocID.search(id)) {
                    queryDocID.insert(id);
                }
                A.findnext();
            }

            if (!A.empty()) {
                Integer id = A.retrieve();
                if (!queryDocID.search(id)) {
                    queryDocID.insert(id);
                }
            }
        }
    }

    // Perform merge sort on an array of DocumentRank objects
    public static void mergeSortDocumentRank(DocumentRank[] A, int l, int r) {
        if (l >= r) return; 
        int m = (l + r) / 2;
        mergeSortDocumentRank(A, l, m);
        mergeSortDocumentRank(A, m + 1, r);
        mergeDocumentRank(A, l, m, r);
    }

    // Merge two sorted subarrays of DocumentRank objects
    private static void mergeDocumentRank(DocumentRank[] A, int l, int m, int r) {
        DocumentRank[] B = new DocumentRank[r - l + 1];
        int i = l, j = m + 1, k = 0;

        while (i <= m && j <= r) {
            if (A[i].rank > A[j].rank) {
                B[k++] = A[i++];
            } else if (A[i].rank < A[j].rank) {
                B[k++] = A[j++];
            } else {
                if (A[i].id < A[j].id) {
                    B[k++] = A[i++];
                } else {
                    B[k++] = A[j++];
                }
            }
        }

        while (i <= m) B[k++] = A[i++];
        while (j <= r) B[k++] = A[j++];

        for (k = 0; k < B.length; k++) {
            A[l + k] = B[k];
        }
    }
}
