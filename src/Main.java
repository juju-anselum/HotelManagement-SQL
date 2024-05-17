import java.sql.*;

public class Main {
    public static void main(String[] args) {
        DBOperations dbOperations = new DBOperations();
        try {
            ResultSet rs = dbOperations.fetchData();
            if(rs!= null) System.out.println("No null");
            while (rs.next()) {
                int columnCount = rs.getMetaData().getColumnCount();
                for (int i = 1; i <= columnCount; i++) {
                    System.out.print("Column" + i + ": " + rs.getString(i) + " ");
                }
                System.out.println();
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }
}
