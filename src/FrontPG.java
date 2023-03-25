import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class FrontPG extends JFrame {
    private JTextField textField1;
    private JRadioButton radioButton1;
    private JRadioButton radioButton2;
    private JTextArea textArea1;
    private JLabel labelWord;
    private JLabel lableFrenzy;
    private JPanel panelMain;
    private JPanel panelAll;
    private JButton clearBtn;
    private JButton exit;
    private JButton addButton;
    private JButton searchButton;


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
<<<<<<< HEAD
                else if(userChoice == JOptionPane.NO_OPTION)
                {

                }
            }
        });

        
=======
            }
        });
        clearBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                textField1.setText("");
                textArea1.setText("");
                textField1.setEditable(true);
                textField1.setEnabled(true);
                radioButton2.setSelected(false);
                radioButton1.setSelected(false);
            }
        });
>>>>>>> 3942fe280c0c376cb6dc43b44089c0f8739f1e04
        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                textField1.setEnabled(false);
                textField1.setEditable(false);
                String input = textField1.getText();
                List<String> unscrambledWords = generateUnscrambledWords(input);
                displayUnscrambledWords(unscrambledWords);
            }
        });
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String input =  textField1.getText();
                int n =  Integer.parseInt(JOptionPane.showInputDialog(panelAll,"Word to be Added :-",input  ));
                if(n== JOptionPane.YES_OPTION){
                    //YES code here
                }
            }
        });
        setVisible(true);
    }

    private List<String> generateUnscrambledWords(String input) {
        List<String> unscrambledWords = new ArrayList<>();
        generateCombos("", input, unscrambledWords);
        return unscrambledWords;
    }

    private void checkWord(String word, List<String> unscrambledWords) {
        try {
            dictDAO d = new dictDAO();
            Connection con = d.Connect();
            Statement stmt = con.createStatement();
            String query = "SELECT word FROM dictionary WHERE word = '" + word + "'";
            ResultSet rs = stmt.executeQuery(query);
            if (rs.next()) {
                unscrambledWords.add(word);
            }
            rs.close();
            stmt.close();
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void displayUnscrambledWords(List<String> unscrambledWords) {
        StringBuilder sb = new StringBuilder();
        for (String word : unscrambledWords) {
            textArea1.append(word + "\n");
        }
    }

    private void generateCombos(String prefix, String remaining, List<String> unscrambledWords) {
        if (remaining.length() == 0) {
                checkWord(prefix, unscrambledWords);
        }
        for (int i = 0; i < remaining.length(); i++) {
            String newPrefix = prefix + remaining.charAt(i);
            String newRemaining = remaining.substring(0, i) + remaining.substring(i + 1);
            generateCombos(newPrefix, newRemaining, unscrambledWords);
        }
    }
}
