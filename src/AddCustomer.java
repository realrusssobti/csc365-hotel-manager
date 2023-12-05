import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AddCustomer extends JFrame {

    private JTextField firstNameField;
    private JTextField lastNameField;
    private JTextField emailField;
    private JTextField phoneField;
    private JTextField addressField;
    private JTextField cityField;
    private JTextField stateField;
    private JTextField zipField;
    private SQLQueries connection;

    public AddCustomer(SQLQueries connection) {
        initializeComponents();
        this.connection = connection;
    }

    private void initializeComponents() {
        setTitle("Add Customer");
        setSize(400, 300);
        setLayout(new GridLayout(9, 2));

        add(new JLabel("First Name:"));
        firstNameField = new JTextField();
        add(firstNameField);

        add(new JLabel("Last Name:"));
        lastNameField = new JTextField();
        add(lastNameField);

        add(new JLabel("Email:"));
        emailField = new JTextField();
        add(emailField);

        add(new JLabel("Phone:"));
        phoneField = new JTextField();
        add(phoneField);

        add(new JLabel("Address:"));
        addressField = new JTextField();
        add(addressField);

        add(new JLabel("City:"));
        cityField = new JTextField();
        add(cityField);

        add(new JLabel("State:"));
        stateField = new JTextField();
        add(stateField);

        add(new JLabel("Zip:"));
        zipField = new JTextField();
        add(zipField);

        JButton addButton = new JButton("Add Customer");
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Get the values entered by the user
                String firstName = firstNameField.getText();
                String lastName = lastNameField.getText();
                String email = emailField.getText();
                String phone = phoneField.getText();
                String address = addressField.getText();
                String city = cityField.getText();
                String state = stateField.getText();
                String zip = zipField.getText();

                // Perform any validation or save to the database logic here
                // For now, let's print the values
                System.out.println("Adding Customer:");
                System.out.println("First Name: " + firstName);
                System.out.println("Last Name: " + lastName);
                System.out.println("Email: " + email);
                System.out.println("Phone: " + phone);
                System.out.println("Address: " + address);
                System.out.println("City: " + city);
                System.out.println("State: " + state);
                System.out.println("Zip: " + zip);

                // Add the customer to the database
                connection.addCustomer(firstName, lastName, email, phone, address, city, state, zip);

                // Close the pop-up frame
                dispose();
            }
        });
        add(addButton);

        // Handle window closing event
        addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                dispose(); // Close the pop-up
            }
        });
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            AddCustomer addCustomer = new AddCustomer(new SQLQueries());
            addCustomer.setLocationRelativeTo(null);
            addCustomer.setVisible(true);
        });
    }
}
