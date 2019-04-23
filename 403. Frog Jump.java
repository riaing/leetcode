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
