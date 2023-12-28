import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.util.List;
import java.util.ArrayList;
import java.util.Collections;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Random;
import java.util.Scanner;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.Timer;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultHighlighter;
import javax.swing.text.Highlighter;
import java.util.TimerTask;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */

/**
 *
 * @author USER
 */
public class TimedModeGame extends javax.swing.JFrame {
    private Random random = new Random();
    private int time;
    private boolean includePunctuation;
    private boolean isGameEnded = false; 
    private Timer timer;

    private JLabel timerLabel;
    private JScrollPane jScrollPane2;
    
    private String currentWord;
    private int currentIndex;
    private Highlighter.HighlightPainter painterCorrect;
    private Highlighter.HighlightPainter painterWrong;
    private List<Boolean> wordStatus;
    /**
     * Creates new form TimedModeGame
     */
    public TimedModeGame(boolean includePunctuation) {
        initComponents();
        this.includePunctuation = includePunctuation;
        setupTextFieldListener();
        generateRandomWords();
        setupHighlightPainters();
    }
    private void handleGameEnd() {
        jTextField2.setEditable(false);
        isGameEnded = true;
    }

    private void handleTextFieldEnter() {
        if (!isGameEnded) {
            JOptionPane.showMessageDialog(this, "Game end!");
            jTextField2.setEditable(false); // Disable further input in the text field
        }
    }
    
    private void generateRandomWords() {
    try {
        Scanner scanner = new Scanner(new File("C:/Users/USER/Downloads/random-words.txt"));
        List<String> wordsList = new ArrayList<>();

        // Read all words from the file
        while (scanner.hasNext()) {
            String word = scanner.next();
            if (!includePunctuation) {
        // Remove punctuation
        word = word.replaceAll("[^a-zA-Z]", "");
           } else {
        // Add random punctuation with a probability of 40%
                if (random.nextDouble() < 0.4) {
                String[] punctuation = {",", ".", "!", "?", "\"", ";", ":"};
                word += punctuation[random.nextInt(punctuation.length)];
                }
           }
            wordsList.add(word);
        }

        // Shuffle the list to get random words
        Collections.shuffle(wordsList);

        // Take the first 30 words or less if the file has fewer words
        int wordsToDisplay = Math.min(wordsList.size(), 30);
        StringBuilder randomWords = new StringBuilder();

        for (int i = 0; i < wordsToDisplay; i++) {
            randomWords.append(wordsList.get(i)).append(" ");
        }

        jTextArea1.setLineWrap(true);
        jTextArea1.setWrapStyleWord(true);
        jTextArea1.setText(randomWords.toString().trim());

        // Center align the text
        jTextArea1.setAlignmentX(CENTER_ALIGNMENT);
        jTextArea1.setAlignmentY(CENTER_ALIGNMENT);
        wordStatus = new ArrayList<>(Collections.nCopies(wordsToDisplay, false));
        currentWord = wordsList.get(0);
        currentIndex = 0;
        jTextField2.getDocument().addDocumentListener(new DocumentListener() {
                @Override
                public void insertUpdate(DocumentEvent e) {
                    highlightUserInput();
                }

                @Override
                public void removeUpdate(DocumentEvent e) {
                    highlightUserInput();
                }

                @Override
                public void changedUpdate(DocumentEvent e) {
                    highlightUserInput();
                }
            });

    } catch (FileNotFoundException e) {
        System.err.println("File not found: random-words.txt");
    }
}
    private void checkUserInput() {
        String userInput = jTextField2.getText().trim();
        String[] words = jTextArea1.getText().split("\\s+");
        for (int i = currentIndex; i < words.length; i++) {
            if (userInput.equals(words[i])) {
                wordStatus.set(i, true); // Set the word as correct
                currentIndex = i + 1;
            } else {
                wordStatus.set(i, false); // Set the word as wrong
                break;
            }
        }
        if (currentIndex == words.length) {
            handleGameEnd();
        }
    }
    private void setupTextFieldListener() {
        jTextField2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleTextFieldEnter();
            }
        });
    }
    private void highlightUserInput() {
    String userInput = jTextField2.getText().trim();

    try {
        String text = jTextArea1.getText();
        int start = 0;

        Highlighter highlighter = jTextField2.getHighlighter();
        highlighter.removeAllHighlights(); // Clear previous highlights

        for (int i = 0; i < userInput.length() && start + i < text.length(); i++) {
            char inputChar = userInput.charAt(i);
            char textChar = text.charAt(start + i);

            if (inputChar == textChar) {
                // Set character as correct
                highlighter.addHighlight(start + i, start + i + 1, painterCorrect);
            } else {
                // Set character as wrong
                highlighter.addHighlight(start + i, start + i + 1, painterWrong);
            }
        }
    } catch (BadLocationException e) {
        e.printStackTrace();
    }
}
    private void setupHighlightPainters() {
    painterCorrect = new DefaultHighlighter.DefaultHighlightPainter(Color.GREEN);
    painterWrong = new DefaultHighlighter.DefaultHighlightPainter(Color.RED);
}

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jSpinner1 = new javax.swing.JSpinner();
        jTextField2 = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTextArea1 = new javax.swing.JTextArea();
        jLabel1 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jTextField2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField2ActionPerformed(evt);
            }
        });

        jTextArea1.setEditable(false);
        jTextArea1.setColumns(20);
        jTextArea1.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jTextArea1.setLineWrap(true);
        jTextArea1.setRows(5);
        jTextArea1.setBorder(null);
        jScrollPane1.setViewportView(jTextArea1);

        jLabel1.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel1.setText("Time remaining:");

        jButton1.setText("Back");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(61, 61, 61)
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 113, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 550, Short.MAX_VALUE)
                            .addComponent(jTextField2))))
                .addContainerGap())
            .addGroup(layout.createSequentialGroup()
                .addGap(241, 241, 241)
                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 18, Short.MAX_VALUE)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 136, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(70, 70, 70)
                .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(43, 43, 43)
                .addComponent(jButton1)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jTextField2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField2ActionPerformed
        // TODO add your handling code here:
        handleTextFieldEnter();
    }//GEN-LAST:event_jTextField2ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        TimedMode timedMode = new TimedMode();
        timedMode.show();//display TimedMode here
        
        dispose();
    }//GEN-LAST:event_jButton1ActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new TimedModeGame(false).setVisible(true);
            }
        });
    }
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSpinner jSpinner1;
    private javax.swing.JTextArea jTextArea1;
    private javax.swing.JTextField jTextField2;
    // End of variables declaration//GEN-END:variables
}
