import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Date;

public class MainDashboard extends Panel {
    private ArrayList<Room> rooms;
    private ArrayList<Reservation> reservations;

    public MainDashboard(ArrayList<Room> rooms, ArrayList<Reservation> reservations) {
        this.rooms = rooms;
        this.reservations = reservations;

        setLayout(new FlowLayout());

        displayRooms();
    }

    private void displayRooms() {
        for (Room room : rooms) {
            Button roomButton = new Button("Room " + room.getRoomNumber());
            roomButton.addActionListener(new RoomButtonClickListener(room));
            roomButton.setBackground(getButtonColor(room.getRoomNumber()));
            add(roomButton);
        }
    }

    private Color getButtonColor(int roomID) {
//        System.out.println("Checking room " + roomID + "...");
        for (Reservation reservation : reservations) {
//            System.out.println("Checking reservation " + reservation.getReservationID() + "...");
//            System.out.println("Room ID: " + reservation.getRoomID());
            if (reservation.getRoomID() == roomID) {
//                System.out.println("Room " + roomID + " is occupied!");

                return Color.RED; // Room is occupied
            }
        }
        System.out.println("Room " + roomID + " is available!");
        return Color.GREEN; // Room is available
    }

    private class RoomButtonClickListener implements ActionListener {
        private Room room;

        public RoomButtonClickListener(Room room) {
            this.room = room;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            // Add any specific action when the button is clicked (if needed)
            System.out.println("Room " + room.getRoomNumber() + " clicked!");
        }
    }

    public static void main(String[] args) {
        // Sample data for testing
        Room[] roomsList = {new Room(1, 101, "Single", 100.0),
                new Room(2, 102, "Double", 150.0),
                new Room(3, 103, "Suite", 200.0)};

        Reservation[] reservationsList = {new Reservation(1, 1, 101, new Date(), new Date()),
                new Reservation(2, 2, 102, new Date(), new Date())};
        ArrayList<Room> rooms = new ArrayList<>();
        ArrayList<Reservation> reservations = new ArrayList<>();
        for (Room room : roomsList) {
            rooms.add(room);
        }
        for (Reservation reservation : reservationsList) {
            reservations.add(reservation);
        }

        MainDashboard mainDashboard = new MainDashboard(rooms, reservations);
        // Assuming you have a Frame to contain the Panel
        Frame frame = new Frame("Main Dashboard");
        frame.setSize(400, 300);
        frame.setLayout(new FlowLayout());
        frame.add(mainDashboard);
        frame.setVisible(true);
    }
}
