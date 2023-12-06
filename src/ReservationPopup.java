import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;

public class ReservationPopup extends Frame {
    private Choice customerIDChoice;
    private TextField customerIDField;

    private final Choice roomIDChoice;
    private final TextField roomIDField;
    private String checkInDate;
    private DatePicker CheckInDatePanel;
    private String checkOutDate;
    private DatePicker CheckOutDatePanel;
    private SQLQueries sqlConnection;

    public ReservationPopup(SQLQueries sqlConnection) {
        this.sqlConnection = sqlConnection;
        setLayout(new GridLayout(6, 2));

        Label customerIDLabel = new Label("Customer ID:");
        // {Customer ID, Name} -> Show name in the dropdown and use ID as the value
        // Get customer Names and IDs from the database
        ArrayList<String[]> customerNamesAndIDs = sqlConnection.getCustomerNamesAndIDs();
        // Create a Choice component for the customer IDs
        customerIDChoice = new Choice();
        customerIDField = new TextField();

        // Add the customer names to the Choice
        for (String[] customer : customerNamesAndIDs) {
            customerIDChoice.add(customer[1]);
        }
        // Add an ItemListener to the Choice to get the selected customer ID
        customerIDChoice.addItemListener(e -> {
            // Get the selected customer name
            String selectedCustomerName = customerIDChoice.getSelectedItem();
            // Find the customer ID that corresponds to the selected name
            for (String[] customer : customerNamesAndIDs) {
                if (customer[1].equals(selectedCustomerName)) {
                    // Set the customer ID field to the selected customer ID
                    customerIDField.setText(customer[0]);
                    break;
                }
            }
        });
        add(customerIDLabel);
        add(customerIDChoice);
        add(customerIDField);


        Label roomIDLabel = new Label("Room ID:");
        roomIDChoice = new Choice();
        roomIDField = new TextField();
// Get room IDs from the database
        ArrayList<String[]> roomIDs = sqlConnection.getRoomIDs();
        // Add the room IDs to the Choice
        for (String[] room : roomIDs) {
            roomIDChoice.add(room[0]);
        }
// Add an ItemListener to the Choice to get the selected room ID
        roomIDChoice.addItemListener(e -> {
            // Get the selected room ID
            String selectedRoomID = roomIDChoice.getSelectedItem();
            // Set the room ID field to the selected room ID
            roomIDField.setText(selectedRoomID);
        });
        add(roomIDLabel);
        add(roomIDChoice);
        add(roomIDField);


        Label checkInLabel = new Label("Check-In Date (yyyy-MM-dd):");
        CheckInDatePanel = new DatePicker();

        Label checkOutLabel = new Label("Check-Out Date (yyyy-MM-dd):");
        CheckOutDatePanel = new DatePicker();

        Button makeReservationButton = new Button("Make Reservation");
        makeReservationButton.addActionListener(new ReservationButtonListener());


        add(customerIDLabel);
        add(customerIDField);

        add(roomIDLabel);
        add(roomIDField);

        add(checkInLabel);
        add(CheckInDatePanel);

        add(checkOutLabel);
        add(CheckOutDatePanel);

        add(makeReservationButton);

        // Add WindowListener to handle the window-closing event
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                dispose(); // Close the window when "X" is clicked
            }
        });
    }


    // just empty constructor for main method tht wont get run in demo (isolation testing. dont do it tho. it wont work)

    private class ReservationButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                int customerID = Integer.parseInt(customerIDField.getText());
                int roomID = Integer.parseInt(roomIDField.getText());

                String checkInDate = ((DatePicker) CheckInDatePanel).getSelectedDate();
                String checkOutDate = ((DatePicker) CheckOutDatePanel).getSelectedDate();
                // Make into LocalDates
                LocalDate checkInDateLocal = LocalDate.parse(checkInDate);
                LocalDate checkOutDateLocal = LocalDate.parse(checkOutDate);


                // modify db to add the reservation
                ArrayList<String> customer_info = sqlConnection.getCustomerInfo(String.valueOf(customerID));
//                sqlConnection.addReservation(customer_info.get(0), customer_info.get(1), Date.valueOf(checkInDateLocal), Date.valueOf(checkOutDateLocal), String.valueOf(roomID));
                sqlConnection.addReservation(String.valueOf(customerID), Date.valueOf(checkInDateLocal), Date.valueOf(checkOutDateLocal), String.valueOf(roomID));
                // Perform operations with the new reservation (e.g., save to database)
                // For demonstration purposes, you can print the new reservation details
                System.out.println("New Reservation Details:");
                System.out.println("Customer ID: " + customerID);
                System.out.println("Room ID: " + roomID);
                System.out.println("Check-In Date: " + checkInDate);
                System.out.println("Check-Out Date: " + checkOutDate);
                System.out.println("Reservation added to database.");
            } catch (NumberFormatException ex) {
                System.err.println("Invalid input format for IDs. Please enter valid numbers.");
            } catch (Exception ex) {
                System.err.println("Invalid date format or other input error. Please check your inputs.");
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            ReservationPopup reservationPopup = new ReservationPopup(new SQLQueries());
            reservationPopup.setSize(400, 300);
            reservationPopup.setVisible(true);
        });
    }
}

