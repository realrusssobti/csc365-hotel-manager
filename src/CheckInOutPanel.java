import java.awt.*;
import java.awt.event.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;

public class CheckInOutPanel extends Panel {

    private final ArrayList<Reservation> reservations;
    private SQLQueries sqlConnection;

    public CheckInOutPanel(SQLQueries sqlConnection) {
        this.sqlConnection = sqlConnection;
        // this.reservations = new ArrayList<>();
        // reservations.add(new Reservation(1, 101, 1, LocalDate.now(), null)); // Replace null with actual check-out date
        // reservations.add(new Reservation(2, 102, 2, null, null)); // Replace null with actual check-in date


        LocalDate curr_date = LocalDate.now();

        //getting upcoming reservations from db (reservations with check-in date later than current date)
        ArrayList<ArrayList<String>> reservations_info = sqlConnection.getReservationsBeyondDate(curr_date);
        this.reservations = MainFrame.parseReservationObjects(reservations_info);

        setLayout(new GridLayout(2, 1));

        Panel checkedInPanel = createCheckedInPanel();
        add(checkedInPanel);

        Panel checkInReservationPanel = createCheckInReservationPanel();
        add(checkInReservationPanel);
    }

    private Button createCheckOutButton(Reservation reservation) {
        Button checkOutButton = new Button("Check-Out");
        checkOutButton.addActionListener(e -> performCheckOut(reservation));
        return checkOutButton;
    }

    private void performCheckOut(Reservation reservation) {
        reservation.setCheckOutDate(LocalDate.now()); // Set the check-out date
        System.out.println("Checked out Room " + reservation.getRoomNumber());
        // Update room status or perform necessary actions
    }

    private Button createCheckInButton(Reservation reservation) {
        Button checkInButton = new Button("Check-In");
        checkInButton.addActionListener(e -> performCheckIn(reservation));
        return checkInButton;
    }

    private void performCheckIn(Reservation reservation) {
        reservation.setCheckInDate(LocalDate.now()); // Set the check-in date
        System.out.println("Checked in Reservation " + reservation.getReservationID());
        // Update reservation status or perform necessary actions
    }

    private Panel createCheckedInPanel() {
        Panel checkedInPanel = new Panel(new GridLayout(0, 2));

        for (Reservation reservation : reservations) {
            if (reservation.getCheckInDate() != null && reservation.getCheckOutDate() == null) {
                Label roomLabel = new Label("Room " + reservation.getRoomNumber());
                Button checkOutButton = createCheckOutButton(reservation);

                checkedInPanel.add(roomLabel);
                checkedInPanel.add(checkOutButton);
            }
        }

        return checkedInPanel;
    }

    private Panel createCheckInReservationPanel() {
        Panel checkInReservationPanel = new Panel(new GridLayout(0, 2));

        for (Reservation reservation : reservations) {
            if (reservation.getCheckInDate() == null) {
                Label reservationLabel = new Label("Reservation " + reservation.getReservationID());
                Button checkInButton = createCheckInButton(reservation);

                checkInReservationPanel.add(reservationLabel);
                checkInReservationPanel.add(checkInButton);
            }
        }

        return checkInReservationPanel;
    }

    // protected static ArrayList<Reservation> parseReservationObjects (ArrayList<ArrayList<String>> sqlData){
    //     ArrayList<Reservation> reservations = new ArrayList<>();
    //     DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    //     for (int i=0; i<sqlData.size(); i++){
    //         ArrayList<String> reservation_info = sqlData.get(i);

    //         int bookingID = Integer.parseInt(reservation_info.get(5));
    //         int guestID = Integer.parseInt(reservation_info.get(6));
    //         int room_number = Integer.parseInt(reservation_info.get(7));
    //         LocalDate checkin_date = LocalDate.parse(reservation_info.get(2), formatter);
    //         LocalDate checkout_date = LocalDate.parse(reservation_info.get(3), formatter);

    //         Reservation new_reservation = new Reservation(bookingID, guestID, room_number, checkin_date, checkout_date);
    //         reservations.add(new_reservation);
    //     }


    //     return reservations;
    // }


    public static void main(String[] args) {

        Frame frame = new Frame("Check-In/Check-Out Panel");
        CheckInOutPanel checkInOutPanel = new CheckInOutPanel();

        frame.setLayout(new BorderLayout());
        frame.add(checkInOutPanel, BorderLayout.CENTER);

        frame.setSize(400, 300);
        frame.setVisible(true);

        frame.addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(WindowEvent windowEvent) {
                System.exit(0);
            }
        });
    }
}

