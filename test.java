public class test{

    // Declare the Scanner instance at the class level
    public static SearchEngine SE = new SearchEngine();


    public static void main(String[] args) {
        
        SE.Data("C:\\Users\\reema\\Downloads\\DS My Work-20241114T234734Z-001\\DS My Work\\dataset\\stop.txt","C:\\Users\\reema\\Downloads\\DS My Work-20241114T234734Z-001\\DS My Work\\dataset\\dataset.csv");

        // query for linkedlist invertedindex

        QueryProcessing queryProcessing = new QueryProcessing(SE.invertedindex);
        QueryProcessing queryProcessingBST = new QueryProcessing(SE.invertedindexBST);

        // Process a query
        
        System.out.println("#Q : market AND sports");
        String query = "market AND sports";
        LinkedList<Integer> results = queryProcessing.processQuery(query);
        queryProcessing.displayResult(results);

        System.out.println("#Q : weather AND warming");
        query = "weather AND warming";
        results = queryProcessing.processQuery(query);
        queryProcessing.displayResult(results);

        System.out.println("#Q : business AND world");
        query = "business AND world";
        results = queryProcessing.processQuery(query);
        queryProcessing.displayResult(results);

        System.out.println("#Q : weather OR warming");
        query = "weather OR warming";
        results = queryProcessing.processQuery(query);
        queryProcessing.displayResult(results);

        System.out.println("#Q : market OR sports");
         query = "market OR sports";
        results = queryProcessing.processQuery(query);
        queryProcessing.displayResult(results);

        System.out.println("Q# : market OR sports AND warming");
        query = "market OR sports AND warming";
        results = queryProcessing.processQuery(query);
        queryProcessing.displayResult(results);

        System.out.println("\nquery for BST invertedindex\n");

        System.out.println("#Q : market AND sports");
         query = "market AND sports";
        results = queryProcessingBST.processQueryWithBST(query);
        queryProcessing.displayResult(results);

        System.out.println("#Q : weather AND warming");
        query = "weather AND warming";
        results = queryProcessingBST.processQueryWithBST(query);
        queryProcessing.displayResult(results);

        System.out.println("#Q : business AND world");
        query = "business AND world";
        results = queryProcessingBST.processQueryWithBST(query);
        queryProcessing.displayResult(results);

        System.out.println("#Q : weather OR warming");
        query = "weather OR warming";
        results = queryProcessingBST.processQueryWithBST(query);
        queryProcessing.displayResult(results);

        System.out.println("#Q : market OR sports");
         query = "market OR sports";
         results = queryProcessingBST.processQueryWithBST(query);
        queryProcessing.displayResult(results);

        System.out.println("Q# : market OR sports AND warming");
        query = "market OR sports AND warming";
        results = queryProcessingBST.processQueryWithBST(query);
        queryProcessing.displayResult(results);

    }
}
