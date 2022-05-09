You are a hiker preparing for an upcoming hike. You are given heights, a 2D array of size rows x columns, where heights[row][col] represents the height of cell (row, col). You are situated in the top-left cell, (0, 0), and you hope to travel to the bottom-right cell, (rows-1, columns-1) (i.e., 0-indexed). You can move up, down, left, or right, and you wish to find a route that requires the minimum effort.

A route's effort is the maximum absolute difference in heights between two consecutive cells of the route.

Return the minimum effort required to travel from the top-left cell to the bottom-right cell.

 

Example 1:



Input: heights = [[1,2,2],[3,8,2],[5,3,5]]
Output: 2
Explanation: The route of [1,3,5,3,5] has a maximum absolute difference of 2 in consecutive cells.
This is better than the route of [1,2,2,2,5], where the maximum absolute difference is 3.
Example 2:



Input: heights = [[1,2,3],[3,8,4],[5,3,5]]
Output: 1
Explanation: The route of [1,2,3,4,5] has a maximum absolute difference of 1 in consecutive cells, which is better than route [1,3,5,3,5].
Example 3:


Input: heights = [[1,2,1,1,1],[1,2,1,2,1],[1,2,1,2,1],[1,2,1,2,1],[1,1,1,2,1]]
Output: 0
Explanation: This route does not require any effort.
 

Constraints:

rows == heights.length
columns == heights[i].length
1 <= rows, columns <= 100
1 <= heights[i][j] <= 106

----------------- BF 超时 ------------------------------------------------
时间：O（3^m*n) -> 每个cell实际有三种选择 （不会往回走，所以是三种）
space: O（m*n) 

/*
BF: 走每条path，记录当前path的max effort 

*/
class Solution {
    int globalMin = Integer.MAX_VALUE;
    public int minimumEffortPath(int[][] heights) {
        
        boolean[][] visited = new boolean[heights.length][heights[0].length];
        helper(heights, visited, 0, 0, 0); // 初始effort设为0，如果只有一个格子就不用走，effort = 0 
        return globalMin; 
    }
    
    private void helper(int[][] heights, boolean[][] visited, int r, int c, int maxEffortOnThePath) {
        
        if (r == heights.length -1 && c ==heights[0].length - 1) {
            globalMin = Math.min(globalMin, maxEffortOnThePath);
            return;
        }
        
        int[] row = {1, -1, 0, 0};
        int[] col = {0, 0, 1, -1};
        for (int i = 0; i < row.length; i++) {
            int newR = r + row[i];
            int newC = c + col[i];
            if (newR >= 0 && newR < heights.length && newC >= 0 && newC < heights[0].length && !visited[newR][newC]) {
                visited[newR][newC] = true;
                int curEffort = Math.abs(heights[r][c] -  heights[newR][newC]);
                helper(heights, visited, newR, newC, Math.max(maxEffortOnThePath, curEffort));
                visited[newR][newC] = false;
            }
        }
    }
}

-------------------- Dijkstra --------------------------------------------
  /*
Dijkstra: 
点到点之间的effort为weight，这样一想，是不是就在让你以左上角坐标为起点，以右下角坐标为终点，计算起点到终点的最短路径？
只不过，这道题中评判一条路径是长还是短的标准不再是路径经过的权重总和，而是路径经过的权重最大值。
明白这一点，再想一下使用 Dijkstra 算法的前提，加权有向图，没有负权重边，求最短路径，OK，可以使用，

time： 
ime Complexity :O(m⋅nlog(m⋅n)), where m is the number of rows and n is the number of columns in matrix. It will take O(m⋅n) time to visit every cell in the matrix. The priority queue will contain at most m·n cells, so it will take O(log(m⋅n)) time to re-sort the queue after every adjacent cell is added to the queue. This given as total time complexiy as O(m⋅nlog(m⋅n))

Space: O(mn) -> maximun m*n nodes in queue 

https://www.notion.so/Graph-04339574fa6548a88d31e82fadaf6ee6

*/
class State {
    int x; 
    int y;
    int effortFromStart; // 从起点 (0, 0) 到当前位置的最小体力消耗（距离）
    public State(int x, int y, int effortFromStart) {
        this.x = x;
        this.y = y;
        this.effortFromStart = effortFromStart;
    }
}

class Solution {
    public int minimumEffortPath(int[][] heights) {
        // 1. 定义dp[]
        int[][] minEfforts = new int[heights.length][heights[0].length];
        for (int[] i : minEfforts) {
            Arrays.fill(i, Integer.MAX_VALUE);
        }
       
            
        minEfforts[0][0] = 0; 
        // 2. queue to store each node 
        PriorityQueue<State> q = new PriorityQueue<State>((a,b) -> a.effortFromStart - b.effortFromStart); // 从effort最小的处理起
        q.offer(new State(0, 0, 0)); // starting point到自己的effort为0 
        
        // 3. 更新
        while (!q.isEmpty()) {
            State cur = q.poll();
            int curEffortFromStart = cur.effortFromStart;
            
            if (curEffortFromStart > minEfforts[cur.x][cur.y]) { //最重要一步：说明此node不是min effort了
                continue; 
            }
            // 跟新自己 ？ 
            minEfforts[cur.x][cur.y] = curEffortFromStart; 
            
            // 此时cur Node代表从start到cur的min effort
            if (cur.x == heights.length - 1 && cur.y == heights[0].length - 1) {
                return curEffortFromStart; 
            }
            
            // 跟新邻居
            for (int[] neibor : getNeibors(heights, cur.x, cur.y)) {
                //得到邻居在这条path上的 effot 
                int neiborEffort = Math.max(curEffortFromStart, Math.abs(heights[cur.x][cur.y] - heights[neibor[0]][neibor[1]]));
                int neiborX = neibor[0];
                int neiborY = neibor[1];
                // 跟新从start到邻居的min effort，并且入栈
                if (neiborEffort < minEfforts[neiborX][neiborY]) {
                    minEfforts[neiborX][neiborY] = neiborEffort;
                    q.offer(new State(neiborX, neiborY, neiborEffort));
                }
            }
        }
        return -1; // 正常情况不会到这里
    }
    
    private List<int[]> getNeibors(int[][] heights, int r, int c) {
        List<int[]> res = new ArrayList<int[]>();
        
        int[] row = {1, -1, 0, 0};
        int[] col ={0, 0, 1, -1}; 
        for (int i = 0; i < row.length; i++) {
            int newR = r + row[i];
            int newC = c + col[i];
            if (newR >= 0 && newR < heights.length && newC >= 0 && newC < heights[0].length) {
                res.add(new int[]{newR, newC});
            }
        }
        return res; 
    }
}
