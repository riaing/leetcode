
Given a m x n matrix, if an element is 0, set its entire row and column to 0. Do it in place.


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
