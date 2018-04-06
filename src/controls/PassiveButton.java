package controls;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import main.GoMainFrame;

public class PassiveButton extends JLabel {
    
    public PassiveButton(String text, Font font, int x, int y, int w, int h) {
        this.setHorizontalAlignment(SwingConstants.CENTER);
        this.setVerticalAlignment(SwingConstants.CENTER);
        this.setForeground(Color.WHITE);
        this.setBackground(GoMainFrame.COLOR_4);
        this.setOpaque(true);
        this.setFont(font);
        this.setBounds(x, y, w, h);
        this.setText(text);
        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseExited(MouseEvent e) { 
                setBackground(GoMainFrame.COLOR_4);
            }
            @Override
            public void mouseEntered(MouseEvent e) {
                setBackground(GoMainFrame.COLOR_3);
            }
        });
    }
    
}
