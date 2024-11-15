public class QueryProcessing {
    public InvertedIndex inverted;
    public InvertedIndexBST invertedBST;

    public QueryProcessing(InvertedIndexBST invertedBST) {
        this.invertedBST = invertedBST;
    }

    public QueryProcessing(InvertedIndex inverted) {
        this.inverted = inverted;
    }

    // Constructor to initialize the inverted index objects
    public QueryProcessing() {
        this.inverted = new InvertedIndex();
        this.invertedBST = new InvertedIndexBST();
    }

    // Public method to process a query with AND and OR, respecting precedence
    public LinkedList<Integer> processQuery(String query) {
        // Split the query by "OR" first, so each part can be processed separately
        String[] orTerms = query.split("OR");

        LinkedList<Integer> result = new LinkedList<>();

        for (String orTerm : orTerms) {
            orTerm = orTerm.trim();
            LinkedList<Integer> andResult = processAndQuery(orTerm);

            // Add all unique results from andResult to result
            andResult.findfirst();
            Integer docID = andResult.retrieve();
            while (docID != null) {
                if (!result.search(docID)) { // Only insert if docID is not already in result
                    result.insert(docID);
                }
                if (!andResult.last()) {
                    andResult.findnext();
                    docID = andResult.retrieve();
                } else {
                    break;
                }
            }
        }

        return result;
    }

    // Helper method to process a query with only AND terms
    private LinkedList<Integer> processAndQuery(String andQuery) {
        String[] terms = andQuery.split("AND");
        LinkedList<Integer> result = new LinkedList<>();

        if (terms.length == 0) return result;

        LinkedList<Integer> firstTermDocIDs = inverted.getDocumentIDsForWord(terms[0].trim().toLowerCase());
        if (firstTermDocIDs == null) return result;

        result = firstTermDocIDs;

        for (int i = 1; i < terms.length; i++) {
            String term = terms[i].trim().toLowerCase();
            LinkedList<Integer> termDocIDs = inverted.getDocumentIDsForWord(term);
            if (termDocIDs == null) {
                return new LinkedList<>(); // Return empty if any term has no documents
            }
            result = ANDQuery(result, termDocIDs);
        }

        return result;
    }

    // AND Query method for intersecting two LinkedLists
    public LinkedList<Integer> ANDQuery(LinkedList<Integer> A, LinkedList<Integer> B) {
        LinkedList<Integer> result = new LinkedList<>();
        if (A.empty() || B.empty()) return result;

        A.findfirst();
        Integer docID = A.retrieve();
        while (docID != null) {
            if (B.search(docID)) {
                if (!result.search(docID)) { // Only insert if docID is not already in result
                    result.insert(docID);
                }
            }
            if (!A.last()) {
                A.findnext();
                docID = A.retrieve();
            } else {
                break;
            }
        }

        return result;
    }

    // OR Query method for union of two LinkedLists
    public LinkedList<Integer> ORQuery(LinkedList<Integer> A, LinkedList<Integer> B) {
        LinkedList<Integer> result = new LinkedList<>();

        A.findfirst();
        Integer docID = A.retrieve();
        while (docID != null) {
            if (!result.search(docID)) {
                result.insert(docID);
            }
            if (!A.last()) {
                A.findnext();
                docID = A.retrieve();
            } else {
                break;
            }
        }

        B.findfirst();
        docID = B.retrieve();
        while (docID != null) {
            if (!result.search(docID)) {
                result.insert(docID);
            }
            if (!B.last()) {
                B.findnext();
                docID = B.retrieve();
            } else {
                break;
            }
        }

        return result;
    }

    // Method to display the result in the format expected by the test case
    public void displayResult(LinkedList<Integer> result) {
        System.out.print("Result: {");
        result.findfirst();
        boolean first = true;
        Integer docID = result.retrieve();
        while (docID != null) {
            if (!first) {
                System.out.print(", ");
            }
            System.out.print(docID);
            first = false;

            if (!result.last()) {
                result.findnext();
                docID = result.retrieve();
            } else {
                break;
            }
        }
        System.out.println("}\n");
    }
    public LinkedList<Integer> processQueryWithBST(String query) {
        // Split the query by "OR" first
        String[] orTerms = query.split("OR");
    
        LinkedList<Integer> result = new LinkedList<>();
    
        for (String orTerm : orTerms) {
            orTerm = orTerm.trim();
            LinkedList<Integer> andResult = processAndQueryWithBST(orTerm);
            
            // Add unique results to the final result
            result = ORQuery(result, andResult);
        }
    
        return result;
    }
    
    private LinkedList<Integer> processAndQueryWithBST(String andQuery) {
        andQuery = andQuery.toLowerCase();
        String[] terms = andQuery.split("and");
        LinkedList<Integer> result = new LinkedList<>();
    
        if (terms.length == 0) return result;
    
        LinkedList<Integer> firstTermDocIDs = this.invertedBST.getDocumentIDsForWord(terms[0].trim());
        if (firstTermDocIDs == null) return result;
    
        result = firstTermDocIDs;
    
        for (int i = 1; i < terms.length; i++) {
            String term = terms[i].trim();
            LinkedList<Integer> termDocIDs = this.invertedBST.getDocumentIDsForWord(term);
            if (termDocIDs == null) {
                return new LinkedList<>(); // Return empty if any term has no documents
            }
            result = ANDQuery(result, termDocIDs);
        }
    
        return result;
    }
}
