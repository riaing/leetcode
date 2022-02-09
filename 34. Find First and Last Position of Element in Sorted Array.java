Given an array of integers nums sorted in ascending order, find the starting and ending position of a given target value.

Your algorithm’s runtime complexity must be in the order of O(log n).

If the target is not found in the array, return [-1, -1].

Example 1:

Input: nums = [5,7,7,8,8,10], target = 8
Output: [3,4]
Example 2:

Input: nums = [5,7,7,8,8,10], target = 6
Output: [-1,-1]


------------------03/04/2021 最新模板 ---------------------------------
    class Solution {
    public int[] searchRange(int[] nums, int target) {
        if (nums == null || nums.length == 0) {
            return new int[]{-1,-1};
        }
        int[] res = new int[2];
        int start = 0;
        int end = nums.length-1;
        // first, find the right one 
        res[1] = findRight(nums, target, start, end);
        res[0] = findLeft(nums, target, start, end); 
        // System.out.println(findRight(nums, target, start, end));
        return res;
        
    }
    
    // find the LAST occurance of a target 
    private int findRight(int[] nums, int target, int start, int end) {
        while (start < end) {
            // step3： 以为已经将start赋值为 mid， 所以调整 mid + 1来避免两数infinite loop
            int mid = start + (end - start) / 2 + 1; 
            
            // step2： 分析出此题的特殊情况是得start = mid查右边，将=的情况与< 合并，修改start的赋值
            if (nums[mid] <= target) {
                start = mid;
            }
            if (nums[mid] > target) {
                end = mid -1;   
            }
            
        }
        return nums[end] == target ? end : -1;
        
    }
    
       private int findLeft(int[] nums, int target, int start, int end) {
        while (start < end) {
            int mid = start + (end - start) / 2; 
            if (nums[mid] < target) {
                start = mid+1;
            }
            else if (nums[mid] >= target) {
                end = mid;
            } 
        }
        return nums[start] == target ? start : -1;   
    }
    
    
}

----------------------------binary search拓展-----------------------------
class Solution {
    public int[] searchRange(int[] nums, int target) {
        if (nums == null || nums.length == 0) {
            return new int[]{-1,-1};
        }
        int[] res = new int[2];
        int start = 0;
        int end = nums.length-1;
        // Find Last Position of Target 
        res[1] = findRight(nums, target, start, end);
        // Find First Position of Target
        res[0] = findLeft(nums, target, start, end);  
        return res;
        
    }
    
    private int findRight(int[] nums, int target, int start, int end) {
        while (start + 1 < end) {
            int mid = start + (end - start) / 2; 
            if (nums[mid] <= target) {
                start = mid;
            }
            // nums[mid] > target
            else {
                end = mid;
            }
        }
        if (nums[end] == target) {
            return end;
        }
        else if (nums[start] == target) {
            return start;
        }
        else {
            return -1;
        }
    }
    
