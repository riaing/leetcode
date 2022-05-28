/*

https://www.1point3acres.com/bbs/thread-673701-1-1.html 
Part 1:
Given a city map as a matrix of size N x N. Each cell has a house with 0 or more people living in it. House at cell (x,y) caught fire and it's spreading alarmingly in all 4 directions per unit time. There is a fire station at cell (a,b) with infinite firemen supply, a fireman can move in any of possible 4 directions per unit time. If a fire reaches a house ahead of the fireman, people living in that house die otherwise they will be saved. Find the maximum number of people that can be saved.
解： 求从火到某个点的mahanttan distance。和消防员到点的mahanttan distance。 可直接做或者BFS 

Part 2: Some cells are blocked; neither the fire not the fireman can traverse through it.
解： BFS。 仍然是每个点由火到和由消防员到的最短distance。

Part 3: Today is vacation in the city and a limited number of firemen are available on duty (say K).
DP. 看笔记吧 
*/

public class Main {
    public static void main(String[] args) {
        System.out.println("Hello World!");
        int[][] matrix = {{1,1,1}, {1,1,1}, {1,1,1}};
        int[] fire = {0,0};
        int[] station = {2,2};
        int res = savePeople1(matrix, fire, station);
        System.out.println(res);
    }
    
    // 第一问：就是求每个点火要几步到，消防员要几步到。消防员早到或同时到则救 -> 求每个点到火&员的mahattan distance
    public static int savePeople1(int[][] matrix, int[] fire, int[] station) {
        int saved = 0; 
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[0].length; j++) {
                int disToFire = Math.abs(i - fire[0]) + Math.abs(j - fire[1]);
                int disToMan = Math.abs(i - station[0]) + Math.abs(j - station[1]);
                if (disToMan <= disToFire) {
                    saved++; 
                }
            }
        }
        return saved; 
    }
    
    // 第二问：此方法也可以套第一问上。只是第一问用mahanttan distance 容易些 
    public static int savePeople2(int[][] matrix, int[] fire, int[] station) {
        // 火到每个点的最短距离
        int[][] disToFire = shortestDist(matrix, fire); 
        int[][] disToMan = shortestDist(matrix, station); 
        Arrays.stream(disToMan).forEach( o -> System.out.println(Arrays.toString(o)));
        int saved = 0; 
        // 某个点 消防员比火先到，或者同时到时，救下人 -> disToMan <= disToFire 
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[0].length; j++) {
                if (disToMan[i][j] <= disToFire[i][j]) {
                    saved++;
                }
            }
        }
        return saved; 
    }
    
    /* BFS 每个点到start的最短距离 */
    private static int[][] shortestDist(int[][] matrix, int[] startLoc) {
        int[][] distToStart = new int[matrix.length][matrix[0].length]; 
        Queue<int[]> q = new LinkedList<int[]>();
        Set<String> visited = new HashSet<String>();
        visited.add(startLoc[0] + "," + startLoc[1]);
        q.offer(startLoc);
        int step = 0;
        distToStart[startLoc[0]][startLoc[1]] = step;
        int[] row = {1, -1, 0, 0};
        int[] col = {0, 0, 1, -1}; 

         while (!q.isEmpty()) {
             int size = q.size(); 
             for (int i = 0; i < size; i++) {
                 int[] cur = q.poll();
                 distToStart[cur[0]][cur[1]] = step;
                for (int k = 0 ; k < row.length; k++) {
                    int newManRow = cur[0] + row[k];
                    int newManCol = cur[1] + col[k];
                    // 第二问 - 就排除那些不能到的点
                    if (newManRow >= 0 && newManRow < matrix.length && newManCol >= 0 && newManCol < matrix[0].length && !visited.contains(newManRow + "," + newManCol)) {
                        q.offer(new int[]{newManRow, newManCol}); 
                        visited.add(newManRow + "," + newManCol); 
                    }
                }  
             }
             step++;
        }
        return distToStart; 
    }
}
