A sorted list A contains 1, plus some number of primes.  Then, for every p < q in the list, we consider the fraction p/q.

What is the K-th smallest fraction considered?  Return your answer as an array of ints, where answer[0] = p and answer[1] = q.

Examples:
Input: A = [1, 2, 3, 5], K = 3
Output: [2, 5]
Explanation:
The fractions to be considered in sorted order are:
1/5, 1/3, 2/5, 1/2, 3/5, 2/3.
The third fraction is 2/5.

Input: A = [1, 7], K = 1
Output: [1, 7]
Note:

A will have length between 2 and 2000.
Each A[i] will be between 1 and 30000.
K will be between 1 and A.length * (A.length - 1) / 2.

-------------------------------------------bineray search + sliding window-----------------------------------------------
class Solution {
    public int[] kthSmallestPrimeFraction(int[] A, int K) {
        double lo = 0;
        double hi = 1;
        int[] ans = new int[2];
     
        //time complexity 取决于这里的精确度（1e-9），可以想象成在0-1之间分了10^9段,所以bineray search O(log10^9)
        while (hi-lo >= 1e-9) { // lo < hi, but since it's double,我们给一个更精确的数, 用科学记数法1e-9 = 1*10^(-9)。这里也可以
            //换成10*（-8）。。。题目中说Each A[i] will be between 1 and 30000.所以只要这个threshould< 1/30000就行。
            double mid = lo + (hi - lo) / 2.0;
            int[] estimate = equalOrSmallerToTarget(A, mid);
            if (estimate[0] >= K) {
                hi = mid;
                //also update the result
                ans[0] = estimate[1];
                ans[1] = estimate[2];
            }
            else {
                lo = mid;
            }
        }
      return ans;
    }
    
    // sliding window  O（n）
    private int[] equalOrSmallerToTarget(int[] A, double target) {
        int start = 0; 
        int end = 0;
        int numer = 0;
        int denom = 1; 
        int cnt = 0;
        
        while (start <= end && end < A.length){
            if (A[start] <= target * A[end]) { // start/end < target
                cnt += (A.length - end);
                // update numer and denon to the largest valid 
                if (numer*A[end] < denom*A[start]) { // numer/denom < start/end
                    numer = A[start];
                    denom = A[end];
                } 
                start++;
            }
            else {
                end++;
            }
        }
        return new int[]{cnt, numer, denom};
    }
}
