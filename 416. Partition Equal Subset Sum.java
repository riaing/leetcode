Given a non-empty array containing only positive integers, find if the array can be partitioned into two subsets such that the sum of elements in both subsets is equal.

Note:

Each of the array element will not exceed 100.
The array size will not exceed 200.
 

Example 1:

Input: [1, 5, 11, 5]

Output: true

Explanation: The array can be partitioned as [1, 5, 5] and [11].
 

Example 2:

Input: [1, 2, 3, 5]

Output: false

Explanation: The array cannot be partitioned into equal sum subsets.
 -------------- 2.15.2022 1D array -------------------------------------
 /*
dp[i][j] : 前 i 个，能否组成 sum为 j
dp[i][j] = dp[i-1][j] || dp[i-1][j-nums[i]] 取当前元素，或者不取
dp[0...i][0] = true; 不取任何元素
dp[0][0...j] = true if nums[0] == j 

*/
class Solution {
    public boolean canPartition(int[] nums) {
        int sum = 0; 
        for (int n : nums) {
            sum += n;
        }
        int mid = sum / 2; 
        if ( sum % 2 != 0) { // corner case,
            return false;
        }
        boolean[][] dp = new boolean[2][mid+1]; // 前 i 个元素中，能否组成 sum 为 j
        
        for (int i = 0; i < nums.length; i++) {
            for (int j = 0; j <= mid; j++) {
                if (j == 0) {
                    dp[i%2][j] = true; // sum 为0时不取    
                }
                
                else if (i == 0) {
                    if (nums[i] == j) {
                        dp[i][j] = true;
                    } 
                }
                else {
                    dp[i%2][j] = dp[(i-1)%2][j]; //不取，则看前 i-1能否组成 sum；
                    if (j - nums[i] >= 0) {
                        dp[i%2][j] = dp[i%2][j] || dp[(i-1)%2][j - nums[i]]; // 取，着看前 i-1能否组成 j-当前 i 的值
                    }
                }
            }
        }
        return dp[(nums.length-1)%2][mid];
        
    }
}
 
 -------- 4.5.2021 DP 优化的代码 -----------------------------------------------------------------------------
 /* dp[i][j] 前i个元素能否组成和为j
dp[0][0] = true; 不放
dp[i][0] = true; 不放

dp[i][j] = true if dp[i-1][j] = true (不放) || dp[i-1][j - curValue] = true (放)
*/
class Solution {
    public boolean canPartition(int[] nums) {
        int sum = 0;
        for (int val : nums) {
            sum += val;
        }
        if (sum % 2 == 1) {
            return false; 
        }
        // 背包问题，能否取出元素组成和为sum的包
        sum = sum / 2; 
        
        boolean[][] dp = new boolean[nums.length + 1][sum + 1];
        dp[0][0] = true; //一个元素不放，就是true
        for (int i = 1; i <= nums.length; i++) {
            for (int j = 0; j <= sum; j++) { //j从0开始，就能给接下来所有dp[i][0]赋值为true
                // if 不放 || 放当前元素
                if (dp[i-1][j] || ((j - nums[i-1] >=0) && dp[i-1][j-nums[i-1]])) {
                    dp[i][j] = true;
                    if (j == sum) {
                        return true;
                    }
                }
            }
        }
        return false;
    }
}
 
----------------- 4.5.2021 DP initial thoughts ------------------------------------------------
 class Solution {
    public boolean canPartition(int[] nums) {
        int sum = 0;
        for (int val : nums) {
            sum += val;
        }
        if (sum % 2 == 1) {
            return false; 
        }
        // 背包问题，能否取出元素组成和为sum的包
        sum = sum / 2; 
        
        boolean[][] dp = new boolean[nums.length + 1][sum + 1];
        
        for (int i = 1; i <= nums.length; i++) {
            for (int j = 1; j <= sum; j++) {
                if (dp[i-1][j] || ((j - nums[i-1] >=0) && dp[i-1][j-nums[i-1]]) || j == nums[i-1]) {
                    dp[i][j] = true;
                    if (j == sum) {
                        return true;
                    }
                }
            }
        }
        return false;
    }
}
-------------------------------------------------------------------------------------
/**
转换成背包问题1, 从list中选元素，组成halfSum
*/
class Solution {
    public boolean canPartition(int[] nums) {
        int sum = 0; 
        for (Integer i : nums) {
            sum += i;
        }
        int halfSum = sum / 2;
        if (sum % 2 != 0) {
            return false;
        }
        
        //背包问题： m[i][j]: 前i个数中，能否组成和为j
        //m[i][j] = m[i-1][j] || if j >= nums[i-1]第i个数能放进去，m[i-1][j-nums[i-1]];
        // start: m[0][0] = true, m[0..length][0] = true, m[0][0..sum] = false;
        // return m[length][sum] 
        boolean[][] m = new boolean[nums.length+1][halfSum+1];
        m[0][0] = true;
        for (int i = 1; i <= nums.length; i++) {
         
            for (int j = 0; j <= halfSum; j++) {
                if (j == 0) {
                    m[i][j] = true;
                    
                }
                else {
                     m[i][j] = m[i-1][j];
                    if (j >= nums[i-1]) {
                         m[i][j] |= m[i-1][j-nums[i-1]];
                    }
                   
                }
                
            }
        }
        return m[nums.length][halfSum];
    }
}
