package controls;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.border.Border;
import main.GoMainFrame;

public class MaterialButton extends JLabel {
    
    public MaterialButton(String text, Font font, int x, int y, int w, int h) {
        this.setHorizontalAlignment(SwingConstants.CENTER);
        this.setVerticalAlignment(SwingConstants.CENTER);
        this.setForeground(Color.WHITE);
        this.setBackground(GoMainFrame.COLOR_3);
        this.setOpaque(true);
        this.setFont(font);
        this.setBounds(x, y, w, h);
        this.setText(text);
        this.setBorder(BorderFactory.createLineBorder(GoMainFrame.COLOR_2, 3));
        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseExited(MouseEvent e) { 
                setBackground(GoMainFrame.COLOR_3);
            }
            @Override
            public void mouseEntered(MouseEvent e) {
                setBackground(GoMainFrame.COLOR_2);
            }
        });
    }
    
}
