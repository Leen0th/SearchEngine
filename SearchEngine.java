import java.util.Scanner;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class SearchEngine {

    // Declare the Scanner instance at the class level
    public static Scanner input = new Scanner(System.in);
    int tokens = 0;
    int tokensAfterDeletion = 0;
    LinkedList<String> stopWords;
    InvertedIndex invertedindex;
    Index index;

    public SearchEngine() {
        this.stopWords = new LinkedList<>();
        this.invertedindex = new InvertedIndex();
        this.index = new Index();
    }

    public static void main(String[] args) {
        // Create an instance of SearchEngine and start the data processing
        SearchEngine SE = new SearchEngine();
        SE.processesData("dataset/stop.txt", "dataset/dataset.csv");
    }

    public void processesData(String stopFile, String fileName) {
        try {
            stopWords = loadStopWords(stopFile);

            File docs = new File(fileName);
            try (BufferedReader docReader = new BufferedReader(new FileReader(docs))) {
                docReader.readLine(); // cus the first line is a header

                String line;
                int lineCount = 0;

                while ((line = docReader.readLine()) != null && lineCount < 50) {
                    lineCount++;
                    line = line.toLowerCase();

                    int firstCommaIndex = line.indexOf(',');
                    if (firstCommaIndex != -1) {
                        int docId = Integer.parseInt(line.substring(0, firstCommaIndex).trim());
                        String text = line.substring(firstCommaIndex + 1).trim().replaceAll("\"", "");

                        String[] words = text.split("\\s+");

                        String[] cleanedWords = new String[words.length];
                        int indexCounter = 0;

                        for (String word : words) {
                            String cleanedWord = word.replaceAll("[^a-zA-Z0-9]", "").trim();
                            
                            if (!cleanedWord.isEmpty() && !isStopWord(cleanedWord)) {
                                tokens++;
                                this.invertedindex.add(cleanedWord, docId);
                                tokensAfterDeletion++;

                                cleanedWords[indexCounter++] = cleanedWord;
                            }
                        }

                        index.addAllDocument(docId, cleanedWords);
                        index.printDocument(docId);
                    }
                }
                this.invertedindex.displayInvertedIndex();
            }
        } catch (FileNotFoundException e) {
            System.out.println("File not found: " + e.getMessage());
        } catch (IOException e) {
            System.out.println("Error reading file: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public LinkedList<String> loadStopWords(String stopFile) {
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

    public boolean isStopWord(String word) {
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

    public void displayStopWords() {
        System.out.println("Loaded Stop Words:");
        stopWords.findfirst();
        while (!stopWords.empty()) {
            System.out.print(stopWords.retrieve() + " ");
            stopWords.findnext();
        }
        System.out.println();
    }

    private Word findWord(LinkedList<Word> wordsList, String word) {
        if (wordsList.empty()) return null;
        wordsList.findfirst();
        do {
            Word currentWord = wordsList.retrieve();
            if (currentWord.getWord().equals(word)) {
                return currentWord;
            }
            wordsList.findnext();
        } while (!wordsList.last());
        return null;
    }
}
