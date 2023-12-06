import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.time.LocalDate;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.sql.Date;
import java.util.*;

// import mySQL


/* Finished queries for
 * getting all reservations & getting a reservation by start/end dates
 * getting room info for room status
 * get key count for all rooms & get key count for specific room number
 */

public class SQLQueries {
    private static Connection connection;

    public SQLQueries() {
        try{
            connection = DriverManager.getConnection("jdbc:mysql://ambari-node5.csc.calpoly.edu:3306","rsobti","28103315");
            // Use RSOBTI
            connection.createStatement().execute("USE rsobti");
            System.out.println("Database connection opened.");
           }
           catch (SQLException e) {
               e.printStackTrace();
           }
    }
    //ends connection to DB
    public void endConnection() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
                System.out.println("Database connection closed.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public ArrayList<ArrayList<String>> getCustomersAll(){
        System.out.println("Getting all customers");

        ArrayList<ArrayList<String>> table = new ArrayList<>();
        try {
            String query =  "SELECT * FROM Guest;";
                                
            try (Statement statement = connection.createStatement()) {
                ResultSet rs = statement.executeQuery(query);
                while (rs.next()) {
                    ArrayList<String> tuple = new ArrayList<>();

                    int guest_id_obj     = rs.getInt("GuestID"); 
                    String guestID      = Integer.toString(guest_id_obj); // convert to string
                    String first_name = rs.getString("FirstName");
                    String last_name = rs.getString("LastName");
                    String email = rs.getString("Email");
                    String ph_num = rs.getString("Phone");
                    String address = rs.getString("Addr");
                    String city = rs.getString("City");
                    String state = rs.getString("St");
                    String zip = rs.getString("Zip");

                    // Date check_in_obj = rs.getDate("CheckInDate");
                    // String check_in_date = check_in_obj.toString(); // convert to string
                    // Date check_out_obj = rs.getDate("CheckOutDate");
                    // String check_out_date = check_out_obj.toString(); // convert to string
                    // String room_type = rs.getString("RoomType");
                    // int booking_id_obj     = rs.getInt("BookingID"); 
                    // String bookingID      = Integer.toString(booking_id_obj); // convert to string
                    
                    // int room_number_obj     = rs.getInt("RoomNumber"); 
                    // String room_number      = Integer.toString(room_number_obj); // convert to string
                    
                    Collections.addAll(tuple, guestID, first_name, last_name, email, ph_num, address, city, state, zip);
                    table.add(tuple); // add new tuple to table list
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return table;

    }

    // returns a list of tuples: first name, last name, check in date, check out date, room type
    public ArrayList<ArrayList<String>> getReservationAll() {
        System.out.println("Getting all reservations");
        ArrayList<ArrayList<String>> table = new ArrayList<>();
        try {
            String query =  "SELECT G.FirstName, G.LastName, B.CheckInDate, B.CheckOutDate, R.RoomType, B.BookingID, G.GuestID, R.RoomNumber " +
                                "FROM Booking B, Guest G, Room R " +
                                "WHERE B.GuestID = G.GuestID AND B.RoomNumber = R.RoomNumber;";
            try (Statement statement = connection.createStatement()) {
                ResultSet rs = statement.executeQuery(query);
                while (rs.next()) {
                    ArrayList<String> tuple = new ArrayList<>();
                    String first_name = rs.getString("FirstName");
                    String last_name = rs.getString("LastName");
                    
                    Date check_out_obj      = rs.getDate("CheckOutDate");
                    String check_out_date   ; // declare string
                    if (check_out_obj == null) {
                         check_out_date = new Date(0).toString();
                    }
                    else { check_out_date   = check_out_obj.toString(); // convert to string
                         }

                    Date check_in_obj      = rs.getDate("CheckInDate");
                    String check_in_date   ; // declare string
                    if (check_in_obj == null) {
                         check_in_date = new Date(0).toString();
                    }
                    else { check_in_date   = check_in_obj.toString(); // convert to string
                    }

                    String room_type = rs.getString("RoomType");
                    int booking_id_obj     = rs.getInt("BookingID"); 
                    String bookingID      = Integer.toString(booking_id_obj); // convert to string
                    int guest_id_obj     = rs.getInt("GuestID"); 
                    String guestID      = Integer.toString(guest_id_obj); // convert to string
                    int room_number_obj     = rs.getInt("RoomNumber"); 
                    String room_number      = Integer.toString(room_number_obj); // convert to string
                    Collections.addAll(tuple, first_name, last_name, check_in_date, check_out_date, room_type, bookingID, guestID, room_number);
                    table.add(tuple); // add new tuple to table list
                    System.out.println("here");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return table;
    }
    // returns a single tuple: first name, last name, check in date, check out date, room type
    public ArrayList<String> getReservationByDate(Date givenCheckIn, Date givenCheckOut) {
        ArrayList<String> tuple = new ArrayList<>();
        try {
            String query =  "SELECT G.FirstName, G.LastName, B.CheckInDate, B.CheckOutDate, R.RoomType, B.BookingID, G.GuestID, R.RoomNumber " +
                                "FROM Booking B, Guest G, Room R " +
                                "WHERE B.GuestID = G.GuestID AND B.RoomNumber = R.RoomNumber " +
                                "AND B.CheckInDate = ? AND B.CheckOutDate = ?;";
            try (PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setDate(1, givenCheckIn);
                statement.setDate(2, givenCheckOut);
                ResultSet rs = statement.executeQuery();
                while (rs.next()) {
                    String first_name       = rs.getString("FirstName");
                    String last_name        = rs.getString("LastName");
                    
                    Date check_out_obj      = rs.getDate("CheckOutDate");
                    String check_out_date   ; // declare string
                    if (check_out_obj == null) {
                         check_out_date = new Date(0).toString();
                    }
                    else { check_out_date   = check_out_obj.toString(); // convert to string
                         }

                    Date check_in_obj      = rs.getDate("CheckInDate");
                    String check_in_date   ; // declare string
                    if (check_in_obj == null) {
                         check_in_date = new Date(0).toString();
                    }
                    else { check_in_date   = check_in_obj.toString(); // convert to string
                    }

                    String room_type        = rs.getString("RoomType");
                    int booking_id_obj      = rs.getInt("BookingID"); 
                    String bookingID        = Integer.toString(booking_id_obj); // convert to string
                    int guest_id_obj        = rs.getInt("GuestID"); 
                    String guestID          = Integer.toString(guest_id_obj); // convert to string
                    int room_number_obj     = rs.getInt("RoomNumber"); 
                    String room_number      = Integer.toString(room_number_obj); // convert to string
                    Collections.addAll(tuple, first_name, last_name, check_in_date, check_out_date, room_type, bookingID, guestID, room_number);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return tuple;
    }

    public ArrayList<ArrayList<String>> getReservationsBeyondDate(LocalDate earliest_date) {
        
        ArrayList<ArrayList<String>> table = new ArrayList<>();
        try {
            String query =  "SELECT G.FirstName, G.LastName, B.CheckInDate, B.CheckOutDate, R.RoomType, B.BookingID, G.GuestID, R.RoomNumber " +
                                "FROM Booking B, Guest G, Room R " +
                                "WHERE B.GuestID = G.GuestID AND B.RoomNumber = R.RoomNumber " +
                                "AND B.CheckInDate > ?";

            try (PreparedStatement statement = connection.prepareStatement(query)) {
                Date earliest_date_sql = Date.valueOf(earliest_date);
                statement.setDate(1, earliest_date_sql);
                ResultSet rs = statement.executeQuery();

                while (rs.next()) {
                    ArrayList<String> tuple = new ArrayList<>();

                    String first_name       = rs.getString("FirstName");
                    String last_name        = rs.getString("LastName");

                    Date check_out_obj      = rs.getDate("CheckOutDate");
                    String check_out_date   ; // declare string
                    if (check_out_obj == null) {
                         check_out_date = new Date(0).toString();
                    }
                    else { check_out_date   = check_out_obj.toString(); // convert to string
                         }

                    Date check_in_obj      = rs.getDate("CheckInDate");
                    String check_in_date   ; // declare string
                    if (check_in_obj == null) {
                         check_in_date = new Date(0).toString();
                    }
                    else { check_in_date   = check_in_obj.toString(); // convert to string
                    }

                    String room_type        = rs.getString("RoomType");
                    int booking_id_obj      = rs.getInt("BookingID"); 
                    String bookingID        = Integer.toString(booking_id_obj); // convert to string
                    int guest_id_obj        = rs.getInt("GuestID"); 
                    String guestID          = Integer.toString(guest_id_obj); // convert to string
                    int room_number_obj     = rs.getInt("RoomNumber"); 
                    String room_number      = Integer.toString(room_number_obj); // convert to string
                    
                    Collections.addAll(tuple, first_name, last_name, check_in_date, check_out_date, room_type, bookingID, guestID, room_number);
                    table.add(tuple); // add new tuple to table list
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return table;
    }


    // returns a list of tuples: room number, room type, num of keys, customer name, checkout date
    public ArrayList<ArrayList<String>> getRoomStatus() {
        ArrayList<ArrayList<String>> table = new ArrayList<>();
        try {
            String query = "SELECT R.RoomNumber, R.RoomType, R.RoomPrice, COUNT(K.RoomKeyID) AS KeyCount, G.FirstName, G.LastName, B.CheckOutDate, B.CheckInDate " +
                    "FROM Room R " +
                    "LEFT JOIN Booking B ON R.RoomNumber = B.RoomNumber " +
                    "LEFT JOIN Guest G ON G.GuestID = B.GuestID " +
                    "LEFT JOIN RoomKey K ON R.RoomNumber = K.RoomNumber " +
                    "GROUP BY R.RoomNumber, R.RoomType, R.RoomPrice, G.FirstName, G.LastName, B.CheckOutDate, B.CheckInDate " +
                    "ORDER BY R.RoomNumber ASC;";
            try (Statement statement = connection.createStatement()) {
                ResultSet rs = statement.executeQuery(query);
                while (rs.next()) {
                    ArrayList<String> tuple = new ArrayList<>();
                    int room_number_obj     = rs.getInt("RoomNumber"); 
                    String room_number      = Integer.toString(room_number_obj); // convert to string
                    String room_type        = rs.getString("RoomType");
                    int room_price_obj      = rs.getInt("RoomPrice");
                    String room_price       = Integer.toString(room_price_obj);
                    int key_count_obj       = rs.getInt("KeyCount");
                    String key_count        = Integer.toString(key_count_obj);
                    String first_name       = rs.getString("FirstName");
                    String last_name        = rs.getString("LastName");
                    Date check_out_obj      = rs.getDate("CheckOutDate");
                    String check_out_date   ; // convert to string
                    if (check_out_obj == null) {
                         check_out_date = new Date(0).toString();
                    }
                    else { check_out_date   = check_out_obj.toString(); // convert to string
                         }

                    Date check_in_obj      = rs.getDate("CheckInDate");
                    String check_in_date   ; // convert to string
                    if (check_in_obj == null) {
                         check_in_date = new Date(0).toString();
                    }
                    else { check_in_date   = check_in_obj.toString(); // convert to string
                    }
                    Collections.addAll(tuple, room_number, room_type, room_price, key_count, first_name, last_name, check_out_date, check_in_date);
                    table.add(tuple); // add new tuple to table list
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return table;
    }

        // returns a list of tuples: room number, room type, num of keys, customer name, checkout date
    public ArrayList<String> getRoomStatus(int givenRoomNumber) {
        ArrayList<String> tuple = new ArrayList<>();
        try {
            String query = "SELECT R.RoomNumber, R.RoomType, R.RoomPrice, COUNT(K.RoomKeyID) AS KeyCount, G.FirstName, G.LastName, B.CheckOutDate, B.CheckInDate " +
                    "FROM Room R " +
                    "LEFT JOIN Booking B ON R.RoomNumber = B.RoomNumber " +
                    "LEFT JOIN Guest G ON G.GuestID = B.GuestID " +
                    "LEFT JOIN RoomKey K ON R.RoomNumber = K.RoomNumber " +
                    "GROUP BY R.RoomNumber, R.RoomType, R.RoomPrice, G.FirstName, G.LastName, B.CheckOutDate, B.CheckInDate " +
                    "ORDER BY R.RoomNumber ASC;";
            try (Statement statement = connection.createStatement()) {
                ResultSet rs = statement.executeQuery(query);
                while (rs.next()) {
                    int room_number_obj     = rs.getInt("RoomNumber"); 
                    String room_number      = Integer.toString(room_number_obj); // convert to string
                    String room_type        = rs.getString("RoomType");
                    int room_price_obj      = rs.getInt("RoomPrice");
                    String room_price       = Integer.toString(room_price_obj);
                    int key_count_obj       = rs.getInt("KeyCount");
                    String key_count        = Integer.toString(key_count_obj);
                    String first_name       = rs.getString("FirstName");
                    String last_name        = rs.getString("LastName");
                    Date check_out_obj      = rs.getDate("CheckOutDate");
                    String check_out_date; // convert to string
                    if (check_out_obj == null) {
                         check_out_date = new Date(0).toString();
                    }
                    else { check_out_date   = check_out_obj.toString(); // convert to string
                         }

                    Date check_in_obj      = rs.getDate("CheckInDate");
                    String check_in_date   ; // convert to string
                    if (check_in_obj == null) {
                         check_in_date = new Date(0).toString();
                    }
                    else { check_in_date   = check_in_obj.toString(); // convert to string
                    }
                    Collections.addAll(tuple, room_number, room_type, room_price, key_count, first_name, last_name, check_out_date, check_in_date);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return tuple;
    }

    // Get all room numbers from the database
    public String[] getRoomNumbers() {
        ArrayList<String> roomNumbers = new ArrayList<>();
        try {
            String query = "SELECT RoomNumber FROM Room;";
            try (Statement statement = connection.createStatement()) {
                ResultSet rs = statement.executeQuery(query);
                while (rs.next()) {
                    int roomNumber = rs.getInt("RoomNumber");
                    roomNumbers.add(String.valueOf(roomNumber));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return roomNumbers.toArray(new String[0]);
    }


    // returns a list of tuples: room number, keycount
    public ArrayList<ArrayList<String>> getKeyCountALL() {
        ArrayList<ArrayList<String>> table = new ArrayList<>();
        try {
            String query =  "SELECT RoomNumber, COUNT(RoomKeyID) AS RoomKeyCount FROM RoomKey GROUP BY RoomNumber;";
            try (Statement statement = connection.createStatement()) {
                ResultSet rs = statement.executeQuery(query);
                while (rs.next()) {
                    ArrayList<String> tuple = new ArrayList<>();
                    int room_number_obj = rs.getInt("RoomNumber"); 
                    String room_number = Integer.toString(room_number_obj); // convert to string
                    int key_count_obj = rs.getInt("KeyCount");
                    String key_count = Integer.toString(key_count_obj);
                    Collections.addAll(tuple, room_number, key_count);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return table;
    }
    // returns a single tuple: room number, keycount
    public ArrayList<String> getKeyCountByRoom(int givenRoomNumber) {
        ArrayList<String> tuple = new ArrayList<>();
        try {
            String query =  "SELECT RoomNumber, COUNT(RoomKeyID) AS RoomKeyCount FROM RoomKey WHERE RoomNumber = ? GROUP BY RoomNumber;";
            try (PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setInt(1, givenRoomNumber);
                ResultSet rs = statement.executeQuery();
                while (rs.next()) {
                    int room_number_obj = rs.getInt("RoomNumber"); 
                    String room_number = Integer.toString(room_number_obj); // convert to string
                    int key_count_obj = rs.getInt("KeyCount");
                    String key_count = Integer.toString(key_count_obj);
                    Collections.addAll(tuple, room_number, key_count);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return tuple;
    }





    // Insert a new key into the database
    public void addKey(String roomNumber, String expirationDate) {
        try {
            String query = "INSERT INTO RoomKey (RoomNumber, Expiration) VALUES (?, ?);";
            try (PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setString(1, roomNumber);
                statement.setString(2, expirationDate);
                statement.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Get all keys from the database
    public ArrayList<ArrayList<String>> getKeys() {
        ArrayList<ArrayList<String>> table = new ArrayList<>();
        try {
            String query = "SELECT * FROM RoomKey;";
            try (Statement statement = connection.createStatement()) {
                ResultSet rs = statement.executeQuery(query);
                while (rs.next()) {
                    ArrayList<String> tuple = new ArrayList<>();
                    int room_key_id_obj = rs.getInt("RoomKeyID");
                    String room_key_id = Integer.toString(room_key_id_obj); // convert to string
                    int room_number_obj = rs.getInt("RoomNumber");
                    String room_number = Integer.toString(room_number_obj); // convert to string
                    Date expiration_obj = rs.getDate("Expiration");
                    String expiration = expiration_obj.toString(); // convert to string
                    Collections.addAll(tuple, room_key_id, room_number, expiration);
                    table.add(tuple); // add new tuple to table list
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return table;
    }

    // Delete a key from the database
    public void deleteKey(String roomKeyID) {
        try {
            String query = "DELETE FROM RoomKey WHERE RoomKeyID = ?;";
            try (PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setString(1, roomKeyID);
                statement.executeUpdate();
                System.out.println("Deleted key with ID: " + roomKeyID);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }




    // Add a new customer to the database
    public void addCustomer(String firstName, String lastName, String email, String phone, String address, String city, String state, String zip) {
        try {
            String query = "INSERT INTO Guest (FirstName, LastName, Email, Phone, Addr, City, St, Zip) VALUES (?, ?, ?, ?, ?, ?, ?, ?);";
            try (PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setString(1, firstName);
                statement.setString(2, lastName);
                statement.setString(3, email);
                statement.setString(4, phone);
                statement.setString(5, address);
                statement.setString(6, city);
                statement.setString(7, state);
                statement.setString(8, zip);
                statement.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Delete a customer from the database
    public void deleteCustomer(String s) {
        try {
            String query = "DELETE FROM Guest WHERE GuestID = ?;";
            try (PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setString(1, s);
                statement.executeUpdate();
                System.out.println("Deleted customer with ID: " + s);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public void addReservation(String firstName, String lastName, Date checkInDate, Date checkOutDate, String roomType) {
        try {
            // Assuming you have tables Guest and Room in your database
            // Adjust the table and column names as per your database schema
            String guestQuery = "INSERT INTO Guest (FirstName, LastName) VALUES (?, ?)";
            String bookingQuery = "INSERT INTO Booking (GuestID, CheckInDate, CheckOutDate, RoomNumber) VALUES ((SELECT GuestID FROM Guest WHERE FirstName = ? AND LastName = ?), ?, ?, (SELECT RoomNumber FROM Room WHERE RoomType = ? LIMIT 1))";

            // Insert guest details first
            try (PreparedStatement guestStatement = connection.prepareStatement(guestQuery)) {
                guestStatement.setString(1, firstName);
                guestStatement.setString(2, lastName);
                guestStatement.executeUpdate();
            }

            // Insert reservation details
            try (PreparedStatement bookingStatement = connection.prepareStatement(bookingQuery)) {
                bookingStatement.setString(1, firstName);
                bookingStatement.setString(2, lastName);
                bookingStatement.setDate(3, checkInDate);
                bookingStatement.setDate(4, checkOutDate);
                bookingStatement.setString(5, roomType);
                bookingStatement.executeUpdate();
            }
            System.out.println("Reservation added successfully.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}