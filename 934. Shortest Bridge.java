
In a given 2D binary array A, there are two islands.  (An island is a 4-directionally connected group of 1s not connected to any other 1s.)

Now, we may change 0s to 1s so as to connect the two islands together to form 1 island.

Return the smallest number of 0s that must be flipped.  (It is guaranteed that the answer is at least 1.)

 

Example 1:

Input: [[0,1],[1,0]]
Output: 1
Example 2:

Input: [[0,1,0],[0,0,0],[0,0,1]]
Output: 2
Example 3:

Input: [[1,1,1,1,1],[1,0,0,0,1],[1,0,1,0,1],[1,0,0,0,1],[1,1,1,1,1]]
Output: 1
 

Note:

1 <= A.length = A[0].length <= 100
A[i][j] == 0 or A[i][j] == 1
 

-------------------DFS+BFS--------------------------------------------------------------------------------------------------
/**先DFS找到第一个岛，把这个岛所有的1放进queue，并且mark下这个岛的1（比如1变成-1），接下来才不会和另一个岛的1搞混；然后BFS这个岛，找到离另一个岛的最短距离（当碰到1时，说明碰到了下一个岛）。
Time: O(m*n) BFS DFSd都有可能遍历整个A的node
Space: O(m*n)
Space: O(m*n)

在找第一个岛，bfs or dfs both ok, here I just used dfs. 
**/

class Solution {
    public int shortestBridge(int[][] A) {
        int[] row = {1,-1,0,0};
        int[] col ={0,0,1,-1};
        //Stores all 1s of the first island 
        Queue<int[]> queue = new LinkedList<int[]>();
        
        //find first island, put all 1s into queue, and mark then as -1 on graph.
        boolean found = false;
        for (int i = 0; i < A.length; i++) {
            if (found) {
                break;
            }
            for (int j = 0; j < A[0].length; j++) {
                if (A[i][j] == 1) {
                    dfs(queue, A, i, j, row, col);
                    found = true;
                    break;
                }
            }
        }
        
        return bfs(queue, A, row, col);
        
    }
    
    private void dfs(Queue<int[]> queue, int[][] A, int i, int j, int[] row, int[] col) {
        
        if(i < A.length && i >= 0 && j < A[0].length && j >= 0) {
            if (A[i][j] == 1) {
                queue.offer(new int[]{i,j});
                A[i][j] = -1;
                for (int n = 0; n < 4; n++) {
                    dfs(queue, A, i+row[n], j+col[n], row, col);
                } 
            }
        }
    }
    
    //find the shortest distance 
    private int bfs(Queue<int[]> queue, int[][] A,int[] row, int[] col) {
        int step = 0;
        // queue will store either 0 or -1, -1 means the node is visited(before is 0) or island(before is 1)
        while(!queue.isEmpty()) {
            step++;
            int size = queue.size();
            for (int i = 0; i< size; i++) {
                int[] cur = queue.poll();
                for (int n = 0; n<4; n++) {
                    int newRow = cur[0] + row[n];
                    int newCol = cur[1] + col[n];
                    if (newRow < A.length && newRow >= 0 && newCol < A[0].length && newCol >= 0 && A[newRow][newCol] != -1) {
                        if (A[newRow][newCol] == 1) {
                            // now, step is "how many steps to reach the other island, so at this step, we reached the island. so we need back one step "
                            return step -1;
                        }
                        queue.offer(new int[]{newRow, newCol});
                        // so we know this node is visited
                        A[newRow][newCol] = -1;
                    }
                }
            }
        }
        return step; 
    }
    
}
