package gomultiplayeronline;

import java.awt.Dimension;
import java.awt.FlowLayout;
import javax.swing.JButton;
import javax.swing.JPanel;
import main.GoMainFrame;

public class GoMultiOnMenu extends JPanel {
    
    GoMainFrame parent;
    JButton btnBack;
    JButton btnCreateRoom;
    JButton btnJoinRoom;
    
    public GoMultiOnMenu (GoMainFrame parent) {
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
     
        btnCreateRoom = new JButton();
        btnCreateRoom.setText("Create Room");
        btnCreateRoom.addActionListener(e -> {
            parent.addComponent("createRoom", new GoCreateRoom(parent));
            parent.changeSceneTo("createRoom");
        });
        this.add(btnCreateRoom);
        
        btnJoinRoom = new JButton();
        btnJoinRoom.setText("Join a Room");
        btnJoinRoom.addActionListener(e -> {
            parent.addComponent("joinRoom", new GoJoinRoom(parent));
            parent.changeSceneTo("joinRoom");
        });
        this.add(btnJoinRoom);
    }
}
