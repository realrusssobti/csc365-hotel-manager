import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.sql.Date;
import java.util.*;

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
    private void endConnection() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
                System.out.println("Database connection closed.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // first name, last name, check in date, check out date, room type
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
    // first name, last name, check in date, check out date, room type
    public String getReservationByDate(java.sql.Date checkIn, java.sql.Date checkOut) {
        return 
            "SELECT G.FirstName, G.LastName, B.CheckInDate, B.CheckOutDate, R.RoomType " +
            "FROM Booking B, Guest G, Room R " +
            "WHERE B.GuestID = G.GuestID AND B.RoomID = R.RoomID " +
            "AND B.CheckInDate = ? AND B.CheckOutDate = ?;";
    }
    // room number, room status, num of keys, customer name, checkout date
    public String getRoomStatus() {
        return 
            "SELECT R.RoomNumber, R.RoomType, COUNT(K.RoomKeyID) AS KeyCount, G.FirstName, G.LastName, B.CheckOutDate " +
            "FROM Booking B, Guest G, Room R, RoomKey K " +
            "WHERE R.RoomNumber = B.RoomNumber AND R.RoomNumber = K.RoomNumber AND G.GuestID = B.GuestID " +
            "GROUP BY K.RoomNumber;";
    }
    // room number, keycount
    public String getKeyCountALL() {
        return 
            "SELECT RoomNumber, COUNT(RoomKeyID) AS RoomKeyCount FROM RoomKey GROUP BY RoomNumber;";
    }
    // room number, keycount
    public String getRoomKeyCountByRoom(int roomNumber) {
        return 
            "SELECT RoomNumber, COUNT(RoomKeyID) AS RoomKeyCount FROM RoomKey WHERE RoomNumber = ? GROUP BY RoomNumber;";
    }
}
