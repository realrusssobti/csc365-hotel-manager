import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.time.LocalDate;
import java.util.ArrayList;

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
        Button customerManagerButton = new Button("Manage Customers");

        // Add button action listeners
        analyticsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(cardPanel, "Analytics");
            }
        });
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
                Room[] roomsList = {new Room(1, 101, "Single", 100.0),
                new Room(2, 102, "Double", 150.0),
                new Room(3, 103, "Suite", 200.0)};

        Reservation[] reservationsList = {new Reservation(1, 1, 101, LocalDate.now(), LocalDate.now().plusDays(1)),
                new Reservation(2, 2, 102, LocalDate.now(), LocalDate.now().plusDays(1))};
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
        ReservationPanel reservationPanel = new ReservationPanel();
        ReservationPanel.addDummyData(reservationPanel);
        RoomKeyPanel roomKeyPanel = new RoomKeyPanel();
        CheckInOutPanel checkInOutPanel = new CheckInOutPanel();
        BillCustomerPanel billCustomerPanel = new BillCustomerPanel();
        CustomerManager customerManagerPanel = new CustomerManager();


        // Create card layout and panel
        cardLayout = new CardLayout();
        cardPanel = new Panel(cardLayout);

        // Add panels to the cardPanel
        cardPanel.add(roomStatusPanel, "RoomStatus");

        cardPanel.add(analyticsPanel, "Analytics");
        cardPanel.add(reservationPanel, "Reservation");
        cardPanel.add(roomKeyPanel, "RoomKey");
        cardPanel.add(checkInOutPanel, "CheckInOut");
        cardPanel.add(billCustomerPanel, "BillCustomer");
        cardPanel.add(customerManagerPanel, "CustomerManager");


        // Add buttons to the main frame using FlowLayout
        Panel buttonPanel = new Panel(new FlowLayout());
        buttonPanel.add(analyticsButton);
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
            }
        });

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
        // set up flatlaf look and feel if not on mac
        try {
            if(!System.getProperty("os.name").toLowerCase().contains("mac"))  UIManager.setLookAndFeel(new com.formdev.flatlaf.FlatLightLaf());
        } catch (Exception e) {
            System.err.println("Failed to initialize LaF");
        }

        EventQueue.invokeLater(MainFrame::new);
    }
}
