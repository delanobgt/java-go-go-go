package gomultiplayeronline;

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
import java.net.InetAddress;
import java.net.Socket;
import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.Timer;
import main.GoMainFrame;
import models.RoomModel;

public class GoCreateRoom extends JPanel {
    
    GoMainFrame parent;
    JLabel btnBack;
    JLabel lblTitle;
    
    JLabel lblBoardSize;
    ButtonGroup buttonGroup;
    JRadioButton rdNineSize;
    JRadioButton rdThirteenSize;
    JRadioButton rdNineteenSize;
    
    JLabel lblPlayerType;
    ButtonGroup buttonGroup2;
    JRadioButton rdBlack;
    JRadioButton rdWhite;
    JLabel lblBlackPlayer;
    JLabel lblWhitePlayer;
    
    JLabel lblPlayerName;
    JTextField txtPlayerName;
    
    JLabel lblRoomCodeBanner;
    JLabel lblRoomCodeValue;
    
    JLabel lblStatus;
    JLabel btnCreate;
 
    RoomInfoServer roomInfoServer; 
    GameServer gameServer;
 
    private volatile boolean waiting = false;
    private Timer timer;
    
    private static final int BTN_WIDTH = 200;
    private static final int BTN_HEIGHT = 50;
    private static final String PLACEHOLDER = "name...";
    
