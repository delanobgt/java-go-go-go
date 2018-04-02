package gomultiplayeroffline;

import enums.BoardSize;
import java.awt.BorderLayout;
import java.awt.Dimension;
import javax.swing.JButton;
import javax.swing.JPanel;
import main.GoMainFrame;

public class GoMultiOffMenu extends JPanel {
    
    GoMainFrame parent;
    JButton singleBtn;
    JButton multiOffBtn;
    JButton multiOnBtn;
    
    public GoMultiOffMenu(GoMainFrame parent) {
        this.parent = parent;
        this.setLayout(new BorderLayout());
        this.setSize(new Dimension(GoMainFrame.FRAME_WIDTH, GoMainFrame.FRAME_HEIGHT));
        this.setPreferredSize(new Dimension(GoMainFrame.FRAME_WIDTH, GoMainFrame.FRAME_HEIGHT));
        
        singleBtn = new JButton();
        singleBtn.setText("9 x 9");
        singleBtn.addActionListener(e -> {
            parent.addComponent("multiOffPanel", new GoMultiOffPanel(parent, "Andiana", "Budiana", BoardSize.SMALL));
            parent.changeSceneTo("multiOffPanel");
        });
        
        multiOffBtn = new JButton();
        multiOffBtn.setText("13 x 13");
        multiOffBtn.addActionListener(e -> {
            parent.addComponent("multiOffPanel", new GoMultiOffPanel(parent, "Andiana", "Budiana", BoardSize.MEDIUM));
            parent.changeSceneTo("multiOffPanel");
        });
        
        multiOnBtn = new JButton();
        multiOnBtn.setText("19 x 19");
        multiOnBtn.addActionListener(e -> {
            parent.addComponent("multiOffPanel", new GoMultiOffPanel(parent, "Andiana", "Budiana", BoardSize.LARGE));
            parent.changeSceneTo("multiOffPanel");
        });
        
        this.add(singleBtn, BorderLayout.NORTH);
        this.add(multiOffBtn, BorderLayout.CENTER);
        this.add(multiOnBtn, BorderLayout.SOUTH);
    }
    
}
