package menus;

import controls.MaterialButton;
import gomultiplayeroffline.GoMultiOffMenu;
import gomultiplayeronline.GoMultiOnMenu;
import gosingle.GoSingleMenu;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import main.GoMainFrame;

public class GoMainMenu extends JPanel {
    
    GoMainFrame parent;
    JLabel singleBtn;
    JLabel multiOffBtn;
    JLabel multiOnBtn;
    JLabel howToPlayBtn;
    JLabel aboutBtn;
    JLabel lblLeftInfo;
    JLabel lblRightInfo;
    JLabel footerLbl;
    PrettyBackground prettyBackground;
    
    private static final int BTN_WIDTH = 220;
    private static final int BTN_HEIGHT = 50;
    
    public GoMainMenu(GoMainFrame parent) {
        this.parent = parent;
        this.setLayout(null);
        this.setSize(new Dimension(GoMainFrame.FRAME_WIDTH, GoMainFrame.FRAME_HEIGHT));
        this.setBackground(GoMainFrame.COLOR_4);
        
        singleBtn = new MaterialButton(
                "Singleplayer",
                new Font("Arial", Font.BOLD, 20),
                centerX(getWidth(), BTN_WIDTH),
                240,
                BTN_WIDTH,
                BTN_HEIGHT
        );
        singleBtn.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                parent.addComponent("singleMenu", new GoSingleMenu(parent));
                parent.changeSceneTo("singleMenu");
            }
        });
        this.add(singleBtn);
        
        multiOffBtn = new MaterialButton(
                "Multiplayer (Local)",
                new Font("Arial", Font.BOLD, 20),
                centerX(getWidth(), BTN_WIDTH),
                325,
                BTN_WIDTH,
                BTN_HEIGHT
        );
        multiOffBtn.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                parent.addComponent("multiOffMenu", new GoMultiOffMenu(parent));
                parent.changeSceneTo("multiOffMenu");
            }
        });
        this.add(multiOffBtn);
        
        multiOnBtn = new MaterialButton(
                "Multiplayer (LAN)",
                new Font("Arial", Font.BOLD, 20),
                centerX(getWidth(), BTN_WIDTH),
                410,
                BTN_WIDTH,
                BTN_HEIGHT
        );
        multiOnBtn.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                parent.addComponent("multiOnMenu", new GoMultiOnMenu(parent));
                parent.changeSceneTo("multiOnMenu");
            }
        });
        this.add(multiOnBtn);
        
        final int BTN_WIDTH2 = BTN_WIDTH*3/4;
        howToPlayBtn = new MaterialButton(
                "How To Play",
                new Font("Arial", Font.BOLD, 20),
                centerX(getWidth(), BTN_WIDTH2)-(BTN_WIDTH2*3/5),
                490,
                BTN_WIDTH2,
                BTN_HEIGHT
        );
        howToPlayBtn.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                parent.addComponent("howToPlay", new HowToPlay(parent));
                parent.changeSceneTo("howToPlay");
            }
        });
        this.add(howToPlayBtn);
        
        aboutBtn = new MaterialButton(
                "About",
                new Font("Arial", Font.BOLD, 20),
                centerX(getWidth(), BTN_WIDTH2)+(BTN_WIDTH2*3/5),
                490,
                BTN_WIDTH2,
                BTN_HEIGHT
        );
        aboutBtn.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                parent.addComponent("about", new About(parent));
                parent.changeSceneTo("about");
            }
        });
        this.add(aboutBtn);

        String htmlRightInfo = "<html><p><em>"
                + "<span style=\"font-size: 28px;\">\"</span>The Traditional Go <br>"
                + "&nbsp;now comes with <br>"
                + "&nbsp;Brand New Feels!<span style=\"font-size: 28px;\">\"</span>"
                + "</em></p></html>";
        lblRightInfo = new JLabel(htmlRightInfo);
        lblRightInfo.setFont(new Font("Arial", Font.BOLD, 24));
        lblRightInfo.setForeground(GoMainFrame.COLOR_1);
        lblRightInfo.setBackground(GoMainFrame.COLOR_3);
//        lblRightInfo.setOpaque(true);
        lblRightInfo.setBounds(575, 250, 300, 200); // x = 25
        lblRightInfo.setHorizontalAlignment(SwingConstants.CENTER);
        lblRightInfo.setVerticalAlignment(SwingConstants.CENTER);
        this.add(lblRightInfo);
        
        String htmlLeftInfo = "<html><p><em>"
                + "<span style=\"font-size: 28px;\">\"</span>Enjoy your<br>"
                + "&nbsp;<span style=\"font-size: 38px;\">Go Adventure!</span><span style=\"font-size: 28px;\">\"</span>"
                + "</em></p></html>";
        lblLeftInfo = new JLabel(htmlLeftInfo);
        lblLeftInfo.setFont(new Font("Arial", Font.BOLD, 24));
        lblLeftInfo.setForeground(GoMainFrame.COLOR_1);
        lblLeftInfo.setBackground(GoMainFrame.COLOR_3);
//        lblLeftInfo.setOpaque(true);
        lblLeftInfo.setBounds(25, 220, 300, 200); // x = 25
        lblLeftInfo.setHorizontalAlignment(SwingConstants.CENTER);
        lblLeftInfo.setVerticalAlignment(SwingConstants.CENTER);
        this.add(lblLeftInfo);
        
        final int FOOTER_HEIGHT = 30;
        final int FOOTER_WIDTH = getWidth();
        String html = "<html><body valign=middle>"
                + "Made with "
                + "<img src=\""
                + GoMainMenu.class.getResource("/res/love.png")
                + "\"> by &nbsp;&nbsp;"
                + "<img src=\""
                + GoMainMenu.class.getResource("/res/github.png")
                + "\">"
                + " delanobgt"
                + "</body></html>";
        footerLbl = new JLabel(html);
        footerLbl.setOpaque(true);
        footerLbl.setForeground(Color.WHITE);
        footerLbl.setBackground(GoMainFrame.COLOR_3);
        footerLbl.setFont(new Font("Arial", Font.BOLD, 16));
        footerLbl.setBounds(0, getHeight()-FOOTER_HEIGHT, FOOTER_WIDTH, FOOTER_HEIGHT);
        footerLbl.setHorizontalAlignment(SwingConstants.CENTER);
        footerLbl.setVerticalAlignment(SwingConstants.CENTER);
        this.add(footerLbl);
        
        prettyBackground = new PrettyBackground();
        this.add(prettyBackground);
    }
    
    private int centerX(int W, int w) {
        return (W-w)/2;
    }
}
