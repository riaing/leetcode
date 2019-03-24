Given an integer array, return the k-th smallest distance among all the pairs. The distance of a pair (A, B) is defined as the absolute difference between A and B.

Example 1:
Input:
nums = [1,3,1]
k = 1
Output: 0 
Explanation:
Here are all the pairs:
(1,3) -> 2
(1,1) -> 0
(3,1) -> 2
Then the 1st smallest distance pair is (1,1), and its distance is 0.
Note:
2 <= len(nums) <= 10000.
0 <= nums[i] < 1000000.
1 <= k <= len(nums) * (len(nums) - 1) / 2.

----------------Binery search w/ sliding window----------------------------------------------------------------
Time: Time Complexity: O(NlogW+NlogN), where N is the length of nums, and W is equal to nums[nums.length - 1] - nums[0]. 
The logW factor comes from our binary search, and we do O(N) work inside our call to calculate count(sliding window). 
The final O(NlogN) factor comes from sorting.

class Solution {
    public int smallestDistancePair(int[] nums, int k) {
        //sort the array 
        Arrays.sort(nums);
        int left = 0; // smallest distance
        int right = nums[nums.length-1] - nums[0]; // largest distance
        while(left < right) {
            int mid = left + (right - left) / 2;
            if (smallerEqualToTarget(nums, mid) >= k) {
                right = mid;
            }
            else {
                left = mid + 1;
            }
        }
        return left;
        
    }
    -------------------------sliding window写法一，for loop: o(n)-------------------------------------
    // find how many distance group in the array that is <= target distance
    private int smallerEqualToTarget(int[] nums, int target) {
        int cnt = 0;
        int start = 0;
        for (int i = 1; i < nums.length; i++) {
            while (start < i) {
                if (nums[i] - nums[start] <= target) {
                    cnt += i - start;
                    break;
                }
                else {
                    start++; 
                } 
            }
        }
        return cnt;
    }
    -----------------------------for loop结束---------------------------------------------------------
    
    ----------------------------sliding window 写法二，直接while，想象成两个指针移动: O(n)----------------------
    / find how many distance group in the array that is <= target distance
    // sliding window - 保持一个容量<= k的window。大于k时，start++，否则更新cnt，再end++。
    private int smallerEqualToTarget(int[] nums, int target) {
        int cnt = 0;
        int start = 0;
        int end = 0;
       
            while (start <= end && end < nums.length) {
                if (nums[end] - nums[start] <= target) {
                    // 举例：[1,2,3]start = 0，end=2，target=1。如果3-1已经<=target了，那说明end -(start+1), end - (start=2...)也小于target。就是要算start到end（不包括end，因为是求pair）之间有多少个数，那么end-start即可
                    cnt += end - start;
                    end++;        
                }
                else {
                    start++; 
                } 
            }
        
        return cnt;
    }
    ------------------------------写法二结束----------------------------------------------------------------
}

