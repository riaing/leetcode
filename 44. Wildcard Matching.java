Given an input string (s) and a pattern (p), implement wildcard pattern matching with support for '?' and '*' where:

'?' Matches any single character.
'*' Matches any sequence of characters (including the empty sequence).
The matching should cover the entire input string (not partial).

 

Example 1:

Input: s = "aa", p = "a"
Output: false
Explanation: "a" does not match the entire string "aa".
Example 2:

Input: s = "aa", p = "*"
Output: true
Explanation: '*' matches any sequence.
Example 3:

Input: s = "cb", p = "?a"
Output: false
Explanation: '?' matches 'c', but the second letter is 'a', which does not match 'b'.
 

Constraints:

0 <= s.length, p.length <= 2000
s contains only lowercase English letters.
p contains only lowercase English letters, '?' or '*'.
  
  ------------------------ DP ------------------------------------
  /*
dp[i][j] -  0..i 的string和0.。j的pattern是否match. 
以 p为准，分情况讨论。 1.? 2. *. 3.字符
Time o(n^3)
*/
class Solution {
    public boolean isMatch(String s, String p) {
        if (p.length() == 0) {
            return s.length() == 0; 
        }
        // pruning. condense consecutive *
        StringBuilder b = new StringBuilder(p.charAt(0) + "");
        for (int i = 1; i < p.length(); i++) {
            if (p.charAt(i-1) == '*' && p.charAt(i) == '*') {
                continue;
            }
            b.append(p.charAt(i));
        }
        String newP = b.toString();
        
        
        boolean[][] dp = new boolean[s.length() + 1][newP.length() + 1]; // 0..i 的string和0.。j的pattern是否match. 
        dp[0][0] = true; // 空字符
       // dp[1][0] = false; default就是false了。不要有这条。否则 S = ""时还要特殊处理
        dp[0][1] = newP.charAt(0) == '*' ? true : false; 
        for (int i = 1; i <= s.length(); i++) {
            for (int j = 1; j <= newP.length(); j++) {
                // 以pattern来判断
                char curP = newP.charAt(j-1); 
                if (curP == '?') {
                    dp[i][j] = dp[i-1][j-1]; 
                }
                else if (curP == '*') {
                    // 只要前面任何一个有match就行
                    boolean findOne = false;
                    for (int k = i; k >= 0;  k--) { // dp[i][j-1] (一个不取）, dp[i-1][j-1], dp[i-2][j-1]... dp[0][j-1] 任意一个match就行 
                        if (dp[k][j-1]) {
                            findOne = true;
                            break;
                        }
                    }
                     dp[i][j] = findOne; 
                }
                // 字母比较
                else {
                    if (s.charAt(i-1) == newP.charAt(j-1)) {
                        dp[i][j] = dp[i-1][j-1];
                    }
                    else {
                        dp[i][j] = false; 
                    }

                }
            }
        }
        return dp[s.length()][newP.length()];
    }
}
