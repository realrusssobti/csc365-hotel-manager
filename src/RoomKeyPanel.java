import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableCellRenderer;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class RoomKeyPanel extends JPanel {
    private JTable keyTable;
    private DefaultTableModel tableModel;

    private JComboBox<String> roomNumberComboBox;
    private DatePicker expirationDateComboBox;

    public RoomKeyPanel() {
        setLayout(new GridLayout(1, 2));

        // Create the table
        createKeyTable();

        // Create the "Create Key" pane
        createCreateKeyPane();
    }

    private void createKeyTable() {
        String[] columnNames = {"Key ID", "Room Number", "Expiration Date", "Action"};

        tableModel = new DefaultTableModel(columnNames, 0);
        keyTable = new JTable(tableModel);

        // Add a delete button to each row
        keyTable.getColumn("Action").setCellRenderer(new ButtonRenderer());
        keyTable.getColumn("Action").setCellEditor(new ButtonEditor(new JCheckBox()));

        JScrollPane scrollPane = new JScrollPane(keyTable);
        add(scrollPane);

        // Add a dummy row for illustration
        addKeyToTable(1, "101", "2023-12-31");
    }

    private void createCreateKeyPane() {
        JPanel createKeyPane = new JPanel(new GridLayout(4, 1));

        // Create components for "Create Key" pane
        roomNumberComboBox = new JComboBox<>(new String[]{"101", "102", "103"});
        expirationDateComboBox = new DatePicker();

        JButton createKeyButton = new JButton("Create Key");
        createKeyButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Print selected values to the console
                String selectedRoomNumber = (String) roomNumberComboBox.getSelectedItem();
                String selectedExpirationDate = (String) expirationDateComboBox.getSelectedDate();

                System.out.println("Creating Key with Room Number: " + selectedRoomNumber +
                        ", Expiration Date: " + selectedExpirationDate);

                // Add the created key to the table
                addKeyToTable(tableModel.getRowCount() + 1, selectedRoomNumber, selectedExpirationDate);
                // Render pop-up for QR code
                QRCodePanel qrCodePopup = new QRCodePanel("" +
                        "Room Number: " + selectedRoomNumber + "\n" +
                        "Expiration Date: " + selectedExpirationDate);

                // set up a pop-up window
                JFrame popup = new JFrame();
                popup.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                popup.getContentPane().add(qrCodePopup);
                popup.setSize(500, 500);
                popup.setLocationRelativeTo(null);
                popup.setVisible(true);
                popup.setTitle("New Room Key");

            }
        });

        // Add components to "Create Key" pane
        JLabel createKeyLabel = new JLabel("Create Key", SwingConstants.CENTER);
        createKeyLabel.setFont(new Font("Arial", Font.BOLD, 24));
        createKeyPane.add(createKeyLabel);
        createKeyPane.add(roomNumberComboBox);
        createKeyPane.add(expirationDateComboBox);
        createKeyPane.add(createKeyButton);

        add(createKeyPane);
    }

    private void addKeyToTable(int keyID, String roomNumber, String expirationDate) {
        Object[] data = {keyID, roomNumber, expirationDate, "Delete"};
        tableModel.addRow(data);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Room Key Panel Example");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

            RoomKeyPanel roomKeyPanel = new RoomKeyPanel();
            frame.getContentPane().add(roomKeyPanel);

            frame.setSize(1600, 900);
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        });
    }

    // Custom TableCellRenderer for rendering buttons
    private static class ButtonRenderer extends JButton implements TableCellRenderer {
        public ButtonRenderer() {
            setOpaque(true);
        }

        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            setText((value == null) ? "" : value.toString());
            return this;
        }
    }

    // Custom TableCellEditor for editing buttons
    private class ButtonEditor extends DefaultCellEditor {
        private JButton button;
        private String label;
        private boolean isPushed;

        public ButtonEditor(JCheckBox checkBox) {
            super(checkBox);
            button = new JButton();
            button.setOpaque(true);
            button.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    fireEditingStopped();
                }
            });
        }

        @Override
        public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
            if (isSelected) {
                button.setForeground(table.getSelectionForeground());
                button.setBackground(table.getSelectionBackground());
            } else {
                button.setForeground(table.getForeground());
                button.setBackground(UIManager.getColor("Button.background"));
            }

            label = (value == null) ? "" : value.toString();
            button.setText(label);
            isPushed = true;
            return button;
        }

        @Override
        public Object getCellEditorValue() {
            if (isPushed) {
                // Perform the delete action when the button is clicked
                int selectedRow = keyTable.getSelectedRow();
                if (selectedRow != -1) {
                    tableModel.removeRow(selectedRow);
                }
            }
            isPushed = false;
            return label;
        }

        @Override
        public boolean stopCellEditing() {
            isPushed = false;
            return super.stopCellEditing();
        }
    }
}
