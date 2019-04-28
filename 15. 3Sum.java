Given an array nums of n integers, are there elements a, b, c in nums such that a + b + c = 0? Find all unique triplets in the array which gives the sum of zero.

Note:

The solution set must not contain duplicate triplets.

Example:

Given array nums = [-1, 0, 1, 2, -1, -4],

A solution set is:
[
  [-1, 0, 1],
  [-1, -1, 2]
]
---------------------------------------------注意去重-----------------------------------------------------------------
/**
最外层遍历一遍，等于选出一个数，之后的数组中转化为找和为target-nums[i]的2SUM问题。 
解决duplicate问题，也可以通过挪动指针来解决判断，当找到一个合格结果时，将3个加数指针挪动到与当前值不同的地方，才再进行继续判断

time: o(nlgn + n^2)
*/
class Solution {
    public List<List<Integer>> threeSum(int[] nums) {
        List<List<Integer>> res = new ArrayList<List<Integer>>();
 
        Arrays.sort(nums);
        for (int i = 0; i < nums.length; i++) {
            if (i > 0 && nums[i] == nums[i-1]) { // remove dup
                continue;
            }
            List<List<Integer>> twoSumRes = twoSum(nums, i, 0 - nums[i]);
            if (!twoSumRes.isEmpty()) {
                for (List<Integer> cur : twoSumRes) {
                    cur.add(nums[i]);
                    res.add(cur);
                }
            }
        }
        return res; 
    }
    
    private List<List<Integer>> twoSum(int[] nums, int curIndex, int sum) {
        List<List<Integer>> results = new ArrayList<List<Integer>>();
        int start = curIndex + 1; //注意这里是从curIndex+1写起
        int end = nums.length - 1; 
        while (start < end) {
            if (nums[start] + nums[end] < sum) {
                start++;
            }
            else if (nums[start] + nums[end] > sum) {
                end--;
            }
            else { // (nums[start] + nums[end] == sum)
                List<Integer> result = new ArrayList<Integer>();
                result.add(nums[start]);
                result.add(nums[end]);
                results.add(result);
                start++;
                end--;
                while (start < end && nums[start] == nums[start-1] ) {  // remove dup
                    start++;
                }
                 while (start < end && nums[end] == nums[end+1] ) {  // remove dup
                    end--;
                }
            }
        }
        return results; 
    }
}
