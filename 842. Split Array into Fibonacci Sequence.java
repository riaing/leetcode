Given a string S of digits, such as S = "123456579", we can split it into a Fibonacci-like sequence [123, 456, 579].

Formally, a Fibonacci-like sequence is a list F of non-negative integers such that:

0 <= F[i] <= 2^31 - 1, (that is, each integer fits a 32-bit signed integer type);
F.length >= 3;
and F[i] + F[i+1] = F[i+2] for all 0 <= i < F.length - 2.
Also, note that when splitting the string into pieces, each piece must not have extra leading zeroes, except if the piece is the number 0 itself.

Return any Fibonacci-like sequence split from S, or return [] if it cannot be done.

Example 1:

Input: "123456579"
Output: [123,456,579]
Example 2:

Input: "11235813"
Output: [1,1,2,3,5,8,13]
Example 3:

Input: "112358130"
Output: []
Explanation: The task is impossible.
Example 4:

Input: "0123"
Output: []
Explanation: Leading zeroes are not allowed, so "01", "2", "3" is not valid.
Example 5:

Input: "1101111"
Output: [110, 1, 111]
Explanation: The output [11, 0, 11, 11] would also be accepted.
Note:

1 <= S.length <= 200
S contains only digits.

-----------------------------------------recursion------------------------------------------------
    Time： 大概O（N^3），因为确定第一二位是n^2, 然后求三，四。。。位等于一个个数字的比较，那么三，四。。。位大概也就n位数，所以每个确定了的1，2位
    要O（n) to find the rest. so the total is O(n^3) approx. 

class Solution {
    List<Integer> res;
    public List<Integer> splitIntoFibonacci(String S) {
        res = new ArrayList<Integer>();
        boolean found = search(S, 0, new ArrayList<Integer>());
        return res; 
    }
    
    private boolean search(String S, int index, List<Integer> fib) {
        if (index == S.length() && fib.size() >= 3) {
            res = new ArrayList<Integer>(fib);
            return true;
        }
        
        for (int i = index; i<S.length(); i++) {
            String cur = S.substring(index, i+1);
            
            // avoid 05 because it's not a valid number 
            if(cur.length() > 1 && cur.charAt(0) == '0') {
                break;
            }
            // could be larger than integer. so we assign it as long first. 
            long curVal = Long.parseLong(cur);
            // Must be a valid integer
            if (curVal > Math.pow(2,31)-1) { // or > Integer.MAX_VALUE
                break;
            }
            int size = fib.size();
            if (size < 2 || (fib.get(size-1) + fib.get(size-2) == curVal)) {
                fib.add((int) curVal);
                if (search(S, i+1, fib)) {
                    return true;
                }
                fib.remove(fib.size()-1);
            }
            
        }
        return false;
    }
}
