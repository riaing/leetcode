Given an unsorted array of integers, find the length of longest increasing subsequence.

Example:

Input: [10,9,2,5,3,7,101,18]
Output: 4 
Explanation: The longest increasing subsequence is [2,3,7,101], therefore the length is 4. 
Note:

There may be more than one LIS combination, it is only necessary for you to return the length.
Your algorithm should run in O(n2) complexity.
Follow up: Could you improve it to O(n log n) time complexity?

--------------------------------------------DP-----------------------------------------------------------------------
/**
m[i]: 以i为结尾的最长sub
m[i] = max{m[j] + 1 && a[j] < a[i]}, 0<= j < i
m[0...n-1] = 1: 这里必须是所以值都initialize成1，
return max{m[0...n-1]}

time: O(n^2), space o(n)
*/
class Solution {
    public int lengthOfLIS(int[] nums) {
        if (nums == null || nums.length == 0) {
            return 0;
        }
        
        
        int[] res = new int[nums.length];
        for (int i = 0; i < res.length; i++) {
            res[i] = 1;
        }

        for (int i = 0; i < nums.length; i++) {
            for (int j = 0; j < i; j++) {
                if (nums[j] < nums[i]) {
                    res[i] = Math.max(res[i], res[j] + 1);
                }
            }
        }
        
        int max = res[0];
        for (int n = 1; n < res.length; n++) {
            max = Math.max(max, res[n]);
        }
        return max; 
    }
}

--------------- 2022.3.9 ---------------------------
    /*
dp[i] 以i结尾的 i个元素的 LIS. 
dp[i] = Max{dp[j] +1 }, 0 <= j <= i-1, if nums[i] > nums[j]

Time: loop is n, 第一次 1， 第二次2， 第 n 次 n-1 -> 1+2+、、、n-1 = n^2
*/
class Solution {
    public int lengthOfLIS(int[] nums) {
        int[] dp = new int[nums.length];
        dp[0] = 1;
        int max = 1;
        for (int i = 1; i < nums.length; i++) {
            dp[i] = 1; // if no prev element smaller than i, the LIS just include itself 
            for (int j = i-1; j>=0; j--) {
                if (nums[j] < nums[i]) {
                    dp[i] = Math.max(dp[i], 1 + dp[j]);
                    max = Math.max(max, dp[i]);
                }
            }
        }
        return max;
    }
}
-------------- 2022.3.9 求 path ----------------------------------
    /*
dp[i] 前 i 个元素的 LIS. 
dp[i] = Max{dp[j] +1 }, 0 <= j <= i-1, if nums[i] > nums[j]

Time: loop is n, 第一次 1， 第二次2， 第 n 次 n-1 -> 1+2+、、、n-1 = n^2
*/
class Solution {
    public int lengthOfLIS(int[] nums) {
        int[] dp = new int[nums.length];
        dp[0] = 1;
        int max = 1;
        int maxIndex = 0; 
        // 求 PATH
        int[] from = new int[nums.length]; //记录到达 i 点的之前的那个位置
        from[0] = -1; // 初始化
        
        for (int i = 1; i < nums.length; i++) {
            dp[i] = 1; // if no prev element smaller than i, the LIS just include itself 
            from[i] = -1; // 为了之后找 path 方便
            for (int j = i-1; j>=0; j--) {
                if (nums[j] < nums[i]) {
                    // if 求 path
                    if (dp[i] < 1 + dp[j]) {
                        dp[i] = 1 + dp[j];
                        from[i] = j; 
                    }
                    if (max < dp[i]) {
                        max = dp[i];
                        maxIndex = i; 
                    }
                    
                    /* if not to get path, simple code 
                    dp[i] = Math.max(dp[i], 1 + dp[j]);
                    max = Math.max(max, dp[i]); 
                    */
                }
            }
        }

        // 求 path
        int index = maxIndex;
        while (index >=0) {
            System.out.println(nums[index]);
            index = from[index];
        }
        
        return max;
    }
}
