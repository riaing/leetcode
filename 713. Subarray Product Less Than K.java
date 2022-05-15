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
  
  -------------- DP做法：超时。 ----------------------
class Solution {
    public int numSubarrayProductLessThanK(int[] nums, int k) {
        int[][] dp = new int[nums.length][nums.length];
        for (int i = 0; i < nums.length; i++) {
            dp[i][i] = nums[i];
        }
        
        int res = 0; 
        for (int i = 0; i < nums.length; i++) {
            if (dp[i][i] < k) {
                res++;
            }
            for (int j = i+1; j < nums.length; j++) {
                dp[i][j] = dp[i][j-1] * nums[j];
                if (dp[i][j] > k) {
                    break; 
                }
                if (dp[i][j] < k) {
                    res++;
                }
            }
        }
        return res; 
    }
}

-------------- Sliding window O(n) ----------------------------------------
 
 /*
sliding window题 + 算window内解的个数（数学）
*/
class Solution {
    public int numSubarrayProductLessThanK(int[] nums, int k) {
        int res = 0; 
        int curProduct = 1; 
        int start = 0; 
        int end;
        for (end = start; end < nums.length; end++) {
            curProduct *= nums[end];
            while (curProduct >= k && start <= end) {
                // 维持window
                curProduct /= nums[start];
                start++; 
            }
              res += end - start + 1; 
        }
        return res; 
    }
}

--------- 进阶：print所有 array ------------------------------
 
 /*
sliding window题 + 算window内解的个数（数学）
*/
class Solution {
    public int numSubarrayProductLessThanK(int[] nums, int k) {
        int res = 0; 
        int curProduct = 1; 
        int start = 0; 
        int end;
        List<List<Integer>> resPrint = new ArrayList<List<Integer>>(); 
        for (end = start; end < nums.length; end++) {
            curProduct *= nums[end];
            while (curProduct >= k && start <= end) {
                // 维持window
                curProduct /= nums[start];
                start++; 
            }
              res += end - start + 1; 
            // print result 
             List<Integer> cur = new ArrayList<Integer>();
            int curStart = start;
            while (curStart <= end) {
                cur.add(nums[curStart]);
                resPrint.add(cur);
                curStart++;
            }
        }
        System.out.println(resPrint);
        return res; 
    }
}

----------- educative的写法- 进阶版：print 所有 subarray。-------------------------------
  
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
