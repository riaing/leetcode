In an infinite chess board with coordinates from -infinity to +infinity, you have a knight at square [0, 0].

A knight has 8 possible moves it can make, as illustrated below. Each move is two squares in a cardinal direction, then one square in an orthogonal direction.


Return the minimum number of steps needed to move the knight to the square [x, y]. It is guaranteed the answer exists.

 

Example 1:

Input: x = 2, y = 1
Output: 1
Explanation: [0, 0] → [2, 1]
Example 2:

Input: x = 5, y = 5
Output: 4
Explanation: [0, 0] → [2, 1] → [4, 2] → [3, 4] → [5, 5]
 

Constraints:

-300 <= x, y <= 300
0 <= |x| + |y| <= 300

---------------------------- BFS -----------------------------------
class Solution {
    public int minKnightMoves(int x, int y) {
        int[][] directions = {{-2, -1}, {-1, -2}, {1, -2}, {2, -1}, {2, 1}, {1, 2}, {-1, 2}, {-2, 1}};
        Queue<int[]> q = new LinkedList<int[]>();
        q.offer(new int[] {0, 0});
        
        
        // - Rather than using the inefficient HashSet, we use the bitmap
        //     otherwise we would run out of time for the test cases.
        // - We create a bitmap that is sufficient to cover all the possible
        //     inputs, according to the description of the problem.
        
        boolean[][] visited = new boolean[607][607];
        visited[302][302] = true; 
        // Set<String> visited = new HashSet<>();
        // visited.add("0,0");
        int step = 0; 
        while (!q.isEmpty()) {
            int size = q.size();
            for (int i = 0; i <size; i++) {
                int[] cur = q.poll();
                if (cur[0] == x && cur[1] == y) {
                    return step;
                }
                // add neibor 
                for (int k = 0; k < directions.length; k++) {
                    int newR = cur[0] + directions[k][0];
                    int newC = cur[1] + directions[k][1];
                    //if (!visited.contains(newR+","+newC)) {
                    if (!visited[newR+302][newC+302]) {
                        q.offer(new int[]{newR, newC});
                        visited[newR+302][newC+302] = true;
                    }
                }
            }
            step++;
        }
        return -1; 
    }
}
