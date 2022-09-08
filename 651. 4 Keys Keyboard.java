Imagine you have a special keyboard with the following keys:

A: Print one 'A' on the screen.
Ctrl-A: Select the whole screen.
Ctrl-C: Copy selection to buffer.
Ctrl-V: Print buffer on screen appending it after what has already been printed.
Given an integer n, return the maximum number of 'A' you can print on the screen with at most n presses on the keys.

 

Example 1:

Input: n = 3
Output: 3
Explanation: We can at most get 3 A's on screen by pressing the following key sequence:
A, A, A
Example 2:

Input: n = 7
Output: 9
Explanation: We can at most get 9 A's on screen by pressing following key sequence:
A, A, A, Ctrl A, Ctrl C, Ctrl V, Ctrl V
 

Constraints:

1 <= n <= 50
  
  -------------- memo --------
  /*
if n <= 3, return 3 
n == 4
 - aaaa 
 - a + 3(a) 
n == 5 
 - aaaaa
 - aa + 3(a)
n == 6 
 - a + 3(a) + aa 
n == 7 
 - a + 3(a) + 3(aa)
 
 
 解法1：
 1. can copy + paste
   str * 2, n -= 3 
  2, direct add A 
  str + 1  . n-= 1
  3. paste 
    str * 2, n -= 1
 第一个状态是剩余的按键次数，用 n 表示；第二个状态是当前屏幕上字符 A 的数量，用 strLen 表示；第三个状态是剪切板中字符 A 的数量，用 buffer 表示。  
 时间复杂度高。起码N^3
 
解法2：
最优的操作序列一定是 C-A C-C 接着若干 C-V，所以我们用一个变量 j 作为若干 C-V 的起点。那么 j 之前的 2 个操作就应该是 C-A C-C 了：
https://labuladong.github.io/algo/3/28/94/ 
  
*/
class Solution {
    public int maxA(int n) {
        return helper(n, 0, 0);  // 提高：加memo
    }
    private int helper(int n, int strLen, int buffer) {
        if (n == 0) {
            return strLen;
        }
        int cand1 = strLen;
        int cand2 = strLen;
        int cand3 = strLen;
        if (n >= 3 && strLen != 0) {
            cand1 = helper(n-3, strLen*2, strLen);
        }
        cand2 = helper(n-1, strLen+1, buffer); // add a 
        cand3 = helper(n-1, strLen + buffer, buffer); // ctrl + V 
        return Math.max(Math.max(cand1, cand2), cand3);
    }
    

}
