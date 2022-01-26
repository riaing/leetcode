Given an array nums of n integers where nums[i] is in the range [1, n], return an array of all the integers in the range [1, n] that do not appear in nums.

 

Example 1:

Input: nums = [4,3,2,7,8,2,3,1]
Output: [5,6]
Example 2:

Input: nums = [1,1]
Output: [2]
 

Constraints:

n == nums.length
1 <= n <= 105
1 <= nums[i] <= n
 

Follow up: Could you do it without extra space and in O(n) runtime? You may assume the returned list does not count as extra space.
  
  ------------- cyclic sort --------------------------
  class Solution {
    public List<Integer> findDisappearedNumbers(int[] nums) {
        //1. use cyclic sort to sort the array 
        int i = 0;
        while (i < nums.length) {
            if (nums[i] != i + 1) {
                int num = nums[i]; 
                // 2. 特殊点是有重复元素，所以碰到重复时跳过即可
                if (nums[num-1] == num) {
                    i++;
                }
                else{
                    swap(i, nums); 
                } 
               
            }
            else {
                i++;
            }
        }

        List<Integer> missing = new ArrayList<Integer>();
        for (i = 0; i < nums.length; i++) {
            if (nums[i] != i+1) {
                missing.add(i+1);
            }
        }
        // 练习 list -> array 
        // Integer[] myArray = new Integer[missing.size()];
        // myArray = missing.toArray(myArray); 
        return missing;
        
    }
    private void swap(int index, int[] nums) {
        int num = nums[index];
        int tmp = nums[num-1];
        nums[num-1] = num;
        nums[index] = tmp; 
        return;
    }
}
