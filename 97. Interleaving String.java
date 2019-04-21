Given s1, s2, s3, find whether s3 is formed by the interleaving of s1 and s2.

Example 1:

Input: s1 = "aabcc", s2 = "dbbca", s3 = "aadbbcbcac"
Output: true
Example 2:

Input: s1 = "aabcc", s2 = "dbbca", s3 = "aadbbbaccc"
Output: false

-----------------------DP --------------------------------------------------------------------------------------------
class Solution {
    public boolean isInterleave(String s1, String s2, String s3) {
        if (s1.length() + s2.length() != s3.length()) {
            return false;
        }
        boolean[][] isInterleave = new boolean[s1.length() + 1][s2.length() + 1];
        
        for(int i = 0; i<= s1.length(); i++) {
            for (int j = 0; j <= s2.length(); j++) {
                if (i == 0 && j == 0) {
                    isInterleave[i][j] = true;
                }
                else if (i == 0) {
                    isInterleave[i][j] = isInterleave[i][j-1] && s3.charAt(i+j-1) == s2.charAt(j-1);
                    
                }
                else if (j == 0) {
                    isInterleave[i][j] = isInterleave[i-1][j] && s3.charAt(i+j-1) == s1.charAt(i-1);
                }
                
                else {
                    isInterleave[i][j] = (s1.charAt(i-1) == s3.charAt(i+j-1) && isInterleave[i-1][j]) || (s2.charAt(j-1) == s3.charAt(i+j-1) && isInterleave[i][j-1]); 
                }
                   
                
            }
        }
        return isInterleave[s1.length()][s2.length()];
    }
}
----------------------DP， 合并一下上面的重复代码 -----------------------------------------------------------------------------
class Solution {
    public boolean isInterleave(String s1, String s2, String s3) {
        if (s1.length() + s2.length() != s3.length()) {
            return false;
        }
        boolean[][] isInterleave = new boolean[s1.length() + 1][s2.length() + 1];
        for(int i = 0; i<= s1.length(); i++) {
            for (int j = 0; j <= s2.length(); j++) {
                if (i == 0 && j == 0) {
                    isInterleave[i][j] = true;
                    continue;
                }
                if (i > 0) {
                    isInterleave[i][j] = isInterleave[i-1][j] && s3.charAt(i+j-1) == s1.charAt(i-1);
                }
                if (j > 0) {
                    isInterleave[i][j] |= (isInterleave[i][j-1] && s3.charAt(i+j-1) == s2.charAt(j-1));
                }
            }
        }
        return isInterleave[s1.length()][s2.length()];
    }
}
-----------------------DP， 发现每一行只depend on 上面一行，所以用一个2行的array来表示 ---------------------------------------------
/**
m[i,j]: 以 s1 i 结尾和以s2 j结尾是否可以构成S3以 i+j 结尾的interleaving string

m[i,j] = (m[i-1, j] && s1[i] == s3[i+j]) || (m[i, j-1] && s2[j] == s3[i+j]),
corner case 为 m[0,1...j]第一行以及m[0...i, j]第一列

m[0, j] = (m[0, j-1] && s2[j] == s3[i+j]
m[i, 0] = (m[i, 0] && s1[i] == s3[i+j]
*/


class Solution {
    public boolean isInterleave(String s1, String s2, String s3) {
        if (s1.length() + s2.length() != s3.length()) {
            return false;
        }
        
        boolean[][] isInterleave = new boolean[2][s2.length() + 1];
        isInterleave[0][0] = true;
        // 先initialize corner case-> 第0行，也就是这里的oldRow
        for (int j = 1; j <= s2.length(); j++) {
            isInterleave[0][j] = isInterleave[0][j - 1] && s3.charAt(j-1) == s2.charAt(j-1);
            // 优化1：当前一个已经是false时
            if (!isInterleave[0][j]) {
                break;
            }
        }
        
        // general case 
        int oldRow = 0;
        for(int i = 1; i<= s1.length(); i++) { // o(n)
            int newRow = 1 - oldRow;
            boolean foundTrue = false;
            for (int j = 0; j <= s2.length(); j++) { //o(m)
                isInterleave[newRow][j] = isInterleave[oldRow][j] && s3.charAt(i+j-1) == s1.charAt(i-1);
                if (j > 0) {// 这里就包括了base case第一列的情况
                    isInterleave[newRow][j] |= (isInterleave[newRow][j-1] && s3.charAt(i+j-1) == s2.charAt(j-1));
                }
                // 优化2，当上一行全是false时，推导：那么m[curRow][0]也是false（因为只depend on m[i-1, j]）,那么m[curRow][1]也是false（因为depend on m[i-1, j]， m[i, j-1]已经是false，有次类推这一行也都是false）
                if (isInterleave[newRow][j]) {
                    foundTrue = true;
                }
            }
            if (!foundTrue) {
                return false;
            }
            oldRow = newRow;
        }
        return isInterleave[oldRow][s2.length()];
    }
}

---------------------------------------------------------------------------------
