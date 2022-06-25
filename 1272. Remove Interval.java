A set of real numbers can be represented as the union of several disjoint intervals, where each interval is in the form [a, b). A real number x is in the set if one of its intervals [a, b) contains x (i.e. a <= x < b).

You are given a sorted list of disjoint intervals intervals representing a set of real numbers as described above, where intervals[i] = [ai, bi] represents the interval [ai, bi). You are also given another interval toBeRemoved.

Return the set of real numbers with the interval toBeRemoved removed from intervals. In other words, return the set of real numbers such that every x in the set is in intervals but not in toBeRemoved. Your answer should be a sorted list of disjoint intervals as described above.

 

Example 1:


Input: intervals = [[0,2],[3,4],[5,7]], toBeRemoved = [1,6]
Output: [[0,1],[6,7]]
Example 2:


Input: intervals = [[0,5]], toBeRemoved = [2,3]
Output: [[0,2],[3,5]]
Example 3:

Input: intervals = [[-5,-4],[-3,-2],[1,2],[3,5],[8,9]], toBeRemoved = [-1,4]
Output: [[-5,-4],[-3,-2],[4,5],[8,9]]
 

Constraints:

1 <= intervals.length <= 104
-109 <= ai < bi <= 109

----------- TreeMap. 是用于 stream 增删的case -----------------
https://leetcode.com/playground/cvs3VGSH 

import java.util.*;
// 正常解法O（N） one pass
// 有overlap时注意判断拆开的interval取值： (curStart, remove start), (remove end, cur end)
class Solution {
    TreeMap<Integer, Integer> map = new TreeMap<Integer, Integer>(); // key:start, val:end
    
    public List<List<Integer>> removeInterval(int[][] intervals, int[] toBeRemoved) {
        // 建map
         List<List<Integer>> res = new ArrayList<>();
         if (toBeRemoved == null || toBeRemoved.length == 0) {
            return res; 
        }
        for (int[] cur : intervals) {
            map.put(cur[0], cur[1]);
        }
        if (map.isEmpty()) { // corner case 
            return res; 
        }
        // get result 
        removeRange(toBeRemoved[0], toBeRemoved[1]);
        for (int k : map.keySet()) {
            res.add(Arrays.asList(k, map.get(k)));
        }
        return res; 
    }
    
     public void removeRange(int from, int to) {
        int[] newInterval = new int[]{from, to}; 
        // 1. 找到可能overlap的interval range
        int left = map.floorKey(newInterval[0]) == null ? map.firstKey() : map.floorKey(newInterval[0]);
        int right = map.floorKey(newInterval[1]) == null ? map.lastKey() : map.floorKey(newInterval[1]);; 
        SortedMap<Integer, Integer> mapRange = map.subMap(left, right+1); // 拿到那一段map的view
 
        List<Integer> keyToRemove = new ArrayList<>();
        List<int[]> toAdd = new ArrayList<>();  
        for (Integer key : mapRange.keySet()) {  // O(m), m -> # overlapped intervals 
            // 查相交
            int curStart = key;
            int curEnd = map.get(key);
            if (newInterval[0] <= curEnd && curStart <= newInterval[1]) { // 删除加入都是 O（lgN)
                keyToRemove.add(key);
                // 下面的if else 可以简化成这，因为反正加时要validate
                 toAdd.add(new int[]{curStart, newInterval[0]}); 
                 toAdd.add(new int[]{newInterval[1], curEnd}); // cur interval 被截成两段
            }
        }
        // remove 
        for (int key : keyToRemove) {
            map.remove(key);
        }
        // add 截开的interval
        for (int[] toadd : toAdd) {
            if (toadd[0] < toadd[1]) {
                map.put(toadd[0], toadd[1]);  
            }
        }
        System.out.println("in remove: " + map); 
    }
}
