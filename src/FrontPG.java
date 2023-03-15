import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.*;

public class FrontPG extends JFrame {
    private JTextField textField1;
    private JRadioButton radioButton1;
    private JRadioButton radioButton2;
    private JTextArea textArea1;
    private JLabel labelWord;
    private JLabel lableFrenzy;
    private JPanel panelMain;
    private JPanel panelAll;





    public  FrontPG(){



            //mergeLabels();
            setTitle("Home Page");
            setContentPane(panelAll);
            setMinimumSize(new Dimension(600,400));
            setLocationRelativeTo(null);
            setDefaultCloseOperation(DISPOSE_ON_CLOSE);
            setVisible(true);









            radioButton1.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    String word = textField1.getText();
                    if(radioButton1.isSelected())
                    {   textField1.setEditable(false);
                        textField1.setEnabled(false);
                        textArea1.setText(word);
                    }
                    else if(radioButton1.isSelected())
                    {
                        textArea1.setText("Hello");
                    }
                    else
                    {

                    }
                }
            });
            textField1.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    textField1.setText(" ");

                }
            });
        textField1.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                textField1.setText("");
            }
        });
    }



    private String url = "jdbc:mysql://localhost:3306/frenzy";
    private String user = "root";
    private String pass = "Kushal@123456";
    Connection conn;

    public Connection Connect(){
        try {
            conn = DriverManager.getConnection(url, user, pass);

            return conn;
        }
        catch(Exception e){
            e.printStackTrace();
        }
        return null;
    }
    public void Disconnect() {
        try {
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }



       /* private void mergeLabels() {
            panelMain.setLayout(new OverlayLayout(panelMain));
            labelWord.setAlignmentX(Component.CENTER_ALIGNMENT);
            labelWord.setAlignmentY(Component.CENTER_ALIGNMENT);
            lableLine.setAlignmentX(Component.CENTER_ALIGNMENT);
            lableLine.setAlignmentY(Component.CENTER_ALIGNMENT);
        }
*/

    }}


