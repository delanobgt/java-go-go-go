package controls;

import java.awt.Color;
import java.awt.Font;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

// LegendJLabel inner class
public class LegendJLabel extends JLabel {
    public LegendJLabel(int x, int y, int size, String text) {
        this.setFont(new Font("Arial", Font.BOLD, 18));
        this.setBounds(x, y, size, size);
        this.setForeground(Color.WHITE);
        this.setHorizontalAlignment(SwingConstants.CENTER);
        this.setVerticalAlignment(SwingConstants.CENTER);
        this.setText(text);
    }
}
