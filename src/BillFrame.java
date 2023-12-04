import javax.swing.*;
import java.awt.*;

public class BillFrame extends JFrame {

    public BillFrame(String selectedCustomer, String selectedReservation) {
        setTitle("Bill for " + selectedCustomer + " (Reservation " + selectedReservation + ")");
        setSize(500, 500);
        setLayout(new FlowLayout());

        // Add your image in a JLabel
        ImageIcon hotelLogo = new ImageIcon("images/IMG_1486.jpg");
        Image image = hotelLogo.getImage();
        Image newimg = image.getScaledInstance(400, 400, java.awt.Image.SCALE_SMOOTH);
        hotelLogo = new ImageIcon(newimg);
        JLabel logoLabel = new JLabel(hotelLogo);
        add(logoLabel);

        // Add labels with bill information
        add(new JLabel("This is the bill for " + selectedCustomer + " (Reservation " + selectedReservation + ")"));
        add(new JLabel("Bill contents go here..."));

        // Set default close operation and make the frame visible
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            String selectedCustomer = "John Doe";
            String selectedReservation = "123";
            new BillFrame(selectedCustomer, selectedReservation);
        });
    }
}
