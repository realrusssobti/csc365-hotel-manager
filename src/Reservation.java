import java.time.LocalDate;
import java.util.Date;

public class Reservation {
    private int reservationID;
    private int customerID;
    private int roomNumber;
    private LocalDate checkInDate;
    private LocalDate checkOutDate;
    private boolean checked_in;

    public Reservation(int reservationID, int customerID, int roomNumber, LocalDate checkInDate, LocalDate checkOutDate) {
        this.reservationID = reservationID;
        this.customerID = customerID;
        this.roomNumber = roomNumber;
        this.checkInDate = checkInDate;
        this.checkOutDate = checkOutDate;
        this.checked_in = false;

    }

    public Reservation() {
    }

    public int getReservationID() {
        return reservationID;
    }

    public void setReservationID(int reservationID) {
        this.reservationID = reservationID;
    }

    public int getCustomerID() {
        return customerID;
    }

    public void setCustomerID(int customerID) {
        this.customerID = customerID;
    }

    public int getRoomNumber() {
        return roomNumber;
    }

    public void setRoomNumber(int roomNumber) {
        this.roomNumber = roomNumber;
    }

    public LocalDate getCheckInDate() {
        return checkInDate;
    }

    public void setCheckInDate(LocalDate checkInDate) {
        this.checkInDate = checkInDate;
    }

    public LocalDate getCheckOutDate() {
        return checkOutDate;
    }

    public void setCheckOutDate(LocalDate checkOutDate) {
        this.checkOutDate = checkOutDate;
    }

    public boolean getCheckedIn(){
        return this.checked_in;
    }

    public void setCheckedIn(boolean bool){
        this.checked_in = bool;
    }

    // Constructor, getters, and setters here
}
