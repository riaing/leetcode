Given a string s, return the number of palindromic substrings in it.

A string is a palindrome when it reads the same backward as forward.

A substring is a contiguous sequence of characters within the string.

 

Example 1:

Input: s = "abc"
Output: 3
Explanation: Three palindromic strings: "a", "b", "c".
Example 2:

Input: s = "aaa"
Output: 6
Explanation: Six palindromic strings: "a", "a", "a", "aa", "aa", "aaa".
 

Constraints:

1 <= s.length <= 1000
s consists of lowercase English letters.
-------------------------------------- dp ----------------------------------------

/*
https://leetcode.com/problems/longest-palindromic-substring/ 
*/
class Solution {
    public int countSubstrings(String s) {
        // dp[i][j] will be 'true' if the string from index 'i' to index 'j' is a palindrome
        int cnt = 0;
        boolean[][] dp = new boolean[s.length()][s.length()];
        for (int i = 0; i < s.length(); i++) {
            dp[i][i] = true;
            cnt++;
        }
        
        for (int i = s.length() - 2; i >= 0; i--) {
            for (int j = i+1; j < s.length(); j++) {
                if (s.charAt(i) == s.charAt(j) && (dp[i+1][j-1] || j-i == 1))  { //j-i == 1是为了保证 i+1. j-1永远 valid， 否则会出现 j 在 i 前
                    dp[i][j] = true;
                    cnt++;
                }
            }
        }
        return cnt;
    }
}
