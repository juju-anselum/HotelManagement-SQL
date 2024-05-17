import java.sql.*;

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
                return;
            }

            Statement stmt = connection.createStatement();

            ResultSet rs = stmt.executeQuery("SELECT * FROM hotelBooking");

            int columnCount = rs.getMetaData().getColumnCount();

            System.out.println();
            for(int i = 1;rs.next();i++){
                System.out.println("Data "+i+":");
                System.out.println("\t\tName: "+rs.getString(1));
                System.out.println("\t\tAge: "+rs.getInt(2));
                System.out.println("\t\tPhone: "+rs.getString(3));
            }

            rs.close();
            stmt.close();
        } catch (ClassNotFoundException e) {
            System.out.println("\nOracle JDBC Driver not found. Include it in your library path.");
            e.printStackTrace();
        } catch (SQLException e) {
            System.out.println("\nSQL error occurred: " + e.getMessage());
            e.printStackTrace();
        } finally {
            try {
                if (connection != null) {
                    connection.close();
                    System.out.println("\nConnection closed.");
                }
            } catch (SQLException e) {
                System.out.println("\nError closing connection: " + e.getMessage());
                e.printStackTrace();
            }
        }
    }
}
