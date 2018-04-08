package gosingle;

import enums.Difficulty;
import enums.Player;
import enums.StoneType;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import models.ImmutableBoard;
import models.Point;

public class Bot {
    // BLACK is minimum, WHITE is maximum
    
    private static Map<String, Point> pointMemo;
    
    public static Point getNextMove(ImmutableBoard board, int passCount, int blackScore, int whiteScore, Player currentPlayer, Difficulty difficulty) {
        StoneType currentStone = currentPlayer.isBlack() ? StoneType.BLACK : StoneType.WHITE;
        
        int depth;
        if (difficulty.isEasy()) depth = 2;
        else if (difficulty.isMedium()) depth = 3;
        else depth = 4;
        
        pointMemo = new HashMap<>();
        minimax(currentStone.isBlack() ? 0 : 1, board, passCount, blackScore, whiteScore, currentStone, depth);
        
        String hash = getHash(board, passCount, currentStone);
        Point bestPoint = pointMemo.get(hash);
        return bestPoint;
    }
    
    private static int minimax(int minimax, ImmutableBoard board, int passCount, int blackScore, int whiteScore, StoneType stoneType, int depth) {
        
        if (depth <= 0 || passCount >= 2) {
            scanTerritory(board);
            int finalScore = whiteScore-blackScore;
            finalScore += whiteTerritoryList.size()-blackTerritoryList.size();
            return finalScore;
        }
        
        String hash = getHash(board, passCount, stoneType);
        pointMemo.put(hash, null);
        
        List<Point> possiblePoints = getPossiblePoints(board);
        System.out.printf("Depth: %d, Possibilities: %d\n", depth, possiblePoints.size());
        int result = minimax == 0 ? Integer.MAX_VALUE : Integer.MIN_VALUE;
        Point resultPoint = null;
        for (Point p : possiblePoints) {
            // coba taruh batu dulu
            ImmutableBoard nextBoard = board.setStoneAt(p.r(), p.c(), stoneType);
            
            // cari smua batu yang ter-eliminasi
            List<List<Point>> connStonesList = getListOfDeadStones(nextBoard);
            //is move suicidal ?
            if (connStonesList.size() == 1 &&
                connStonesList.get(0).contains(p) ) {
                    continue;
            }
            //scoring
            int nextBlackScore = blackScore;
            int nextWhiteScore = whiteScore;
            for (List<Point> connStones : connStonesList) {
                if (!connStones.contains(p)) {
                    if (connStones.size() > 0) {
                        Point p2 = connStones.get(0);
                        if (board.getStoneAt(p2.r(), p2.c()).isBlack())
                            nextWhiteScore += connStones.size();
                        else if (board.getStoneAt(p2.r(), p2.c()).isWhite())
                            nextBlackScore += connStones.size();
                    }
                }
            }
            //remove other zero-liberty stones, except current attacking stone
            nextBoard = nextBoard.removeAllStonesExceptOne(connStonesList, p);
            
            // if sudah pernah visit, terminate
            String nextHash = getHash(nextBoard, 0, stoneType);
            if (pointMemo.containsKey(nextHash)) continue;
            
            int nextResult = 
                    minimax(minimax, nextBoard, 0, nextBlackScore, nextWhiteScore, stoneType.isBlack() ? StoneType.WHITE : StoneType.BLACK, depth-1);
            if (minimax == 0 && nextResult < result)  {
                result = nextResult;
                resultPoint = p;
            } else if (minimax == 1 && nextResult > result)  {
                result = nextResult;
                resultPoint = p;
            }
        }
        {
            int nextResult = 
                    minimax(minimax^1, board, passCount+1, blackScore, whiteScore, stoneType.isBlack() ? StoneType.WHITE : StoneType.BLACK, depth-1);
            if (minimax == 0 && nextResult < result)  {
                result = nextResult;
                resultPoint = null;
            } else if (minimax == 1 && nextResult > result)  {
                result = nextResult;
                resultPoint = null;
            }
        }
        pointMemo.put(hash, resultPoint);
        if (possiblePoints.isEmpty()) {
            pointMemo.put(hash, new Point((int)(Math.random()*board.getSize()), (int)(Math.random()*board.getSize())));
        }
        return result;
    }
    
    private static String getHash(ImmutableBoard board, int passCounter, StoneType stoneType) {
        StringBuilder sb = new StringBuilder();
        sb.append(board.getHash());
        sb.append('(').append(passCounter).append(')');
        sb.append('(').append(stoneType.toString()).append(')');
        return sb.toString();
    }
    
    private static List<List<Point>> getListOfDeadStones(ImmutableBoard board) {
        List<List<Point>> resultList = new ArrayList<>();
        dirtify(board);
        for (int r = 0; r < board.getSize(); r++) {
            for (int c = 0; c < board.getSize(); c++) {
                List<Point> lst = new ArrayList<>();
                unvisit(board);
                int totalLiberty = _getLibertyCountAt(r, c, board, board.getStoneAt(r, c), lst);
                if (totalLiberty == 0 && !lst.isEmpty()) {
                    resultList.add(lst);
                }
            }
        }
        return resultList;
    }
    
