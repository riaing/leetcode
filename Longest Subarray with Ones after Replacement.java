https://www.educative.io/courses/grokking-the-coding-interview/B6VypRxPolJ 

Problem Statement#
Given an array containing 0s and 1s, if you are allowed to replace no more than ‘k’ 0s with 1s, find the length of the longest contiguous subarray having all 1s.

Example 1:

Input: Array=[0, 1, 1, 0, 0, 0, 1, 1, 0, 1, 1], k=2
Output: 6
Explanation: Replace the '0' at index 5 and 8 to have the longest contiguous subarray of 1s having length 6.
Example 2:

Input: Array=[0, 1, 0, 0, 1, 1, 0, 1, 1, 0, 0, 1, 1], k=3
Output: 9
Explanation: Replace the '0' at index 6, 9, and 10 to have the longest contiguous subarray of 1s having length 9.

------- Slide window -----------
  // O(N) 
  class ReplacingOnes {
  public static int findLength(int[] arr, int k) {
    int start = 0; 
    int len = 0;
    int zeroCount = 0; 
    for (int end = 0; end < arr.length; end++) {
      if (arr[end] == 0) {
        zeroCount++; 
      }
      // 条件是：当 window 中的0的个数小于 k 时，window 不成立
      while (k < zeroCount) {
        System.out.println("end " + end);
        System.out.println("zeroCount " + zeroCount);        
        if (arr[start] == 0){
          zeroCount--;
        }
        start++;
      }
      len = Math.max(len, end - start + 1);
    } 
    return len;
  }
}
