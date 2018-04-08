package enums;

public enum Difficulty {
    EASY(1),
    MEDIUM(2),
    HARD(3);
    
    private final int level;
    Difficulty(int level) { this.level = level; }
    public int level() { return level; }
    public boolean equals(Difficulty otherStone) {
        return this.level() == otherStone.level();
    }
    public boolean isEasy() {
        return this.level == 1;
    }
    public boolean isMedium() {
        return this.level == 2;
    }
    public boolean isHard() {
        return this.level == 3;
    }
    @Override
    public String toString() {
        if (this.isEasy()) return "EASY";
        if (this.isMedium()) return "MEDIUM";
        return "HARD";
    }
}
