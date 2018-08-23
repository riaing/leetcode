Write an efficient algorithm that searches for a value in an m x n matrix. This matrix has the following properties:

Integers in each row are sorted in ascending from left to right.
Integers in each column are sorted in ascending from top to bottom.
Example:

Consider the following matrix:

[
  [1,   4,  7, 11, 15],
  [2,   5,  8, 12, 19],
  [3,   6,  9, 16, 22],
  [10, 13, 14, 17, 24],
  [18, 21, 23, 26, 30]
]
Given target = 5, return true.

Given target = 20, return false.

 0------------ 
 首先分析如果从左上角开始搜索，由于元素升序为自左向右和自上而下，因此如果target大于当前搜索元素时还有两个方向需要搜索，不太合适。
如果从右上角开始搜索，由于左边的元素一定不大于当前元素，而下面的元素一定不小于当前元素，因此每次比较时均可排除一列或者一行元素（
大于当前元素则排除当前行，小于当前元素则排除当前列，由矩阵特点可知），可达到题目要求的复杂度。
https://algorithm.yuanbin.me/zh-hans/binary_search/search_a_2d_matrix_ii.html 

 class Solution {
    public boolean searchMatrix(int[][] matrix, int target) {
        if (matrix == null || matrix.length == 0 || matrix[0].length == 0) {
            
            return false;
        }
        // iterate from left bottom 
        int row = matrix.length - 1;
        int col = 0;
        while (row >= 0 && row < matrix.length && col >= 0 &&  col < matrix[0].length) {
           
            if (matrix[row][col] == target) {
                return true;
            }
            else if (matrix[row][col] < target) {
                col++;
            }
            else{
                
                row--;
            }
        }
        return false;
    }
}
