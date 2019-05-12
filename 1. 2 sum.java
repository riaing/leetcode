Given an array of integers, return indices of the two numbers such that they add up to a specific target.

You may assume that each input would have exactly one solution, and you may not use the same element twice.

Example:

Given nums = [2, 7, 11, 15], target = 9,

Because nums[0] + nums[1] = 2 + 7 = 9,
return [0, 1].
---------------------------------------------------------------------------------
+tags: Map
/**
* Hashmap, 每个数放进去之前，查看target-x是否已在map里
*  O(n) time, O(n) space 

* sort, two pointer from start and end 
* O(nlgn), o(1) space  


*/
class Solution {
    public int[] twoSum(int[] nums, int target) {
        int[] result = new int[2]; 
        if (nums == null) {
            return result;
        }
        Map<Integer, Integer> keyValue = new HashMap<Integer, Integer>(); 
        for (int i = 0; i < nums.length; i ++ ) {
            if (keyValue.containsKey(target - nums[i])) {
                return new int[] {keyValue.get(target - nums[i]), i};
            }
            keyValue.put(nums[i], i);
        }
        return result; 
    }
}

