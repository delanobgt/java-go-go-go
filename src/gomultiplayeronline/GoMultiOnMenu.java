package gomultiplayeronline;

import controls.ControlButton;
import controls.MaterialButton;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import main.GoMainFrame;

public class GoMultiOnMenu extends JPanel {
    
    GoMainFrame parent;
    JLabel lblTitle;
    JLabel btnBack;
    JLabel btnCreateRoom;
    JLabel btnJoinRoom;
    JLabel lblInfo;
    private static final int BTN_WIDTH = 250;
    private static final int BTN_HEIGHT = 70;
    
    public GoMultiOnMenu (GoMainFrame parent) {
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
            public void mouseClicked(MouseEvent e) {
                parent.changeSceneTo("mainMenu");
            }
        });
        this.add(btnBack);
        
        lblTitle = new JLabel("Online Multiplayer (LAN)");
        lblTitle.setFont(new Font("Arial", Font.BOLD, 48));
        lblTitle.setForeground(GoMainFrame.COLOR_2);
        lblTitle.setBackground(GoMainFrame.COLOR_3);
        lblTitle.setBounds(0, 0, getWidth(), 110);
        lblTitle.setOpaque(true);
        lblTitle.setHorizontalAlignment(SwingConstants.CENTER);
        lblTitle.setVerticalAlignment(SwingConstants.CENTER);
        this.add(lblTitle);
     
        btnCreateRoom = new MaterialButton(
                "Create Room",
                new Font("Arial", Font.BOLD, 24),
                centerX(getWidth(), BTN_WIDTH),
                200,
                BTN_WIDTH,
                BTN_HEIGHT
        );
        btnCreateRoom.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                parent.addComponent("createRoom", new GoCreateRoom(parent));
                parent.changeSceneTo("createRoom");
            }
        });
        this.add(btnCreateRoom);
        
        btnJoinRoom = new MaterialButton(
                "Join Room",
                new Font("Arial", Font.BOLD, 24),
                centerX(getWidth(), BTN_WIDTH),
                330,
                BTN_WIDTH,
                BTN_HEIGHT
        );
        btnJoinRoom.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                parent.addComponent("joinRoom", new GoJoinRoom(parent));
            parent.changeSceneTo("joinRoom");
            }
        });
        this.add(btnJoinRoom);
        
        String info = 
                "<html>\n" +
                "  <ul>\n" +
                "    <li>Both players must be on the same network</li>\n" +
                "    <li>One player Create Room and the other Join Room</li>\n" +
                "  </ul>\n" +
                "</html>";
        lblInfo = new JLabel(info);
        lblInfo.setFont(new Font("Arial", Font.PLAIN, 18));
        lblInfo.setForeground(Color.WHITE);
        lblInfo.setBounds(centerX(getWidth(), 3*BTN_WIDTH)+BTN_WIDTH*3/5, 450, 3*BTN_WIDTH, 100);
        this.add(lblInfo);
    }
    
    private int centerX(int W, int w) {
        return (W-w)/2;
    }
    
}
