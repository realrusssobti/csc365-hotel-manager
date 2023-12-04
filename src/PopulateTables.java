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
    public static void main (String[]args){


        try{
         connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/your_database_name", "root", "your_password");}
            catch (SQLException e) {
                e.printStackTrace();
            }

        //generate the rooms and add them calling the addRoom function
        ArrayList<Room> rooms = generateRooms(10, 50, 40);
        for (Room room : rooms){
            addRoom(room, connection);
        }

        //generate guests and add them calling addGuests
        ArrayList<Guest> guests = generateGuests(80);
        for (Guest guest : guests){
            addGuest(guest, connection);
        }

        ArrayList<Reservation> reservations = new ArrayList<>();


        //can move this into a function but for now just here for creating 1 reservation
        int customer_id = generate_random_num(0, guest_counter - 1);
        int room_id = generate_random_num(0, room_counter - 1);
        LocalDate checkin_date = LocalDate.of(2014, 1, 1);
        LocalDate checkout_date = LocalDate.of(2014, 1, 20);

        Reservation reservation_1 = new Reservation(reservation_counter, 1, 101, checkin_date, checkout_date);
        reservation_counter++;
    }

    public static ArrayList<Room> generateRooms(int num_suites, int num_reg, int num_basic){
        ArrayList<Room> rooms = new ArrayList<>();

        for (int i=0; i<num_basic; i++){
            int price = 70;
            Room rm = new Room(room_counter, room_counter, "Basic", price);
            rooms.add(rm);
            room_counter++;
        }

        for (int i=0; i<num_reg; i++){
            int price = 120;
            Room rm = new Room(room_counter, room_counter, "Regular", price);
            rooms.add(rm);
            room_counter++;
        }

        for (int i=0; i<num_suites; i++){
            int price = 180;
            Room rm = new Room(room_counter, room_counter, "Suite", price);
            rooms.add(rm);
            room_counter++;
        }

        return rooms;
    }

    public static ArrayList<Guest> generateGuests(int num_guests){
        ArrayList<Guest> guests = new ArrayList<>();
        String[] first_names = {"Alex", "Cameron", "Ethan", "Ben", "JP", "Kira", "Elijah", "Tray", "Jessica", "Sahara"};
        String[] addresses = {"1 grand", "151 madona", "1145 newport", "992 walnut", "403 canyon cir", "470 foothill", "49 fortuna", "887 gilroy"};

        for (int i=0; i<num_guests; i++){
            String first_name = first_names[generate_random_num(0, first_names.length)];
            String last_name = String.valueOf(generate_random_char());
            String email = first_name + "." + last_name + "@gmail.com";
            String ph_num = String.valueOf(generate_random_num(1111111111, Integer.MAX_VALUE));
            String address = addresses[generate_random_num(0, addresses.length)];
            String zip_code = String.valueOf(generate_random_num(93401, 93412));

            Guest new_guest = new Guest(guest_counter, first_name, last_name, email, ph_num, address, "San Luis Obispo", "CA", zip_code);
            guests.add(new_guest);
            guest_counter++;
        }

        return guests;
    }

    public static void addGuest(Guest guest, Connection connection){
        try{
            String query = "INSERT INTO Guest (FirstName, LastName, Email, Phone, Addr, City, St, Zip) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, guest.getFirstName());
            statement.setString(2, guest.getLastName());
            statement.setString(1, guest.getEmail());
            statement.setString(4, guest.getPhone());
            statement.setString(5, guest.getAddress());
            statement.setString(6, guest.getCity());
            statement.setString(7, guest.getState());
            statement.setString(8, guest.getZip());
            statement.executeUpdate();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void addRoom(Room room, Connection connection){
        try{
            String query = "INSERT INTO Room (RoomNumber, RoomType, RoomPrice) VALUES (?, ?, ?)";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, room.getRoomNumber());
            statement.setString(2, room.getRoomType());
            statement.setDouble(3, room.getRoomPrice());
            statement.executeUpdate();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static int generate_random_num(int min, int max){
        int random_num = (int) (Math.random() * (max - min)) + min;
        return random_num;
    }

    public static char generate_random_char(){
        Random r = new Random();
        char c = (char)(r.nextInt(26) + 'a');

        return c;
    }
}
