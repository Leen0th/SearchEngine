import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class SearchEngine {
    int tokens = 0;
    int tokensAfterDeletion = 0;
    LinkedList<String> stopWords;
    InvertedIndex invertedindex;
    InvertedIndexBST invertedindexBST;
    Index index; // Add an Index instance

    public SearchEngine() {
        this.stopWords = new LinkedList<>();
        this.invertedindex = new InvertedIndex();
        this.invertedindexBST = new InvertedIndexBST();
        this.index = new Index(); // Initialize the Index
    }

    public void Data(String stopFile, String fileName) {
        try {
            // Load stop words into the instance variable
            stopWords = loadStopWords(stopFile);
            
            // Read the document file using BufferedReader
            File docsfile = new File(fileName);
            try (BufferedReader docReader = new BufferedReader(new FileReader(docsfile))) {
                // Skip the first line (if necessary)
                docReader.readLine(); // Assuming the first line is a header
                
                String line;
                int lineCount = 0; // Counter for processed lines

                // Process the document file
                while ((line = docReader.readLine()) != null && lineCount < 50) {
                    lineCount++; // Increment line count here
                    line = line.toLowerCase();

                    // Find the first comma
                    int firstCommaIndex = line.indexOf(',');
                    if (firstCommaIndex != -1) {
                        // Extract the docId from the first cell
                        int docId = Integer.parseInt(line.substring(0, firstCommaIndex).trim()); // Parse the first cell as docId
                        String text = line.substring(firstCommaIndex + 1).trim().replaceAll("\"", ""); // Get the rest of the line

                        // Split the text into words
                        String[] words = text.split("\\s+"); // Split by one or more whitespace characters

                        // Prepare to collect words for this document
                        String[] cleanedWords = new String[words.length];
                        int indexCounter = 0;

                        // Process each word
                        for (String word : words) {
                            String cleanedWord = word.replaceAll("[^a-zA-Z0-9]", "").trim(); // Clean the word
                            
                            // Check if the cleaned word is valid
                            if (!cleanedWord.isEmpty() && !isStopWord(cleanedWord)) {
                                tokens++; // Increment token count only for valid words
                                this.invertedindex.add(cleanedWord, docId); // Add to the inverted index
                                this.invertedindexBST.add(cleanedWord, docId);
                                tokensAfterDeletion++; // Increment count for tokens after deletion
                                
                                // Store valid cleaned words for the document
                                cleanedWords[indexCounter++] = cleanedWord;
                            }
                        }

                        // Add all cleaned words to the index
                        index.addAllDocument(docId, cleanedWords);

 
                        
                        // Print the document with its words
                        //index.printDocument(docId);
                    }
                }
                                       // Optionally, display the inverted index
                                      // this.invertedindex.displayInvertedIndex();
                                      //this.invertedindexBST.displayInvertedIndex();
            }
        } catch (FileNotFoundException e) {
            System.out.println("File not found: " + e.getMessage());
        } catch (IOException e) {
            System.out.println("Error reading file: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
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
    // Method to display the loaded stop words
public void displayStopWords() {
    System.out.println("Loaded Stop Words:");
    stopWords.findfirst(); // Start from the head of the list
    while (!stopWords.empty()) {
        System.out.print(stopWords.retrieve() + " ");
        stopWords.findnext(); // Move to the next node
    }
    System.out.println(); // Print a newline at the end
}

private Word findWord(LinkedList<Word> wordsList, String word) {
    if (wordsList.empty()) return null; // Handle empty list case
    wordsList.findfirst(); // Start from the head of the list
    do {
        Word currentWord = wordsList.retrieve(); // Get the current Word object
        if (currentWord.getWord().equals(word)) {
            return currentWord; // Return the existing Word object if found
        }
        wordsList.findnext(); // Move to the next node
    } while (!wordsList.last()); // Continue until the last node
    return null; // Not found
}
}
