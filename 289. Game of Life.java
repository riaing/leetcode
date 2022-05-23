According to the Wikipedia's article: "The Game of Life, also known simply as Life, is a cellular automaton devised by the British mathematician John Horton Conway in 1970."

Given a board with m by n cells, each cell has an initial state live (1) or dead (0). Each cell interacts with its eight neighbors (horizontal, vertical, diagonal) using the following four rules (taken from the above Wikipedia article):

Any live cell with fewer than two live neighbors dies, as if caused by under-population.
Any live cell with two or three live neighbors lives on to the next generation.
Any live cell with more than three live neighbors dies, as if by over-population..
Any dead cell with exactly three live neighbors becomes a live cell, as if by reproduction.
Write a function to compute the next state (after one update) of the board given its current state. The next state is created by applying the above rules simultaneously to every cell in the current state, where births and deaths occur simultaneously.

Example:

Input: 
[
  [0,1,0],
  [0,0,1],
  [1,1,1],
  [0,0,0]
]
Output: 
[
  [0,0,0],
  [1,0,1],
  [0,1,1],
  [0,1,0]
]
Follow up:

Could you solve it in-place? Remember that the board needs to be updated at the same time: You cannot update some cells first and then use their updated values to update other cells.
In this question, we represent the board using a 2D array. In principle, the board is infinite, which would cause problems when the active area encroaches the border of the array. How would you address these problems?



------------------ 
细胞变为“活”，只有两种可能。当它本来为存活态时，邻居有2或3个时保持原来的状态，也就是“活”。还有一种是，当原本为死亡态时，邻居有3个时，也会变为“活”。

其余状态，要么是由存活态变为死亡态，要么就是保持死亡态。

我们通过加上10来保存这个细胞的状态，最后统一进行刷新，这也就是这个问题的核心。

 
class Solution {
    public void gameOfLife(int[][] board) {
        if (board == null || board.length == 0) {
            return;
        }
        
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j <board[0].length; j++){
                int count = countNeighbors(board, i, j);
                if (count == 3 || (count == 2 && board[i][j] == 1)) {
                    // Add 10 to indicate "live" in next generation. 
                
                    board[i][j] = board[i][j] + 10;
                }
            }
        }
       
        flashBoard(board);
        return;   
    }
    
    private int countNeighbors(int[][] board, int r, int c) {
        int count = 0; 
        for (int i = Math.max(r-1, 0); i <= Math.min(r+1, board.length-1); i++) {
            for (int j = Math.max(c-1, 0); j<= Math.min(c+1, board[0].length-1); j++) {
                // Get the original value of cells other than the central cell
                if (i != r || j != c) {
                    count = count + board[i][j] % 10;
                }
                
            }
        }
        return count; 
    }
    // Refresh the board to next generation based on the current value. 
    private void flashBoard(int[][] board) {
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j <board[0].length; j++){
                // 10 indicates the cell should be live: 11(original cell is live)/10 = 1; 10(original cell id dead)/10 = 1; 
                // if the original cell should be die now: 1/10 = 0;(original cell is live, now should be dead), 0/10 = 0; 
                board[i][j] = board[i][j] / 10; 
            }
        }
        return; 
    }
}

https://blog.csdn.net/NoMasp/article/details/52122735 

----------------------- 2022、5 ---------------------------------------------------
  /*
走两遍。第一遍，根据规则变cell，但用特殊字符表示之前的state
live -> die. 将cell 变成-1
die -> live, 将cell 变成 2

第二遍
-1的cell恢复成0
2的cell恢复成1 

时间O（m*n*8）
空间O（1）

follow up 2: 只记活cell -> sparce matrix的解法
*/
class Solution {
    public void gameOfLife(int[][] board) {
        
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[0].length; j++) {
                // 1. find live neibors count 
                int liveNeiborCount = 0;
                int[] row = {1,-1, 0, 0, -1,-1,1, 1};
                int[] col = {0, 0, 1,-1, -1, 1,-1,1};
                for (int k = 0; k < row.length; k++) {
                    int newR = i + row[k];
                    int newC = j + col[k];
                    if (newR >= 0 && newR < board.length && newC >= 0 && newC < board[0].length && (board[newR][newC] == 1 || board[newR][newC] == -1)) {
                        liveNeiborCount++;
                    }
                }
                // 跟新
                if (board[i][j] == 0 && liveNeiborCount == 3) {
                    board[i][j] = 2;
                }
                else if (board[i][j] == 1) {
                    if (liveNeiborCount < 2 || liveNeiborCount > 3) {
                        board[i][j] = -1;
                    }
                }
            }
        }
        
        // 恢复特殊记号： -1 -> 0, 2 -> 1
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[0].length; j++) {
                if (board[i][j] == 2) {
                    board[i][j] = 1;
                }
                else if (board[i][j] == -1) {
                    board[i][j] = 0;
                }
            }
        }
                
    }
}

------------ follow up 2: sparse matrix problem: board is infinite -----------------------------------
  solution： 只存live cell的node in a set 
  iterate set, 找到 all neibors of the live cell, put into a map as a key, and the live cell as value (cell -> List(neibors)). The map should contain 
  a cel -> its live neibors 
  after done the set, iterate through the Map, follow the rule to check how many cells from the key set are now alive 
  
  
  
  
