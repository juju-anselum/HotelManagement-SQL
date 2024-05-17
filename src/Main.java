import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSet;


public class Main {
    public static void main(String[] args) {
        Connection connection = null;
        try {

            Class.forName("oracle.jdbc.driver.OracleDriver");


            connection = DriverManager.getConnection(
                    "jdbc:oracle:thin:@localhost:1521:xe", "system", "abcd123");

            if (connection != null) {
                System.out.println("Connection established successfully!");
            } else {
                System.out.println("Failed to establish connection!");
            }

            Statement stmt = connection.createStatement();

            stmt.executeQuery("insert into hotelBooking values('aaa',21,9122934909)");

            ResultSet rs = stmt.executeQuery("SELECT * FROM hotelBooking");


            while (rs.next()) {
                System.out.println("Column1: " + rs.getString(1));
                System.out.println("Column2: " + rs.getString(2));
                System.out.println("Column2: " + rs.getString(3));
            }

            rs.close();
            stmt.close();

        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
