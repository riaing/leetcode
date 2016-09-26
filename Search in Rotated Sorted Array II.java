ollow up for "Search in Rotated Sorted Array":
What if duplicates are allowed?

Would this affect the run-time complexity? How and why?

Write a function to determine if a given target is in the array.

public class Solution {
    public boolean search(int[] nums, int target) {
        if(nums == null || nums.length == 0){
            return false;
        }
        int start = 0;
        int end = nums.length -1; 
        while (start < end){
            int mid = start + (end -start)/2;
            if(nums[mid] == target){
                return true;
            }
            else if( nums[mid] == nums[end]){
                end = end -1;
            }
            else if (nums[mid]<nums[end]){
                if(target > nums[mid] && target <= nums[end]){ //注意等号！
                    start = mid+1;
                }
                else{
                    end = mid;
                }
            }
            else if( nums[mid]> nums[end]){
                if(target< nums[mid] && target >= nums[start]){ //注意等号
                    end = mid;
                }
                else{
                    start = mid +1; 
                }
            }
        }
        if(nums[start] == target){
            return true;
        }
        else{
            return false; 
        }
    }
}
