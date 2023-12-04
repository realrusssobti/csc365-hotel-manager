public class SQLQueries {

    public SQLQueries() {
    }

    // first name, last name, check in date, check out date, room type
    public String getReservationAll() {
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
            "WHERE R.RoomNumber = B.RoomNumber AND R.RoomNumber = K.RoomNumber AND G.GuestID = B.GuestID" +
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
