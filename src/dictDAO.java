import java.io.BufferedWriter;
import java.io.FileWriter;
import java.sql.*;

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
    }
    public int addToDict(String word){
        this.Connect();
        if(!word.equals("")){
            try{
                String query = "SELECT word FROM dictionary WHERE word=?;";
                PreparedStatement pst = conn.prepareStatement(query);
                pst.setString(1,word);
                ResultSet rs = pst.executeQuery();
                if(rs.next()){
                    return 0;
                }
                else{
                    pst.close();
                    query = "INSERT INTO dictionary VALUES(?);";
                    pst = conn.prepareStatement(query);
                    pst.setString(1,word);
                    int res = pst.executeUpdate();
                    pst.close();

                    String fileName = "All words.txt";
                    try {
                        FileWriter fw = new FileWriter(fileName, true);
                        BufferedWriter bw = new BufferedWriter(fw);

                        bw.newLine();
                        bw.write(word);

                        bw.close();
                        fw.close();
                    }
                    catch (Exception p) {
                        System.out.println("An error occurred while trying to append words to the file: " + p.getMessage());
                    }
                    return res;
                }
            }
            catch (Exception ex){
                ex.printStackTrace();
            }
        }
        this.Disconnect();
        return -1;
    }
}