import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
<<<<<<< HEAD
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
=======
import java.sql.*;
>>>>>>> 560a571703b19f8694869ae2930943485be35bad
import java.util.List;
import java.util.*;

public class FrontPG extends JFrame {
    private JTextField textField1;
    private JTextArea textArea1;
    private JLabel labelWord;
    private JLabel lableFrenzy;
    private JPanel panelMain;
    private JPanel panelAll;
    private JButton clearBtn;
    private JButton exit;
    private JButton addButton;
    private JButton searchButton;
    private Map<Character, Set<String>> wordMap = new HashMap<>();


    public  FrontPG(){
        setTitle("Home Page");
        setContentPane(panelAll);
        setMinimumSize(new Dimension(600,400));
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setResizable(false);

        exit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                int userChoice = JOptionPane.showConfirmDialog(panelAll, "Do you really want to close?", "Let try one more Word..!", JOptionPane.YES_NO_OPTION);

                if (userChoice == JOptionPane.YES_OPTION){
                    System.exit(0);
                }

                else if(userChoice == JOptionPane.NO_OPTION)
                {

                }
            }
        });



        clearBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                textField1.setText("");
                textArea1.setText("");
                textField1.setEditable(true);
                textField1.setEnabled(true);
            }
        });

        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                textArea1.setText(null);
                textField1.setEnabled(false);
                textField1.setEditable(false);
                String input = textField1.getText();
                List<String> unscrambledWords = generateUnscrambledWords(input);
                displayUnscrambledWords(unscrambledWords);
                if(textArea1.getText().equals("")){
                    textArea1.setText("No word found");
                }
            }
        });
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String input= JOptionPane.showInputDialog(panelAll,"Word to be Added :-",textField1.getText());
                if(input!=null){
                    textArea1.setText(null);
                    dictDAO d = new dictDAO();
                    int res = d.addToDict(input);
                    if(res==0){
                        textArea1.setText("Word already present in dictionary");
                    }
                    else if(res == -1){
                        textArea1.setText("Word cannot be added");
                    }
                    textArea1.append(input);
                    textArea1.append("\n"+ res +" word added successfully");
                }
            }
        });
        setVisible(true);
    }

    private List<String> generateUnscrambledWords(String input) {
        List<String> unscrambledWords = new ArrayList<>();
        Set<Character> usedChars = new HashSet<>();

        for (char c : input.toCharArray()) {
            if (usedChars.contains(c)) {
                continue;
            }
            usedChars.add(c);

            Set<String> words = wordMap.get(c);
            if (words == null) {
                words = getWordsStartingWith(c);
                wordMap.put(c, words);
            }

            List<String> combos = new ArrayList<>();
            generateCombos("", input, combos);

            for (String combo : combos) {
                if (words.contains(combo)) {
                    unscrambledWords.add(combo);
                }
            }
        }
        return unscrambledWords;
    }



    private void checkWord(String word, List<String> unscrambledWords) {
        if (word.length() > 1) {
            Set<String> initialWords = wordMap.get(word.charAt(0));
            if (initialWords == null) {
                initialWords = getWordsStartingWith(word.charAt(0));
                wordMap.put(word.charAt(0), initialWords);
            }
            if (initialWords.contains(word)) {
                unscrambledWords.add(word);
            }
        }
    }

    private void displayUnscrambledWords(List<String> unscrambledWords) {
        StringBuilder sb = new StringBuilder();
        for (String word : unscrambledWords) {
            textArea1.append(word);
            textArea1.append("\n");
        }
    }
    private void generateCombos(String prefix, String remaining, List<String> unscrambledWords) {
        if (remaining.length() == 0) {
            checkWord(prefix, unscrambledWords);
        } else {
            for (int i = 0; i < remaining.length(); i++) {
                String newPrefix = prefix + remaining.charAt(i);
                String newRemaining = remaining.substring(0, i) + remaining.substring(i + 1);
                generateCombos(newPrefix, newRemaining, unscrambledWords);
            }
        }
    }

    private Set<String> getWordsStartingWith(char c) {
        Set<String> words = new HashSet<>();
        try {
            String url = "jdbc:mysql://localhost:3306/frenzy";
            String user = "root";
            String pass = "Kushal@123456";
            Connection conn;
            conn = DriverManager.getConnection(url, user, pass);
            Statement stmt = conn.createStatement();
            String query = "SELECT word FROM dictionary WHERE word LIKE '" + c + "%'";
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                words.add(rs.getString("word"));
            }
            rs.close();
            stmt.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return words;
    }}
