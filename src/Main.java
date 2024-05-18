import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Connection conc;
        DBOperations dbOperations = new DBOperations();
        try {
            conc = DBConnection.getConnection();

            bookRoom(conc);

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }

    public static void bookRoom(Connection conc) throws SQLException {
        Scanner sc = new Scanner(System.in);
        System.out.println("=============== Book Room ===============");
        System.out.println("Enter your name: ");
        String name = sc.nextLine();
        System.out.println("Enter your age: ");
        int age = sc.nextInt();
        sc.nextLine();
        System.out.println("Enter your gender: ");
        String gender = sc.nextLine();
        System.out.println("Enter your address: ");
        String address = sc.nextLine();
        System.out.println("Enter your phone no.: ");
        String phone = sc.nextLine();
        System.out.println("Enter your check-in date: ");
        String checkInDate = sc.nextLine();
        System.out.println("Enter your check-out date: ");
        String checkOutDate = sc.nextLine();

        ResultSet rs = new DBOperations().fetchData(conc, "reservations");
        int columnCount = rs.getMetaData().getColumnCount();

        String custId = String.valueOf(rs.getRow() + 1);
        String roomNo = String.valueOf(rs.getRow() + 101);

        String custStatement = "INSERT INTO customers values ('" + custId + "', '" + name + "', " + age + ", '" + gender + "', '" + address + "', '" + phone + "','" + roomNo + "', '" + checkInDate + "', '" + checkOutDate + "')";
        String reservationStatement = "INSERT INTO reservations values ('" + roomNo + "', " + custId + ", '" + name + "', '" + checkInDate + "', '" + checkOutDate + "')";


        new DBOperations().executeData(conc, custStatement);
        new DBOperations().executeData(conc, reservationStatement);


    }
}
