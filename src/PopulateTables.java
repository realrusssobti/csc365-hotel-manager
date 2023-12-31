import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Random;

public class PopulateTables {

    public static int room_counter = 100;
    public static int guest_counter = 0;
    public static int reservation_counter = 0;
    private static Connection connection;

    PopulateTables() {
        try {
            connection = DriverManager.getConnection("jdbc:mysql://ambari-node5.csc.calpoly.edu:3306", "rsobti",
                    "28103315");
            // Use RSOBTI
            connection.createStatement().execute("USE rsobti");
        } catch (SQLException e) {
            e.printStackTrace();
        }

        resetTables();

        // generate the rooms and add them calling the addRoom function
        ArrayList<Room> rooms = generateRooms(10, 50, 40);
        for (Room room : rooms) {
            addRoom(room, connection);
        }

        // generate guests and add them calling addGuests
        ArrayList<Guest> guests = generateGuests(80);
        for (Guest guest : guests) {
            addGuest(guest, connection);
        }

        ArrayList<Reservation> reservations = generateReservations();
        for (Reservation reservation : reservations) {
            addReservation(reservation);
        }

        // //can move this into a function but for now just here for creating 1
        // reservation
        // int customer_id = generate_random_num(0, guest_counter - 1);
        // int room_id = generate_random_num(0, room_counter - 1);
        // LocalDate checkin_date = LocalDate.of(2014, 1, 1);
        // LocalDate checkout_date = LocalDate.of(2014, 1, 20);

        // Reservation reservation_1 = new Reservation(reservation_counter, 1, 101,
        // checkin_date, checkout_date);
        // reservation_counter++;
        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static ArrayList<Room> generateRooms(int num_suites, int num_reg, int num_basic) {
        ArrayList<Room> rooms = new ArrayList<>();
        int room_counter = 100;

        for (int i = 0; i < num_basic; i++) {
            int price = 70;
            Room rm = new Room(room_counter, "Basic", price);
            rooms.add(rm);
            room_counter++;
        }

        for (int i = 0; i < num_reg; i++) {
            int price = 120;
            Room rm = new Room(room_counter, "Regular", price);
            rooms.add(rm);
            room_counter++;
        }

        for (int i = 0; i < num_suites; i++) {
            int price = 180;
            Room rm = new Room(room_counter, "Suite", price);
            rooms.add(rm);
            room_counter++;
        }

        return rooms;
    }

    private static void resetRooms() {
        return;
    }

    public static ArrayList<Guest> generateGuests(int num_guests) {
        ArrayList<Guest> guests = new ArrayList<>();
        String[] first_names = {"Alex", "Cameron", "Ethan", "Ben", "JP", "Kira", "Elijah", "Tray", "Jessica",
                "Sahara"};
        String[] addresses = {"1 grand", "151 madona", "1145 newport", "992 walnut", "403 canyon cir", "470 foothill",
                "49 fortuna", "887 gilroy"};
        int guest_counter = 0;

        for (int i = 0; i < num_guests; i++) {
            String first_name = first_names[generate_random_num(0, first_names.length)];
            String last_name = String.valueOf(Character.toUpperCase(generate_random_char()));
            String email = first_name + "." + last_name + "@gmail.com";
            String ph_num = String.valueOf(generate_random_num(1111111111, Integer.MAX_VALUE));
            String address = addresses[generate_random_num(0, addresses.length)];
            String zip_code = String.valueOf(generate_random_num(93401, 93412));

            Guest new_guest = new Guest(guest_counter, first_name, last_name, email, ph_num, address, "San Luis Obispo",
                    "CA", zip_code);
            guests.add(new_guest);
            guest_counter++;
        }

        return guests;
    }

    public static ArrayList<Reservation> generateReservations() {
        // get users from database
        ArrayList<ArrayList<String>> customers_info = SQLQueries.getCustomersAll();
        // get the list of Guest IDs
        ArrayList<Integer> guest_ids = new ArrayList<Integer>();
        for (int i = 0; i < customers_info.size(); i++) {
            guest_ids.add(Integer.valueOf(customers_info.get(i).get(0)));
        }


        ArrayList<Reservation> reservations = new ArrayList<>();

        // customer 1 reserves room 101 from jan-1 to jan-30 in 2014
        Reservation r1 = new Reservation(1, guest_ids.get((int) (Math.random() * guest_ids.size())), 101, LocalDate.of(2014, 1, 1), LocalDate.of(2014, 1, 30), false);

        // customer 2 reserves room 150 from jan-4 to feb-5 in 2014
        Reservation r2 = new Reservation(2, guest_ids.get((int) (Math.random() * guest_ids.size())), 150, LocalDate.of(2014, 1, 4), LocalDate.of(2014, 2, 5), false);

        // customer 3 reserves room 190 from dec-1 to dec-26 in 2023 to cheat on his
        // wife during christmas
        Reservation r3 = new Reservation(3, guest_ids.get((int) (Math.random() * guest_ids.size())), 190, LocalDate.of(2023, 12, 1), LocalDate.of(2023, 12, 26), false);

        // customer 3 also reserves room 191 from dec-2 to dec-27 in 2023 for the person
        // he is cheating with
        Reservation r4 = new Reservation(4, guest_ids.get((int) (Math.random() * guest_ids.size())), 191, LocalDate.of(2023, 12, 2), LocalDate.of(2023, 12, 27), false);

        // customer 4 reserves room 192 from dec-1 to dec-26 in 2023 to spy on her
        // cheating husband after seeing his recent checkings history
        Reservation r5 = new Reservation(5, guest_ids.get((int) (Math.random() * guest_ids.size())), 191, LocalDate.of(2023, 12, 1), LocalDate.of(2023, 12, 26), false);

        // customer 10 reserves room 120 from jun-5 to jul-30 in 2024 for summer break
        Reservation r6 = new Reservation(6, guest_ids.get((int) (Math.random() * guest_ids.size())), 120, LocalDate.of(2024, 6, 5), LocalDate.of(2024, 7, 30), false);

        reservations.add(r1);
        reservations.add(r2);
        reservations.add(r3);
        reservations.add(r4);
        reservations.add(r5);
        reservations.add(r6);

        return reservations;
    }

    public static void resetTables() {
        deleteTables();
        createTables();
    }

    // deletes tables
    private static void deleteTables() {
        try {
            String resetGuestQuery = "DELETE FROM Guest"; // Updated for MySQL syntax.
            String resetRoomKeyQuery = "DELETE FROM RoomKey";
            String resetRoomQuery = "DELETE FROM Room";
            String resetBookingQuery = "DELETE FROM Booking";
            String resetReceiptQuery = "DELETE FROM Receipt";
            PreparedStatement deleteStatement = connection.prepareStatement(resetReceiptQuery);
            deleteStatement.executeUpdate();
            deleteStatement = connection.prepareStatement(resetRoomKeyQuery);
            deleteStatement.executeUpdate();
            deleteStatement = connection.prepareStatement(resetBookingQuery);
            deleteStatement.executeUpdate();
            deleteStatement = connection.prepareStatement(resetRoomQuery);
            deleteStatement.executeUpdate();
            deleteStatement = connection.prepareStatement(resetGuestQuery);
            deleteStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.println("deleting rooms");
    }
    // creates tables
    private static void createTables() {
        try {
            String createGuestQuery = "CREATE TABLE Guest (" +
                                            "GuestID INT NOT NULL AUTO_INCREMENT," +
                                            "FirstName VARCHAR(50) NOT NULL," +
                                            "LastName VARCHAR(50) NOT NULL," +
                                            "Email VARCHAR(50) NOT NULL," +
                                            "Phone VARCHAR(50) NOT NULL," +
                                            "Addr VARCHAR(50) NOT NULL," +
                                            "City VARCHAR(50) NOT NULL," +
                                            "St VARCHAR(50) NOT NULL," +
                                            "Zip VARCHAR(50) NOT NULL," +
                                            "PRIMARY KEY (GuestID)" +
                                            ")";
            String createRoomQuery = "CREATE TABLE Room (" +
                                            "RoomNumber INT NOT NULL," +
                                            "RoomType VARCHAR(50) NOT NULL," +
                                            "RoomPrice DECIMAL(10,2) NOT NULL," +
                                            "PRIMARY KEY (RoomNumber)" +
                                            ")";
            String createRoomKeyQuery = "CREATE TABLE RoomKey (" +
                                            "RoomKeyID INT NOT NULL AUTO_INCREMENT," +
                                            "RoomNumber INT NOT NULL," +
                                            "Expiration DATE NOT NULL," +
                                            "PRIMARY KEY (RoomKeyID)," +
                                            "FOREIGN KEY (RoomNumber) REFERENCES Room(RoomNumber)" +
                                            ")";
            String createBookingQuery = "CREATE TABLE Booking (" +
                                            "BookingID INT NOT NULL AUTO_INCREMENT," +
                                            "GuestID INT NOT NULL," +
                                            "RoomNumber INT NOT NULL," +
                                            "CheckInDate DATE NOT NULL," +
                                            "CheckOutDate DATE NOT NULL," +
                                            "CheckedIn BOOLEAN DEFAULT false," +
                                            "PRIMARY KEY (BookingID)," +
                                            "FOREIGN KEY (GuestID) REFERENCES Guest(GuestID)," +
                                            "FOREIGN KEY (RoomNumber) REFERENCES Room(RoomNumber)" +
                                            ");";
            String createReceiptQuery = "CREATE TABLE Receipt (" +
                                            "ReceiptID INT NOT NULL AUTO_INCREMENT," +
                                            "GuestID INT NOT NULL," +
                                            "BookingID INT NOT NULL," +
                                            "ReceiptDate DATE NOT NULL," +
                                            "TotalAmount DECIMAL(10,2) NOT NULL," +
                                            "PRIMARY KEY (ReceiptID)," +
                                            "FOREIGN KEY (GuestID) REFERENCES Guest(GuestID)," +
                                            "FOREIGN KEY (BookingID) REFERENCES Booking(BookingID)" +
                                            ");";
            PreparedStatement createStatement = connection.prepareStatement(createGuestQuery);
            createStatement.executeUpdate();
            createStatement = connection.prepareStatement(createRoomKeyQuery);
            createStatement.executeUpdate();
            createStatement = connection.prepareStatement(createRoomQuery);
            createStatement.executeUpdate();
            createStatement = connection.prepareStatement(createBookingQuery);
            createStatement.executeUpdate();
            createStatement = connection.prepareStatement(createReceiptQuery);
            createStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.println("creating rooms");
    }



    public static void addGuest(Guest guest, Connection connection) {
        try {
            String query = "INSERT INTO Guest (FirstName, LastName, Email, Phone, Addr, City, St, Zip) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, guest.getFirstName());
            statement.setString(2, guest.getLastName());
            statement.setString(3, guest.getEmail());
            statement.setString(4, guest.getPhone());
            statement.setString(5, guest.getAddress());
            statement.setString(6, guest.getCity());
            statement.setString(7, guest.getState());
            statement.setString(8, guest.getZip());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void addRoom(Room room, Connection connection) {
        try {
            String query = "INSERT INTO Room (RoomNumber, RoomType, RoomPrice) VALUES (?, ?, ?)";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, room.getRoomNumber());
            statement.setString(2, room.getRoomType());
            statement.setDouble(3, room.getRoomPrice());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void addReservation(Reservation reservation) {
        String firstName;
        String lastName;
        String roomType;

        // get the info from the reservation
        String customer_id = String.valueOf(reservation.getCustomerID());
        // get the customer name from the customer id
        ArrayList<String> customer_info = SQLQueries.getCustomerInfo(customer_id);
        // check if the customer exists
        if (customer_info.size() > 0) {
            firstName = customer_info.get(0);
            lastName = customer_info.get(1);
            // convert CheckInDate from LocalDate to SQL date
            Date checkInDateSQL = Date.valueOf(reservation.getCheckInDate());
            // convert CheckOutDate from LocalDate to SQL date
            Date checkOutDateSQL = Date.valueOf(reservation.getCheckOutDate());
            roomType = SQLQueries.getRoomType(String.valueOf(reservation.getRoomNumber()));
            // find an eligible room
            int roomNumber = SQLQueries.findEligibleRoom(roomType, checkInDateSQL, checkOutDateSQL);

            SQLQueries.addReservation(customer_id, checkInDateSQL, checkOutDateSQL, String.valueOf(roomNumber), reservation.getCheckedIn());

        } else {
            System.out.println("Customer does not exist");
        }


    }

    public static int generate_random_num(int min, int max) {
        int random_num = (int) (Math.random() * (max - min)) + min;
        return random_num;
    }

    public static char generate_random_char() {
        Random r = new Random();
        char c = (char) (r.nextInt(26) + 'a');

        return c;
    }
}
