import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class RoomStatusPopup extends Frame {

    private final String roomNumber;
    private final boolean isTaken;
    private final int numberOfKeys;
    private final String customerName;
    private final String checkoutDate;

    public RoomStatusPopup(String roomNumber, boolean isTaken, int numberOfKeys, String customerName, String checkoutDate) {
        this.roomNumber = roomNumber;
        this.isTaken = isTaken;
        this.numberOfKeys = numberOfKeys;
        this.customerName = customerName;
        this.checkoutDate = checkoutDate;

        initializeComponents();
    }

    private void initializeComponents() {
        setTitle("RoomStatus - Room " + roomNumber);
        setSize(300, 200);
        setLayout(new GridLayout(5, 1));

        add(new Label("Room " + roomNumber));
        add(new Label(isTaken ? "Status: TAKEN" : "Status: VACANT"));
        add(new Label("Keys: " + numberOfKeys));
        add(new Label("Customer: " + (isTaken ? customerName : "N/A")));
        add(new Label("Checkout Date: " + (isTaken ? checkoutDate : "N/A")));

        Button closeButton = new Button("Close");
        closeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose(); // Close the pop-up
            }
        });

        add(closeButton);

        Button makeReservationButton = new Button("Make Reservation");
        makeReservationButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                BookingRoomPopup reservationPopup = new BookingRoomPopup(roomNumber);
                reservationPopup.setSize(600, 400);
                reservationPopup.setVisible(true);
            }
        });

        add(makeReservationButton);

        // Handle window closing event
        addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                dispose(); // Close the pop-up
            }
        });
    }

    public void showPopup() {
        setVisible(true);
    }

    public static void main(String[] args) {
        // Dummy testing view
        EventQueue.invokeLater(() -> {
            RoomStatusPopup roomStatusPopup = new RoomStatusPopup("101", true, 3, "John Doe", "2023-12-15");
            roomStatusPopup.showPopup();
        });
    }
}
