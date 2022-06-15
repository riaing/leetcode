Given an integer n, return a binary string representing its representation in base -2.

Note that the returned string should not have leading zeros unless the string is "0".

 

Example 1:

Input: n = 2
Output: "110"
Explantion: (-2)2 + (-2)1 = 2
Example 2:

Input: n = 3
Output: "111"
Explantion: (-2)2 + (-2)1 + (-2)0 = 3
Example 3:

Input: n = 4
Output: "100"
Explantion: (-2)2 = 4
 

Constraints:

0 <= n <= 109

------------------------ 转-2进制 ---------------
/*
正常转2进制，就是num % 2，再 num = num / 2
这题是num %2， 再num = - （num / 2);

注意：我们都知道对于一个正数来说，右移一位就相当于除以2，但是对于负数来说，右移一位却不等于除以2。比如 -3 除以2，等于 -1，但是右移一位却不等于 -1，-3 的八位的表示为 11111101，右移一位是 11111110，是 -2。
所以负数这里不能通过/, %答题，需要：
n / 2 = n >> 1
n % 2 = n & 1
*/
class Solution {
    public String baseNeg2(int n) {
        String res = "";
        while (n != 0) {
            // System.out.println("n: " + n + " n%2 " + n%2 + " n&1 " + (n &1));  // n: -1, n%2 = -1, n&1 = 1
     

            res = (n & 1) + res; 
            // System.out.println("n: " + n + " n/2 " + n/2 + " n>>1 " + (n >>1)); // n: -1,  n/2 = 0,  n>>1 = -1
            n = -(n >> 1);
        }
        return res == "" ? "0" : res; 
    }
}
