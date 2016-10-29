greedy : 
注意题目中A[i]表示的是在位置i，“最大”的跳跃距离，而并不是指在位置i只能跳A[i]的距离。所以当跳到位置i后，能达到的最大的距离至少是i+A[i]。用greedy来解，记录一个当前能达到的最远距离maxIndex：

1. 能跳到位置i的条件：i<=maxIndex。
2. 一旦跳到i，则maxIndex = max(maxIndex, i+A[i])。
3. 能跳到最后一个位置n-1的条件是：maxIndex >= n-1


public class Solution {
    //corner case: [0]
    public boolean canJump(int[] nums) {
        if(nums == null || nums.length == 0 ){
            return true; 
        }
        int max = 0;
        for (int i = 0; i <nums.length; i ++){ //循环走过每一格，找到最大的
            if( max < i || max >= nums.length -1){
                break; 
            }
            max = Math.max(max, i+nums[i]);
        }
        return (max >= nums.length -1)? true : false; 
    }
}
