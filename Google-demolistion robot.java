题： https://leetcode.com/playground/mPVaPnRe 

------------------ DFS brute force: o(2^nm) ---------------------------------------------------------------------- 
public class Main {
    public static void main(String[] args) {
        // int[][] grid = new int[6][6]; // true 
        // grid[0] = new int[] {0,0,1,1,0,0};
        // grid[1] = new int[] {0,0,1,1,0,0};
        // grid[2] = new int[] {0,0,0,1,0,0};
        // grid[3] = new int[] {0,0,1,1,0,0};
        // grid[4] = new int[] {0,0,1,1,0,0};
        // grid[5] = new int[] {0,0,1,1,0,0};
        
        // int[][] grid = new int[6][6]; // false 
        // grid[0] = new int[] {0,0,1,1,0,0};
        // grid[1] = new int[] {0,0,1,1,0,0};
        // grid[2] = new int[] {0,0,1,1,0,0};
        // grid[3] = new int[] {0,0,1,1,0,0};
        // grid[4] = new int[] {0,0,1,1,0,0};
        // grid[5] = new int[] {0,0,1,1,0,0};
        
        // int[][] grid = new int[2][2]; // true 
        // grid[0] = new int[] {0,1};
        // grid[1] = new int[] {1,0};
        
        // int[][] grid = new int[3][3]; // false
        // grid[0] = new int[] {0,1,1};
        // grid[1] = new int[] {1,1,0};
        // grid[2] = new int[] {1,0,0};
        
        int[][] grid = new int[3][3]; // true
        grid[0] = new int[] {0,1,0};
        grid[1] = new int[] {1,1,0};
        grid[2] = new int[] {1,0,0};
        
        
        
        boolean res = demolishRobot(3, grid);
        System.out.println(res);
    }
    
    public static boolean demolishRobot(int n, int[][] grid) {
        grid[0][0] = 2; // mark as visited 
        return helper(grid, 0, 0, false);
    }
    
    private static boolean helper(int[][] grid, int row, int col, boolean demolished) {
        if (row == grid.length - 1 && col == grid[0].length - 1) {
            return true;
        }
        
        // move to next 
        int[] rowCal = {1, -1, 0, 0};
        int[] colCal = {0, 0, 1, -1};
        for (int i = 0; i < rowCal.length; i++) {
            int newRow = row + rowCal[i];
            int newCol = col + colCal[i];
            if (newRow >= 0 && newRow < grid.length && newCol >= 0 && newCol < grid[0].length && grid[newRow][newCol] != 2) {
                // System.out.println("check " + newRow + " " + newCol + " demolish " + demolished);
                int curVal = grid[newRow][newCol];
                // // mark as visited;
                boolean possibleRes; 

                if (curVal == 0) {
                    // mark as visited;
                    grid[newRow][newCol] = 2;
                    possibleRes = helper(grid, newRow, newCol, demolished); 
                    if (possibleRes) {

                        return true;
                    }
                }
                else if (curVal == 1) {
                    if (demolished) { // 得找新的方法
                        continue;
                    }
                    // mark as visited;
                    grid[newRow][newCol] = 2; 
                    possibleRes = helper(grid, newRow, newCol, true);
                    if (possibleRes) {
                        return true;
                    }
                }
                // resume 
                grid[newRow][newCol] = curVal; 
            }
        }
        return false; 
    }
}

------------------ BFS 染色解法：第一问 + 第二问 -----------------------------------------
class Node {
    int r; 
    int c;
    boolean demolished;
    public Node(int r, int c, boolean demolished) {
        this.r = r;
        this.c = c; 
        this.demolished = demolished; 
    }
}

