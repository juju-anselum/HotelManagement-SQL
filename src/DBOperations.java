import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DBOperations {

    public void insertData(Connection conc, String name, int age, String phone) {
        try {
            Statement stmt = conc.createStatement();
            stmt.executeUpdate("INSERT INTO hotelBooking VALUES('" + name + "', " + age + ", '" + phone + "')");
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public void deleteData(Connection conc, String name) {
        try {
            Statement stmt = conc.createStatement();
            stmt.executeUpdate("DELETE FROM hotelBooking WHERE name = '" + name + "'");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public ResultSet fetchData(Connection connection, String tableName) {
        Statement stmt = null;
        ResultSet rs = null;

        try {
            stmt = connection.createStatement();
            rs = stmt.executeQuery("SELECT * FROM " + tableName);
            if (rs != null) return rs;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rs;
    }



}
