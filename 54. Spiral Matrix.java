Given an m x n matrix, return all elements of the matrix in spiral order.

 

Example 1:


Input: matrix = [[1,2,3],[4,5,6],[7,8,9]]
Output: [1,2,3,6,9,8,7,4,5]
Example 2:


Input: matrix = [[1,2,3,4],[5,6,7,8],[9,10,11,12]]
Output: [1,2,3,4,8,12,11,10,9,5,6,7]
 

Constraints:

m == matrix.length
n == matrix[i].length
1 <= m, n <= 10
-100 <= matrix[i][j] <= 100
  
  
  ----------------------- coding ----------
  /*
similar: spider matrix: https://www.notion.so/Rippling-510eba04ac86495796b75aad11d4f6ac 
O(mn)
*/
class Solution {
    int t, b, l, r;
    int[][] matrix;
    public List<Integer> spiralOrder(int[][] matrix) {
        this.matrix = matrix;
        final int m = matrix.length;
        final int n = matrix[0].length;
        t = 0; b = m-1;
        l = 0; r = n-1;
        List<Integer> list = new ArrayList<>();
        int i=0;
        while (t <= b && l <= r) {
            if (i == 0)
                Right(list);
            if (i == 1)
                Down(list);
            if (i == 2)
                Left(list);
            if (i == 3)
                Up(list);
            i++;
            i %= 4;
        }
        return list;
    }
    
    void Right(final List<Integer> list) {
        for (int j=l; j<=r; j++) {
            list.add(matrix[t][j]);
        }
        t++;
    }
    void Left(final List<Integer> list) {
        for (int j=r; j>=l; j--) {
            list.add(matrix[b][j]);
        }
        b--;
    }
    void Down(final List<Integer> list) {
        for (int i=t; i<=b; i++) {
            list.add(matrix[i][r]);
        }
        r--;
    }
    void Up(final List<Integer> list) {
        for (int i=b; i>=t; i--) {
            list.add(matrix[i][l]);
        }
        l++;
    }
}
