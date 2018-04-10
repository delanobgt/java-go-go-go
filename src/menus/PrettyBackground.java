package menus;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.imageio.ImageIO;
import javax.swing.JPanel;
import javax.swing.Timer;
import main.GoMainFrame;
import models.Point;

public class PrettyBackground extends JPanel {

    private final static int ANIMATION_DELAY = 5;

    private BufferedImage buffImg;
    private List<Point> whitePoints = new ArrayList<>();
    private List<Circle> circleList = new ArrayList<>();
    private Timer timer;
    
    public PrettyBackground() {
        this.setLocation(0, 0);
        this.setSize(new Dimension(GoMainFrame.FRAME_WIDTH, 200));
        this.setPreferredSize(new Dimension(GoMainFrame.FRAME_WIDTH, 200));
        this.setBackground(GoMainFrame.COLOR_4);

        buffImg = loadBufferedImage("/res/gogogo.jpg");
        whitePoints = findAllWhiteCoords(buffImg);

        timer = new Timer(ANIMATION_DELAY, e -> {
            Point newPoint;
            Circle newCircle;
            do {
                newPoint = whitePoints.get((int) (Math.random() * whitePoints.size()));
                newCircle = new Circle(newPoint.r(), newPoint.c());
            } while (newCircle.isHurt(circleList));
            circleList.add(newCircle);

            for (Circle circle : circleList) {
                circle.update(circleList);
            }
            for (int i = circleList.size() - 1; i >= 0; i--) {
                if (!circleList.get(i).isAlive()) {
                    circleList.remove(i);
                }
            }
            repaint();
        });
        timer.start();
    }
    
    private BufferedImage loadBufferedImage(String URL) {
        try {
            return ImageIO.read(PrettyBackground.class.getResource(URL));
        } catch (IOException ex) {
            System.out.println(ex);
            return null;
        }
    }

    private List<Point> findAllWhiteCoords(BufferedImage buffImg) {
        List<Point> list = new ArrayList<>();

        int imgWidth = buffImg.getWidth();
        int imgHeight = buffImg.getHeight();

        for (int r = 0; r < imgHeight; r++) {
            for (int c = 0; c < imgWidth; c++) {
                Color curColor = new Color(buffImg.getRGB(c, r));
                if (curColor.getRed() >= 230 && curColor.getGreen() >= 230 && curColor.getBlue() >= 230) {
                    list.add(new Point(r, c));
                }
            }
        }

        return list;
    }

    @Override
    protected void paintComponent(Graphics oldG) {
        super.paintComponent(oldG);
        Graphics2D g = (Graphics2D) oldG;
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        
        g.setColor(GoMainFrame.COLOR_2);
        g.fillRect(0, 0, getWidth(), 10);
        
        g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, (float) Math.max(0, (150/500.0))));
        g.setColor(GoMainFrame.COLOR_3);
        g.fillRect(0, 0, getWidth(), getHeight());
        
//        g.drawImage(buffImg, 0, 0, null);
        for (Circle circle : circleList) {
            circle.draw(g);
        }
    }

}
