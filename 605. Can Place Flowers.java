You have a long flowerbed in which some of the plots are planted, and some are not. However, flowers cannot be planted in adjacent plots.

Given an integer array flowerbed containing 0's and 1's, where 0 means empty and 1 means not empty, and an integer n, return if n new flowers can be planted in the flowerbed without violating the no-adjacent-flowers rule.

 

Example 1:

Input: flowerbed = [1,0,0,0,1], n = 1
Output: true
Example 2:

Input: flowerbed = [1,0,0,0,1], n = 2
Output: false
 

Constraints:

1 <= flowerbed.length <= 2 * 104
flowerbed[i] is 0 or 1.
There are no two adjacent flowers in flowerbed.
0 <= n <= flowerbed.length
----------------------------------------------------------------------------
/*解法：每个查其前后是否为0，尽量多种。注意首尾的特殊处理*/
class Solution {
    // 直白
    public boolean canPlaceFlowers(int[] flowerbed, int n) {
        if (flowerbed.length == 1 && flowerbed[0] == 0) {
            return true; 
        }
        int canPlanCnt = 0;
      for (int i = 0; i < flowerbed.length; i++) {
          if (flowerbed[i] == 0) {
              // at begining 
              if (i == 0 && i+1 < flowerbed.length && flowerbed[i+1] == 0) {
                  canPlanCnt++;
                  i++;
              }
              // at the end 
              else if (i == flowerbed.length-1 && i-1 >= 0 && flowerbed[i-1] == 0) {
                  canPlanCnt++;
                  i++;
              }
             // 在中间，查前后
              else if (i-1 >= 0 && i+1 < flowerbed.length && flowerbed[i-1] == 0 && flowerbed[i+1] == 0) {
                  canPlanCnt++;
                  i++;
              }
              if (canPlanCnt == n) {
                  return true;
              }
          }
      }
        return canPlanCnt >= n; 
    }
    
    // 更精炼的写法
    public boolean canPlaceFlowers(int[] flowerbed, int n) {
        int count = 0;
        for (int i = 0; i < flowerbed.length; i++) {
            // Check if the current plot is empty.
            if (flowerbed[i] == 0) {
                // Check if the left and right plots are empty.
                boolean emptyLeftPlot = (i == 0) || (flowerbed[i - 1] == 0);
                boolean emptyRightPlot = (i == flowerbed.length - 1) || (flowerbed[i + 1] == 0);
                
                // If both plots are empty, we can plant a flower here.
                if (emptyLeftPlot && emptyRightPlot) {
                    flowerbed[i] = 1;
                    count++;
                    if (count >= n) {
                        return true;
                    }
                }
            }
        }
        return count >= n;
    }
}

 
