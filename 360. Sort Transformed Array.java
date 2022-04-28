Given a sorted integer array nums and three integers a, b and c, apply a quadratic function of the form f(x) = ax2 + bx + c to each element nums[i] in the array, and return the array in a sorted order.

 

Example 1:

Input: nums = [-4,-2,2,4], a = 1, b = 3, c = 5
Output: [3,9,15,33]
Example 2:

Input: nums = [-4,-2,2,4], a = -1, b = 3, c = 5
Output: [-23,-5,1,7]
 

Constraints:

1 <= nums.length <= 200
-100 <= nums[i], a, b, c <= 100
nums is sorted in ascending order.
 

Follow up: Could you solve it in O(n) time?


------------------- O（n）
/*
计算题。如果a是正，抛物线向上（首尾大，中间小），否则抛物线向下（首尾小，中间大）
每次对比首尾，更具抛物线向上/下 决定 1：首尾谁先进result array 2.result array的放置顺序 （从小到大or大到小）

*/
class Solution {
    public int[] sortTransformedArray(int[] nums, int a, int b, int c) {
        boolean up = a >=0 ? true : false; 
        int left = 0;
        int right = nums.length - 1; 
        int curP = a >= 0 ? nums.length - 1 : 0; 
        int[] res = new int[nums.length];
        while (left < right) {
            int leftVal = cal(nums[left], a, b, c);
            int rightVal = cal(nums[right], a, b, c);
            if (up) {
                if (leftVal > rightVal) {
                    res[curP] = leftVal;
                    left++;
                }
                else {
                    res[curP] = rightVal;
                    right--;
                }
                curP--;
            }
            else {
                if (leftVal < rightVal) {
                    res[curP] = leftVal;
                    left++;
                }
                else {
                    res[curP] = rightVal;
                    right--;
                }
                curP++;      
            }
        }
        // one num left if nums is odd number
        if (left == right) {
            res[curP] = cal(nums[left], a, b, c);
        }
        
        return res; 
    }
    
    private int cal(int num, int a, int b, int c) {
        return num * num * a + num * b + c;
    }
}
