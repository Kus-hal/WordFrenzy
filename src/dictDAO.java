import java.sql.Connection;
import java.sql.DriverManager;
import java.util.ArrayList;
import java.util.List;

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
    public void Disconnect()
    {
        try
        {
            conn.close();
        } catch (Exception e)
        {
            e.printStackTrace();
        }
    }




    private static List<String> unscrambleWord(String word)
    {
        List<String> unscrambledWords = new ArrayList<>();

        // Generate all permutations of the word
        List<String> permutations = generatePermutations(word);

        // Check each permutation against the dictionary
        for (String permutation : permutations)
        {
            if (dictionary.contains(permutation))
            {
                unscrambledWords.add(permutation);
            }
        }

        return unscrambledWords;
    }
}
