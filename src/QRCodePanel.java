import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class QRCodePanel extends JPanel {

    private String content;

    public QRCodePanel(String content) {
        this.content = content;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        try {
            BufferedImage qrCodeImage = generateQRCode(content, 500, 500);
            g.drawImage(qrCodeImage, 0, 0, this);
        } catch (WriterException e) {
            e.printStackTrace();
        }
    }

    private BufferedImage generateQRCode(String content, int width, int height) throws WriterException {
        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        BitMatrix bitMatrix = qrCodeWriter.encode(content, BarcodeFormat.QR_CODE, width, height);

        BufferedImage bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                bufferedImage.setRGB(x, y, bitMatrix.get(x, y) ? Color.BLACK.getRGB() : Color.WHITE.getRGB());
            }
        }

        return bufferedImage;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("QR Code Panel Example");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

            // Create the QRCodePanel with the content "Hello World"
            QRCodePanel qrCodePanel = new QRCodePanel("Hello World \n Room: 101\n" +
                    "Check In: 2021-04-01\n" +
                    "Check Out: 2021-04-02\n" +
                    "Guests: 2\n" +
                    "Total: $100.00 \n");

            // Add the QRCodePanel to the frame
            frame.getContentPane().add(qrCodePanel);

            frame.setSize(500, 500);
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        });
    }
}
