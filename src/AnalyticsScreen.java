import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AnalyticsScreen extends Panel {

        public AnalyticsScreen() {
            add(new Label("This is the Analytics Screen"), BorderLayout.CENTER);

            DatePicker dp = new DatePicker();
            add(dp);
            // print out the date when the button is clicked
            Button analyticsButton = new Button("Analytics Button");
            analyticsButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    System.out.println("Analytics button clicked!");
                    System.out.println("Selected Date: " + dp.getSelectedDate());
                }
            });
            add(analyticsButton);
        }
}
