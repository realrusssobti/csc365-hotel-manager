import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.Date;

public class MainFrame extends Frame {

    private CardLayout cardLayout;
    private Panel cardPanel;

    public MainFrame() {
        // Set layout manager for the main frame
        setLayout(new BorderLayout());

        // Create buttons
        Button analyticsButton = new Button("Test Shit Here Screen");
        Button roomStatusButton = new Button("Room Status");
        Button reservationButton = new Button("Reservation Screen");
        Button roomKeyButton = new Button("Room Key Screen");
        Button billingButton = new Button("Billing Screen");
        Button checkInOutButton = new Button("Check In/Out Screen");

        // Add button action listeners
        analyticsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(cardPanel, "Analytics");
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

        // Create panels for each "frame"
        /* TODO: Replace this with a getData function and query */
                Room[] roomsList = {new Room(1, 101, "Single", 100.0),
                new Room(2, 102, "Double", 150.0),
                new Room(3, 103, "Suite", 200.0)};

        Reservation[] reservationsList = {new Reservation(1, 1, 101, new Date(), new Date()),
                new Reservation(2, 2, 102, new Date(), new Date())};
        ArrayList<Room> rooms = new ArrayList<>();
        ArrayList<Reservation> reservations = new ArrayList<>();
        for (Room room : roomsList) {
            rooms.add(room);
        }
        for (Reservation reservation : reservationsList) {
            reservations.add(reservation);
        }
        AnalyticsScreen analyticsPanel = new AnalyticsScreen();
        RoomPanel roomStatusPanel = new RoomPanel(rooms, reservations);

        // Create card layout and panel
        cardLayout = new CardLayout();
        cardPanel = new Panel(cardLayout);

        // Add panels to the cardPanel
        cardPanel.add(roomStatusPanel, "RoomStatus");

        cardPanel.add(analyticsPanel, "Analytics");


        // Add buttons to the main frame using FlowLayout
        Panel buttonPanel = new Panel(new FlowLayout());
        buttonPanel.add(analyticsButton);
        buttonPanel.add(roomStatusButton);
        buttonPanel.add(reservationButton);
        buttonPanel.add(roomKeyButton);
        buttonPanel.add(billingButton);
        buttonPanel.add(checkInOutButton);

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
            }
        });

    }


    public static void main(String[] args) {
        EventQueue.invokeLater(MainFrame::new);
    }
}
