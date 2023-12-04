import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.Connection;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class MainFrame extends Frame {

    private CardLayout cardLayout;
    private Panel cardPanel;
    private static SQLQueries sqlConnection = new SQLQueries();

    public MainFrame() {
        // Set layout manager for the main frame
        setLayout(new BorderLayout());

        // Create buttons
        Button roomStatusButton = new Button("Room Status");
        Button reservationButton = new Button("Reservation Screen");
        Button roomKeyButton = new Button("Room Key Screen");
        Button billingButton = new Button("Billing Screen");
        Button checkInOutButton = new Button("Check In/Out Screen");
        Button customerManagerButton = new Button("Manage Customers");

        // Add button action listeners

        customerManagerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(cardPanel, "CustomerManager");
            }
        });
        roomStatusButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Room Status button clicked!");
                cardLayout.show(cardPanel, "RoomStatus");
                ;
            }
        });
        reservationButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Reservation button clicked!");
                cardLayout.show(cardPanel, "Reservation");
                ;
            }
        });
        roomKeyButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Room Key button clicked!");
                cardLayout.show(cardPanel, "RoomKey");
                ;
            }
        });
        checkInOutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Check IN/OUT button clicked!");
                cardLayout.show(cardPanel, "CheckInOut");
                ;
            }
        });

        billingButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Billing button clicked!");
                cardLayout.show(cardPanel, "BillCustomer");
                ;
            }
        });

        // Create panels for each "frame"
        /* TODO: Replace this with a getData function and query */
        Room[] roomsList = {new Room(101, "Single", 100.0),
        new Room(102, "Double", 150.0),
        new Room(103, "Suite", 200.0)};
        ArrayList<Room> rooms = new ArrayList<>();
        for (Room room : roomsList) {
            rooms.add(room);
        }
        ArrayList<ArrayList<String>> rooms_info = new ArrayList<ArrayList<String>>();

        // //TODO: Uncomment these and delete prev paragraph to get room info from database when sqlQueries can make the connection
        // SQLQueries query = new SQLQueries();
        // ArrayList<ArrayList<String>> rooms_info = query.getRoomStatus();
        // rooms = parseRoomObjects(rooms_info);

        Reservation[] reservationsList = {new Reservation(1, 1, 101, LocalDate.now(), LocalDate.now().plusDays(1)),
                new Reservation(2, 2, 102, LocalDate.now(), LocalDate.now().plusDays(1))};
        ArrayList<Reservation> reservations = new ArrayList<>();
        for (Reservation reservation : reservationsList) {
            reservations.add(reservation);
        }

        // //TODO: Uncomment these and delete prev paragraph to get room info from database when sqlQueries can make the connection
        // ArrayList<ArrayList<String>> reservations_info = query.getReservationAll();
        // reservations = parseReservationObjects(reservations_info);

        AnalyticsScreen analyticsPanel = new AnalyticsScreen();
        RoomPanel roomStatusPanel = new RoomPanel(rooms, reservations, sqlConnection);
        ReservationPanel reservationPanel = new ReservationPanel();
        ReservationPanel.addDummyData(reservationPanel);
        RoomKeyPanel roomKeyPanel = new RoomKeyPanel();
        CheckInOutPanel checkInOutPanel = new CheckInOutPanel();
        BillCustomerPanel billCustomerPanel = new BillCustomerPanel();
        CustomerManager customerManagerPanel = new CustomerManager();
        HotelSplashScreen hotelSplashScreen = new HotelSplashScreen();


        // Create card layout and panel
        cardLayout = new CardLayout();
        cardPanel = new Panel(cardLayout);

        // Add panels to the cardPanel
        cardPanel.add(hotelSplashScreen, "SplashScreen");
        cardPanel.add(roomStatusPanel, "RoomStatus");

        cardPanel.add(analyticsPanel, "Analytics");
        cardPanel.add(reservationPanel, "Reservation");
        cardPanel.add(roomKeyPanel, "RoomKey");
        cardPanel.add(checkInOutPanel, "CheckInOut");
        cardPanel.add(billCustomerPanel, "BillCustomer");
        cardPanel.add(customerManagerPanel, "CustomerManager");


        // Add buttons to the main frame using FlowLayout
        Panel buttonPanel = new Panel(new FlowLayout());
        buttonPanel.add(roomStatusButton);
        buttonPanel.add(reservationButton);
        buttonPanel.add(roomKeyButton);
        buttonPanel.add(billingButton);
        buttonPanel.add(checkInOutButton);
        buttonPanel.add(customerManagerButton);
        // Add components to the main frame
        add(buttonPanel, BorderLayout.NORTH);
        add(cardPanel, BorderLayout.CENTER);

        // Set main frame properties
        setTitle("Main Frame");
        setSize(1600, 900);
        setVisible(true);
        setResizable(true);
        // listen and close the frame
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                System.exit(0);
                sqlConnection.endConnection(); //end connection when main window gets closed
            }
        });

    }


    //goes through the list of strings of room info returned from sqlQueries and makes an arraylist of rooms from it
    private static ArrayList<Room> parseRoomObjects (ArrayList<ArrayList<String>> sqlData){
        ArrayList<Room> rooms = new ArrayList<>();

        for (int i=0; i<sqlData.size(); i++){
            ArrayList<String> room_info = sqlData.get(i);
            int room_num = Integer.parseInt(room_info.get(0));
            String room_type = room_info.get(1);
            double room_price = Integer.parseInt(room_info.get(2));

            Room new_room = new Room(room_num, room_type, room_price);
            rooms.add(new_room);
        }

        return rooms;
    }

    private static ArrayList<Reservation> parseReservationObjects (ArrayList<ArrayList<String>> sqlData){
        ArrayList<Reservation> reservations = new ArrayList<>();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        for (int i=0; i<sqlData.size(); i++){
            ArrayList<String> reservation_info = sqlData.get(i);

            int bookingID = Integer.parseInt(reservation_info.get(5));
            int guestID = Integer.parseInt(reservation_info.get(6));
            int room_number = Integer.parseInt(reservation_info.get(7));
            LocalDate checkin_date = LocalDate.parse(reservation_info.get(2), formatter);
            LocalDate checkout_date = LocalDate.parse(reservation_info.get(3), formatter);

            Reservation new_reservation = new Reservation(bookingID, guestID, room_number, checkin_date, checkout_date);
            reservations.add(new_reservation);
        }


        return reservations;
    }

    private static void showPopup(Component parentComponent, Component popupComponent) {
        // Create a JDialog as the pop-up
        JDialog dialog = new JDialog((Frame) SwingUtilities.getWindowAncestor(parentComponent), "Popup", true);

        // Create your panel (replace with your own panel class)
        JPanel popupPanel = new JPanel();
        popupPanel.add(popupComponent);

        // Add the panel to the dialog
        dialog.getContentPane().add(popupPanel);

        // Set the size and show the dialog
        dialog.setSize(200, 150);
        dialog.setLocationRelativeTo(parentComponent);
        dialog.setVisible(true);
    }


    public static void main(String[] args) {
        EventQueue.invokeLater(MainFrame::new);
    }
}
