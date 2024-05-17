import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
    private static final String URL = "jdbc:oracle:thin:@localhost:1521:xe";
    private static final String USER = "system";
    private static final String PASSWORD = "abcd123";
    private static final String DRIVER_CLASS = "oracle.jdbc.driver.OracleDriver";

    public static Connection getConnection() throws ClassNotFoundException, SQLException {
        Class.forName(DRIVER_CLASS);

        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}