    private static boolean visit[][];
    private static boolean clean[][];
    private static final int[] DIR_R = {1,-1, 0, 0};
    private static final int[] DIR_C = {0, 0, 1,-1};
    private static int _getLibertyCountAt(int row, int col, ImmutableBoard board, StoneType stoneType, List<Point> connectedStones) {
        if (!(0 <= row && row < board.getSize())) return 0; //out of bound
        if (!(0 <= col && col < board.getSize())) return 0; //out of bound
        if (visit[row][col]) return 0;  //sudah pernah visit, termintate
        visit[row][col] = true; //flag sudah visit
        if (board.getStoneAt(row, col).isEmpty()) return 1; //ketemu liberty, tambah 1
        if (!board.getStoneAt(row, col).equals(stoneType)) return 0; //stone tidak sejenis, terminate
        
        if (connectedStones != null) { //apabila caller minta connectedStones
            if (clean[row][col]) return 0; //sudah clean, terminate
            clean[row][col] = true;
            connectedStones.add(new Point(row, col)); //push sebuah connectedStone
        }
        
        int libertyTotal = 0;
        for (int i = 0; i < 4; i++) {
            libertyTotal += _getLibertyCountAt(row+DIR_R[i], col+DIR_C[i], board, stoneType, connectedStones);
        }
        return libertyTotal;
    }
 
    private static void unvisit(ImmutableBoard board) {
        visit = new boolean[board.getSize()][board.getSize()];
    }
    private static void dirtify(ImmutableBoard board) {
        clean = new boolean[board.getSize()][board.getSize()];
    }
    
    private static List<Point> getPossiblePoints(ImmutableBoard board) {
        List<Point> list = new ArrayList<>();
        for (int r = 0; r < board.getSize(); r++) {
            for (int c = 0; c < board.getSize(); c++) {
                if (board.getStoneAt(r, c).isEmpty()) {
                    if ((r > 0 && !board.getStoneAt(r-1, c).isEmpty()) ||
                        (c > 0 && !board.getStoneAt(r, c-1).isEmpty()) ||
                        (r < board.getSize()-1 && !board.getStoneAt(r+1, c).isEmpty()) ||
                        (c < board.getSize()-1 && !board.getStoneAt(r, c+1).isEmpty()) ) {
                            list.add(new Point(r, c));
                    }
                }
            }
        }
        return list;
    }
    
    private static List<Point> blackTerritoryList = null;
    private static List<Point> whiteTerritoryList = null;
    private static Map<Point, Integer> freqOfPoint;
    private static Map<String, List<Point> > stoneToList;
    private static String _convertListToKey(List<Point> list) {
        list.sort( (a, b) -> (a.r()*31+a.c()*37)-(b.r()*31+b.c()*37) );
        StringBuilder sb = new StringBuilder();
        for (Point p : list) {
            sb.append(p.r()).append(p.c());
        }
        return sb.toString();
    }
    private static int _scanTerritoryAt(int row, int col, ImmutableBoard board, StoneType stoneType, List<Point> connectedTerritories) {
        if (!(0 <= row && row < board.getSize())) return 0; //out of bound
        if (!(0 <= col && col < board.getSize())) return 0; //out of bound
        //ketemu stone sejenis, cek apakah sudah benturan 1x, 2x, 3x ?
        if (board.getStoneAt(row, col).equals(stoneType)) {
            Point p = new Point(row, col);
            if (!freqOfPoint.containsKey(p)) freqOfPoint.put(p, 1);
            else freqOfPoint.put(p, freqOfPoint.get(p)+1);
            
            // crazy chain of important if statements
            if ( ((p.r()==0 && p.c()==0) || (p.r()==0 && p.c()==board.getSize()-1)|| 
                  (p.r()==board.getSize()-1 && p.c()==0) || (p.r()==board.getSize()-1 && p.c()==board.getSize()-1)) &&
                  freqOfPoint.get(p) > 1 ) {
                return -(20*20);
            } else if ((p.r()==0 || p.r()==board.getSize()-1 || p.c()==0 || p.c()==board.getSize()-1) &&
                    freqOfPoint.get(p) > 2){
                return -(20*20);
            } else if (freqOfPoint.get(p) > 3) {
                return -(20*20);
            } else return 1;
        }
        if (visit[row][col]) return 0;  //sudah pernah visit, termintate
        visit[row][col] = true; //flag sudah visit
        
        // bukan ketemu EMPTY cell, tapi ketemu stone jenis lawan, return INF
        if (!board.getStoneAt(row, col).equals(StoneType.EMPTY)) return -(20*20); 
        
        if (connectedTerritories != null) { //apabila caller minta connectedStones
            if (clean[row][col]) return 0; //sudah clean, terminate
            clean[row][col] = true;
            connectedTerritories.add(new Point(row, col)); //push sebuah connectedStone
        }
        
        int commonStoneCount = 0;
        for (int i = 0; i < 4; i++) {
            commonStoneCount += _scanTerritoryAt(row+DIR_R[i], col+DIR_C[i], board, stoneType, connectedTerritories);
        }
        return commonStoneCount;
    }
    public static boolean scanTerritoryAt(int row, int col, ImmutableBoard board, StoneType stoneType) {
        List<Point> connectedTerritories = new ArrayList<>();
        unvisit(board);
        freqOfPoint = new HashMap<>();
        if (freqOfPoint == null) System.out.println("NULL BEGO!");
        int commonStoneCount = _scanTerritoryAt(row, col, board, stoneType, connectedTerritories);
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
    public static void scanTerritory(ImmutableBoard board) {
        blackTerritoryList = new ArrayList<>();
        whiteTerritoryList = new ArrayList<>();
        dirtify(board);
        stoneToList = new HashMap<>();
        for (int r = 0; r < board.getSize(); r++) {
            for (int c = 0; c < board.getSize(); c++) {
                if (board.getStoneAt(r, c).isEmpty()) {
                    if(!scanTerritoryAt(r, c, board, StoneType.BLACK)) {
                        scanTerritoryAt(r, c, board, StoneType.WHITE);
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
}
