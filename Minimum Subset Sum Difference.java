Given a set of positive numbers, partition the set into two subsets with minimum difference between their subset sums.

Example 1:#
Input: {1, 2, 3, 9}
Output: 3
Explanation: We can partition the given set into two subsets where minimum absolute difference 
between the sum of numbers is '3'. Following are the two subsets: {1, 2, 3} & {9}.
Example 2:#
Input: {1, 2, 7, 1, 5}
Output: 0
Explanation: We can partition the given set into two subsets where minimum absolute difference 
between the sum of number is '0'. Following are the two subsets: {1, 2, 5} & {7, 1}.
Example 3:#
Input: {1, 3, 100, 4}
Output: 92
Explanation: We can partition the given set into two subsets where minimum absolute difference 
between the sum of numbers is '92'. Here are the two subsets: {1, 3, 4} & {100}.
------------------------DP 背包 ------------------------------------------------------------------
/*
 we are trying to find a subset whose sum is as close to ‘S/2’ as possible, because if we can 
 partition the given set into two subsets of an equal sum, we get the minimum difference, i.e. zero.
Essentially, we need to calculate all the possible sums up to ‘S/2’ for all numbers.

Steps:
For every possible sum ‘s’ (where 0 <= s <= S/2), we have two options:

Exclude the number. In this case, we will see if we can get the sum ‘s’ from the subset excluding this number => dp[index-1][s]
Include the number if its value is not more than ‘s’. In this case, we will see if we can find a subset to get the remaining sum => dp[index-1][s-num[index]]
If either of the two above scenarios is true, we can find a subset with a sum equal to ‘s’. We should dig into this before we can learn how to find the closest subset.

Time and Space complexity#
The above solution has the time and space complexity of O(N*S)O(N∗S), where ‘N’ represents total numbers and ‘S’ is the total sum of all the numbers.


*/
class PartitionSet {

  public int canPartition(int[] nums) {
        int sum = 0; 
        for (int n : nums) {
            sum += n;
        }
        int mid = sum / 2; 

        boolean[][] dp = new boolean[2][mid+1]; // 前 i 个元素中，能否组成 sum 为 j
        
        for (int i = 0; i < nums.length; i++) {
            for (int j = 0; j <= mid; j++) {
                if (j == 0) {
                    dp[i%2][j] = true; // sum 为0时不取    
                }
                
                else if (i == 0) { // 所以 dp[0][nums[0] = true;
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
        // 最后再找 difference 
        int diff = Integer.MAX_VALUE;
        for (int i = 0; i <= mid; i++) {
          if (dp[(nums.length-1)%2][i]) { // 所有最后能组成的 sum中。找绝对值差最小
            if (Math.abs(i - (sum - i)) < diff) {
              diff = Math.abs(i - (sum - i));
            }
          }
        }
        return diff; 
  }

  public static void main(String[] args) {
    PartitionSet ps = new PartitionSet();
    int[] num = {1, 2, 3, 9};
    System.out.println(ps.canPartition(num));
    num = new int[]{1, 2, 7, 1, 5};
    System.out.println(ps.canPartition(num));
    num = new int[]{1, 3, 100, 4};
    System.out.println(ps.canPartition(num));
  }
}
