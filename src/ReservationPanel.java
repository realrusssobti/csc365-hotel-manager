import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Date;
import java.time.LocalDate;

public class ReservationPanel extends JPanel {
    private JTable reservationTable;
    private DefaultTableModel tableModel;
    private SQLQueries sqlConnection;
    private Date startDate = Date.valueOf(LocalDate.now());
    private Date endDate = Date.valueOf(LocalDate.now().plusDays(9999));

    public ReservationPanel(SQLQueries sqlConnection) {
        this.sqlConnection = sqlConnection;
        setLayout(new BorderLayout());

        // Title
        JLabel titleLabel = new JLabel("Reservations");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        add(titleLabel, BorderLayout.NORTH);

        // Buttons Panel
        JPanel buttonsPanel = new JPanel(new FlowLayout());
        JButton createReservationButton = new JButton("Create Reservation");
//        JButton selectDatesButton = new JButton("Select Dates");
        // Date picker
        DatePicker StartDatePicker = new DatePicker();
        DatePicker EndDatePicker = new DatePicker();
        add(StartDatePicker, BorderLayout.WEST);
        add(EndDatePicker, BorderLayout.EAST);

        JButton selectDatesButton = new JButton("Select Dates");
        selectDatesButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Set the start and end dates
                startDate = Date.valueOf(StartDatePicker.getDate());
                endDate = Date.valueOf(EndDatePicker.getDate());
                // Refresh the table
                getReservationData(ReservationPanel.this, sqlConnection, startDate, endDate);
            }
        });

        createReservationButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Display ReservationPopup as a popup
                ReservationPopup reservationPopup = new ReservationPopup(sqlConnection);
                reservationPopup.setSize(600, 400);
                reservationPopup.setVisible(true);



            }
        });


        buttonsPanel.add(createReservationButton);
        buttonsPanel.add(selectDatesButton);
        add(buttonsPanel, BorderLayout.CENTER);

        // Table
        String[] columnNames = {"First Name", "Last Name", "Start", "End", "Room Type", "Check In/Out"};
        tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public Class<?> getColumnClass(int columnIndex) {
                return columnIndex == 5 ? JButton.class : Object.class;
            }
        };
        reservationTable = new JTable(tableModel);

        // fetch data from database
        getReservationData(this, sqlConnection, new Date(120, 01, 01), new Date(125, 01, 01));

        // Add buttons to the "Check In/Out" column
        TableColumn checkInOutColumn = reservationTable.getColumnModel().getColumn(5);
        checkInOutColumn.setCellRenderer(new ButtonRenderer());
        checkInOutColumn.setCellEditor(new ButtonEditor(new JCheckBox()));

        JScrollPane tableScrollPane = new JScrollPane(reservationTable);
        add(tableScrollPane, BorderLayout.SOUTH);
    }
    
    // static void addDummyData(ReservationPanel reservationPanel) {
    //     DefaultTableModel tableModel = reservationPanel.tableModel;

    //     // Dummy data
    //     Object[][] data = {
    //             {"John Doe", "101", "2023-12-01", "2023-12-10", "Single", "Check In/Out"},
    //             {"Jane Smith", "102", "2023-12-05", "2023-12-15", "Double", "Check In/Out"},
    //             {"Bob Johnson", "103", "2023-12-12", "2023-12-20", "Suite", "Check In/Out"}
    //             // Add more rows as needed
    //     };

    //     for (Object[] row : data) {
    //         tableModel.addRow(row);
    //     }
    // }


    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Reservation Panel Example");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

            ReservationPanel reservationPanel = new ReservationPanel(new SQLQueries());
            frame.getContentPane().add(reservationPanel);

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
                // Perform the action when the "Check In/Out" button is clicked
                JOptionPane.showMessageDialog(ReservationPanel.this, "Check In/Out Button Clicked for Row " + reservationTable.getSelectedRow());
                // Render a Check In/Out Panel as a popup
                Frame frame = new Frame();
                frame.setSize(600, 400);
                frame.setLocationRelativeTo(null);
                CheckInOutPanel checkInOutPanel = new CheckInOutPanel(sqlConnection);
                frame.add(checkInOutPanel);
                frame.setVisible(true);


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

    private static void getReservationData(ReservationPanel panel, SQLQueries sqlConnection, Date startDate, Date endDate) {
        DefaultTableModel tableModel = panel.tableModel;
        String[][] reservationData = sqlConnection.getReservationData(startDate, endDate);
        // clear the table
        tableModel.setRowCount(0);
        // add the new data
        for (String[] row : reservationData) {
            System.out.println(row[0] + " " + row[1] + " " + row[2] + " " + row[3]);
            // Append the "Check In/Out" button to the end of the row
            Object[] newRow = {row[0], row[1], row[2], row[3], row[4], "Check In/Out"};
            tableModel.addRow(newRow);

        }

    }
}

