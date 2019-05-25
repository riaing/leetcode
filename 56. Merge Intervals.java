Given a collection of intervals, merge all overlapping intervals.

Example 1:

Input: [[1,3],[2,6],[8,10],[15,18]]
Output: [[1,6],[8,10],[15,18]]
Explanation: Since intervals [1,3] and [2,6] overlaps, merge them into [1,6].
Example 2:

Input: [[1,4],[4,5]]
Output: [[1,5]]
Explanation: Intervals [1,4] and [4,5] are considered overlapping.
NOTE: input types have been changed on April 15, 2019. Please reset to default code definition to get new method signature.

-------------------------------------------
/* sort takes O(nlgn). wosrt case o(n) ON MERGING like no interval's can be merged */
class Solution {
    public int[][] merge(int[][] intervals) {
        Comparator<int[]> comparator = new Comparator<int[]>(){
            @Override
            public int compare(int[] a, int[] b) {
                return Integer.compare(a[0], b[0]);
            }
        };
        Arrays.sort(intervals, comparator);
        List<int[]> temRes = new ArrayList<int[]>();
        for (int i = 0; i < intervals.length; i++) {
            
            if (temRes.size() == 0 || temRes.get(temRes.size() - 1)[1] < intervals[i][0]) {
                temRes.add(intervals[i]);
            }
            // merge two intervals 
            else {
                int[] pre = temRes.get(temRes.size() - 1);
                pre[1] = Math.max(pre[1] , intervals[i][1]);
            }
        }
        
        int[][] res = new int[temRes.size()][2];
        for (int i = 0; i < temRes.size(); i++) {
            res[i] = temRes.get(i);
        }
        return res;
    }
}
