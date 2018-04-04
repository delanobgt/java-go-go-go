package enums;

public enum Player {
    BLACK(0),
    WHITE(1);
    
    private final int type;
    Player(int type) { this.type = type; }
    public int type() { return type; }
    public boolean equals(Player player) {
        return this.type == player.type();
    }
    public boolean isBlack() {
        return this.type == 0;
    }
    public boolean isWhite() {
        return this.type == 1;
    }
    public String toString() {
        if (this.isBlack()) return "BLACK";
        else return "WHITE";
    }
}
