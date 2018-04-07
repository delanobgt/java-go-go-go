package controls;

import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import models.GoModel;

public class GameOverPanel extends JPanel {
    
    private JRadioButton rdPlayAgain;
    private JRadioButton rdMainMenu;
    private ButtonGroup buttonGroup;
    
    public GameOverPanel(String firstName, String secondName, GoModel goModel) {
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        
        double blackTotalScore = goModel.getBlackTotalScore();
        double whiteTotalScore = goModel.getWhiteTotalScore();
        
        String msg = String.format(
                    "<html>" +
                    "<span style=\"font-size:16px;\">%s</span> won! <br>" +
                    "%s (BLACK): %.1f points<br>" +
                    "%s (WHITE): %.1f points (included +6.5)<br>" +
                    "</html>",
                    goModel.getWin().isBlack() ? firstName : secondName,
                    firstName, blackTotalScore,
                    secondName, whiteTotalScore
            );
        JLabel lblStatus = new JLabel(msg);
        this.add(lblStatus);
        
        rdPlayAgain = new JRadioButton("Play Again");
        rdPlayAgain.setSelected(true);
        this.add(rdPlayAgain);
        
        rdMainMenu = new JRadioButton("Main Menu");
        this.add(rdMainMenu);
        
        buttonGroup = new ButtonGroup();
        buttonGroup.add(rdPlayAgain);
        buttonGroup.add(rdMainMenu);
    }
    
    public boolean isPlayAgainSelected() {
        return rdPlayAgain.isSelected();
    }
}
