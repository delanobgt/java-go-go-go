package gosingle;

import controls.ControlButton;
import controls.MaterialButton;
import controls.PlayerTypeLabel2;
import enums.BoardSize;
import enums.Difficulty;
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

public class GoSingleMenu extends JPanel {
    
    GoMainFrame parent;
    JLabel btnBack;
    JLabel lblTitle;
    
    JLabel lblBoardSize;
    ButtonGroup buttonGroup;
    JRadioButton rdNineSize;
    JRadioButton rdThirteenSize;
    JRadioButton rdNineteenSize;
    
    JLabel lblDifficulty;
    ButtonGroup buttonGroup3;
    JRadioButton rdEasy;
    JRadioButton rdMedium;
    JRadioButton rdHard;
    
    JLabel lblPlayerType;
    ButtonGroup buttonGroup2;
    JRadioButton rdBlack;
    JRadioButton rdWhite;
    JLabel lblBlackPlayer;
    JLabel lblWhitePlayer;
    
    JLabel lblPlayerName;
    JTextField txtPlayerName;
    JLabel lblStatus;
    JLabel btnCreate;
    
    private static final int BTN_WIDTH = 200;
    private static final int BTN_HEIGHT = 50;
    private static final String PLACEHOLDER = "name...";
    
    public GoSingleMenu (GoMainFrame parent) {
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
                parent.removeComponent("singleMenu");
                parent.changeSceneTo("mainMenu");
            }
        });
        this.add(btnBack);
        
        lblTitle = new JLabel("Singleplayer (Bot)");
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
        lblBoardSize.setBounds(centerX(getWidth(), 200), 110, 200, 50);
        lblBoardSize.setHorizontalAlignment(SwingConstants.CENTER);
        lblBoardSize.setVerticalAlignment(SwingConstants.CENTER);
        this.add(lblBoardSize);
        
        rdNineSize = new JRadioButton();
        rdNineSize.setText("9 x 9");
        rdNineSize.setBackground(GoMainFrame.COLOR_2);
        rdNineSize.setFont(new Font("Arial", Font.BOLD, 24));
        rdNineSize.setForeground(Color.WHITE);
        rdNineSize.setBackground(GoMainFrame.COLOR_3);
        rdNineSize.setBounds(170, 160, 100, 50);
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
        rdThirteenSize.setBounds(390, 160, 120, 50);
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
        rdNineteenSize.setBounds(620, 160, 120, 50);
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
        
        lblDifficulty = new JLabel();
        lblDifficulty.setText("Difficulty");
        lblDifficulty.setFont(new Font("Arial", Font.BOLD, 24));
        lblDifficulty.setForeground(GoMainFrame.COLOR_2);
        lblDifficulty.setBounds(centerX(getWidth(), 200), 220, 200, 50);
        lblDifficulty.setHorizontalAlignment(SwingConstants.CENTER);
        lblDifficulty.setVerticalAlignment(SwingConstants.CENTER);
        this.add(lblDifficulty);
        
        rdEasy = new JRadioButton();
        rdEasy.setText("Easy");
        rdEasy.setBackground(GoMainFrame.COLOR_2);
        rdEasy.setFont(new Font("Arial", Font.BOLD, 24));
        rdEasy.setForeground(Color.WHITE);
        rdEasy.setBackground(GoMainFrame.COLOR_3);
        rdEasy.setBounds(170, 270, 100, 50);
        rdEasy.setHorizontalAlignment(SwingConstants.CENTER);
        rdEasy.setVerticalAlignment(SwingConstants.CENTER);
        rdEasy.setFocusPainted(false);
        rdEasy.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == ItemEvent.SELECTED)
                    rdEasy.setBackground(GoMainFrame.COLOR_2);
                else
                    rdEasy.setBackground(GoMainFrame.COLOR_3);
            }
        });
        this.add(rdEasy);
        
        rdMedium = new JRadioButton();
        rdMedium.setText("Medium");
        rdMedium.setFont(new Font("Arial", Font.BOLD, 24));
        rdMedium.setForeground(Color.WHITE);
        rdMedium.setBackground(GoMainFrame.COLOR_3);
        rdMedium.setBounds(390, 270, 120, 50);
        rdMedium.setHorizontalAlignment(SwingConstants.CENTER);
        rdMedium.setVerticalAlignment(SwingConstants.CENTER);
        rdMedium.setFocusPainted(false);
        rdMedium.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == ItemEvent.SELECTED)
                    rdMedium.setBackground(GoMainFrame.COLOR_2);
                else
                    rdMedium.setBackground(GoMainFrame.COLOR_3);
            }
        });
        this.add(rdMedium);
        
        rdHard = new JRadioButton();
        rdHard.setText("Hard");
        rdHard.setFont(new Font("Arial", Font.BOLD, 24));
        rdHard.setForeground(Color.WHITE);
        rdHard.setBackground(GoMainFrame.COLOR_3);
        rdHard.setBounds(620, 270, 100, 50);
        rdHard.setHorizontalAlignment(SwingConstants.CENTER);
        rdHard.setVerticalAlignment(SwingConstants.CENTER);
        rdHard.setFocusPainted(false);
        rdHard.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == ItemEvent.SELECTED)
                    rdHard.setBackground(GoMainFrame.COLOR_2);
                else
                    rdHard.setBackground(GoMainFrame.COLOR_3);
            }
        });
        this.add(rdHard);
        
        buttonGroup3 = new ButtonGroup();
        buttonGroup3.add(rdEasy);
        buttonGroup3.add(rdMedium);
        buttonGroup3.add(rdHard);
        
        lblPlayerType = new JLabel("Your Player Type");
        lblPlayerType.setFont(new Font("Arial", Font.BOLD, 24));
        lblPlayerType.setForeground(GoMainFrame.COLOR_2);
        lblPlayerType.setBounds(185, 330, 200, 50);
        lblPlayerType.setHorizontalAlignment(SwingConstants.CENTER);
        lblPlayerType.setVerticalAlignment(SwingConstants.CENTER);
        this.add(lblPlayerType);
        
        lblBlackPlayer = new PlayerTypeLabel2(
                Player.BLACK, 
                new Font("Arial", Font.BOLD, 16), 
                165,
                380,
                120,
                50
        );
        lblBlackPlayer.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                rdBlack.setSelected(true);
            }
        });
        this.add(lblBlackPlayer);
        
        rdBlack = new JRadioButton();
        rdBlack.setBackground(GoMainFrame.COLOR_3);
        rdBlack.setBounds(150, 380, 20, 50);
        rdBlack.setFocusPainted(false);
        rdBlack.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == ItemEvent.SELECTED) {
                    rdBlack.setBackground(GoMainFrame.COLOR_2);
                    lblBlackPlayer.setBorder(BorderFactory.createLineBorder(GoMainFrame.COLOR_2, 4));
                } else {
                    rdBlack.setBackground(GoMainFrame.COLOR_3);
                    lblBlackPlayer.setBorder(BorderFactory.createLineBorder(GoMainFrame.COLOR_3, 4));
                }
            }
        });
        this.add(rdBlack);
        
        lblWhitePlayer = new PlayerTypeLabel2(
                Player.WHITE, 
                new Font("Arial", Font.BOLD, 16), 
                315,
                380,
                120,
                50
        );
        lblWhitePlayer.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                rdWhite.setSelected(true);
            }
        });
        this.add(lblWhitePlayer);
        
        rdWhite = new JRadioButton();
        rdWhite.setBackground(GoMainFrame.COLOR_3);
        rdWhite.setBounds(300, 380, 20, 50);
        rdWhite.setFocusPainted(false);
        rdWhite.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == ItemEvent.SELECTED) {
                    rdWhite.setBackground(GoMainFrame.COLOR_2);
                    lblWhitePlayer.setBorder(BorderFactory.createLineBorder(GoMainFrame.COLOR_2, 4));
                } else {
                    rdWhite.setBackground(GoMainFrame.COLOR_3);
                    lblWhitePlayer.setBorder(BorderFactory.createLineBorder(GoMainFrame.COLOR_3, 4));
                }
            }
        });
        this.add(rdWhite);
        
        buttonGroup2 = new ButtonGroup();
        buttonGroup2.add(rdBlack);
        buttonGroup2.add(rdWhite);
        
        lblPlayerName = new JLabel("Your Name");
        lblPlayerName.setFont(new Font("Arial", Font.BOLD, 24));
        lblPlayerName.setForeground(GoMainFrame.COLOR_2);
        lblPlayerName.setBounds(510, 330, 200, 50);
        lblPlayerName.setHorizontalAlignment(SwingConstants.CENTER);
        lblPlayerName.setVerticalAlignment(SwingConstants.CENTER);
        this.add(lblPlayerName);
        
        txtPlayerName = new JTextField();
        txtPlayerName.setFont(new Font("Arial", Font.BOLD, 24));
        txtPlayerName.setText(PLACEHOLDER);
        txtPlayerName.setForeground(Color.GRAY);
        txtPlayerName.setBackground(GoMainFrame.COLOR_3);
        txtPlayerName.setBounds(525, 380, 175, 40);
        txtPlayerName.setBorder(BorderFactory.createLineBorder(GoMainFrame.COLOR_2, 3));
        txtPlayerName.setCaretColor(Color.WHITE);
        txtPlayerName.setHorizontalAlignment(SwingConstants.CENTER);
        txtPlayerName.addFocusListener(new FocusAdapter() {
            @Override
            public void focusLost(FocusEvent e) {
                if (txtPlayerName.getText().isEmpty()) {
                    txtPlayerName.setText(PLACEHOLDER);
                    txtPlayerName.setForeground(Color.GRAY);
                }
            }
            @Override
            public void focusGained(FocusEvent e) {
                if (txtPlayerName.getText().equals(PLACEHOLDER)) {
                    txtPlayerName.setText("");
                    txtPlayerName.setForeground(Color.WHITE);
                }
            }
        });
        this.add(txtPlayerName);
        
        lblStatus = new JLabel();
        lblStatus.setFont(new Font("Arial", Font.BOLD, 16));
        lblStatus.setBounds(0, 460, getWidth(), 30);
        lblStatus.setForeground(Color.WHITE);
        promptStatus(GoMainFrame.COLOR_3, "Name must have 3-7 characters!");
        lblStatus.setHorizontalAlignment(SwingConstants.CENTER);
        lblStatus.setVerticalAlignment(SwingConstants.CENTER);
        lblStatus.setOpaque(true);
        this.add(lblStatus);
        
        btnCreate = new MaterialButton(
                "Start Game!",
                new Font("Arial", Font.BOLD, 20),
                centerX(getWidth(), BTN_WIDTH),
                520,
                BTN_WIDTH,
                BTN_HEIGHT
        );
        btnCreate.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if (!validateForm()) return;
                
                BoardSize boardSize = null;
                if (rdNineSize.isSelected()) boardSize = BoardSize.SMALL;
                else if (rdThirteenSize.isSelected()) boardSize = BoardSize.MEDIUM;
                else boardSize = BoardSize.LARGE;
                
                Difficulty difficulty = null;
                if (rdEasy.isSelected()) difficulty = Difficulty.EASY;
                else if (rdMedium.isSelected()) difficulty = Difficulty.MEDIUM;
                else difficulty = Difficulty.HARD;
                
                Player playerType;
                if (rdBlack.isSelected()) playerType = Player.BLACK;
                else playerType = Player.WHITE;
                
                String playerName = txtPlayerName.getText();
                
                parent.removeComponent("singleMenu");
                parent.addComponent("singlePanel", new GoSinglePanel(parent, playerName, playerType, boardSize, difficulty));
                parent.changeSceneTo("singlePanel");
            }
        });
        this.add(btnCreate);
    }
    
    private boolean validateForm() {
        if (!rdNineSize.isSelected() && !rdThirteenSize.isSelected() && !rdNineteenSize.isSelected()) {
            promptStatus(Color.RED, "Please choose a board size!");
            return false;
        }
        if (!rdEasy.isSelected() && !rdMedium.isSelected() && !rdHard.isSelected()) {
            promptStatus(Color.RED, "Please choose a difficulty mode!");
            return false;
        }
        if (!rdBlack.isSelected() && !rdWhite.isSelected()) {
            promptStatus(Color.RED, "Please choose a player type!");
            return false;
        }
        int firstLength = txtPlayerName.getText().length();
        if ( !(3 <= firstLength && firstLength <= 8) || txtPlayerName.getText().equals(PLACEHOLDER) ) {
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
