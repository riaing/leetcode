Given an integer array nums sorted in non-decreasing order, remove the duplicates in-place such that each unique element appears only once. The relative order of the elements should be kept the same.
  
Example 1:

Input: nums = [1,1,2]
Output: 2, nums = [1,2,_]
Explanation: Your function should return k = 2, with the first two elements of nums being 1 and 2 respectively.
It does not matter what you leave beyond the returned k (hence they are underscores).
Example 2:

Input: nums = [0,0,1,1,1,2,2,3,3,4]
Output: 5, nums = [0,1,2,3,4,_,_,_,_,_]
Explanation: Your function should return k = 5, with the first five elements of nums being 0, 1, 2, 3, and 4 respectively.
It does not matter what you leave beyond the returned k (hence they are underscores).
 

Constraints:

0 <= nums.length <= 3 * 104
-100 <= nums[i] <= 100
nums is sorted in non-decreasing order.  
  
  ---------------------------------------------------
  /*
2 pointer 
通过placeToreplaceDup来 maintain 一个non-dup 的 array,
   i 来扫整个 array。
   每当发现 dup 时，把下一个 non-dup 的 element 放到 dup 的位置上
   time O(n)
*/
class Solution {
    public int removeDuplicates(int[] arr) {
        int i = 1;
        int placeToreplaceDup = 1;
        
        while (i < arr.length && placeToreplaceDup < arr.length) {
          //arr[placeToreplaceDup-1]就是 non-dup array 里的最后一个
          if (arr[placeToreplaceDup-1] != arr[i]) {
            //这时说明找到了 next non-dup element，把它移到目前通过placeToreplaceDup来的位置上
             arr[placeToreplaceDup] =  arr[i];
              i++;
             placeToreplaceDup++; 
          }
         //如果是 dup，则移动指针往下找
          else {
              i++;
          }
        }
        return placeToreplaceDup;
        }
}
