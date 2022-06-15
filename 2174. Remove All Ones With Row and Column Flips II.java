You are given a 0-indexed m x n binary matrix grid.

In one operation, you can choose any i and j that meet the following conditions:

0 <= i < m
0 <= j < n
grid[i][j] == 1
and change the values of all cells in row i and column j to zero.

Return the minimum number of operations needed to remove all 1's from grid.

 

Example 1:


Input: grid = [[1,1,1],[1,1,1],[0,1,0]]
Output: 2
Explanation:
In the first operation, change all cell values of row 1 and column 1 to zero.
In the second operation, change all cell values of row 0 and column 0 to zero.
Example 2:


Input: grid = [[0,1,0],[1,0,1],[0,1,0]]
Output: 2
Explanation:
In the first operation, change all cell values of row 1 and column 0 to zero.
In the second operation, change all cell values of row 2 and column 1 to zero.
Note that we cannot perform an operation using row 1 and column 1 because grid[1][1] != 1.
Example 3:


Input: grid = [[0,0],[0,0]]
Output: 0
Explanation:
There are no 1's to remove so return 0.
 

Constraints:

m == grid.length
n == grid[i].length
1 <= m, n <= 15
1 <= m * n <= 15
grid[i][j] is either 0 or 1.

----------------------- DFS 找最小 + bitmask 来记录matrix 状态----------------------------
/*
总：用的DFS思路，bitmask 就是通过bit来代表新的matrix。

DFS,对于每个1，可取可不取。取的话更新matrix，传个新的matrix，进行递归。
技巧1： 把matrix flatten成一维。对于 i,j -> i * n + j就是转换后的数。 恢复时对于k -> k / n = row, k %n = col
技巧2： 用32位数来代表新的matrix。 i,j位是1，就先算出i，j 变成1维的数k，然后state & 1 << k 就把这个数设成1 
当要把这个数变成0 时： 先取非，把位数变成0，然后再 & state： newState &= ~(1 << (k * n + j)); 

refer： http://leungyukshing.cn/archives/LeetCode%E8%A7%A3%E9%A2%98%E6%8A%A5%E5%91%8A%EF%BC%88519%EF%BC%89--%202174.%20Remove%20All%20Ones%20With%20Row%20and%20Column%20Flips%20II.html

*/
class Solution {
    int row;
    int col; 
    public int removeOnes(int[][] grid) {
        row = grid.length; 
        col = grid[0].length; 
        
        int lastNumInMatrx = grid.length * grid[0].length; 
        int[] memo = new int[1<<lastNumInMatrx]; 
        Arrays.fill(memo, Integer.MAX_VALUE); // 必须设为最大值
        int state = 0;
        // 把1记录到state里
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                if (grid[i][j] == 1) {
                    int flatten = i * col + j;
                    state = state | (1 << flatten);  // 赋值
                }
            }
        }
        return dfs(state, memo);  // 传matrix进去
    }
    
    private int dfs(int state, int[] memo) {
        if (state == 0) { // base case, 如果matrix里全是0，说明不用flip
            return 0; 
        }
        if (memo[state] != Integer.MAX_VALUE) { 
            return memo[state]; 
        }
        
        // 对每一个可用的1进行dfs。这时当前matrix的状态之前可能已经找到解了，这里要取min
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                int flatten = i * col + j; 
                if ((state & (1 << flatten)) > 0) {// 当前cell是1，说明可以进行dfs 
                    int newState = state; 
                    // 1. 把当前行和列的1全部变0 
                    // 先变行。循环列
                    for (int k = 0; k < col; k++) {
                        flatten = i * col + k; 
                        newState = newState & (~(1 << flatten)); // 先把这位的1 变成0， 再 & 就得0 
                    }
                    // 再变列
                    for (int k = 0; k < row; k++) {
                        flatten = k * col + j;
                        newState = newState & (~(1 << flatten)); 
                    }
                    // 2. 往下dfs
                    int curRes = 1 + dfs(newState, memo);
                    // 3. 当前matrix的状态之前可能已经找到解了，这里就要取min更新
                    memo[state] = Math.min(memo[state], curRes); 
                }
            }
        }
        return memo[state];
    }
}
