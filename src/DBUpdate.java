import javax.swing.*;
import java.awt.*;

public class DBUpdate extends JFrame{
    private JPanel panelDBUpdate;
    public JLabel labelLoading;
    DBUpdate(){
        setTitle("Updating Database");
        setContentPane(panelDBUpdate);
        setMinimumSize(new Dimension(300,200));
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setResizable(false);
        setVisible(true);
    }
}
