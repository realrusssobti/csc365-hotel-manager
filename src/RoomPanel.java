import com.sun.tools.javac.Main;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class RoomPanel extends Panel {
    private ArrayList<Room> rooms;
    private ArrayList<Reservation> reservations;
    private static SQLQueries sqlConnection;

    public RoomPanel(ArrayList<Room> rooms, ArrayList<Reservation> reservations, SQLQueries sqlConnection) {
        this.rooms = rooms;
        this.reservations = reservations;
        this.sqlConnection = sqlConnection;

        // Fetch rooms and reservations from database
        this.rooms = MainFrame.parseRoomObjects(sqlConnection.getRoomStatus());
        this.reservations = MainFrame.parseReservationObjects(sqlConnection.getReservationAll());
        for (Room room : this.rooms) {
            System.out.println("Room " + room.getRoomNumber() + " is a " + room.getRoomType() + " room.");
        }
        for (Reservation reservation : this.reservations) {
            System.out.println("Reservation " + reservation.getReservationID() + " is for room " + reservation.getRoomNumber());
        }
        System.out.println("RoomPanel initialized!");
        setLayout(new FlowLayout());
//        add(new Label("This is the Room Status Screen"), BorderLayout.CENTER);

        displayRooms();
    }

    private void displayRooms() {
        for (Room room : rooms) {
            System.out.println("Rendering room " + room.getRoomNumber());
            Button roomButton = new Button("Room " + room.getRoomNumber());
            roomButton.addActionListener(new RoomButtonClickListener(room, sqlConnection));
            roomButton.setBackground(getButtonColor(room.getRoomNumber()));
            add(roomButton, BorderLayout.CENTER);
        }
    }

    private Color getButtonColor(int roomID) {
        for (Reservation reservation : reservations) {
            if (reservation.getRoomNumber() == roomID) {
                return Color.RED; // Room is occupied
            }
        }
        return Color.GREEN; // Room is available
    }

    private class RoomButtonClickListener implements ActionListener {
        private Room room;
        private SQLQueries sqlConnection;

        public RoomButtonClickListener(Room room, SQLQueries sqlConnection) {
            this.room = room;
            this.sqlConnection = sqlConnection;
        }

        @Override
        public void actionPerformed(ActionEvent e) {

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

            //Uncomment when connection works
            ArrayList<String> room_info = sqlConnection.getRoomStatus(room.getRoomNumber());
            // print out the room info
            for (String info : room_info){
                System.out.println(info);
            }

            System.out.println("Room " + room.getRoomNumber() + " clicked!");
            //RoomStatusPopup popup = new RoomStatusPopup(String.valueOf(room.getRoomNumber()), false, 3, "John Doe", "2023-12-15");
            //uncomment and delete prev line when connection works

            String guest_name = room_info.get(4) + room_info.get(5);
            int num_keys = Integer.parseInt(room_info.get(3));
            String check_out_date_string = room_info.get(6);
            
            boolean isTaken = false;
            LocalDate curr_date = LocalDate.now();
            LocalDate check_in_date = LocalDate.parse(room_info.get(7), formatter);

            RoomStatusPopup popup = new RoomStatusPopup(String.valueOf(room.getRoomNumber()), isTaken, num_keys, guest_name, check_out_date_string);
            popup.setSize(300, 500);
            popup.setVisible(true);
        }
    }

}
