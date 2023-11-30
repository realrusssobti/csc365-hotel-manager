// Database functions for the program: All the queries and database functions will be here
// mysql JDBC
import java.sql.*;
import java.util.ArrayList;

public class DatabaseFunctions {
     private Connection connection;
     public DatabaseFunctions() {
         try{
         connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/your_database_name", "root", "your_password");}
            catch (SQLException e) {
                e.printStackTrace();
            }
     }
    public User login(String username, String password){
        String query = "SELECT * FROM User WHERE username = ? AND password = ?";
        try {
            // create the mysql insert preparedstatement
            PreparedStatement statement = connection.prepareStatement(query);
            ResultSet resultSet = statement.executeQuery();
            if(resultSet.next()){
                User user = new User();
                user.setUserID(resultSet.getInt("userID"));
                user.setUsername(resultSet.getString("username"));
                user.setPassword(resultSet.getString("password"));
                return user;
        }
        } catch (SQLException e) {
            e.printStackTrace();}
        return null;

    }
    public ArrayList<Guest> getGuests(){
         try{
         String query = "SELECT * FROM Guest";
         PreparedStatement statement = connection.prepareStatement(query);
         ResultSet resultSet = statement.executeQuery();
         ArrayList<Guest> guests = new ArrayList<Guest>();
         while(resultSet.next()){
             Guest guest = new Guest();
             guest.setCustomerID(resultSet.getInt("customerID"));
             guest.setFirstName(resultSet.getString("firstName"));
             guest.setLastName(resultSet.getString("lastName"));
             guest.setEmail(resultSet.getString("email"));
             guest.setPhone(resultSet.getString("phone"));
             guest.setAddress(resultSet.getString("address"));
             guest.setCity(resultSet.getString("city"));
             guest.setState(resultSet.getString("state"));
             guest.setZip(resultSet.getString("zip"));
             guests.add(guest);
         }
            return guests;
    }
         catch (SQLException e) {
            e.printStackTrace();
        return null;
    }}

    public Guest getGuest(int customerID){
        try{
            String query = "SELECT * FROM Guest WHERE customerID = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, customerID);
            ResultSet resultSet = statement.executeQuery();
            if(resultSet.next()){
                Guest guest = new Guest();
                guest.setCustomerID(resultSet.getInt("customerID"));
                guest.setFirstName(resultSet.getString("firstName"));
                guest.setLastName(resultSet.getString("lastName"));
                guest.setEmail(resultSet.getString("email"));
                guest.setPhone(resultSet.getString("phone"));
                guest.setAddress(resultSet.getString("address"));
                guest.setCity(resultSet.getString("city"));
                guest.setState(resultSet.getString("state"));
                guest.setZip(resultSet.getString("zip"));
                return guest;
            }
            return null;
        }
        catch (SQLException e) {
            e.printStackTrace();
        return null;}
    }
    public void addGuest(Guest guest){
        try{
            String query = "INSERT INTO Guest (firstName, lastName, email, phone, address, city, state, zip) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
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
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void updateGuest(Guest guest){
        try{
            String query = "UPDATE Guest SET firstName = ?, lastName = ?, email = ?, phone = ?, address = ?, city = ?, state = ?, zip = ? WHERE customerID = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, guest.getFirstName());
            statement.setString(2, guest.getLastName());
            statement.setString(3, guest.getEmail());
            statement.setString(4, guest.getPhone());
            statement.setString(5, guest.getAddress());
            statement.setString(6, guest.getCity());
            statement.setString(7, guest.getState());
            statement.setString(8, guest.getZip());
            statement.setInt(9, guest.getCustomerID());
            statement.executeUpdate();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void deleteGuest(int customerID){
        try{
            String query = "DELETE FROM Guest WHERE customerID = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, customerID);
            statement.executeUpdate();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public ArrayList<Room> getRooms(){
         try{
            String query = "SELECT * FROM Room";
            PreparedStatement statement = connection.prepareStatement(query);
            ResultSet resultSet = statement.executeQuery();
            ArrayList<Room> rooms = new ArrayList<Room>();
            while(resultSet.next()){
                Room room = new Room();
                room.setRoomID(resultSet.getInt("roomID"));
                room.setRoomNumber(resultSet.getInt("roomNumber"));
                room.setRoomType(resultSet.getString("roomType"));
                room.setRoomPrice(resultSet.getDouble("roomPrice"));
                rooms.add(room);
            }
            return rooms;
         }
         catch (SQLException e) {
            e.printStackTrace();
        return null;
    }
 }
    public Room getRoom(int roomID){
        try{
            String query = "SELECT * FROM Room WHERE roomID = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, roomID);
            ResultSet resultSet = statement.executeQuery();
            if(resultSet.next()){
                Room room = new Room();
                room.setRoomID(resultSet.getInt("roomID"));
                room.setRoomNumber(resultSet.getInt("roomNumber"));
                room.setRoomType(resultSet.getString("roomType"));
                room.setRoomPrice(resultSet.getDouble("roomPrice"));
                return room;
            }
            return null;
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    public void addRoom(Room room){
        try{
            String query = "INSERT INTO Room (roomNumber, roomType, roomPrice) VALUES (?, ?, ?)";
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
    public void updateRoom(Room room){
        try{
            String query = "UPDATE Room SET roomNumber = ?, roomType = ?, roomPrice = ? WHERE roomID = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, room.getRoomNumber());
            statement.setString(2, room.getRoomType());
            statement.setDouble(3, room.getRoomPrice());
            statement.setInt(4, room.getRoomID());
            statement.executeUpdate();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void deleteRoom(int roomID){
        try{
            String query = "DELETE FROM Room WHERE roomID = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, roomID);
            statement.executeUpdate();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public ArrayList<Reservation> getReservations(){
        try{
            String query = "SELECT * FROM Reservation";
            PreparedStatement statement = connection.prepareStatement(query);
            ResultSet resultSet = statement.executeQuery();
            ArrayList<Reservation> reservations = new ArrayList<Reservation>();
            while(resultSet.next()){
                Reservation reservation = new Reservation();
                reservation.setReservationID(resultSet.getInt("reservationID"));
                reservation.setCustomerID(resultSet.getInt("customerID"));
                reservation.setRoomID(resultSet.getInt("roomID"));
                reservation.setCheckInDate(resultSet.getDate("checkInDate"));
                reservation.setCheckOutDate(resultSet.getDate("checkOutDate"));
                reservations.add(reservation);
            }
            return reservations;

        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    public Reservation getReservation(int reservationID){
        try{
            String query = "SELECT * FROM Reservation WHERE reservationID = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, reservationID);
            ResultSet resultSet = statement.executeQuery();
            if(resultSet.next()){
                Reservation reservation = new Reservation();
                reservation.setReservationID(resultSet.getInt("reservationID"));
                reservation.setCustomerID(resultSet.getInt("customerID"));
                reservation.setRoomID(resultSet.getInt("roomID"));
                reservation.setCheckInDate(resultSet.getDate("checkInDate"));
                reservation.setCheckOutDate(resultSet.getDate("checkOutDate"));
                return reservation;
            }
            return null;
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    public void addReservation(Reservation reservation){
         try {
             String query = "INSERT INTO Reservation (customerID, roomID, checkInDate, checkOutDate) VALUES (?, ?, ?, ?)";
             PreparedStatement statement = connection.prepareStatement(query);
             statement.setInt(1, reservation.getCustomerID());
             statement.setInt(2, reservation.getRoomID());
             statement.setDate(3, new java.sql.Date(reservation.getCheckInDate().getTime()));
             statement.setDate(4, new java.sql.Date(reservation.getCheckOutDate().getTime()));
             statement.executeUpdate();
         }
         catch (SQLException e) {
             e.printStackTrace();
         }
    }
    public void updateReservation(Reservation reservation){
         try {
                String query = "UPDATE Reservation SET customerID = ?, roomID = ?, checkInDate = ?, checkOutDate = ? WHERE reservationID = ?";
                PreparedStatement statement = connection.prepareStatement(query);
                statement.setInt(1, reservation.getCustomerID());
                statement.setInt(2, reservation.getRoomID());
                statement.setDate(3, new java.sql.Date(reservation.getCheckInDate().getTime()));
                statement.setDate(4, new java.sql.Date(reservation.getCheckOutDate().getTime()));
                statement.setInt(5, reservation.getReservationID());
                statement.executeUpdate();
            }
            catch (SQLException e) {
                e.printStackTrace();
         }
    }
    public void deleteReservation(int reservationID){
            try {
                    String query = "DELETE FROM Reservation WHERE reservationID = ?";
                    PreparedStatement statement = connection.prepareStatement(query);
                    statement.setInt(1, reservationID);
                    statement.executeUpdate();
                }
                catch (SQLException e) {
                    e.printStackTrace();
            }
    }
    public ArrayList<Reservation> getReservationsByGuest(int customerID){
        try{
            String query = "SELECT * FROM Reservation WHERE customerID = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, customerID);
            ResultSet resultSet = statement.executeQuery();
            ArrayList<Reservation> reservations = new ArrayList<Reservation>();
            while(resultSet.next()){
                Reservation reservation = new Reservation();
                reservation.setReservationID(resultSet.getInt("reservationID"));
                reservation.setCustomerID(resultSet.getInt("customerID"));
                reservation.setRoomID(resultSet.getInt("roomID"));
                reservation.setCheckInDate(resultSet.getDate("checkInDate"));
                reservation.setCheckOutDate(resultSet.getDate("checkOutDate"));
                reservations.add(reservation);
            }
            return reservations;
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    public ArrayList<Reservation> getReservationsByRoom(int roomID){
        try{
            String query = "SELECT * FROM Reservation WHERE roomID = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, roomID);
            ResultSet resultSet = statement.executeQuery();
            ArrayList<Reservation> reservations = new ArrayList<Reservation>();
            while(resultSet.next()){
                Reservation reservation = new Reservation();
                reservation.setReservationID(resultSet.getInt("reservationID"));
                reservation.setCustomerID(resultSet.getInt("customerID"));
                reservation.setRoomID(resultSet.getInt("roomID"));
                reservation.setCheckInDate(resultSet.getDate("checkInDate"));
                reservation.setCheckOutDate(resultSet.getDate("checkOutDate"));
                reservations.add(reservation);
            }
            return reservations;
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    public ArrayList<Room> getAvailableRooms(Date checkInDate, Date checkOutDate){
         try {
                String query = "SELECT * FROM Room WHERE roomID NOT IN (SELECT roomID FROM Reservation WHERE checkInDate <= ? AND checkOutDate >= ?)";
                PreparedStatement statement = connection.prepareStatement(query);
                statement.setDate(1, new java.sql.Date(checkInDate.getTime()));
                statement.setDate(2, new java.sql.Date(checkOutDate.getTime()));
                ResultSet resultSet = statement.executeQuery();
                ArrayList<Room> rooms = new ArrayList<Room>();
                while(resultSet.next()){
                    Room room = new Room();
                    room.setRoomID(resultSet.getInt("roomID"));
                    room.setRoomNumber(resultSet.getInt("roomNumber"));
                    room.setRoomType(resultSet.getString("roomType"));
                    room.setRoomPrice(resultSet.getDouble("roomPrice"));
                    rooms.add(room);
                }
                return rooms;
            }
            catch (SQLException e) {
                e.printStackTrace();
         }
        return null;
    }
    public ArrayList<Room> getBookedRooms(Date checkInDate){
        try {
            String query = "SELECT * FROM Room WHERE roomID IN (SELECT roomID FROM Reservation WHERE checkInDate <= ? AND checkOutDate >= ?)";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setDate(1, new java.sql.Date(checkInDate.getTime()));
            statement.setDate(2, new java.sql.Date(checkInDate.getTime()));
            ResultSet resultSet = statement.executeQuery();
            ArrayList<Room> rooms = new ArrayList<Room>();
            while(resultSet.next()){
                Room room = new Room();
                room.setRoomID(resultSet.getInt("roomID"));
                room.setRoomNumber(resultSet.getInt("roomNumber"));
                room.setRoomType(resultSet.getString("roomType"));
                room.setRoomPrice(resultSet.getDouble("roomPrice"));
                rooms.add(room);
            }
            return rooms;
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    public boolean checkin(int reservationID){
         try {
                String query = "UPDATE Reservation SET checkInDate = ? WHERE reservationID = ?";
                PreparedStatement statement = connection.prepareStatement(query);
                statement.setDate(1, new java.sql.Date(new java.util.Date().getTime()));
                statement.setInt(2, reservationID);
                statement.executeUpdate();
                return true;
            }
            catch (SQLException e) {
                e.printStackTrace();
         }
        return false;
    }
    public boolean checkout(int reservationID){
        try {
            String query = "UPDATE Reservation SET checkOutDate = ? WHERE reservationID = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setDate(1, new java.sql.Date(new java.util.Date().getTime()));
            statement.setInt(2, reservationID);
            statement.executeUpdate();
            return true;
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }



}
