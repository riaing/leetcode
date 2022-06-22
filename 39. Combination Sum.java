Given an array of distinct integers candidates and a target integer target, return a list of all unique combinations of candidates where the chosen numbers sum to target. You may return the combinations in any order.

The same number may be chosen from candidates an unlimited number of times. Two combinations are unique if the frequency of at least one of the chosen numbers is different.

It is guaranteed that the number of unique combinations that sum up to target is less than 150 combinations for the given input.

 

Example 1:

Input: candidates = [2,3,6,7], target = 7
Output: [[2,2,3],[7]]
Explanation:
2 and 3 are candidates, and 2 + 2 + 3 = 7. Note that 2 can be used multiple times.
7 is a candidate, and 7 = 7.
These are the only two combinations.
Example 2:

Input: candidates = [2,3,5], target = 8
Output: [[2,2,2,2],[2,3,3],[3,5]]
Example 3:

Input: candidates = [2], target = 1
Output: []
 

Constraints:

1 <= candidates.length <= 30
1 <= candidates[i] <= 200
All elements of candidates are distinct.
1 <= target <= 500

-------------------------------------------------------------
/*
如果求总共多少个解，就是coin change - dp
dp[i][j] 前i个数字中组成sum为j的解法
dp[i][j] = dp[i-1][j] + dp[i][j-num[i]] :当前数字取或者不取
如果数字只能取一次： 
dp[i][j] = dp[i-1][j] + dp[i-1][j-num[i]] :当前数字取或者不取： https://github.com/riaing/leetcode/blob/master/Combination%20Sum%E7%9A%84DP%E7%89%88.java 
*/
class Solution {
    public List<List<Integer>> combinationSum(int[] candidates, int target) {
        List<List<Integer>> res = new ArrayList<>();
        helper(candidates, target, 0, 0, new ArrayList<>(), res);
        return res; 
    }
    
    private void helper(int[] arr, int target, int curSum, int index, List<Integer> cur, List<List<Integer>> res) {
        if (curSum == target) {
            res.add(new ArrayList<>(cur));
            return;
        }
        if (index >= arr.length || curSum > target) {
            return; 
        }
        // 取或者不取
        if (curSum + arr[index] <= target) {
            cur.add(arr[index]); 
            helper(arr, target, curSum + arr[index], index, cur, res); 
            cur.remove(cur.size()-1);
        }
        helper(arr, target, curSum, index+1, cur, res);
    }
}

----------- 易理解写法 --------------------------
 /*
如果求总共多少个解，就是coin change - dp
dp[i][j] 前i个数字中组成sum为j的解法
dp[i][j] = dp[i-1][j] + dp[i][j-num[i]] :当前数字取或者不取
如果数字只能取一次： 
dp[i][j] = dp[i-1][j] + dp[i-1][j-num[i]] :当前数字取或者不取： https://github.com/riaing/leetcode/blob/master/Combination%20Sum%E7%9A%84DP%E7%89%88.java 
*/
class Solution {
    public List<List<Integer>> combinationSum(int[] candidates, int target) {
        Arrays.sort(candidates); 
        List<Integer> curRes = new ArrayList<>();
        List<List<Integer>> res = new ArrayList<>();
        helper(candidates, target, 0, 0, curRes, res);
        return res; 
        
    }
    private void helper(int[] cand, int target, int index, int curSum, List<Integer> curRes, List<List<Integer>> res) {
        if (curSum == target) {
            res.add(new ArrayList<>(curRes));
            return; 
        }
        for (int i = index; i < cand.length; i++) {
            if (curSum + cand[i] > target) {
                return;
            }
            curRes.add(cand[i]);
            helper(cand, target, i, curSum + cand[i], curRes, res);
            curRes.remove(curRes.size() - 1);
        }
    }
}
