package menus;

import java.awt.AlphaComposite;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.util.List;
import main.GoMainFrame;

public class Circle {
    
    private int r;
    private int c;
    private double radius;
    private double speedRadius;
    private int hp = 500;
    private boolean growing = true;
    private boolean alive = true;
    
    public Circle(int r, int c) {
        this.r = r;
        this.c = c;
        this.radius = 1;
        this.speedRadius = 0.02;
    }
    
    public void update(List<Circle> circleList) {
        if (!alive) {
            return;
        } else if (hp <= 0) {
            alive = false;
        } else if (!growing) {
            hp -= 1;
        } else if (isHurt(circleList)) {
            growing = false;
        } else {
            this.radius += speedRadius;
        }
    }
    
    public void draw(Graphics2D g) {
        g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, (float) (hp/500.0)));
        g.setColor(GoMainFrame.COLOR_3);
        g.fillOval((int)(c-radius), (int)(r-radius), (int)(2*radius), (int)(2*radius));
        
        g.setStroke(new BasicStroke(2));
        g.setColor(GoMainFrame.COLOR_2);
        g.drawOval((int)(c-radius), (int)(r-radius), (int)(2*radius), (int)(2*radius));
    }
    
    // sama aja artinya dengan menyentuh atau bertabrakan dengan Circle lain
    public boolean isHurt(List<Circle> circleList) {
        for (Circle circle : circleList) {
            if (circle.hp <= 350) continue;
            if (this.equals(circle)) continue;
            if (this.radius()+this.speedRadius+circle.radius()+circle.speedRadius >
                    Math.hypot(this.r()-circle.r(), this.c()-circle.c())) {
                return true;
            }
        }
        return false;
    }
    
    public int r() {
        return r;
    }
    public int c() {
        return c;
    }
    public double radius() {
        return radius;
    }
    public double speedRadius() {
        return speedRadius;
    }
    public boolean isAlive() {
        return alive;
    }
    
    @Override
    public boolean equals(Object obj) {
        Circle circle = (Circle) obj;
        return this.r == circle.r() && this.c == circle.c() && this.radius == circle.radius();
    }
}
