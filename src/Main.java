import java.sql.Connection;
import java.sql.ResultSet;

public class Main {
    public static void main(String[] args) {
        Connection conc;
        DBOperations dbOperations = new DBOperations();
        try {
            conc = DBConnection.getConnection();

//            dbOperations.insertData(conc, "Anselum", 20, "1234567890");
//            dbOperations.insertData(conc, "JUJU", 21, "1234567890");
//            dbOperations.insertData(conc, "Ansel", 22, "1234567890");


            ResultSet rs = dbOperations.fetchData(conc, "hotelBooking");

            System.out.println();
            for (int i = 1; rs.next(); i++) {
                System.out.println("Data " + i + ":");
                System.out.println("\t\tName: " + rs.getString(1));
                System.out.println("\t\tAge: " + rs.getInt(2));
                System.out.println("\t\tPhone: " + rs.getString(3));
            }

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }
}
