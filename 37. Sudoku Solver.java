Write a program to solve a Sudoku puzzle by filling the empty cells.

A sudoku solution must satisfy all of the following rules:

Each of the digits 1-9 must occur exactly once in each row.
Each of the digits 1-9 must occur exactly once in each column.
Each of the the digits 1-9 must occur exactly once in each of the 9 3x3 sub-boxes of the grid.
Empty cells are indicated by the character '.'.


A sudoku puzzle...


...and its solution numbers marked in red.

Note:

The given board contain only digits 1-9 and the character '.'.
You may assume that the given Sudoku puzzle will have a single unique solution.
The given board size is always 9x9.

-------------------solution 1-------------------------------------------------------------------------------------
// 第一反应就是N皇后问题。就是一点点尝试着填数，1，不行的话就回溯，直到都填满就返回。 
//2，如果对一个格子尝试从0~9都不行，那么说明整个sudoku无解，返回false就好。
//3， 对整个棋盘所有'.'都填完了，那么就可以返回true了。
class Solution {
    public void solveSudoku(char[][] board) {
        if (board == null || board.length == 0) {
             return;
        }
        helper(board);
    }
    
    private boolean helper(char[][] board) {
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j< board[0].length; j++) {
                if (board[i][j] == '.') {
                    for (char n = '1'; n <='9'; n++) {
            
                        if (validCell(board, i, j, n)) {
                            board[i][j] = n;
                            if (helper(board)) {
                                return true;
                            }
                            else {
                                // 说明这个puzzle没有解，回溯。
                                board[i][j] = '.';
                            }
                        }
                    }
                    return false;
                }
            }
        }
        return true;
    }
    
    private boolean validCell(char[][] board, int i, int j, char c) {
        // check valid column 
        for (int row = 0; row <9; row++) {
            if (board[row][j] == c) {
                return false;
            }
        }
        // check valid row 
        for (int col = 0; col <9; col++) {
            if (board[i][col] == c) {
                return false;
            }
        }
        // check block 
        for (int row = i/3*3; row < i/3*3+3; row ++) {
            for (int col = j/3*3; col < j/3*3+3; col ++) {
                if (board[row][col] == c) {
                   return false; 
                }
            }
        }
        return true; 
    }  
}


---------------------solution 2-------------------------------------------------------------------------------------
//观察解法1，发现每次递归都从board[0][0]开始是没有必要的，只需从当前方格的右侧方格开始递归即可。若当前方格已经是该行的最后一个方格，则需从下一行的第一个方格开始递归。代码如下：。
class Solution {
    public void solveSudoku(char[][] board) {
        if (board == null || board.length == 0) {
             return;
        }
        helper(board,0,0);
    }
    
    private boolean helper(char[][] board, int i, int j) {
        while(i < 9) {
            if (board[i][j] == '.') {
                     for (char n = '1'; n <='9'; n++) {
            
                        if (validCell(board, i, j, n)) {
                            board[i][j] = n;
                            //// ---different from solution 1. reduce duplicate recursion 
                            if (j < 8) {
                                if (helper(board, i, j+1)) {
                                    return true;
                                }
                                else {
                                    board[i][j] = '.';
                                }
                            }
                            else{
                                if (helper(board, i+1, 0)) {
                                    return true;
                                }
                                else {
                                    board[i][j] = '.';
                                }
                            }
                            /////---different from solution 1. reduce duplicate recursion 
                        }
                    }
                // for (char n = '1'; n <='9'; n++) {
                  return false;
            }
            // if (board[i][j] == '.') {
            j++;
            if (j > 8) {
                j = 0;
                i++;
            }
        }
        // while(i < 9) {
        return true;
    }
        
    // same as solution 1 
    private boolean validCell(char[][] board, int i, int j, char c) {
        // check valid column 
        for (int row = 0; row <9; row++) {
            if (board[row][j] == c) {
                return false;
            }
        }
        // check valid row 
        for (int col = 0; col <9; col++) {
            if (board[i][col] == c) {
                return false;
            }
        }
        // check block 
        for (int row = i/3*3; row < i/3*3+3; row ++) {
            for (int col = j/3*3; col < j/3*3+3; col ++) {
                if (board[row][col] == c) {
                   return false; 
                }
            }
        }
        return true; 
    }  
}
