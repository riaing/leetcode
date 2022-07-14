You are given an m x n integer matrix grid where each cell is either 0 (empty) or 1 (obstacle). You can move up, down, left, or right from and to an empty cell in one step.

Return the minimum number of steps to walk from the upper left corner (0, 0) to the lower right corner (m - 1, n - 1) given that you can eliminate at most k obstacles. If it is not possible to find such walk return -1.

 

Example 1:


Input: grid = [[0,0,0],[1,1,0],[0,0,0],[0,1,1],[0,0,0]], k = 1
Output: 6
Explanation: 
The shortest path without eliminating any obstacle is 10.
The shortest path with one obstacle elimination at position (3,2) is 6. Such path is (0,0) -> (0,1) -> (0,2) -> (1,2) -> (2,2) -> (3,2) -> (4,2).
Example 2:


Input: grid = [[0,1,1],[1,1,1],[1,0,0]], k = 1
Output: -1
Explanation: We need to eliminate at least two obstacles to find such a walk.
 

Constraints:

m == grid.length
n == grid[i].length
1 <= m, n <= 40
1 <= k <= m * n
grid[i][j] is either 0 or 1.
grid[0][0] == grid[m - 1][n - 1] == 0

-------------- BFS with state ----------------------

/*
BFS with state. O(mn*k) and 每个node最多访问k遍 
A start： the optimal directions to explore should be either right or down, rather than left or up. 

*/
class Node {
    int x;
    int y;
    int k;
    public Node(int x, int y, int k) {
        this.x = x;
        this.y = y;
        this.k = k; 
    }
}


class Solution {
    public int shortestPath(int[][] grid, int k) {
        int rows = grid.length, cols = grid[0].length;
        /*优化：先扫一遍看k够不够用，够的话直接return mahattan distance */
         if (k >= rows + cols - 2) {
            return rows + cols - 2;
        }
        
        if (grid == null || grid[0] == null) {
            return -1;
        }
        if (grid.length == 1 && grid[0].length == 1) {
            return 0; 
        }
        
        Queue<Node> q = new LinkedList<>();
        q.offer(new Node(0, 0, k));
        boolean[][][] visited = new boolean[grid.length][grid[0].length][k+1]; //重点！
        visited[0][0][k] = true; 
        
        int[] row = {1, -1, 0, 0};
        int[] col = {0, 0, 1, -1};
        int steps = 0; 
        while (!q.isEmpty()) {
            int size = q.size();
            for (int i = 0; i < size; i++) {
               Node cur = q.poll();
                // System.out.println(cur.x + " " + cur.y); 
                if (cur.x == grid.length - 1 && cur.y == grid[0].length - 1) {
                    return steps; 
                }
                for (int n = 0; n <4; n++) {
                    int newR = cur.x + row[n];
                    int newC = cur.y + col[n];
                    if (newR >= 0 && newR < grid.length && newC >= 0 && newC < grid[0].length) { 
                        // 因为0代表空，优化写法：
                        int nextK = cur.k - grid[newR][newC];
                        if (nextK >= 0 && !visited[newR][newC][nextK]) {
                            visited[newR][newC][nextK] = true;
                            q.offer(new Node(newR, newC, nextK));
                        }
                        // if (grid[newR][newC] == 0) {
                        //     visited[newR][newC] = true;
                        //     q.offer(new Node(newR, newC, cur.k));
                        // }
                        // else if (grid[newR][newC] == 1 && cur.k > 0) {
                        //     visited[newR][newC] = true;
                        //     q.offer(new Node(newR, newC, cur.k - 1));
                        // }
                    }
                }
            }
            steps++;
        }
        return -1; 
    }
}
