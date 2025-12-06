import java.sql.*;

public class CriarBanco{

    public static void main(String[] args){

        try{

            Class.forName("org.sqlite.JDBC");

            Connection conn = DriverManager.getConnection("jdbc:sqlite:Hospital.db");
            Statement st = conn.createStatement();

            st.execute("CREATE TABLE IF NOT EXISTS CadastroPacientes (" + 
            "ID INTEGER PRIMARY KEY AUTOINCREMENT," +
            "Nome TEXT NOT NULL," +
            "Idade INTEGER," +
            "Peso REAL," +
            "Altura REAL)");

            st.close();
            conn.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
