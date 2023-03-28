import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;
import java.util.List;

public class FrontPage extends JFrame{
    private JPanel panelFrontPg;
    private JPanel panelLogo;
    private JPanel panelSearch;
    private JPanel panelBtns;
    private JPanel panelResult;
    private JButton btnSearch;
    private JButton btnClear;
    private JButton btnAdd;
    private JButton btnExit;
    private JTextField textSearch;
    private JLabel labelSearch;
    private JTextArea textAreaRes;
    private JLabel labelPic;
    private ImageIcon img;
    private ImageIcon img1;
    private Map<Character, Set<String>> wordMap = new HashMap<>();

    FrontPage(){
        setTitle("Home Page");
        setContentPane(panelFrontPg);
        setMinimumSize(new Dimension(600,500));
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setResizable(false);

        img = new ImageIcon(getClass().getClassLoader().getResource("Logo.png"));
        labelPic.setIcon(img);

        img1 = new ImageIcon(getClass().getClassLoader().getResource("Icon-1.png"));
        setIconImage(img1.getImage());
        btnExit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int userChoice = JOptionPane.showConfirmDialog(panelFrontPg, "Do you really want to close?", "Wanna try one more Word..!", JOptionPane.YES_NO_OPTION);
                if (userChoice == JOptionPane.YES_OPTION){
                    System.exit(0);
                }
            }
        });

        btnClear.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                textSearch.setText(null);
                textSearch.setEditable(true);
                textSearch.setEnabled(true);
                textAreaRes.setText(null);
            }
        });

        btnSearch.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                textAreaRes.setText(null);
                textSearch.setEnabled(false);
                textSearch.setEditable(false);
                String input = textSearch.getText();
                List<String> unscrambledWords = generateUnscrambledWords(input);
                displayUnscrambledWords(unscrambledWords);
                if(textAreaRes.getText().equals("")){
                    textAreaRes.setText("No word found");
                }
            }
        });

        btnAdd.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String input= JOptionPane.showInputDialog(panelFrontPg,"Word to be Added :-",textSearch.getText());
                if(input!=null){
                    textAreaRes.setText(null);
                    dictDAO d = new dictDAO();
                    int res = d.addToDict(input);
                    if(res==0){
                        textAreaRes.setText("Word already present in dictionary");
                    }
                    else if(res == -1){
                        textAreaRes.setText("Word cannot be added");
                    }
                    textAreaRes.append(input);
                    textAreaRes.append("\n"+ res +" word added successfully");
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
            if (initialWords.contains(word) && !unscrambledWords.contains(word)) {
                unscrambledWords.add(word);
            }
        }
    }

    private void displayUnscrambledWords(List<String> unscrambledWords) {
        for (String word : unscrambledWords) {
            textAreaRes.append(word + "\n");
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
            dictDAO d = new dictDAO();
            Connection conn = d.Connect();
            Statement stmt = conn.createStatement();
            String query = "SELECT word FROM dictionary WHERE word LIKE '" + c + "%'";
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                words.add(rs.getString(1));
            }
            rs.close();
            stmt.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return words;
    }
}
