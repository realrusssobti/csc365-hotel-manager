SET @input_date = 'input_date';
SET @start_date = 'start_date';
SET @end_date   = 'end_date';
SET @room_type  = 'room_type';
SET @room_number= 'room_number';
SET @guest_id   = 'guest_id';

--Get count of active room keys for room
SELECT K.RoomID, COUNT(K.KeyID) as KeyCount
        FROM RoomKey K
        WHERE K.RoomNumber = @room_number AND K.Active = true;

--Get count of inactive room keys for room
SELECT K.RoomID, COUNT(K.KeyID) as KeyCount
        FROM RoomKey K
        WHERE K.RoomNumber = @room_number AND K.Active = false;

--Create view of bookings of specific person
CREATE VIEW GuestBookings AS
        SELECT G.FirstName, G.LastName, B.RoomNumber, B.CheckInDate, B.CheckOutDate, B.CheckedIn
        FROM Guest G, Booking B
        WHERE G.GuestID = @guest_id AND B.GuestID = @guest_id;

--Get room information by name and date
SELECT *
        FROM GuestBookings, 
        WHERE  

--Get all previous bookings from before input_date:
CREATE VIEW Bookings AS
        SELECT G.GuestID, G.FirstName, G.LastName, R.RoomID, R.CheckInDate, R.CheckOutDate
        FROM Guest G, Reservation R
        WHERE CheckOutDate < 'input_date'
SELECT * FROM Bookings;
--Need to switch to date input

--Get all rooms of one type:
CREATE VIEW RoomsOfType AS
        SELECT 

--Get all open rooms and the dates they are open:
CREATE VIEW OpenRooms AS
        SELECT *
        FROM Room RO, Reservation RE
        WHERE CheckOutDate < 'input_date'



--Get longest current booking:


--Get most active days of week


--Get most frequently returning customers


--Get upcoming bookings for the next week


--

