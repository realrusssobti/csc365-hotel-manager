import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class BillCustomerPanel extends Panel {
    private final Choice customerChoice;
    private final Choice reservationChoice;

    public BillCustomerPanel() {
        setLayout(new GridLayout(4, 2));

        Label customerLabel = new Label("Select Customer:");
        customerChoice = createCustomerChoice();

        Label reservationLabel = new Label("Select Reservation:");
        reservationChoice = createReservationChoice();

        Button generateBillButton = new Button("Generate Bill");
        generateBillButton.addActionListener(new GenerateBillButtonListener());

        add(customerLabel);
        add(customerChoice);

        add(reservationLabel);
        add(reservationChoice);

        add(new Label()); // Empty label for spacing
        add(new Label());

        add(generateBillButton);
    }

    private Choice createCustomerChoice() {
        Choice choice = new Choice();
        // Add dummy customer names for demonstration
        choice.add("John Doe");
        choice.add("Jane Smith");
        choice.add("Bob Johnson");
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
            String selectedCustomer = customerChoice.getSelectedItem();
            String selectedReservation = reservationChoice.getSelectedItem();

            // Here, you can perform any additional logic to generate the bill
            // For simplicity, let's just display the information for now
            showMessageDialog("Generating bill for Customer: " + selectedCustomer
                    + "\nReservation Number: " + selectedReservation
                    + "\nBill Generated!");
        }
    }

    private void showMessageDialog(String message) {
        Dialog dialog = new Dialog((Frame) getParent(), "Message", true);
        dialog.setLayout(new FlowLayout());
        dialog.add(new Label(message));
        Button okButton = new Button("OK");
        okButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dialog.dispose();
            }
        });
        dialog.add(okButton);
        dialog.setSize(200, 100);
        dialog.setLocationRelativeTo((Frame) getParent());
        dialog.setVisible(true);
    }

    public static void main(String[] args) {
        Frame frame = new Frame("Bill Customer Panel");
        frame.add(new BillCustomerPanel());
        frame.setSize(300, 200);
        frame.setVisible(true);
    }
}
