public class SQLQueries {
    // ()
    // first name, last name, check in date, check out date, room type
    public String getReservationAll = 
    "SELECT G.FirstName, G.LastName, B.CheckInDate, B.CheckOutDate, R.RoomType " +
    "FROM Bookings B, Guest G, Room R " +
    "WHERE B.GuestID = G.GuestID AND B.RoomID = R.RoomID;";
    // (Start date, End date)
    // first name, last name, check in date, check out date, room type
    public String getReservationByDate = "SELECT G.FirstName, G.LastName, B.CheckInDate, B.CheckOutDate, R.RoomType " +
    "FROM Booking B, Guest G, Room R " +
    "WHERE B.GuestID = G.GuestID AND B.RoomID = R.RoomID " +
    "AND B.CheckInDate = ? AND B.CheckOutDate = ?;";

    // ()
    // room number, room status, num of keys, customer name, checkout date
    public String getRoomStatus = "SELECT R.RoomNumber, R.RoomType, COUNT(K.RoomKeyID) AS KeyCount, G.FirstName, G.LastName, B.CheckOutDate " +
    "FROM Booking B, Guest G, Room R, RoomKey K " +
    "WHERE R" +
    "GROUP BY K.RoomNumber";

    public String getRoomKeyAll = "";
    public String getRoomKeySingle = "";

    public String getRecieptAll = "";
    public String getRecieptRecent = "";

    public SQLQueries() {
        // 
        this.getReservationInfo = "";
        this.getReservationInfo = "";
        this.getReservationInfo = "";
        this.getReservationInfo = "";
        this.getReservationInfo = "";
        this.getReservationInfo = "";

    }

}
