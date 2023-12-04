-- Hotel Database Project --
CREATE TABLE Guest (
                            GuestID INT NOT NULL AUTO_INCREMENT,
                            FirstName VARCHAR(50) NOT NULL,
                            LastName VARCHAR(50) NOT NULL,
                            Phone VARCHAR(50) NOT NULL,
                            Addr VARCHAR(50) NOT NULL,
                            City VARCHAR(50) NOT NULL,
                            St VARCHAR(50) NOT NULL,
                            Zip VARCHAR(50) NOT NULL,
                            PRIMARY KEY (GuestID)
);

CREATE TABLE Room (
                            RoomNumber INT NOT NULL,
                            RoomType VARCHAR(50) NOT NULL,
                            RoomPrice DECIMAL(10,2) NOT NULL,
                            PRIMARY KEY (RoomNumber)
);

CREATE TABLE RoomKey (
                            RoomKeyID INT NOT NULL AUTO_INCREMENT,
                            RoomNumber INT NOT NULL,
                            Expiration DATE NOT NULL,
                            PRIMARY KEY (KeyID),
                            FOREIGN KEY (RoomNumber) REFERENCES Room(RoomNumber)
);

CREATE TABLE Booking (
                            BookingID INT NOT NULL AUTO_INCREMENT,
                            GuestID INT NOT NULL,
                            RoomNumber INT NOT NULL,
                            CheckInDate DATE NOT NULL,
                            CheckOutDate DATE NOT NULL,
                            CheckedIn BOOLEAN DEFAULT false,
                            PRIMARY KEY (BookingID),
                            FOREIGN KEY (GuestID) REFERENCES Guest(GuestID),
                            FOREIGN KEY (RoomNumber) REFERENCES Room(RoomNumber)
);

CREATE TABLE Receipt (
                            ReceiptID INT NOT NULL AUTO_INCREMENT,
                            GuestID INT NOT NULL,
                            BookingID INT NOT NULL,
                            ReceiptDate DATE NOT NULL,
                            TotalAmount DECIMAL(10,2) NOT NULL,
                            PRIMARY KEY (ReceiptID),
                            FOREIGN KEY (GuestID) REFERENCES Guest(GuestID),
                            FOREIGN KEY (BookingID) REFERENCES Booking(BookingID)
);