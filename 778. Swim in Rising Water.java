On an N x N grid, each square grid[i][j] represents the elevation at that point (i,j).

Now rain starts to fall. At time t, the depth of the water everywhere is t. You can swim from a square to another 4-directionally adjacent square if and only if the elevation of both squares individually are at most t. You can swim infinite distance in zero time. Of course, you must stay within the boundaries of the grid during your swim.

You start at the top left square (0, 0). What is the least time until you can reach the bottom right square (N-1, N-1)?

Example 1:

Input: [[0,2],[1,3]]
Output: 3
Explanation:
At time 0, you are in grid location (0, 0).
You cannot go anywhere else because 4-directionally adjacent neighbors have a higher elevation than t = 0.

You cannot reach point (1, 1) until time 3.
When the depth of water is 3, we can swim anywhere inside the grid.
Example 2:

Input: [[0,1,2,3,4],[24,23,22,21,5],[12,13,14,15,16],[11,17,18,19,20],[10,9,8,7,6]]
Output: 16
Explanation:
 0  1  2  3  4
24 23 22 21  5
12 13 14 15 16
11 17 18 19 20
10  9  8  7  6

The final route is marked in bold.
We need to wait until time 16 so that (0, 0) and (4, 4) are connected.
Note:

2 <= N <= 50.
grid[i][j] is a permutation of [0, ..., N*N - 1].

------------------------Bineray search + BFS----------------------------------------------------------------
/**
Bineray search时间，然后对于每个时间，BFS看是否能找到解。
Time O(N^2*log(N^2)) -> O(N^2*logN)
Space Complexity: O(N^2) the maximum size of the stack.
*/
class Solution {
    public int swimInWater(int[][] grid) {
        // Bineray search O(log(N^2))
        int n = grid.length;
        int lo = 0;
        int hi = n*n; //根据左闭右开，这里其实是n*n-1+1
        while(lo < hi) {
            int mid = lo + (hi - lo) / 2;
            if (!canReachEnd(mid, grid)) {
                lo = mid + 1;
            }
            else {
                hi = mid;
            }
        }
        return lo;  
    }
    
    // BFS the matrix to find if can reach the end in time T, O(n^2)
    private boolean canReachEnd(int T, int[][] grid) {
        if (grid[0][0] > T) {
            return false;
        }
        
        boolean[][] used = new boolean[grid.length][grid.length];
        Queue<int[]> queue = new LinkedList<int[]>();
        queue.offer(new int[]{0,0});
        used[0][0] = true;
        int[] row = {1,-1,0,0};
        int[] col = {0, 0, 1,-1};
        while (!queue.isEmpty()) {
            int size = queue.size();
            for (int i = 0; i < size; i++) {
                int[] position = queue.poll();
                if (position[0] == grid.length-1 && position[1] == grid.length-1) {
                    return true;
                }
                for (int j = 0; j < 4; j++) {
                    int newRow = position[0] + row[j];
                    int newCol = position[1] + col[j];
                    if (newRow >= 0 && newRow < grid.length && newCol >= 0 && newCol < grid.length && !used[newRow][newCol] && grid[newRow][newCol] <= T) {
                        queue.offer(new int[]{newRow, newCol});
                        used[newRow][newCol] = true;
                    }
                }
            }
        }
        return false;
    }
}

-------------------------PriorityQueue-----------------------------------------------------------------------------------
 /**
Time Complexity: O(N^2 log N). We may expand O(N^2) nodes, and each one requires O(logN) time to perform the heap operations(offer).

Space Complexity: O(N^2) the maximum size of the heap.
*/
class Solution {
    public int swimInWater(int[][] grid) {
        return canReachEnd(grid);
    }
    

    private int canReachEnd(int[][] grid) {
        int answer = 0; 
        boolean[][] used = new boolean[grid.length][grid.length];
        // always go to the smallest number in next step. 
        Comparator<int[]> comparator = new Comparator<int[]>() {
          @Override
          public int compare(int[] a, int[] b) {
              return grid[a[0]][a[1]] - grid[b[0]][b[1]];
          }
        };
        PriorityQueue<int[]> queue = new PriorityQueue<int[]>(comparator);
        
        queue.offer(new int[]{0,0});
        used[0][0] = true;
        int[] row = {1,-1,0,0};
        int[] col = {0, 0, 1,-1};
        
        while (!queue.isEmpty()) {
            int size = queue.size();
            for (int i = 0; i < size; i++) {
                int[] position = queue.poll();
                answer = Math.max(answer, grid[position[0]][position[1]]);
                if (position[0] == grid.length-1 && position[1] == grid.length-1) {
                    return answer;
                }
                for (int j = 0; j < 4; j++) {
                    int newRow = position[0] + row[j];
                    int newCol = position[1] + col[j];
                    if (newRow >= 0 && newRow < grid.length && newCol >= 0 && newCol < grid.length && !used[newRow][newCol]) {
                        queue.offer(new int[]{newRow, newCol});
                        used[newRow][newCol] = true;
                    }
                }
            }
        }
        throw null;
    }
}
