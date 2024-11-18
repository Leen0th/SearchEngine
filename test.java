public class test {

    // Declare the SearchEngine instance at the class level
    public static SearchEngine SE = new SearchEngine();

    public static void main(String[] args) {
        // Load the dataset and stop words
        SE.Data("dataset/stop.txt", "dataset/dataset.csv");

        // Query for LinkedList-based inverted index
        QueryProcessing queryProcessing = new QueryProcessing(SE.invertedindex);
        QueryProcessing queryProcessingBST = new QueryProcessing(SE.invertedindexBST);

        // Initialize Ranking with InvertedIndexBST and Index
        Ranking ranking = new Ranking(SE.invertedindexBST, SE.index);

        System.out.println("################### Boolean Retrieval LinkedList ####################");

        // Boolean Retrieval Queries using LinkedList
        System.out.println("# Q: market AND sports");
        queryProcessing.displayResult(queryProcessing.processQuery("market AND sports"));

        System.out.println("# Q: weather AND warming");
        queryProcessing.displayResult(queryProcessing.processQuery("weather AND warming"));

        System.out.println("# Q: business AND world");
        queryProcessing.displayResult(queryProcessing.processQuery("business AND world"));

        System.out.println("# Q: weather OR warming");
        queryProcessing.displayResult(queryProcessing.processQuery("weather OR warming"));

        System.out.println("# Q: market OR sports");
        queryProcessing.displayResult(queryProcessing.processQuery("market OR sports"));

        System.out.println("# Q: market OR sports AND warming");
        queryProcessing.displayResult(queryProcessing.processQuery("market OR sports AND warming"));

        System.out.println("################### Boolean Retrieval BST ####################");

        // Boolean Retrieval Queries using BST
        System.out.println("# Q: market AND sports");
        queryProcessingBST.displayResult(queryProcessingBST.processQueryWithBST("market AND sports"));

        System.out.println("# Q: weather AND warming");
        queryProcessingBST.displayResult(queryProcessingBST.processQueryWithBST("weather AND warming"));

        System.out.println("# Q: business AND world");
        queryProcessingBST.displayResult(queryProcessingBST.processQueryWithBST("business AND world"));

        System.out.println("# Q: weather OR warming");
        queryProcessingBST.displayResult(queryProcessingBST.processQueryWithBST("weather OR warming"));

        System.out.println("# Q: market OR sports");
        queryProcessingBST.displayResult(queryProcessingBST.processQueryWithBST("market OR sports"));

        System.out.println("# Q: market OR sports AND warming");
        queryProcessingBST.displayResult(queryProcessingBST.processQueryWithBST("market OR sports AND warming"));

        System.out.println("################## Ranked Retrieval ###########################");

        // Ranked Retrieval Queries
        System.out.println("\n## Q: market sports");
        ranking.rank_query("market sports");

        System.out.println("\n## Q: weather warming");
        ranking.rank_query("weather warming");

        System.out.println("\n## Q: business world market");
        ranking.rank_query("business world market");
    }
}
