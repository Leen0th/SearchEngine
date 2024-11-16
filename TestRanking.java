public class TestRanking {
    public static void main(String[] args) {
        // Step 1: Initialize Ranking
        Ranking ranking = new Ranking();

        // Step 2: Populate the Index
        String[] data1 = {"market", "sports", "content", "test"};
        String[] data2 = {"example", "sports", "ranking", "market"};
        String[] data3 = {"random", "content", "market"};

        ranking.index.addAllDocument(0, data1); // Add data to Document 0
        ranking.index.addAllDocument(1, data2); // Add data to Document 1
        ranking.index.addAllDocument(2, data3); // Add data to Document 2

        // Step 3: Populate InvertedIndexBST
        for (String word : data1) {
            ranking.invertedBST.add(word, 0);
        }
        for (String word : data2) {
            ranking.invertedBST.add(word, 1);
        }
        for (String word : data3) {
            ranking.invertedBST.add(word, 2);
        }

        // Step 4: Display the content of each document
        System.out.println("################## Document Words ###########################");
        for (int i = 0; i < ranking.index.indexes.length; i++) {
            Document doc = ranking.index.indexes[i];
            if (doc != null && !doc.index.empty()) {
                System.out.println("DocID: " + doc.getId() + " Words: " + doc.getDocumentContent());
            }
        }

        // Step 5: Define the query
        String query = "market sports";

        // Step 6: Calculate scores for each document
        LinkedList<DocumentRank> rankedDocuments = new LinkedList<>();
        for (int i = 0; i < ranking.index.indexes.length; i++) {
            Document doc = ranking.index.indexes[i];
            if (doc == null || doc.index.empty()) continue;

            int score = ranking.calculateDocRankScore(doc, query);
            if (score > 0) {
                insertRanked(rankedDocuments, new DocumentRank(doc.getId(), score));
            }
        }

        // Step 7: Display the ranked results
        System.out.println("################## Ranked Retrieval ###########################");
        System.out.println("## Q: " + query);
        System.out.println("DocID\tScore");

        rankedDocuments.findfirst();
        while (rankedDocuments.retrieve() != null) {
            DocumentRank docRank = rankedDocuments.retrieve();
            System.out.printf("%-8d%-8d%n", docRank.id, docRank.rank);

            if (!rankedDocuments.last()) {
                rankedDocuments.findnext();
            } else {
                break;
            }
        }
    }

    // Helper method to insert DocumentRank in sorted order
    private static void insertRanked(LinkedList<DocumentRank> rankedDocuments, DocumentRank newDocRank) {
        rankedDocuments.findfirst();

        // If the list is empty, insert the first item
        if (rankedDocuments.empty()) {
            rankedDocuments.insert(newDocRank);
            return;
        }

        // Traverse and find the correct position to insert
        while (!rankedDocuments.last()) {
            DocumentRank current = rankedDocuments.retrieve();

            // Sort by descending rank, and by ascending ID for ties
            if (newDocRank.rank > current.rank || 
                (newDocRank.rank == current.rank && newDocRank.id < current.id)) {
                rankedDocuments.insert(newDocRank);
                return;
            }

            rankedDocuments.findnext();
        }

        // Insert at the end if no earlier position is found
        rankedDocuments.insert(newDocRank);
    }
}
