A frog is crossing a river. The river is divided into x units and at each unit there may or may not exist a stone. The frog can jump on a stone, but it must not jump into the water.

Given a list of stones' positions (in units) in sorted ascending order, determine if the frog is able to cross the river by landing on the last stone. Initially, the frog is on the first stone and assume the first jump must be 1 unit.

If the frog's last jump was k units, then its next jump must be either k - 1, k, or k + 1 units. Note that the frog can only jump in the forward direction.

Note:

The number of stones is ≥ 2 and is < 1,100.
Each stone's position will be a non-negative integer < 231.
The first stone's position is always 0.
Example 1:

[0,1,3,5,6,8,12,17]

There are a total of 8 stones.
The first stone at the 0th unit, second stone at the 1st unit,
third stone at the 3rd unit, and so on...
The last stone at the 17th unit.

Return true. The frog can jump to the last stone by jumping 
1 unit to the 2nd stone, then 2 units to the 3rd stone, then 
2 units to the 4th stone, then 3 units to the 6th stone, 
4 units to the 7th stone, and 5 units to the 8th stone.
Example 2:

[0,1,2,3,4,8,9,11]

Return false. There is no way to jump to the last stone as 
the gap between the 5th and 6th stone is too large.

    
 ------------ 3.28.2021 DP ----------------------------------------------------------------------------------- 
    /*
dp[i][j]: 走j步能不能到达第i个石头
dp[i][j] = dp[i-k][j-1] || dp[i-k][j] || dp[i-k][j+1], 1<=k<=i; j = stones[i] - stones[i-k]. 从前面某个石头，走j/j-1/j+1步能不能到
dp[0][0] = true;
return： dp[最后石头][任意j] = true

*/
class Solution {
    public boolean canCross(int[] stones) {
        /*第一块石头最多1步，第二块石头最多2步，第三块石头最多三步。所以第n个石头最多n步。取个min。*/
        int maxUnit = stones[stones.length-1] -stones[0]; // 最多多少步到最后
        int size = stones.length; 
        int maxStep = Math.min(maxUnit, size);

        boolean[][] dp = new boolean[stones.length][maxStep+2];//如果首尾差17步，那么最后可以走18步。
        
        
        dp[0][0] = true;
        for (int i = 1; i < stones.length; i++) {
            for (int k = 1; k <= i; k++) {
                int unit = stones[i] - stones[i-k]; //从k到i需要多少步
                // 优化：ex1中的最后一个石头(17)，最多走8步到，所以当unit = 17时不可能
                if (unit > i) {
                    break; // 到达第i石头做多可以跳i步。这里break而不是continue，因为如果i个和第i-1个之间距离已经过大得不到了，那么i-2肯定到达不了i了。因为i-2时最多可以走i-2步。
                }
                if (dp[i-k][unit] || (unit-1>=0 && dp[i-k][unit-1]) || dp[i-k][unit+1]) {
                    dp[i][unit] = true;
                }
            }
        }
        
        for (int j = 0; j < maxStep+2; j++) {
            if (dp[stones.length-1][j]) {
                return true;
            }
        }
        return false;
    }
}

--------------------Memorization ----------------------------------------------------------------------------------- 
class Solution {
    public boolean canCross(int[] stones) {
        Map<String, Boolean> map = new HashMap<String, Boolean>();
        return helper(stones, 0, 0, map);
    }
    
    private boolean helper(int[] stones, int index, int lastUnit, Map<String, Boolean> map) {
        String key = index+","+lastUnit;
        if (map.containsKey(key)) {
            return map.get(index+","+lastUnit);
        }
        
        if (index == stones.length-1) {
            map.put(key, true);
            return true;
        }
        for (int i = lastUnit-1; i <= lastUnit+1; i++) { 
            if (i <= 0) {
                continue;
            }
            int nextIndex = findNextIndex(stones, index, i);
            // 如果会掉在水里
            if (nextIndex == -1) {
                continue;
            }
            if (helper(stones, nextIndex, i, map)) {
                map.put(key, true);
                return true;
            }
        }
         map.put(key, false);
        return false;
    }
    
   
    private int findNextIndex(int[] stones, int curIndex, int unit) {
        // linear search, O(n)
        // for (int i = curIndex+1; i <= Math.min(curIndex+unit, stones.length-1); i++) {
        //     if (stones[i] == stones[curIndex]+ unit) {
        //         return i;
        //     }
        // }
        // return -1;
        
         // binary search 法 O(lgN)
        int low = curIndex+1;
        int high = Math.min(curIndex+unit, stones.length-1);
        while (low < high) {
            int mid = low + (high - low) / 2;
            // mid永远在包含条件那边
            if (stones[mid] >= stones[curIndex]+ unit) {
                high = mid;
            }
            else {
                low = mid + 1;
            }
        }
        return stones[low] == stones[curIndex]+ unit ? low : -1;
    }
}
