import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

// The SearchEngine class handles indexing and searching documents based on user queries.
public class SearchEngine {
    int tokens = 0;
    int vocap = 0;
    LinkedList<String> stopWords;
    InvertedIndex invertedindex;
    InvertedIndexBST invertedindexBST;
    InvertedIndexBST invertedindexBSTvocab;
    Index index; 
    Ranking ranking;

    // Constructor initializes the search engine components.
    public SearchEngine() {
        this.stopWords = new LinkedList<>();
        this.invertedindex = new InvertedIndex();
        this.invertedindexBST = new InvertedIndexBST();
        this.index = new Index(); 
        this.invertedindexBSTvocab = new InvertedIndexBST();
    }

    // The Data method loads stop words and processes documents to build the index.
    public void Data(String stopFile, String fileName) {
        try {
            stopWords = loadStopWords(stopFile);
            File docsfile = new File(fileName);
            try (BufferedReader docReader = new BufferedReader(new FileReader(docsfile))) {
                docReader.readLine(); 
                
                String line;
                int lineCount = 0;

                while (lineCount < 50 && (line = docReader.readLine()) != null) {
                    lineCount++;
                    line = line.toLowerCase().replaceAll("[\"]", "");
                    int firstCommaIndex = line.indexOf(',');
                    if (firstCommaIndex != -1) {
                        int docId = Integer.parseInt(line.substring(0, firstCommaIndex).trim());
                        String text = line.substring(firstCommaIndex + 1).trim().replaceAll("\'", "").replaceAll("-", " ").replaceAll("[^a-zA-Z0-9]", " ").trim();
                        String[] words = text.split("\\s+");
                        tokens += words.length;

                        String[] cleanedWords = new String[1600];
                        int indexCounter = 0;

                        for (String word : words) {
                            String cleanedWord = word.replaceAll("[^a-zA-Z0-9]", "").trim();
                            if (!cleanedWord.isEmpty()) {
                                this.invertedindexBSTvocab.add(cleanedWord, docId);
                                if (!isStopWord(cleanedWord)) {
                                    this.invertedindex.add(cleanedWord, docId);
                                    this.invertedindexBST.add(cleanedWord, docId);
                                    cleanedWords[indexCounter++] = cleanedWord;
                                }
                            }
                        }

                        index.addAllDocument(docId, cleanedWords);
                    }
                }

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
        System.out.println("Total tokens: " + tokens);
        System.out.println("Total vocap: " + vocap);
    }

    // The loadStopWords method reads stop words from a file and returns them as a LinkedList.
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

    // The isStopWord method checks if a given word is in the list of stop words.
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

    // The searchAndRank method executes a search query and ranks the results.
    public void searchAndRank(String query) {
        this.ranking = new Ranking(invertedindexBST, index);
        ranking.rank_query(query);
    }
}
