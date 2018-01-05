Given an array of integers and an integer k, find out whether there are two distinct indices i and j in the array such that nums[i] = nums[j] and the absolute difference between i and j is at most k.
//注意duplicate number in the array
class Solution {
    public boolean containsNearbyDuplicate(int[] nums, int k) {
        if (nums == null || nums.length ==0) {
            return false;
        } 
        //Use a map to record the num and it's indecies. 
        Map<Integer, Integer> numMap = new HashMap<Integer, Integer>();
        for (int i = 0; i < nums.length; i++) {
            if (numMap.containsKey(nums[i])) {
                if (i - numMap.get(nums[i]) <= k) {
                    return true;
                }
            
            }
            // Two situation: 1, map doesn't have the key -> add it in. 2, map contains this key: update the value(indice) to the recent one. ex: [1,0,1,1] & k = 1: need to update (num, indice) (1,0) -> (1,2) 
            numMap.put(nums[i], i);
            
        }
        return false;
    }
}
