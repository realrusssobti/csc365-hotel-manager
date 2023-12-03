import java.awt.*;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

public class DatePicker extends Panel {

    private Choice monthChoice;
    private Choice dayChoice;
    private Choice yearChoice;

    public DatePicker() {

        // Create Choice components for month, day, and year
        monthChoice = new Choice();
        dayChoice = new Choice();
        yearChoice = new Choice();

        // Fill the Choices with values
        for (int i = 1; i <= 12; i++) {
            if(i < 10)
                monthChoice.add("0" + String.valueOf(i));
            else
                monthChoice.add(String.valueOf(i));
        }

        for (int i = 1; i <= 31; i++) {
            if(i < 10)
                dayChoice.add("0" + String.valueOf(i));
            else
                dayChoice.add(String.valueOf(i));
        }

        for (int i = 2023; i <= 2069; i++) {
            yearChoice.add(String.valueOf(i));
        }

        // Set initial values
        monthChoice.select(0);
        dayChoice.select(0);
        yearChoice.select(0);

        // Add ItemListeners to detect changes in the Choices
        monthChoice.addItemListener(new ItemChangeListener());
        dayChoice.addItemListener(new ItemChangeListener());
        yearChoice.addItemListener(new ItemChangeListener());

        // Create layout
        setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));
        add(monthChoice);
        add(dayChoice);
        add(yearChoice);

        // Set frame properties
        setSize(300, 100);
        setVisible(true);

    }

    private class ItemChangeListener implements ItemListener {
        @Override
        public void itemStateChanged(ItemEvent e) {
            // Handle item state change
            System.out.println("Selected Date: "
                    + monthChoice.getSelectedItem() + "/"
                    + dayChoice.getSelectedItem() + "/"
                    + yearChoice.getSelectedItem());
        }
    }
    // Getter methods to retrieve selected values
    public String getSelectedMonth() {
        return monthChoice.getSelectedItem();
    }

    public String getSelectedDay() {
        return dayChoice.getSelectedItem();
    }

    public String getSelectedYear() {
        return yearChoice.getSelectedItem();
    }
    public String getSelectedDate() {
        return yearChoice.getSelectedItem() + "-"
                + monthChoice.getSelectedItem() + "-"
                + dayChoice.getSelectedItem();
    }

    public static void main(String[] args) {
        EventQueue.invokeLater(DatePicker::new);
    }
}
