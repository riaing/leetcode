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
