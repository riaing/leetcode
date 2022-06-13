You are given an m x n grid where each cell can have one of three values:

0 representing an empty cell,
1 representing a fresh orange, or
2 representing a rotten orange.
Every minute, any fresh orange that is 4-directionally adjacent to a rotten orange becomes rotten.

Return the minimum number of minutes that must elapse until no cell has a fresh orange. If this is impossible, return -1.

 

Example 1:


Input: grid = [[2,1,1],[1,1,0],[0,1,1]]
Output: 4
Example 2:

Input: grid = [[2,1,1],[0,1,1],[1,0,1]]
Output: -1
Explanation: The orange in the bottom left corner (row 2, column 0) is never rotten, because rotting only happens 4-directionally.
Example 3:

Input: grid = [[0,2]]
Output: 0
Explanation: Since there are already no fresh oranges at minute 0, the answer is just 0.
 

Constraints:

m == grid.length
n == grid[i].length
1 <= m, n <= 10
grid[i][j] is 0, 1, or 2.


-------------------------- BFS 染色 求最短 -----------------
/*
BFS 染色求最短路径
*/
class Solution {
    public int orangesRotting(int[][] grid) {
        int ones = 0;
        Queue<int[]> q = new LinkedList<>();
        // 1, put all 2s into q 
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[0].length; j++) {
                if (grid[i][j] == 1) {
                    ones++;
                }
                if (grid[i][j] == 2) {
                    q.offer(new int[]{i, j});
                }
            }
        }
        
        // corner case: 无需染色时
        if (ones == 0) {
            return 0; 
        }
        
        // 2. rotten 
        int generation = 0; 
        int trans = 0; 
        while (!q.isEmpty()) {
            int size = q.size();
            for (int n = 0; n < size; n++) {
                int[] cur = q.poll();
                // 将周围好的橘子染色
                int[] row = {1, -1, 0, 0};
                int[] col = {0, 0, 1, -1};
                for (int i = 0; i < row.length; i++) {
                    int[] next = {cur[0] + row[i], cur[1] + col[i]};
                    if (next[0] >= 0 && next[0] < grid.length && next[1] >= 0 && next[1] < grid[0].length && grid[next[0]][next[1]] == 1) {
                        grid[next[0]][next[1]] = 2;
                        trans++;
                        q.offer(next); 
                    }
                } 
            }
            
            generation++; // 染完一趟
            if (trans == ones) {
                return generation; 
            }
        }
        return -1; 
    }
}
