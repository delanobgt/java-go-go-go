package gomultiplayeroffline;

import controls.ControlButton;
import controls.PlayerPanel;
import enums.BoardSize;
import java.awt.Dimension;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import main.GoMainFrame;
import models.GoModel;

public class GoMultiOffPanel extends JPanel {
    
    public static final int CONTROL_PANEL_HEIGHT = 40;
    GoMainFrame parent;
    private GoModel goModel;
    private GoMultiOffCanvas goMultiOffCanvas;
    private PlayerPanel firstPanel;
    private PlayerPanel secondPanel;
    private JPanel controlPanel;
    
    public GoMultiOffPanel(GoMainFrame parent, String firstName, String secondName, BoardSize boardSize) {
        this.parent = parent;
        this.goModel = new GoModel(boardSize);
        this.setLayout(null);
        this.setSize(new Dimension(GoMainFrame.FRAME_WIDTH, GoMainFrame.FRAME_HEIGHT));
        this.setPreferredSize(new Dimension(GoMainFrame.FRAME_WIDTH, GoMainFrame.FRAME_HEIGHT));
        
        goMultiOffCanvas = new GoMultiOffCanvas(goModel);
        this.add(goMultiOffCanvas);
        
        // first player panel
        firstPanel = new PlayerPanel(
                firstName,
                0,
                0,
                GoMainFrame.FRAME_WIDTH-goMultiOffCanvas.getWidth(), 
                GoMainFrame.FRAME_HEIGHT/2
        );
        this.add(firstPanel);
        firstPanel.activate();
        
        // second player panel
        secondPanel = new PlayerPanel(
                secondName,
                0,
                GoMainFrame.FRAME_HEIGHT/2, 
                GoMainFrame.FRAME_WIDTH-goMultiOffCanvas.getWidth(), 
                GoMainFrame.FRAME_HEIGHT/2
        );
        this.add(secondPanel);
        secondPanel.deactivate();
        
        // controlPanel
        controlPanel = new JPanel();
        controlPanel.setLayout(null);
        controlPanel.setBounds(
                firstPanel.getWidth(),
                goMultiOffCanvas.getHeight(), 
                goMultiOffCanvas.getWidth(),
                GoMainFrame.FRAME_HEIGHT-goMultiOffCanvas.getHeight()
        );
        controlPanel.setBackground(GoMainFrame.COLOR_4);
        this.add(controlPanel);
        
        final int BTN_WIDTH = 150;
        final int V_MARGIN = 7;
        final int BTN_SPACING = (controlPanel.getWidth()-(3*BTN_WIDTH))/4;
        final int BTN_HEIGHT = controlPanel.getHeight()-(2*V_MARGIN);
        
        JLabel territoryBtn = new ControlButton(
                "Show Territory",
                BTN_SPACING, 
                V_MARGIN,
                BTN_WIDTH, 
                BTN_HEIGHT
        );
        controlPanel.add(territoryBtn);
        
        JLabel passBtn = new ControlButton(
                "Pass",
                2*BTN_SPACING+BTN_WIDTH, 
                V_MARGIN, 
                BTN_WIDTH,
                BTN_HEIGHT
        );
        passBtn.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                goModel.addPassCounter();
            }
        });
        controlPanel.add(passBtn);
        
        JLabel surrenderBtn = new ControlButton(
                "Surrender",
                3*BTN_SPACING+2*BTN_WIDTH,
                V_MARGIN,
                BTN_WIDTH, 
                BTN_HEIGHT
        );
        surrenderBtn.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                goModel.surrenderedBy(goModel.getTurn());
            }
        });
        controlPanel.add(surrenderBtn);
    }
    
}
