Given an array of integers, find two non-overlapping subarrays which have the largest sum.
The number in each subarray should be contiguous.
Return the largest sum.

Example
Example 1:

Input:
[1, 3, -1, 2, -1, 2]
Output:
7
Explanation:
the two subarrays are [1, 3] and [2, -1, 2] or [1, 3, -1, 2] and [2].
Example 2:

Input:
[5,4]
Output:
9
Explanation:
the two subarrays are [5] and [4].
Challenge
Can you do it in time complexity O(n) ?

Notice
The subarray should contain at least one number

-------DP, 类似sell and buy stock at most Kth transaction的思路，注意边界条件的m[i][j] = Min, when i > j.   o(K*N)---------------------

public class Solution {
    /*
     * @param nums: A list of integers
     * @return: An integer denotes the sum of max two non-overlapping subarrays
     */
    public int maxTwoSubArrays(List<Integer> nums) {
        int[][] m = new int[3][nums.size()+1];
        for (int i = 1; i <= 2; i++) {
            int maxDiff = 0;
            for (int j = 1; j <= nums.size(); j++) {
                maxDiff = Math.max(maxDiff, m[i-1][j-1]);
                -------------------
                写法一：
                if (i > j-1) {  //要让m[i][j-1]合理,这里其实写成base case: m[i][j] -> i > j时 m[i][j] = Min_value更好
                    m[i][j] = maxDiff + nums.get(j-1);
                }
                else {
                    m[i][j] = Math.max(m[i][j-1], maxDiff + nums.get(j-1)); 
                }
                -----------------------
                写法二：
                if (i > j-1) {
                    m[i][j-1] = Integer.MIN_VALUE;
                }
                 m[i][j] = Math.max(m[i][j-1], maxDiff + nums.get(j-1)); 
               ------------------------------
                maxDiff = maxDiff + nums.get(j-1);
            }
        }
        return m[2][nums.size()];
    }
}

--------------------------------------从头到尾，从尾到头扫一遍 -------------------------------------------------
 public class Solution {
/*
     * @param nums: A list of integers
     * @return: An integer denotes the sum of max two non-overlapping subarrays
     
     
     类似max subarray I的解法，区别是我们这里扫两遍，从左边向右边的时候算出globalmax，从右边向左边的时候算出localMax，然后找两边加起来的最大值。
     首先我们定义两个变量，localMax[i]为以i结尾的subarray中最大的值，globalMax[i]定义为[0, i]范围中最大的subarray(subarray不一定需要以i结尾)。
     递推表达式是：
        localMax[i] = max(localMax[i - 1] + A[i], A[i]);
        globalMax[i] = max(globalMax[i - 1], localMax[i]);

    从右边向左边的时候维护localMax[i]，这时的localMax[i]指的是以i开头的最大的subarray
        localMax[i] = max(localMax[i + 1] + A[i], A[i]);
    扫两遍，时间复杂度O(n)，空间复杂度O(n)，从右边向左边扫的时候不需要开辟一个新的数组，并且计算最后最大值可以在第二次循环的时候一起做了，代码如下：
 */
     
    public int maxTwoSubArrays(List<Integer> nums) {
       int localMax = nums.get(0); // 以i-1结尾的最大subarray. 可以像下面rightToLeftLocalMax一样写成一个数的形式，而不需要用数组
       int[] globalMax = new int[nums.size()]; // 前i-1个（不需要以i-1结尾）的最大subarray
       globalMax[0] = nums.get(0);
       for (int i = 1; i < nums.size(); i++) {
           localMax = Math.max(localMax, 0) + nums.get(i); // 等于localMax[i] = max(localMax[i - 1] + A[i], A[i]);
           globalMax[i] = Math.max(globalMax[i-1], localMax);
       }
       
       int[] rightToLeftLocalMax = new int[nums.size()]; // 以i-1开头的最大subarray 
       
       int res = Integer.MIN_VALUE;
        for (int i = nums.size()-1; i > 0; i--) {
            if (i == nums.size()-1) {
                rightToLeftLocalMax[i] = nums.get(nums.size()-1);
            }
            else {
                rightToLeftLocalMax[i] = Math.max(rightToLeftLocalMax[i+1], 0) + nums.get(i);
            }
           
           res = Math.max(res, rightToLeftLocalMax[i] + globalMax[i-1]);
       }
       
       return res; 
    }
}
