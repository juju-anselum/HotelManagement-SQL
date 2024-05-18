import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Main {
    private static Connection connection;
    private static DBOperations dbOperations = new DBOperations();

    public static void main(String[] args) {
        try {
            connection = DBConnection.getConnection();
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            throw new RuntimeException(e);
        }

        JFrame frame = new JFrame("Hotel Management System");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 400);
        frame.setLayout(new BorderLayout());

        // Tabbed Pane
        JTabbedPane tabbedPane = new JTabbedPane();

        // Book Room Panel
        JPanel bookRoomPanel = createBookRoomPanel();
        tabbedPane.addTab("Book Room", bookRoomPanel);

        // Display Customers Panel
        JPanel displayCustomersPanel = createDisplayPanel("customers");
        tabbedPane.addTab("Display Customers", displayCustomersPanel);

        // Display Reservations Panel
        JPanel displayReservationsPanel = createDisplayPanel("reservations");
        tabbedPane.addTab("Display Reservations", displayReservationsPanel);

        frame.add(tabbedPane, BorderLayout.CENTER);
        frame.setVisible(true);
    }

    private static JPanel createBookRoomPanel() {
        JPanel panel = new JPanel(new GridLayout(9, 2));

        JLabel nameLabel = new JLabel("Name:");
        JTextField nameField = new JTextField();
        JLabel ageLabel = new JLabel("Age:");
        JTextField ageField = new JTextField();
        JLabel genderLabel = new JLabel("Gender:");
        JTextField genderField = new JTextField();
        JLabel addressLabel = new JLabel("Address:");
        JTextField addressField = new JTextField();
        JLabel phoneLabel = new JLabel("Phone:");
        JTextField phoneField = new JTextField();
        JLabel checkInLabel = new JLabel("Check-in Date:");
        JTextField checkInField = new JTextField();
        JLabel checkOutLabel = new JLabel("Check-out Date:");
        JTextField checkOutField = new JTextField();

        JButton bookButton = new JButton("Book Room");
        bookButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    String name = nameField.getText();
                    int age = Integer.parseInt(ageField.getText());
                    String gender = genderField.getText();
                    String address = addressField.getText();
                    String phone = phoneField.getText();
                    String checkIn = checkInField.getText();
                    String checkOut = checkOutField.getText();

                    bookRoom(name, age, gender, address, phone, checkIn, checkOut);
                    JOptionPane.showMessageDialog(panel, "Room booked successfully!");
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(panel, "Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        panel.add(nameLabel);
        panel.add(nameField);
        panel.add(ageLabel);
        panel.add(ageField);
        panel.add(genderLabel);
        panel.add(genderField);
        panel.add(addressLabel);
        panel.add(addressField);
        panel.add(phoneLabel);
        panel.add(phoneField);
        panel.add(checkInLabel);
        panel.add(checkInField);
        panel.add(checkOutLabel);
        panel.add(checkOutField);
        panel.add(bookButton);

        return panel;
    }

    private static JPanel createDisplayPanel(String tableName) {
        try{
            connection = DBConnection.getConnection();
        }catch(Exception e){
            System.out.println("Error: " + e.getMessage());
            throw new RuntimeException(e);
        }
        JPanel panel = new JPanel(new BorderLayout());
        JTextArea textArea = new JTextArea();
        textArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(textArea);
        panel.add(scrollPane, BorderLayout.CENTER);

        JButton refreshButton = new JButton("Refresh");
        refreshButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    ResultSet rs = dbOperations.fetchData(connection, tableName);
                    StringBuilder sb = new StringBuilder();

                    if (tableName.equals("customers")) {
                        while (rs.next()) {
                            sb.append("Customer ").append(rs.getInt("custId")).append("\n")
                                    .append("\tName: ").append(rs.getString("name")).append("\n")
                                    .append("\tAge: ").append(rs.getInt("age")).append("\n")
                                    .append("\tGender: ").append(rs.getString("gender")).append("\n")
                                    .append("\tAddress: ").append(rs.getString("address")).append("\n")
                                    .append("\tPhone: ").append(rs.getString("phone")).append("\n")
                                    .append("\tRoom No: ").append(rs.getInt("roomNo")).append("\n")
                                    .append("\tCheck-in date: ").append(rs.getString("checkIn")).append("\n")
                                    .append("\tCheck-out date: ").append(rs.getString("checkOut")).append("\n\n");
                        }
                    } else if (tableName.equals("reservations")) {
                        while (rs.next()) {
                            sb.append("Room ").append(rs.getInt("roomNo")).append("\n")
                                    .append("\tCustomer ID: ").append(rs.getInt("custId")).append("\n")
                                    .append("\tName: ").append(rs.getString("name")).append("\n")
                                    .append("\tCheck-in date: ").append(rs.getString("checkIn")).append("\n")
                                    .append("\tCheck-out date: ").append(rs.getString("checkOut")).append("\n\n");
                        }
                    }

                    textArea.setText(sb.toString());
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(panel, "Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        panel.add(refreshButton, BorderLayout.NORTH);

        return panel;
    }

    public static void bookRoom(String name, int age, String gender, String address, String phone, String checkIn, String checkOut) throws SQLException {
        ResultSet rs = dbOperations.fetchData(connection, "customers");
        int size=0;
        while(rs.next())
            size++;

        String custId = String.valueOf((size + 1));
        String roomNo = String.valueOf((size + 101));

        String custStatement = "INSERT INTO customers (custId, name, age, gender, address, phone, roomNo, checkIn, checkOut) VALUES ("
                + custId + ", '" + name + "', " + age + ", '" + gender + "', '" + address + "', '" + phone + "', " + roomNo + ", '"
                + checkIn + "', '" + checkOut + "')";
        String reservationStatement = "INSERT INTO reservations (roomNo, custId, name, checkIn, checkOut) VALUES ("
                + roomNo + ", " + custId + ", '" + name + "', '" + checkIn + "', '" + checkOut + "')";

        try {
            dbOperations.executeData(connection, custStatement);
            System.out.println("Room booked successfully!");
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
        try {
            dbOperations.executeData(connection, reservationStatement);
            System.out.println("Reservation made successfully!");
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}
