SET @input_date = 'input_date';
SET @start_date = 'start_date';
SET @end_date   = 'end_date';
SET @room_type  = 'room_type';
SET @room_num   = 'room_number';
SET @phone      = 'phone';
SET @first_name = 'first_name';
SET @last_name  = 'last_name';


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



--Test Shit Here

--Room status
CREATE VIEW RoomStatus AS

________________________
--All guest reservations
--Room Number, Occupied or not, Num of keys, guest name, leaving date

--Info from all reservations
CREATE VIEW GuestBookings AS
        SELECT B.RoomNumber, B.CheckedIn, B.CheckOutDate, G.FirstName, G.LastName, G.Phone
        FROM Booking B, Guest G
--Get all rooms' active key count
CREATE VIEW RoomKeys AS
        SELECT R.RoomNumber, COUNT(K.RoomKeyID) as KeyCount
        FROM Room R LEFT JOIN RoomKey K 
        ON R.RoomNumber = K.RoomNumber
        GROUP BY R.RoomNumber

--Get view of all reservation info
CREATE VIEW BookingInfo AS
        SELECT *
        FROM GuestBookings G JOIN ActiveRoomKeys A 
        ON G.RoomNumber = A.RoomNumber
--Get view of specific reservation info
CREATE VIEW GuestBookingInfo AS
        SELECT *
        FROM BookingInfo
        WHERE Phone = @phone;
________________________
--Get room key count for a room by room number
CREATE VIEW RoomKeyByRoom AS
        SELECT RoomNumber, COUNT(RoomKeyID) AS RoomKeyCount
        FROM RoomKey
        GROUP BY RoomNumber;
________________________
--Get all reciepts for the guest which matches the given phone number
CREATE VIEW GuestReciepts AS 
        SELECT R.*, G.FirstName, G.LastName, G.Phone
        FROM Reciept R, Guest G
        ON R.GuestID = G.GuestID
        WHERE G.Phone = @phone;
--Select the most recent reciept
SELECT *
        FROM GuestReciepts
        ORDER BY RecieptDate DESC
        LIMIT 1;
________________________
--Check in / Check out

________________________