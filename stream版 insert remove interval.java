import java.util.*;
/*
https://www.1point3acres.com/bbs/thread-848471-1-1.html
这两题的combine： 
https://leetcode.com/problems/remove-interval/ 
https://leetcode.com/problems/insert-interval/
的简化版： https://leetcode.com/problems/range-module/ 

insert intervals变形，设计一个Intervals class，要求支持addInterval(int from, int to)和getTotalLengh()，需要return所有interval cover的长度
followup是再实现一个removeRange(from, to)api，反正就是现存的range如果和from，to有overlap，那就把overlap的那部分interval删掉（一个interval如果只overlap一半，那就只remove那一半）。


解法： 
1. 按insert那题来做，remove和insert都要新创list。add/remove O(n), getRange O（1）

2. TreeMap {key:left, value:right} 
add/remove( mlgn), m: # of overlapped intervals. 最坏 nlgn，到要merge
getRange O(1)
 
# Add 思路： 重点，floorKey on newInterval的start和end
1.找到第一个和最后一个可能overlap的interval [first, last]
eg: 10,20; 21,30. 插入 18，23
- 找最后一个overlap： map.floorKey(23): Returns  reatest one <= the given key, , or null
- 找第一个overlap：map.floorKey(18) 
注意特殊case：首尾插入可能 TreeMap floorKey() return null

2.遍历这段 intervals, 有相交就merge，删除；
general 判断相交： a start <= b end && b start <= a end 

3.map加入merge后的interval

-------------------------------------------------------------- 
test case 
8,9; 10,12; 16,18; 21,30; 31,35 
add(12,30)
return (10,30) 

*/
interface Intervals {  
    /** 
    * Adds an interval [from, to] into internal structure. 
    */  
    void addInterval(int from, int to);  

    /** 
    * Returns a total length covered by intervals. 
    * If several intervals intersect, intersection should be counted only once. 
    * Example: 
    * 
    * addInterval(3, 6) 
    * addInterval(8, 9) 
    * addInterval(1, 5) 
    * 
    * getTotalCoveredLength() -> 6 
    * i.e. [1,5] and [3,6] intersect and give a total covered interval [1,6] 
    * [1,6] and [8,9] don't intersect so total covered length is a sum for both intervals, that is 6. 
    * 
    *                   _________ 
    *                                               ___ 
    *     ____________ 
    * 
    * 0  1    2    3    4    5   6   7    8    9    10 
    * 
    */  
    int getTotalCoveredLength();   // 比如insert之后的interval是[1,3], [6,9], 那return的coverlength是啥呢？是9-1=8？还是(9-6)+(3-1) = 5呢？答：5
}  

// -------------------- coding part --------------------------------
class MyIntervals implements Intervals {  
    int coverLen = 0;
    TreeMap<Integer, Integer> map = new TreeMap<Integer, Integer>(); // key:start, val:end
    
    @Override  
    public void addInterval(int from, int to) {
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
        
        System.out.println(map);
    }  

    @Override  
    public int getTotalCoveredLength() {  
        System.out.println("len " + coverLen);
        return coverLen; 
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
                // // 不简化版：注意把一个切割成两段情况
                // if (curStart <= newInterval[0]) {
                //     toAdd.add(new int[]{curStart, newInterval[0]}); 
                //     toAdd.add(new int[]{newInterval[1], curEnd}); // cur interval 被截成两段
                // }
                // else {
                //     toAdd.add(new int[]{newInterval[1], curEnd}); 
                // }
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
        System.out.println("in remove: " + map); 
    }
}  

// ----------------test part --------------------------------------------
public class Main {
    public static void main(String[] args) {
        MyIntervals test = new MyIntervals();
        // test.addInterval(1,2);
        // test.addInterval(3,5);
        // test.getTotalCoveredLength();
        // test.addInterval(6,7);
        //  test.getTotalCoveredLength();
        // test.addInterval(8,10);
        // test.addInterval(12,16);
        // test.addInterval(4,8); // [[1,2],[3,10],[12,16]] 
        //  test.getTotalCoveredLength();
        
        // test remove 
        test.addInterval(1,4);
        test.addInterval(10,15);
        test.getTotalCoveredLength();
        // 一下test case得independently 运行
        // test.removeRange(5,6); // invalid remove,{1=4, 10=15}
        // test.removeRange(4,10); // 相接 {1=4, 10=15}
        // test.removeRange(3,11); // touch 两段 {1=3, 11=15} 
        // test.removeRange(2,5); // touch一段一部分 {1=2, 10=15}
        // test.removeRange(0, 2); // touch一段一部分 {2=4, 10=15}
        test.removeRange(2, 3); // 分割一段 {1=2, 3=4. 10=15}
        test.getTotalCoveredLength();
        
        
        
    }

}
