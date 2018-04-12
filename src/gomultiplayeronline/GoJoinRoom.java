package gomultiplayeronline;

import controls.ControlButton;
import controls.MaterialButton;
import enums.BoardSize;
import enums.Player;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.Timer;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.AbstractDocument;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;
import main.GoMainFrame;
import models.RoomModel;

public class GoJoinRoom extends JPanel {

    GoMainFrame parent;
    JLabel btnBack;
    JLabel lblTitle;

    JLabel lblFirstName;
    JTextField txtFirstName;

    JLabel lblRoomCode;
    JTextField txtRoomCode;

    JLabel lblStatus;
    JLabel btnJoin;

    RoomInfoClient roomInfoClient;
    private volatile boolean waiting = false;
    private Timer timer;

    private static final int BTN_WIDTH = 200;
    private static final int BTN_HEIGHT = 50;
    private static final String PLACEHOLDER = "name...";
    private static final String PLACEHOLDER2 = "ROOM CODE / IP...";

    public GoJoinRoom(GoMainFrame parent) {
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
                parent.changeSceneTo("multiOnMenu");
            }
        });
        this.add(btnBack);

        lblTitle = new JLabel("Join Room");
        lblTitle.setFont(new Font("Arial", Font.BOLD, 48));
        lblTitle.setForeground(GoMainFrame.COLOR_2);
        lblTitle.setBackground(GoMainFrame.COLOR_3);
        lblTitle.setBounds(0, 0, getWidth(), 110);
        lblTitle.setOpaque(true);
        lblTitle.setHorizontalAlignment(SwingConstants.CENTER);
        lblTitle.setVerticalAlignment(SwingConstants.CENTER);
        this.add(lblTitle);

        lblFirstName = new JLabel("Your Name");
        lblFirstName.setFont(new Font("Arial", Font.BOLD, 24));
        lblFirstName.setForeground(GoMainFrame.COLOR_2);
        lblFirstName.setBounds(centerX(getWidth(), 200), 150, 200, 50);
        lblFirstName.setHorizontalAlignment(SwingConstants.CENTER);
        lblFirstName.setVerticalAlignment(SwingConstants.CENTER);
        this.add(lblFirstName);

        txtFirstName = new JTextField();
        txtFirstName.setFont(new Font("Arial", Font.BOLD, 24));
        txtFirstName.setText(PLACEHOLDER);
        txtFirstName.setForeground(Color.GRAY);
        txtFirstName.setBackground(GoMainFrame.COLOR_3);
        txtFirstName.setBounds(centerX(getWidth(), 175), 200, 175, 40);
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

        lblRoomCode = new JLabel("Room Code / IP");
        lblRoomCode.setFont(new Font("Arial", Font.BOLD, 24));
        lblRoomCode.setForeground(GoMainFrame.COLOR_2);
        lblRoomCode.setBounds(centerX(getWidth(), 250), 270, 250, 50);
        lblRoomCode.setHorizontalAlignment(SwingConstants.CENTER);
        lblRoomCode.setVerticalAlignment(SwingConstants.CENTER);
        this.add(lblRoomCode);

        txtRoomCode = new JTextField();
        txtRoomCode.setFont(new Font("Arial", Font.BOLD, 24));
        txtRoomCode.setText(PLACEHOLDER2);
        txtRoomCode.setForeground(Color.GRAY);
        txtRoomCode.setBackground(GoMainFrame.COLOR_3);
        txtRoomCode.setBounds(centerX(getWidth(), 250), 320, 250, 40);
        txtRoomCode.setBorder(BorderFactory.createLineBorder(GoMainFrame.COLOR_2, 3));
        txtRoomCode.setCaretColor(Color.WHITE);
        txtRoomCode.setHorizontalAlignment(SwingConstants.CENTER);
        txtRoomCode.addFocusListener(new FocusAdapter() {
            @Override
            public void focusLost(FocusEvent e) {
                if (txtRoomCode.getText().isEmpty()) {
                    txtRoomCode.setText(PLACEHOLDER2);
                    txtRoomCode.setForeground(Color.GRAY);
                }
            }

            @Override
            public void focusGained(FocusEvent e) {
                if (txtRoomCode.getText().equals(PLACEHOLDER2)) {
                    txtRoomCode.setText("");
                    txtRoomCode.setForeground(Color.WHITE);
                }
            }
        });
        ((AbstractDocument) txtRoomCode.getDocument()).setDocumentFilter(new DocumentFilter() {
            public void insertString(DocumentFilter.FilterBypass fb, int offset, String text, AttributeSet attr) throws BadLocationException {
                fb.insertString(offset, text.toUpperCase(), attr);
            }
            public void replace(DocumentFilter.FilterBypass fb, int offset, int length, String text, AttributeSet attrs) throws BadLocationException {
                fb.replace(offset, length, text.toUpperCase(), attrs);
            }
        });

        this.add(txtRoomCode);

        lblStatus = new JLabel();
        lblStatus.setFont(new Font("Arial", Font.BOLD, 16));
        lblStatus.setBounds(0, 410, getWidth(), 30);
        lblStatus.setForeground(Color.WHITE);
        lblStatus.setBackground(GoMainFrame.COLOR_3);
        lblStatus.setText("Name must have 3-7 characters!");
        lblStatus.setHorizontalAlignment(SwingConstants.CENTER);
        lblStatus.setVerticalAlignment(SwingConstants.CENTER);
        lblStatus.setOpaque(true);
        this.add(lblStatus);

        btnJoin = new MaterialButton(
                "Join Room!",
                new Font("Arial", Font.BOLD, 20),
                centerX(getWidth(), BTN_WIDTH),
                480,
                BTN_WIDTH,
                BTN_HEIGHT
        );
        btnJoin.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseExited(MouseEvent e) {
                if (waiting) {
                    btnJoin.setBackground(Color.decode("#5E170C"));
                    btnJoin.setBorder(BorderFactory.createLineBorder(Color.decode("#DC000E"), 4));
                } else {
                    btnJoin.setBorder(BorderFactory.createLineBorder(GoMainFrame.COLOR_2, 4));
                }
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                if (waiting) {
                    btnJoin.setBackground(Color.decode("#DC000E"));
                    btnJoin.setBorder(BorderFactory.createLineBorder(Color.decode("#DC000E"), 4));
                } else {
                    btnJoin.setBorder(BorderFactory.createLineBorder(GoMainFrame.COLOR_2, 4));
                }
            }

            @Override
            public void mousePressed(MouseEvent e) {
                if (waiting) {
                    waiting = false;
                    stopConnection();
                    txtFirstName.setEnabled(true);
                    txtRoomCode.setEnabled(true);
                    btnJoin.setText("Join Room!");
                    btnJoin.setBackground(GoMainFrame.COLOR_2);
                    btnJoin.setBorder(BorderFactory.createLineBorder(GoMainFrame.COLOR_2, 4));
                    stopWaitingAnimation();
                    promptStatus(GoMainFrame.COLOR_3, "Name must have 3-7 characters!");
                } else if (!waiting) {
                    if (!validateForm()) {
                        return;
                    }

                    waiting = true;
                    startWaitingAnimation();
                    btnJoin.setText("Stop Waiting");
                    btnJoin.setBackground(Color.decode("#DC000E"));
                    btnJoin.setBorder(BorderFactory.createLineBorder(Color.decode("#DC000E"), 4));
                    txtFirstName.setEnabled(false);
                    txtRoomCode.setEnabled(false);

                    new Thread(() -> {
                        String IP = Networking.toTraditionalIPAddress(txtRoomCode.getText());
                        roomInfoClient = new RoomInfoClient(IP, GoMainFrame.ROOM_INFO_SERVER_PORT);
                        RoomModel roomModel = roomInfoClient.getRoomModel();
                        roomInfoClient.close();
                        if (roomModel == null) {
                            if (waiting) {
                                promptStatus(Color.RED, "Connection failed!");
                                waiting = false;
                                txtFirstName.setEnabled(true);
                                txtRoomCode.setEnabled(true);
                                stopConnection();
                                btnJoin.setText("Join Room!");
                                stopWaitingAnimation();
                                btnJoin.setBackground(GoMainFrame.COLOR_2);
                                btnJoin.setBorder(BorderFactory.createLineBorder(GoMainFrame.COLOR_2, 4));
                            }
                            return;
                        }

                        int response = JOptionPane.showConfirmDialog(parent, roomModel.toString(), "Room found!", JOptionPane.YES_NO_OPTION);
                        if (response == JOptionPane.YES_OPTION) {
                            String clientName = txtFirstName.getText();
                            GameSocket gameSocket = new GameSocket(IP, GoMainFrame.GAME_SERVER_PORT);
                            gameSocket.send(clientName);
                            {
                                BoardSize boardSize = roomModel.getBoardSize();

                                Player serverPlayerType = roomModel.getPlayerType();
                                Player playerType = serverPlayerType.isBlack() ? Player.WHITE : Player.BLACK;

                                String firstName, secondName;
                                String serverName = roomModel.getRoomOwnerName();
                                if (playerType.isBlack()) {
                                    firstName = clientName;
                                    secondName = serverName;
                                } else {
                                    firstName = serverName;
                                    secondName = clientName;
                                }
                                waiting = false;
                                parent.removeComponent("joinRoom");
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
                            waiting = false;
                            stopConnection();
                            txtFirstName.setEnabled(true);
                            btnJoin.setText("Join Room!");
                            btnJoin.setBackground(GoMainFrame.COLOR_2);
                            btnJoin.setBorder(BorderFactory.createLineBorder(GoMainFrame.COLOR_2, 4));
                            stopWaitingAnimation();
                            promptStatus(GoMainFrame.COLOR_3, "Name must have 3-7 characters!");
                        }
                        stopWaitingAnimation();
                    }).start();
                }
            }
        });
        this.add(btnJoin);
    }

    private boolean validateForm() {
        int firstLength = txtFirstName.getText().length();
        if (!(3 <= firstLength && firstLength <= 8) || txtFirstName.getText().equals(PLACEHOLDER)) {
            lblStatus.setBackground(Color.RED);
            lblStatus.setText("Name must have 3-7 characters!");
            return false;
        }
        if (!txtRoomCode.getText().matches("[A-P]{4}-[A-P]{4}") && 
                !txtRoomCode.getText().matches("[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}")) {
            lblStatus.setBackground(Color.RED);
            lblStatus.setText("Please enter a valid Room Code!");
            return false;
        }
        return true;
    }

    private int centerX(int W, int w) {
        return (W - w) / 2;
    }

    private void promptStatus(Color color, String text) {
        lblStatus.setForeground(Color.WHITE);
        lblStatus.setBackground(color);
        lblStatus.setText(text);
    }

    private void startWaitingAnimation() {
        String initialWaitingStatus = "Connecting to room";
        promptStatus(Color.decode("#1C9C81"), initialWaitingStatus);
        final int len = initialWaitingStatus.length();
        timer = new Timer(500, e -> {
            String waitingStatus = lblStatus.getText();
            waitingStatus += " .";
            if (waitingStatus.length() > len + (5 * 2)) {
                waitingStatus = waitingStatus.substring(0, len);
            }
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
        if (roomInfoClient != null) {
            roomInfoClient.close();
            roomInfoClient = null;
        }
    }
}
