package models;

import enums.StoneType;
import java.util.Arrays;
import java.util.List;

public class ImmutableBoard {
    
    private final StoneType[][] board;
    
    public ImmutableBoard(StoneType[][] board) {
        this.board = getClone(board);
    }
    public ImmutableBoard(ImmutableBoard board) {
        this.board = getConverted(board);
    }
    
    public ImmutableBoard setStoneAt(int r, int c, StoneType stoneType) {
        StoneType[][] newBoard = getClone(board);
        newBoard[r][c] = stoneType;
        return new ImmutableBoard(newBoard);
    }
    public ImmutableBoard removeStoneAt(int r, int c) {
        StoneType[][] newBoard = getClone(board);
        newBoard[r][c] = StoneType.EMPTY;
        return new ImmutableBoard(newBoard);
    }
    public ImmutableBoard removeAllStonesExceptOne(List<List<Point>> connStonesList, Point p) {
        StoneType[][] newBoard = getClone(board);
        for (List<Point> connStones : connStonesList) {
            if (!connStones.contains(p)) {
                for (Point stone : connStones)
                    newBoard[stone.r()][stone.c()] = StoneType.EMPTY;
            }
        }
        return new ImmutableBoard(newBoard);
    }
    public StoneType getStoneAt(int r, int c) {
        return board[r][c];
    }
    public int getSize() {
        return board.length;
    }
    public String getHash() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board.length; j++) {
                sb.append(board[i][j].toString());
            }
        }
        return sb.toString();
    }
    
    private static StoneType[][] getClone(StoneType[][] board) {
        StoneType[][] newBoard = new StoneType[board.length][];
        for (int i = 0; i < board.length; i++) {
            newBoard[i] = Arrays.copyOf(board[i], board[i].length);
        }
        return newBoard;
    }
    private static StoneType[][] getConverted(ImmutableBoard board) {
        int size = board.getSize();
        StoneType[][] newBoard = new StoneType[size][size];
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                newBoard[i][j] = board.getStoneAt(i, j);
            }
        }
        return newBoard;
    }
}
