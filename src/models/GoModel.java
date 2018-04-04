package models;

import enums.BoardSize;
import enums.Player;
import enums.StoneType;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;

public class GoModel implements Serializable {
    
    private final int boardSize;
    private Player turn = Player.BLACK;
    private Player win = null;
    private StoneType[][] board;
    private int passCounter = 0;
    private volatile boolean boardAltered = false;
    private List<Point> blackTerritoryList = null;
    private List<Point> whiteTerritoryList = null;
    private int blackCapturedScore = 0;
    private int whiteCapturedScore = 0;
    private Point lastMovePoint = null;
    
    public GoModel(BoardSize boardSize) {
        this.boardSize = boardSize.size();
        board = new StoneType[this.boardSize][this.boardSize];
        backupBoard = new StoneType[this.boardSize][this.boardSize];
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
    
    // win & lose stuff
    public Player getWin() { return win; }
    private void setWin(Player win) { this.win = win; }
    public void winBlack() { setWin(Player.BLACK); }
    public void winWhite() { setWin(Player.WHITE); }
    
    // pass stuff
    public int getPassCounter() { return passCounter; }
    public void addPassCounter() { this.passCounter += 1; }
    public void resetPassCounter() {this.passCounter = 0; }
    
    // surrender stuff
    public void surrenderedBy(Player player) {
        if (player.isBlack()) winWhite();
        else winBlack();
    }
    
    // territory stuff
    public boolean isBoardAltered() {
        return boardAltered;
    }
    public void setBoardAltered(boolean boardAltered) {
        this.boardAltered = boardAltered;
    }
    
    private Map<Point, Integer> freqOfPoint;
    private Map<String, List<Point> > stoneToList;
    private String _convertListToKey(List<Point> list) {
        list.sort( (a, b) -> (a.r()*31+a.c()*37)-(b.r()*31+b.c()*37) );
        StringBuilder sb = new StringBuilder();
        for (Point p : list) {
            sb.append(p.r()).append(p.c());
        }
        return sb.toString();
    }
    private int _scanTerritoryAt(int row, int col, StoneType stoneType, List<Point> connectedTerritories) {
        if (!(0 <= row && row < boardSize)) return 0; //out of bound
        if (!(0 <= col && col < boardSize)) return 0; //out of bound
        //ketemu stone sejenis, cek apakah sudah benturan 1x, 2x, 3x ?
        if (board[row][col].equals(stoneType)) {
            Point p = new Point(row, col);
            if (!freqOfPoint.containsKey(p)) freqOfPoint.put(p, 1);
            else freqOfPoint.put(p, freqOfPoint.get(p)+1);
            // crazy chain of important if statements
            if ( ((p.r()==0 && p.c()==0) || (p.r()==0 && p.c()==boardSize-1)|| 
                  (p.r()==boardSize-1 && p.c()==0) || (p.r()==boardSize-1 && p.c()==boardSize-1)) &&
                  freqOfPoint.get(p) > 1 ) {
                return -(20*20);
            } else if ((p.r()==0 || p.r()==boardSize-1 || p.c()==0 || p.c()==boardSize-1) &&
                    freqOfPoint.get(p) > 2){
                return -(20*20);
            } else if (freqOfPoint.get(p) > 3) {
                return -(20*20);
            } else return 1;
        }
        if (visit[row][col]) return 0;  //sudah pernah visit, termintate
        visit[row][col] = true; //flag sudah visit
        
        // bukan ketemu EMPTY cell, tapi ketemu stone jenis lawan, return INF
        if (!board[row][col].equals(StoneType.EMPTY)) return -(20*20); 
        
        if (connectedTerritories != null) { //apabila caller minta connectedStones
            if (clean[row][col]) return 0; //sudah clean, terminate
            clean[row][col] = true;
            connectedTerritories.add(new Point(row, col)); //push sebuah connectedStone
        }
        
        int commonStoneCount = 0;
        for (int i = 0; i < 4; i++) {
            commonStoneCount += _scanTerritoryAt(row+DIR_R[i], col+DIR_C[i], stoneType, connectedTerritories);
        }
        return commonStoneCount;
    }
    public boolean scanTerritoryAt(int row, int col, StoneType stoneType) {
        List<Point> connectedTerritories = new ArrayList<>();
        unvisit();
        freqOfPoint = new HashMap<>();
        int commonStoneCount = _scanTerritoryAt(row, col, stoneType, connectedTerritories);
        if (commonStoneCount > 0) {     // if it is a "valid territory"
            String existKey = 
                    (stoneType.isBlack() ? "BLACK-" : "WHITE-") +
                    _convertListToKey(new ArrayList<>(freqOfPoint.keySet()));
            // if current connectedTerritories is larger than newly found one
            if (!stoneToList.containsKey(existKey) || 
                    stoneToList.get(existKey).size() > connectedTerritories.size()) {
                stoneToList.put(existKey, connectedTerritories);
            }
            return true;
        } else {            // if it is a "neutral territory" or a "no-owner territory"
            // if failed, dirtify again (rollback)
            for (Point p : connectedTerritories)
                clean[p.r()][p.c()] = false;
            return false;
        }
    }
    public void scanTerritory() {
        blackTerritoryList = new ArrayList<>();
        whiteTerritoryList = new ArrayList<>();
        dirtify();
        stoneToList = new HashMap<>();
        for (int r = 0; r < boardSize; r++) {
            for (int c = 0; c < boardSize; c++) {
                if (board[r][c].isEmpty()) {
                    if(!scanTerritoryAt(r, c, StoneType.BLACK)) {
                        scanTerritoryAt(r, c, StoneType.WHITE);
                    }
                }
            }
        }
        for (Map.Entry<String, List<Point> > entry : stoneToList.entrySet()) {
            if (entry.getKey().contains("BLACK-")) {
                blackTerritoryList.addAll(entry.getValue());
            } else if (entry.getKey().contains("WHITE-")) {
                whiteTerritoryList.addAll(entry.getValue());
            }
        }
    }

    public List<Point> getBlackTerritoryList() { return blackTerritoryList; }
    public List<Point> getWhiteTerritoryList() { return whiteTerritoryList; }
    
    public int getBoardSize() {
        return boardSize;
    }
    
    // scoring stuff
    public int getBlackTerritoryScore() {
        scanTerritory();
        return blackTerritoryList.size();
    }
    public int getWhiteTerritoryScore() {
        scanTerritory();
        return whiteTerritoryList.size();
    }
    public int getBlackCapturedScore() { return blackCapturedScore; }
    public void addBlackCapturedScore(int blackCapturedScore) { this.blackCapturedScore += blackCapturedScore; }
    public int getWhiteCapturedScore() { return whiteCapturedScore; }
    public void addWhiteCapturedScore(int whiteCapturedScore) { this.whiteCapturedScore += whiteCapturedScore; }
    public double getBlackTotalScore() {
        return getBlackCapturedScore() + getBlackTerritoryScore();
    }
    public double getWhiteTotalScore() {
        return getWhiteCapturedScore() + getWhiteTerritoryScore() + (6.5);
    }
    
    public void setLastMovePoint (Point p) { this.lastMovePoint = p; }
    public Point getLastMovePoint() { return lastMovePoint; }
    
    private Queue<String> recentTwoStates = new LinkedList<>();
    public void memorizeCurrentState() {
        while (recentTwoStates.size() > 1)
            recentTwoStates.poll();
        recentTwoStates.add(getStateHash());
    }
    public boolean isCurrentStateRecently() {
        return recentTwoStates.contains(getStateHash());
    }
    private String getStateHash() {
        StringBuilder sb = new StringBuilder();
        for (int r = 0; r < boardSize; r++) {
            for (int c = 0; c < boardSize; c++) {
                sb.append(board[r][c].toString());
            }
        }
        return sb.toString();
    }
    
    private StoneType[][] backupBoard;
    public void backupBoard() {
        for (int r = 0; r < boardSize; r++) {
            for (int c = 0; c < boardSize; c++) {
                backupBoard[r][c] = board[r][c];
            }
        }
    }
    public void restoreBoard() {
        for (int r = 0; r < boardSize; r++) {
            for (int c = 0; c < boardSize; c++) {
                board[r][c] = backupBoard[r][c];
            }
        }
    }
}
