import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Date;
import java.time.LocalDate;

import java.util.ArrayList;

public class BillCustomerPanel extends Panel {
    private final Choice customerChoice;
    private SQLQueries sqlConnection;

    public BillCustomerPanel(SQLQueries sqlConnection) {
        this.sqlConnection = sqlConnection;
        // Set the layout manager to GridBagLayout
        setLayout(new GridBagLayout());

        // Add a title label
        JLabel titleLabel = new JLabel("Billings");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18)); // Set font and style
        GridBagConstraints titleConstraints = new GridBagConstraints();
        titleConstraints.gridx = 0;
        titleConstraints.gridy = 0;
        titleConstraints.insets = new Insets(10, 0, 20, 0); // Padding: top, left, bottom, right
        add(titleLabel, titleConstraints);

        // Create a panel for the "Select Customer" components
        Panel customerPanel = new Panel(new GridLayout(1, 2));
        Label customerLabel = new Label("Select Customer:");
        customerChoice = createCustomerChoice();
        customerPanel.add(customerLabel);
        customerPanel.add(customerChoice);

        // Create a panel for the "Select Reservation" components

        // Create GridBagConstraints for the customerPanel
        GridBagConstraints customerPanelConstraints = new GridBagConstraints();
        customerPanelConstraints.gridx = 0;
        customerPanelConstraints.gridy = 1;
        customerPanelConstraints.insets = new Insets(0, 0, 10, 0); // Padding: top, left, bottom, right
        add(customerPanel, customerPanelConstraints);


        JButton generateBillButton = new JButton("Generate Bill");
        generateBillButton.addActionListener(new GenerateBillButtonListener());

        // Customize the button appearance
        generateBillButton.setForeground(Color.WHITE);
        generateBillButton.setBackground(new Color(41, 128, 185)); // Adjust color as needed
        generateBillButton.setFont(new Font("Arial", Font.PLAIN, 14));
        generateBillButton.setBorder(BorderFactory.createEmptyBorder(8, 15, 8, 15)); // Add padding

        // Create GridBagConstraints for the "Generate Bill" button
        GridBagConstraints buttonConstraints = new GridBagConstraints();
        buttonConstraints.gridx = 0;
        buttonConstraints.gridy = 3;
        buttonConstraints.weightx = 0.67; // 2/3 of the horizontal space
        buttonConstraints.insets = new Insets(10, 0, 10, 0); // Padding: top, left, bottom, right
        add(generateBillButton, buttonConstraints);
    }

    private Choice createCustomerChoice() {
        Choice choice = new Choice();
        // Add dummy customer names for demonstration

        ArrayList<ArrayList<String>> customers_info = sqlConnection.getCustomersAll();

        for (int i=0; i<customers_info.size(); i++){
            ArrayList<String> customer = customers_info.get(i);
//            choice.add(customer.get(6));
            // Get the customer name
            String customerName = customer.get(1) + " " + customer.get(2);
            choice.add(customerName);
        }
        // choice.add("John Doe");
        // choice.add("Jane Smith");
        // choice.add("Bob Johnson");
        // Add more customers as needed
        return choice;
    }

    private Choice createReservationChoice() {
        Choice choice = new Choice();
        // Add dummy reservation numbers for demonstration
        choice.add("12345");
        choice.add("67890");
        choice.add("54321");
        // Add more reservation numbers as needed
        return choice;
    }

    private class GenerateBillButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            int total_bill = 0;
            String selectedCustomer = customerChoice.getSelectedItem();

            LocalDate today = LocalDate.now();
            // Get the selected customer name
            String selectedCustomerName = customerChoice.getSelectedItem();
            // Find the customer ID that corresponds to the selected name
            ArrayList<ArrayList<String>> customers_info = sqlConnection.getCustomersAll();
            for (int i = 0; i < customers_info.size(); i++) {
                ArrayList<String> customer = customers_info.get(i);
                String customerName = customer.get(1) + " " + customer.get(2);
                if (customerName.equals(selectedCustomerName)) {
                    selectedCustomer = customer.get(0);
                    break;
                }
            }
            ArrayList<ArrayList<String>> reservationsForBilling = sqlConnection.getReservationByCustomer_Billing(Integer.parseInt(selectedCustomer), Date.valueOf(today));

            for (int i = 0; i < reservationsForBilling.size(); i++) {
                ArrayList<String> curr_reservation = reservationsForBilling.get(i);

                LocalDate checkinDate = LocalDate.parse(curr_reservation.get(2));
                LocalDate checkOutDate = LocalDate.parse(curr_reservation.get(3));

                int days_stayed = checkinDate.until(checkOutDate).getDays();
                int amount_to_pay = Integer.parseInt(curr_reservation.get(8)) * days_stayed;

                //get room price
                total_bill += amount_to_pay;

            }
            try {

                String fullName = selectedCustomerName;


                // Here, you can perform any additional logic to generate the bill
                // For simplicity, let's just display the information for now
                showMessageDialog("Generating bill for Customer: " + fullName
                        + "\nTotal Bill: " + total_bill);
            } catch (Exception ex) {
                showMessageDialog("No reservations for this customer");
            }
        }
    }

    private void showMessageDialog(String message) {
        JOptionPane.showMessageDialog(this, message, "Message", JOptionPane.INFORMATION_MESSAGE);
    }

    // public static void main(String[] args) {
    //     Frame frame = new Frame("Bill Customer Panel");
    //     frame.add(new BillCustomerPanel());
    //     frame.setSize(300, 250);
    //     frame.setVisible(true);
    // }
}
