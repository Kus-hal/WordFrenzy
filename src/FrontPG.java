import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;
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

    private static List<String> dictionary = Arrays.asList("apple", "banana", "pear", "orange", "peach");

    // A function to generate all permutations of a given string
    private static List<String> generatePermutations(String str)
    {
        List<String> permutations = new ArrayList<>();

        if (str == null || str.length() == 0)
        {
            permutations.add("");
            return permutations;
        }

        char first = str.charAt(0);
        String remainder = str.substring(1);
        List<String> words = generatePermutations(remainder);
        for (String word : words)
        {
            for (int i = 0; i <= word.length(); i++)
            {
                String permutation = insertCharAt(word, first, i);
                permutations.add(permutation);
            }
        }

        return permutations;
    }

    // A helper function to insert a character at a specific index in a string
    private static String insertCharAt(String str, char c, int index)
    {
        String left = str.substring(0, index);
        String right = str.substring(index);
        return left + c + right;
    }

    // A function to unscramble a word and return a list of valid words
    


    public  FrontPG(){


        setTitle("Home Page");
        setContentPane(panelAll);
        setMinimumSize(new Dimension(600,400));
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
       // mergeLabels();
        setVisible(true);




        exit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                    System.exit(0);
            }
        });


        ActionListener listener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                        textField1.setEnabled(false);
                        textField1.setEditable(false);
                        textField1.setSelectionEnd(0);
                        String word = textField1.getText();
                        if(radioButton1.isSelected())
                        {
                            textArea1.setText("");
                            List<String> unscrambledWords = unscrambleWord(word);
                            textArea1.setText(String.valueOf(unscrambledWords));
                        }
                        if(radioButton2.isSelected())
                        {
                            textArea1.setText("");
                            textArea1.setText("helloo");
                        }

            }
        };
        radioButton1.addActionListener(listener);
        radioButton2.addActionListener(listener);











    }



}


