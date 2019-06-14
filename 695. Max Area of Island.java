Given a non-empty 2D array grid of 0's and 1's, an island is a group of 1's (representing land) connected 4-directionally (horizontal or vertical.) You may assume all four edges of the grid are surrounded by water.

Find the maximum area of an island in the given 2D array. (If there is no island, the maximum area is 0.)

Example 1:

[[0,0,1,0,0,0,0,1,0,0,0,0,0],
 [0,0,0,0,0,0,0,1,1,1,0,0,0],
 [0,1,1,0,1,0,0,0,0,0,0,0,0],
 [0,1,0,0,1,1,0,0,1,0,1,0,0],
 [0,1,0,0,1,1,0,0,1,1,1,0,0],
 [0,0,0,0,0,0,0,0,0,0,1,0,0],
 [0,0,0,0,0,0,0,1,1,1,0,0,0],
 [0,0,0,0,0,0,0,1,1,0,0,0,0]]
Given the above grid, return 6. Note the answer is not 11, because the island must be connected 4-directionally.
Example 2:

[[0,0,0,0,0,0,0,0]]
Given the above grid, return 0.
Note: The length of each dimension in the given grid does not exceed 50.


---------------------------------------dfs-----------------------------------------------
/*
DFS, 碰到1后把面积找出来，同时把当前的点渲染成-1
time : O(r*c)
*/
class Solution {
    public int maxAreaOfIsland(int[][] grid) {
        if (grid == null || grid.length == 0) {
            return 0; 
        }
        int max = 0;
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[0].length; j++) {
                if (grid[i][j] == 1) {
                    max = Math.max(max, helper(grid, i, j));
                }
            }
        }
        return max;
    }
    
    private int helper(int[][] grid, int r, int c) {
        grid[r][c] = -1;
        int res = 1;
        int[] rows = {1, -1, 0, 0};
        int[] cols = {0, 0, 1, -1};
        
        for(int i = 0; i < 4; i++) {
            int newRow = r + rows[i];
            int newCol = c + cols[i];
            if (newRow >= 0 && newRow < grid.length && newCol >= 0 && newCol < grid[0].length && grid[newRow][newCol] == 1) {
                grid[newRow][newCol] = -1;
                res += helper(grid, newRow, newCol);
            }
        }
        return res;
    }
}
