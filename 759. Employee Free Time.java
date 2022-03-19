We are given a list schedule of employees, which represents the working time for each employee.

Each employee has a list of non-overlapping Intervals, and these intervals are in sorted order.

Return the list of finite intervals representing common, positive-length free time for all employees, also in sorted order.

(Even though we are representing Intervals in the form [x, y], the objects inside are Intervals, not lists or arrays. For example, schedule[0][0].start = 1, schedule[0][0].end = 2, and schedule[0][0][0] is not defined).  Also, we wouldn't include intervals like [5, 5] in our answer, as they have zero length.

 

Example 1:

Input: schedule = [[[1,2],[5,6]],[[1,3]],[[4,10]]]
Output: [[3,4]]
Explanation: There are a total of three employees, and all common
free time intervals would be [-inf, 1], [3, 4], [10, inf].
We discard any intervals that contain inf as they aren't finite.
Example 2:

Input: schedule = [[[1,3],[6,7]],[[2,4]],[[2,5],[9,12]]]
Output: [[5,6],[7,9]]
 

Constraints:

1 <= schedule.length , schedule[i].length <= 50
0 <= schedule[i].start < schedule[i].end <= 10^8 


------------------------ Heap + merge interval --------------------
The above algorithm’s time complexity is O(N*logK)
, where ‘N’ is the total number of intervals, and ‘K’ is the total number of employees. This is because we are iterating through the intervals only once
(which will take O(N), and every time we process an interval, we remove (and can insert) one interval in the Min Heap, (which will take O(logK)
, At any time, the heap will not have more than ‘K’ elements.

Space complexity#
The space complexity of the above algorithm will be O(K)
 as at any time, the heap will not have more than ‘K’ elements.
 
 /*
// Definition for an Interval.
class Interval {
    public int start;
    public int end;

    public Interval() {}

    public Interval(int _start, int _end) {
        start = _start;
        end = _end;
    }
};
*/

/*
第一想法： sort 所有interval by starting time，碰到 overlap时，更新end为大的那个end，然后每个interval和cur对比。找non overlap
这题的性质： 1. 每个员工的时间sorted by stort time. 2. 员工自己的时间不可能重叠。 所以其实我们只需要比较当前cur和所有员工中start time最小的那个，看有没有重叠。这就需要一个ds能动态代表每个员工当前最小的starting time的interval。就想到用queue。

algorithm 
1. use a queue 代表每个员工的interval sorted array by starting time。queue size为员工数量
2. 把所有员工的start time 放到queue
3. 拿出queue顶作为cur（代表某员工最早的start time），和pre 对比看有没有重叠，
    没有就记录一个答案
    有的话，更新pre的end max{cur, pre}
4. 把该员工的下一个interval加到queue里 —> 所以需要记录每个interval在schedule里的index，以及在每个员工list中的index
*/

class EmployeeInterval {
    int start;
    int end;
    int employeeIndex; // index in the whole array
    int intervalIndex; // index in the emplyee array 
    public EmployeeInterval(int start, int end, int employeeIndex, int intervalIndex) {
        this.start = start;
        this.end = end;
        this.employeeIndex = employeeIndex;
        this.intervalIndex = intervalIndex; 
    }
}

class Solution {
    public List<Interval> employeeFreeTime(List<List<Interval>> schedule) {
        // 1. add the first interval of each employee into queue 
        PriorityQueue<EmployeeInterval> q = new PriorityQueue<EmployeeInterval>((a, b) -> a.start - b.start);
        for (int i = 0; i < schedule.size(); i++) {
            Interval cur = schedule.get(i).get(0);
            q.offer(new EmployeeInterval(cur.start, cur.end, i, 0));
        }
        List<Interval> res = new ArrayList<>();
        EmployeeInterval pre = q.peek();
        while (q.size() != 0) {
            // 拿出top，与之前的比是否overlap
            EmployeeInterval cur = q.poll();
            if (pre.end >= cur.start) { // has overlap, update pre's end time to be the bigger one 
                cur.end = Math.max(pre.end, cur.end);
            }
            else {
                res.add(new Interval(pre.end, cur.start));
            }
            pre = cur; 
            
            // 如果这员工还有下个interval，加到q里
            if (cur.intervalIndex < schedule.get(cur.employeeIndex).size() - 1) {
                Interval nextInterval = schedule.get(cur.employeeIndex).get(cur.intervalIndex+1);
                EmployeeInterval toAdd = new EmployeeInterval(nextInterval.start, nextInterval.end, cur.employeeIndex, cur.intervalIndex+1);
                q.offer(toAdd);
            }
        }
        return res; 
    }
}
