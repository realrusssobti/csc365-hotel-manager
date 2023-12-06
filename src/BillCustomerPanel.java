import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.StringTokenizer;

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
        generateBillButton.setForeground(Color.BLACK);
        generateBillButton.setBackground(new Color(41, 128, 185)); // Adjust color as needed
        generateBillButton.setFont(new Font("Arial", Font.PLAIN, 20));
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

        ArrayList<ArrayList<String>> customers_info = SQLQueries.getCustomersWithReservation();

        for (int i=0; i<customers_info.size(); i++){
            ArrayList<String> customer = customers_info.get(i);
            String cust = String.valueOf(customer.get(0)) + " " + customer.get(1);
            choice.add(cust);
//            choice.add(customer.get(6));
            // Get the customer name

//            String customerName = customer.get(1) + " " + customer.get(2);
//            choice.add(customerName);
        }
        return choice;
    }

    private Choice createReservationChoice() {
        Choice choice = new Choice();
        
        ArrayList<ArrayList<String>> reservation_info = sqlConnection.getReservationAll();
        System.out.println("------------------------");
        System.out.println(reservation_info.size());
        for (int i=0; i<reservation_info.size(); i++){
            ArrayList<String> reservation = reservation_info.get(i);
            System.out.println(reservation);
            String res_num = String.valueOf(reservation.get(0)) + " " + reservation.get(1);
            choice.add(res_num);
        }

        return choice;
    }

    private class GenerateBillButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            int total_bill = 0;
            // extract cust id from input cust
            StringTokenizer tokenizer = new StringTokenizer(customerChoice.getSelectedItem());
            int selectedCustomer = Integer.parseInt(tokenizer.nextToken());

            LocalDate today = LocalDate.now();
            // extract id from cust input
            ArrayList<ArrayList<String>> reservationsForBilling = sqlConnection.getReservationByCustomer_Billing(selectedCustomer);

            for (int i = 0; i < reservationsForBilling.size(); i++) {
                ArrayList<String> curr_reservation = reservationsForBilling.get(i);
        
                LocalDate checkinDate = LocalDate.parse(curr_reservation.get(2));
                LocalDate checkOutDate = LocalDate.parse(curr_reservation.get(3));

                int days_stayed = checkinDate.until(checkOutDate).getDays();
                int amount_to_pay = Integer.parseInt(curr_reservation.get(8)) * days_stayed;

                //get room price
                total_bill += amount_to_pay;
            }
            System.out.println("making the bill");
            if (reservationsForBilling.size() > 0) {
                String fullName = reservationsForBilling.get(0).get(0) + " " + reservationsForBilling.get(0).get(1);
                showMessage("Generating bill for Customer: " + fullName 
                    + "\nTotal Bill: " + String.valueOf(total_bill));
            } else {            
                showMessage("No reservations to pay for . . .");
            }
    
        }
    }

    private void showMessage(String message) {
        JOptionPane.showMessageDialog(this, message, "Message", JOptionPane.INFORMATION_MESSAGE);
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Bill Customer Panel");
        frame.add(new BillCustomerPanel(new SQLQueries()));
        frame.setSize(300, 250);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}
