You are given an array of intervals, where intervals[i] = [starti, endi] and each starti is unique.

The right interval for an interval i is an interval j such that startj >= endi and startj is minimized. Note that i may equal j.

Return an array of right interval indices for each interval i. If no right interval exists for interval i, then put -1 at index i.

 

Example 1:

Input: intervals = [[1,2]]
Output: [-1]
Explanation: There is only one interval in the collection, so it outputs -1.
Example 2:

Input: intervals = [[3,4],[2,3],[1,2]]
Output: [-1,0,1]
Explanation: There is no right interval for [3,4].
The right interval for [2,3] is [3,4] since start0 = 3 is the smallest start that is >= end1 = 3.
The right interval for [1,2] is [2,3] since start1 = 2 is the smallest start that is >= end2 = 2.
Example 3:

Input: intervals = [[1,4],[2,3],[3,4]]
Output: [-1,2,-1]
Explanation: There is no right interval for [1,4] and [3,4].
The right interval for [2,3] is [3,4] since start2 = 3 is the smallest start that is >= end1 = 3.
 

Constraints:

1 <= intervals.length <= 2 * 104
intervals[i].length == 2
-106 <= starti <= endi <= 106
The start point of each interval is unique.


------------------------------ 2 sort + 2 pointer --------------------------------------------
/*
题关键：
1. 找next interval时要对比start time 最相近的（要sort by start time） 
2. 先处理end time小的 （需要sort by end time）

方法1： Sorting 2 list + 2 pointers. 

sorting: O(nlgn)
2 pointers to iterate through list sorted by end time O(n)

Space: copy list O(n)
*/
class Solution {
    public int[] findRightInterval(int[][] intervals) {
        int[] res = new int[intervals.length];
        // map to store interval and index 
        Map<int[], Integer> indexMap = new HashMap<int[], Integer>();
        for (int i = 0; i < intervals.length; i++) {
            indexMap.put(intervals[i], i);
        }
        
        // 2 array sorted by end and start, ascending 
        int[][] endIntervals = Arrays.copyOf(intervals, intervals.length);
        Arrays.sort(intervals, (a, b) -> a[0] - b[0]); // sort by starting time ascending 
        Arrays.sort(endIntervals, (a, b) -> a[1] - b[1]); // sort by end time ascending. we'll process intervals with smaller ending time 
        
        // 3. 2 pointers, iterate endIntervals to find its next interval
        int startPointer = 0; 
        for (int endPointer = 0; endPointer < endIntervals.length; endPointer++) {
            // move to the interval 
            while(startPointer < intervals.length && intervals[startPointer][0] < endIntervals[endPointer][1]) {
                startPointer++;
            }
            
            int curIntervalIndex = indexMap.get(endIntervals[endPointer]);
            //
            if (startPointer >= intervals.length) {
                res[curIntervalIndex] = -1; //这里可优化成之后的所有interval都为-1
            }
            else {
                res[curIntervalIndex] = indexMap.get(intervals[startPointer]); // 找到后不移start pointer，因为下一个interval可能还是用的当前的start interval
            }
        }
        return res; 
    }
}

----------------------------- sort by starting time + binary search --------------------------------------------
/*
题关键：
1. 找next interval时要对比start time 最相近的（要sort by start time） 
2. 先处理end time小的 （需要sort by end time）

方法2： Sorting by starting + binary search 

sorting: O(nlgn)
binary search on each element: lgn. so n element: O(nlgn)

Space complexity : O(n) res array of size n is used. A hashmap of size O(n)is used.

*/
class Solution {
    public int[] findRightInterval(int[][] intervals) {
        int[] res = new int[intervals.length];
        // map to store interval and index 
        Map<int[], Integer> indexMap = new HashMap<int[], Integer>();
        for (int i = 0; i < intervals.length; i++) {
            indexMap.put(intervals[i], i);
        }
        
        // 2 array sorted by start, ascending 
        Arrays.sort(intervals, (a, b) -> a[0] - b[0]); 
        
        // 3. 对于每个interval，binary search找到一个 whose start time >= its end time 
        for (int i = 0; i < intervals.length; i++) {
            int k = intervals[i][1]; // 拿到end time，从array中找start time >= k
            int nextIntervalSortedIndex = findFirstBiggerOrEqualToK(intervals, k); 
            
            int curIntervalIndex = indexMap.get(intervals[i]);
            if (nextIntervalSortedIndex >= intervals.length) {
                res[curIntervalIndex] = -1; 
            }
            
            else {
                 res[curIntervalIndex] = indexMap.get(intervals[nextIntervalSortedIndex]); 
            }
        }
        return res; 
    }
    
