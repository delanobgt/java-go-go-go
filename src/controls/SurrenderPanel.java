package controls;

import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import models.GoModel;

public class SurrenderPanel extends JPanel {
    
    private JRadioButton rdPlayAgain;
    private JRadioButton rdMainMenu;
    private ButtonGroup buttonGroup;
    
    public SurrenderPanel(String firstName, String secondName, GoModel goModel) {
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        
        String blackName = String.format(
                "<span style=\"font-size:16px;\">%s</span>",
                firstName
        );
        String whiteName = String.format(
                "<span style=\"font-size:16px;\">%s</span>",
                secondName
        );
        String msg = String.format(
                "<html>%s won because %s surrendered.</html>",
                goModel.getWin().isBlack() ? blackName : whiteName,
                !goModel.getWin().isBlack() ? blackName : whiteName
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
