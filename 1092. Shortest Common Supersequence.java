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

 
 -----------Brute Force -------------------------------------------
 The problem is quite similar to the Longest Common Subsequence.

A basic brute-force solution could be to try all the super-sequences of the given sequences. We can process both of the sequences one character at a time, so at any step, 
we must choose between:

If the sequences have a matching character, we can skip one character from both the sequences and make a recursive call for the remaining lengths to get SCS.
If the strings don’t match, we start two new recursive calls by skipping one character separately from each string. The minimum of these two recursive calls will have our answer.
 
The time complexity of the above algorithm is exponential O(2^{n+m}), where ‘n’ and ‘m’ are the lengths of the input sequences. The space complexity is O(n+m)
which is used to store the recursion stack. 
 class SCS {

  public int findSCSLength(String s1, String s2) {
      return findSCSLengthRecursive(s1, s2, 0, 0);
  }

  private int findSCSLengthRecursive(String s1, String s2, int i1, int i2) {
    // if we have reached the end of a string, return the remaining length of the other string, 
    // as in this case we have to take all of the remaining other string
    if(i1 == s1.length())
      return s2.length()-i2;
    if(i2 == s2.length())
      return s1.length()-i1;

    if(s1.charAt(i1) == s2.charAt(i2))
      return 1 + findSCSLengthRecursive(s1, s2, i1+1, i2+1);

    int length1 = 1 + findSCSLengthRecursive(s1, s2, i1, i2+1);
    int length2 = 1 + findSCSLengthRecursive(s1, s2, i1+1, i2);

    return Math.min(length1, length2);
  }

  public static void main(String[] args) {
    SCS scs = new SCS();
    System.out.println(scs.findSCSLength("abcf", "bdcf"));
    System.out.println(scs.findSCSLength("dynamic", "programming"));
  }
}
 
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
