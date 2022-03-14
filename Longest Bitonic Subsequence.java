Given a number sequence, find the length of its Longest Bitonic Subsequence (LBS). A subsequence is considered bitonic if it is monotonically increasing and 
then monotonically decreasing.

Example 1:

Input: {4,2,3,6,10,1,12}
Output: 5
Explanation: The LBS is {2,3,6,10,1}.
Example 2:

Input: {4,2,5,9,7,6,10,3,1}
Output: 7
Explanation: The LBS is {4,5,9,7,6,3,1}.

----------------------------DP ---------------------------------------------------

找 个节点 i，0-i 为 longest increasing subsequence； i-end 为 longest decreasing subsequence。就转变成基础题： https://leetcode.com/problems/longest-increasing-subsequence/ 

class LBS {

  private int findLBSLength(int[] nums) {
      int[] right = new int[nums.length]; // from i to the end that MUST include i, longest decreasing subsequence 
      int[] rightFrom = new int[nums.length]; // 记录上一个的路径
      for (int i = nums.length-1; i >= 0; i--) {
          right[i] = 1; // 只取本身
          rightFrom[i] = -1; //没有上一个元素，自己就是第一个
          for (int j = i + 1; j < nums.length; j++) {
              if (nums[i] > nums[j]) {
                  rightFrom[i] = 1 + right[j] > right[i] ? j : rightFrom[i];
                  right[i] = Math.max(right[i], 1 + right[j]); //顺序不能换。。。否则 rightFrom 拿不到值
              }
          }
      }
    // System.out.println("check from[4] " + rightFrom[4]); 
      
      int[] left = new int[nums.length]; // from 0 to i that MUST include i, longest increasing subsequence 
      int[] leftFrom = new int[nums.length]; // 记录上一个的路径
      for (int i = 0; i < nums.length; i++) {
          left[i] = 1;
          leftFrom[i] = -1; 
          for (int j = i - 1; j >= 0; j--) {
              if (nums[i] > nums[j]) {
                  leftFrom[i] = 1 + left[j] > left[i] ? j : leftFrom[i];
                   left[i] = Math.max(left[i], 1 + left[j]);
              }
          }
      }

      
      // combine the two and find the max bitconic sub sequence. since both dp[] contains i, need to substract 1 duplicate
      int max = 0; 
      int index = 0; // record the index that separate nums[] apart
      for (int i = 0; i < nums.length; i++) {
          int curBitcoin = right[i] + left[i] - 1;
          if (curBitcoin > max) {
              max = curBitcoin;
              index = i;
          }
      }
      
      // 求 path
      int rightIndex = index;
      String rightPath = "";
      while (rightIndex < nums.length && rightIndex >= 0) {
          rightPath = rightPath + "->" + nums[rightIndex];
          rightIndex = rightFrom[rightIndex];
      }
      
      int leftIndex = index;
      String leftPath = "";
      while (leftIndex < nums.length && leftIndex >= 0) {
          leftPath = nums[leftIndex] + " <-" + leftPath;
          leftIndex = leftFrom[leftIndex];
      }
      
      
      System.out.println("left path " + leftPath + " right path " + rightPath); 
    return max;
  }

  public static void main(String[] args) {
    LBS lbs = new LBS();
    int[] nums = {4,2,3,6,10,1,12};
    System.out.println(lbs.findLBSLength(nums));
    nums = new int[]{4,2,5,9,7,6,10,3,1};
    System.out.println(lbs.findLBSLength(nums));
  }
}
 
