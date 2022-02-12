Given a n x n matrix where each of the rows and columns are sorted in ascending order, find the kth smallest element in the matrix.

Note that it is the kth smallest element in the sorted order, not the kth distinct element.

Example:

matrix = [
   [ 1,  5,  9],
   [10, 11, 13],
   [12, 13, 15]
],
k = 8,

return 13.
Note: 
You may assume k is always valid, 1 ≤ k ≤ n2.
   
-------------------- 2022 heap solution, 比以下稍微减少一点，不会到nlgk, 而是最坏 nlgk --------------------------
   

class Solution {
    public int kthSmallest(int[][] matrix, int k) {
        // max heap 
        PriorityQueue<Integer> q = new PriorityQueue<Integer>((n1, n2) -> n2 - n1);
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[0].length; j++) {
                if (q.size() < k) {
                    q.offer(matrix[i][j]);
                }
                // 维持一个 K size 的 max heap，只存 top K smallest num,.所以当 cur num
                //小于 heap 头时，需要拿出头，放入 cur
                else if (q.peek() > matrix[i][j]) {
                    q.poll();
                    q.offer(matrix[i][j]);
                }
            }
        }
        return q.peek();
    }
}

------------------------Priority Queue---------------------------------------
我们使用一个最大堆，然后遍历数组每一个元素，将其加入堆，根据最大堆的性质，大的元素会排到最前面，然后我们看当前堆中的元素个数是否大于k，
大于的话就将首元素去掉，循环结束后我们返回堆中的首元素即为所求:
Omn(logmn)

class Solution {
    public int kthSmallest(int[][] matrix, int k) {
        // create a comparator that will used to sort list in descending order 
        Comparator<Integer> comparator = new Comparator<Integer>() {
          @Override 
          public int compare(Integer a, Integer b) {
              return b.compareTo(a);
          }  
        };
    
    
        PriorityQueue<Integer> queue = new PriorityQueue<Integer>(comparator);

        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[0].length; j++) {
                queue.offer(matrix[i][j]);
                if (queue.size() > k) {
                    queue.poll();
                }
            }
        }
        return queue.poll();
    }
}
-------------------------------------Bineray search------------------------------------------------------------
   /**
设lo = min(matrix) hi= max(matrix)  , mid =( L + R ) / 2 ，mid为我们猜测的答案。

然后对于mid,通过searchLowerThanMid找matrix中有多少个元素(cnt）小于等于它，如果cnt < k, 说明k对应的元素比mid大，更新mid为lo+1；如果cnt >=k, 则hi = cnt。
这样不停缩小hi和lo，总会使hi == lo，并且hi/lo肯定在matrix中

我感觉这道题的精华部分是用count记录当前的数是第几小的数，跟k比较从而更新binary search的左右边界. 这种同化成binary search的思路很值得借鉴呐，而且这样一来今后凡是碰到 k-th smallest/largest之类的，其实都是binary search的general case, 除了用pq的方法之外的另一种方法

横列都increasing的 matrix的性质：第一行第一个肯定是最小值，最后一行最后一个肯定是最大值，做法一般是从坐下开始找起，如果左下小于target，说明整列都小，则像右移动；如果大于，则像上移动。

https://www.hrwhisper.me/leetcode-kth-smallest-element-sorted-matrix/ 
*/
class Solution {
    public int kthSmallest(int[][] matrix, int k) {
        int n = matrix.length - 1;
        int lo = matrix[0][0];
        int hi = matrix[n][n];
        // how many element that is smaller than mid
       
        // while loop time complexity O(logX) -> X = max - min.
        while (lo < hi) {
            int mid = lo + (hi - lo) / 2;
            int cnt = searchLowerThanMid(matrix, n, mid);

            if (cnt < k) {
                lo = mid + 1;
            }
            else {
                hi = mid;
            }
        }
        return lo; 
       // Q: Why we return lo at the end:
       // A: Here lo=hi+1, for hi, we found <k elems, for lo, we found >=k elem, lo must have duplicates in matrix, return lo
    
    
    // Find the number of element that <= mid
    // Time complexity  O(m+n)
    private int searchLowerThanMid(int[][] matrix, int n, int mid) {
        int i = n;
        int j = 0;
        int cnt = 0;
        while(i >= 0 && j <= n) {
            // means the whole col is smaller than mid 
            if (matrix[i][j] <= mid) {
                cnt += i+1; // n+1 is the number of element in the whole column
                j++; // move to the next column;
            }
            else {
                i--;
            }
        }
        return cnt; 
    }
    
}