    public GoCreateRoom (GoMainFrame parent) {
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
                stopConnection();
                stopWaitingAnimation();
                parent.changeSceneTo("multiOnMenu");
            }
        });
        this.add(btnBack);
        
        lblTitle = new JLabel("Create Room");
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
        rdNineSize.setBounds(170, 170, 100, 50);
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
        rdThirteenSize.setBounds(390, 170, 120, 50);
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
        rdNineteenSize.setBounds(620, 170, 120, 50);
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
        
        lblPlayerType = new JLabel("Your Player Type");
        lblPlayerType.setFont(new Font("Arial", Font.BOLD, 24));
        lblPlayerType.setForeground(GoMainFrame.COLOR_2);
        lblPlayerType.setBounds(185, 250, 200, 50);
        lblPlayerType.setHorizontalAlignment(SwingConstants.CENTER);
        lblPlayerType.setVerticalAlignment(SwingConstants.CENTER);
        this.add(lblPlayerType);
        
        lblBlackPlayer = new PlayerTypeLabel2(
                Player.BLACK, 
                new Font("Arial", Font.BOLD, 16), 
                235,
                305,
                120,
                50
        );
        lblBlackPlayer.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if (!waiting)
                    rdBlack.setSelected(true);
            }
        });
        this.add(lblBlackPlayer);
        
        rdBlack = new JRadioButton();
        rdBlack.setBackground(GoMainFrame.COLOR_3);
        rdBlack.setBounds(220, 305, 20, 50);
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
                235,
                365,
                120,
                50
        );
        lblWhitePlayer.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if (!waiting)
                    rdWhite.setSelected(true);
            }
        });
        this.add(lblWhitePlayer);
        
        rdWhite = new JRadioButton();
        rdWhite.setBackground(GoMainFrame.COLOR_3);
        rdWhite.setBounds(220, 365, 20, 50);
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
        lblPlayerName.setBounds(510, 250, 200, 50);
        lblPlayerName.setHorizontalAlignment(SwingConstants.CENTER);
        lblPlayerName.setVerticalAlignment(SwingConstants.CENTER);
        this.add(lblPlayerName);
        
        txtPlayerName = new JTextField();
        txtPlayerName.setFont(new Font("Arial", Font.BOLD, 24));
        txtPlayerName.setText(PLACEHOLDER);
        txtPlayerName.setForeground(Color.GRAY);
        txtPlayerName.setBackground(GoMainFrame.COLOR_3);
        txtPlayerName.setBounds(525, 300, 175, 40);
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
        
        lblRoomCodeBanner = new JLabel("Room Code");
        lblRoomCodeBanner.setFont(new Font("Arial", Font.BOLD, 24));
        lblRoomCodeBanner.setForeground(GoMainFrame.COLOR_2);
        lblRoomCodeBanner.setBounds(510, 345, 200, 50);
        lblRoomCodeBanner.setHorizontalAlignment(SwingConstants.CENTER);
        lblRoomCodeBanner.setVerticalAlignment(SwingConstants.CENTER);
        this.add(lblRoomCodeBanner);
        
        lblRoomCodeValue = new JLabel(getFancyRoomCode());
        lblRoomCodeValue.setFont(new Font("Arial", Font.BOLD, 24));
        lblRoomCodeValue.setForeground(Color.WHITE);
        lblRoomCodeValue.setBackground(GoMainFrame.COLOR_2);
        lblRoomCodeValue.setOpaque(true);
        lblRoomCodeValue.setBounds(510, 390, 200, 50);
        lblRoomCodeValue.setHorizontalAlignment(SwingConstants.CENTER);
        lblRoomCodeValue.setVerticalAlignment(SwingConstants.CENTER);
        this.add(lblRoomCodeValue);
        
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
                "Create Room!",
                new Font("Arial", Font.BOLD, 20),
                centerX(getWidth(), BTN_WIDTH),
                520,
                BTN_WIDTH,
                BTN_HEIGHT
        );
        btnCreate.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseExited(MouseEvent e) {
                if (waiting) {
                    btnCreate.setBackground(Color.decode("#5E170C"));
                    btnCreate.setBorder(BorderFactory.createLineBorder(Color.decode("#DC000E"), 4));
                } else {
                    btnCreate.setBorder(BorderFactory.createLineBorder(GoMainFrame.COLOR_2, 4));
                }
            }
            @Override
            public void mouseEntered(MouseEvent e) {
                if (waiting) {
                    btnCreate.setBackground(Color.decode("#DC000E"));
                    btnCreate.setBorder(BorderFactory.createLineBorder(Color.decode("#DC000E"), 4));
                } else {
                    btnCreate.setBorder(BorderFactory.createLineBorder(GoMainFrame.COLOR_2, 4));
                }
            }
            @Override
            public void mousePressed(MouseEvent e) {
                if (waiting) {
                    setRadioButtonsEnabled(true);
                    waiting = false;
                    stopConnection();
                    txtPlayerName.setEnabled(true);
                    btnCreate.setText("Create Room!");
                    btnCreate.setBackground(GoMainFrame.COLOR_2);
                    btnCreate.setBorder(BorderFactory.createLineBorder(GoMainFrame.COLOR_2, 4));
                    stopWaitingAnimation();
                    promptStatus(GoMainFrame.COLOR_3, "Name must have 3-7 characters!");
                } else if (!waiting) {
                    if (!validateForm()) return;

                    waiting = true;
                    setRadioButtonsEnabled(false);
                    txtPlayerName.setEnabled(false);
                    startWaitingAnimation();
                    btnCreate.setText("Stop Waiting");
                    btnCreate.setBackground(Color.decode("#DC000E"));
                    btnCreate.setBorder(BorderFactory.createLineBorder(Color.decode("#DC000E"), 4));
                    
                    final BoardSize boardSize;
                    if (rdNineSize.isSelected()) boardSize = BoardSize.SMALL;
                    else if (rdThirteenSize.isSelected()) boardSize = BoardSize.MEDIUM;
                    else boardSize = BoardSize.LARGE;

                    final Player playerType;
                    if (rdBlack.isSelected()) playerType = Player.BLACK;
                    else playerType = Player.WHITE;

                    RoomModel roomModel = new RoomModel(
                            txtPlayerName.getText(), 
                            boardSize, 
                            playerType
                    );

                    roomInfoServer = new RoomInfoServer(roomModel);
                    roomInfoServer.startServer();

                    gameServer = new GameServer();
                    new Thread(() -> {
                        Socket socket = null;
                        if ( (socket = gameServer.waitForConnection()) != null ) {
                            roomInfoServer.stopServer();
                            GameSocket gameSocket = new GameSocket(socket);
                            String serverName = txtPlayerName.getText();
                            {
                                String firstName, secondName;
                                String clientName = (String) gameSocket.receive();
                                if (playerType.isBlack()) {
                                    firstName = serverName;
                                    secondName = clientName;
                                } else {
                                    firstName = clientName;
                                    secondName = serverName;
                                }
                                waiting = false;
                                parent.removeComponent("createRoom");
                                parent.addComponent("multiOnPanel", new GoMultiOnPanel(
                                        parent,
                                        playerType,
                                        firstName,
                                        secondName,
                                        boardSize,
                                        gameSocket
                                ));
                                parent.changeSceneTo("multiOnPanel");
                            }
                        } else {
                            promptStatus(Color.RED, "Connection failed!");
                            txtPlayerName.setEnabled(true);
                            waiting = false;
                        }
                        stopWaitingAnimation();
                    }).start();
                }
            }
        });
        this.add(btnCreate);
    }
    
    private boolean validateForm() {
        if (!rdNineSize.isSelected() && !rdThirteenSize.isSelected() && !rdNineteenSize.isSelected()) {
            promptStatus(Color.RED, "Please choose a board size!");
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
 
    private void startWaitingAnimation() {
        String initialWaitingStatus = "Waiting for other player";
        promptStatus(Color.decode("#1C9C81"), initialWaitingStatus);
        final int len = initialWaitingStatus.length();
        timer = new Timer(500, e -> {
            String waitingStatus = lblStatus.getText();
            waitingStatus += " .";
            if (waitingStatus.length() > len+(5*2))
                waitingStatus = waitingStatus.substring(0, len);
            lblStatus.setText(waitingStatus);
        });
        timer.start();
    }
    private void stopWaitingAnimation() {
        try {
            if (timer != null && timer.isRunning()) {
                timer.stop();
                timer = null;
            }
        } catch (Exception ex) {
            System.out.println(ex);
        }
    }
    
    private void stopConnection() {
        if (roomInfoServer != null) {
            roomInfoServer.stopServer();
            roomInfoServer = null;
        }
        if (gameServer != null) {
            gameServer.stopServer();
            gameServer = null;
        }
    }
    
    private void setRadioButtonsEnabled(boolean bool) {
        rdNineSize.setEnabled(bool);
        rdThirteenSize.setEnabled(bool);
        rdNineteenSize.setEnabled(bool);
        rdBlack.setEnabled(bool);
        rdWhite.setEnabled(bool);
    }
    
    private String getFancyRoomCode() {
        return toFancyRoomCode(getOwnIPAddress());
    }
    
    private String getOwnIPAddress() {
        try {
            return InetAddress.getLocalHost().getHostAddress();
        } catch (Exception ex) {
            System.out.println(ex);
            return null;
        }
    }
    private String toFancyRoomCode(String IP) {
        String[] tokens = IP.split("\\.");
        String[] codes = new String[4];
        for (int i = 0; i < codes.length; i++) {
            int decimal = Integer.parseInt(tokens[i]);
            String hex = Integer.toHexString(decimal);
            if (hex.length() == 1) hex = "0"+hex;
            codes[i] = hex;
        }
        String roomCode = codes[0]+codes[1]+"-"+codes[2]+codes[3];
        return convertToAlphabets(codes[0]+codes[1]+"-"+codes[2]+codes[3]);
    }
    private String convertToAlphabets(String hex) {
        StringBuilder sb = new StringBuilder();
        for (char ch : hex.toCharArray()) {
            if ('0' <= ch && ch <= '9') {
                sb.append( (char)('G'+(ch-'0')) );
            } else {
                sb.append(Character.toUpperCase(ch));
            }
        }
        return sb.toString();
    }
}
