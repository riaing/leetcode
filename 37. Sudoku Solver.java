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

    
 ------------------take away from this problem:-----------------------------------------------------------------
     
 1, 9*9格中，确认（i，j）所在3*3方格的范围： row[i/3*3, i/3*3+3), col[j/3*3, j/3*3+3)
 2, 确认（i,j）在第几个方格：
 3*3方格共有9个，编号如下所示：
0 1 2
3 4 5
6 7 8
很明显3*3方格的编号和行号列号之间存在对应关系如下；
方格编号 = 行号 / 3 * 3 + 列号 / 3
                                                      
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
-------------solution 3-----------------------------------------------------------------------------------------------------
  解法3通过记录每行，每列，每个3*3方格的剩余可填数字，以缩短检查重复的时间。
  List<int[]> unvisit ->记录所有为‘.’的position i j
  记录每行，每列，每个block中存在的数字 
  private boolean[][] row_used = new boolean[9][9];
  private boolean[][] col_used = new boolean[9][9];
  private boolean[][] block_used = new boolean[9][9];
eg: row_used[0][0] == true ->第一行中，1 已经存在。

3*3方格共有9个，编号如下所示：
0 1 2
3 4 5
6 7 8
很明显3*3方格的编号和行号列号之间存在对应关系如下；
方格编号 = 行号 / 3 * 3 + 列号 / 3
    
在递归的过程中，每次填入一个数字，都需要更新相应的行、列和3*3方格。而重置方格为未填状态时，也需更新相应的行、列和3*3方格


class Solution {
        private boolean[][] row_used = new boolean[9][9];
        private boolean[][] col_used = new boolean[9][9];
        private boolean[][] block_used = new boolean[9][9];
    
    public void solveSudoku(char[][] board) {
        List<int[]> unvisit = new ArrayList<int[]>();
        // 第一个元素存第几行/列/block，第二个元素分别为1-9，值为此数字是否存在
        //eg: row_used[0][0] = true -> 第一行中已经有1了。 
       
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j< board[0].length; j++) {
                 // find all unVisited position 
                if (board[i][j] == '.') {
                    int[] cur = {i, j};
                    unvisit.add(cur);
                }
                else{
                    // mark the exist num as exist
                    row_used[i][board[i][j] - '1'] = true;
                    col_used[j][board[i][j] - '1'] = true;
                    block_used[i/3*3 + j/3][board[i][j] - '1'] = true;
                 }
            }
        }
        helper(board, unvisit, row_used, col_used, block_used, 0);
    }
    
    private boolean helper(char[][] board, List<int[]> unvisit, boolean[][] row_used, boolean[][] col_used, boolean[][] block_used, int index) {
        if (index == unvisit.size()) {
            return true; 
        }
        else{
            int row = unvisit.get(index)[0];
            int col = unvisit.get(index)[1];
            for (int i = 0; i < 9; i++) {
                if (validCell(row, col, i)){
                    board[row][col] = (char) (i + '1');
                    row_used[row][i] = true;
                    col_used[col][i] = true;
                    block_used[row/3*3 + col/3][i] = true;
                    if (helper(board, unvisit, row_used, col_used, block_used, index+1)) {
                        return true;
                    }
                    else {
                        board[row][col] = '.';
                        row_used[row][i] = false;
                        col_used[col][i] = false;
                        block_used[row/3*3 + col/3][i] = false;
                    }
                }
            }
            return false; 
        } 
    }
        
    private boolean validCell(int i, int j, int val) {
        return !row_used[i][val] && !col_used[j][val] && !block_used[i/3*3 + j/3][val];
    }  
}
