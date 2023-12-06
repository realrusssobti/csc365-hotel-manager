import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;
import java.util.ArrayList;

public class CheckInOutPanel extends JPanel {

    private ArrayList<Reservation> reservationsToCheckIn;
    private SQLQueries sqlConnection;

    public CheckInOutPanel(SQLQueries sqlConnection) {
        this.sqlConnection = sqlConnection;

        LocalDate currentDate = LocalDate.now();

        ArrayList<ArrayList<String>> reservationsInfo = sqlConnection.getReservationsBeyondDate(currentDate);
        this.reservationsToCheckIn = MainFrame.parseReservationObjects(reservationsInfo);

        setLayout(new BorderLayout());

        JPanel checkedInPanel = createCheckedInPanel();
        add(checkedInPanel, BorderLayout.NORTH);

        JPanel checkInReservationPanel = createCheckInReservationPanel();
        add(checkInReservationPanel, BorderLayout.CENTER);

        // Add debug text at the bottom
        JLabel debugLabel = new JLabel("Debug: " + reservationsToCheckIn.size() + " reservations to check in");
        add(debugLabel, BorderLayout.SOUTH);
    }

    private JButton createCheckOutButton(Reservation reservation) {
        JButton checkOutButton = new JButton("Check-Out");
        checkOutButton.addActionListener(e -> performCheckOut(reservation));
        return checkOutButton;
    }

    private void performCheckOut(Reservation reservation) {
        reservation.setCheckOutDate(LocalDate.now());
        reservation.setCheckedIn(false);

        LocalDate today = LocalDate.now();
        sqlConnection.setReservationCheckOut(today, reservation.getReservationID());
        System.out.println("Checked out Room " + reservation.getRoomNumber());
        // Update room status or perform necessary actions

        // Fetch updated data and recreate UI
        updateReservations();
        recreateUI();
    }

    private JButton createCheckInButton(Reservation reservation) {
        JButton checkInButton = new JButton("Check-In");
        checkInButton.addActionListener(e -> performCheckIn(reservation));
        return checkInButton;
    }

    private void performCheckIn(Reservation reservation) {
        reservation.setCheckInDate(LocalDate.now());
        reservation.setCheckedIn(true);

        LocalDate today = LocalDate.now();
        sqlConnection.setReservationCheckIn(today, reservation.getReservationID());
        System.out.println("Checked in Reservation " + reservation.getReservationID());
        // Update reservation status or perform necessary actions

        // Fetch updated data and recreate UI
        updateReservations();
        recreateUI();
    }

    private JPanel createCheckedInPanel() {
        JPanel checkedInPanel = new JPanel(new FlowLayout());

        for (Reservation reservation : reservationsToCheckIn) {
            if (reservation.getCheckedIn()) {
                JLabel roomLabel = new JLabel("Room " + reservation.getRoomNumber());
                JButton checkOutButton = createCheckOutButton(reservation);
                checkedInPanel.add(roomLabel);
                checkedInPanel.add(checkOutButton);
            }
        }

        // Add border for visual appeal
        checkedInPanel.setBorder(BorderFactory.createTitledBorder("Checked In Rooms"));

        return checkedInPanel;
    }

    private JPanel createCheckInReservationPanel() {
        JPanel checkInReservationPanel = new JPanel(new FlowLayout());

        for (Reservation reservation : reservationsToCheckIn) {
            if (!reservation.getCheckedIn()) {
                JLabel reservationLabel = new JLabel("Reservation " + reservation.getReservationID());
                JButton checkInButton = createCheckInButton(reservation);

                checkInReservationPanel.add(reservationLabel);
                checkInReservationPanel.add(checkInButton);
            }
        }

        // Add border for visual appeal
        checkInReservationPanel.setBorder(BorderFactory.createTitledBorder("Check In Reservations"));

        return checkInReservationPanel;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Check-In/Check-Out Panel");
            CheckInOutPanel checkInOutPanel = new CheckInOutPanel(new SQLQueries());

            frame.setLayout(new BorderLayout());
            frame.add(checkInOutPanel, BorderLayout.CENTER);

            frame.setSize(800, 600);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setVisible(true);
        });
    }

    public void updateReservations() {
        LocalDate currentDate = LocalDate.now();

        ArrayList<ArrayList<String>> reservationsInfo = sqlConnection.getReservationsBeyondDate(currentDate);
        this.reservationsToCheckIn = MainFrame.parseReservationObjects(reservationsInfo);
    }

    private void recreateUI() {
        removeAll();

        JPanel checkedInPanel = createCheckedInPanel();
        add(checkedInPanel, BorderLayout.NORTH);

        JPanel checkInReservationPanel = createCheckInReservationPanel();
        add(checkInReservationPanel, BorderLayout.CENTER);

        // Add debug text at the bottom
        JLabel debugLabel = new JLabel("Debug: " + reservationsToCheckIn.size() + " reservations to check in");
        add(debugLabel, BorderLayout.SOUTH);

        revalidate();
        repaint();
    }
}
