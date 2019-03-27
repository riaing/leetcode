Suppose a sorted array is rotated at some pivot unknown to you beforehand.

(i.e., 0 1 2 4 5 6 7 might become 4 5 6 7 0 1 2).

Find the minimum element.

You may assume no duplicate exists in the array.

思路：Find Minimum in Rotated Sorted Array I
和Search in Rotated Sorted Array I这题换汤不换药。同样可以根据A[mid]和A[end]来判断右半数组是否sorted：
原数组：0 1 2 4 5 6 7
情况1：  6 7 0 1 2 4 5   
情况2：  2 4 5 6 7 0 1  
(1) A[mid] < A[end]：A[mid : end] sorted => min不在A[mid+1 : end]中
搜索A[start : mid]
(2) A[mid] > A[end]：A[start : mid] sorted且又因为该情况下A[end]<A[start] => min不在A[start : mid]中
搜索A[mid+1 : end]
(3) base case：
a. start =  end，必然A[start]为min，为搜寻结束条件。
b. start + 1 = end，此时A[mid] =  A[start]，而min = min(A[mid], A[end])。而这个条件可以合并到(1)和(2)中。

public class Solution {
    public int findMin(int[] nums) {
        if(nums== null || nums.length ==0){
            return -1;
        }
        int start =0 ;
        int end = nums.length -1;
        int mid =0;
        
        while(start < end){
            mid = start + (end - start )/2;
            if(nums[mid] >nums[mid+1]){
                return nums[mid+1];
            }
            if (nums[mid]< nums[end]){ //mid could be the min , so don't do end = mid+1 
                end = mid;
            }
            else if(nums[mid]> nums[end]){//consider corner case size =2, normally do start = mid+1
            //do avoid endless loop, but here will be the same as first if if in corner case,
            //so this line won't run in coner case, so it's fine 
                start = mid ; 
            }
            
        }
        return nums[start];
    }
}

-----------------------------3.21.19 九章模板---------------------------------------------------------------------
    class Solution {
    public int findMin(int[] nums) {
        if (nums== null || nums.length == 0) {
            return -1;
        }
        int start = 0;
        int end = nums.length - 1;
        
        while (start + 1 < end) {
            int mid = start + (end - start) / 2;
            if (nums[mid] < nums[end]) {
                end = mid;
            }
            // another rotated array 
            else {
                start = mid;
            }
        }
     
        return nums[start] < nums[end] ? nums[start] : nums[end];
        
    }
}
------------------------3.26.19 自己模板---------------------------------------------------------------------------
    class Solution {
    public int findMin(int[] nums) {
        if (nums== null || nums.length == 0) {
            return -1;
        }
        int start = 0;
        int end = nums.length - 1;
        
        while (start < end) {
            int mid = start + (end - start) / 2;
            if (nums[mid] <= nums[end]) { // another rotated array 
                end = mid;
            }
            else {
                start = mid+1;
            }
        }
     
        return nums[start];
        
    }
}
