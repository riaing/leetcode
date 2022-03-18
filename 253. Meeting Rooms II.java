一些变种题： https://github.com/tongzhang1994/Facebook-Interview-Coding/blob/master/253.%20Meeting%20Rooms%20II.java 
Given an array of meeting time intervals consisting of start and end times [[s1,e1],[s2,e2],...] (si < ei), find the minimum number of conference rooms required.

Example 1:

Input: [[0, 30],[5, 10],[15, 20]]
Output: 2
Example 2:

Input: [[7,10],[2,4]]
Output: 1 
    
-------------------- 2022.3.17 Heap ----------------------------------------------------
    
    Let’s take the above-mentioned example (4) and try to follow our Merge Intervals approach:

Meetings: [[4,5], [2,3], [2,4], [3,5]]

Step 1: Sorting these meetings on their start time will give us: [[2,3], [2,4], [3,5], [4,5]]

Step 2: Merging overlapping meetings:

[2,3] overlaps with [2,4], so after merging we’ll have => [[2,4], [3,5], [4,5]]
[2,4] overlaps with [3,5], so after merging we’ll have => [[2,5], [4,5]]
[2,5] overlaps [4,5], so after merging we’ll have => [2,5]
Since all the given meetings have merged into one big meeting ([2,5]), does this mean that they all are overlapping and we need a minimum of four rooms 
to hold these meetings? You might have already guessed that the answer is NO! As we can clearly see, some meetings are mutually exclusive. For example,
[2,3] and [3,5] do not overlap and can happen in one room. So, to correctly solve our problem, we need to keep track of the mutual exclusiveness of the
overlapping meetings.

Here is what our strategy will look like:

We will sort the meetings based on start time.
We will schedule the first meeting (let’s call it m1) in one room (let’s call it r1).
If the next meeting m2 is not overlapping with m1, we can safely schedule it in the same room r1.
If the next meeting m3 is overlapping with m2 we can’t use r1, so we will schedule it in another room (let’s call it r2).
Now if the next meeting m4 is overlapping with m3, we need to see if the room r1 has become free. For this, we need to keep track of the end time of the
meeting happening in it. If the end time of m2 is before the start time of m4, we can use that room r1, otherwise, we need to schedule m4 in another room 
r3.
We can conclude that we need to keep track of the ending time of all the meetings currently happening so that when we try to schedule a new meeting, we
can see what meetings have already ended. We need to put this information in a data structure that can easily give us the smallest ending time. A Min Heap
would fit our requirements best.

So our algorithm will look like this:

Sort all the meetings on their start time.
Create a min-heap to store all the active meetings. This min-heap will also be used to find the active meeting with the smallest end time.
Iterate through all the meetings one by one to add them in the min-heap. Let’s say we are trying to schedule the meeting m1.
Since the min-heap contains all the active meetings, so before scheduling m1 we can remove all meetings from the heap that have ended before m1, i.e.,
remove all meetings from the heap that have an end time smaller than or equal to the start time of m1.
Now add m1 to the heap.
The heap will always have all the overlapping meetings, so we will need rooms for all of them. Keep a counter to remember the maximum size of the heap 
at any time which will be the minimum number of rooms needed.
    
