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
}
