Given an array of non-negative integers nums, you are initially positioned at the first index of the array.

Each element in the array represents your maximum jump length at that position.

Determine if you are able to reach the last index.

 

Example 1:

Input: nums = [2,3,1,1,4]
Output: true
Explanation: Jump 1 step from index 0 to 1, then 3 steps to the last index.
Example 2:

Input: nums = [3,2,1,0,4]
Output: false
Explanation: You will always arrive at index 3 no matter what. Its maximum jump length is 0, which makes it impossible to reach the last index.
 

Constraints:

1 <= nums.length <= 3 * 104
0 <= nums[i] <= 105


----------------------------- 03.31.2021 DP --------------------------------------------------------------------------------------
/**
dp[i] index i 是否为true
dp[i] = dp[j] && j +  nums[j] >=i,  0 <= j < i

dp[0] = true 


*/

class Solution {
    public boolean canJump(int[] nums) {
        boolean[] dp = new boolean[nums.length];
        dp[0] = true;
        
        for (int i = 0; i < nums.length; i++) {
            for (int j = 0; j < i; j++) {
                if (dp[j] && (j + nums[j]) >= i) {
                    dp[i] = true;
                    break;
                }
            }
        }
        return dp[nums.length - 1];
    }
}


---------------------10. 2016 greedy ----------------------------------------------------------------------------------------- 
注意题目中A[i]表示的是在位置i，“最大”的跳跃距离，而并不是指在位置i只能跳A[i]的距离。所以当跳到位置i后，能达到的最大的距离至少是i+A[i]。用greedy来解，记录一个当前能达到的最远距离maxIndex：

1. 能跳到位置i的条件：i<=maxIndex。
2. 一旦跳到i，则maxIndex = max(maxIndex, i+A[i])。
3. 能跳到最后一个位置n-1的条件是：maxIndex >= n-1


public class Solution {
    //corner case: [0]
    public boolean canJump(int[] nums) {
        if(nums == null || nums.length == 0 ){
            return true; 
        }
        int max = 0;
        for (int i = 0; i <nums.length; i ++){ //循环走过每一格，找到最大的
            if( max < i || max >= nums.length -1){
                break; 
            }
            max = Math.max(max, i+nums[i]);
        }
        return (max >= nums.length -1)? true : false; 
    }
}
