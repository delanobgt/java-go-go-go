package enums;

/**
 *
 * @author irvin
 */
public enum BoardSize {
    SMALL(9),
    MEDIUM(13),
    LARGE(19);
    
    private final int size;
    BoardSize(int size) { this.size = size; }
    public int size() { return size; }
    
    @Override
    public String toString() {
        return String.format("(%d)", size);
    }
    
    public static BoardSize fromSize(int n) {
        if (n == SMALL.size) return SMALL;
        if (n == MEDIUM.size) return MEDIUM;
        return LARGE;
    }
}
