import java.sql.*;

public class DBOperations {

    public void insertData(Connection conc, String name, int age, String phone) {
        try {
            Statement stmt = conc.createStatement();
            stmt.executeUpdate("INSERT INTO hotelBooking VALUES('"+name+"', "+age+", '"+phone+"')");
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public ResultSet fetchData() {
        Connection connection = null;
        Statement stmt = null;
        ResultSet rs = null;

        try {
            connection = DBConnection.getConnection();
            stmt = connection.createStatement();
            rs = stmt.executeQuery("SELECT * FROM hotelBooking");
            if(rs!=null) return rs;

        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
        return rs;
    }
}
