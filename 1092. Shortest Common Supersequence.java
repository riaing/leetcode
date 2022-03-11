Given two strings str1 and str2, return the shortest string that has both str1 and str2 as subsequences. If there are multiple valid strings, return any of them.

A string s is a subsequence of string t if deleting some number of characters from t (possibly 0) results in the string s.

 

Example 1:

Input: str1 = "abac", str2 = "cab"
Output: "cabac"
Explanation: 
str1 = "abac" is a subsequence of "cabac" because we can delete the first "c".
str2 = "cab" is a subsequence of "cabac" because we can delete the last "ac".
The answer provided is the shortest such string that satisfies these properties.
Example 2:

Input: str1 = "aaaaaaaa", str2 = "aaaaaaaa"
Output: "aaaaaaaa"
 

Constraints:

1 <= str1.length, str2.length <= 1000
str1 and str2 consist of lowercase English letters.

-----------------------------------------------—DP + find path ---------------------------

class Solution {
    public String shortestCommonSupersequence(String str1, String str2) {
        int[][] dp = new int[str1.length()+1][str2.length()+1];
        int[][] from = new int[str1.length()+1][str2.length()+1]; // 1-> i==j; 2-> i-1; 3-> j-1
        for (int i = 1; i <= str1.length(); i++) {
            dp[i][0] = i;
            from[i][0] = 2;
        }
        
        for (int j = 1; j <= str2.length(); j++) {
            dp[0][j] = j;
            from[0][j] = 3;
        }
        
        for (int i = 1; i <= str1.length(); i++) {
            for (int j = 1; j <= str2.length(); j++) {
                if (str1.charAt(i-1) == str2.charAt(j-1)) {
                    dp[i][j] = 1 + dp[i-1][j-1];
                    from[i][j] = 1; 
                }
                else {
                    dp[i][j] = 1 + Math.min(dp[i-1][j], dp[i][j-1]);
                    if (dp[i-1][j] < dp[i][j-1]) {
                        from[i][j] = 2;
                    }
                    else {
                        from[i][j] = 3; 
                    }
                }
                  
            }
        }
        
        String scs = "";
        int s1Index = str1.length();
        int s2Index = str2.length(); 
        while (s1Index >= 0 && s2Index >= 0 && from[s1Index][s2Index] != 0) {
            if (from[s1Index][s2Index] == 1) {
                scs = str1.charAt(s1Index-1) + scs;
                s1Index--;
                s2Index--;
            }
            else if (from[s1Index][s2Index] == 2) {
                scs = str1.charAt(s1Index-1) + scs;
                s1Index--;
            }
            else if (from[s1Index][s2Index] == 3) {
                scs = str2.charAt(s2Index-1) + scs;
                s2Index--;
            }
        }
        

        // System.out.println(dp[str1.length()][str2.length()]);
        return scs;
    }
}