    // find the index of the interval whose start time >= k
    private int findFirstBiggerOrEqualToK(int[][] intervals, int k) {
        int start = 0; 
        int end = intervals.length - 1;
        while (start <= end) {
            int mid = start + (end  - start) / 2;
            if (intervals[mid][0] <= k) {
                start = mid + 1;
            }
            else {
                end = mid - 1;
            }
        }
        if (start > 0 && intervals[start-1][0] == k) {
            return start - 1;
        }
        return start; 
    }
}

------------------------------- 2 heaps 解法 ---------------------------------------------------
A brute force solution could be to take one interval at a time and go through all the other intervals to find the next interval. This algorithm will 
take O(N^2) where N is the total number of intervals. Can we do better than that?

We can utilize the Two Heaps approach. We can push all intervals into two heaps: one heap to sort the intervals on maximum start time (let’s call it 
maxStartHeap) and the other on maximum end time (let’s call it maxEndHeap). We can then iterate through all intervals of the maxEndHeap to find their 
next interval. Our algorithm will have the following steps:

1. Take out the top (having highest end) interval from the maxEndHeap to find its next interval. Let’s call this interval topEnd.
2、 Find an interval in the maxStartHeap with the closest start greater than or equal to the start of topEnd. Since maxStartHeap is sorted by ‘start’ 
of intervals, it is easy to find the interval with the highest ‘start’. Let’s call this interval topStart.
3. Add the index of topStart in the result array as the next interval of topEnd. If we can’t find the next interval, add ‘-1’ in the result array.
4. Put the topStart back in the maxStartHeap, as it could be the next interval of other intervals.
5. Repeat steps 1-4 until we have no intervals left in maxEndHeap.

Time complexity#
The time complexity of our algorithm will be O(NlogN),N is the total number of intervals.

Space complexity#
The space complexity will be O(N) because we will be storing all the intervals in the heaps.


import java.util.*;

class Interval {
  int start = 0;
  int end = 0;

  Interval(int start, int end) {
    this.start = start;
    this.end = end;
  }
}

class NextInterval {
  public static int[] findNextInterval(Interval[] intervals) {
    int n = intervals.length;
    // heap for finding the maximum start
    PriorityQueue<Integer> maxStartHeap = new PriorityQueue<>(n, (i1, i2) -> intervals[i2].start - intervals[i1].start);
    // heap for finding the minimum end
    PriorityQueue<Integer> maxEndHeap = new PriorityQueue<>(n, (i1, i2) -> intervals[i2].end - intervals[i1].end);
    int[] result = new int[n];
    for (int i = 0; i < intervals.length; i++) {
      maxStartHeap.offer(i);
      maxEndHeap.offer(i);
    }

    // go through all the intervals to find each interval's next interval
    for (int i = 0; i < n; i++) {
      int topEnd = maxEndHeap.poll(); // let's find the next interval of the interval which has the highest 'end' 
      result[topEnd] = -1; // defaults to -1
      if (intervals[maxStartHeap.peek()].start >= intervals[topEnd].end) {
        int topStart = maxStartHeap.poll();
        // find the the interval that has the closest 'start'
        while (!maxStartHeap.isEmpty() && intervals[maxStartHeap.peek()].start >= intervals[topEnd].end) {
          topStart = maxStartHeap.poll();
        }
        result[topEnd] = topStart;
        maxStartHeap.add(topStart); // put the interval back as it could be the next interval of other intervals
      }
    }
    return result;
  }

  public static void main(String[] args) {
    Interval[] intervals = new Interval[] { new Interval(2, 3), new Interval(3, 4), new Interval(5, 6) };
    int[] result = NextInterval.findNextInterval(intervals);
    System.out.print("Next interval indices are: ");
    for (int index : result)
      System.out.print(index + " ");
    System.out.println();

    intervals = new Interval[] { new Interval(3, 4), new Interval(1, 5), new Interval(4, 6) };
    result = NextInterval.findNextInterval(intervals);
    System.out.print("Next interval indices are: ");
    for (int index : result)
      System.out.print(index + " ");
  }
}
