'ALL OF THE USER INPUTS ARE REPRESENTED BY QUOTES DESCRIBING WHAT THEY SHOULD BE'
'DONT FORGET TO SWITCH THEM TO ?'

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
        WHERE Phone = 'phone';
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
        WHERE G.Phone = 'phone';
--Select the most recent reciept
SELECT *
        FROM GuestReciepts
        ORDER BY RecieptDate DESC
        LIMIT 1;
________________________
--Check in / Check out

________________________



________________________
 -- Gonna put other stuff here thats not just queries -- 
 -- Gonna move it to another file later --

________________________
--Insert new Guest
INSERT IN