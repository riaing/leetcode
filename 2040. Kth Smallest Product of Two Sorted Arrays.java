Given two sorted 0-indexed integer arrays nums1 and nums2 as well as an integer k, return the kth (1-based) smallest product of nums1[i] * nums2[j] where 0 <= i < nums1.length and 0 <= j < nums2.length.
 

Example 1:

Input: nums1 = [2,5], nums2 = [3,4], k = 2
Output: 8
Explanation: The 2 smallest products are:
- nums1[0] * nums2[0] = 2 * 3 = 6
- nums1[0] * nums2[1] = 2 * 4 = 8
The 2nd smallest product is 8.
Example 2:

Input: nums1 = [-4,-2,0,3], nums2 = [2,4], k = 6
Output: 0
Explanation: The 6 smallest products are:
- nums1[0] * nums2[1] = (-4) * 4 = -16
- nums1[0] * nums2[0] = (-4) * 2 = -8
- nums1[1] * nums2[1] = (-2) * 4 = -8
- nums1[1] * nums2[0] = (-2) * 2 = -4
- nums1[2] * nums2[0] = 0 * 2 = 0
- nums1[2] * nums2[1] = 0 * 4 = 0
The 6th smallest product is 0.
Example 3:

Input: nums1 = [-2,-1,0,1,2], nums2 = [-3,-1,2,4,5], k = 3
Output: -6
Explanation: The 3 smallest products are:
- nums1[0] * nums2[4] = (-2) * 5 = -10
- nums1[0] * nums2[3] = (-2) * 4 = -8
- nums1[4] * nums2[0] = 2 * (-3) = -6
The 3rd smallest product is -6.
 

Constraints:

1 <= nums1.length, nums2.length <= 5 * 104
-105 <= nums1[i], nums2[j] <= 105
1 <= k <= nums1.length * nums2.length
nums1 and nums2 are sorted.

------------------------------------------------ Binary Search (n+m)log(10^10) -------------------------------------------------
/*
bs: 乘积[lower, upper] ， 可用无穷小到无穷大 
主思路：
while (lower < upper)
    m = lower + (upper - lower) / 2; 
    乘积<= m 的有多少个: countSmallerOrEqual(m) = count 
    if (count < k) {m 猜的偏小。 lower = m + 1}。 这里不能= m 是因为 m肯定不是解
    else { m 可能是答案。 upper = m }
return lower/ upper 


countSmallerOrEqual 写法： 
固定nums1 i，找nums2 j，使得 n1[i] * n2[j] <= m 

int res; 
1. n1[i] > 0 : 找到j使得 n2[j] <=  floor(m / n1[i])， 则 【0.。。j】 这么多个pair 都是解。 res += j+1. => 用bs
2. n1[i]  = 0 : 
    - if m >= 0, n2的所有元素都能配对。 res += n2.length 
    - if m < 0, 衡不成立，返回0 , res += 0; 
3. n1[i] < 0; n2[j] >= ceil(m / n1[i]). 则 [j.... n2]都可以。res += (n2.length -1) - j + 1 => 用bs


技巧： 
int index = Arrays.binarySearch(nums1, 1); // 找不到return -1， 找到就return 任意一个解的index
*/
class Solution {
    public long kthSmallestProduct(int[] nums1, int[] nums2, long k) {
        // https://www.youtube.com/watch?v=Ct-seYTr1dM 
        // int index = LargerOrEqualToK(nums2, 4);   
        
        
        // int test = countSmallerOrEqual(nums1, nums2, -9); 
        // System.out.println(test); 
        //   return 0; 
        long lower =  (long) Math.pow(10,10) * -1; 
        long upper =  (long) Math.pow(10,10);
        while (lower < upper) {
            long m =  lower + (upper - lower) / 2; 
            long count = countSmallerOrEqual(nums1, nums2, m);
            // System.out.println("m " + m + ". count " + count);
            if (count < k) {
                lower = m + 1;
            }
            else {
                upper = m;
            }
        }
        return lower;
      
    }
    
    // 找到pair数，pair的乘积 <= m 
    private int countSmallerOrEqual(int[] n1, int[] n2, long m) {
        int res = 0; 
        // 1, 固定n1
        for (int i = 0; i < n1.length; i++) {
            if (n1[i] > 0) { 
                // 找到j使得 n2[j] <=  floor(m / n1[i])
                int j = smallerOrEqualToK(n2, (int) Math.floor(m * 1.0 / n1[i])); // 一定要乘以 1.0 变成double
                if (j >= 0) { // 可能超range，所以必须检验
                    res += j+1; // [0...j]都是解
                }
            }
            else if (n1[i] == 0) {
                if (m >= 0) {
                    res += n2.length;
                }
                // 否则衡不成立
            }
            else {
                //  找到j使得 n2[j] >= ceil(m / n1[i])
                int j = LargerOrEqualToK(n2, (int) Math.ceil(m * 1.0 / n1[i]));
                if (j < n2.length && j >= 0) { // 可能超range，所以必须检验
                    res += n2.length -1 - j + 1;
                }
                // System.out.println(n2.length -1 - j + 1);
                 
            }
        }
        return res; 
    }
    
    private int smallerOrEqualToK(int[] nums, int target) {
        int start = 0;
        int end = nums.length - 1;
        while (start <= end) {
            int mid = start + (end - start) / 2;
            if (nums[mid] < target) { 
                start = mid + 1; 
            }
            if (nums[mid] >= target) { // 这里有可能已经丢掉 最后一个 target 了，所以最后判断
                end = mid - 1;
            }
        }
        if (end < nums.length - 1 && nums[end+1] == target) { // 和 找第一个>k 的区别
            end = end + 1;
        }
        // 重复数组会返回最左边那个 [1,1,1]。 如果要找最右边那个，遍历一下
        while (end > 0 && end + 1 < nums.length && nums[end+1] == nums[end]) {
            end++;
        }
        
        return end; // 注意end可能小于左边界
    }
    
    // 
    private int LargerOrEqualToK(int[] nums, int target) {
        int start = 0;
        int end = nums.length - 1;
        while (start <= end) {
            int mid = start + (end - start) / 2;
            if (nums[mid] <= target) { // 这里有可能已经丢掉 最后一个 target 了，所以最后判断
                start = mid + 1; 
            }
            if (nums[mid] > target) {
                end = mid - 1;
            }
        }
        if (start > 0 && nums[start-1] == target) { // 和 找第一个>k 的区别
            start = start - 1;
        }
        // 【optinoal】处理dup。如果要返回最左边那个
        while (start < nums.length && start - 1 >= 0 && nums[start-1] == nums[start]) {
            start--;
        }

        return start; 
    }
}


