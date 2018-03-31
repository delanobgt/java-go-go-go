package gomultiplayeroffline;

import models.GoModel;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.util.List;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.Timer;
import main.GoMainFrame;
import models.Point;

public class GoMultiOffCanvas extends JPanel {
    
    private final int CELL_SIZE;
    private static final int CELL_MARGIN = 2;
    private static final int ANIMATION_DELAY = 10;
    private int canvasWidth;
    private int canvasHeight;
    private GoModel goModel;
    private Timer timer;
    
    private volatile int mouseX = -1;
    private volatile int mouseY = -1;
    
    private MouseAdapter mouseAdapter = new MouseAdapter() {
        @Override
        public void mousePressed(MouseEvent e) {
            handleUserClick(e.getX(), e.getY());
        }
        @Override
        public void mouseExited(MouseEvent e) {
            mouseX = -1;
            mouseY = -1;
        }
    };
    private MouseMotionAdapter mouseMotionAdapter = new MouseMotionAdapter() {
        @Override
        public void mouseMoved(MouseEvent e) {
            mouseX = e.getX();
            mouseY = e.getY();
        }        
    };
    
    public GoMultiOffCanvas(GoModel goModel) {
        this.goModel = goModel;
        canvasWidth = canvasHeight = GoMainFrame.FRAME_HEIGHT;
        CELL_SIZE = canvasWidth/(goModel.getBoardSize()+2);
        this.setLayout(null);
        this.setPreferredSize(new Dimension(canvasWidth, canvasHeight));
        this.setSize(canvasWidth, canvasHeight);
        this.setLocation(GoMainFrame.FRAME_WIDTH-GoMainFrame.FRAME_HEIGHT, 0);
        this.setFocusable(true);
        
        this.addMouseListener(mouseAdapter);
        this.addMouseMotionListener(mouseMotionAdapter);
        
        timer = new Timer(ANIMATION_DELAY, (e) -> {
            repaint();
        });
        timer.start();
        
        // add Legend labels
        for (int r = 1; r <= goModel.getBoardSize(); r++) {
            // add alphabets coordinates label
            JLabel label = new LegendJLabel(r*CELL_SIZE, 0, Character.toString((char)('A'+r-1)));
            this.add(label);
            JLabel label2 = new LegendJLabel(r*CELL_SIZE, (goModel.getBoardSize()+1)*CELL_SIZE, Character.toString((char)('A'+r-1)));
            this.add(label2);
            // add digit coordinates label
            JLabel label3 = new LegendJLabel(0, r*CELL_SIZE, Integer.toString(r));
            this.add(label3);
            JLabel label4 = new LegendJLabel((goModel.getBoardSize()+1)*CELL_SIZE, r*CELL_SIZE, Integer.toString(r));
            this.add(label4);
        }
    }

    class LegendJLabel extends JLabel {
        public LegendJLabel(int x, int y, String text) {
            this.setFont(new Font("Arial", Font.BOLD, 18));
            this.setBounds(x, y, CELL_SIZE, CELL_SIZE);
            this.setForeground(Color.WHITE);
            this.setHorizontalAlignment(SwingConstants.CENTER);
            this.setVerticalAlignment(SwingConstants.CENTER);
            this.setText(text);
        }
    }
    
    private void handleUserClick(int userX, int userY) {
        // translate coordinate backward (-1, -1)
        int userR = userY/CELL_SIZE-1;
        int userC = userX/CELL_SIZE-1;
        if (! (0 <= userR && userR < goModel.getBoardSize() &&
                0 <= userC && userC < goModel.getBoardSize()) ) return;
        Point userPoint = new Point(userR, userC);
        
        if (goModel.isOccupiedAt(userR, userC)) {
            JOptionPane.showMessageDialog(this, "Cell occupied!");
            return;
        }
        goModel.setStoneAt(userR, userC, goModel.getCurrentStoneType());
        
        List<List<Point>> connStonesList = goModel.getListOfDeadStones();
        //is move suicidal ?
        if (connStonesList.size() == 1 &&
            connStonesList.get(0).contains(userPoint) ) {
                goModel.removeStoneAt(userR, userC);
                JOptionPane.showMessageDialog(this, "Move is suicidal!");
                return;
        }
        //remove other zero-liberty stones, except current attacking stone
        for (List<Point> connStones : connStonesList) {
            if (!connStones.contains(userPoint))
                goModel.removeAllStones(connStones);
        }
        
        goModel.toggleTurn();
    }
    
    @Override
    protected void paintComponent(Graphics oldG) {
        super.paintComponent(oldG);
        Graphics2D g = (Graphics2D) oldG;
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        
        //paint background
        g.setColor(new Color(186, 135, 78)); //brown color
        g.fillRect(0, 0, canvasWidth, canvasHeight);
        
        //paint board grid and legends
        for (int r = 0; r <= goModel.getBoardSize()+1; r++) {
            for (int c = 0; c <= goModel.getBoardSize()+1; c++) {
                // print board grid
                if (1 <= c && c < goModel.getBoardSize()
                            && 1 <= r && r < goModel.getBoardSize()) {
                    g.setColor(Color.BLACK);
                    g.drawRect(
                            (r*CELL_SIZE)+(CELL_SIZE/2),
                            (c*CELL_SIZE)+(CELL_SIZE/2),
                            CELL_SIZE,
                            CELL_SIZE
                    );
                }
            }
        }
        
        //paint mouse highlight
        {
            int r = mouseY/CELL_SIZE, c = mouseX/CELL_SIZE;
            // only paint shadow at valid grids
            if ( (1 <= r && r <= goModel.getBoardSize() &&
                    1 <= c && c <= goModel.getBoardSize()) ) {
                if (goModel.getTurn().isBlack()) {
                    g.setColor(new Color(0, 0, 0, 120));
                } else {
                    g.setColor(new Color(255, 255, 255, 120));
                }
                drawStone(g, r, c);
            }
        }
            
        //paint stones
        for (int r = 0; r < goModel.getBoardSize(); r++) {
            for (int c = 0; c < goModel.getBoardSize(); c++) {
                if (goModel.getStoneAt(r, c).isBlack()) {
                    g.setColor(Color.BLACK);
                    drawStone(g, r+1, c+1); // translate coordinate forward (+1,+1)
                } else if (goModel.getStoneAt(r, c).isWhite()) {
                    g.setColor(Color.WHITE);
                    drawStone(g, r+1, c+1); // translate coordinate forward (+1,+1)
                }
            }
        }
    }
    
    private void drawStone(Graphics2D g, int r, int c) {
        g.fillOval(
            c*CELL_SIZE+CELL_MARGIN,
            r*CELL_SIZE+CELL_MARGIN,
            CELL_SIZE-(2*CELL_MARGIN),
            CELL_SIZE-(2*CELL_MARGIN)
        );
    }
    
}
