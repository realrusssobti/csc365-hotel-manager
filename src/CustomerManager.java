import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Vector;

public class CustomerManager extends JPanel {

    private JTable customerTable;
    private DefaultTableModel tableModel;
    private SQLQueries sqlConnection;

    public CustomerManager(SQLQueries sqlConnection) {
        this.sqlConnection = sqlConnection;
        initializeComponents();
        getData(); // Add mock data for testing
    }

    //emtpy constructor for mainmethod in this file when we first made it and wanted to isolate run it.
    public CustomerManager(){};


    private void initializeComponents() {
        setLayout(new BorderLayout());

        // Create the table model with columns
        Vector<String> columns = new Vector<>();
        columns.add("GuestID");
        columns.add("FirstName");
        columns.add("LastName");
        columns.add("Email");
        columns.add("Phone");
        columns.add("Addr");
        columns.add("City");
        columns.add("St");
        columns.add("Zip");
        columns.add("Actions"); // New column for "Delete Customer" button

        tableModel = new DefaultTableModel(columns, 0);
        customerTable = new JTable(tableModel);

        // Set a custom renderer and editor for the "Actions" column
        customerTable.getColumnModel().getColumn(columns.size() - 1).setCellRenderer(new ButtonRenderer());
        customerTable.getColumnModel().getColumn(columns.size() - 1).setCellEditor(new ButtonEditor());

        // Add the table to a scroll pane
        JScrollPane tableScrollPane = new JScrollPane(customerTable);
        add(tableScrollPane, BorderLayout.CENTER);

        // Create the Add Customer button
        JButton addButton = new JButton("Add Customer");
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Call a method to handle adding a customer
                // You can show a dialog or navigate to another view for adding customer details
                // For simplicity, let's just print a message for now
                System.out.println("Add Customer button clicked");
                // Render a AddCustomer as a popup
                AddCustomer addCustomer = new AddCustomer(sqlConnection);
                addCustomer.setVisible(true);

            }
        });

        // Add the Add Customer button to the panel
        add(addButton, BorderLayout.NORTH);

        // Set preferred column widths
        setColumnWidths();

        // Set preferred size of the panel
        setPreferredSize(new Dimension(800, 600));
    }

    private void setColumnWidths() {
        // Set preferred column widths based on your preferences
        customerTable.getColumnModel().getColumn(0).setPreferredWidth(50); // GuestID
        customerTable.getColumnModel().getColumn(1).setPreferredWidth(100); // FirstName
        customerTable.getColumnModel().getColumn(2).setPreferredWidth(100); // LastName
        customerTable.getColumnModel().getColumn(3).setPreferredWidth(150); // Email
        customerTable.getColumnModel().getColumn(4).setPreferredWidth(100); // Phone
        customerTable.getColumnModel().getColumn(5).setPreferredWidth(150); // Addr
        customerTable.getColumnModel().getColumn(6).setPreferredWidth(100); // City
        customerTable.getColumnModel().getColumn(7).setPreferredWidth(50);  // St
        customerTable.getColumnModel().getColumn(8).setPreferredWidth(50);  // Zip
        customerTable.getColumnModel().getColumn(9).setPreferredWidth(100); // Actions
    }

    private void getData() {

        ArrayList<ArrayList<String>> customers_info = sqlConnection.getCustomersAll();

        for (int i=0; i<customers_info.size(); i++){
            ArrayList<String> curr_customer = customers_info.get(i);
            Vector<Object> new_row = new Vector<>();
            new_row.add(curr_customer.get(0));  // guestID
            new_row.add(curr_customer.get(1));  // First name
            new_row.add(curr_customer.get(2));  // Last name
            new_row.add(curr_customer.get(3));  // email
            new_row.add(curr_customer.get(4));  // phNum
            new_row.add(curr_customer.get(5));  // Address
            new_row.add(curr_customer.get(6));  // City
            new_row.add(curr_customer.get(7));  // State
            new_row.add(curr_customer.get(8));  // Zip

            tableModel.addRow(new_row);
        }
        // // Add some mock data for testing
        // Vector<Object> row1 = new Vector<>();
        // row1.add(1);
        // row1.add("John");
        // row1.add("Doe");
        // row1.add("john.doe@example.com");
        // row1.add("123-456-7890");
        // row1.add("123 Main St");
        // row1.add("Cityville");
        // row1.add("CA");
        // row1.add("12345");

        // Vector<Object> row2 = new Vector<>();
        // row2.add(2);
        // row2.add("Jane");
        // row2.add("Smith");
        // row2.add("jane.smith@example.com");
        // row2.add("987-654-3210");
        // row2.add("456 Oak St");
        // row2.add("Townton");
        // row2.add("NY");
        // row2.add("54321");

        

        // tableModel.addRow(row1);
        // tableModel.addRow(row2);
    }

    // Custom renderer for the "Delete Customer" button
    private class ButtonRenderer extends JButton implements TableCellRenderer
     {
        public ButtonRenderer() {
            setOpaque(true);
            addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    int row = customerTable.convertRowIndexToModel(customerTable.getEditingRow());
                    showConfirmationDialog(row);
                }
            });
        }

        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            // Set button text and appearance
            setText("Delete Customer");
            setForeground(Color.RED);
            setBackground(isSelected ? Color.BLUE : Color.WHITE);

            return this;
        }

        private void showConfirmationDialog(int row) {
            String customerName = tableModel.getValueAt(row, 1) + " " + tableModel.getValueAt(row, 2);
            int option = JOptionPane.showConfirmDialog(null,
                    "Deleting customer: " + customerName + "\nAre you sure?",
                    "Confirmation", JOptionPane.YES_NO_OPTION);

            if (option == JOptionPane.YES_OPTION) {
                // Handle the deletion logic here (remove row from the table model, database, etc.)
                tableModel.removeRow(row);
            }
        }
    }

    // Custom editor for the "Delete Customer" button
    private class ButtonEditor extends DefaultCellEditor {
        private final JButton button;

        public ButtonEditor() {
            super(new JCheckBox());
            button = new JButton();
            button.setOpaque(true);
            button.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    int row = customerTable.convertRowIndexToModel(customerTable.getEditingRow());
                    // pop-up "Deleted customer: <customer name>"
                    String customerName = tableModel.getValueAt(row, 1) + " " + tableModel.getValueAt(row, 2);
                    sqlConnection.deleteCustomer(tableModel.getValueAt(row, 0).toString());
                    JOptionPane.showMessageDialog(null, "Deleted customer: " + customerName);
                    // Handle the deletion logic here (remove row from the table model, database, etc.)
                    tableModel.removeRow(row);
                }
            });
        }

        @Override
        public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
            // Set button text and appearance
            button.setText("Delete Customer");
            button.setForeground(Color.RED);
            button.setBackground(isSelected ? Color.BLUE : Color.WHITE);

            return button;
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Customer Manager Panel");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

            CustomerManager customerManager = new CustomerManager();
            frame.add(customerManager);
            frame.pack();
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        });
    }
}
