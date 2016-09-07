Write an efficient algorithm that searches for a value in an m x n matrix. This matrix has the following properties:

Integers in each row are sorted from left to right.
The first integer of each row is greater than the last integer of the previous row.
For example,

Consider the following matrix:

[
  [1,   3,  5,  7],
  [10, 11, 16, 20],
  [23, 30, 34, 50]
]
Given target = 3, return true.

public class Solution {
    public boolean searchMatrix(int[][] matrix, int target) {
        int row = matrix.length;
        int col = matrix[0].length;
        int start = 0;
        int end = row -1;
        
        while(start < end){ //先找到对应的行
            int mid = start + (end +1 - start) /2; 
            if(matrix[mid][0] == target){
                return true;
            }
            else if(matrix[mid][0] < target){
                start = mid ;  //这里是target有可能出现在mid这一行。
                //同时注意line9 要加1， 考虑两个元素时，会一直卡在这里，因为start 总没变。
            }
            else{
                end = mid -1;
            }
        }
        
        int colstart = 0;
        int colend = col-1;
        
        while(colstart < colend){ //在行里找对应的数
            int colmid = colstart+ (colend -colstart)/2;
            if(matrix[start][colmid] ==target){
                return true;
            }
            
            else if(matrix[start][colmid]<target){
                colstart = colmid+1 ;
                
            }
            else{
                colend = colmid -1;
            }
        }
        
        if(matrix[start][colstart] == target){
            return true;
        }
        else{
            return false;
        }
        
        
    }
}
