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

public class HowToPlay extends JPanel {

    GoMainFrame parent;
    JLabel lblTitle;
    JLabel btnBack;
    JLabel lblHowToPlay;

    public HowToPlay(GoMainFrame parent) {
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

        lblTitle = new JLabel("How To Play");
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
                + "  <p>Players take turns, placing one of their stones on a vacant point at each turn, with Black playing first. Note that stones\n"
                + "    are placed on the intersections of the lines rather than in the squares and once played stones are not moved. However\n"
                + "    they may be captured, in which case they are removed from the board, and kept by the capturing player as prisoners.</p>\n"
                + "\n"
                + "  <h2 style=\"color: rgb(1,175,184);\">4 Basic Rules</h2>\n"
                + "  <ol>\n"
                + "    <li>\n"
                + "      <p>Once a stone is placed, it cannot be moved anywhere else.</p>\n"
                + "    </li>\n"
                + "    <li>\n"
                + "      <p>Stone with no liberties are captured by opponent.\n"
                + "        <br> Stone liberties are defined as empty space around its edges:</p>\n"
                + "      <ul>\n"
                + "        <li>Corner stones have 2 liberties</li>\n"
                + "        <li>Edge stones have 3 liberties</li>\n"
                + "        <li>Other stones have 4 liberties</li>\n"
                + "      </ul>\n"
                + "    </li>\n"
                + "    <li>\n"
                + "      <p>Ko Rule forbid player from replicating 2 previous game state</p>\n"
                + "    </li>\n"
                + "    <li>\n"
                + "      <p>The end of a game is indicated by 2 consecutive player's passes</p>\n"
                + "    </li>\n"
                + "  </ol>\n"
                + "\n"
                + "  <h2 style=\"color: rgb(1,175,184);\">Scoring</h2>\n"
                + "  <p>The total score of a player is the sum of captured opponent stone and territory area</p>\n"
                + "  <p>Territory is defined as the area surrounded by only one type of stone</p>\n"
                + "</body>\n"
                + "\n"
                + "</html>";
        lblHowToPlay = new JLabel();
        lblHowToPlay.setFont(new Font("Arial", Font.BOLD, 16));
        lblHowToPlay.setBounds(centerX(getWidth(), getWidth() - 150), 110, getWidth() - 150, getHeight() - 110);
        lblHowToPlay.setText(html);
        lblHowToPlay.setForeground(Color.WHITE);
        lblHowToPlay.setBackground(GoMainFrame.COLOR_4);
        lblHowToPlay.setHorizontalAlignment(SwingConstants.CENTER);
        lblHowToPlay.setVerticalAlignment(SwingConstants.CENTER);
        lblHowToPlay.setOpaque(true);
        this.add(lblHowToPlay);
    }

    private int centerX(int W, int w) {
        return (W - w) / 2;
    }
}
