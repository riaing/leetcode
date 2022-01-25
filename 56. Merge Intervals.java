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

----------------- 2022.2.24 ----------------------
    /*
思路是每次只要和 result 中的最后一个比。
1.每次比较 start大小
 - 如果没有 overlap，则加到结果里
 - 如果有 overlap，则更新 end
 
 Time  O(N * logN) -> O (N) for interating the array, but nlgn for sorting
 Space: O(N) -> to print the result 
*/
class Solution {
    public int[][] merge(int[][] intervals) {
        // sort intervals by starting 
        // Collections.sort(intervals,  (a,b) -> Integer.compare(a,get(0), b.get(0))); // operates on a list 
        Arrays.sort(intervals, (a, b) -> Integer.compare(a[0], b[0])); // operates on an array 
        
        List<int[]> result = new ArrayList<int[]>();
        for (int i = 0; i < intervals.length; i++) {
            if (result.size() == 0) {
                result.add(intervals[i]);
                continue;
            }
            int[] lastArrayInResult = result.get(result.size() -1);
            if (lastArrayInResult[1] < intervals[i][0]) { // 如果没有 overlap，把当前 array 加到结果里。
                 result.add(intervals[i]);
            }
            else {
               lastArrayInResult[1] = Math.max(lastArrayInResult[1], intervals[i][1]); // 如果 overlap，则更新
            }
            
            
        }
        
        // print to final result 
        int[][] finalRes = new int[result.size()][2];
        for (int i = 0; i < result.size(); i++) {
            finalRes[i] = result.get(i);
        }
        
        return finalRes;
    }
}
