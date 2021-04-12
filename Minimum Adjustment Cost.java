Given an integer array, adjust each integers so that the difference of every adjacent integers are not greater than a given number target.

If the array before adjustment is A, the array after adjustment is B, you should minimize the sum of |A[i]-B[i]|

Example
Example 1:
	Input:  [1,4,2,3], target=1
	Output:  2

Example 2:
	Input:  [3,5,4,7], target=2
	Output:  1
	
Notice
You can assume each number in the array is a positive integer and not greater than 100.
		
-------------4.11.2021 DP 思路和下差不多，局部有小优化 ---------------------------------------------------------
public class Solution {
    /*
    每个index上的值都可以为所有数，包括第一个。只是从第二个开始值会受第一个的影响。只能取[第一个值-target， 第一个值+target】范围内的
    DP[I][J]: 第i个数值为j时的最小sum。
    dp[i][j] = min{dp[i-1][k] + abs|v[i| - j|}, 0<j<=100, j-target <= k <= j+ target
     */
    public int MinAdjustmentCost(List<Integer> A, int target) {
        int[][] dp = new int[A.size()][100+1];

        // 初始化值可以为-1或者integer.MAX  
        for (int i = 1; i < A.size(); i++) {
            for (int j = 0; j <= 100; j++) {
                dp[i][j] = -1;
            }
        }
        dp[0][0] = -1; 
        for (int i = 1; i <= 100; i++) {
            dp[0][i] = Math.abs(A.get(0) - i);
        }

        for (int i = 1; i < A.size(); i++) {
            for (int j = 1; j <=100; j++) {
                // get k's range，k值的是当前index取j的话，为了满足差值条件，前一个数能取多少 
                int min = Math.max(j-target, 0);
                int max = Math.min(j+target, 100);
                for (int k = min; k <= max; k++) {
                    if (dp[i-1][k] != -1) {
                        int cur = dp[i-1][k] + Math.abs(A.get(i) - j);
                        dp[i][j] = dp[i][j] == -1 ? cur : Math.min(dp[i][j], cur); 
                    }
                }
                
            }
        }
        int result = Integer.MAX_VALUE; 
        for (int i = 1; i <= 100; i++) {
            if (dp[A.size()-1][i] != -1) {
                 result = Math.min(result, dp[A.size()-1][i]);
            }
        }
        return result;
    }
}

----------------------------------------------------------backpack problem --------------------------------------------
public class Solution {
    /*
    m[i][j]: 对前i个进行调整，把第i个int调整为j的最小代价
    m[i][j] = min{f[i-1][x]} +|A[i] - j|, x = {j-target, j+ target}
    
    start: f[0][...j] = 0; f[...i][0] = max MAX_VALUE
    return: f[length][0...j]最小值
    
    
    
     * @param A: An integer array
     * @param target: An integer
     * @return: An integer
     */
    public int MinAdjustmentCost(List<Integer> A, int target) {
        int[][] m = new int[A.size()+1][100+1];
        // m[0][...] = 0; m[...][0] = max; 
        for (int i = 0; i <= A.size(); i++) {
            for (int j = 0; j <=100; j++) {
                if (i == 0) {
                     m[i][j] = 0;
                }
                else {
                    m[i][j] = Integer.MAX_VALUE;
                }
            }
        }
        
       for (int i = 1; i <= A.size(); i++) {
            for (int j = 0; j <=100; j++) {
                int rangeMin = j - target < 0 ? 0 : j - target;
                int rangeMax = j + target > 100 ? 100 : j + target;
                int minLastChange = Integer.MAX_VALUE;
                for (int k = rangeMin; k <= rangeMax; k++) {
                    minLastChange = Math.min(minLastChange, m[i-1][k]);
                }
                m[i][j] = minLastChange + Math.abs(A.get(i-1) - j); 
            }
       }
       
       // 找最后一行的最小值
       int res = Integer.MAX_VALUE;
        for (int j = 0; j <=100; j++) {
             res = Math.min(res, m[A.size()][j]);
        }
        
        return res;
    }
}
