The n-queens puzzle is the problem of placing n queens on an n×n chessboard such that no two queens attack each other.



Given an integer n, return the number of distinct solutions to the n-queens puzzle.

Example:

Input: 4
Output: 2
Explanation: There are two distinct solutions to the 4-queens puzzle as shown below.
[
 [".Q..",  // Solution 1
  "...Q",
  "Q...",
  "..Q."],

 ["..Q.",  // Solution 2
  "Q...",
  "...Q",
  ".Q.."]
]

class Solution {
    boolean[] col_used; 
    boolean[] diagonal_used; 
    boolean[] ver_diagonal_used;
    int total;
    public int totalNQueens(int n) {
        total = 0; 
       
        col_used = new boolean[n]; 
        diagonal_used = new boolean[2*n-1];
        ver_diagonal_used = new boolean[2*n-1];
        // index is the row and val is the column 
        int[] position = new int[n];
        
    
        helper(n, 0, position);
        return total;
    }
    
    private void helper(int n, int index, int[] position) {
        if (index == position.length) {
            total++;
            return;
        }
        //iterate through each col and see if can put queen at the position 
        for (int j = 0; j < n; j++) {
            if (validPlace(position, index, j, n)) {
                position[index] = j;
                col_used[j] = true;
                diagonal_used[index - j + n-1] = true;
                ver_diagonal_used[index+j] = true;
                helper(n, index+1, position);
                col_used[j] = false;
                diagonal_used[index - j + n-1] = false;
                ver_diagonal_used[index+j] = false;
                
            }
        }        
    }
    
    // Give a position(i,j), check if put the queue here, no queues on col j and on diagonal 
    // now position has valid row i-1, so only for loop this range. 
    private boolean validPlace(int[] position, int i, int j, int n) {
        if (col_used[j] || diagonal_used[i-j + n -1] || ver_diagonal_used[i+j]) {
            return false;
        }
        return true;
    }
}
