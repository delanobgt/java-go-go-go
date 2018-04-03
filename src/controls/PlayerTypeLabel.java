package controls;

import enums.Player;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

public class PlayerTypeLabel extends JLabel {
    
    private Color BACK_COLOR = null;
    private Color FORE_COLOR = null;
    
    public PlayerTypeLabel(int width, int height, int spacing, Player playerType) {
        this.setBounds(spacing, spacing, width, height);
        this.setFont(new Font("Arial", Font.BOLD, 12));
        this.setHorizontalAlignment(SwingConstants.CENTER);
        this.setVerticalAlignment(SwingConstants.CENTER);
        this.setOpaque(true);
        if (playerType.isBlack()) {
            this.setText("BLACK");
            FORE_COLOR = Color.WHITE;
            BACK_COLOR = Color.BLACK;
        } else {
            this.setText("WHITE");
            FORE_COLOR = Color.BLACK;
            BACK_COLOR = Color.WHITE;
        }
        this.setForeground(FORE_COLOR);
        this.setBackground(BACK_COLOR);
    }
}
