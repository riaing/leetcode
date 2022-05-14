Given a 2D binary matrix filled with 0’s and 1‘s, find the largest rectangle containing only 1’s and return its area.

Example:

Input:
[
  ["1","0","1","0","0"],
  ["1","0","1","1","1"],
  ["1","1","1","1","1"],
  ["1","0","0","1","0"]
]
Output: 6 
----------------------------------------------------------------------------------------------------------------------
完全是Largest Rectangle in Histogram的在运用，1，算出每一行的每列上的最大高度 2，对于这样的每一行，找Largest Rectangle in Histogram 

https://www.cnblogs.com/lichen782/p/leetcode_maximal_rectangle.html 


class Solution {
    public int maximalRectangle(char[][] matrix) {
          int maxArea = 0;
        if(matrix == null || matrix.length == 0) {
            return maxArea;
        }
        //h 记录每一行上各列的最大高度，便于转换成Largest Rectangle in Histogram 问题
        // 注意这里列数要比matrix的列数多1，因为最后需要一个dummy var
        int[][] h = new int[matrix.length][matrix[0].length+1];
        // interate through each row to find the maximum height in each column at
        // such row, store the value in h[][]
        for (int i = 0; i< matrix.length; i++) {
            for (int j = 0; j<matrix[0].length; j++) {
                if (matrix[i][j] == '0') {
                    h[i][j] = 0;
                }
                //如果当前位置上为1，那么这个位置的高度就为上一行的高度+1
                else {
                    h[i][j] = i == 0 ? 1 : h[i-1][j] + 1;
                }
            }
        }
        
      
        for (int i = 0; i< matrix.length; i++) {
            maxArea = Math.max(maxArea, maxAreaInHist(h[i]));
        }
        return maxArea;
    }
    
    //转换成 Largest Rectangle in Histogram问题，这时的h[] 最后已经有了dummy var
    private int maxAreaInHist(int[] h) {
        Deque<Integer> stack = new LinkedList<Integer>();
        int area = 0;
        int i = 0;
        // 遍历这个数组，找到最大的rectangle
        while (i < h.length) {
            if (stack.isEmpty() || h[stack.peek()] <= h[i]) {
                stack.push(i);
                i++;
            }
            // 如果小于stack的top，那么我们pop stack，找到i往左的rectange
            else {
                int cur = stack.pop();
                area = Math.max(area, h[cur] * (stack.isEmpty() ? i : i - stack.peek() -1));
            }
        }
        return area;
    }
}

--------- 2022.5 常规做法：找两点 --------------------
  /*
必须掌握的解法：求matrix中两点围成的长方形的面积

本题变一点：求任意两点围成的长方形中的1的总数。如果此时长*宽 = 1的总数，说明围成的是长方形
1. 求（0，0）到任意点围成的长方形中1的总数  -> O（n^2)
dp[i][j] - 以（i.j） 为右下角，到（0，0）的长方形中含有的1的总数
dp[i][j] = dp[i-1][j] + dp[i][j-1] - dp[i-1][j-1] + matrix[i][j] 

2. 求任一点(i,j) 左上角， 到任一点（a,b）右下角，围成的长方形中1的总数
size = dp[a][b] - dp[a-i][b] - dp[a, b-j] + dp[a-i][b-j] 

3. 判断这两点是否围成长方形： size == (a-i)*(b-j) 
*/
class Solution {
    public int maximalRectangle(char[][] matrix) {
        // 1 - O(n^2)
        int[][] dp = new int[matrix.length + 1][matrix[0].length + 1]; // 上，左分别加一行0
        for (int i = 1; i <= matrix.length; i++) {
            for (int j = 1; j <= matrix[0].length; j++) {
                dp[i][j] = dp[i-1][j] + dp[i][j-1] -dp[i-1][j-1] + (matrix[i-1][j-1] - '0');
                System.out.println("i " + i + " j " + j + " : " + dp[i][j]);
            }
        }
    
        // 2. 任意点(i,j) 左上角， 到任意点（a,b）右下角，围成的长方形中1的总数
        //2.1 边界
        int res = 0; 
        int size = 0; 
        // 确定右下角
        for (int a = 1; a <= matrix.length; a++) {
           for (int b = 1; b <= matrix[0].length; b++) {
               // 确定左上角
               for (int i = a - 1; i >=0; i--) {
                   for (int j = b - 1; j >= 0; j--) {
                       size = dp[a][b] - dp[i][b] - dp[a][j] + dp[i][j];
                       if (size == (a-i) * (b-j)) {
                           res = Math.max(res, (a-i)*(b-j));
                       }
                   }
               }
           }
        }
        return res; 
    }
}
