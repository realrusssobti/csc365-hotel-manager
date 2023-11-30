-- Hotel Database Project --
CREATE TABLE Guest (
                          CustomerID INT NOT NULL AUTO_INCREMENT,
                          FirstName VARCHAR(50) NOT NULL,
                          LastName VARCHAR(50) NOT NULL,
                          Email VARCHAR(50) NOT NULL,
                          Phone VARCHAR(50) NOT NULL,
                          Address VARCHAR(50) NOT NULL,
                          City VARCHAR(50) NOT NULL,
                          State VARCHAR(50) NOT NULL,
                          Zip VARCHAR(50) NOT NULL,
                          PRIMARY KEY (CustomerID)
);

CREATE TABLE Room (
                      RoomID INT NOT NULL AUTO_INCREMENT,
                      RoomNumber INT NOT NULL,
                      RoomType VARCHAR(50) NOT NULL,
                      RoomPrice DECIMAL(10,2) NOT NULL,
                      PRIMARY KEY (RoomID)
);

CREATE TABLE Reservation (
                             ReservationID INT NOT NULL AUTO_INCREMENT,
                             CustomerID INT NOT NULL,
                             RoomID INT NOT NULL,
                             CheckInDate DATE NOT NULL,
                             CheckOutDate DATE NOT NULL,
                             PRIMARY KEY (ReservationID),
                             FOREIGN KEY (CustomerID) REFERENCES Guest(CustomerID),
                             FOREIGN KEY (RoomID) REFERENCES Room(RoomID)
);

CREATE TABLE User (
                      UserID INT NOT NULL AUTO_INCREMENT,
                      Username VARCHAR(50) NOT NULL,
                      Password VARCHAR(50) NOT NULL,
                      PRIMARY KEY (UserID)
);