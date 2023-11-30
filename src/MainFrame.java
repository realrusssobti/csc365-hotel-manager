import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class MainFrame extends Frame {

    private CardLayout cardLayout;
    private Panel cardPanel;

    public MainFrame() {
        // Set layout manager for the main frame
        setLayout(new BorderLayout());

        // Create buttons
        Button button1 = new Button("Open Frame 1");
        Button button2 = new Button("Open Frame 2");

        // Add button action listeners
        button1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(cardPanel, "Frame 1");
            }
        });

        button2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(cardPanel, "Frame 2");
            }
        });

        // Create panels for each "frame"
        AnalyticsScreen frame1Panel = new AnalyticsScreen();
        mainDashboard frame2Panel = new mainDashboard();

        // Create card layout and panel
        cardLayout = new CardLayout();
        cardPanel = new Panel(cardLayout);

        // Add panels to the cardPanel
        cardPanel.add(frame1Panel, "Frame 1");
        cardPanel.add(frame2Panel, "Frame 2");

        // Add buttons to the main frame using FlowLayout
        Panel buttonPanel = new Panel(new FlowLayout());
        buttonPanel.add(button1);
        buttonPanel.add(button2);

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
