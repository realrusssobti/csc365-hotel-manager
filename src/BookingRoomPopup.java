import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;

public class BookingRoomPopup extends JFrame {
    private final JTextField reservationIDField;
    private final JComboBox<Integer> customerComboBox;
    private String roomIDField;
    private final DatePicker checkInDatePanel;
    private final DatePicker checkOutDatePanel;
    private SQLQueries sqlConnection;

    public BookingRoomPopup(String roomNum, SQLQueries sqlConnection) {
        this.sqlConnection = sqlConnection;
        setLayout(new GridLayout(8, 2)); // Adjusted to include an additional row for the title

        // Create a JLabel for the title
        JLabel titleLabel = new JLabel("Book Room");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20)); // Set font, size, and style
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER); // Center the text horizontally

        // Placeholder label for the new line
        JLabel placeholderLabel1 = new JLabel();
        JLabel placeholderLabel2 = new JLabel();

        JLabel reservationIDLabel = new JLabel("Reservation ID:");
        reservationIDField = new JTextField();

        JLabel customerLabel = new JLabel("Select Customer ID:");
        customerComboBox = new JComboBox<>(new Integer[]{1, 2, 3}); // Add customer IDs as needed

        JLabel roomIDLabel = new JLabel("Room ID: " + roomNum);
        roomIDField = roomNum;

        JLabel checkInLabel = new JLabel("Check-In Date (yyyy-MM-dd):");
        checkInDatePanel = new DatePicker();

        JLabel checkOutLabel = new JLabel("Check-Out Date (yyyy-MM-dd):");
        checkOutDatePanel = new DatePicker();

        JButton makeReservationButton = new JButton("Make Reservation");
        makeReservationButton.addActionListener(new ReservationButtonListener());

        // Add the title label to the frame
        add(titleLabel);
        // Add the placeholder labels for the new lines
        // add(placeholderLabel1);
        add(placeholderLabel2);
        // Add the rest of the components
        add(reservationIDLabel);
        add(reservationIDField);

        add(customerLabel);
        add(customerComboBox);

        add(roomIDLabel);
        add(placeholderLabel1); // Placeholder label for the new line

        add(checkInLabel);
        add(checkInDatePanel);

        add(checkOutLabel);
        add(checkOutDatePanel);

        add(makeReservationButton);

        // Add WindowListener to handle the window-closing event
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                dispose(); // Close the window when "X" is clicked
            }
        });

        // // Set the title at the top
        // setTitle("Book Room");
        // // Set a titled border
        // ((JComponent) getContentPane()).setBorder(BorderFactory.createTitledBorder("Book Room"));
    }

    public BookingRoomPopup(String roomNum) {
         JLabel reservationIDLabel = new JLabel("Reservation ID:");
        reservationIDField = new JTextField();

        JLabel customerLabel = new JLabel("Select Customer ID:");
        customerComboBox = new JComboBox<>(new Integer[]{1, 2, 3}); // Add customer IDs as needed

        JLabel roomIDLabel = new JLabel("Room ID: " + roomNum);
        roomIDField = roomNum;

        JLabel checkInLabel = new JLabel("Check-In Date (yyyy-MM-dd):");
        checkInDatePanel = new DatePicker();

        JLabel checkOutLabel = new JLabel("Check-Out Date (yyyy-MM-dd):");
        checkOutDatePanel = new DatePicker();
    }

    private class ReservationButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                int reservationID = Integer.parseInt(reservationIDField.getText());
                int selectedCustomerID = (int) customerComboBox.getSelectedItem();
                int roomID = Integer.parseInt(roomIDField);

                String checkInDate = checkInDatePanel.getSelectedDate();
                String checkOutDate = checkOutDatePanel.getSelectedDate();

                // Make into LocalDates
                LocalDate checkInDateLocal = LocalDate.parse(checkInDate);
                LocalDate checkOutDateLocal = LocalDate.parse(checkOutDate);
                // convert to sql date
                Date sqlCheckInDate = Date.valueOf(checkInDateLocal);
                Date sqlCheckOutDate = Date.valueOf(checkOutDateLocal);


                // Create a new Reservation object with the input data
                Reservation newReservation = new Reservation(reservationID, selectedCustomerID, roomID, checkInDateLocal, checkOutDateLocal, false);

                ArrayList<String> customer_info = sqlConnection.getCustomerInfo(String.valueOf(selectedCustomerID));
                sqlConnection.addReservation(Integer.toString(selectedCustomerID), sqlCheckInDate, sqlCheckOutDate, String.valueOf(roomID));

                // Perform operations with the new reservation (e.g., save to database)
                // For demonstration purposes, you can print the new reservation details
                System.out.println("New Reservation Details:");
                System.out.println("Reservation ID: " + newReservation.getReservationID());
                System.out.println("Customer ID: " + newReservation.getCustomerID());
                System.out.println("Room ID: " + newReservation.getRoomNumber());
                System.out.println("Check-In Date: " + newReservation.getCheckInDate());
                System.out.println("Check-Out Date: " + newReservation.getCheckOutDate());
            } catch (NumberFormatException ex) {
                System.err.println("Invalid input format for IDs. Please enter valid numbers.");
            } catch (Exception ex) {
                System.err.println("Invalid date format or other input error. Please check your inputs.");
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            BookingRoomPopup bookingRoomPopup = new BookingRoomPopup("101");
            bookingRoomPopup.setSize(400, 300);
            bookingRoomPopup.setVisible(true);
        });
    }
}
