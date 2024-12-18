public class QueryProcessing {
    public InvertedIndex inverted;
    public InvertedIndexBST invertedBST;
    public Index index;
    public InvertedIndexAVL invertedIndexAVL;

     // Constructor for BST-based query processing
    public QueryProcessing(InvertedIndexBST invertedBST) {
        this.invertedBST = invertedBST;
    }

    // Constructor for List-based Inverted Index query processing
    public QueryProcessing(InvertedIndex inverted) {
        this.inverted = inverted;
    }

    // Constructor for Index-based query processing
    public QueryProcessing(Index index) {
        this.index = index;
    }

    // Constructor for InvertedIndexAVL-based query processing
    public QueryProcessing(InvertedIndexAVL invertedIndexAVL) {
        this.invertedIndexAVL = invertedIndexAVL;
    }

    // Method to process a query using the appropriate data structure
    public LinkedList<Integer> processQuery(String query) {
        if (index != null) {
            return processQueryWithIndex(query);
        } else if (inverted != null) {
            return processQueryWithInverted(query);
        } else if (invertedBST != null) {
            return processQueryWithBST(query);
        } else if (invertedIndexAVL != null) {
            return processQueryWithInvertedAVL(query);
        } else {
            throw new IllegalStateException("No valid data structure initialized for query processing.");
        }
    }

     // Method to process a query (contains AND, OR) using the Index
    public LinkedList<Integer> processQueryWithIndex(String query) {
        String[] orTerms = query.split("(?i)\\s+OR\\s+"); 
        LinkedList<Integer> result = new LinkedList<>();

        for (String orTerm : orTerms) {
            orTerm = orTerm.trim();
            LinkedList<Integer> andResult = processAndQueryWithIndex(orTerm);
            result = ORQuery(result, andResult);
        }

        return result;
    }

    // Helper method to process AND queries using the Index
    private LinkedList<Integer> processAndQueryWithIndex(String andQuery) {
        String[] terms = andQuery.split("(?i)\\s+AND\\s+"); 
        LinkedList<Integer> result = new LinkedList<>();

        if (terms.length == 0) return result;

        LinkedList<Integer> firstTermDocIDs = index.getDocumentIDs(terms[0].trim().toLowerCase());
        if (firstTermDocIDs == null) return result;

        result = firstTermDocIDs;

        for (int i = 1; i < terms.length; i++) {
            String term = terms[i].trim().toLowerCase();
            LinkedList<Integer> termDocIDs = index.getDocumentIDs(term);
            if (termDocIDs == null) {
                return new LinkedList<>();
            }
            result = ANDQuery(result, termDocIDs);
        }

        return result;
    }

    // Method to process a query (contains AND, OR) using a List-based Inverted Index
    public LinkedList<Integer> processQueryWithInverted(String query) {
        String[] orTerms = query.split("(?i)\\s+OR\\s+");
        LinkedList<Integer> result = new LinkedList<>();

        for (String orTerm : orTerms) {
            orTerm = orTerm.trim();
            LinkedList<Integer> andResult = processAndQueryWithInverted(orTerm);
            result = ORQuery(result, andResult);
        }

        return result;
    }

     // Helper method to process AND queries using a List-based Inverted Index
    private LinkedList<Integer> processAndQueryWithInverted(String andQuery) {
        String[] terms = andQuery.split("(?i)\\s+AND\\s+");
        LinkedList<Integer> result = new LinkedList<>();

        if (terms.length == 0) return result;

        LinkedList<Integer> firstTermDocIDs = inverted.getDocumentIDsForWord(terms[0].trim().toLowerCase());
        if (firstTermDocIDs == null) return result;

        result = firstTermDocIDs;

        for (int i = 1; i < terms.length; i++) {
            String term = terms[i].trim().toLowerCase();
            LinkedList<Integer> termDocIDs = inverted.getDocumentIDsForWord(term);
            if (termDocIDs == null) {
                return new LinkedList<>();
            }
            result = ANDQuery(result, termDocIDs);
        }

        return result;
    }

    // Method to process a query (contains AND, OR) using a BST-based Inverted Index
    public LinkedList<Integer> processQueryWithBST(String query) {
        String[] orTerms = query.split("(?i)\\s+OR\\s+");
        LinkedList<Integer> result = new LinkedList<>();

        for (String orTerm : orTerms) {
            orTerm = orTerm.trim();
            LinkedList<Integer> andResult = processAndQueryWithBST(orTerm);
            result = ORQuery(result, andResult);
        }

        return result;
    }

    // Helper method to process AND queries using a BST-based Inverted Index
    private LinkedList<Integer> processAndQueryWithBST(String andQuery) {
        String[] terms = andQuery.split("(?i)\\s+AND\\s+");
        LinkedList<Integer> result = new LinkedList<>();

        if (terms.length == 0) return result;

        LinkedList<Integer> firstTermDocIDs = invertedBST.getDocumentIDsForWord(terms[0].trim().toLowerCase());
        if (firstTermDocIDs == null) return result;

        result = firstTermDocIDs;

        for (int i = 1; i < terms.length; i++) {
            String term = terms[i].trim().toLowerCase();
            LinkedList<Integer> termDocIDs = invertedBST.getDocumentIDsForWord(term);
            if (termDocIDs == null) {
                return new LinkedList<>();
            }
            result = ANDQuery(result, termDocIDs);
        }

        return result;
    }

    // Method to process a query (contains AND, OR) using InvertedIndexAVL
    public LinkedList<Integer> processQueryWithInvertedAVL(String query) {
        String[] orTerms = query.split("(?i)\\s+OR\\s+");
        LinkedList<Integer> result = new LinkedList<>();

        for (String orTerm : orTerms) {
            orTerm = orTerm.trim();
            LinkedList<Integer> andResult = processAndQueryWithInvertedAVL(orTerm);
            result = ORQuery(result, andResult);
        }

        return result;
    }

    // Helper method to process AND queries using InvertedIndexAVL
    private LinkedList<Integer> processAndQueryWithInvertedAVL(String andQuery) {
        String[] terms = andQuery.split("(?i)\\s+AND\\s+");
        LinkedList<Integer> result = new LinkedList<>();

        if (terms.length == 0) return result;

        LinkedList<Integer> firstTermDocIDs = getDocumentIDsFromInvertedAVL(terms[0].trim().toLowerCase());
        if (firstTermDocIDs == null) return result;

        result = firstTermDocIDs;

        for (int i = 1; i < terms.length; i++) {
            String term = terms[i].trim().toLowerCase();
            LinkedList<Integer> termDocIDs = getDocumentIDsFromInvertedAVL(term);
            if (termDocIDs == null) {
                return new LinkedList<>();
            }
            result = ANDQuery(result, termDocIDs);
        }

        return result;
    }

    // Helper method to retrieve document IDs for a word from 
    private LinkedList<Integer> getDocumentIDsFromInvertedAVL(String word) {
        return invertedIndexAVL.getDocumentIDsForWord(word);
    }

    // Method to perform an AND operation on two sets of document IDs
    public LinkedList<Integer> ANDQuery(LinkedList<Integer> A, LinkedList<Integer> B) {
        LinkedList<Integer> result = new LinkedList<>();
       
        if (A.empty() || B.empty()) 
            return result;

        A.findfirst();
        Integer docID = A.retrieve();

        while (docID != null) {
            if (B.search(docID)) {
                if (!result.search(docID)) { // to avoid duplicates
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

    // Method to perform an OR operation on two sets of document IDs
    public LinkedList<Integer> ORQuery(LinkedList<Integer> A, LinkedList<Integer> B) {
        LinkedList<Integer> result = new LinkedList<>();

        A.findfirst();
        Integer docID = A.retrieve();

        while (docID != null) {
            if (!result.search(docID)) { // to avoid duplicates
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
            if (!result.search(docID)) { // to avoid duplicates
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
}
