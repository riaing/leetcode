Given a string s, return the number of different non-empty palindromic subsequences in s. Since the answer may be very large, return it modulo 109 + 7.

A subsequence of a string is obtained by deleting zero or more characters from the string.

A sequence is palindromic if it is equal to the sequence reversed.

Two sequences a1, a2, ... and b1, b2, ... are different if there is some i for which ai != bi.

 

Example 1:

Input: s = "bccb"
Output: 6
Explanation: The 6 different non-empty palindromic subsequences are 'b', 'c', 'bb', 'cc', 'bcb', 'bccb'.
Note that 'bcb' is counted only once, even though it occurs twice.
Example 2:

Input: s = "abcdabcdabcdabcdabcdabcdabcdabcddcbadcbadcbadcbadcbadcbadcbadcba"
Output: 104860361
Explanation: There are 3104860382 different non-empty palindromic subsequences, which is 104860361 modulo 109 + 7.
 

Constraints:

1 <= s.length <= 1000
s[i] is either 'a', 'b', 'c', or 'd'. 



------------------------------------------ DP ---------------------------------------------------
/*

看blog https://zxi.mytechroad.com/blog/?s=730 

1000 说明算法只能O(n^2)
重点：怎么去重

如果首尾一样，
 -如果中间部分不包含首尾字符：
                        count(bccb) = 2 * count(cc) + 2 : *2是因为对子问题的每一个解，可以append bb，所以子问题解double (cc, c -> bccb, bcb, cc, c)； +2因为这两可以组成b, bb 
                        base: count(x) = 1 if x = a/b/c/d; = 0 if empty 
 - 若中间包含了首尾字符：会有重复解。
    - 如果中间有一个字母和首尾一样： count(bbb) = 2 * count(b) + 1
    - 如果中间有>1个字母和首尾一样： count(bbcbb) = 2 * count(bcb) - count(c) 

如果首尾不一样： 
count(bcca) = count(bcc) + count(cca) - count(cc) => dp[i][j] = dp[i+1][j] + dp[i][j-1] - dp[i+1][j-1]
eg: count(bc) = count(b) + count(c) - count("") = 1 + 1 - 0 = 2

dp[i][j] count of different palindromic subseq of s[i] ~ s[j], inclusive 
dp[i][j] = 
if (i != j) 
 - dp[i+1][j] + dp[i][j-1] - dp[i+1][j-1] 
if (i == j) 
    dp[i+1][j-1] * 2 + 2 -> if (中间有0个n[i]) = l > r
    dp[i+1][j-1] * 2 + 1 -> if (中间有1个n[i]) = l = r
    dp[i+1][j-1] * 2 - dp[l+1][r-1] -> if (中间有2个n[i]),  if n[i] = b, l,r定义为内层b的里面： b..b(l...r)b...b = l < r


 

*/
class Solution {
    public int countPalindromicSubsequences(String s) {
        long modulo = (long) Math.pow(10, 9) + 7;
        int n = s.length(); 
        long[][] dp = new long[n][n];
        // base 
        for (int i = 0; i < n; i++) {
            dp[i][i] = 1;
        }
        for (int i = n -2; i >=0; i--) {
            for (int j = i+1; j < n; j++) { // i,j总差一位
                if (s.charAt(i) != s.charAt(j)) {
                    dp[i][j] = dp[i+1][j] + dp[i][j-1] - dp[i+1][j-1];
                    continue;
                }
                // i == j 
                int l = i + 1; // 找中间的相同字母位子
                int r = j - 1;
                while (l <= r && s.charAt(l) != s.charAt(i)) {
                    l++;
                }
                while (l <= r && s.charAt(r) != s.charAt(i)) {
                    r--;
                }
                if (l > r) { //中间有0个n[i]
                    dp[i][j] = dp[i+1][j-1] * 2 + 2; // bcb = c*2 + 2
                    
                }
                else if (l < r) {
                     dp[i][j] = dp[i+1][j-1] * 2 - dp[l+1][r-1]; // bbcbb = bcb*2 - c
                }
                else {
                      dp[i][j] = dp[i+1][j-1] * 2 + 1; // bbb = b * 2 + 1 
                }
                // if (dp[i][j] < 0) {
                //      System.out.println("i " + i + ".j: " + j + " dp " + dp[i][j] + " + module " + (dp[i][j] + modulo));
                // }
               
                // 注意overflow
                dp[i][j] = (dp[i][j] + modulo) % modulo; // dp[i][j] 可能已经overflow变负数了。记吧。感觉也不会考
            }
        }
        
        // long t = 3104860382L;
        // System.out.println(t % modulo);
        return (int) dp[0][n-1];
    }
}
