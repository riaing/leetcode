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
----------------------3.22.19 with 九章模板------------------------------------------------------------------------
  解法：先二分行，找到可能在哪一行，再二分列
  Time: ROW: O(logm). COL: O(logn) --> O(logm) + O(logn) = O (log(m*n))
  class Solution {
    public boolean searchMatrix(int[][] matrix, int target) {
        // [], [[]] as corner case 
        if (matrix == null || matrix.length == 0 || matrix[0].length == 0) {
            return false; 
        }
        int row = findRow(matrix, target);
        
        // binery search in this row to find if target exists
        return findInCol(matrix[row], target);
        
    }
    private int findRow(int[][] matrix, int target) {
        int start = 0;
        int end = matrix.length -1;
        while(start + 1 < end) {
            int mid = start + (end-start) / 2;
            if (matrix[mid][0] <= target) {
                start = mid;
            }
            else {
                end = mid;
            }
        }
        //如果end这一行的开头已经大于target了，那么target肯定不在这一行
       
        if (matrix[end][0] > target) {
            return start;
        } 
        else {
            return end;
        }
    }
    
    private boolean findInCol(int[] row, int target) {
        int start = 0;
        int end = row.length - 1;
        while (start + 1 < end) {
            int mid = start + (end - start) / 2;
            if (row[mid] == target) {
                return true;
            }
            else if (row[mid] > target) {
                end = mid;
            }
            else {
                start = mid;
            }
        }
        return row[end] == target || row[start] == target;
    }
}
-----------------------------------3.22.19 update:一遍二分，treat as 1D array-----------------------------------------------------
  /* treat as a sorted array, 2D ->1D
找到某个index的行列方法：
row = index / #columns
col = index % #columns
*/
class Solution {
    public boolean searchMatrix(int[][] matrix, int target) {
        // [], [[]] as corner case 
        if (matrix == null || matrix.length == 0 || matrix[0].length == 0) {
            return false; 
        }
        return findTargetInSortedArray(matrix, target);
  
    }
    
    private boolean findTargetInSortedArray(int[][] matrix, int target) {
        // 重点1， 用long，因为可能overflow
        long start = 0;
        long end = matrix.length * matrix[0].length - 1;
        while (start + 1 < end) {
            long mid = start + (end - start) / 2;
            //重点2， 给个index，判断在2D array中行列的计算
            int rowIndex = (int) mid / matrix[0].length;
            int colIndex = (int) mid % matrix[0].length;
            if (matrix[rowIndex][colIndex] == target) {
                return true;
            }
            else if (matrix[rowIndex][colIndex] > target) {
                end = mid;
            }
            else {
                start = mid;
            }
        }
        // start = 0. end = 1 
        int startRow = (int) start / matrix[0].length;
        int startCol =  (int) start % matrix[0].length;
        int endRow = (int) end / matrix[0].length; 
        int endCol =  (int) end % matrix[0].length; 
        
        return matrix[startRow][startCol] == target || matrix[endRow][endCol] == target;
    }
}
