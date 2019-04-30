public class Solution {
    /**
     * 和Maximum Subarray II一样，找到左边的max，和右边的min，求个最大值；注意还要再找到左边的min和右边的max，求个最大值。再两个最大值比较

     * 
     * @param nums: A list of integers
     * @return: An integer indicate the value of maximum difference between two substrings
     */
    public int maxDiffSubArrays(int[] nums) {
        return Math.max(leftMaxRightMin(nums), leftMinRightMax(nums));
    }
    
    private int leftMaxRightMin(int[] nums) {
        int localMax = nums[0]; // 以i-1结尾的最大subarray. 可以像下面rightToLeftLocalMin一样写成一个数的形式，而不需要用数组
       int[] globalMax = new int[nums.length]; // 前i-1个（不需要以i-1结尾）的最大subarray
       globalMax[0] = nums[0];
       for (int i = 1; i < nums.length; i++) {
           localMax = Math.max(localMax, 0) + nums[i]; // 等于localMax[i] = max(localMax[i - 1] + A[i], A[i]);
           globalMax[i] = Math.max(globalMax[i-1], localMax);
       
       }
       
       int[] rightToLeftLocalMin = new int[nums.length]; // 以i-1开头的最小subarray 
       
       int res = Integer.MIN_VALUE;
        for (int i = nums.length -1; i > 0; i--) {
            if (i == nums.length-1) {
                rightToLeftLocalMin[i] = nums[nums.length-1];
            }
            else {
                rightToLeftLocalMin[i] = Math.min(rightToLeftLocalMin[i+1], 0) + nums[i];
            }
           
           res = Math.max(res, Math.abs(rightToLeftLocalMin[i] - globalMax[i-1]));
       }
       
       return res; 
    }
    // 这段和上面基本一样，为了清楚写成两段。面试中可以优化在一起
    private int leftMinRightMax(int[] nums) {
        int localMin = nums[0]; 
       int[] globalMin = new int[nums.length]; // 前i-1个（不需要以i-1结尾）的最小subarray
       globalMin[0] = nums[0];
       for (int i = 1; i < nums.length; i++) {
           localMin = Math.min(localMin, 0) + nums[i]; 
           globalMin[i] = Math.min(globalMin[i-1], localMin);
      
       }
       
       int[] rightToLeftLocalMax = new int[nums.length]; // 以i-1开头的最大subarray 
       
       int res = Integer.MIN_VALUE;
        for (int i = nums.length -1; i > 0; i--) {
            if (i == nums.length-1) {
                rightToLeftLocalMax[i] = nums[nums.length-1];
            }
            else {
                rightToLeftLocalMax[i] = Math.max(rightToLeftLocalMax[i+1], 0) + nums[i];
            }
           
           res = Math.max(res, Math.abs(rightToLeftLocalMax[i] - globalMin[i-1]));
       }
       
       return res; 
    }
}