       private int findLeft(int[] nums, int target, int start, int end) {
        while (start + 1 < end) {
            int mid = start + (end - start) / 2; 
            if (nums[mid] < target) {
                start = mid;
            }
            // nums[mid] >= target
            else {
                end = mid;
            }
        }
        // this order matters! 
        if (nums[start] == target) {
            return start;
        }
        else if (nums[end] == target) {
            return end;
        }
        else {
            return -1;
        }
    }   
}
========== 2021 [练习，不用记】通过找第一个小于k的数 ===================================
    class Solution {
    public int[] searchRange(int[] nums, int target) {
        int left = firstElementSmallerThenK(nums, target); // 找到第一个小于 target 的数
        if (left >= nums.length - 1 || nums[left+1] != target) { // corner case 以及 target 不存在
            left = -1;
        }
        else {
            left = left+1; //包括了 left == -1
        }
        
        int right = firstElementSmallerThenK(nums, target+1); // 找第一个小于 target+1的数
        if (right < 0 || (right > nums.length - 1) || nums[right] != target) { // corner case 以及 target 不存在
            right = -1;
        }
        
        return new int[]{left, right};  
    }
    
    
    // find first element < k in dup array
    private int firstElementSmallerThenK(int[] nums, int target) {
        int start = 0;
        int end = nums.length - 1;
        while (start <= end) {
            int mid = start + (end - start) / 2;
            if (nums[mid] < target) {
                start = mid + 1;
            }
            if (nums[mid] >= target) {
                end = mid - 1;
            }
        }
        return end;
        
    }
}

============= 【纯练习】找到第一个大于 k 的元素 =====================================
    class Solution {
    public int[] searchRange(int[] nums, int target) {

        int left = firstElementLargerThenK(nums, target-1); // 找到第一个大于 target-1 的数
        if (left < 0 || left > nums.length - 1 || nums[left] != target) { // corner case 以及 target 不存在
            left = -1;
        }
        else {
            left = left; //包括了 left == -1
        }
        
        int right = firstElementLargerThenK(nums, target); // 找第一个大于 target的数
        if (right <= 0 || nums[right-1] != target) { // corner case 以及 target 不存在
            right = -1;
        }
        else {
            right = right - 1; 
        }
        return new int[]{left, right};  
    }
    
    
    // find first element > k in dup array 
    private int firstElementLargerThenK(int[] nums, int target) {
        int start = 0;
        int end = nums.length - 1;
        while (start <= end) {
            int mid = start + (end - start) / 2;
            if (nums[mid] <= target) {
                start = mid + 1;
            }
            if (nums[mid] > target) {
                end = mid - 1;
            }
        }
        return start; 
        
    }
}

=========== 【纯练习】 找到第一个大于等于 k 的元素 ================================== 

class Solution {
    public int[] searchRange(int[] nums, int target) {

        int left = firstElementLargerOrEqualToK(nums, target-1); // 找到第一个大于等于 target-1 的数
        if (left >= 0 && left < nums.length && nums[left] == target) { //left本身已经大于target-1时
            left = left;
        }
        else if (left < nums.length -1 && nums[left+1] == target) { // left刚好等于 target-1时 
            left++;
        }
        else {
            left = -1; 
        }
        
        int right = firstElementLargerOrEqualToK(nums, target); // 找第一个大于等于 target的数
        if (right < 0 || right > nums.length - 1 || nums[right] != target) {
            right = -1; 
        }
       
        return new int[]{left, right};  
    }
    
    
    // find first element >= k in dup array 
    private int firstElementLargerOrEqualToK(int[] nums, int target) {
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
            return start - 1;
        }
        return start; 
    }
}
===========[纯练习】  找到第一个小于等于 k 的元素 =============================================== 

class Solution {
    public int[] searchRange(int[] nums, int target) {
        int left = firstElementSmallerOrEqualToK(nums, target); // 找到第一个小于等于 target 的数
        if (left < 0 || left > nums.length - 1 || nums[left] != target) {
            left = -1; 
        }
      
        
        int right = firstElementSmallerOrEqualToK(nums, target+1); // 找第一个小于等于 target+1的数
        if (right >= 0 && right < nums.length && nums[right] == target) { //right本身已经小于target+1时，就有可能是 target
            right = right;
        }
        else if (right > 0 && nums[right-1] == target) { // right刚好等于target+1时，它的前一个可能是 target 
            right--;
        }
        else {
            right = -1; 
        }
       
        return new int[]{left, right};  
    }
    
    
    // find first element <= k in dup array 
    private int firstElementSmallerOrEqualToK(int[] nums, int target) {
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
        if (end < nums.length - 1 && nums[end+1] == target) { // 和 找第一个<k 的区别
            return end + 1;
        }
        return end; 
    }
}
======= 最终解法（educative 模板），找左边界和右边界 =======================
            class Solution {
    public int[] searchRange(int[] nums, int target) {
        int left = firstElementSmallerOrEqualToK(nums, target); // 找到第一个小于等于 target 的数
        if (left < 0 || left > nums.length - 1 || nums[left] != target) {
            left = -1; 
        }
      
        
        int right = firstElementLargerOrEqualToK(nums, target); // 找第一个大于等于 target 的数
        if (right < 0 || right > nums.length - 1 || nums[right] != target) {
            right = -1; 
        }
       
        return new int[]{left, right};  
    }
    
    
    // find first element <= k in dup array 
    private int firstElementSmallerOrEqualToK(int[] nums, int target) {
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
        if (end < nums.length - 1 && nums[end+1] == target) { // 和 找第一个<k 的区别
            return end + 1;
        }
        return end; 
    }
    
    // find first element >= k 
     private int firstElementLargerOrEqualToK(int[] nums, int target) {
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
            return start - 1;
        }
        return start; 
    }
}
