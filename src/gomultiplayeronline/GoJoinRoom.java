/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gomultiplayeronline;

import enums.BoardSize;
import enums.Player;
import java.awt.Dimension;
import java.awt.FlowLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import main.GoMainFrame;
import models.RoomModel;

public class GoJoinRoom extends JPanel {
    
    GoMainFrame parent;
    JButton btnBack;
    
    JLabel lblFirstName;
    JTextField txtFirstName;
    
    JLabel lblRoomCode;
    JTextField txtRoomCode;
    
    JLabel lblStatus;
    JButton btnJoin;
    
    RoomInfoClient roomInfoClient;
    
    public GoJoinRoom (GoMainFrame parent) {
        this.parent = parent;
        this.setLayout(new FlowLayout());
        this.setSize(new Dimension(GoMainFrame.FRAME_WIDTH, GoMainFrame.FRAME_HEIGHT));
        this.setPreferredSize(new Dimension(GoMainFrame.FRAME_WIDTH, GoMainFrame.FRAME_HEIGHT));
        
        btnBack = new JButton();
        btnBack.setText("< BACK");
        btnBack.addActionListener(e -> {
            parent.changeSceneTo("multiOnMenu");
        });
        this.add(btnBack);
        
        lblFirstName = new JLabel();
        lblFirstName.setText("Player name: ");
        this.add(lblFirstName);
        
        txtFirstName = new JTextField();
        txtFirstName.setPreferredSize(new Dimension(100, 30));
        this.add(txtFirstName);
        
        lblRoomCode = new JLabel();
        lblRoomCode.setText("Room Code: ");
        this.add(lblRoomCode);
        
        txtRoomCode = new JTextField();
        txtRoomCode.setPreferredSize(new Dimension(100, 30));
        this.add(txtRoomCode);
        
        lblStatus = new JLabel();
        this.add(lblStatus);
        
        btnJoin = new JButton();
        btnJoin.setText("Join Room!");
        btnJoin.addActionListener(e -> {
            if (!validateForm()) return;
            
            txtFirstName.setEditable(false);
            txtRoomCode.setEditable(false);
            btnJoin.setEnabled(false);
            
            new Thread(() -> {
                String IP = txtRoomCode.getText();
                roomInfoClient = new RoomInfoClient(IP, GoMainFrame.ROOM_INFO_SERVER_PORT);
                RoomModel roomModel= roomInfoClient.getRoomModel();
                roomInfoClient.close();
                
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
                }
                txtFirstName.setEditable(true);
                txtRoomCode.setEditable(true);
                btnJoin.setEnabled(true);
            }).start();
        });
        this.add(btnJoin);
    }
    
    private boolean validateForm() {
        int firstLength = txtFirstName.getText().length();
        if ( !(3 <= firstLength && firstLength <= 8) ) {
            lblStatus.setText("Name must be between 3 to 8 characters!");
            return false;
        }
        if ( txtRoomCode.getText().length() == 0 ) {
            lblStatus.setText("Please fill in the Room Code!");
            return false;
        }
        return true;
    }
    
}
