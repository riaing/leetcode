Given a set of non-overlapping intervals, insert a new interval into the intervals (merge if necessary).

You may assume that the intervals were initially sorted according to their start times.

Example 1:

Input: intervals = [[1,3],[6,9]], newInterval = [2,5]
Output: [[1,5],[6,9]]
Example 2:

Input: intervals = [[1,2],[3,5],[6,7],[8,10],[12,16]], newInterval = [4,8]
Output: [[1,2],[3,10],[12,16]]
Explanation: Because the new interval [4,8] overlaps with [3,5],[6,7],[8,10].
NOTE: input types have been changed on April 15, 2019. Please reset to default code definition to get new method signature.

-----方法一，把start小余newinterval的放进去，然后merge newinterval与result list中之前的一个，再陆陆续续merge后面的-----------------
这种做法只比较end
class Solution {
    public int[][] insert(int[][] intervals, int[] newInterval) {
        int index = 0;
        int n = intervals.length;
        int s = newInterval[0];
        int e = newInterval[1];
        LinkedList<int[]> output = new LinkedList<int[]>();
        // 1, 把start小余new的放进去
        while (index < n  && intervals[index][0] < s) {
            output.add(intervals[index]);
             index++;
        }
        //2， add new interval 
        if (output.isEmpty() || output.getLast()[1] < s) {
            output.add(newInterval);
        }
        else {
            output.get(index-1)[1] = Math.max(output.getLast()[1], e);
        }
       
        //3， merge 
        while (index < n) {
            int[] cur = intervals[index];
            index++;
            int curStart = cur[0];
            int curEnd = cur[1];
            if (output.getLast()[1] < curStart) {
                output.add(cur);
            }
            else {
                output.getLast()[1] = Math.max(output.getLast()[1], curEnd); 
            }
        }
     
        return output.toArray(new int[output.size()][2]);
        
    }
}

------------方法二：可能是Shift的人更喜欢的解法，感觉也更清楚一些 ---------------------------------------------


class Solution {
    public int[][] insert(int[][] intervals, int[] newInterval) {
        int index = 0;
        int n = intervals.length;

        LinkedList<int[]> output = new LinkedList<int[]>();
        // 将所有小余newInterval的interval先放入list
        while(index < intervals.length && intervals[index][1] < newInterval[0]) { //这里与上一种解法不同的是，把整个小余newinterval的放进
            output.add(intervals[index]);
            index++;
        }
        
        // 找到所有要合并的地方。
        // 注意这里，判断新插的interval的尾是不是大于等于list中当前interval的头，是的话就merge
        while(index < intervals.length && intervals[index][0] <= newInterval[1]) {
            newInterval[0] = Math.min(intervals[index][0], newInterval[0]);
            newInterval[1] = Math.max(intervals[index][1], newInterval[1]);
        
            index++;
        }
       output.add(newInterval);
        
        // 因为题目中给出是non overlapping的list，所以直接加上就好
        while (index < intervals.length) {
            output.add(intervals[index]);
            index++;
        }
        return output.toArray(new int[output.size()][2]);
        
    }
}
------------- 2022.1.25 update ---------------------
    class Solution {
    public int[][] insert(int[][] intervals, int[] newInterval) {
        //1. find the place to insert the interval, will be after the interval i where  interval[i].end < newInterval.start. so interval 0...i is sorted and non-overlap 
        int i = 0;
        List<int[]> result = new ArrayList<int[]>();
        while (i < intervals.length && intervals[i][1] < newInterval[0]) {
            result.add(intervals[i]);
            i++;
        }
        // 2. i is the place to insert the interval. 
        result.add(newInterval);
        merge(result, intervals, i);
        
        //3. put result together 
        int[][] results = new int[result.size()][2];
        for(int n = 0; n < result.size(); n++) {
            results[n] = result.get(n);
        }
        return results;
        
    }
    
    private void merge(List<int[]> result, int[][] intervals, int position) {
        for (int i = position; i < intervals.length; i++) {
            int[] lastOne = result.get(result.size()-1);
            // way 1: 分别判断 new interval 和 interval[i]的 start 的大小
            // if (lastOne[0] <= intervals[i][0]) {
            //     if (lastOne[1] < intervals[i][0]) {
            //         result.add(intervals[i]);
            //     }
            //     else{
            //         lastOne[1] = Math.max(lastOne[1], intervals[i][1]);
            //     }
            // }
            // else {
            //     if (intervals[i][1] < lastOne[0]) {
            //         result.add(result.size()-1, intervals[i]);
            //     }
            //     else {
            //         lastOne[0] = intervals[i][0];
            //         lastOne[1] = Math.max(intervals[i][1], lastOne[1]);
            //     }
            // }

            // way 2: 当按照 start sort 好后，判断overlap 的条件是 newInterval.end > interval[i].start 
            if (intervals[i][0] <= lastOne[1]) { // has overlap 
                int[] tmp = new int[]{Math.min(lastOne[0], intervals[i][0]), Math.max(lastOne[1], intervals[i][1])};
                result.set(result.size()-1, tmp);
                
            }
            else{
                result.add(intervals[i]);
                
            }
        }
        return;
    }
}

------------------ 2022. TreeMap 本题不用，是给 https://leetcode.com/problems/range-module/discuss/2120522/Java-or-TreeMap-or-Beats-85 的code -------
/*
https://leetcode.com/playground/cvs3VGSH 
TreeMap做法

# Add: 
1.找到第一个和最后一个可能overlap的interval [first, last]
eg: 10,20; 21,30. 插入 18，23
- 找最后一个overlap： map.floorKey(23): Returns  reatest one <= the given key, , or null
- 找第一个overlap：map.floorKey(18) 
注意特殊case：首尾插入可能 TreeMap floorKey() return null

2.遍历这段 intervals, 有相交就merge，删除；
general 判断相交： a start <= b end && b start <= a end 

3.map加入merge后的interval

add(mlgn), m: # of overlapped intervals. 最坏 nlgn，到要merge
getRange O(1)
*/


import java.util.*;

class Solution {
    int coverLen = 0; // follow up 
    public int[][] insert(int[][] intervals, int[] newInterval) {
        if (newInterval == null || newInterval.length == 0) {
            return intervals; 
        }
        
        TreeMap<Integer, Integer> map = new TreeMap<Integer, Integer>();
        for (int[] cur : intervals) {
            coverLen += cur[1] - cur[0]; // follow up 
            map.put(cur[0], cur[1]);
        }
        if (map.isEmpty()) { // corner case 
            map.put(newInterval[0], newInterval[1]); 
            coverLen += newInterval[1] - newInterval[0]; // follow up 
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
            coverLen -= map.get(key) - key; // follow up 
            map.remove(key);
        }
        // add new interval 
        map.put(newInterval[0], newInterval[1]);
        coverLen += newInterval[1] - newInterval[0]; // follow up; 
        
       // System.out.println(map);
        System.out.println("coverLen " + coverLen); // follow up
       
       // get result 
        int[][] res = new int[map.size()][2];
        int i = 0;
        for (int k : map.keySet()) {
            res[i][0] = k;
            res[i][1] = map.get(k);
            i++;
        }
        return res; 
    }    
    
    // follow up: 比如insert之后的interval是[1,3], [6,9] -> (9-6)+(3-1) = 5 . 要求O（1）
    private int getTotalCoveredLength() {
        return coverLen; 
    }
}

