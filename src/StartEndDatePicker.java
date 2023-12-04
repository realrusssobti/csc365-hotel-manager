import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class StartEndDatePicker extends Panel {

    private JLabel titleLabel;
    private DatePicker startDatePicker;
    private DatePicker endDatePicker;
    JButton submitButton;

    public StartEndDatePicker() {
        initializeComponents();

        setLayout(new GridLayout(4, 1));
        add(titleLabel);
        add(startDatePicker);
        add(endDatePicker);
        add(submitButton);

        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Perform any action needed before closing the window
                System.out.println("Selected Start Date: " + startDatePicker.getSelectedDate());
                System.out.println("Selected End Date: " + endDatePicker.getSelectedDate());

            }
        });

    }

    

    private void initializeComponents() {
        titleLabel = new JLabel("Select start and end dates");
        startDatePicker = new DatePicker();
        endDatePicker = new DatePicker();
        submitButton = new JButton("Submit");
    }

    public String getStartDate() {
        return startDatePicker.getSelectedDate();
    }
    public String getEndDate() {
        return endDatePicker.getSelectedDate();
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame();
        frame.setSize(600, 400);
        frame.setLocationRelativeTo(null);

        StartEndDatePicker startEndDatePicker = new StartEndDatePicker();
        frame.add(startEndDatePicker);
        frame.setVisible(true);
    }
}