package gomultiplayeronline;

import enums.BoardSize;
import enums.Player;
import java.awt.Dimension;
import java.awt.FlowLayout;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import main.GoMainFrame;
import models.RoomModel;


public class GoCreateRoom extends JPanel {
    
    GoMainFrame parent;
    JButton btnBack;
    
    JLabel lblBoardSize;
    ButtonGroup buttonGroup;
    JRadioButton rdNineSize;
    JRadioButton rdThirteenSize;
    JRadioButton rdNineteenSize;
    
    JLabel lblPlayerType;
    ButtonGroup buttonGroup2;
    JRadioButton rdBlack;
    JRadioButton rdWhite;
    
    JLabel lblFirstName;
    JTextField txtFirstName;
    JLabel lblStatus;
    JButton btnCreate;
 
    public GoCreateRoom (GoMainFrame parent) {
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

        lblPlayerType = new JLabel();
        lblPlayerType.setText("Player Type: ");
        this.add(lblPlayerType);
        
        rdBlack = new JRadioButton();
        rdBlack.setText("Black");
        rdBlack.setSelected(true);
        this.add(rdBlack);
        
        rdWhite = new JRadioButton();
        rdWhite.setText("White");
        this.add(rdWhite);
        
        buttonGroup2 = new ButtonGroup();
        buttonGroup2.add(rdBlack);
        buttonGroup2.add(rdWhite);
        
        lblFirstName = new JLabel();
        lblFirstName.setText("Player name: ");
        this.add(lblFirstName);
        
        txtFirstName = new JTextField();
        txtFirstName.setPreferredSize(new Dimension(100, 30));
        this.add(txtFirstName);
        
        lblStatus = new JLabel();
        this.add(lblStatus);
        
        btnCreate = new JButton();
        btnCreate.setText("Create Room!");
        btnCreate.addActionListener(e -> {
            BoardSize boardSize = null;
            if (rdNineSize.isSelected()) boardSize = BoardSize.SMALL;
            else if (rdThirteenSize.isSelected()) boardSize = BoardSize.MEDIUM;
            else boardSize = BoardSize.LARGE;
            
            Player playerType = null;
            if (rdBlack.isSelected()) playerType = Player.BLACK;
            else playerType = Player.WHITE;
            
            if (!validateForm()) return;
            
            RoomModel roomModel = new RoomModel(
                    txtFirstName.getText(), 
                    boardSize, 
                    playerType
            );
            
            
        });
        this.add(btnCreate);
    }
    
    private boolean validateForm() {
        int firstLength = txtFirstName.getText().length();
        if ( !(3 <= firstLength && firstLength <= 8) ) {
            lblStatus.setText("Name must be between 3 to 8 characters!");
            return false;
        }
        return true;
    }
    
}
