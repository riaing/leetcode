Given an m x n matrix grid where each cell is either a wall 'W', an enemy 'E' or empty '0', return the maximum enemies you can kill using one bomb. You can only place the bomb in an empty cell.

The bomb kills all the enemies in the same row and column from the planted point until it hits the wall since it is too strong to be destroyed.

 

Example 1:


Input: grid = [["0","E","0","0"],["E","0","W","E"],["0","E","0","0"]]
Output: 3
Example 2:


Input: grid = [["W","W","W"],["0","0","0"],["E","E","E"]]
Output: 1
 

Constraints:

m == grid.length
n == grid[i].length
1 <= m, n <= 500
grid[i][j] is either 'W', 'E', or '0'.

----------------------------------------------类似DP ----------------
/*
https://www.youtube.com/watch?v=QtZ16WcTjxo
dp是记录每行每列的enemy个数。
rowCount = 当前行在没遇到阻碍前的enemy 数
colCount[j] = 第j列没遇到阻碍的enmy数
对于每个空格子：能炸的敌人 = rowCount + colCount{j}
边遍历边记录

Time: o(m*n) 
Space: O(n)
*/

class Solution {
    public int maxKilledEnemies(char[][] grid) {
        int rowCount = 0;
        int[] colCount = new int[grid[0].length];
        int res = 0;
        
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[0].length; j++) {
                // 当碰到每行第一个，或者前一个是wall时，重新往右计算下rowCount
                if (j == 0 || grid[i][j-1] == 'W') {
                    rowCount = 0; 
                    for (int k = j; k < grid[0].length && grid[i][k] != 'W'; k++) {
                         // 往右扫直到墙，看能找到几个enemy
                        if (grid[i][k] == 'E') {
                            rowCount++; 
                        }
                    }
                }
                // 计算每列，只有是没列的第一个，或者上一个是墙时，才要重新往下计算
                if (i == 0 || grid[i-1][j] == 'W') {
                    colCount[j] = 0; 
                    for (int k = i; k < grid.length && grid[k][j] != 'W'; k++) {
                        if (grid[k][j] == 'E') {
                            colCount[j]++;
                        }
                    }
                }
                // 统计当前空格子能炸的enemy数
                if (grid[i][j] == '0') {
                    res = Math.max(res, rowCount + colCount[j]);
                }
            }
        }
        return res; 
    }
}
