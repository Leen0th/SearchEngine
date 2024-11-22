import javax.swing.*;
import java.awt.*;

public class SearchEngineGUI {
    private final JFrame frame;
    private final JTextArea outputArea;
    private final JTextArea vocabArea; 
    private final JTextArea tokenArea; 

    // Search engine and supporting components
    private static final SearchEngine SE = new SearchEngine();
    private static final QueryProcessing queryProcessingIndex = new QueryProcessing(SE.index);
    private static final QueryProcessing queryProcessing = new QueryProcessing(SE.invertedindex);
    private static final QueryProcessing queryProcessingBST = new QueryProcessing(SE.invertedindexBST);
    private static final QueryProcessing queryProcessingAVL = new QueryProcessing(SE.invertedIndexAVL);

    private static final Ranking ranking = new Ranking(SE.invertedindexBST, SE.index);

    public SearchEngineGUI() {
        // Load dataset and stop words
        SE.Data(
            "C:\\Users\\renad\\OneDrive\\المستندات\\NetBeansProjects\\DSSProject1\\src\\main\\java\\rand\\dssproject1\\dataset\\stop.txt",
            "C:\\Users\\renad\\OneDrive\\المستندات\\NetBeansProjects\\DSSProject1\\src\\main\\java\\rand\\dssproject1\\dataset\\dataset.csv"
        );

        // Main frame setup
        frame = new JFrame("Modern SearchEngine");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1000, 700);
        frame.setLayout(new GridBagLayout()); 
        GridBagConstraints gbc = new GridBagConstraints();
        frame.setBackground(new Color(240, 240, 240)); 

        // Title label (Top center)
        JLabel titleLabel = new JLabel(" SearchEngine 💡", JLabel.CENTER);
        titleLabel.setFont(new Font("Sans Serif", Font.BOLD, 26));
        titleLabel.setForeground(new Color(34, 45, 65)); 
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 3; 
        gbc.insets = new Insets(10, 10, 10, 10);
        frame.add(titleLabel, gbc);

