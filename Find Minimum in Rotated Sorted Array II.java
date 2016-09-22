 //  这道题目在面试中不会让写完整的程序
        //  只需要知道最坏情况下 [1,1,1....,1] 里有一个0
        //  这种情况使得时间复杂度必须是 O(n)
        //  因此写一个for循环就好了。
        //  如果你觉得，不是每个情况都是最坏情况，你想用二分法解决不是最坏情况的情况，那你就写一个二分吧。
        //  反正面试考的不是你在这个题上会不会用二分法。这个题的考点是你想不想得到最坏情况。
        
当A[mid] = A[end]时，无法判断min究竟在左边还是右边。
3 1 2 3 3 3 3 
3 3 3 3 1 2 3
但可以肯定的是可以排除A[end]：因为即使min = A[end]，由于A[end] = A[mid]，排除A[end]并没有让min丢失。所以增加的条件是：
A[mid] = A[end]：搜索A[start : end-1]

public class Solution {
    public int findMin(int[] nums) {
        if(nums == null || nums.length ==0){
            return -1;
        }
        int start = 0;
        int end = nums.length -1;
        
        
        while(start < end){
            int mid = start + (end -start)/2;
            if(nums[mid]< nums[end]){
                end = mid; 
            }
            else if(nums[mid]> nums[end]){
                start = mid+1; 
            }
            else if(nums[mid] == nums[end]){
                end= end-1;
            }
        }
        return nums[start]; 
        
    }
}
