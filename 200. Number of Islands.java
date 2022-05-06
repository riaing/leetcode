Given a 2d grid map of '1's (land) and '0's (water), count the number of islands. An island is surrounded by water and is formed by connecting adjacent lands horizontally or vertically. You may assume all four edges of the grid are all surrounded by water.

Example 1:

11110
11010
11000
00000
Answer: 1

Example 2:

11000
11000
00100
00011
Answer: 3



public class Solution {
    private int m, n ;  
    public int numIslands(char[][] grid) {
        ---------判断方法之一，逐层递进
       if(grid == null){ // 三步判断条件逐一递进
           return 0 ; 
       }
        m = grid.length;  // heng 
        
        //考虑 grid[0]得存在！
        if(m == 0){
            return 0; 
        }
        n = grid[0].length; //zong 
        
        if  (n == 0 ){
            return 0; 
        }
        --------------- 方法之二，运用 or的性质
        if(grid == null || grid.length ==0 || grid[0].length == 0){ 
           return 0 ; 
       }
        
         m = grid.length;
         n = grid[0].length;
         
         注意：这里 or的三个顺序不可以更改，因为如果grid == null是第一判断，如果==null就立马return 0，不会再进判断grid。length ==0
         （不然会nullpointerexception）。
         if中不能把grid.length用变量替代。eg：不能把 m=...，n = ..写在if的前面，因为如果没先判断grid == null， m，n就会nullpointerexception。
         ---------------------------
        
        int count = 0; 
        for (int i = 0 ; i <m; i++ ){
            for (int j = 0 ; j <n; j++){
                if (grid[i][j] == '1'){
                    count ++; 
                    dfs(grid, i, j); 
                }
            }
        }
        return count; 
    }
    
    private void dfs(char[][] grid, int i, int j ){
       if(i<0 || i>=m || j <0 || j>= n){
           return;
       }
        if (grid[i][j] == '1') {
                grid[i][j] = '2'; 
                dfs(grid, i -1 , j );
                dfs(grid, i +1 , j );
                dfs(grid, i , j - 1 );
                dfs(grid, i , j + 1 );

               
        }

    }
}

---------BFS 解法----------------

public class Solution {
    private int m, n ;  
    public int numIslands(char[][] grid) {
       if(grid == null || grid.length ==0 || grid[0].length == 0){ // 三步判断条件逐一递进
           return 0 ; 
       }
        m = grid.length;  // heng 
        n = grid[0].length; //zong 
        int count = 0; 
        
        for(int i = 0 ; i< m ; i++){
            for(int j =0; j< n; j++){
                if(grid[i][j] == '1'){
                    count ++; 
                    bfs(grid, i, j) ;
                }
            }
        }
        return count; 

    }
    
    private void bfs(char[][] grid, int x, int y){
        int[] row = {1,-1,0,0};
        int[] col ={0,0,1,-1};
        grid[x][y] = '0';
        Queue<Integer> q = new LinkedList<Integer>() ; 
        int code = x*n+ y; //get the current ith element; 把坐标作为一个数存下来，就不用用两个Queue
        q.offer(code);
        while(q.size() != 0){
            code = q.poll();
            int i = code/n;
            int j =code%n ; //get the location。
           
             for (int k = 0; k < 4; k++) {
                int newRow = i+row[k];
                int newCol = j + col[k];
               
                if (newRow >= 0 && newRow < m && newCol >= 0 && newCol < n && grid[newRow][newCol] == '1') {
                    q.offer(newRow*n+newCol);
                    grid[newRow][newCol] = '0';
                }
            }
            
//             if(i > 0 && grid[i-1][j] == '1'){
//                 q.offer((i-1)*n +j);
//                 grid[i-1][j] ='0'; 
//             }
//             if(i < m-1  && grid[i+1][j] == '1'){ //can't be < m;  
//                 q.offer((i+1)*n +j);
//                 grid[i+1][j] ='0'; 
//             }
//             if(j < n-1 && grid[i][j+1] == '1'){ //can't be < n; 
//                 q.offer(i*n +j+1);
//                 grid[i][j+1] ='0'; 
//             }
//             if(j >0  && grid[i][j-1] == '1'){
//                 q.offer(i*n +j -1);
//                 grid[i][j-1] ='0'; 
//             }
            
        }
    }
}

--------- 2022 DFS -----------------------------------
    class Solution {
    public int numIslands(char[][] grid) {
        int cnt = 0;
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[0].length; j++) {
                if (grid[i][j] == '1') {
                    cnt++;
                    paint(grid, i, j);
                }
            }
        }
        return cnt; 
    }
    
    private void paint(char[][] grid, int r, int c) {
        // 先将此cell paint
        grid[r][c] = '0';
        //将四面八方paint成0
        int[] row = {1, -1, 0, 0};
        int[] col = {0, 0, 1, -1};
        for (int i = 0; i < row.length; i++) {
            int newRow = r + row[i];
            int newCol = c + col[i];
            if (newRow >= 0 && newRow < grid.length && newCol >= 0 && newCol < grid[0].length && grid[newRow][newCol] == '1') {
                paint(grid, newRow, newCol);
            }
        }
    }
 }
