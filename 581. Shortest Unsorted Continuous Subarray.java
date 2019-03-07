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
