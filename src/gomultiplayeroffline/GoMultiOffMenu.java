package gomultiplayeroffline;

import enums.BoardSize;
import java.awt.Dimension;
import java.awt.FlowLayout;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import main.GoMainFrame;

public class GoMultiOffMenu extends JPanel {
    
    GoMainFrame parent;
    JButton btnBack;
    JLabel lblBoardSize;
    JRadioButton rdNineSize;
    JRadioButton rdThirteenSize;
    JRadioButton rdNineteenSize;
    ButtonGroup buttonGroup;
    JLabel lblFirstName;
    JLabel lblSecondName;
    JTextField txtFirstName;
    JTextField txtSecondName;
    JLabel lblStatus;
    JButton btnStart;
    
    public GoMultiOffMenu(GoMainFrame parent) {
        this.parent = parent;
        this.setLayout(new FlowLayout());
        this.setSize(new Dimension(GoMainFrame.FRAME_WIDTH, GoMainFrame.FRAME_HEIGHT));
        this.setPreferredSize(new Dimension(GoMainFrame.FRAME_WIDTH, GoMainFrame.FRAME_HEIGHT));
        
        btnBack = new JButton();
        btnBack.setText("< BACK");
        btnBack.addActionListener(e -> {
            parent.changeSceneTo("mainMenu");
        });
        this.add(btnBack);
        
        lblBoardSize = new JLabel();
        lblBoardSize.setText("Board Size: ");
        this.add(lblBoardSize);
        
        rdNineSize = new JRadioButton();
        rdNineSize.setText("9 x 9");
        rdNineSize.setSelected(true);
        this.add(rdNineSize);
        
        rdThirteenSize = new JRadioButton();
        rdThirteenSize.setText("13 x 13");
        this.add(rdThirteenSize);
        
        rdNineteenSize = new JRadioButton();
        rdNineteenSize.setText("19 x 19");
        this.add(rdNineteenSize);
        
        buttonGroup = new ButtonGroup();
        buttonGroup.add(rdNineSize);
        buttonGroup.add(rdThirteenSize);
        buttonGroup.add(rdNineteenSize);

        lblFirstName = new JLabel();
        lblFirstName.setText("BLACK player name: ");
        this.add(lblFirstName);
        
        txtFirstName = new JTextField();
        txtFirstName.setPreferredSize(new Dimension(100, 30));
        this.add(txtFirstName);
        
        lblSecondName = new JLabel();
        lblSecondName.setText("WHITE player name: ");
        this.add(lblSecondName);
        
        txtSecondName = new JTextField();
        txtSecondName.setPreferredSize(new Dimension(100, 30));
        this.add(txtSecondName);
        
        lblStatus = new JLabel();
        this.add(lblStatus);
        
        btnStart = new JButton();
        btnStart.setText("Start Game!");
        btnStart.addActionListener(e -> {
            BoardSize boardSize = null;
            if (rdNineSize.isSelected()) boardSize = BoardSize.SMALL;
            else if (rdThirteenSize.isSelected()) boardSize = BoardSize.MEDIUM;
            else boardSize = BoardSize.LARGE;
            
            if (!validateForm()) return;
            
            parent.addComponent("multiOffPanel", new GoMultiOffPanel(parent, txtFirstName.getText(), txtSecondName.getText(), boardSize));
            parent.changeSceneTo("multiOffPanel");            
        });
        this.add(btnStart);
    }
    
    private boolean validateForm() {
        int firstLength = txtFirstName.getText().length();
        int secondLength = txtSecondName.getText().length();
        if (!(3 <= firstLength && firstLength <= 7) || !(3 <= secondLength && secondLength <= 7)) {
            lblStatus.setText("Name must be between 3 to 8 characters!");
            return false;
        }
        return true;
    }
}
