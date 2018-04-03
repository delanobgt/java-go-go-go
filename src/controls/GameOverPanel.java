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
        
        String winName, loseName;
        if (goModel.getWin().isBlack()) {
            winName = firstName;
            loseName = secondName;
        } else {
            winName = secondName;
            loseName = firstName;
        }
        
        double blackFinalScore = goModel.getBlackTotalScore();
        double whiteFinalScore = goModel.getWhiteTotalScore();
        
        String status = String.format(
                "%s won by %.1f points! (Total Points: %.1f)",
                winName,
                Math.abs(blackFinalScore-whiteFinalScore),
                winName.equals(firstName) ? blackFinalScore : whiteFinalScore
        );
        JLabel lblStatus = new JLabel(status);
        this.add(lblStatus);
            
        String subStatus = String.format(
                "%s's total points: %.1f",
                loseName,
                winName.equals(firstName) ? whiteFinalScore : blackFinalScore
        );
        JLabel lblSubStatus = new JLabel(subStatus);
        this.add(lblSubStatus);
        
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
