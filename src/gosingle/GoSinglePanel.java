package gosingle;

import controls.ControlButton;
import controls.GameOverPanel;
import controls.PersistentButton;
import controls.PlayerPanel;
import controls.SurrenderPanel;
import enums.BoardSize;
import enums.Difficulty;
import enums.Player;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import main.GoMainFrame;
import models.GoModel;
import models.Point;

public class GoSinglePanel extends JPanel {
    
    public static final int CONTROL_PANEL_HEIGHT = 40;
    GoMainFrame parent;
    private GoModel goModel;
    private GoSingleCanvas goSingleCanvas;
    private PlayerPanel firstPanel;
    private PlayerPanel secondPanel;
    private JPanel controlPanel;
    private JLabel surrenderBtn;
    private JLabel passBtn;
    private Difficulty difficulty;
    private final String playerName;
    private final String firstName, secondName;
    private final Player playerType;
    private final BoardSize boardSize;
    private volatile boolean waitingComputer;
    
    public GoSinglePanel(GoMainFrame parent, String playerName, Player playerType, BoardSize boardSize, Difficulty difficulty) {
        this.parent = parent;
        this.goModel = new GoModel(boardSize);
        this.difficulty = difficulty;
        this.boardSize = boardSize;
        this.setLayout(null);
        this.setSize(new Dimension(GoMainFrame.FRAME_WIDTH, GoMainFrame.FRAME_HEIGHT));
        this.setPreferredSize(new Dimension(GoMainFrame.FRAME_WIDTH, GoMainFrame.FRAME_HEIGHT));
        this.waitingComputer = playerType.equals(goModel.getTurn()) ? false : true;
        
        this.playerType = playerType;
        this.playerName = playerName;
        this.firstName = playerType.isBlack() ? playerName : "Computer";
        this.secondName = playerType.isWhite() ? playerName : "Computer";
        
        goSingleCanvas = new GoSingleCanvas(goModel, this);
        this.add(goSingleCanvas);
        
        // first player panel
        firstPanel = new PlayerPanel(
                firstName,
                0,
                0,
                GoMainFrame.FRAME_WIDTH-goSingleCanvas.getWidth(), 
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
                GoMainFrame.FRAME_WIDTH-goSingleCanvas.getWidth(), 
                GoMainFrame.FRAME_HEIGHT/2,
                Player.WHITE
        );
        this.add(secondPanel);
        secondPanel.deactivateColor();
        
        // controlPanel
        controlPanel = new JPanel();
        controlPanel.setLayout(null);
        controlPanel.setBounds(firstPanel.getWidth(),
                goSingleCanvas.getHeight(), 
                goSingleCanvas.getWidth(),
                GoMainFrame.FRAME_HEIGHT-goSingleCanvas.getHeight()
        );
        controlPanel.setBackground(GoMainFrame.COLOR_4);
        this.add(controlPanel);
        
        final int BTN_WIDTH = 150;
        final int V_MARGIN = 7;
        final int BTN_SPACING = (controlPanel.getWidth()-(3*BTN_WIDTH))/4;
        final int BTN_HEIGHT = controlPanel.getHeight()-(2*V_MARGIN);
        
        PersistentButton territoryBtn = new PersistentButton(
                "Show Territory",
                new Font("Arial", Font.BOLD, 12),
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
                goSingleCanvas.toggleTerritoryBeingShown();
                if (goSingleCanvas.isTerritoryBeingShown())
                    territoryBtn.setBackground(GoMainFrame.COLOR_2);
            }
        });
        controlPanel.add(territoryBtn);
        
        passBtn = new ControlButton(
                "Pass",
                new Font("Arial", Font.BOLD, 12),
                2*BTN_SPACING+BTN_WIDTH, 
                V_MARGIN, 
                BTN_WIDTH,
                BTN_HEIGHT
        );
        passBtn.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if (waitingComputer) return;
                handlePassBtn();
            }
            @Override
            public void mouseExited(MouseEvent e) {
                if (waitingComputer) {
                    passBtn.setBackground(GoMainFrame.COLOR_3);
                    passBtn.setForeground(Color.GRAY);
                }
            }
            @Override
            public void mouseEntered(MouseEvent e) {
                if (waitingComputer) {
                    passBtn.setBackground(GoMainFrame.COLOR_3);
                    passBtn.setForeground(Color.GRAY);
                }
            }
        });
        controlPanel.add(passBtn);
        
        surrenderBtn = new ControlButton(
                "Surrender",
                new Font("Arial", Font.BOLD, 12),
                3*BTN_SPACING+2*BTN_WIDTH,
                V_MARGIN,
                BTN_WIDTH, 
                BTN_HEIGHT
        );
        surrenderBtn.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if (waitingComputer) return;
                int response = JOptionPane.showConfirmDialog(parent, "Are you sure?", "Surrender", JOptionPane.YES_NO_OPTION);
                if (response == JOptionPane.YES_OPTION) {
                    goModel.surrenderedBy(goModel.getTurn());
                    
                    SurrenderPanel surrenderPanel = new SurrenderPanel(
                            firstName,
                            secondName,
                            goModel
                    );
                    
                    JOptionPane.showMessageDialog(parent, surrenderPanel);
                    
                    if (surrenderPanel.isPlayAgainSelected()) {
                        parent.addComponent("singlePanel", new GoSinglePanel(parent, playerName, playerType, boardSize, difficulty));
                        parent.changeSceneTo("singlePanel");
                    } else {
                        parent.removeComponent("singlePanel");
                        parent.changeSceneTo("mainMenu");
                    }
                }
            }
            @Override
            public void mouseExited(MouseEvent e) {
                if (waitingComputer) {
                    surrenderBtn.setBackground(GoMainFrame.COLOR_3);
                    surrenderBtn.setForeground(Color.GRAY);
                }
            }
            @Override
            public void mouseEntered(MouseEvent e) {
                if (waitingComputer) {
                    surrenderBtn.setBackground(GoMainFrame.COLOR_3);
                    surrenderBtn.setForeground(Color.GRAY);
                }
            }
        });
        controlPanel.add(surrenderBtn);
        
        updatePlayerStatus();
        goSingleCanvas.startBotThread();
    }
    
    public void handlePassBtn() {
        goModel.addPassCounter();
        goModel.toggleTurn();
        updatePlayerStatus();
        if (goModel.getPassCounter() >= 2) {
            if (goModel.getWhiteTotalScore() >= goModel.getBlackTotalScore())
                goModel.winWhite();
            else
                goModel.winBlack();
            
            GameOverPanel gameOverPanel = new GameOverPanel(firstName, secondName, goModel);

            JOptionPane.showMessageDialog(parent, gameOverPanel);

            if (gameOverPanel.isPlayAgainSelected()) {
                parent.addComponent("singlePanel", new GoSinglePanel(parent, playerName, playerType, boardSize, difficulty));
                parent.changeSceneTo("singlePanel");
            } else {
                parent.removeComponent("singlePanel");
                parent.changeSceneTo("mainMenu");
            }
        }
        if (!goModel.getTurn().equals(playerType))
            setWaitingComputer(true);
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
    
    public boolean isWaitingComputer() {
        return waitingComputer;
    }
    public void setWaitingComputer(boolean waitingComputer) {
        this.waitingComputer = waitingComputer;
    }

    public Difficulty getDifficulty() {
        return difficulty;
    }
    
    public void setControlButtonsActivated(boolean bool) {
        if (bool) {
            passBtn.setBackground(GoMainFrame.COLOR_3);
            passBtn.setForeground(Color.WHITE);
            surrenderBtn.setBackground(GoMainFrame.COLOR_3);
            surrenderBtn.setForeground(Color.WHITE);
        } else {
            passBtn.setBackground(GoMainFrame.COLOR_3);
            passBtn.setForeground(Color.GRAY);
            surrenderBtn.setBackground(GoMainFrame.COLOR_3);
            surrenderBtn.setForeground(Color.GRAY);
        }
    }
}
