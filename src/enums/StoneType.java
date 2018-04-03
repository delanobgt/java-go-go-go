package enums;

public enum StoneType {
    BLACK(0),
    WHITE(1),
    EMPTY(-1);
    
    private final int type;
    StoneType(int type) { this.type = type; }
    public int type() { return type; }
    public boolean equals(StoneType otherStone) {
        return this.type() == otherStone.type();
    }
    public boolean isBlack() {
        return this.type == 0;
    }
    public boolean isWhite() {
        return this.type == 1;
    }
    public boolean isEmpty() {
        return this.type == -1;
    }
    @Override
    public String toString() {
        if (this.isBlack()) return "0";
        if (this.isWhite()) return "1";
        return "-1";
    }
}
