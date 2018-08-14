Given a collection of intervals, merge all overlapping intervals.

Example 1:

Input: [[1,3],[2,6],[8,10],[15,18]]
Output: [[1,6],[8,10],[15,18]]
Explanation: Since intervals [1,3] and [2,6] overlaps, merge them into [1,6].
Example 2:

Input: [[1,4],[4,5]]
Output: [[1,5]]
Explanation: Intervals [1,4] and [4,5] are considerred overlapping. 

首先需要sort整个list，按照interval的start。之后，go over every emelment in the list, 每个current interval(A）
和要ruturn的list中的最后一个interval（B）比，如果两个不重合，则把current（A）加到return list中的最后一个（A replace B），
如果重合，则改变return list中最后一个interval（B）的end, end为两个interval的较大值。（(A)可能完全在(B)里面重叠）。


/**
 * Definition for an interval.
 * public class Interval {
 *     int start;
 *     int end;
 *     Interval() { start = 0; end = 0; }
 *     Interval(int s, int e) { start = s; end = e; }
 * }
 */
class Solution {
   
    public List<Interval> merge(List<Interval> intervals) {
        // Create comparator
     Comparator<Interval> comparator = new Comparator<Interval>(){
            @Override
            public int compare(final Interval i1, final Interval i2) {
                if (i1.start < i2.start) {
                    return -1;
                }
                else if (i1.start == i2.start) {
                    return 0;
                }
                else {
                    return 1;
                }
            }  
  
        };
        
        List<Interval> re = new ArrayList<Interval>();
        if (intervals == null || intervals.size() == 0) {
            return re; 
        }
        // Sort the list with interval's start 
        Collections.sort(intervals, comparator);
        
        for (Interval cur : intervals) {
             
            // if not overlap, then added the current interval to the end 
            if (re.isEmpty() || re.get(re.size() -1).end < cur.start ){
                re.add(cur);
            }
            else{
                Interval currentLastInterval = re.get(re.size() -1);
                //!!! 注意重叠情况！ 
                // If overlap, use the end of the interval whose end is larger. Since 2 possible cases: [1,5],[2,3] ; [1,5],[2,6]
                re.set(re.size() -1, new Interval(currentLastInterval.start, Math.max(cur.end, currentLastInterval.end)));
            }
        }
        return re;
    }
}
