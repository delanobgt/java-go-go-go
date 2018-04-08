package models;

public class Point {
    private int r;
    private int c;
    
    public Point(int r, int c) {
        this.r = r;
        this.c = c;
    }
    
    public int r() {
        return r;
    }
    
    public int c() {
        return c;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Point){
            Point point = (Point) obj;
            return this.r == point.r && this.c == point.c;
        }
        return false;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 17 * hash * this.r + this.c;
        return hash;
    }
    
    @Override
    public String toString() {
        return "("+r+","+c+")";
    }
}

