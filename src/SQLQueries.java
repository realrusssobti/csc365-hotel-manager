import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.sql.Date;

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
    public String getReservationAll() {
        try{
            String query =  "SELECT G.FirstName, G.LastName, B.CheckInDate, B.CheckOutDate, R.RoomType " +
                                "FROM Bookings B, Guest G, Room R " +
                                "WHERE B.GuestID = G.GuestID AND B.RoomID = R.RoomID;";
            PreparedStatement statement = connection.prepareStatement(query);
            ResultSet resultSet = statement.executeQuery();

        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return 
            "SELECT G.FirstName, G.LastName, B.CheckInDate, B.CheckOutDate, R.RoomType " +
            "FROM Bookings B, Guest G, Room R " +
            "WHERE B.GuestID = G.GuestID AND B.RoomID = R.RoomID;";
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
