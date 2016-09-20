A peak element is an element that is greater than its neighbors.

Given an input array where num[i] ≠ num[i+1], find a peak element and return its index.

The array may contain multiple peaks, in that case return the index to any one of the peaks is fine.

You may imagine that num[-1] = num[n] = -∞.

For example, in array [1, 2, 3, 1], 3 is a peak element and your function should return the index number 2.

若A[mid - 1] > A[mid]，则A[mid]左侧必定存在一个peak，若A[mid + 1] > A[mid]，则A[mid]右侧必定存在一个peak
public class Solution {
    public int findPeakElement(int[] nums) {
        if(nums == null || nums.length ==0 ){
            return -1;
        }
        int start = 0; 
        int end = nums.length -1; 
        int mid=end/2; 
        
        while(start +1 < end){ //at least three element 
             mid= start +(end -start)/2; 
            if(nums[mid+1]> nums[mid]){
                start = mid+1; // don't need to include mid since it abviously not a peak 
               
            }
             else if(nums[mid-1]>nums[mid]){
                end = mid-1;
             
            }
            else{
                return mid; 
            }
        }
        
        mid = nums[start]> nums[end] ? start :end; 
        return mid; 
        
    }
}

---------better way-----------

public class Solution {
    public int findPeakElement(int[] nums) {
        if(nums == null || nums.length ==0 ){
            return -1;
        }
        int start = 0; 
        int end = nums.length -1;         
        while(start < end){
            int mid=(start+end)/2;
            if ((mid > 0) && (nums[mid] < nums[mid-1])) { //if mid is corner case(left), consider it's right part( else if) 
                end = mid - 1; // mid is abviously not a peak, don't include it 
            } else if ((mid < nums.length - 1) && (nums[mid] < nums[mid+1])) {//if mid is last element, determine if it is smaller than
            //the prevoius one(above if), if no, return mid 
                start = mid + 1; //mid is not peak 
            } else{
                return mid; 
            }
        }
        //return (end < 0)?0:end; //don't need condition here , end is always valid since line 42,43 
       // return (start > nums.length-1) ? nums.length-1 :start; //start is always valid here 
       return start; 
    }
}
