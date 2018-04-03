package controls;

import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

public class SurrenderPanel extends JPanel {
    
    private JRadioButton rdPlayAgain;
    private JRadioButton rdMainMenu;
    private ButtonGroup buttonGroup;
    
    public SurrenderPanel(String winName, String loseName) {
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        
        String status = String.format(
                "%s won!",
                winName
        );
        JLabel lblStatus = new JLabel(status);
        this.add(lblStatus);
        
        String subStatus = String.format(
                "because %s surrendered",
                loseName
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
