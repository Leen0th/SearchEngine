public class PerformanceAnalysis {
    public static void main(String[] args) {
        SearchEngine SE = new SearchEngine();
        SE.Data("dataset/stop.txt", "dataset/dataset.csv");

        QueryProcessing queryIndex = new QueryProcessing(SE.index);
        QueryProcessing queryInverted = new QueryProcessing(SE.invertedindex);
        QueryProcessing queryBST = new QueryProcessing(SE.invertedindexBST);
        QueryProcessing queryAVL = new QueryProcessing(SE.avl);

        String[] queries = {
            "market AND sports",
            "weather AND warming",
            "business AND world",
            "weather OR warming",
            "market OR sports",
            "market OR sports AND warming"
        };

        System.out.println("Total tokens: " + SE.tokens);
        System.out.println("Total vocap: " + SE.vocap);

        System.out.println("################### Performance Analysis ####################\n");

        for (String query : queries) {
            System.out.println("Query: \"" + query + "\"");

            // Measure performance for Index
            long start = System.nanoTime();
            LinkedList<Integer> resultIndex = queryIndex.processQueryWithIndex(query);
            long end = System.nanoTime();
            System.out.println("- Index Retrieval (using list of lists): Result: " + resultIndex.displaydocs() + " | Time: " + (end - start) + " ns");

            // Measure performance for Inverted Index
            start = System.nanoTime();
            LinkedList<Integer> resultInverted = queryInverted.processQueryWithInverted(query);
            end = System.nanoTime();
            System.out.println("- Inverted Index Retrieval (using list of lists): Result: " + resultInverted.displaydocs() + " | Time: " + (end - start) + " ns");

            // Measure performance for Inverted Index BST
            start = System.nanoTime();
            LinkedList<Integer> resultBST = queryBST.processQueryWithBST(query);
            end = System.nanoTime();
            System.out.println("- Inverted Index Retrieval (using BST): Result: " + resultBST.displaydocs() + " | Time: " + (end - start) + " ns");

            // Measure performance for AVL
            start = System.nanoTime();
            LinkedList<Integer> resultAVL = queryAVL.processQueryWithAVL(query);
            end = System.nanoTime();
            System.out.println("- Inverted Index Retrieval (using AVL): Result: " + resultAVL.displaydocs() + " | Time: " + (end - start) + " ns");

            System.out.println();
        }
    }
}
