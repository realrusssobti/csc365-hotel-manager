import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Date;

public class RoomPanel extends Panel {
    private ArrayList<Room> rooms;
    private ArrayList<Reservation> reservations;

    public RoomPanel(ArrayList<Room> rooms, ArrayList<Reservation> reservations) {
        this.rooms = rooms;
        this.reservations = reservations;

        setLayout(new FlowLayout());
//        add(new Label("This is the Room Status Screen"), BorderLayout.CENTER);

        displayRooms();
    }

    private void displayRooms() {
        for (Room room : rooms) {
            Button roomButton = new Button("Room " + room.getRoomNumber());
            roomButton.addActionListener(new RoomButtonClickListener(room));
            roomButton.setBackground(getButtonColor(room.getRoomNumber()));
            add(roomButton, BorderLayout.CENTER);
        }
    }

    private Color getButtonColor(int roomID) {
        for (Reservation reservation : reservations) {
            if (reservation.getRoomID() == roomID) {
                return Color.RED; // Room is occupied
            }
        }
        return Color.GREEN; // Room is available
    }

    private class RoomButtonClickListener implements ActionListener {
        private Room room;

        public RoomButtonClickListener(Room room) {
            this.room = room;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            System.out.println("Room " + room.getRoomNumber() + " clicked!");
        }
    }

}
