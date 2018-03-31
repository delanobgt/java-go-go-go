package gomultiplayeroffline;

import enums.BoardSize;
import java.awt.Color;
import javax.swing.JPanel;
import main.GoMainFrame;
import models.GoModel;

public class GoMultiOffPanel extends JPanel {
    
    GoMainFrame parent;
    private GoModel goModel;
    private GoMultiOffCanvas goMultiOffCanvas;
    private JPanel firstPanel;
    private JPanel secondPanel;
    
    public GoMultiOffPanel(GoMainFrame parent, BoardSize boardSize) {
        this.parent = parent;
        goModel = new GoModel(boardSize);
        goMultiOffCanvas = new GoMultiOffCanvas(goModel);
        
        this.setLayout(null);
        this.add(goMultiOffCanvas);
        
        firstPanel = new JPanel();
        firstPanel.setBounds(0, 0, 300, 300);
        firstPanel.setBackground(Color.RED);
        this.add(firstPanel);
        
        secondPanel = new JPanel();
        secondPanel.setBounds(0, 300, 300, 300);
        secondPanel.setBackground(Color.GREEN);
        this.add(secondPanel);
    }
    
}
