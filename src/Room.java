public class Room {
    private int roomNumber;
    private String roomType;
    private double roomPrice;
    
    

    public Room(int roomNumber, String roomType, double roomPrice) {
        this.roomNumber = roomNumber;
        this.roomType = roomType;
        this.roomPrice = roomPrice;
    }

    public Room() {
        
    }

    public int getRoomNumber() {
        return roomNumber;
    }

    public void setRoomNumber(int roomNumber) {
        this.roomNumber = roomNumber;
    }

    public String getRoomType() {
        return roomType;
    }

    public void setRoomType(String roomType) {
        this.roomType = roomType;
    }

    public double getRoomPrice() {
        return roomPrice;
    }

    public void setRoomPrice(double roomPrice) {
        this.roomPrice = roomPrice;
    }

    // Constructor, getters, and setters here
}
