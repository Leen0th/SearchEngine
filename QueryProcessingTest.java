public class QueryProcessingTest {
    public static void main(String[] args) {
        QueryProcessing queryProcessor = new QueryProcessing();

        // Populate the inverted index with test data
        queryProcessor.inverted.add("market", 0);
        queryProcessor.inverted.add("market", 41);
        queryProcessor.inverted.add("sports", 33);
        queryProcessor.inverted.add("sports", 34);
        queryProcessor.inverted.add("sports", 41);
        queryProcessor.inverted.add("warming", 34);
        queryProcessor.inverted.add("warming", 38);
        queryProcessor.inverted.add("business", 8);
        queryProcessor.inverted.add("world", 8);
        queryProcessor.inverted.add("weather", 34);
        queryProcessor.inverted.add("weather", 2);

        // Example queries
        System.out.println("# Q: market AND sports");
        queryProcessor.displayResult(queryProcessor.processQuery("market AND sports"));

        System.out.println("# Q: weather AND warming");
        queryProcessor.displayResult(queryProcessor.processQuery("weather AND warming"));

        System.out.println("# Q: business AND world");
        queryProcessor.displayResult(queryProcessor.processQuery("business AND world"));

        System.out.println("# Q: weather OR warming");
        queryProcessor.displayResult(queryProcessor.processQuery("weather OR warming"));

        System.out.println("# Q: market OR sports");
        queryProcessor.displayResult(queryProcessor.processQuery("market OR sports"));

        System.out.println("# Q: market OR sports AND warming");
        queryProcessor.displayResult(queryProcessor.processQuery("market OR sports AND warming"));
    }
}
