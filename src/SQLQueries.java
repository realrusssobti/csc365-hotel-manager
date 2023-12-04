import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.sql.Date;
import java.util.*;

/* Finished queries for
 * getting all reservations & getting a reservation by start/end dates
 * getting room info for room status
 * get key count for all rooms & get key count for specific room number
 */

public class SQLQueries {
    private static Connection connection;

    public SQLQueries() {
        try{
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/your_database_name", "root", "your_password");
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



    // returns a list of tuples: first name, last name, check in date, check out date, room type
    public ArrayList<ArrayList<String>> getReservationAll() {
        ArrayList<ArrayList<String>> table = new ArrayList<>();
        try {
            String query =  "SELECT G.FirstName, G.LastName, B.CheckInDate, B.CheckOutDate, R.RoomType " +
                                "FROM Bookings B, Guest G, Room R " +
                                "WHERE B.GuestID = G.GuestID AND B.RoomID = R.RoomID;";
            try (Statement statement = connection.createStatement()) {
                ResultSet rs = statement.executeQuery(query);
                while (rs.next()) {
                    ArrayList<String> tuple = new ArrayList<>();
                    String first_name = rs.getString("FirstName");
                    String last_name = rs.getString("LastName");
                    Date check_in_obj = rs.getDate("CheckInDate");
                    String check_in_date = check_in_obj.toString(); // convert to string
                    Date check_out_obj = rs.getDate("CheckOutDate");
                    String check_out_date = check_out_obj.toString(); // convert to string
                    String room_type = rs.getString("RoomType");
                    Collections.addAll(tuple, first_name, last_name, check_in_date, check_out_date, room_type);
                    table.add(tuple); // add new tuple to table list
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
            String query =  "SELECT G.FirstName, G.LastName, B.CheckInDate, B.CheckOutDate, R.RoomType " +
                                "FROM Booking B, Guest G, Room R " +
                                "WHERE B.GuestID = G.GuestID AND B.RoomID = R.RoomID " +
                                "AND B.CheckInDate = ? AND B.CheckOutDate = ?;";
            try (PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setDate(1, givenCheckIn);
                statement.setDate(2, givenCheckOut);
                ResultSet rs = statement.executeQuery();
                while (rs.next()) {
                    String first_name = rs.getString("FirstName");
                    String last_name = rs.getString("LastName");
                    Date check_in_obj = rs.getDate("CheckInDate");
                    String check_in_date = check_in_obj.toString(); // convert to string
                    Date check_out_obj = rs.getDate("CheckOutDate");
                    String check_out_date = check_out_obj.toString(); // convert to string
                    String room_type = rs.getString("RoomType");
                    Collections.addAll(tuple, first_name, last_name, check_in_date, check_out_date, room_type);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return tuple;
    }


    // returns a list of tuples: room number, room type, num of keys, customer name, checkout date
    public ArrayList<ArrayList<String>> getRoomStatus() {
        ArrayList<ArrayList<String>> table = new ArrayList<>();
        try {
            String query =  "SELECT R.RoomNumber, R.RoomType, R.RoomPrice, COUNT(K.RoomKeyID) AS KeyCount, G.FirstName, G.LastName, B.CheckOutDate " +
                                "FROM Booking B, Guest G, Room R, RoomKey K " +
                                "WHERE R.RoomNumber = B.RoomNumber AND R.RoomNumber = K.RoomNumber AND G.GuestID = B.GuestID " +
                                "GROUP BY K.RoomNumber ORDER BY K.RoomNumber ASC;";
            try (Statement statement = connection.createStatement()) {
                ResultSet rs = statement.executeQuery(query);
                while (rs.next()) {
                    ArrayList<String> tuple = new ArrayList<>();
                    int room_number_obj     = rs.getInt("RoomNumber"); 
                    String room_number      = Integer.toString(room_number_obj); // convert to string
                    String room_type        = rs.getString("RoomType");
                    int room_price_obj      = rs.getInt("RoomType");
                    String room_price       = Integer.toString(room_price_obj);
                    int key_count_obj       = rs.getInt("KeyCount");
                    String key_count        = Integer.toString(key_count_obj);
                    String first_name       = rs.getString("FirstName");
                    String last_name        = rs.getString("LastName");
                    Date check_out_obj      = rs.getDate("CheckOutDate");
                    String check_out_date   = check_out_obj.toString(); // convert to string
                    Collections.addAll(tuple, room_number, room_type, room_price, key_count, first_name, last_name, check_out_date);
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
            String query =  "SELECT R.RoomNumber, R.RoomType, COUNT(K.RoomKeyID) AS KeyCount, G.FirstName, G.LastName, B.CheckOutDate " +
                                "FROM Booking B, Guest G, Room R, RoomKey K " +
                                "WHERE R.RoomNumber = ? AND R.RoomNumber = B.RoomNumber AND R.RoomNumber = K.RoomNumber AND G.GuestID = B.GuestID " +
                                "GROUP BY K.RoomNumber;";
            try (PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setInt(1, givenRoomNumber);
                ResultSet rs = statement.executeQuery();
                while (rs.next()) {
                    int room_number_obj = rs.getInt("RoomNumber"); 
                    String room_number = Integer.toString(room_number_obj); // convert to string
                    String room_type = rs.getString("RoomType");
                    int key_count_obj = rs.getInt("KeyCount");
                    String key_count = Integer.toString(key_count_obj);
                    String first_name = rs.getString("FirstName");
                    String last_name = rs.getString("LastName");
                    Date check_out_obj = rs.getDate("CheckOutDate");
                    String check_out_date = check_out_obj.toString(); // convert to string
                    Collections.addAll(tuple, room_number, room_type, key_count, first_name, last_name, check_out_date);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return tuple;
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
}
