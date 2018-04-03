/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controls;

import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import main.GoMainFrame;
/**
 *
 * @author irvin
 */
public class PersistentButton extends JLabel {
    
    private boolean persistentOn = false;
    
    public PersistentButton(String text, int x, int y, int w, int h) {
        this.setHorizontalAlignment(SwingConstants.CENTER);
        this.setVerticalAlignment(SwingConstants.CENTER);
        this.setForeground(Color.WHITE);
        this.setBackground(GoMainFrame.COLOR_3);
        this.setOpaque(true);
        this.setBounds(x, y, w, h);
        this.setText(text);
        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseExited(MouseEvent e) {
                if (!persistentOn)
                    setBackground(GoMainFrame.COLOR_3);
            }
            @Override
            public void mouseEntered(MouseEvent e) {
                if (!persistentOn)
                    setBackground(GoMainFrame.COLOR_2);
            }
        });
    }
    
    public void togglePersistent() {
        this.persistentOn = !this.persistentOn;
    }
}