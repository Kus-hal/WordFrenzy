import java.io.BufferedReader;
import java.io.FileReader;
import java.sql.*;
import java.util.ArrayList;

public class DBConfig {
    private String url = "jdbc:mysql://localhost:3306/";
    private String user = DBCredential.user;
    private String pass = DBCredential.pass;
    DBConfig(){
        try{
            Connection con = DriverManager.getConnection(url, user, pass);
            String query = "SHOW DATABASES;";
            Statement stmt = con.createStatement();
            ResultSet res = stmt.executeQuery(query);
            ArrayList<String> list = new ArrayList<>();
            while(res.next()){
                list.add(res.getString(1));
            }
            if(!list.contains("frenzy")){
                query = "CREATE DATABASE frenzy;";
                stmt.executeUpdate(query);

                query = "USE frenzy;";
                stmt.executeUpdate(query);

                query = "CREATE TABLE dictionary (word varchar(255));";
                stmt.executeUpdate(query);
                addWords(con);
            }
            else{
                query = "USE frenzy;";
                stmt.executeUpdate(query);

                query = "SHOW TABLES;";
                res =stmt.executeQuery(query);
                list.clear();
                while(res.next()){
                    list.add(res.getString(1));
                }
                if(!list.contains("dictionary")){
                    query = "CREATE TABLE dictionary (word varchar(255));";
                    stmt.executeUpdate(query);
                    addWords(con);
                }
            }
            res.close();
            stmt.close();
            con.close();
        }
        catch (Exception ex){
            ex.printStackTrace();
        }
    }

    private void addWords(Connection con){
        try{
            String filePath = "All words.txt";
            String sql = "INSERT INTO dictionary VALUES (?);";
            PreparedStatement statement = con.prepareStatement(sql);
            BufferedReader reader = new BufferedReader(new FileReader(filePath));
            String line;
            while ((line = reader.readLine()) != null) {
                statement.setString(1, line);
                statement.executeUpdate();
            }
            reader.close();
            System.out.println("Words inserted successfully.");
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
