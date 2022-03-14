Given a number sequence, find the increasing subsequence with the highest sum. Write a method that returns the highest sum.

Example 1:

Input: {4,1,2,6,10,1,12}
Output: 32
Explanation: The increaseing sequence is {4,6,10,12}. 
Please note the difference, as the LIS is {1,2,6,10,12} which has a sum of '31'.
Example 2:

Input: {-4,10,3,7,15}
Output: 25
Explanation: The increaseing sequences are {10, 15} and {3,7,15}.

------------- similar to https://leetcode.com/problems/longest-increasing-subsequence/ ------------
/*
dp[i] 以i 结尾的 max. 
dp[i] = Max{dp[j] + nums[i] }, 0 <= j <= i-1, if nums[i] > nums[j]

Time: loop is n, 第一次 1， 第二次2， 第 n 次 n-1 -> 1+2+、、、n-1 = n^2

1. If the number at the current index is bigger than the number at the previous index, we include that number in the sum for an increasing sequence up to the current index.
2. But if there is a maximum sum increasing subsequence (MSIS), without including the number at the current index, we take that.
So we need to find all the increasing subsequences for a number at index i, from all the previous numbers (i.e. numbers till index i-1), to find MSIS.

If i represents the currentIndex and ‘j’ represents the previousIndex, our recursive formula would look like:
    if num[i] > num[j] => dp[i] = dp[j] + num[i] if there is no bigger MSIS for 'i'
*/

class MSIS {

  public int findMSIS(int[] nums) {
        int[] dp = new int[nums.length];
        dp[0] = nums[0];
        int max = nums[0];
        int maxIndex = 0; 
        // 求 PATH
        int[] from = new int[nums.length]; //记录到达 i 点的之前的那个位置
        from[0] = -1; // 初始化
        
        for (int i = 1; i < nums.length; i++) {
            dp[i] = nums[i]; // if no prev element smaller than i, the LIS just include itself 
            from[i] = -1; // 为了之后找 path 方便
            for (int j = i-1; j>=0; j--) {
                if (nums[j] < nums[i]) {
                    // if 求 path
                    if (dp[i] < nums[i] + dp[j]) {
                        dp[i] = nums[i] + dp[j];
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

  public static void main(String[] args) {
    MSIS msis = new MSIS();
    // int[] nums = {4,1,2,6,10,1,12};
    // System.out.println(msis.findMSIS(nums));
    int[] nums = new int[]{-4,10,3,7,15};
    System.out.println(msis.findMSIS(nums));
  }
}
