stream的add remove query interval。 用list每次O（n）扫太慢，这里用treeMap来快速找到要改的range。
Linkedin 简化版： https://leetcode.com/playground/cvs3VGSH 


--------题 
A Range Module is a module that tracks ranges of numbers. Design a data structure to track the ranges represented as half-open intervals and query about them.

A half-open interval [left, right) denotes all the real numbers x where left <= x < right.

Implement the RangeModule class:

RangeModule() Initializes the object of the data structure.
void addRange(int left, int right) Adds the half-open interval [left, right), tracking every real number in that interval. Adding an interval that partially overlaps with currently tracked numbers should add any numbers in the interval [left, right) that are not already tracked.
boolean queryRange(int left, int right) Returns true if every real number in the interval [left, right) is currently being tracked, and false otherwise.
void removeRange(int left, int right) Stops tracking every real number currently being tracked in the half-open interval [left, right).
 

Example 1:

Input
["RangeModule", "addRange", "removeRange", "queryRange", "queryRange", "queryRange"]
[[], [10, 20], [14, 16], [10, 14], [13, 15], [16, 17]]
Output
[null, null, null, true, false, true]

Explanation
RangeModule rangeModule = new RangeModule();
rangeModule.addRange(10, 20);
rangeModule.removeRange(14, 16);
rangeModule.queryRange(10, 14); // return True,(Every number in [10, 14) is being tracked)
rangeModule.queryRange(13, 15); // return False,(Numbers like 14, 14.03, 14.17 in [13, 15) are not being tracked)
rangeModule.queryRange(16, 17); // return True, (The number 16 in [16, 17) is still being tracked, despite the remove operation)
 

Constraints:

1 <= left < right <= 109
At most 104 calls will be made to addRange, queryRange, and removeRange.

------------------ TreeMap -------------

import java.util.*; 

/*
stream 版。stream的add remove query interval。 用list每次O（n）扫太慢，
Time:
add/remove: O(n) - 就是基础题，每次要重建个list
query:O(lgN) bs找到第一个重叠的


这里用treeMap来快速找到要改的range。 
Time: 
m - overlap intervals. N - total intervals in map 
add/remove: O(mlgN) 
query: O(lgN)

重点，floorKey on newInterval的start和end
 
 # Add 思路： 
1.找到第一个和最后一个可能overlap的interval [first, last]
eg: 10,20; 21,30. 插入 18，23
- 找最后一个overlap： map.floorKey(23): Returns  reatest one <= the given key, , or null
- 找第一个overlap：map.floorKey(18) 
注意特殊case：首尾插入可能 TreeMap floorKey() return null

2.遍历这段 intervals, 有相交就merge，删除；
general 判断相交： a start <= b end && b start <= a end 

3.map加入merge后的interval
*/
class RangeModule {
    TreeMap<Integer, Integer> map = new TreeMap<Integer, Integer>(); // key:start, val:end
    int coverLen = 0; // Q2: 找interval covered len
    public RangeModule() {
        
    }
    
     // 找到第一个overlap的，看是不是在里面
    public boolean queryRange(int left, int right) {
        int firstOverlapStart = map.floorKey(left) == null ? Integer.MAX_VALUE  : map.floorKey(left); 
        if (left >= firstOverlapStart && right <= map.get(firstOverlapStart)) {
            return true;
        }
        return false; 
    }
    
 
    public void addRange(int from, int to) {
        int[] newInterval = new int[]{from, to}; 
        if (map.isEmpty()) { // corner case 
            map.put(newInterval[0], newInterval[1]); 
            coverLen += newInterval[1] - newInterval[0]; // Q2
        }
        
        // 1. 找到可能overlap的interval range
        int left = map.floorKey(newInterval[0]) == null ? map.firstKey() : map.floorKey(newInterval[0]);
        int right = map.floorKey(newInterval[1]) == null ? map.lastKey() : map.floorKey(newInterval[1]);; 
        SortedMap<Integer, Integer> mapRange = map.subMap(left, right+1); // 拿到那一段map的view
 
        List<Integer> keyToRemove = new ArrayList<>();
        for (Integer key : mapRange.keySet()) {  // O(m), m -> # overlapped intervals 
            // 查相交
            int curStart = key;
            int curEnd = map.get(key);
            if (newInterval[0] <= curEnd && curStart <= newInterval[1]) { // 删除加入都是 O（lgN)
                keyToRemove.add(key);
                newInterval[0] = Math.min(newInterval[0], curStart);
                newInterval[1] = Math.max(newInterval[1], curEnd);
            }
        }
        // remove 
        for (int key : keyToRemove) {
            coverLen -= map.get(key) - key; // Q2
            map.remove(key);
        }
        // add new interval 
        map.put(newInterval[0], newInterval[1]);
        coverLen += newInterval[1] - newInterval[0]; // Q2
        
        // System.out.println(map);
    }  
    
    // 有overlap时注意判断拆开的interval取值： (curStart, remove start), (remove end, cur end) 
    public void removeRange(int from, int to) {
        if (map.isEmpty() || from >= to) {
            return; 
        }
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
            coverLen -= map.get(key) - key; // Q2
            map.remove(key);
        }
        // add 截开的interval
        for (int[] toadd : toAdd) {
            if (toadd[0] < toadd[1]) {
                map.put(toadd[0], toadd[1]);  
                coverLen += toadd[1] - toadd[0]; // Q2
            }
        }
        // System.out.println("in remove: " + map); 
    }
}

/**
 * Your RangeModule object will be instantiated and called as such:
 * RangeModule obj = new RangeModule();
 * obj.addRange(left,right);
 * boolean param_2 = obj.queryRange(left,right);
 * obj.removeRange(left,right);
 */
