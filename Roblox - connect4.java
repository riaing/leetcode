// drop (int column， color) 和checkWin(row, column, color) 
// "static void main" must be defined in a public class.
class Connect4 {
    String p1;
    String p2;
    int col;
    int row;
    String[][] board;
    int[] rowIndex; 
    
    public Connect4(String p1, String p2, int row, int col) {
        this.p1 = p1;
        this.p2 = p2;
        this.row = row;
        this.col = col;
        this.board = new String[row][col];
        rowIndex = new int[col]; 
        Arrays.stream(board).forEach( o -> Arrays.fill(o, "")); // in case NPE when compare 
    }
    
    public void drop(String player, int c) {
        // get row 
        int r = rowIndex[c]; 
        if (r >= 0 && r < row && c >= 0 && c < col && board[r][c].length() == 0) {
            board[r][c] = player;
            rowIndex[c]++;  // update row index 
        }
    }
    
    public boolean checkWin(int r, int c, String player) {
        // 问：r.c这里一定是current player吗？是另player或者空怎么办? 是return false还是放棋？
        // 从当前点想上下，左右，左对角，右对角展开，看总共有几个连续的player的棋 
        int[][] rowDirections = {{1, -1}, {0, 0}, {-1, 1}, {-1, 1}};
        int[][] colDirections = {{0, 0}, {1, -1}, {-1, 1}, {1, -1}};
        for (int i = 0; i < rowDirections.length; i++) {
             boolean possibleRes = find4(r, c, player, rowDirections[i], colDirections[i]); 
            if (possibleRes) {
                return true; 
            }
        }
       return false;
    }
    
    private boolean find4(int r, int c, String player, int[] rowDir, int[] colDir) {
        int consecutive = 1; // assume 了当前位置就是player
        for (int i = 0; i < rowDir.length; i++) {
            int newR = r + rowDir[i];
            int newC = c + colDir[i]; 
            // 从四个方向找四个一样的
            while (newR >=0 && newC >= 0 && newR < row && newC < col && board[newR][newC].equals(player)) {
                consecutive++; 
                if (consecutive == 4) {
                    return true;
                }
                // 继续朝同一个方向找
                newR += rowDir[i];
                newC += colDir[i]; 
            }
        }
        return false; 
        
    }
}


public class Main {
    public static void main(String[] args) {
        Connect4 c = new Connect4("a", "b", 2,4);
        c.drop("a", 0);
        c.drop("a", 1);
        c.drop("a", 2);
        c.drop("a", 3);
        System.out.println(Arrays.deepToString(c.board));
        System.out.println(c.checkWin(1, 1, "a"));
        
    }
}
