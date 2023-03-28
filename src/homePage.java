import static java.lang.Thread.sleep;

public class homePage {
    public static void main(String[] args) {
        DBUpdate du = new DBUpdate();
        DBConfig db = new DBConfig();
        du.labelLoading.setText("Update successfully");
        try{
            sleep(500);
        }
        catch (Exception ex){
            ex.printStackTrace();
        }
        du.dispose();
        FrontPage f = new FrontPage();
    }
}
