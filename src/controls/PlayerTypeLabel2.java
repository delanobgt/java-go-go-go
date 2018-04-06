package controls;

import enums.Player;
import java.awt.Color;
import java.awt.Font;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import main.GoMainFrame;

public class PlayerTypeLabel2 extends JLabel {
    
    private Color BACK_COLOR = null;
    private Color FORE_COLOR = null;
    
    public PlayerTypeLabel2(Player playerType, Font font, int x, int y, int w, int h) {
        this.setBounds(x ,y, w, h);
        this.setFont(font);
        this.setHorizontalAlignment(SwingConstants.CENTER);
        this.setVerticalAlignment(SwingConstants.CENTER);
        this.setOpaque(true);
        this.setBorder(BorderFactory.createLineBorder(GoMainFrame.COLOR_3, 4));
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
