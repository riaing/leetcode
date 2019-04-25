Given a string S and a string T, count the number of distinct subsequences of S which equals T.

A subsequence of a string is a new string which is formed from the original string by deleting some (can be none) of the characters without disturbing the relative positions of the remaining characters. (ie, "ACE" is a subsequence of "ABCDE" while "AEC" is not).

Example 1:

Input: S = "rabbbit", T = "rabbit"
Output: 3
Explanation:

As shown below, there are 3 ways you can generate "rabbit" from S.
(The caret symbol ^ means the chosen letters)

rabbbit
^^^^ ^^
rabbbit
^^ ^^^^
rabbbit
^^^ ^^^
Example 2:

Input: S = "babgbag", T = "bag"
Output: 5
Explanation:

As shown below, there are 5 ways you can generate "bag" from S.
(The caret symbol ^ means the chosen letters)

babgbag
^^ ^
babgbag
^^    ^
babgbag
^    ^^
babgbag
  ^  ^^
babgbag
    ^^^
------------------------------------------------------------------------------------------
/**
m[i][j]: T中的前j个字符（包括j）在S中前i的count

m[i][j] = m[i-1][j-1] + m[i-1][j], if i== j
          m[i-1][j], if i != j
start: m[0][0] = 1;
        m[0][0...length] = 0
        m[0...length][0] = 1
end: m[length][length] 
*/
class Solution {
    public int numDistinct(String s, String t) {
        int[][] m = new int[s.length()+1][t.length()+1];
          for (int j = 1; j <= t.length(); j++) {
            m[0][j] = 0;
        }
        // 必须将m[0][0] = 1; 
          for (int i = 0; i <= s.length(); i++) {
            m[i][0] = 1;
        }
        
        for (int i = 1; i <= s.length(); i++) {
            for (int j = 1; j <= t.length(); j++) {
                if (s.charAt(i-1) == t.charAt(j-1)) {
                    m[i][j] = m[i-1][j-1] + m[i-1][j];
                }
                else {
                    m[i][j] = m[i-1][j];
                }
            }
        }
        return m[s.length()][t.length()];
    }
}
