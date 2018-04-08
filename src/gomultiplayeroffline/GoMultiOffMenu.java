package gomultiplayeroffline;

import controls.ControlButton;
import controls.MaterialButton;
import controls.PlayerTypeLabel2;
import enums.BoardSize;
import enums.Player;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import main.GoMainFrame;

public class GoMultiOffMenu extends JPanel {
    
    GoMainFrame parent;
    JLabel lblTitle;
    JLabel btnBack;
    JLabel lblBoardSize;
    JRadioButton rdNineSize;
    JRadioButton rdThirteenSize;
    JRadioButton rdNineteenSize;
    ButtonGroup buttonGroup;
    JLabel lblPlayersName;
    PlayerTypeLabel2 lblFirstName;
    JLabel lblSecondName;
    JTextField txtFirstName;
    JTextField txtSecondName;
    JLabel lblStatus;
    JLabel btnStart;
    private static final int BTN_WIDTH = 200;
    private static final int BTN_HEIGHT = 50;
    private static final String PLACEHOLDER = "name...";
    
    public GoMultiOffMenu(GoMainFrame parent) {
        this.parent = parent;
        this.setLayout(null);
        this.setSize(new Dimension(GoMainFrame.FRAME_WIDTH, GoMainFrame.FRAME_HEIGHT));
        this.setPreferredSize(new Dimension(GoMainFrame.FRAME_WIDTH, GoMainFrame.FRAME_HEIGHT));
        this.setBackground(GoMainFrame.COLOR_4);
        
        btnBack = new ControlButton(
                "<",
                new Font("Arial", Font.BOLD, 42),
                0,
                0,
                80,
                110
        );
        btnBack.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                parent.changeSceneTo("mainMenu");
            }
        });
        this.add(btnBack);
        
        lblTitle = new JLabel("Offline Multiplayer (Local)");
        lblTitle.setFont(new Font("Arial", Font.BOLD, 48));
        lblTitle.setForeground(GoMainFrame.COLOR_2);
        lblTitle.setBackground(GoMainFrame.COLOR_3);
        lblTitle.setBounds(0, 0, getWidth(), 110);
        lblTitle.setOpaque(true);
        lblTitle.setHorizontalAlignment(SwingConstants.CENTER);
        lblTitle.setVerticalAlignment(SwingConstants.CENTER);
        this.add(lblTitle);
        
        lblBoardSize = new JLabel();
        lblBoardSize.setText("Board Size");
        lblBoardSize.setFont(new Font("Arial", Font.BOLD, 24));
        lblBoardSize.setForeground(GoMainFrame.COLOR_2);
        lblBoardSize.setBounds(centerX(getWidth(), 200), 130, 200, 50);
        lblBoardSize.setHorizontalAlignment(SwingConstants.CENTER);
        lblBoardSize.setVerticalAlignment(SwingConstants.CENTER);
        this.add(lblBoardSize);
        
        rdNineSize = new JRadioButton();
        rdNineSize.setText("9 x 9");
        rdNineSize.setFont(new Font("Arial", Font.BOLD, 24));
        rdNineSize.setForeground(Color.WHITE);
        rdNineSize.setBackground(GoMainFrame.COLOR_3);
        rdNineSize.setBounds(170, 190, 100, 50);
        rdNineSize.setHorizontalAlignment(SwingConstants.CENTER);
        rdNineSize.setVerticalAlignment(SwingConstants.CENTER);
        rdNineSize.setFocusPainted(false);
        rdNineSize.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == ItemEvent.SELECTED)
                    rdNineSize.setBackground(GoMainFrame.COLOR_2);
                else
                    rdNineSize.setBackground(GoMainFrame.COLOR_3);
            }
        });
        this.add(rdNineSize);
        
        rdThirteenSize = new JRadioButton();
        rdThirteenSize.setText("13 x 13");
        rdThirteenSize.setFont(new Font("Arial", Font.BOLD, 24));
        rdThirteenSize.setForeground(Color.WHITE);
        rdThirteenSize.setBackground(GoMainFrame.COLOR_3);
        rdThirteenSize.setBounds(390, 190, 120, 50);
        rdThirteenSize.setHorizontalAlignment(SwingConstants.CENTER);
        rdThirteenSize.setVerticalAlignment(SwingConstants.CENTER);
        rdThirteenSize.setFocusPainted(false);
        rdThirteenSize.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == ItemEvent.SELECTED)
                    rdThirteenSize.setBackground(GoMainFrame.COLOR_2);
                else
                    rdThirteenSize.setBackground(GoMainFrame.COLOR_3);
            }
        });
        this.add(rdThirteenSize);
        
        rdNineteenSize = new JRadioButton();
        rdNineteenSize.setText("19 x 19");
        rdNineteenSize.setFont(new Font("Arial", Font.BOLD, 24));
        rdNineteenSize.setForeground(Color.WHITE);
        rdNineteenSize.setBackground(GoMainFrame.COLOR_3);
        rdNineteenSize.setBounds(620, 190, 120, 50);
        rdNineteenSize.setHorizontalAlignment(SwingConstants.CENTER);
        rdNineteenSize.setVerticalAlignment(SwingConstants.CENTER);
        rdNineteenSize.setFocusPainted(false);
        rdNineteenSize.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == ItemEvent.SELECTED)
                    rdNineteenSize.setBackground(GoMainFrame.COLOR_2);
                else
                    rdNineteenSize.setBackground(GoMainFrame.COLOR_3);
            }
        });
        this.add(rdNineteenSize);
        
        buttonGroup = new ButtonGroup();
        buttonGroup.add(rdNineSize);
        buttonGroup.add(rdThirteenSize);
        buttonGroup.add(rdNineteenSize);

        lblPlayersName = new JLabel("Players' Name");
        lblPlayersName.setFont(new Font("Arial", Font.BOLD, 24));
        lblPlayersName.setForeground(GoMainFrame.COLOR_2);
        lblPlayersName.setBounds(centerX(getWidth(), 200), 280, 200, 50);
        lblPlayersName.setHorizontalAlignment(SwingConstants.CENTER);
        lblPlayersName.setVerticalAlignment(SwingConstants.CENTER);
        this.add(lblPlayersName);
        
        lblFirstName = new PlayerTypeLabel2(
                Player.BLACK, 
                new Font("Arial", Font.BOLD, 16), 
                268,
                340,
                120,
                35
        );
        this.add(lblFirstName);
        
        txtFirstName = new JTextField();
        txtFirstName.setFont(new Font("Arial", Font.BOLD, 24));
        txtFirstName.setText(PLACEHOLDER);
        txtFirstName.setForeground(Color.GRAY);
        txtFirstName.setBackground(GoMainFrame.COLOR_3);
        txtFirstName.setBounds(240, 390, 175, 40);
        txtFirstName.setBorder(BorderFactory.createLineBorder(GoMainFrame.COLOR_2, 3));
        txtFirstName.setCaretColor(Color.WHITE);
        txtFirstName.setHorizontalAlignment(SwingConstants.CENTER);
        txtFirstName.addFocusListener(new FocusAdapter() {
            @Override
            public void focusLost(FocusEvent e) {
                if (txtFirstName.getText().isEmpty()) {
                    txtFirstName.setText(PLACEHOLDER);
                    txtFirstName.setForeground(Color.GRAY);
                }
            }
            @Override
            public void focusGained(FocusEvent e) {
                if (txtFirstName.getText().equals(PLACEHOLDER)) {
                    txtFirstName.setText("");
                    txtFirstName.setForeground(Color.WHITE);
                }
            }
        });
        this.add(txtFirstName);
        
        lblSecondName = new PlayerTypeLabel2(
                Player.WHITE,
                new Font("Arial", Font.BOLD, 16), 
                528,
                340,
                120,
                35
        );
        this.add(lblSecondName);
        
        txtSecondName = new JTextField();
        txtSecondName.setFont(new Font("Arial", Font.BOLD, 24));
        txtSecondName.setText(PLACEHOLDER);
        txtSecondName.setForeground(Color.GRAY);
        txtSecondName.setBackground(GoMainFrame.COLOR_3);
        txtSecondName.setBounds(500, 390, 175, 40);
        txtSecondName.setBorder(BorderFactory.createLineBorder(GoMainFrame.COLOR_2, 3));
        txtSecondName.setCaretColor(Color.WHITE);
        txtSecondName.setHorizontalAlignment(SwingConstants.CENTER);
        txtSecondName.addFocusListener(new FocusAdapter() {
            @Override
            public void focusLost(FocusEvent e) {
                if (txtSecondName.getText().isEmpty()) {
                    txtSecondName.setText(PLACEHOLDER);
                    txtSecondName.setForeground(Color.GRAY);
                }
            }
            @Override
            public void focusGained(FocusEvent e) {
                if (txtSecondName.getText().equals(PLACEHOLDER)) {
                    txtSecondName.setText("");
                    txtSecondName.setForeground(Color.WHITE);
                }
            }
        });
        this.add(txtSecondName);
        
        lblStatus = new JLabel();
        lblStatus.setFont(new Font("Arial", Font.BOLD, 16));
        lblStatus.setBounds(0, 460, getWidth(), 30);
        lblStatus.setText("Name must have 3-7 characters!");
        lblStatus.setForeground(Color.WHITE);
        lblStatus.setBackground(GoMainFrame.COLOR_3);
        lblStatus.setHorizontalAlignment(SwingConstants.CENTER);
        lblStatus.setVerticalAlignment(SwingConstants.CENTER);
        lblStatus.setOpaque(true);
        this.add(lblStatus);
        
        btnStart = new MaterialButton(
                "Start Game",
                new Font("Arial", Font.BOLD, 20),
                centerX(getWidth(), BTN_WIDTH),
                520,
                BTN_WIDTH,
                BTN_HEIGHT
        );
        btnStart.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if (!validateForm()) return;

                BoardSize boardSize = null;
                if (rdNineSize.isSelected()) boardSize = BoardSize.SMALL;
                else if (rdThirteenSize.isSelected()) boardSize = BoardSize.MEDIUM;
                else boardSize = BoardSize.LARGE;
                
                parent.addComponent("multiOffPanel", new GoMultiOffPanel(parent, txtFirstName.getText(), txtSecondName.getText(), boardSize));
                parent.changeSceneTo("multiOffPanel");
            }
        });
        this.add(btnStart);
    }
    
    private boolean validateForm() {
        int firstLength = txtFirstName.getText().length();
        int secondLength = txtSecondName.getText().length();
        if (!rdNineSize.isSelected() && !rdThirteenSize.isSelected() && !rdNineteenSize.isSelected()) {
            promptStatus(Color.RED, "Please choose a board size!");
            return false;
        }
        if (!(3 <= firstLength && firstLength <= 7) || 
                !(3 <= secondLength && secondLength <= 7) ||
                txtFirstName.getText().equals(PLACEHOLDER) ||
                txtSecondName.getText().equals(PLACEHOLDER)) {
            promptStatus(Color.RED, "Name must have 3-7 characters!");
            return false;
        }
        return true;
    }
    
    private int centerX(int W, int w) {
        return (W-w)/2;
    }
    
    private void promptStatus(Color color, String text) {
        lblStatus.setForeground(Color.WHITE);
        lblStatus.setBackground(color);
        lblStatus.setText(text);
    }
}
