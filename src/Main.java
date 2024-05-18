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

            //bookRoom(conc);

            //display customer details
            ResultSet rs = new DBOperations().fetchData(conc, "customers");
            for(int i = 1;rs.next();i++){
                System.out.println("Customer " + i);
                System.out.println("\tName: " + rs.getString(2));
                System.out.println("\tAge: " + rs.getInt(3));
                System.out.println("\tGender: " + rs.getString(4));
                System.out.println("\tAddress: " + rs.getString(5));
                System.out.println("\tPhone: " + rs.getString(6));
                System.out.println("\tRoom No: " + rs.getString(7));
                System.out.println("\tCheck-in date: " + rs.getString(8));
                System.out.println("\tCheck-out date: " + rs.getString(9));
            }

            //display booked rooms
            rs = new DBOperations().fetchData(conc, "reservations");
            for(int i = 1;rs.next();i++){
                System.out.println("Room " + i);
                System.out.println("\tRoom No: " + rs.getString(1));
                System.out.println("\tCustomer ID: " + rs.getString(2));
                System.out.println("\tName: " + rs.getString(3));
                System.out.println("\tCheck-in date: " + rs.getString(4));
                System.out.println("\tCheck-out date: " + rs.getString(5));
            }

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
