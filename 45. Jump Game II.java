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

---------------------------------------------------greedy --------------------------------------------------------------------------
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

/////////// from 拉不拉东 

比如上图这种情况，我们站在索引 0 的位置，可以向前跳 1，2 或 3 步，你说应该选择跳多少呢？

显然应该跳 2 步调到索引 2，因为 nums[2] 的可跳跃区域涵盖了索引区间 [3..6]，比其他的都大。如果想求最少的跳跃次数，那么往索引 2 跳必然是最优的选择。

你看，这就是贪心选择性质，我们不需要「递归地」计算出所有选择的具体结果然后比较求最值，而只需要做出那个最有「潜力」，看起来最优的选择即可。

绕过这个弯儿来，就可以写代码了：
int jump(int[] nums) {
    int n = nums.length;
    int end = 0, farthest = 0;
    int jumps = 0;
    for (int i = 0; i < n - 1; i++) {
        farthest = Math.max(nums[i] + i, farthest);
        if (end == i) {
            jumps++;
            end = farthest;
        }
    }
    return jumps;
}
i 和 end 标记了可以选择的跳跃步数，farthest 标记了所有选择 [i..end] 中能够跳到的最远距离，jumps 记录了跳跃次数。
本算法的时间复杂度 O(N)，空间复杂度 O(1)，可以说是非常高效，动态规划都被吊起来打了。 

------------ DP ---------------
 /*
dp[] min steps here 
dp[i] = dp[j] + 1, if j + nums[j] >= i && dp[j] is reachable, j <[0, i-1]
dp[0] = 0

*/
 /*
dp[] min steps here 
dp[i] = dp[j] + 1, if j + nums[j] >= i && dp[j] is reachable, j <[0, i-1]
dp[0] = 0
*/
 
class Solution {
    public int jump(int[] nums) {
        int[] dp = new int[nums.length]; // at index i, the min steps needed 
        dp[0] = 0;
        for (int i = 1; i < nums.length; i++) {
            dp[i] = Integer.MAX_VALUE;
            for (int j = i-1; j >= 0; j--) {
                if (nums[j] >= i-j && dp[j] != Integer.MAX_VALUE) { //如果前某位能跳到当前位置
                    dp[i] = Math.min(dp[i], dp[j] + 1);
                }
            }
            // System.out.println(dp[i]);
        }
        return dp[nums.length-1];
    }
}
