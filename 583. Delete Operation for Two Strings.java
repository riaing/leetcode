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
