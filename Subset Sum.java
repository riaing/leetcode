这题的简化版： https://github.com/riaing/leetcode/blob/master/416.%20Partition%20Equal%20Subset%20Sum.java 

Given a set of positive numbers, determine if a subset exists whose sum is equal to a given number ‘S’.

Example 1:#
Input: {1, 2, 3, 7}, S=6
Output: True
The given set has a subset whose sum is '6': {1, 2, 3}
Example 2:#
Input: {1, 2, 7, 1, 5}, S=10
Output: True
The given set has a subset whose sum is '10': {1, 2, 7}
Example 3:#
Input: {1, 3, 4, 8}, S=6
Output: False
The given set does not have any subset whose sum is equal to '6'.


----------- DP backpack -------------------------
Time and Space complexity#
The above solution has the time and space complexity of O(N*S)O(N∗S), where ‘N’ represents total numbers and ‘S’ is the required sum. 

class SubsetSum {

  public boolean canPartition(int[] nums, int sum) {
        boolean[][] dp = new boolean[2][sum+1]; // 前 i 个元素中，能否组成 sum 为 j
        
        for (int i = 0; i < nums.length; i++) {
            for (int j = 0; j <= sum; j++) {
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
        return dp[(nums.length-1)%2][sum];
  }

  public static void main(String[] args) {
    SubsetSum ss = new SubsetSum();
    int[] num = { 1, 2, 3, 7 };
    System.out.println(ss.canPartition(num, 6));
    num = new int[] { 1, 2, 7, 1, 5 };
    System.out.println(ss.canPartition(num, 10));
    num = new int[] { 1, 3, 4, 8 };
    System.out.println(ss.canPartition(num, 6));
  }
}

-------------------- 一维数组， O（s）space complexity ---------------------
class SubsetSum {

  static boolean canPartition(int[] num, int sum) {
    int n = num.length;
    boolean[] dp = new boolean[sum + 1];

    // handle sum=0, as we can always have '0' sum with an empty set
    dp[0] = true;

    // with only one number, we can have a subset only when the required sum is equal to its value
    for (int s = 1; s <= sum; s++) {
      dp[s] = (num[0] == s ? true : false);
    }

    // process all subsets for all sums
    for (int i = 1; i < n; i++) {
      for (int s = sum; s >= 0; s--) {
        // if dp[s]==true, this means we can get the sum 's' without num[i], hence we can move on to
        // the next number else we can include num[i] and see if we can find a subset to get the
        // remaining sum
        if (!dp[s] && s >= num[i]) {
          dp[s] = dp[s - num[i]];
        }
      }
    }

    return dp[sum];
  }
}
