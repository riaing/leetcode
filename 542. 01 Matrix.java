Given a matrix consists of 0 and 1, find the distance of the nearest 0 for each cell.

The distance between two adjacent cells is 1.
Example 1: 
Input:

0 0 0
0 1 0
0 0 0
Output:
0 0 0
0 1 0
0 0 0
Example 2: 
Input:

0 0 0
0 1 0
1 1 1
Output:
0 0 0
0 1 0
1 2 1
Note:
The number of elements of the given matrix will not exceed 10,000.
There are at least one 0 in the given matrix.
The cells are adjacent in only four directions: up, down, left and right.
----------------------BFS----------------------------------------------------------------------------------
//思路是全局的dfs，看成图。首先找到所有的0 node，加入queue，然后BFS开始遍历四周的node，如果这个node不为0，并且没被访问过（有可能已经被更新了），那么我们给这个node的值+1，并且把这个node加入queue。
// time: O（row*col) node进入queue只会有一次。
//space：O（row*col）：如果所有的都是0的话，所有node入queue
//注意：queue中存的是position，所以先定义Position class。也可以用array，pair等解决

class Solution {
    class Position {
        int row;
        int col;
        public Position(int row, int col) {
            this.row = row;
            this.col = col;
        }
    }
    public int[][] updateMatrix(int[][] matrix) {
        // corner case 
        if (matrix == null || matrix.length == 0) {
            return new int[matrix.length][matrix[0].length];
        }
       
        bfs(matrix);
        return matrix;  
    }
    
    private void bfs(int[][] matrix) {
        // find all 0s and add it into the queue. for 1, we give an Max value
        Queue<Position> queue = new LinkedList<Position>();
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[0].length; j++) {
                if (matrix[i][j] == 0) {
                    queue.offer(new Position(i, j));
                }
                else {
                    matrix[i][j] = Integer.MAX_VALUE;
                }
            }
        }

        int[] row = {1,-1,0,0};
        int[] col = {0,0,1,-1};
        while (!queue.isEmpty()) {
            int size = queue.size();
            for (int n = 0; n < size; n++) {
                Position cur = queue.poll();
                for (int k = 0; k < 4; k++) {
                    int newRow = cur.row + row[k];
                    int newCol = cur.col + col[k];
       
                    if (newRow >= 0 && newRow < matrix.length && newCol >= 0 && newCol < matrix[0].length) {    // if the neighbor node has been visited, (either updated by other cells or itself is 0), skip
                        if (matrix[newRow][newCol] <= matrix[cur.row][cur.col]+1) {
                           continue; 
                        }
                        // we updated its neighbor by adding 1 step
                        queue.offer(new Position(newRow, newCol));
                        matrix[newRow][newCol] = matrix[cur.row][cur.col] +1;
                    }
                }
            }
        }
    }
}
