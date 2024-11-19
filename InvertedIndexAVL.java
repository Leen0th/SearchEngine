public class InvertedIndexAVL extends AVL<Word> {
    private AVL<Word> index;
    private int size = 0; // Number of unique words in the inverted index

    public InvertedIndexAVL() {
        index = new AVL<>(); 
    }

    public int size() {
        return size;
    }

    public boolean containsWord(String word) {
        word = word.toLowerCase();
        return index.search(word) != null; 
    }

    public void add(String word, int docId) {
        word = word.toLowerCase(); 

        Word existingWord = index.search(word);
        if (existingWord == null) {
            Word newWord = new Word();
            newWord.word = word;
            newWord.addDoc(docId);
            index.insert(word, newWord); 
            size++;
        } else {
            if (!existingWord.searchDocId(docId)) {
                existingWord.addDoc(docId); 
            }
        }
    }

    public void displayInvertedIndex() {
        index.inOrder(); 
    }

    public LinkedList<Integer> getDocumentIDsForWord(String word) {
        Word currentWord = index.search(word.toLowerCase());
        return currentWord != null ? currentWord.getDocIDs() : null; 
    }
}
