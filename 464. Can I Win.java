In the "100 game" two players take turns adding, to a running total, any integer from 1 to 10. The player who first causes the running total to reach or exceed 100 wins.

What if we change the game so that players cannot re-use integers?

For example, two players might take turns drawing from a common pool of numbers from 1 to 15 without replacement until they reach a total >= 100.

Given two integers maxChoosableInteger and desiredTotal, return true if the first player to move can force a win, otherwise, return false. Assume both players play optimally.

 

Example 1:

Input: maxChoosableInteger = 10, desiredTotal = 11
Output: false
Explanation:
No matter which integer the first player choose, the first player will lose.
The first player can choose an integer from 1 up to 10.
If the first player choose 1, the second player can only choose integers from 2 up to 10.
The second player will win by choosing 10 and get a total = 11, which is >= desiredTotal.
Same with other integers chosen by the first player, the second player will always win.
Example 2:

Input: maxChoosableInteger = 10, desiredTotal = 0
Output: true
Example 3:

Input: maxChoosableInteger = 10, desiredTotal = 1
Output: true
 

Constraints:

1 <= maxChoosableInteger <= 20
0 <= desiredTotal <= 300

-------------------------------------- bit manipulation to do memo -----------------------------------------------

/*
min-max 
memo: 走过的路线21/12 结果是一样的，所以用一个array记录用过的integer -> 结果。

bit manupulation: https://www.youtube.com/watch?v=GNZIAbf0gT0 
https://www.educative.io/blog/bit-manipulation-in-java 

*/
class Solution {
    public boolean canIWin(int maxChoosableInteger, int desiredTotal) {
        int sum = maxChoosableInteger * (maxChoosableInteger + 1) / 2;
        if (sum < desiredTotal) {
            return false;
        }
        if (desiredTotal <= 1) {
            return true; 
        }
        // memo的两写法：用arry或者map
        // int[] memo = new int[1<<maxChoosableInteger]; // index 存state，1001，1100这种，代表哪些number被用过0001 = 1 << 0 说明1被用过. value: 0 - 没visited过。 1 - visit过且能win， -1 - visited过，不能赢
        Map<Integer, Boolean> memoMap = new HashMap<>(); 
        boolean res = helper(maxChoosableInteger, desiredTotal, memoMap, 0);
        return res; 
    }
    // private boolean helper(int max, int k, int[] memo, int state) {
    private boolean helper(int max, int k, Map<Integer, Boolean> memoMap, int state) {
        if (k <= 0) { // 说明上轮对手已经赢了
            return false; 
        }  
        // memo part 
        // if (memo[state] != 0) {
        //     return memo[state] == 1;
        // }
        if (memoMap.containsKey(state)) {
            return memoMap.get(state); 
        }
        
        for (int i = 0; i < max; i++) { // 用 1 << i 来代表当前数。 eg: 1: 1<<0 = 0001, 10: 1<<9 
            if ((state & 1<<i) > 0) { // number i used 
                continue;
            }
  

            int newK = k - (i+1);
            int newState = state | (1<<i); // 把i代表的位数设置为1
            if (!helper(max, newK, memoMap, newState)) {
                // memo[state] = 1;
                memoMap.put(state, true);
                return true;
            }
            
        }
        // memo[state] = -1; 
        memoMap.put(state, false); 
        return false;
    }
}
