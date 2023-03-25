import java.sql.Connection;
import java.sql.DriverManager;

public class dictDAO {
    private String url = DBCredential.url;
    private String user = DBCredential.user;
    private String pass = DBCredential.pass;
    Connection conn;

    public Connection Connect(){
        try {
            conn = DriverManager.getConnection(url, user, pass);
            return conn;
        }
        catch(Exception e) {
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