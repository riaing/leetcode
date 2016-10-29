找到当前能覆盖的最大值（coverDistance），当i 超过这个值时，说明step ++， 在i没超过 coverDistance的循环中，每次update能到的最远距离。
当i超过 coverDistance时，再把coverDistance update到之前步骤中找到的能到的最大距离。 
http://www.cnblogs.com/lichen782/p/leetcode_Jump_Game_II.html


public class Solution {
    public int jump(int[] nums) {
        if(nums == null || nums.length ==0 ){
            return -1 ; 
        }
        int step = 0; 
        int max = 0 ;
        int coverDistance = 0; 
        for( int i = 0; i <nums.length; i ++){
     
            if(i  > coverDistance ){
                step ++; 
                coverDistance = max; 
            }
            max = Math.max(max, i + nums[i]);
        }
   

            return step;    
        
        
    }
}