public class Main {
    public static void main(String[] args) {
        int[][] grid = new int[6][6]; // true, 1 
        grid[0] = new int[] {0,0,1,1,0,0};
        grid[1] = new int[] {0,0,1,1,0,0};
        grid[2] = new int[] {0,0,0,1,0,0};
        grid[3] = new int[] {0,0,1,1,0,0};
        grid[4] = new int[] {0,0,1,1,0,0};
        grid[5] = new int[] {0,0,1,1,0,0};
        
        int[][] grid2 = new int[6][6]; // false, 2 
        grid2[0] = new int[] {0,0,1,1,0,0};
        grid2[1] = new int[] {0,0,1,1,0,0};
        grid2[2] = new int[] {0,0,1,1,0,0};
        grid2[3] = new int[] {0,0,1,1,0,0};
        grid2[4] = new int[] {0,0,1,1,0,0};
        grid2[5] = new int[] {0,0,1,1,0,0};
        
        int[][] grid3 = new int[2][2]; // true, 1 
        grid3[0] = new int[] {0,1};
        grid3[1] = new int[] {1,0};
        
        int[][] grid4 = new int[3][3]; // false, 2
        grid4[0] = new int[] {0,1,1};
        grid4[1] = new int[] {1,1,0};
        grid4[2] = new int[] {1,0,0};
        
        int[][] grid5 = new int[3][3]; // true, 1 
        grid5[0] = new int[] {0,1,0};
        grid5[1] = new int[] {1,1,0};
        grid5[2] = new int[] {1,0,0};
        
        int[][] grid6 = new int[3][3]; // true, 1 
        grid6[0] = new int[] {0,1,0};
        grid6[1] = new int[] {0,1,1};
        grid6[2] = new int[] {1,0,0};
        
        List<int[][]> inputs = Arrays.asList(grid, grid2, grid3, grid4, grid5, grid6);
        // for (int[][] input : inputs) {
        //      boolean res = demolishRobot(input);
        //     System.out.println(res);
        // }
        
        // print 第二问
        // List<int[][]> inputs = new ArrayList<int[][]>();
        // inputs.add(grid6);
        int caseNum = 1;
        for (int[][] input : inputs) {
            // System.out.println(Arrays.deepToString(input));
            int res = minimalDemolish(input);
            System.out.println("test case " + caseNum + " : " + res);
            caseNum++;
        }
    }
    // 第一问：只能打一面墙，能否走到底
    /*
     BFS 染色解法：把第一圈遇到的1染色，那么接下来再遇到的1就是死胡同。注意用visited 避免重复 o(nm)
    */
    public static boolean demolishRobot(int[][] grid) {
        boolean[][] visited = new boolean[grid.length][grid[0].length];
        Queue<Node> q = new LinkedList<Node>();
        q.offer(new Node(0, 0, false));
        visited[0][0] = true; 
        
        while (!q.isEmpty()) {
            Node cur = q.poll(); 
            if (cur.r == grid.length -1 && cur.c == grid[0].length - 1) { // reach desitnation 
                return true; 
            }
            // move to next 
            int[] rowCal = {1, -1, 0, 0};
            int[] colCal = {0, 0, 1, -1};
            for (int i = 0; i < rowCal.length; i++) {
                int newRow = cur.r + rowCal[i];
                int newCol = cur.c + colCal[i];
                if (newRow >= 0 && newRow < grid.length && newCol >= 0 && newCol < grid[0].length && !visited[newRow][newCol]) {
                    int curVal = grid[newRow][newCol];
                    if (curVal == 0) {
                        q.offer(new Node(newRow, newCol, cur.demolished));
                        visited[newRow][newCol] = true;
                    }
                    if (curVal == 1 && !cur.demolished) {
                        q.offer(new Node(newRow, newCol, true));
                        visited[newRow][newCol] = true;
                    }
                }
            }
        }
        return false;
    }
    
    // 第二问，最少打通几面墙才能走到底
    /*
    BFS染色次数： 每次碰到1时加入List。找到每轮要染色的1.一圈圈扩展
    */
    public static int minimalDemolish(int[][] grid) {
        boolean[][] visited = new boolean[grid.length][grid[0].length];
        Queue<int[]> q = new LinkedList<int[]>(); // 存每个item的坐标
        q.offer(new int[]{0,0});
        visited[0][0] = true; 
        List<int[]> wallsToBreak = new LinkedList<int[]>();
        int steps = 0; 
        while(!q.isEmpty()) {
            while(!q.isEmpty()) {
                int[] curLocal = q.poll();
                if (curLocal[0] == grid.length - 1 && curLocal[1] == grid[0].length - 1) {
                    return steps; 
                }
                 // move to next 
                int[] rowCal = {1, -1, 0, 0};
                int[] colCal = {0, 0, 1, -1};
                for (int i = 0; i < rowCal.length; i++) {
                    int newRow = curLocal[0] + rowCal[i];
                    int newCol = curLocal[1] + colCal[i];
                    if (newRow >= 0 && newRow < grid.length && newCol >= 0 && newCol < grid[0].length && !visited[newRow][newCol]) {
                        int nextVal = grid[newRow][newCol];
                        if (nextVal == 0) {
                            q.offer(new int[]{newRow, newCol});
                        }
                        if (nextVal == 1) {
                            wallsToBreak.add(new int[]{newRow, newCol}); //说明当前轮遇到了墙，需要break
                        }
                         visited[newRow][newCol] = true;
                    }
                }
            } 
            // 当无路可走时，说明遇到了这一轮的所有墙
            steps++; // 需要打通这一圈的墙
            q = new LinkedList<int[]>(wallsToBreak);
            wallsToBreak = new LinkedList<int[]>();
            
            /* debug 
            System.out.println("step " + steps );
            for (int i = 0; i < wallsToBreak.size(); i++) {
                System.out.println("1s: " + wallsToBreak.get(i)[0] + " " + wallsToBreak.get(i)[1]);
            }
            */ 
        }
        return steps; 
    }
    
}
