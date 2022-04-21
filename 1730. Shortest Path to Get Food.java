https://leetcode.com/problems/shortest-path-to-get-food/

you are starving and you want to eat food as quickly as possible. You want to find the shortest path to arrive at any food cell.

You are given an m x n character matrix, grid, of these different types of cells:

'*' is your location. There is exactly one '*' cell.
'#' is a food cell. There may be multiple food cells.
'O' is free space, and you can travel through these cells.
'X' is an obstacle, and you cannot travel through these cells.
You can travel to any adjacent cell north, east, south, or west of your current location if there is not an obstacle.

Return the length of the shortest path for you to reach any food cell. If there is no path for you to reach food, return -1.

 

Example 1:


Input: grid = [["X","X","X","X","X","X"],["X","*","O","O","O","X"],["X","O","O","#","O","X"],["X","X","X","X","X","X"]]
Output: 3
Explanation: It takes 3 steps to reach the food.
Example 2:


Input: grid = [["X","X","X","X","X"],["X","*","X","O","X"],["X","O","X","#","X"],["X","X","X","X","X"]]
Output: -1
Explanation: It is not possible to reach the food.
Example 3:


Input: grid = [["X","X","X","X","X","X","X","X"],["X","*","O","X","O","#","O","X"],["X","O","O","X","O","O","X","X"],["X","O","O","O","O","#","O","X"],["X","X","X","X","X","X","X","X"]]
Output: 6
Explanation: There can be multiple food cells. It only takes 6 steps to reach the bottom food.
 

Constraints:

m == grid.length
n == grid[i].length
1 <= m, n <= 200
grid[row][col] is '*', 'X', 'O', or '#'.
The grid contains exactly one '*'.

-------------------------------------- BFS + print Path ------------------------------------------------------

class Solution {
    public int getFood(char[][] grid) {
        Map<int[], int[]> map = new HashMap<int[], int[]>(); // itself - parent 
        int[] start = new int[2];
        // 1. find start location
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[0].length; j++) {
                if (grid[i][j] == '*') {
                    start[0] = i;
                    start[1] = j;
                    break;
                }
            }
        }
        
        // bfs to find shortest path
        Queue<int[]> q = new LinkedList<int[]>(); 
        // Set<int[]> visited = new HashSet<int[]>(); 不行，set无法比较int[], 解决是mark visited 为 "X"
        int steps = 0;
        q.offer(start);
        int[] rowCal = {1, -1, 0, 0};
        int[] colCal = {0, 0, 1, -1}; 
        while (!q.isEmpty()) {
            int layer = q.size();
            for (int n = 0; n < layer; n++) {
                int[] curLocal = q.poll();
                char curCell = grid[curLocal[0]][curLocal[1]];
                // add it's neibors into queue, visited, and map 
                for (int i = 0; i < rowCal.length; i++) {
                        int neiborRow = curLocal[0] + rowCal[i];
                        int neiborCol = curLocal[1] + colCal[i]; 
                        if (neiborRow >=0 && neiborRow < grid.length && neiborCol >=0 && neiborCol < grid[0].length && grid[neiborRow][neiborCol] != 'X') {
                            int[] neibor = new int[] {neiborRow, neiborCol};
                            map.put(neibor, curLocal); // add it's relationsihp to parent into map
                            
                            if (grid[neibor[0]][neibor[1]] == '#') {
                                steps++;
                                // print path 
                                printPath(neibor, map);

                                return steps; 
                            }
                            
                            q.offer(neibor);
                            grid[neiborRow][neiborCol] = 'X'; // mark as visited 
                        }
                    
                }
            }
            steps++; 
        }
        return -1;   
    }
    
    private void printPath(int[] cur,  Map<int[], int[]> map) {
        while (map.containsKey(cur)) {
            System.out.println(cur[0] + " " + cur[1] + " ->");
            cur = map.get(cur);
        }
    }
} 

