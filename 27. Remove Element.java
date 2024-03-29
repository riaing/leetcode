Given an unsorted array of numbers and a target ‘key’, remove all instances of ‘key’ in-place and return the new length of the array.

Example 1:

Input: [3, 2, 3, 6, 3, 10, 9, 3], Key=3
Output: 4
Explanation: The first four elements after removing every 'Key' will be [2, 6, 10, 9].
Example 2:

Input: [2, 11, 2, 2, 1], Key=2
Output: 2
Explanation: The first two elements after removing every 'Key' will be [11, 1].
  
  --------------------- 2 pointer -------- 
  /**
2 pointer. O（n）
*/
class Solution {
    public int removeElement(int[] nums, int val) {
        int placeToRepleceDup = 0;
        for (int i = 0; i < nums.length; i++) {
            if (nums[i] != val) {
                nums[placeToRepleceDup] = nums[i];
                placeToRepleceDup++;
            }
        }
        return placeToRepleceDup;
    }
}
