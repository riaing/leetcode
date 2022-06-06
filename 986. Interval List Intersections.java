iven two lists of intervals, find the intersection of these two lists. Each list consists of disjoint intervals sorted on their start time.

Examples:

Input: arr1=[[1, 3], [5, 6], [7, 9]], arr2=[[2, 3], [5, 7]]
Output: [2, 3], [5, 6], [7, 7]
Explanation: The output list contains the common intervals between the two lists.
Example 2:

Input: arr1=[[1, 3], [5, 7], [9, 12]], arr2=[[5, 10]]
Output: [5, 7], [9, 10]
Explanation: The output list contains the common intervals between the two lists.

Input: firstList = [[1,3],[5,9]], secondList = []
Output: []
 

Constraints:

0 <= firstList.length, secondList.length <= 1000
firstList.length + secondList.length >= 1
0 <= starti < endi <= 109
endi < starti+1
0 <= startj < endj <= 109
endj < startj+1


-------------------------------------------------------------
// 运用 merge interval 的思路：1）判断何时 merge 2）merge 完后怎么处理
class Solution {
    public int[][] intervalIntersection(int[][] firstList, int[][] secondList) {
        int i = 0;
        int j = 0;
        List<int[]> result = new ArrayList<int[]>();
        while (i < firstList.length && j < secondList.length) {
             // 1. 判断 overlap by a.end >= b.start. 再取 overlap 部分 by max[start1, start2] min[end1, end2]
            int[] a;
            int[] b;
            if (firstList[i][0] <= secondList[j][0]) {
                a = firstList[i];
                b = secondList[j];
            }
            else {
                a = secondList[j];
                b = firstList[i];
            }
            if (a[1] >= b[0]) {
                int[] tmp = new int[]{Math.max(a[0], b[0]), Math.min(a[1], b[1])};
                result.add(tmp);
            }
            
            // 2.重点！move ponter forward by comparing end。因为 end 长的可能还和别的 interval 有 overlap。ex：A: [9,20] B[11,12] [12, 14], [15, 16...20]
            if (firstList[i][1] <= secondList[j][1]) {
                i++;
            }
            else {
                j++;
            }
            
        }
        
        //3. put result together 
        int[][] results = new int[result.size()][2];
        for(int n = 0; n < result.size(); n++) {
            results[n] = result.get(n);
        }
        return results;
    }
}

---------------------------- 2022.6 --------------------------------------------------------------
 class Solution {
    public int[][] intervalIntersection(int[][] firstList, int[][] secondList) {
        int i = 0; int j = 0;
        List<int[]> res = new ArrayList<int[]>();
        while (i < firstList.length && j < secondList.length) {
            int[] first = firstList[i];
            int[] second = secondList[j];
            int[] common = new int[]{Math.max(first[0], second[0]), Math.min(first[1], second[1])};
            if (common[0] <= common[1]) { // 是否有intersect
                res.add(common);
            }
            if (first[1] < second[1]) {
                i++;
            }
            else {
                j++;
            }
        }
        int[][] arr = new int[res.size()][2];
        for (int k = 0; k < res.size(); k++) {
            arr[k] = res.get(k);
        }
        return arr; 
    }
}
