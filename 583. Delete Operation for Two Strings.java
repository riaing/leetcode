Given two strings word1 and word2, return the minimum number of steps required to make word1 and word2 the same.

In one step, you can delete exactly one character in either string.

 

Example 1:

Input: word1 = "sea", word2 = "eat"
Output: 2
Explanation: You need one step to make "sea" to "ea" and another step to make "eat" to "ea".
Example 2:

Input: word1 = "leetcode", word2 = "etco"
Output: 4
 

Constraints:

1 <= word1.length, word2.length <= 500
word1 and word2 consist of only lowercase English letters.

-------                         4.4.2021 DP --------------------------------------------------------------
/*
dp[i][j] 以i j个char结尾的string的min steps =>注意这里初始化用了【length+1】，来使得初始值简单。

dp[i][j] = min{dp[i-1][j], dp[i][j-1]} + 1 => 删stringA 还是删StringB，删除操作就得加1 
          && then = min(dp[i][j], dp[i-1][j-1]) if char i == char j =>如果i,j相同，还得和i-1 j-1比一比
*/
class Solution {
    public int minDistance(String word1, String word2) {
        int[][] dp = new int[word1.length()+1][word2.length()+1];
        dp[0][0] = 0;
        for (int i = 1; i <= word1.length(); i++) {
            dp[i][0] = i;
        }
          for (int i = 1; i <= word2.length(); i++) {
            dp[0][i] = i;
        }
        
        for (int i = 1; i <= word1.length(); i++) {
            for (int j = 1; j <= word2.length(); j++) {
              
                if (word1.charAt(i-1) == word2.charAt(j-1)) {
                    dp[i][j] = dp[i-1][j-1];
                }
                else {
                      dp[i][j] = Math.min(dp[i-1][j], dp[i][j-1]) + 1;
                }
            }
        }
        return dp[word1.length()][word2.length()];
    }
}
-------------------------------------------DP,类似Edit Distance----------------------------------------------------
/**
m[i][j] : min deletion 对于string1的前i个字符和string2的前j个字符
m[i][j] = min{m[i-1][j]+1, m[i][j-1]+1, m[i-1][j-1]}, if i==j
          min{m[i-1][j]+1, m[i][j-1]+1}, if i!= j 
          
m[0][0...length] = j
m[0...length][0] = i

return m[length][length]
*/
class Solution {
    public int minDistance(String word1, String word2) {
        int[][] m = new int[word1.length()+1][word2.length()+1];
        
        // 从空到word2的前j个要几次操作
        for (int j = 0; j <= word2.length(); j++) {
            m[0][j] = j;
        }
        for (int i = 0; i <= word1.length(); i++) {
            m[i][0] = i;
        }
        
        for (int i = 1; i <= word1.length(); i++) {
            for (int j = 1; j <= word2.length(); j++) {
               m[i][j] = Math.min(m[i-1][j], m[i][j-1]) + 1;
               
                if (word1.charAt(i-1) == word2.charAt(j-1)) {
                    m[i][j] = Math.min(m[i][j], m[i-1][j-1]);
                }
              
            }
        }
        return m[word1.length()][word2.length()];
    }
}

-------------------方法二：找到Longest common Subsequence，将A,B与LCS的差加起来---------------------------------------
class Solution {
    public int minDistance(String A, String B) {
       int lcs = longestCommonSubsequence(A, B);
        System.out.println(lcs);
        return A.length() - lcs + B.length() - lcs;
    }
    
    private int longestCommonSubsequence(String A, String B) {
        int[][] m = new int[2][B.length()+1];
        
        int oldrow = 0; 
        for (int i = 1; i <= A.length(); i++) {
            int newRow = 1 - oldrow;
            for (int j = 1; j <= B.length(); j++) {
                if (A.charAt(i-1) == B.charAt(j-1)) {
                    m[newRow][j] = m[oldrow][j-1] + 1;
                }
                else {
                     m[newRow][j] = Math.max(m[newRow][j-1], m[oldrow][j]);
                }
            }
            oldrow = newRow;
        }
        return m[oldrow][B.length()];
    }
}
