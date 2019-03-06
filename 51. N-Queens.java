
The n-queens puzzle is the problem of placing n queens on an n×n chessboard such that no two queens attack each other.



Given an integer n, return all distinct solutions to the n-queens puzzle.

Each solution contains a distinct board configuration of the n-queens' placement, where 'Q' and '.' both indicate a queen and an empty space respectively.

Example:

Input: 4
Output: [
 [".Q..",  // Solution 1
  "...Q",
  "Q...",
  "..Q."],

 ["..Q.",  // Solution 2
  "Q...",
  "...Q",
  ".Q.."]
]
Explanation: There exist two distinct solutions to the 4-queens puzzle as shown above. 
-------------------------------------------solution 1 -------------------------------------------------------------------------
同样也是类似DFS的backtracking问题。难点在于如何判断当前某一位置是否可以放皇后，需要通过之前所有放置过的皇后位置来判断。对已经放置的任意皇后，
需要判断当前位置是否在同一行、列、对角线上这三个条件。
1. 逐行放置皇后：排除在同一行的可能。
2. 记录之前所放皇后的列坐标：col[i]=j表示第i行的皇后在第j列。这样在放置第i+1行时，只要保证col[i+1] != col[k], k=0...i 即可。
3. 对角线判断：对于任意(i1, col[i1])和(i2, col[i2])，只有当abs(i1-i2) = abs(col[i1]-col[i2])时，两皇后才在同一对角线。 

Time (N!*N) 第一层有n个选择，第二层有n*（n-3）个选择（除去行，列，对角线的可能），。然后每层要循环1，2，3....n次去排除不可能的情况（validPlace()）
第一层：n 
第二层：n*(n-1) * 1（validPlace()此时有一层）
第n层：n！      * n
-> = O(n!*n)
Space: O(n) 数组positio[]

class Solution {
    public List<List<String>> solveNQueens(int n) {
        // index is the row and val is the column 
        int[] position = new int[n];
        List<List<String>> res = new ArrayList<List<String>>();
        helper(n, 0, position, res);
        return res;
    }
    
    private void helper(int n, int index, int[] position, List<List<String>> res) {
        if (index == position.length) {
            
            // construct the string from saved position. 
            List<String> cur = new ArrayList<String>();
            for (int col : position) {
                StringBuilder builder = new StringBuilder();
                for (int i = 0; i < n; i++) {
                    if (i == col) {
                        builder.append('Q');
                    }
                    else {
                        builder.append('.');
                    } 
                }
                //form the string and add it to current list. 
                cur.add(builder.toString());
            }
            res.add(cur);
            return;
        }
        //iterate through each col and see if can put queen at the position 
        for (int j = 0; j < n; j++) {
            if (validPlace(position, index, j)) {
                position[index] = j;
                helper(n, index+1, position, res);
                // backtracking ????? don't need, because will reset position[index]
            }
        }        
    }
    
    
    // Give a position(i,j), check if put the queue here, no queues on col j and on diagonal 
    // now position has valid row i-1, so only for loop this range. 
    private boolean validPlace(int[] position, int i, int j) {
        for (int row = 0; row < i; row++) {
            // check if queen is at the same col
            if (position[row] == j) {
                return false;
            }
            // check if queen is at diagonal
            if (Math.abs(position[row] - j) == i - row) {
                return false;
            }
        }
        return true;
    }
}



