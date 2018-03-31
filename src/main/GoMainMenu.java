package main;

import java.awt.BorderLayout;
import java.awt.Dimension;
import javax.swing.JButton;
import javax.swing.JPanel;

public class GoMainMenu extends JPanel {
    
    GoMainFrame parent;
    JButton singleBtn;
    JButton multiOffBtn;
    JButton multiOnBtn;
    
    public GoMainMenu(GoMainFrame parent) {
        this.parent = parent;
        this.setLayout(new BorderLayout());
        this.setSize(new Dimension(GoMainFrame.FRAME_WIDTH, GoMainFrame.FRAME_HEIGHT));
        
        singleBtn = new JButton();
        singleBtn.setText("Singleplayer");
        singleBtn.addActionListener(e -> {
            parent.changeSceneTo("single");
        });
        
        multiOffBtn = new JButton();
        multiOffBtn.setText("Multiplayer<br>(Offline)");
        multiOffBtn.addActionListener(e -> {
            parent.changeSceneTo("multiOffMenu");
        });
        
        multiOnBtn = new JButton();
        multiOnBtn.setText("Multiplayer<br>(Online)");
        multiOnBtn.addActionListener(e -> {
            parent.changeSceneTo("multiOn");
        });
        
        this.add(singleBtn, BorderLayout.NORTH);
        this.add(multiOffBtn, BorderLayout.CENTER);
        this.add(multiOnBtn, BorderLayout.SOUTH);
    }
    
    
    
}
