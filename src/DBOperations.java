import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DBOperations {

    public void executeData(Connection conc, String statement) {
        try {
            Statement stmt = conc.createStatement();
            stmt.executeUpdate(statement);
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
