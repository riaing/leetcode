给一个矩阵, 找出所有从左上到右下path最小值中的最大值.
eg: 
{8,4,3,5}
{6,5,9,8}

所有的path:

8->4->3->5->8 min:3

8->4->3->9->8 min:3

8->4->5->9->8 min:5

8->6->5->9->8 min:5

Result = Math.max(3,3,5,5,) = 5
  
------------- 4.4.2020 没做出来. Learning：正面想想不通时就倒着想  -----------------------------------------------------
/**
反过来构建的DP
DP[i][j]： 从i,j走到右下角需要多少血量。https://www.youtube.com/watch?v=pt-xIS6huIg&ab_channel=HuaHua 

错误的想法：运用min max 
DP1： 
1. 先求个每个店的maxSum, 得到一个DP matrix => 到达每个点最多能有多少滴血（求每个点最多的血，才能保证最后一个点求出最少需要多少血）。
maxSum[i][j] = max{maxSum[i][j-1], maxSum[i-1][j]} + m[i][j] 
初始
maxSum[0][0] = m[0][j]
maxSum[0][j] = maxSum[0][j-1] + m[0][j]
maxSum[i][0] = maxSum[i-1][0] + m[i][0]

DP2： 
2. 在上面的dp matrix中找到每条path中的最小值 => 走这条path的话最少需要多少血（A路需要-1， B路需要-10）
3. 在所有path最小值中取个最大的（因为A路最少需要2滴血就能活，所以走A路）
转成 max minimum path的题

公式解析见：
https://github.com/riaing/leetcode/blob/master/Maximum%20Minimum%20Path%20in%20Matrix.java
*/
class Solution {
    public int calculateMinimumHP(int[][] dungeon) {
        int row = dungeon.length;
        int col = dungeon[0].length;
        int[][] maxSum = new int[row][col];
        int[][] minMaxDP = new int[row][col]; 
        
        // initialization 
        maxSum[0][0] = dungeon[0][0];
        minMaxDP[0][0] = dungeon[0][0];
        for (int i = 1; i < row; i++) {
            maxSum[i][0] = maxSum[i-1][0] + dungeon[i][0];
            minMaxDP[i][0] = Math.min(maxSum[i][0], minMaxDP[i-1][0]); //第二个DP用的是第一个DP当matrix
        }
        for (int j = 1; j < col; j++) {
            maxSum[0][j] = maxSum[0][j-1] + dungeon[0][j];
            minMaxDP[0][j] = Math.min(maxSum[0][j], minMaxDP[0][j-1]);
        }
        
         for (int i = 1; i < row; i++) {
            for (int j = 1; j < col; j++) {
                maxSum[i][j] = Math.max(maxSum[i][j-1], maxSum[i-1][j]) + dungeon[i][j];
                minMaxDP[i][j] = Math.min(minMaxDP[i][j-1], maxSum[i][j-1] + dungeon[i][j]);
                minMaxDP[i][j] = Math.max(minMaxDP[i][j], Math.min(minMaxDP[i-1][j], maxSum[i-1][j] + dungeon[i][j]));             
            }
         }
        
        //return处理下，如果最后值为正，return 0；为负，绝对值加1
        // System.out.println("return " + Math.abs(minMaxDP[row-1][col-1]));
        return minMaxDP[row-1][col-1] < 0 ? Math.abs(minMaxDP[row-1][col-1]) + 1 : 1;
        
    }
}

---------------- DP ------------------------------------------------------
DP[i][j]:到达i,j这个点时所有path最小值中的最大值

公式：到达某点时，其实只有两条path：从左后者上来。
if m[i][j] < dp[i-1][j] && dp[i][j-1], dp[i][j] = m[i][j] //到达m[i][j]时可以从左或者右来，
                                                                                //这时m[i][j]肯定分别是两条路径的最小值了，所以所有路径中的最小值（就是m[i][j])的最大值就是它本身
if m[i][j] > dp[i-1][j] && dp[i][j-1], dp[i][j] = max(dp[i-1][j], dp[i][j-1])  //到达m[i][j]时可以从左或者右来，因为m[i][j]已经比路径中的最小值都大了，它就失去了路径最小值的资格，所以两条包含m[i][j]
                                                           //的d的路径的最小值还是dp[i-1][j], dp[i][j-1], 两者取最大即是包含m[i][j]的所有路径最小值中最大的那一个
if dp[i][j-1] < m[i][j] < dp[i-1][j]或相反，dp[i][j] = m[i][j] // 如果m[i][j] 小于其中一个大于另一个，那它肯定还是两路径中的最小值，返回它本身
                                                             //eg: m[i][j] = 4; A路 5 - 6. B路 5 - 2. 到达m[i][j]后，A路变成 5-6-4，最小值更新为4. B路变成 5-2-4，最小值还是2
                                                             // 现在要在路径最小值中取大的那个，就是在A的4和B的2中中取max（4，2） = 4. 所以返回m[i][j]
初始化：
dp[0][0] = m[i][j] 
dp[0][j]= min{m[0][j], dp[0][j-1]} // 只有一条path，取path中的最小值即可
dp[i][0] = min{m[i][0], dp[i-1][0]} 

return dp[m][n]   
  
  


