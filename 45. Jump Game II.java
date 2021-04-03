Given an array of non-negative integers nums, you are initially positioned at the first index of the array.

Each element in the array represents your maximum jump length at that position.

Your goal is to reach the last index in the minimum number of jumps.

You can assume that you can always reach the last index.

 

Example 1:

Input: nums = [2,3,1,1,4]
Output: 2
Explanation: The minimum number of jumps to reach the last index is 2. Jump 1 step from index 0 to 1, then 3 steps to the last index.
Example 2:

Input: nums = [2,3,0,1,4]
Output: 2
 

Constraints:

1 <= nums.length <= 1000
0 <= nums[i] <= 105

---------------------------- 4.3.2021 DP --------------------------------------------------------------------------------------------

/*
dp[i] = min{dp[j] + 1} if j + nums[j] >= i, 0<= j < i, 
dp[0] = 0 

*/
class Solution {
    public int jump(int[] nums) {
        int[] dp = new int[nums.length]; 
        dp[0] = 0; 
        for (int i = 1; i < nums.length; i++) {
            dp[i] = nums.length+1; //优化：这里不用设置为integer.max_value; 最多走index的max步到达最后的index
        }
        
        for (int i = 1; i < nums.length; i++) {
            for (int j = 0; j < i; j++) {
                if (j + nums[j] >= i) {
                    dp[i] = Math.min(dp[i], dp[j] + 1);
                }
            }
        }
        return dp[nums.length-1];
     }
}

------------------------------------------------------------------------------------------------------------------------------
找到当前能覆盖的最大值（coverDistance），当i 超过这个值时，说明step ++， 在i没超过 coverDistance的循环中，每次update能到的最远距离。
当i超过 coverDistance时，再把coverDistance update到之前步骤中找到的能到的最大距离。 
http://www.cnblogs.com/lichen782/p/leetcode_Jump_Game_II.html


public class Solution {
    public int jump(int[] nums) {
        if(nums == null || nums.length ==0 ){
            return -1 ; 
        }
        int step = 0; 
        int max = 0 ;
        int coverDistance = 0; 
        for( int i = 0; i <nums.length; i ++){
     
            if(i  > coverDistance ){
                step ++; 
                coverDistance = max; 
            }
            max = Math.max(max, i + nums[i]);
        }
   

            return step;    
        
        
    }
}
