Given an array nums containing n + 1 integers where each integer is between 1 and n (inclusive), prove that at least one duplicate number must exist. Assume that there is only one duplicate number, find the duplicate one.

Example 1:

Input: [1,3,4,2,2]
Output: 2
Example 2:

Input: [3,1,3,4,2]
Output: 3
Note:

You must not modify the array (assume the array is read only).
You must use only constant, O(1) extra space.
Your runtime complexity should be less than O(n2).
There is only one duplicate number in the array, but it could be repeated more than once.

-----------------------bineray search ------------------------------------------------------------------------------------
/**
我们在区间[1, n]中搜索，首先求出中点mid，然后遍历整个数组，统计所有小于等于mid的数的个数，如果个数小于等于mid，则说明重复值在[mid+1, n]之间，
反之，重复值应在[1, mid]之间，然后依次类推，直到搜索完成，此时的low就是我们要求的重复值.
Time nlogx -> x= max - min;
*/
class Solution {
    public int findDuplicate(int[] nums) {
        int max = nums.length -1;
        int min = 1; 
        while (min < max) {
            int mid = min + (max - min) / 2;
            if (findLessOrEqualToK(nums, mid) <= mid) {
                min = mid + 1;
            }
            else {
                max = mid;
            }
        }
        return min;  
    }
    
    private int findLessOrEqualToK(int[] nums, int k) {
        int count  = 0; 
        for (int num : nums) {
            if (num <= k) {
                count++;
            }
        }
        return count;
    }
}

----------------- 2-22.1.26 Cyclis sort ----------------------------------
    /*
运用 cyclic sort。还完后repeate num可能在两个地方：1）正确的 index 上 2） 在最后一位
O（n）， O（1）space 
*/
class Solution {
    public int findDuplicate(int[] nums) {
        int i = 0;
        while (i < nums.length) {
            int curNum = nums[i];
            if (curNum != i+1) { // 说明需要交换
                if (nums[curNum-1] == curNum) {  // 1） repeated num 在正确的 index 上 
                    return curNum;
                }
                else{
                    //swap;
                    int tmp = nums[curNum-1];
                    nums[curNum-1] = curNum;
                    nums[i] = tmp;
                    
                }
            }
            else {
                i++;
            }
        }
        return nums[nums.length -1]; // 2） 换完后最后一位就是 repeated num
        
    }
}

----------- 更巧的方法 (nums[i] != nums[nums[i] - 1]) -----------------
    // 这是 find all duplicates 的解但思路一样
    class Solution {
    public List<Integer> findDuplicates(int[] nums) {
        int i = 0;
    while (i < nums.length) {
      if (nums[i] != nums[nums[i] - 1]) // 如果这个数不等于它 expect 的值
        swap(nums, i, nums[i] - 1);
      else
        i++;
    }

    List<Integer> duplicateNumbers = new ArrayList<>();
    for (i = 0; i < nums.length; i++) {
      if (nums[i] != i + 1)
        duplicateNumbers.add(nums[i]);
    }

    return duplicateNumbers;
    }
    
     private void swap(int[] arr, int i, int j) {
    int temp = arr[i];
    arr[i] = arr[j];
    arr[j] = temp;
  }
}
