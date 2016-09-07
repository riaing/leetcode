Given a sorted array and a target value, return the index if the target is found. If not, return the index where it would be if it were inserted in order.

You may assume no duplicates in the array.

Here are few examples.
[1,3,5,6], 5 → 2
[1,3,5,6], 2 → 1
[1,3,5,6], 7 → 4
[1,3,5,6], 0 → 0


ublic class Solution {
    public int searchInsert(int[] nums, int target) {
        int start = 0;
        int end = nums.length-1;
        while (start < end){
            int mid= start + (end - start)/2;
            if(nums[mid] == target){
                return mid;
            }
            else if(nums[mid] > target){
                end = mid-1;
            }
            else{
                start =mid +1;
            }
        }
        //因为出while时总是end=start，出while说明值不存在，那么对比start/end 和 target即可。注意，如果target<start时，target取代start的index。
        if(nums[start]>target){
            return start;
        }
        if(nums[start]<target){
            return start+1;
        }
        return start;
    }
}
