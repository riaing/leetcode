Given a set of positive numbers, find the total number of subsets whose sum is equal to a given number ‘S’.

Example 1:#
Input: {1, 1, 2, 3}, S=4
Output: 3
The given set has '3' subsets whose sum is '4': {1, 1, 2}, {1, 3}, {1, 3}
Note that we have two similar sets {1, 3}, because we have two '1' in our input.
Example 2:#
Input: {1, 2, 7, 1, 5}, S=9
Output: 3
The given set has '3' subsets whose sum is '9': {2, 7}, {1, 7, 1}, {1, 2, 1, 5}

---------------------------------
class SubsetSum {
  static int countSubsets(int[] nums, int sum) {
    int[][] dp = new int[nums.length][sum+1];
    for (int i = 0; i < nums.length; i++) {
      dp[i][0] = 1; //empty set is also a solution 
    }
    
    dp[0][nums[0] = 1; //下面一段就是这个意思
//     for (int j = 0; j <= sum; j++) {
//       if (nums[0] == j) {
//         dp[0][j] = 1; 
//       }
//     }

    for (int i = 1; i < nums.length; i++) {
       for (int j = 1; j <= sum; j++) {
          dp[i][j] = dp[i-1][j]; // skip cur num 
          if (j - nums[i] >= 0) {
            dp[i][j] += dp[i-1][j - nums[i]];  // add cur num
          } 
       }
    }
    return dp[nums.length-1][sum];
  }
} 
