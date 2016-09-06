Given a sorted array of integers, find the starting and ending position of a given target value.

Your algorithm's runtime complexity must be in the order of O(log n).

If the target is not found in the array, return [-1, -1].

For example,
Given [5, 7, 7, 8, 8, 10] and target value 8,
return [3, 4].

思路：
既然要求O(log n)那必然又是binary search变种。要找到target在数组中的左右边界，必然先得要在数组中找到一个target。一种条件反射的思路是binary search找到target，即A[mid] = target，然后从mid开始向左右扫描来发现左右边界。但显然这种算法不是O(log n)的，比如当所有元素都一样，并且等于target时，算法退化为O(n)。
所以这里当A[mid] = target时，我们必须继续用二分法来查找左右边界。以下面数组为例：
4 5 8 8 8 8 9 9 10
target = A[mid=4] = 8
此时要在A[0:3]中寻找left，在A[5:8]中寻找right。
搜索left：
4 5 8 8
target != A[mid]，搜索 A[mid+1 : end]。
8 8 
target = A[mid]，搜索A[start: mid-1]
start > end，left = start
搜索right：
8 9 9 10
target != A[mid]，搜索A[start : mid-1]。
8
target = A[mid]，搜索A[mid+1 : end]。
start > end，right = end

总结的规律就是：
二分查找时特殊处理target = A[mid]的情况
对搜索left：如果target = A[mid]则继续向左找，否则向右找。直到搜索结束，left = start
对搜索right：如果target = A[mid]则继续向右找，否则向左找。直到搜索结束，right = end
最后判断如果A[left], A[right] != target，则表明target不存在于数组中, left = right = -1

public class Solution {
    public int[] searchRange(int[] nums, int target) {
        int[] none;
        int start = 0;
        int end = nums.length-1;
        while(start < end){
            int mid = start + (end -start )/2; 
            if(nums[mid] == target){
                start = searchLeft(nums, start, mid, target);
                end = searchRight(nums, mid, end, target); 
                break;
            }
            else if(nums[mid] < target){
                start =mid +1;
            }
            else{
                end = mid-1;
            }
        }

        if(nums[start] == target && nums[end] == target){
            none = new int[]{start, end};
        }
        else{
            none = new int[]{-1, -1}; 
        }
         return none; 
        
    }
    private int searchLeft(int[] nums, int start, int end,int target){
        while(start<= end){ //chqnge to = then right! 
            int mid = start+ (end -start)/2;
            if(nums[mid] != target){
                start =mid +1;
            }
            else{
                end = mid -1;
            }
        }
        return start; 
    }
    
    private int searchRight(int[] nums, int start, int end, int target){
        while(start<= end){ //change to = then right ! 
            int mid = start+ (end+1 -start)/2;
            if(nums[mid] != target){
                end = mid  - 1;
            }
            else{
                start = mid +1;
            }
        }
        return end; 
    }
    
    
}
