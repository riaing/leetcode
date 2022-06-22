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

--------------- 精简版 ----------------
 /*
Time: MNlg10^10 
思路：
1. 从能达到的最大值(upper)和最小值(lower)开始，取一个中间数，看有多少个pair小于中间数，根据结果二分
while (lower < upper)
    m = lower + (upper - lower) / 2; 
    count = countSmaller(m) 
    if (count < k) {lower = m } : m之前有0。。。k-1个数排队，m可能是第k个数
    else {upper = m - 1 } : 数组为[x，x，x (>=k个数), m, xxx。。。]， 说明m不是第k个数
return lower/ upper 

2. 怎么求countSmaller(m) ? 
固定nums1 i，找nums2 j，使得 n1[i] * n2[j] < m。 就是在i*j组成的数组上找 i*j
 - i >= 0时，正常binary search 找 第一个j 使得 i*j < m. educative 模板。  [0...j]之间的解都要 res += j+1 
 - i < 0 时，i*j数组为降序数组（因为nums2已经sorted），就用educative 降序模板。 [j...end]之间的解都要 res +=[len - j]
 
 

Ref：  // https://www.youtube.com/watch?v=Ct-seYTr1dM  这个解细节太多了，大方向和我的一致。
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

Time: MNlg10^10 
*/
class Solution {
    public long kthSmallestProduct(int[] nums1, int[] nums2, long k) {
        
        int[] test = {5,4,3,2,1};
        int re = findElementSmallerThanKInDesc2(test, 5);
        System.out.println(re); 
        
        long lower =  (long) Math.pow(10,10) * -1; 
        long upper =  (long) Math.pow(10,10) ;
        // 主模板，注意 m放哪边 
        while (lower < upper) {
            long m =  lower + (upper - lower) / 2 + 1;  // 防止死循环
             long count = countSmaller(nums1, nums2, m);
   
            if (count < k) { // [6,8,15,20] , k = 2, m = 8时，小于8的只有一个，那么8可能是解
                lower = m ;
            }
            else { // [6,8,15,20] , k = 2, m = 10时，小于10的都有2个了，说明10肯定不是解
                upper = m - 1;
            }
        }
        return lower;
    }


       private int findElementSmallerThanKInDesc2(int[] nums, long target) {
        int start = 0;
        int end = nums.length - 1;
        while (start <= end) {
            int mid = start + (end - start) / 2;
            if ((long) nums[mid] < target) {
                end = mid - 1;
            }
            else {
                start = mid + 1;
            }
        }
        return start;
    }
    
    // 找到pair数，pair的乘积 < m 
    private long countSmaller(int[] n1, int[] n2, long m) {
        long res = 0; 
        // 1, 固定n1
        for (int i = 0; i < n1.length; i++) {
            
            if (n1[i] >= 0) { 
                int j = firstElementSmallerThenK(n2, n1[i], m); 
                if (j >= 0) { // 可能超range
                    res += j+1; // [0...j]都是解
                }
            }
            else {
                int j = findElementSmallerThanKInDesc(n2, n1[i], m);
                if (j < n2.length && j >= 0) { // 可能超range 
                    res += n2.length - j; // 因为array是descen的，所以[j.. end]都是解
                }
            }
        }
        return res; 
    }
    
    // 正常模板找第一个<k的数
    private int firstElementSmallerThenK(int[] nums, int n1,long target) {
        int start = 0;
        int end = nums.length - 1;
        while (start <= end) {
            int mid = start + (end - start) / 2;
            if ((long) nums[mid] * n1 < target) {
                start = mid + 1;
            }
            else {
                end = mid - 1;
            }
        }
        return end;
    }
    
    // 在descending array中找第一个 < k的数, 与上面倒过来就是 
    private int findElementSmallerThanKInDesc(int[] nums, int n1,long target) {
        int start = 0;
        int end = nums.length - 1;
        while (start <= end) {
            int mid = start + (end - start) / 2;
            if ((long) nums[mid] * n1 < target) {
                end = mid - 1;
            }
            else {
                start = mid + 1;
            }
        }
        return start;
    }
    
    // -------------- 别人的code： https://leetcode.com/problems/kth-smallest-product-of-two-sorted-arrays/discuss/2006069/Java-Binary-Search-Solution-with-Explanation  -------------------
        
    public long kthSmallestProductBLAHBLAH(int[] nums1, int[] nums2, long k) {
        // 1. define search space
        long left = (long)-1e11;
        long right = (long)1e11;
        while(left < right){
            long mid = left + (right - left) / 2;
            if(countSmaller2(nums1, nums2, mid) >= k){
                right = mid;
            } else {
                left = mid + 1;
            }
        }
        // At the end, left is the product that has k smaller products before it, so the kth smallest product is left - 1
        return left - 1;
    }
    
    private long countSmaller2(int[] nums1, int[] nums2, long product){
        long count = 0;
        for(int i = 0; i < nums1.length; i++){
            // count all nums2[j] such that nums1[i] * nums2[j] < product
            if(nums1[i] >= 0){
                int left = 0;
                int right = nums2.length;
                while(left < right){
                    int mid = left + (right - left) / 2;
                    // if nums1[i] > 0, find the first occurrence of nums2[j] where nums1[i] * nums2[j] >= product
                    if((long) nums1[i] * nums2[mid] < product){
                        left = mid + 1;
                    } else {
                        right = mid;
                    }   
                }
                count += left;
            } else {
                int left = 0;
                int right = nums2.length;
                while(left < right){
                    int mid = left + (right - left) / 2;
                    // since nums1[i] is negative, nums2 is sorted
                    // nums[j] * nums[i] will be in descending order from [0...nums2.length-1]
                    // take [-4,-2,0,3] as an example, product with a negative number will become smaller if the number gets bigger
                    // want to find the last nums2[j] such that nums1[i] * nums2[j] is not smaller than product
                    if((long) nums1[i] * nums2[mid] >= product){
                        left = mid + 1;
                    } else {
                        right = mid;
                    }
                }
                count += nums2.length - left;
            }
        }
        return count;
    }
}




