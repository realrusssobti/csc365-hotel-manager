import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;

public class MainFrame extends Frame {

    private CardLayout cardLayout;
    private Panel cardPanel;

    public MainFrame() {
        // Set layout manager for the main frame
        setLayout(new BorderLayout());

        // Create buttons
        Button analyticsButton = new Button("Analytics Screen");
        Button roomStatusButton = new Button("Room Status");

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
                cardLayout.show(cardPanel, "RoomStatus");
            }
        });

        // Create panels for each "frame"
        AnalyticsScreen analyticsPanel = new AnalyticsScreen();
        ArrayList<Room> rooms = new ArrayList<>();
        ArrayList<Reservation> reservations = new ArrayList<>();
        MainDashboard roomStatusPanel = new MainDashboard(rooms, reservations);

        // Create card layout and panel
        cardLayout = new CardLayout();
        cardPanel = new Panel(cardLayout);

        // Add panels to the cardPanel
        cardPanel.add(analyticsPanel, "Analytics");
        cardPanel.add(roomStatusPanel, "RoomStatus");

        // Add buttons to the main frame using FlowLayout
        Panel buttonPanel = new Panel(new FlowLayout());
        buttonPanel.add(analyticsButton);
        buttonPanel.add(roomStatusButton);

        // Add components to the main frame
        add(buttonPanel, BorderLayout.NORTH);
        add(cardPanel, BorderLayout.CENTER);

        // Set main frame properties
        setTitle("Main Frame");
        setSize(400, 300);
        setVisible(true);
        setResizable(false);
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
