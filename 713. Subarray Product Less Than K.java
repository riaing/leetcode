Given an array of integers nums and an integer k, return the number of contiguous subarrays where the product of all the elements in the subarray is strictly less than k.

 

Example 1:

Input: nums = [10,5,2,6], k = 100
Output: 8
Explanation: The 8 subarrays that have product less than 100 are:
[10], [5], [2], [6], [10, 5], [5, 2], [2, 6], [5, 2, 6]
Note that [10, 5, 2] is not included as the product of 100 is not strictly less than k.
Example 2:

Input: nums = [1,2,3], k = 0
Output: 0
 

Constraints:

1 <= nums.length <= 3 * 104
1 <= nums[i] <= 1000
0 <= k <= 106
  
  -------------- sliding window ----------------------
  /*
maintain 一个window，里面的 product 小于 k。 O（n*2)
*/
class Solution {
    public int numSubarrayProductLessThanK(int[] nums, int k) {
        int result = 0;
        for (int start = 0; start < nums.length; start++) {
            int end = start; 
            int curVal = 1; 
            while (end < nums.length) {
                curVal = curVal * nums[end];
                if (curVal < k) {
                    result++;
                    end++;
                }
                else { // pruning. 因为都是正数
                    break;
                }
            }
        }
        return result; 
    }
}

----------- 进阶版：print 所有 subarray。-------------------------------
  
  /*
https://www.educative.io/courses/grokking-the-coding-interview/RMV1GV1yPYz 

进阶版：print 所有 subarray。 
O（N*3）因为 print subarray 最差需要 O（n）：The main for-loop managing the sliding window takes O(N) but creating subarrays can take up to O(N^2) in the worst case. Therefore overall, our algorithm will take O(N^3)

Space：Ignoring the space required for the output list, the algorithm runs in O(N) space which is used for the temp list.
*/
class Solution {
    public int numSubarrayProductLessThanK(int[] nums, int k) {
        int result = 0;
        // 进阶版：print 所有 subarray
        List<List<Integer>> subArrays = new ArrayList<List<Integer>>();
        
        for (int start = 0; start < nums.length; start++) {
            int end = start; 
            int curVal = 1; 
            while (end < nums.length) {
                curVal = curVal * nums[end];
                if (curVal < k) {
                    // 进阶版：print 所有 subarrayy
                    List<Integer> curSubArray = new ArrayList<Integer>();
                    for (int i = start; i <=end; i++) {
                        curSubArray.add(nums[i]);
                    }
                    System.out.println(curSubArray);
                    subArrays.add(curSubArray);
                    
                    result++;
                    end++;
                }
                else { // pruning. 因为都是正数
                    break;
                }
            }
        }
        return result; 
    }
}
