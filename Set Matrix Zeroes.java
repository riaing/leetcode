
Given a m x n matrix, if an element is 0, set its entire row and column to 0. Do it in place.
 O(mn)解法：克隆原来的matrix，然后扫描原来的matrix，遇到0，则在克隆版本中将对应的行列置0。
 

O(m+n)解法：用两个bool数组O(n)和O(m)，分别记录每行和每列的是否需要被置0。最后根据这两个数组来置0整个矩阵。

public class Solution {
    int m, n; 
    public void setZeroes(int[][] matrix) {
        if(matrix == null || matrix.length == 0 || matrix[0].length == 0){
            return; 
        }
         m = matrix.length;
         n = matrix[0].length;
         
        boolean[] emptyRow = new boolean[m]; //boolean[] 存储每行没列是否需要变0
        boolean[] emptyCol = new boolean[n]; 
        
        for (int i = 0; i < m; i ++){
            for(int j = 0; j < n; j ++){
                if (matrix[i][j] == 0 ){ //若这个element==0； 这一行这一列都得变0
                    emptyRow[i] = true;
                    emptyCol[j] = true; 
                }
            }
        }
        
        for (int x = 0 ; x < m ;x ++){
            for (int y = 0; y < n ; y ++){
            if(emptyRow[x] == true || emptyCol[y] == true){ //如果这一行或这一列曾经有一个0，这行/这列每个元素都得变0 
                matrix[x][y] = 0; 
               
            }
        }
    }
        
    }

}
-------------------O(1)解法：用第0行和第0列来记录第1 ~ m-1行和第1 ~ n-1列是否需要置0。而用两个变量记录第0行和第0列是否需要置0。in space

public class Solution {
    int m, n; 
    public void setZeroes(int[][] matrix) {
        if(matrix == null || matrix.length == 0 || matrix[0].length == 0){
            return; 
        }
         m = matrix.length;
         n = matrix[0].length;
         
        boolean emptyRow0 = false;
        boolean emptyCol0 = false;
        
        //储存第一行信息，因为之后第一行列用于存boolean信息，这里得先存储
        for( int i= 0; i< m ; i++){ //判断第一列是否为零
            if (matrix[i][0] == 0){
                emptyCol0 = true;
            }
        }
        
        for( int j= 0; j< n ; j++){ //判断第一行是否为零
            if (matrix[0][j] == 0){
                emptyRow0 = true;
            }
        }
        
        //判断其他行列信息，存到第一行列之中
        for (int i = 1; i < m; i ++){
            for(int j = 1; j < n; j ++){
                if (matrix[i][j] == 0 ){ //若这个element==0； 
                    matrix[i][0] =  0; //第一列的对应元素变0
                    matrix[0][j] = 0; //第一行的对应元素变0 
                }
            }
        }
        
        for (int  i = 1 ; i < m ; i ++){
            for (int j = 1; j < n ; j ++){
                if(matrix[i][0] == 0 || matrix[0][j] == 0){ //如果这一行或这一列曾经有一个0，这行/这列每个元素都得变0 
                    matrix[i][j] = 0; 
                }
            }
        }
        
        //回到第一行列，如果曾经有零，设置整行/列为零
        if(emptyRow0){
            for(int i = 0; i < n; i ++ ){
                matrix[0][i] = 0; //注意！容易写错
            }
        }
        
           if(emptyCol0){
            for(int j = 0; j < m; j ++ ){
                matrix[j][0] = 0; //容易错
            }
        }
        
        
    }

    
  
}
