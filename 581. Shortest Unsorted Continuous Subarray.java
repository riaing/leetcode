Given an integer array, you need to find one continuous subarray that if you only sort this subarray in ascending order, then the whole array will be sorted in ascending order, too.

You need to find the shortest such subarray and output its length.

Example 1:
Input: [2, 6, 4, 8, 10, 9, 15]
Output: 5
Explanation: You need to sort [6, 4, 8, 10, 9] in ascending order to make the whole array sorted in ascending order.
Note:
Then length of the input array is in range [1, 10,000].
The input array may contain duplicates, so ascending order here means <=. 


-----------先sortarray，再对比与原array不一样的index即可，可见leetcode solution----------------------------------
Time complexity : O(nlogn)O(nlogn). Sorting takes nlognnlogn time.
Space complexity : O(n)O(n). We are making copy of original array. 


----------------------------用 monotone stack -------------------------------------------------------------
Time complexity : O(n). Stack of size n is filled.
Space complexity : O(n)O. Stack size grows upto n. 

class Solution {
    public int findUnsortedSubarray(int[] nums) {
        Deque<Integer> stack = new LinkedList<Integer>();
        int leftBound = Integer.MAX_VALUE; //or = nums.length : because store index, so the max index won't exceed array's length 
        int rightBound = Integer.MIN_VALUE; // or = 0: because it won't smaller than the first element's index. 
        for (int i = 0; i < nums.length; i++) {
            while (!stack.isEmpty() && nums[stack.peek()] > nums[i]) {
                // Get the minimun index that need to swap.
                leftBound = Math.min(leftBound, stack.pop());
            }
            stack.push(i);
        }
        stack.clear();
        
        //find the rightBound by reverse from the end of the array 
        for (int i = nums.length -1; i >=0; i--) {
            while (!stack.isEmpty() && nums[stack.peek()] < nums[i]) {
                rightBound = Math.max(rightBound, stack.pop());
            }
            stack.push(i);
        }
        return rightBound < leftBound ? 0 : rightBound - leftBound + 1; 
        
    }
}

--------------- 3.17.2022 2 pointer + (optional) binary search ------------------------------------------------------------------------------------------------------------
    
    /*
1 From the beginning and end of the array, find the first elements that are out of the sorting order. The two elements will be our candidate subarray.
2 Find the maximum and minimum of this subarray.
3 Extend the subarray from beginning to include any number which is bigger than the minimum of the subarray.
4 Similarly, extend the subarray from the end to include any number which is smaller than the maximum of the subarray.

Time: O(n), space O(1)

*/
class Solution {
    public int findUnsortedSubarray(int[] nums) {
        // 1. find start and end of the subarray that needs to be sorted 
        int start = 0; 
        int end = nums.length - 1; 
        while (start < nums.length -1 && nums[start] <= nums[start+1]) {
            start++;
        }
        
        if (start == nums.length - 1) { // 特殊判断：当array已经是sort时
            return 0;
        }
        
        while (end > 0 && nums[end] >= nums[end-1]) {
            end--;
        }
        
        // 2, find min, and max in the given window 
        int min = Integer.MAX_VALUE;
        int max = Integer.MIN_VALUE; 
        for (int i = start; i <= end; i++) {
            min = Math.min(min, nums[i]);
            max = Math.max(max, nums[i]);
        }
        
        // 3. 方法1. binary search - check if needs to expand the window: if start's left has number < min, and end's right has number > max 
        // 3.1 find the first number > min on the left 
        int newStart = fistNumLargerThanK(0, start - 1, nums, min); 
        // 3.2 find the firt number < max on the right 
        int newEnd = fistNumSmallerThanK(end+1, nums.length-1, nums, max);
        
        
        /* 3. 方法2：直接扫不用binary search
        // extend the subarray to include any number which is bigger than the minimum of the subarray 
        while (newStart > 0 && nums[newStart - 1] > min)
          newStart--;
        // extend the subarray to include any number which is smaller than the maximum of the subarray
        while (newEnd < nums.length - 1 && nums[newEnd + 1] < max)
          newEnd++;
        */

        return newEnd - newStart + 1; 
    }
    
    private int fistNumLargerThanK(int left, int right, int[] nums, int k) {
        while (left <= right) {
            int mid = right + (left - right) / 2; 
            if (nums[mid] > k) {
                right = mid - 1;
            }
            else {
                left = mid + 1;
            }
        }
        return left;
    }
    
    private int fistNumSmallerThanK(int left, int right, int[] nums, int k) {
        while (left <= right) {
            int mid = right + (left - right) / 2; 
            if (nums[mid] < k) {
                left = mid + 1;
            }
            else {
                right = mid - 1;
            }
        }
        return right;
    }
}
