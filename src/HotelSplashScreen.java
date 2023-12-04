import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class HotelSplashScreen extends JPanel {

    public HotelSplashScreen() {
        initializeComponents();
    }

    private void initializeComponents() {
        setLayout(new BorderLayout());
//
//        ImageIcon hotelLogo = new ImageIcon("images/vector-hotel-icon.jpg");
//        JLabel logoLabel = new JLabel(hotelLogo);
//        add(logoLabel, BorderLayout.CENTER);

        // render an image in the center of the screen, full image no cropping
        ImageIcon hotelLogo = new ImageIcon("images/vector-hotel-icon.jpg");
        Image image = hotelLogo.getImage(); // transform it
        Image newimg = image.getScaledInstance(400, 400,  java.awt.Image.SCALE_SMOOTH); // scale it the smooth way

        hotelLogo = new ImageIcon(newimg);  // transform it back
        JLabel logoLabel = new JLabel(hotelLogo);
        add(logoLabel, BorderLayout.CENTER);

        // Add hotel name label
        JLabel hotelNameLabel = new JLabel("Welcome to Your Hotel Name");
        hotelNameLabel.setFont(new Font("Arial", Font.BOLD, 20));
        hotelNameLabel.setHorizontalAlignment(SwingConstants.CENTER);
        add(hotelNameLabel, BorderLayout.SOUTH);

    }

    private void closeSplashScreen() {
        // Perform actions to close or hide the splash screen
        // For example, you can remove the splash screen from the JFrame
        JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(this);
        frame.getContentPane().remove(this);
        frame.pack();
        frame.setLocationRelativeTo(null); // Center the main frame
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            // Create a JFrame and set its properties
            JFrame frame = new JFrame("Hotel Splash Screen");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(1600, 900);
            frame.setLayout(new BorderLayout());

            // Create the splash screen panel
            HotelSplashScreen splashScreen = new HotelSplashScreen();

            // Add the splash screen panel to the JFrame
            frame.add(splashScreen);

            // Center the JFrame on the screen
            frame.setLocationRelativeTo(null);

            // Make the JFrame visible
            frame.setVisible(true);
        });
    }
}
