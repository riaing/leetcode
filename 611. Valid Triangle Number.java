Given an integer array nums, return the number of triplets chosen from the array that can make triangles if we take them as side lengths of a triangle.

 

Example 1:

Input: nums = [2,2,3,4]
Output: 3
Explanation: Valid combinations are: 
2,3,4 (using the first 2)
2,3,4 (using the second 2)
2,2,3
Example 2:

Input: nums = [4,2,3,4]
Output: 4
 

Constraints:

1 <= nums.length <= 1000
0 <= nums[i] <= 1000

-------------- bs ----------------
/*
1、 binary search
根据公式 a+b > c, a+c > b, b+c >a, 可以先sort array，然后BS找到最远的那条边C < a+b、 因为array是sort的，所以C和b+1之间的值都valid
Time: O(n^2lgn  + nlgn)

2. 更巧妙，每次增加j后，其实找right limit不用从j+1开始，而是从上次找到的right limit开始即可。因为a+b>c中，随着b的增加，c必定也增加。所以可以在i层循环给定初始值k
Time：O（n^2 + nlgn） -> 每次找right limit都是递增，都不用lgn
*/
class Solution {
    public int triangleNumber(int[] nums) {
        Arrays.sort(nums);
        int res = 0;
        for (int i = 0; i < nums.length - 2; i++) {
            
            for (int j = i+1; j < nums.length - 1 && nums[i] != 0; j++) { // 可以跳过一条边是0的情况
                int largestValidIndex = firstElementSmallerThenK(nums, nums[i] + nums[j], j+1);

                if (largestValidIndex < nums.length && largestValidIndex - j > 0) {
                    res += (largestValidIndex - j); 
                }
            }
        }

        return res; 
    }
    
    
    private int firstElementSmallerThenK(int[] nums, int target, int start) {
        int end = nums.length - 1;
        while (start <= end) {
            int mid = start + (end - start) / 2;
            if (nums[mid] < target) {
                start = mid + 1;
            }
            if (nums[mid] >= target) {
                end = mid - 1;
            }
        }
        return end;
    }
}

--------------- 不用bs。直接linear scan加递增 ----------------
/*
1、 binary search
根据公式 a+b > c, a+c > b, b+c >a, 可以先sort array，然后BS找到最远的那条边C < a+b、 因为array是sort的，所以C和b+1之间的值都valid
Time: O(n^2lgn  + nlgn)

2. 更巧妙，每次增加j后，其实找right limit不用从j+1开始，而是从上次找到的right limit开始即可。因为a+b>c中，随着b的增加，c必定也增加。所以可以在i层循环给定初始值k
Time：O（n^2 + nlgn） -> 每次找right limit都是递增，都不用lgn
*/
class Solution {
    public int triangleNumber(int[] nums) {
        Arrays.sort(nums);
        int res = 0;
        for (int i = 0; i < nums.length - 2; i++) {
            int k = i + 2; //第三条边的index
            for (int j = i+1; j < nums.length - 1 && nums[i] != 0; j++) {
                while (k < nums.length && nums[i] + nums[j] > nums[k]) {
                    k++;
                }
                //这时候的k已经不valid了
                res += (k -1 - j); 
            }
        }

        return res; 
    }
    
    
}
