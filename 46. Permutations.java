https://labuladong.github.io/algo/4/29/105/ 

Given an array nums of distinct integers, return all the possible permutations. You can return the answer in any order.

 

Example 1:

Input: nums = [1,2,3]
Output: [[1,2,3],[1,3,2],[2,1,3],[2,3,1],[3,1,2],[3,2,1]]
Example 2:

Input: nums = [0,1]
Output: [[0,1],[1,0]]
Example 3:

Input: nums = [1]
Output: [[1]]
 

Constraints:

1 <= nums.length <= 6
-10 <= nums[i] <= 10
All the integers of nums are unique.

-------------------------------------------------------------
class Solution {
    public List<List<Integer>> permute(int[] nums) {
        List<List<Integer>> res = new ArrayList<List<Integer>>();
        boolean[] visited = new boolean[nums.length];
        helper(nums, visited, new ArrayList<Integer>(), res);
        return res;
    }
    
    private void helper(int[] nums, boolean[] visited, List<Integer> cur, List<List<Integer>> res) {
        if (cur.size() == nums.length) {
            res.add(new ArrayList<Integer>(cur));
            return;
        }
        for (int i = 0; i < nums.length; i++) {
            if (!visited[i]) {
                cur.add(nums[i]);
                visited[i] = true; 
                helper(nums, visited, cur, res);
                visited[i] = false;
                cur.remove(cur.size() - 1);
            }
        }
    }
} 
