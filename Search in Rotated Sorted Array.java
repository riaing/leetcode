Suppose a sorted array is rotated at some pivot unknown to you beforehand.

(i.e., 0 1 2 4 5 6 7 might become 4 5 6 7 0 1 2).

You are given a target value to search. If found in the array return its index, otherwise return -1.

You may assume no duplicate exists in the array.


public class Solution {
    public int search(int[] nums, int target) {
        if(nums == null|| nums.length ==0){
            return -1;
        }
        int start = 0;
        int end = nums.length -1;
        
        while(start <end ){
            int mid = start +(end -start)/2;
            if(nums[mid] == target){
                return mid;
            }
            //一下两else if中都是考虑数列连续的情况，比较简单，容易判断。并且剩下的都归于else。 
            else if(nums[mid]> nums[end]){   
                if( target< nums[mid] && target > nums[end]){  //  3，4，5，6，7，1，2 因为前面比较的是mid和end，所以这里也有target和end，相对清晰
                    end = mid; 
                }
                else{
                    start =mid + 1;  
                }
            }
            else if(nums[mid]< nums[end]){
                if(target > nums[mid] && target <= nums[end]){//6，7，1，2，3，4，5 这里有等于号，因为现在的end没变
                    start = mid+1;
                }
                else{
                    end = mid;
                }
            }
        }
        if(nums[start] == target){
            return start;
        }
        else{
            return -1;
        }
        
    }
}
