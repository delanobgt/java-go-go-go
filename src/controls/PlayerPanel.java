package controls;

import enums.Player;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import main.GoMainFrame;

public class PlayerPanel extends JPanel {
    
    private static final int SIDE_MARGIN = 25;
    private String name;
    private Player playerType;
    private JLabel lblName;
    private JLabel lblCaptured;
    private JLabel lblTerritory;
    private JLabel lblAction;
    private JLabel lblTurn;
    private JLabel lblPlayerType;
    private Color borderColor = null;
    
    public PlayerPanel(String name, int x, int y, int w, int h, Player playerType) {
        this.name = name;
        this.playerType = playerType;
        this.setLayout(null);
        this.setBounds(x, y, w, h);
        
        {
            lblName = new JLabel();
            lblName.setText(name);
            lblName.setFont(new Font("Arial", Font.BOLD, 32));
            lblName.setBounds(0, 0, getWidth(), 50);
            lblName.setHorizontalAlignment(SwingConstants.CENTER);
            lblName.setVerticalAlignment(SwingConstants.CENTER);
            lblName.setOpaque(true);
            
            final int WIDTH = 50;
            final int HEIGHT = 30;
            final int SPACING = (lblName.getHeight()-HEIGHT)/2;
            PlayerTypeLabel lblPlayerType = new PlayerTypeLabel(WIDTH, HEIGHT, SPACING, playerType);
            
            add(lblPlayerType);
            add(lblName);
        }
        
        lblCaptured = new JLabel();
        changeCapturedText("Captured: ");
        lblCaptured.setFont(new Font("Arial", Font.BOLD, 18));
        lblCaptured.setBounds(SIDE_MARGIN, 60, getWidth()-2*SIDE_MARGIN, 50);
        lblCaptured.setVerticalAlignment(SwingConstants.CENTER);
        add(lblCaptured);
        
        lblTerritory = new JLabel();
        changeTerritoryText("Territory: ");
        lblTerritory.setFont(new Font("Arial", Font.BOLD, 18));
        lblTerritory.setBounds(SIDE_MARGIN, 100, getWidth()-2*SIDE_MARGIN, 50);
        lblTerritory.setVerticalAlignment(SwingConstants.CENTER);
        add(lblTerritory);
        
        lblAction = new JLabel();
        changeActionText("Last action: ");
        lblAction.setFont(new Font("Arial", Font.BOLD, 18));
        lblAction.setBounds(SIDE_MARGIN, 140, getWidth()-2*SIDE_MARGIN, 50);
        lblAction.setVerticalAlignment(SwingConstants.CENTER);
        add(lblAction);
        
        final int LBL_TURN_WIDTH = 150;
        final int LBL_TURN_HEIGHT = 50;
        lblTurn = new JLabel();
        lblTurn.setFont(new Font("Arial", Font.BOLD, 24));
        lblTurn.setBounds((getWidth()-LBL_TURN_WIDTH)/2, 220, LBL_TURN_WIDTH, LBL_TURN_HEIGHT);
        lblTurn.setHorizontalAlignment(SwingConstants.CENTER);
        lblTurn.setVerticalAlignment(SwingConstants.CENTER);
        lblTurn.setOpaque(true);
        add(lblTurn);
    }
    
    public void takeAwayTurn() {
        changeTurnText("Waiting...");
    }
    public void giveTurn() {
        changeTurnText("Your Turn!");
    }
    public void thinkingTurn() {
        changeTurnText("Thinking..");
    }
    
    public void activateColor() {
        this.setBackground(GoMainFrame.COLOR_4);
        
        lblName.setForeground(GoMainFrame.COLOR_1);
        lblName.setBackground(GoMainFrame.COLOR_2);
        
        lblCaptured.setForeground(GoMainFrame.COLOR_2);
        
        lblTerritory.setForeground(GoMainFrame.COLOR_2);
        
        lblAction.setForeground(GoMainFrame.COLOR_2);
        
        lblTurn.setForeground(GoMainFrame.COLOR_1);
        lblTurn.setBackground(GoMainFrame.COLOR_2);
        
        borderColor = GoMainFrame.COLOR_2;
        repaint();
    }
    
    public void deactivateColor() {
        this.setBackground(GoMainFrame.COLOR_4);
        
        lblName.setForeground(GoMainFrame.COLOR_1);
        lblName.setBackground(GoMainFrame.COLOR_3);
        
        lblCaptured.setForeground(GoMainFrame.COLOR_2);
        
        lblTerritory.setForeground(GoMainFrame.COLOR_2);
        
        lblAction.setForeground(GoMainFrame.COLOR_2);
        
        lblTurn.setForeground(GoMainFrame.COLOR_1);
        lblTurn.setBackground(GoMainFrame.COLOR_3);
        
        borderColor = GoMainFrame.COLOR_4;
        repaint();
    }
    
    //below are methods for labels' text manipulation
    public void changeCapturedText(String... texts) {
        changeLabelText(lblCaptured, texts);
    }
    public void changeTerritoryText(String... texts) {
        changeLabelText(lblTerritory, texts);
    }
    public void changeActionText(String... texts) {
        changeLabelText(lblAction, texts);
    }
    public void changeTurnText(String... texts) {
        changeLabelText(lblTurn, texts);
    }
    private void changeLabelText(JLabel lbl, String... texts) {
        StringBuilder sb = new StringBuilder("<html>");
        for (String text : texts) {
            if (text.contains("-")) {
                String[] splitted = text.split("-");
                sb.append(String.format(
                        "<span style=\"color: %s;\">%s</span>",
                        splitted[0],
                        splitted[1]
                ));
            } else {
                sb.append(text);
            }
        }
        lbl.setText(sb.toString());
    }

    @Override
    protected void paintComponent(Graphics oldG) {
        super.paintComponent(oldG);
        Graphics2D g = (Graphics2D) oldG;
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        
        final int BOLD_SIZE = 6;
        if (borderColor != null) {
            g.setStroke(new BasicStroke(BOLD_SIZE));
            g.setColor(borderColor);           
            g.drawRect(BOLD_SIZE/2, BOLD_SIZE/2, getWidth()-BOLD_SIZE, getHeight()-BOLD_SIZE);
        }
    } 
}


