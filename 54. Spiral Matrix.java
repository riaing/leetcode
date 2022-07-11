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

------------- 方法2，只要确认四个边界 -----------------
 List<Integer> spiralOrder(int[][] matrix) {
    int m = matrix.length, n = matrix[0].length;
    int upper_bound = 0, lower_bound = m - 1;
    int left_bound = 0, right_bound = n - 1;
    List<Integer> res = new LinkedList<>();
    // res.size() == m * n 则遍历完整个数组
    while (res.size() < m * n) {
        if (upper_bound <= lower_bound) {
            // 在顶部从左向右遍历
            for (int j = left_bound; j <= right_bound; j++) {
                res.add(matrix[upper_bound][j]);
            }
            // 上边界下移
            upper_bound++;
        }
        
        if (left_bound <= right_bound) {
            // 在右侧从上向下遍历
            for (int i = upper_bound; i <= lower_bound; i++) {
                res.add(matrix[i][right_bound]);
            }
            // 右边界左移
            right_bound--;
        }
        
        if (upper_bound <= lower_bound) {
            // 在底部从右向左遍历
            for (int j = right_bound; j >= left_bound; j--) {
                res.add(matrix[lower_bound][j]);
            }
            // 下边界上移
            lower_bound--;
        }
        
        if (left_bound <= right_bound) {
            // 在左侧从下向上遍历
            for (int i = lower_bound; i >= upper_bound; i--) {
                res.add(matrix[i][left_bound]);
            }
            // 左边界右移
            left_bound++;
        }
    }
    return res;
}