/*
1. 如果两meeting overlap： a.end > b.start 
2. 用一个q来存正在进行的会议，sort by end time。 如果当前会议和最早结束的会议没overlap，则可与堆顶共享会议室：拿出堆顶会议，放入当前会议
                                            如果当前会议与最早结束会议有overlap，则会议室++， 把当前会议放入堆
 Time：最坏所有会议都重叠，nlgn 把会议放进堆 + n扫所有会议  + nlgn sorting -> nlgn 
 Space: O(n) of constructing the hip 
*/
/*
1. 如果两meeting overlap： a.end > b.start 
2. 用一个q来存正在进行的会议，sort by end time。 如果当前会议和最早结束的会议没overlap，则可与堆顶共享会议室：拿出堆顶会议，放入当前会议
                                            如果当前会议与最早结束会议有overlap，则会议室++， 把当前会议放入堆
 Time：最坏所有会议都重叠，nlgn 把会议放进堆 + n扫所有会议  + nlgn sorting -> nlgn 
 Space: O(n) of constructing the hip 
*/
class Solution {
    public int minMeetingRooms(int[][] intervals) {
        // 1. sort array by start time 
        Comparator<int[]> com = new Comparator<int[]>() {
            @Override
            public int compare(int[] a, int[] b) {
                return a[0] - b[0];
            }
        };
        Arrays.sort(intervals, com);
        
        int count = 1; // the first meeting in intervals  
        PriorityQueue<int[]> q = new PriorityQueue<int[]>((a, b) -> a[1] - b[1]); // q 存所有正在进行的meeting，sort by end time
        q.offer(intervals[0]); 
        for (int i = 1; i < intervals.length; i++) {
            // check if have overlap by comparing the heap top. end > interval.start 
            int[] curMeeting = intervals[i];
            while (q.size() != 0 && q.peek()[1] <= curMeeting[0]) { // remove all meetings that have ended
                q.poll();
            }
            // put the current meeting into q for 1) if non overlap, curMeeting must end after firstEndMeeting, so put it into q. 2) if overlap, then 2 meetins must be in q, so also needs to put it into queue 
            q.offer(curMeeting);
            // all active meeting are in the minHeap, so we need rooms for all of them.
            count = Math.max(count, q.size());
        }
        return count; 
        
    }
}   
------------------------------------------------------------------------------------------------------------

PriorityQueue: insertion: O(logN), deletion(logN). buid a PQ: O(N) other: O(1） 
https://blog.csdn.net/yangzhongblog/article/details/8607632 

PQ 的解释： https://blog.csdn.net/changyuanchn/article/details/14564403

复杂度
时间 O(NlogN) 空间 O(n)

思路
这题的思路和Rearrange array to certain distance很像，我们要用贪心法，即从第一个时间段开始，选择下一个最近不冲突的时间段，再选择下一个最近不冲突的时间段，直到没有更多。
然后如果有剩余时间段，开始为第二个房间安排，选择最早的时间段，再选择下一个最近不冲突的时间段，直到没有更多，如果还有剩余时间段，则开辟第三个房间，以此类推。这里的技巧是我们不一定要
遍历这么多遍，我们实际上可以一次遍历的时候就记录下，比如第一个时间段我们放入房间1，然后第二个时间段，如果和房间1的结束时间不冲突，就放入房间1，否则开辟一个房间2。然后第三个时间段
，如果和房间1或者房间2的结束时间不冲突，就放入房间1或者2，否则开辟一个房间3，依次类推，最后统计开辟了多少房间。对于每个房间，我们只要记录其结束时间就行了，这里我们查找不冲突房间时，
只要找结束时间最早的那个房间。
这里还有一个技巧，如果我们把这些房间当作List来管理，每次查询需要O(N)时间，如果我们用堆来管理，可以用logN时间找到时间最早结束的房间。 

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
    public int minMeetingRooms(Interval[] intervals) {
        if (intervals == null || intervals.length == 0) {
            return 0; 
        }
        // Sort intervals by starting value; 
        Arrays.sort(intervals, new Comparator<Interval>(){
            public int compare(Interval i1, Interval i2) {
                return i1.start -i2.start;
            }
        });
        
        PriorityQueue<Integer> endTimes = new PriorityQueue<Integer>();
        endTimes.offer(intervals[0].end);
        for (int i = 1; i < intervals.length; i++) {
            //// 如果当前时间段的开始时间大于最早结束的时间，则可以更新这个最早的结束时间为当前时间段的结束时间，如果小于的话，就加入一个新的结束时间，表示新的房间
            // = means 刚好赶上下一个会议的开始时间。eg: [[13,15],[1,13]]
            if( intervals[i].start >= endTimes.peek() ) {
                endTimes.poll();
            }
            endTimes.offer(intervals[i].end);
        }
         // 有多少结束时间就有多少房间
        return endTimes.size(); 
    }
} 
