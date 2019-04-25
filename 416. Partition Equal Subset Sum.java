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
