Map来做。O(N) 
Given an array of integers, return indices of the two numbers such that they add up to a specific target.

You may assume that each input would have exactly one solution.

Example:
Given nums = [2, 7, 11, 15], target = 9,

Because nums[0] + nums[1] = 2 + 7 = 9,
return [0, 1].


public class Solution {
    public int[] twoSum(int[] nums, int target) {
        int[] res = new int[2]; 
        if ( nums == null || nums.length == 0 ){
            return res; 
        }
        
        Map<Integer, Integer> table = new HashMap<Integer, Integer>();
        for ( int i = 0; i < nums.length; i ++ ){
            
            if ( table.containsKey(target - nums[i])){
                res[0] = i;
                res[1] = table.get( target - nums[i]); 
                return res;
            }
            else{
                table.put(nums[i], i );
            }
        }
        return res; 
        
    }
}
