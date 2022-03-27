Given an array of numbers which is sorted in ascending order and is rotated ‘k’ times around a pivot, find ‘k’.

You can assume that the array does not have any duplicates.

Example 1:

Input: [10, 15, 1, 3, 8]
Output: 2
Explanation: The array has been rotated 2 times.
    1   
    3   
    8   
    10   
    15   
 Original array:  
 Array after 2 rotations:  
    10   
    15   
    1   
    3   
    8   
Example 2:

Input: [4, 5, 7, 9, 10, -1, 2]
Output: 5
Explanation: The array has been rotated 5 times.
 Original array:  
    -1   
    2   
    4   
    5   
    7   
    9   
    10   
 Array after 5 rotations:        
    4   
    5   
    7   
    9   
    10   
    -1   
    2   

Example 3:

Input: [1, 3, 8, 10]
Output: 0
Explanation: The array has been not been rotated.
  
  
  ----------- binary search求min，返回min的index ------------------------
  
  class RotationCountOfRotatedArray {
 // find min and return its index 
  public static int countRotations(int[] nums) {
        int start = 0;
        int end = nums.length - 1; 
        while (start < end) {
            int mid = start + (end - start) / 2;
            // System.out.println(mid);
            if (nums[mid] > nums[end]) {
                start = mid + 1;
            }
            // if (nums[start] >= nums[mid] && nums[mid] < nums[end]) {
            else{
                end = mid;
            }
        }
        return start;
  }

  public static void main(String[] args) {
    System.out.println(RotationCountOfRotatedArray.countRotations(new int[] { 10, 15, 1, 3, 8 }));
    System.out.println(RotationCountOfRotatedArray.countRotations(new int[] { 4, 5, 7, 9, 10, -1, 2 }));
    System.out.println(RotationCountOfRotatedArray.countRotations(new int[] { 1, 3, 8, 10 }));
  }
}
