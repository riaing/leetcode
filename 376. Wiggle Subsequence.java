Given a number sequence, find the length of its Longest Alternating Subsequence (LAS). A subsequence is considered alternating if its elements are in alternating order.

A three element sequence (a1, a2, a3) will be an alternating sequence if its elements hold one of the following conditions:

{a1 > a2 < a3 } or { a1 < a2 > a3}. 
Example 1:

Input: {1,2,3,4}
Output: 2
Explanation: There are many LAS: {1,2}, {3,4}, {1,3}, {1,4}
Example 2:

Input: {3,2,1,4}
Output: 3
Explanation: The LAS are {3,2,4} and {2,1,4}.
Example 3:

Input: {1,3,2,4}
Output: 4
Explanation: The LAS is {1,3,2,4}.

----------------DP ------------------------------------------------
similar to https://leetcode.com/problems/longest-increasing-subsequence/ 

/*
dp[i][0] 以 i 结尾，到 i 是上升的 longest wiggle subsequence
dp[i][1] 以 i 结尾，到 i 是下降的 longest wiggle subsequence

初始： dp[i][0] = 1; 不管前面怎么来的，包含它自己
dp[i][0/2] =  if nums[j] < nums[i], 0 <= j <= j-1, => dp[i][0] = Max {1 + dp[j][1]} . 如果 j 到 i 这段是上升, 那么 j 之前的那段必须是下降
            if nums[j] > nums[i], 0 <= j <= j-1, => dp[i][1] = Max {1 + dp[j][0]}
            
result: 循环dp 找个最大值            
*/
class Solution {
    public int wiggleMaxLength(int[] nums) {
        int[][] dp = new int[nums.length][2];
        int maxRes = 0;
        for (int i = 0; i < nums.length; i++) {
            dp[i][0] = 1; 
            dp[i][1] = 1;
            for (int j = i-1; j >= 0; j--) {
                if (nums[j] < nums[i]) {
                    dp[i][0] = Math.max(dp[i][0], 1 + dp[j][1]);
                }
                else if (nums[j] > nums[i]) { // 为防止相同的数； 【0， 0】 
                     dp[i][1] = Math.max(dp[i][1], 1 + dp[j][0]);
                }
            }
            maxRes = Math.max(maxRes, Math.max(dp[i][1], dp[i][0]));
        }
        return maxRes; 
    }
}

---------------- DP + 求 PATH --------------------------------------------------------
/*
dp[i][0] 以 i 结尾，到 i 是上升的 longest wiggle subsequence
dp[i][1] 以 i 结尾，到 i 是下降的 longest wiggle subsequence

初始： dp[i][0] = 1; 不管前面怎么来的，包含它自己
dp[i][0/2] =  if nums[j] < nums[i], 0 <= j <= j-1, => dp[i][0] = Max {1 + dp[j][1]} . 如果 j 到 i 这段是上升, 那么 j 之前的那段必须是下降
            if nums[j] > nums[i], 0 <= j <= j-1, => dp[i][1] = Max {1 + dp[j][0]}
            
result: 循环dp 找个最大值            
*/
class Solution {
    public int wiggleMaxLength(int[] nums) {
        int[][] dp = new int[nums.length][2];
        int[][] from = new int[nums.length][2]; //记录上一个从哪儿来
        int maxRes = 0;
        for (int i = 0; i < nums.length; i++) {
            dp[i][0] = 1; 
            dp[i][1] = 1;
            from[i][0] = -1;  from[i][1] = -1; 
            for (int j = i-1; j >= 0; j--) {
                if (nums[j] < nums[i]) {
                    if ( 1 + dp[j][1] > dp[i][0]) { // 求 path
                        from[i][0] = j;
                    }
                    
                    dp[i][0] = Math.max(dp[i][0], 1 + dp[j][1]);
                    
                }
                else if (nums[j] > nums[i]) { // 为防止相同的数； 【0， 0】 
                    if ( 1 + dp[j][0] > dp[i][1]) { // 求 path
                        from[i][1] = j;
                    }
                    
                    dp[i][1] = Math.max(dp[i][1], 1 + dp[j][0]);
                }
            }
            maxRes = Math.max(maxRes, Math.max(dp[i][1], dp[i][0]));
        }
        
        // 求 path，先找到那个 dp[i][1 or 0] has max value.再从那 track back 
        int maxResDup = 0; //和maxRes一样的，这里用来求 path 而已。
        int[] maxPlace = new int[2] ; // 0 index 代表 i，1 index 代表上升0或者下降1 
        for (int i = 0; i < nums.length; i++) {
            for (int j = 0; j < 2; j++) {
                if (dp[i][j] > maxResDup) {
                    maxResDup = dp[i][j];
                    maxPlace[0] = i; 
                    maxPlace[1] = j;
                }
            }
        }
        System.out.println("maxVal index: " + maxPlace[0] + " 降或者升 " + maxPlace[1]);
        
        String path = "";
        int trend = maxPlace[1]; //当前是升还是降
        int index = maxPlace[0];
        while (index >= 0) {
            path = nums[index] + " " + path; 
            index = from[index][trend];
            trend = 1 - trend; 
        }
        System.out.println("path: " + path);
        
        return maxRes; 
    }
}
