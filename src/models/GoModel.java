package models;

import enums.BoardSize;
import enums.Player;
import enums.StoneType;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class GoModel {
    
    private final int boardSize;
    private Player turn = Player.BLACK;
    private StoneType[][] board;
    
    public GoModel(BoardSize boardSize) {
        this.boardSize = boardSize.size();
        board = new StoneType[this.boardSize][this.boardSize];
        for (int i = 0; i < board.length; i++)
            Arrays.fill(board[i], StoneType.EMPTY);
    }
    
    public void toggleTurn() {
        turn = turn.isBlack() ? Player.WHITE : Player.BLACK;
    }
    public Player getTurn() {
        return turn;
    }
    public StoneType getCurrentStoneType() {
        return turn.isBlack() ? StoneType.BLACK : StoneType.WHITE;
    }
    
    public boolean isOccupiedAt(int row, int col) {
        return !board[row][col].equals(StoneType.EMPTY);
    }
    
    public StoneType getStoneAt(int row, int col) {
        return board[row][col];
    }
    
    public void setStoneAt(int row, int col, StoneType stoneType) {
        board[row][col] = stoneType;
    }
    
    private boolean visit[][];
    private boolean clean[][];
    private static final int[] DIR_R = {1,-1, 0, 0};
    private static final int[] DIR_C = {0, 0, 1,-1};
    private int _getLibertyCountAt(int row, int col, StoneType stoneType, List<Point> connectedStones) {
        if (!(0 <= row && row < boardSize)) return 0; //out of bound
        if (!(0 <= col && col < boardSize)) return 0; //out of bound
        if (visit[row][col]) return 0;  //sudah pernah visit, termintate
        visit[row][col] = true; //flag sudah visit
        if (board[row][col].isEmpty()) return 1; //ketemu liberty, tambah 1
        if (!board[row][col].equals(stoneType)) return 0; //stone tidak sejenis, terminate
        
        if (connectedStones != null) { //apabila caller minta connectedStones
            if (clean[row][col]) return 0; //sudah clean, terminate
            clean[row][col] = true;
            connectedStones.add(new Point(row, col)); //push sebuah connectedStone
        }
        
        int libertyTotal = 0;
        for (int i = 0; i < 4; i++) {
            libertyTotal += _getLibertyCountAt(row+DIR_R[i], col+DIR_C[i], stoneType, connectedStones);
        }
        return libertyTotal;
    }
    public int getLibertyCountAt(int row, int col, StoneType stoneType) {
        unvisit();
        return _getLibertyCountAt(row, col, stoneType, null);
    }
    private void unvisit() {
        visit = new boolean[boardSize][boardSize];
    }
    private void dirtify() {
        clean = new boolean[boardSize][boardSize];
    }
    public List<List<Point>> getListOfDeadStones() {
        List<List<Point>> resultList = new ArrayList<>();
        dirtify();
        for (int r = 0; r < boardSize; r++) {
            for (int c = 0; c < boardSize; c++) {
                List<Point> lst = new ArrayList<>();
                unvisit();
                int totalLiberty = _getLibertyCountAt(r, c, board[r][c], lst);
                if (totalLiberty == 0 && !lst.isEmpty()) {
                    resultList.add(lst);
                }
            }
        }
        return resultList;
    }
    
    public void removeStoneAt(int row, int col) {
        board[row][col] = StoneType.EMPTY;
    }
    public void removeAllStones(List<Point> lst) {
        for (Point coord : lst) {
            removeStoneAt(coord.r(), coord.c());
        }
    }
    
    public int getBoardSize() {
        return boardSize;
    }
}