        // Tokens and vocabulary panel 
        JPanel topPanel = new JPanel();
        topPanel.setLayout(new GridLayout(1, 2, 20, 0)); 
        topPanel.setBackground(new Color(240, 240, 240)); 
        topPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); 

        // Token Area 
        tokenArea = new JTextArea();
        tokenArea.setEditable(false);
        tokenArea.setFont(new Font("Monospaced", Font.PLAIN, 16));
        tokenArea.setBackground(new Color(220, 220, 220)); 
        tokenArea.setText("Total Tokens: " + SE.tokens); 
        tokenArea.setMargin(new Insets(10, 10, 10, 10));
        JScrollPane tokenScroll = new JScrollPane(tokenArea);

        // Vocabulary Area 
        vocabArea = new JTextArea();
        vocabArea.setEditable(false);
        vocabArea.setFont(new Font("Monospaced", Font.PLAIN, 16));
        vocabArea.setBackground(new Color(220, 220, 220)); 
        vocabArea.setText("Total Vocabulary Size: " + SE.vocap); 
        vocabArea.setMargin(new Insets(10, 10, 10, 10));
        JScrollPane vocabScroll = new JScrollPane(vocabArea);

        topPanel.add(tokenScroll);
        topPanel.add(vocabScroll);

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 3; 
        frame.add(topPanel, gbc);

        // Button Panel 
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));
        buttonPanel.setBackground(new Color(240, 240, 240));

        // Create buttons
        JButton retrieveTerm = createStyledButton("Retrieve Term");
        JButton booleanRetrieval = createStyledButton("Boolean Retrieval");
        JButton rankedRetrieval = createStyledButton("Ranked Retrieval");
        JButton printIndexedDocs = createStyledButton(" Indexed Documents");
        JButton printIndexedTokens = createStyledButton(" Indexed Tokens");

        // Add action listeners to buttons
        retrieveTerm.addActionListener(e -> retrieveTerm());
        booleanRetrieval.addActionListener(e -> booleanRetrieval());
        rankedRetrieval.addActionListener(e -> rankedRetrieval());
        printIndexedDocs.addActionListener(e -> printIndexedDocuments());
        printIndexedTokens.addActionListener(e -> printIndexedTokens());
        

        // Add buttons to panel
        buttonPanel.add(retrieveTerm);
        buttonPanel.add(booleanRetrieval);
        buttonPanel.add(rankedRetrieval);
        buttonPanel.add(printIndexedDocs);
        buttonPanel.add(printIndexedTokens);

        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 3;
        frame.add(buttonPanel, gbc);

        // Bottom panel for results 
        JPanel resultsPanel = new JPanel();
        resultsPanel.setLayout(new BorderLayout());
        resultsPanel.setBackground(Color.WHITE);
        resultsPanel.setBorder(BorderFactory.createTitledBorder("Results"));

        // Initialize outputArea and add it to the results panel
        outputArea = new JTextArea(); 
        outputArea.setEditable(false);
        outputArea.setFont(new Font("Monospaced", Font.PLAIN, 14));
        resultsPanel.add(new JScrollPane(outputArea), BorderLayout.CENTER);  

        // Properly position the results panel
        gbc.gridx = 0;         
        gbc.gridy = 3;          
        gbc.gridwidth = 0;      
        gbc.fill = GridBagConstraints.BOTH;  
        gbc.weightx = 1.0;      
        gbc.weighty = 1.0;    

        frame.add(resultsPanel, gbc); 

        // Finalize the frame setup
        frame.revalidate();
        frame.repaint();
        frame.setVisible(true);
    }

    private JButton createStyledButton(String text) {
        JButton button = new JButton(text);
        button.setFont(new Font("Sans Serif", Font.BOLD, 16)); 
        button.setBackground(new Color(220, 220, 220)); 
        button.setForeground(new Color(34, 45, 65)); 
        button.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(180, 180, 180), 1), 
                BorderFactory.createEmptyBorder(10, 20, 10, 20)
        ));
        button.setFocusPainted(false); 
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        return button;
    }     

    // Action listeners for buttons
    private void retrieveTerm() {
        String query = JOptionPane.showInputDialog(frame, "Enter a term to retrieve:", "Retrieve a Term", JOptionPane.QUESTION_MESSAGE);

        if (query != null && !query.trim().isEmpty()) {
            String[] options = {"Index", "InvertedIndex with BST", "InvertedIndex", "InvertedIndex with AVL"};
            String choice = (String) JOptionPane.showInputDialog(frame, "Select Retrieval Method:", "Retrieve a Term",
                    JOptionPane.QUESTION_MESSAGE, null, options, options[0]);

            if (choice != null) {
                outputArea.setText("Query: " + query + "\n\n");
                switch (choice) {
                    case "Index" -> {
                        outputArea.append("Results (Index):\n");
                        outputArea.append(formatResult(queryProcessingIndex.processQuery(query)) + "\n");
                    }
                    case "InvertedIndex with BST" -> {
                        outputArea.append("Results (BST):\n");
                        outputArea.append(formatResult(queryProcessingBST.processQuery(query)) + "\n");
                    }
                    case "InvertedIndex" -> {
                        outputArea.append("Results (InvertedIndex):\n");
                        outputArea.append(formatResult(queryProcessing.processQuery(query)) + "\n");
                    }
                    case "InvertedIndex with AVL" -> {
                        outputArea.append("Results (AVL):\n");
                        outputArea.append(formatResult(queryProcessingAVL.processQuery(query)) + "\n");
                    }
                }
            }
        }
    }

    private void booleanRetrieval() {
        String query = JOptionPane.showInputDialog(frame, "Enter a Boolean query (e.g., market AND sports):", "Boolean Retrieval", JOptionPane.QUESTION_MESSAGE);

        if (query != null && !query.trim().isEmpty()) {
            outputArea.setText("Boolean Query: " + query + "\n\n");
            outputArea.append("Results (Index):\n");
            outputArea.append(formatResult(queryProcessingIndex.processQuery(query)) + "\n");
            outputArea.append("\nResults (BST):\n");
            outputArea.append(formatResult(queryProcessingBST.processQuery(query)) + "\n");
            outputArea.append("\nResults (InvertedIndex):\n");
            outputArea.append(formatResult(queryProcessing.processQuery(query)) + "\n");
            outputArea.append("\nResults (AVL):\n");
            outputArea.append(formatResult(queryProcessingAVL.processQuery(query)) + "\n");
        }
    }
private void printIndexedDocuments() {
    String tokenscount = SE.displayDocTokenCounts();
        outputArea.setText("Indexed Documents with Word Counts:\n"+ tokenscount);
        


    }
private void printIndexedTokens() {
       
    String invertedIndexOutput = SE.invertedindex. displayInvertedIndexWithCounts(); // Call your method from the SearchEngine class
    outputArea.setText("IndexedTokens with Doucument Counts:\n" + invertedIndexOutput);
       }
    
    private void rankedRetrieval() {
        String query = JOptionPane.showInputDialog(frame, "Enter a query for ranked retrieval:", "Ranked Retrieval", JOptionPane.QUESTION_MESSAGE);

        if (query != null && !query.trim().isEmpty()) {
            outputArea.setText("Ranked Retrieval Query: " + query + "\n");
            outputArea.append(ranking.rank_query(query) + "\n");
        }
    }

    // Method to display the results of a query
    private String formatResult(LinkedList<Integer> result) {
        StringBuilder sb = new StringBuilder();
        sb.append("Result: {");
        result.findfirst();
        boolean first = true;
        Integer docID = result.retrieve();
        while (docID != null) {
            if (!first) {
                sb.append(", ");
            }
            sb.append(docID);
            first = false;

            if (!result.last()) {
                result.findnext();
                docID = result.retrieve();
            } else {
                break;
            }
        }
        sb.append("}");
        return sb.toString();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(SearchEngineGUI::new);
    }
}
