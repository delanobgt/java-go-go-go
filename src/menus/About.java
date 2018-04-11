package menus;

import controls.ControlButton;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import main.GoMainFrame;

public class About extends JPanel {

    GoMainFrame parent;
    JLabel lblTitle;
    JLabel btnBack;
    JLabel lblAbout;

    public About(GoMainFrame parent) {
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

        lblTitle = new JLabel("About");
        lblTitle.setFont(new Font("Arial", Font.BOLD, 48));
        lblTitle.setForeground(GoMainFrame.COLOR_2);
        lblTitle.setBackground(GoMainFrame.COLOR_3);
        lblTitle.setBounds(0, 0, getWidth(), 110);
        lblTitle.setOpaque(true);
        lblTitle.setHorizontalAlignment(SwingConstants.CENTER);
        lblTitle.setVerticalAlignment(SwingConstants.CENTER);
        this.add(lblTitle);

        String html = "<html>\n"
                + "\n"
                + "<body>\n"
                + "\n"
                + "  <p>\n"
                + "    <strong><span style=\"color: rgb(1,175,184);\">Go UPH Go</span></strong> is a board game based on one of the oldest yet interesting Chinese traditional board game - The Game of Go.<br>\n"
                + "    <br> This game supports several modes. Those are:\n"
                + "    <ul>\n"
                + "      <li>Singleplayer (versus AI)</li>\n"
                + "      <li>Local Multiplayer (on the same machine)</li>\n"
                + "      <li>LAN\n"
                + "          Multiplayer (between machines in the same network)</li>\n"
                + "    </ul>\n"
                + "  </p>\n"
                + "  <p>This game is made by &nbsp; <img src=\""
                + About.class.getResource("/res/github.png")
                + "\"> delanobgt &nbsp; for his 2nd Term Final Project</p>\n"
                + "\n"
                + "</body>\n"
                + "\n"
                + "</html>";
        lblAbout = new JLabel();
        lblAbout.setFont(new Font("Arial", Font.BOLD, 16));
        lblAbout.setBounds(centerX(getWidth(), getWidth() - 300), 110, getWidth() - 300, getHeight() - 110);
        lblAbout.setText(html);
        lblAbout.setForeground(Color.WHITE);
        lblAbout.setBackground(GoMainFrame.COLOR_4);
        lblAbout.setHorizontalAlignment(SwingConstants.CENTER);
        lblAbout.setVerticalAlignment(SwingConstants.CENTER);
        lblAbout.setOpaque(true);
        this.add(lblAbout);
    }

    private int centerX(int W, int w) {
        return (W - w) / 2;
    }
}
