Nearly every one have used the Multiplication Table. But could you find out the k-th smallest number quickly from the multiplication table?

Given the height m and the length n of a m * n Multiplication Table, and a positive integer k, you need to return the k-th smallest number in this table.

Example 1:
Input: m = 3, n = 3, k = 5
Output: 
Explanation: 
The Multiplication Table:
1	2	3
2	4	6
3	6	9

The 5-th smallest number is 3 (1, 2, 2, 3, 3).
Example 2:
Input: m = 2, n = 3, k = 6
Output: 
Explanation: 
The Multiplication Table:
1	2	3
2	4	6

The 6-th smallest number is 6 (1, 2, 2, 3, 4, 6).
Note:
The m and n will be in the range [1, 30000].
The k will be in the range [1, m * n]

------------------------------bineray search-----------------------------------------------------------------
解释讲得比较好。。https:// https://www.youtube.com/watch?v=qvtYRm4reL4 
类似的题还有 Find the Duplicate Number， K-th Smallest Prime Fraction 
Time：M*logM*N
class Solution {
    public int findKthNumber(int m, int n, int k) {
        int start = 1;
        int end = m*n;
        // O(logm*n)
        while(start < end) {
            int mid = start + (end - start) / 2;
            if (largerEqualTox(m,n,mid,k)) {
                end = mid;
            }
            else{
                start = mid + 1;
            }
        }
        return start;
    }
    
    // O(m) -> search each row and cnt in each row is O(1)
    private boolean largerEqualTox(int m, int n, int x, int k) {
        int cnt = 0;
        for (int i = 1; i <= m; i++) {
            // how many number <= k in each row. 
            cnt += Math.min(n, x/i);
            // pruning: break if cnt has been bigger than k in middle.
            if (cnt >= k) {
                return true;
            }
        }
        return cnt >= k;
    }
}
