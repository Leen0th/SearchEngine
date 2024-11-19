public class PerformanceAnalysis {
    public static void main(String[] args) {
        SearchEngine SE = new SearchEngine();
        SE.Data("dataset/stop.txt", "dataset/dataset.csv");

        QueryProcessing queryIndex = new QueryProcessing(SE.index);
        QueryProcessing queryInverted = new QueryProcessing(SE.invertedindex);
        QueryProcessing queryBST = new QueryProcessing(SE.invertedindexBST);
        QueryProcessing queryAVL = new QueryProcessing(SE.invertedIndexAVL);

        String[] booleanQueries = {
            "market AND sports",
            "weather AND warming",
            "business AND world",
            "weather OR warming",
            "market OR sports",
            "market OR sports AND warming"
        };



        System.out.println("Total tokens: " + SE.tokens);
        System.out.println("Total vocab: " + SE.vocap);

        System.out.println("################### Performance Analysis ####################\n");

        // Boolean Retrieval Performance
        for (String query : booleanQueries) {
            System.out.println("Query: \"" + query + "\"");

            // Measure performance for Index
            long start = System.nanoTime();
            queryIndex.processQueryWithIndex(query);
            long end = System.nanoTime();
            System.out.printf("- Index Retrieval (using list of lists): Time: %.2f ms%n", (end - start) / 1_000_000.0);

            // Measure performance for Inverted Index
            start = System.nanoTime();
            queryInverted.processQueryWithInverted(query);
            end = System.nanoTime();
            System.out.printf("- Inverted Index Retrieval (using list of lists): Time: %.2f ms%n", (end - start) / 1_000_000.0);

            // Measure performance for Inverted Index BST
            start = System.nanoTime();
            queryBST.processQueryWithBST(query);
            end = System.nanoTime();
            System.out.printf("- Inverted Index Retrieval (using BST): Time: %.2f ms%n", (end - start) / 1_000_000.0);

            // Measure performance for AVL
            start = System.nanoTime();
            queryAVL.processQueryWithInvertedAVL(query);
            end = System.nanoTime();
            System.out.printf("- Inverted Index Retrieval (using AVL): Time: %.2f ms%n", (end - start) / 1_000_000.0);

            System.out.println();
        }

    }
}
