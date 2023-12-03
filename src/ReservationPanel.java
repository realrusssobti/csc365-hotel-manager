import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;

public class ReservationPanel extends Panel {
    private final TextField reservationIDField;
    private final TextField customerIDField;
    private final TextField roomIDField;
    private String checkInDate;
    private Panel CheckInDatePanel;
    private String checkOutDate;
    private Panel CheckOutDatePanel;

    public ReservationPanel() {
        setLayout(new GridLayout(6, 2));

        Label reservationIDLabel = new Label("Reservation ID:");
        reservationIDField = new TextField();

        Label customerIDLabel = new Label("Customer ID:");
        customerIDField = new TextField();

        Label roomIDLabel = new Label("Room ID:");
        roomIDField = new TextField();

        Label checkInLabel = new Label("Check-In Date (yyyy-MM-dd):");
        CheckInDatePanel = new DatePicker();

        Label checkOutLabel = new Label("Check-Out Date (yyyy-MM-dd):");
        CheckOutDatePanel = new DatePicker();

        Button makeReservationButton = new Button("Make Reservation");
        makeReservationButton.addActionListener(new ReservationButtonListener());

        add(reservationIDLabel);
        add(reservationIDField);

        add(customerIDLabel);
        add(customerIDField);

        add(roomIDLabel);
        add(roomIDField);

        add(checkInLabel);
        add(CheckInDatePanel);

        add(checkOutLabel);
        add(CheckOutDatePanel);

        add(makeReservationButton);
    }

    private class ReservationButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                int reservationID = Integer.parseInt(reservationIDField.getText());
                int customerID = Integer.parseInt(customerIDField.getText());
                int roomID = Integer.parseInt(roomIDField.getText());

//                Date checkInDate = new Date(checkInDateField.getText());
//                Date checkOutDate = new Date(checkOutDateField.getText());
                String checkInDate = ((DatePicker) CheckInDatePanel).getSelectedDate();
                String checkOutDate = ((DatePicker) CheckOutDatePanel).getSelectedDate();
                // replace the - with /
                checkInDate = checkInDate.replace("-", "/");
                checkOutDate = checkOutDate.replace("-", "/");




                // Create a new Reservation object with the input data
                Reservation newReservation = new Reservation(reservationID, customerID, roomID, new Date(checkInDate), new Date(checkOutDate));

                // Perform operations with the new reservation (e.g., save to database)
                // For demonstration purposes, you can print the new reservation details
                System.out.println("New Reservation Details:");
                System.out.println("Reservation ID: " + newReservation.getReservationID());
                System.out.println("Customer ID: " + newReservation.getCustomerID());
                System.out.println("Room ID: " + newReservation.getRoomID());
                System.out.println("Check-In Date: " + newReservation.getCheckInDate());
                System.out.println("Check-Out Date: " + newReservation.getCheckOutDate());
            } catch (NumberFormatException ex) {
                System.err.println("Invalid input format for IDs. Please enter valid numbers.");
            } catch (Exception ex) {
                System.err.println("Invalid date format or other input error. Please check your inputs.");
            }
        }
    }
}


