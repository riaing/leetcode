Suppose you have n integers labeled 1 through n. A permutation of those n integers perm (1-indexed) is considered a beautiful arrangement if for every i (1 <= i <= n), either of the following is true:

perm[i] is divisible by i.
i is divisible by perm[i].
Given an integer n, return the number of the beautiful arrangements that you can construct.

 

Example 1:

Input: n = 2
Output: 2
Explanation: 
The first beautiful arrangement is [1,2]:
    - perm[1] = 1 is divisible by i = 1
    - perm[2] = 2 is divisible by i = 2
The second beautiful arrangement is [2,1]:
    - perm[1] = 2 is divisible by i = 1
    - i = 2 is divisible by perm[2] = 1
Example 2:

Input: n = 1
Output: 1
 

Constraints:

1 <= n <= 15

---------------------- backtracking 时间差--------------------
/*
1. backtracking. (2^n)


*/
class Solution {
    int res; 
    public int countArrangement(int n) {
        Set<Integer> visited = new HashSet<>();
        helper(n, 1, visited);
        return res; 
    }
    
    private void helper(int n, int index, Set<Integer> visited) {
        if (index > n) {
            res++;
            return;
        }
        for (int i = 1; i <= n; i++) {
            if (!visited.contains(i) && (i % index == 0 || index % i == 0)) {
                visited.add(i);
                helper(n, index+1, visited);
                visited.remove(i);
            }
        }
    }
}

------------------- memo by using bit manipulation,最差也是 o(2^n) ------------------
 /*
最坏每个state都要访问一遍。 O (2^N)
*/
class Solution {
    public int countArrangement(int n) {
        int[] dp = new int[1<<n];
        Arrays.fill(dp, -1); 
        return helper(n, dp, 0, 1);
        
    }
    private int helper(int n, int[] dp, int state, int index) {
        // if (state == (1<<n)-1) { // base case的写法1. 每个数字都用了. 可以按照下个
        //     return 1; 
        // }
        
        if (index == n+1) { // base case的写法2
            return 1; 
        }
        
        if (dp[state] != -1) {
            return dp[state];
        }
        
        // get index -> 通过传参数解决了
        // int index = 1; // 因为1 base
        // for (int i = 0; i < n; i++) {
        //     if ((state & (1 << i)) > 0) { // 如果i+1这个数被用过了
        //         index++; 
        //     }
        // }
        
        // 计算
        int sum = 0;
        for (int i = 0; i < n; i++) {
             if ((state & (1 << i)) > 0) { // 如果i+1这个数被用过了
                continue; 
            }
            // 没被用，计算是否valid
            if ((i+1) % index == 0 || index % (i+1) == 0) {
                int newState = state | 1 << i; // 把当前数用上
                sum += helper(n, dp, newState, index+1); 
            }
        }
        dp[state] = sum;
        return sum; 
    }
}
