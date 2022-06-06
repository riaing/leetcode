Given an input string s and a pattern p, implement regular expression matching with support for '.' and '*' where:

'.' Matches any single character.​​​​
'*' Matches zero or more of the preceding element.
The matching should cover the entire input string (not partial).

 

Example 1:

Input: s = "aa", p = "a"
Output: false
Explanation: "a" does not match the entire string "aa".
Example 2:

Input: s = "aa", p = "a*"
Output: true
Explanation: '*' means zero or more of the preceding element, 'a'. Therefore, by repeating 'a' once, it becomes "aa".
Example 3:

Input: s = "ab", p = ".*"
Output: true
Explanation: ".*" means "zero or more (*) of any character (.)".
 

Constraints:

1 <= s.length <= 20
1 <= p.length <= 30
s contains only lowercase English letters.
p contains only lowercase English letters, '.', and '*'.
It is guaranteed for each appearance of the character '*', there will be a previous valid character to match.
  
  ---------------- DP -----------------------------------------------
  /*
一旦遇到*通配符，前面的那个字符可以选择重复一次，可以重复多次，也可以一次都不出现，

dp[i][j] = dp[i][j-2] ; b, a* -> 一个不取
 = dp[i][j-1]: a, a* -> 取一个
 =dp[i-1][j]: a, a* -> 取多个
 
 
扩展：如果还要支持+。 思路和* 一样，只是
1） 当加号前的字符match时， 对比*少了一个都不取的情况
2） 当不match时（当 b, a+时），直接返回false

*/
class Solution {
    public boolean isMatch(String s, String p) {
        boolean[][] dp = new boolean[s.length()+1][p.length()+1];
        // 1, base case 
        dp[0][0] = true; 
        // dp[1][0] = false by default 
        // dp[0][1...p'th length] if p[j] == "*", 则取决于 p[j-1] 是否match s[i]
        for (int j = 1; j <= p.length(); j++) {
            if (p.charAt(j-1) == '*') {
                dp[0][j] = dp[0][j-2]; //  唯一可能匹配的情况就是把p当成空字符。s: "", p: "a*" match 
            }
        }
        
        for (int i = 1; i <= s.length(); i++) {
            for (int j = 1; j <= p.length(); j++) {
                char curP = p.charAt(j-1);
                // 1. 如果是字符或者点
                if (curP == s.charAt(i-1) || curP == '.') {
                    dp[i][j] = dp[i-1][j-1];
                }
                // 2. 如果是*, 先考虑和前一个字符是否match。 三种情况：match零次，一次，多次
                else if (curP == '*') {
                    // 2.1 前一个字符不match，那么只能把此*当成0次使用 s:b p:a*
                    if (p.charAt(j-2) != s.charAt(i-1) && p.charAt(j-2) != '.') {
                        dp[i][j] = dp[i][j-2]; 
                    }
                    // 2.2 前面的字符match了，再分三种情况：match零次，一次，多次
                    else {
                        dp[i][j] = dp[i][j-2] || // 这个*算零次     s: "", p: .*
                                   dp[i][j-1] ||  // 算match 1次： s: a, p: a*
                                   dp[i-1][j]; // match多次: 和当前的i匹配了，查查和i-1是否匹配
                                   
                    }
                }
                // 加号
                else if (p.charAt(j-1) == '+') { 
                    if (p.charAt(j-2) != s.charAt(i-1) && p.charAt(j-2) != '.') {
                        dp[i][j] = false; // c, a+
                    }
                    else {
                         dp[i][j] =  dp[i][j-1] || dp[i-1][j];    // 相比*，少了一个都不取的情况
                    }
                }
            }
        }
        return dp[s.length()][p.length()];     
    }
        
   
}
