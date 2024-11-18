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
    Index index; // Add an Index instance
    Ranking ranking;

    public SearchEngine() {
        this.stopWords = new LinkedList<>();
        this.invertedindex = new InvertedIndex();
        this.invertedindexBST = new InvertedIndexBST();
        this.index = new Index(); // Initialize the Index
        this.invertedindexBSTvocab = new InvertedIndexBST();
    }

    public void Data(String stopFile, String fileName) {
        try {
            // Load stop words into the instance variable
            stopWords = loadStopWords(stopFile);
            
            // Read the document file using BufferedReader
            File docsfile = new File(fileName);
            try (BufferedReader docReader = new BufferedReader(new FileReader(docsfile))) {
                // Skip the first line (if necessary)
                docReader.readLine(); // Read the header line
                
                String line;
                int lineCount = 0; // Counter for processed lines
             

                // Process the document file
                while (lineCount < 50) {
                    line = docReader.readLine();
                    lineCount++; // Increment line count here

                    // Convert to lowercase and handle potential formatting issues
                    line = line.toLowerCase().replaceAll("[\"]", ""); // Remove quotes

                    // Find the first comma
                    int firstCommaIndex = line.indexOf(',');
                    if (firstCommaIndex != -1) {
                        // Extract the docId from the first cell
                        int docId = Integer.parseInt(line.substring(0, firstCommaIndex).trim()); // Parse the first cell as docId
                        String text = line.substring(firstCommaIndex + 1).trim().replaceAll("\"", "").trim(); // Extract the content after the first comma
                        text = text.replaceAll("-", " ").trim();
                        text = text.replaceAll("[^a-zA-Z0-9]", " ").trim();
                        // Split the text into words
                        String[] words = text.split("[\\s]+"); // Split by one or more whitespace characters

                        // Prepare to collect words for this document
                        String[] cleanedWords = new String[1600];
                        int indexCounter = 0;

                        // Process each word
                        for (String word : words) {
                            String cleanedWord = word.replaceAll("[^a-zA-Z0-9]", " ").trim(); // Clean the word

                            // Count tokens (every valid word)
                            tokens++; // Increment token count for every word
                            this.invertedindexBSTvocab.add(cleanedWord, docId);

                            // Check if the cleaned word is valid (not a stop word)
                            if (!cleanedWord.isEmpty() && !isStopWord(cleanedWord)) {
                                // Add to the inverted index
                                this.invertedindex.add(cleanedWord, docId); // Add to the inverted index
                                this.invertedindexBST.add(cleanedWord, docId);

                                // Store valid cleaned words for the document
                                cleanedWords[indexCounter++] = cleanedWord;
                            }
                        }

                        // Add all cleaned words to the index
                        index.addAllDocument(docId, cleanedWords);
                    }
                }
                while (lineCount < 35) {
                    line = docReader.readLine();
                    lineCount++; // Increment line count here

                    // Convert to lowercase and handle potential formatting issues
                    line = line.toLowerCase().replaceAll("[\"]", ""); // Remove quotes

                    // Find the first comma
                    int firstCommaIndex = line.indexOf(',');
                    if (firstCommaIndex != -1) {
                        // Extract the docId from the first cell
                        int docId = Integer.parseInt(line.substring(0, firstCommaIndex).trim()); // Parse the first cell as docId
                        String text = line.substring(firstCommaIndex + 1).trim().replaceAll("\"", "").replace("-", " "); // Extract the content after the first comma and replace hyphens with spaces

                        // Split the text into words
                        String[] words = text.split(" "); // Split by one or more whitespace characters

                        // Prepare to collect words for this document
                        String[] cleanedWords = new String[words.length];
                        int indexCounter = 0;

                        // Process each word
                        for (String word : words) {
                            String cleanedWord = word.replaceAll("[^a-zA-Z0-9]", "").trim(); // Clean the word

                            // Count tokens (every valid word)
                            if (!cleanedWord.isEmpty()) {
                                tokens++; // Increment token count for every word
                            }

                            // Check if the cleaned word is valid (not a stop word)
                            if (!cleanedWord.isEmpty() && !isStopWord(cleanedWord)) {
                                // Add to the inverted index
                                this.invertedindex.add(cleanedWord, docId); // Add to the inverted index
                                this.invertedindexBST.add(cleanedWord, docId);

                                // Store valid cleaned words for the document
                                cleanedWords[indexCounter++] = cleanedWord;
                            }
                        }

                        // Add all cleaned words to the index
                        index.addAllDocument(docId, cleanedWords);
                    }
                }

                // Update the vocabulary count

            }
        } catch (FileNotFoundException e) {
            System.out.println("File not found: " + e.getMessage());
        } catch (IOException e) {
            System.out.println("Error reading file: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
        int s = invertedindexBSTvocab.size();
        vocap = s;
    }

    // Load stop words from the file into a String array
    private LinkedList<String> loadStopWords(String stopFile) {
        LinkedList<String> stopWordsList = new LinkedList<String>();

        try (BufferedReader stopReader = new BufferedReader(new FileReader(stopFile))) {
            String stopWord;
            while ((stopWord = stopReader.readLine()) != null) {
                String trimmedWord = stopWord.trim();
                if (!trimmedWord.isEmpty()) { // Add only non-empty words
                    stopWordsList.insert(trimmedWord.toLowerCase()); // Convert to lowercase if needed
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("Stop words file not found: " + e.getMessage());
        } catch (IOException e) {
            System.out.println("Error reading stop words file: " + e.getMessage());
        }

        return stopWordsList; // Return the LinkedList
    }

    private boolean isStopWord(String word) {
        if (stopWords.empty()) return false; // Early return if the list is empty
        stopWords.findfirst(); // Start from the head of the list
        do {
            if (stopWords.retrieve().equals(word)) {
                return true; // Found a match
            }
            stopWords.findnext(); // Move to the next node
        } while (!stopWords.last()); // Loop until the last node
        // Check the last word separately
        if (!stopWords.empty() && stopWords.retrieve().equals(word)) {
            return true;
        }
        return false; // Not found
    }

    public void searchAndRank(String query) {
        this.ranking = new Ranking(invertedindexBST, index);
        ranking.rank_query(query);
    }
}
