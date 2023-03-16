import java.sql.Connection;
import java.sql.DriverManager;

public class dictDAO {





    private String url = "jdbc:mysql://localhost:3306/frenzy";
    private String user = "root";
    private String pass = "Kushal@123456";
    Connection conn;

    public Connection Connect(){
        try {
            conn = DriverManager.getConnection(url, user, pass);

            return conn;
        }
        catch(Exception e)
        {
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
    }}







