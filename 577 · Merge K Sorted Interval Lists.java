Description
Merge K sorted interval lists into one sorted interval list. You need to merge overlapping intervals too.

Contact me on wechat to get Amazon、Google requent Interview questions . (wechat id : jiuzhang15)


Example
Example1

Input: [
  [(1,3),(4,7),(6,8)],
  [(1,2),(9,10)]
]
Output: [(1,3),(4,8),(9,10)]
Example2

Input: [
  [(1,2),(5,6)],
  [(3,4),(7,8)]
]
Output: [(1,2),(3,4),(5,6),(7,8)]

---------------------------- HEAP ------------------------------------------------

  /**
 * Definition of Interval:
 * public classs Interval {
 *     int start, end;
 *     Interval(int start, int end) {
 *         this.start = start;
 *         this.end = end;
 *     }
 * }
 */


class Pair { // 代表每个interval的位置，想象成一个矩阵
    int row; // list index 
    int col; // interval index 
    public Pair(int row, int col) {
        this.row = row;
        this.col = col; 
    }
}

/*
Time: 时间复杂度O ( n l log ⁡ n ) O(nl\log n)O(nllogn)，n nn是list的数量，l ll是最长list的长度，空间O ( n ) O(n)O(n)
————————————————
版权声明：本文为CSDN博主「记录算法」的原创文章，遵循CC 4.0 BY-SA版权协议，转载请附上原文出处链接及本声明。
原文链接：https://blog.csdn.net/qq_46105170/article/details/109142962
*/
public class Solution {
    /**
     * @param intervals: the given k sorted interval lists
     * @return:  the new sorted interval list
     */
    public List<Interval> mergeKSortedIntervalLists(List<List<Interval>> intervals) {
        // 1. 用最小堆模拟一个list sort by start time， 每次堆顶和res中的最后一个相比，看看要不要merge
        PriorityQueue<Pair> q = new PriorityQueue<>((a, b) -> intervals.get(a.row).get(a.col).start - intervals.get(b.row).get(b.col).start);
        // 2. 加入每个list的第一个interval
        for (int i = 0; i < intervals.size(); i++) {
            if (intervals.get(i).size() != 0) {
                 q.offer(new Pair(i, 0));
            }
        }
        List<Interval> res = new ArrayList<>(); 
        // 3. 拿出堆顶做merge
        while (!q.isEmpty()) {
            Pair curLoc = q.poll();
            mergeOrAdd(curLoc, res, intervals);
            // 4. 如果当前list还有值，放入q
            List<Interval> curList = intervals.get(curLoc.row);
            if (curLoc.col < curList.size() - 1) {
                q.offer(new Pair(curLoc.row, curLoc.col + 1));
            }
        }
        return res; 
    }
    private void mergeOrAdd(Pair curLoc, List<Interval> res, List<List<Interval>> intervals) {
        Interval cur = new Interval(intervals.get(curLoc.row).get(curLoc.col).start, intervals.get(curLoc.row).get(curLoc.col).end);
        // 1. 如果是第一个interval，直接加list
        if (res.size() == 0) {
            res.add(cur);
            return;
        }
        // 2. 和最后一个比要不要merge
        Interval pre = res.get(res.size() - 1);
        if (pre.end >= cur.start) {
            pre.end = Math.max(pre.end, cur.end);
        }
        else {
            res.add(cur);
        }
    }
}
