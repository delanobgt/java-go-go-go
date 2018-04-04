package gomultiplayeronline;

import controls.ControlButton;
import controls.GameOverPanel;
import controls.PersistentButton;
import controls.PlayerPanel;
import controls.SurrenderPanel;
import enums.BoardSize;
import enums.Player;
import java.awt.Dimension;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import main.GoMainFrame;
import models.GoModel;
import models.Point;

public class GoMultiOnPanel extends JPanel {
    
    public static final int CONTROL_PANEL_HEIGHT = 40;
    GoMainFrame parent;
    private GoModel goModel;
    private GoMultiOnCanvas goMultiOffCanvas;
    private PlayerPanel firstPanel;
    private PlayerPanel secondPanel;
    private JPanel controlPanel;
    private Player playerType;
    
    public GoMultiOnPanel(GoMainFrame parent, Player playerType, String firstName, String secondName, BoardSize boardSize, GameSocket gameSocket) {
        this.parent = parent;
        this.playerType = playerType;
        this.goModel = new GoModel(boardSize);
        this.setLayout(null);
        this.setSize(new Dimension(GoMainFrame.FRAME_WIDTH, GoMainFrame.FRAME_HEIGHT));
        this.setPreferredSize(new Dimension(GoMainFrame.FRAME_WIDTH, GoMainFrame.FRAME_HEIGHT));
        
        goMultiOffCanvas = new GoMultiOnCanvas(goModel, this);
        this.add(goMultiOffCanvas);
        
        // first player panel
        firstPanel = new PlayerPanel(
                firstName,
                0,
                0,
                GoMainFrame.FRAME_WIDTH-goMultiOffCanvas.getWidth(), 
                GoMainFrame.FRAME_HEIGHT/2,
                Player.BLACK
        );
        this.add(firstPanel);
        firstPanel.activateColor();
        
        // second player panel
        secondPanel = new PlayerPanel(
                secondName,
                0,
                GoMainFrame.FRAME_HEIGHT/2, 
                GoMainFrame.FRAME_WIDTH-goMultiOffCanvas.getWidth(), 
                GoMainFrame.FRAME_HEIGHT/2,
                Player.WHITE
        );
        this.add(secondPanel);
        secondPanel.deactivateColor();
        
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
        
        PersistentButton territoryBtn = new PersistentButton(
                "Show Territory",
                BTN_SPACING, 
                V_MARGIN,
                BTN_WIDTH, 
                BTN_HEIGHT
        );
        territoryBtn.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                territoryBtn.togglePersistent();
                goModel.scanTerritory();
                goMultiOffCanvas.toggleTerritoryBeingShown();
                if (goMultiOffCanvas.isTerritoryBeingShown())
                    territoryBtn.setBackground(GoMainFrame.COLOR_2);
            }
        });
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
            public void mousePressed(MouseEvent e) {
                goModel.addPassCounter();
                goModel.toggleTurn();
                updatePlayerStatus();
                if (goModel.getPassCounter() >= 2) {
                    if (goModel.getWhiteTotalScore() > goModel.getBlackTotalScore())
                        goModel.winWhite();
                    else
                        goModel.winBlack();
                    
                    GameOverPanel gameOverPanel = new GameOverPanel(firstName, secondName, goModel);
                    
                    JOptionPane.showMessageDialog(parent, gameOverPanel);
                    
                    if (gameOverPanel.isPlayAgainSelected()) {
                        parent.addComponent("multiOffPanel", new GoMultiOnPanel(parent, playerType, firstName, secondName, boardSize, gameSocket));
                        parent.changeSceneTo("multiOffPanel");
                    } else {
                        parent.removeComponent("multiOffPanel");
                        parent.changeSceneTo("mainMenu");
                    }
                }
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
            public void mousePressed(MouseEvent e) {
                int response = JOptionPane.showConfirmDialog(parent, "Are you sure?", "Surrender", JOptionPane.YES_NO_OPTION);
                if (response == JOptionPane.YES_OPTION) {
                    goModel.surrenderedBy(goModel.getTurn());
                    
                    SurrenderPanel surrenderPanel = new SurrenderPanel(
                            goModel.getWin().isBlack() ? "BLACK" : "WHITE",
                            goModel.getWin().isBlack() ? "WHITE" : "BLACK"                
                    );
                    
                    JOptionPane.showMessageDialog(parent, surrenderPanel);
                    
                    if (surrenderPanel.isPlayAgainSelected()) {
                        parent.addComponent("multiOffPanel", new GoMultiOnPanel(parent, playerType, firstName, secondName, boardSize, gameSocket));
                        parent.changeSceneTo("multiOffPanel");
                    } else {
                        parent.removeComponent("multiOffPanel");
                        parent.changeSceneTo("mainMenu");
                    }
                }
            }
        });
        controlPanel.add(surrenderBtn);
        
        updatePlayerStatus();
    }
    
    public void updatePlayerStatus() {
        if (goModel.getTurn().isBlack()) {
            firstPanel.activateColor();
            firstPanel.giveTurn();
            secondPanel.deactivateColor();
            secondPanel.takeAwayTurn();
        } else {
            firstPanel.deactivateColor();
            firstPanel.takeAwayTurn();
            secondPanel.activateColor();
            secondPanel.giveTurn();
        }
        
        if (goModel.getPassCounter() == 2) {
            firstPanel.changeActionText("Last action: ", "yellow-PASS");
            secondPanel.changeActionText("Last action: ", "yellow-PASS");
        } else if (goModel.getPassCounter() == 1) {
            if (goModel.getTurn().isBlack())
                secondPanel.changeActionText("Last action: ", "yellow-PASS");
            else
                firstPanel.changeActionText("Last action: ", "yellow-PASS");
        } else if (goModel.getLastMovePoint() != null) {
            Point p = goModel.getLastMovePoint();
            if (goModel.getTurn().isBlack()) {
                secondPanel.changeActionText(
                        "Last action: <br>", 
                        String.format("white-PLACE STONE AT&nbsp;&nbsp;&nbsp;(%s %s)", p.r()+1, (char)('A'+p.c()))
                );
            } else {
                firstPanel.changeActionText(
                        "Last action: <br>",
                        String.format("white-PLACE STONE AT&nbsp;&nbsp;&nbsp;(%s %s)", p.r()+1, (char)('A'+p.c()))
                );
            }
        }
        
        firstPanel.changeTerritoryText("Territory: ", "white-"+goModel.getBlackTerritoryScore());
        secondPanel.changeTerritoryText("Territory: ", "white-"+goModel.getWhiteTerritoryScore());
        
        firstPanel.changeCapturedText("Captured: ", "white-"+goModel.getBlackCapturedScore());
        secondPanel.changeCapturedText("Captured: ", "white-"+goModel.getWhiteCapturedScore());
    }
    
}
