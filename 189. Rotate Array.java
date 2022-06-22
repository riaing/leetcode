Given an array, rotate the array to the right by k steps, where k is non-negative.

 

Example 1:

Input: nums = [1,2,3,4,5,6,7], k = 3
Output: [5,6,7,1,2,3,4]
Explanation:
rotate 1 steps to the right: [7,1,2,3,4,5,6]
rotate 2 steps to the right: [6,7,1,2,3,4,5]
rotate 3 steps to the right: [5,6,7,1,2,3,4]
Example 2:

Input: nums = [-1,-100,3,99], k = 2
Output: [3,99,-1,-100]
Explanation: 
rotate 1 steps to the right: [99,-1,-100,3]
rotate 2 steps to the right: [3,99,-1,-100]
 

Constraints:

1 <= nums.length <= 105
-231 <= nums[i] <= 231 - 1
0 <= k <= 105
 

Follow up:

Try to come up with as many solutions as you can. There are at least three different ways to solve this problem.
Could you do it in-place with O(1) extra space?

 /*
solution：
1. 每次移一格，移k次
2. 找切口再把两段接起来。用extra space
3. 三次翻转。 O（1） 

从哪切： len - k%len. 注意k可能超过arry 长度
*/
------------------- 找到切口，用extra space --------------
/*
从哪切： len - k%len. 注意k可能超过arry 长度
*/
class Solution {
    public void rotate(int[] nums, int k) {
        // 1. 从 len - k %len 的地方放到新array
        int[] output = new int[nums.length];
        int index = 0;
        for (int i = nums.length - k % nums.length; i < nums.length; i++) {
            output[index] = nums[i];
            index++;
        }
        // 2. 把切口前的部分放到新array
        for (int i = 0; i < nums.length - k % nums.length; i++) {
            output[index] = nums[i];
            index++;
        }
        // 3. 赋值
        for (int i = 0; i < output.length; i++) {
            nums[i] = output[i];
        }
    }
}

------------------- 找到切口，三遍reverse， O（1） space ----------------------------
/*
从哪切： len - k%len. 注意k可能超过arry 长度
*/
class Solution {
    public void rotate(int[] nums, int k) {
        int len = nums.length;
        // 1. 从 len - k %len -> end, 将arry reverse。 reverse就是两指针一头一尾swap len / 2 次
        int start = len - k% len; 
        int end = len - 1;
        int times = (end - start + 1) / 2; 
        swap(nums, start, end, times);
        
        // 2. 从0 -> len - k % len -1 也reverse
        end = len - k % len - 1;  
        swap(nums, 0, end, (end+1)/ 2); 
        
        // 3. 两指针一头一尾，全array swap
        swap(nums, 0, len -1, len / 2);
        
    }
    
    // 也可以不传 times 参数
    private void swap(int[] nums, int start, int end, int times) {
        while (times > 0) {
            // swap start and end 
            int tmp = nums[end];
            nums[end] = nums[start];
            nums[start] = tmp;
            start++;
            end--;
        
            times--; 
        }
    }
}
