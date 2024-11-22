import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;



public class SearchEngine {
    int tokens = 0;
    int vocap = 0;
    LinkedList<String> stopWords;
    InvertedIndex invertedindex;
    InvertedIndexBST invertedindexBST;
    InvertedIndexBST invertedindexBSTvocab;
    Index index;
    InvertedIndexAVL invertedIndexAVL;

    // Custom class to store Document ID and Token Count
    class DocumentTokenCount {
        int docId;
        int tokenCount;

        DocumentTokenCount(int docId, int tokenCount) {
            this.docId = docId;
            this.tokenCount = tokenCount;
        }
    }

    // List to store documents and their token counts
    LinkedList<DocumentTokenCount> docTokenCounts;

    public SearchEngine() {
        this.stopWords = new LinkedList<>();
        this.invertedindex = new InvertedIndex();
        this.invertedindexBST = new InvertedIndexBST();
        this.index = new Index();
        this.invertedindexBSTvocab = new InvertedIndexBST();
        this.invertedIndexAVL = new InvertedIndexAVL();
        this.docTokenCounts = new LinkedList<>();  // Initialize the LinkedList to store document counts
    }

    public void Data(String stopFile, String fileName) {
        try {
            stopWords = loadStopWords(stopFile);
            File docsfile = new File(fileName);
            try (BufferedReader docReader = new BufferedReader(new FileReader(docsfile))) {
                docReader.readLine(); // Skip header line
                String line;
                int lineCount = 0;

                while (lineCount < 50) {
                    line = docReader.readLine();
                    lineCount++;

                    line = line.toLowerCase().replaceAll("[\"]", "");
                    int firstCommaIndex = line.indexOf(',');
                    if (firstCommaIndex != -1) {
                        int docId = Integer.parseInt(line.substring(0, firstCommaIndex).trim());
                        String text = line.substring(firstCommaIndex + 1).trim().replaceAll("\'", "").trim();
                        while (text.contains("-")) {
                            if (text.indexOf("-") == 1)
                                text = text.replaceFirst("-", "");
                            else if (text.charAt(text.indexOf("-") - 2) == ' ')
                                text = text.replaceFirst("-", "");
                            else
                                text = text.replaceFirst("-", " ");
                        }

                        String[] words = text.split("[\\s]+");
                        tokens += words.length;

                        int tokenCountWithoutStopWords = 0;
                        String[] cleanedWords = new String[1600];
                        int indexCounter = 0;

                        for (String word : words) {
                            String cleanedWord = word.replaceAll("[^a-zA-Z0-9]", " ").trim();

                            this.invertedindexBSTvocab.add(cleanedWord, docId);

                            if (!cleanedWord.isEmpty() && !isStopWord(cleanedWord)) {
                                tokenCountWithoutStopWords++;
                                this.invertedindex.add(cleanedWord, docId);
                                this.invertedindexBST.add(cleanedWord, docId);
                                this.invertedIndexAVL.add(cleanedWord, docId);
                                cleanedWords[indexCounter++] = cleanedWord;
                            }
                        }

                        index.addAllDocument(docId, cleanedWords);

                        // Instead of using a map, add the docId and token count to the list
                        docTokenCounts.insert(new DocumentTokenCount(docId, tokenCountWithoutStopWords));
                    }
                }

                vocap = invertedindexBSTvocab.size();

            }
        } catch (FileNotFoundException e) {
            System.out.println("File not found: " + e.getMessage());
        } catch (IOException e) {
            System.out.println("Error reading file: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    // Loads stop words from a specified file into a linked list
    private LinkedList<String> loadStopWords(String stopFile) {
        LinkedList<String> stopWordsList = new LinkedList<>();

        try (BufferedReader stopReader = new BufferedReader(new FileReader(stopFile))) {
            String stopWord;
            while ((stopWord = stopReader.readLine()) != null) {
                String trimmedWord = stopWord.trim();
                if (!trimmedWord.isEmpty()) {
                    stopWordsList.insert(trimmedWord.toLowerCase());
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("Stop words file not found: " + e.getMessage());
        } catch (IOException e) {
            System.out.println("Error reading stop words file: " + e.getMessage());
        }

        return stopWordsList;
    }

    // Checks if a word is a stop word
    private boolean isStopWord(String word) {
        if (stopWords.empty()) return false;
        stopWords.findfirst();
        do {
            if (stopWords.retrieve().equals(word)) {
                return true;
            }
            stopWords.findnext();
        } while (!stopWords.last());
        if (!stopWords.empty() && stopWords.retrieve().equals(word)) {
            return true;
        }
        return false;
    }

    // Method to display documents with their token counts (excluding stop words)
public String displayDocTokenCounts() {
    StringBuilder result = new StringBuilder();

    if (!docTokenCounts.empty()) {
        docTokenCounts.findfirst();
        do {
            DocumentTokenCount doc = docTokenCounts.retrieve();
            result.append("Document ID: ").append(doc.docId)
                  .append(", Token Count: ").append(doc.tokenCount)
                  .append("\n");

            if (docTokenCounts.last()) {
                break; 
            }
            docTokenCounts.findnext();
        } while (true); 
    } else {
        result.append("No documents available.");
    }
    return result.toString();
}

}
