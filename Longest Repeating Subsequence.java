
Given a sequence, find the length of its longest repeating subsequence (LRS). A repeating subsequence will be the one that appears at least twice in the original sequence and is not overlapping (i.e. none of the corresponding characters in the repeating subsequences have the same index).

Example 1:

Input: “t o m o r r o w”
Output: 2
Explanation: The longest repeating subsequence is “or” {tomorrow}.

Example 2:

Input: “a a b d b c e c”
Output: 3
Explanation: The longest repeating subsequence is “a b c” {a a b d b c e c}.

Example 3:

Input: “f m f f”
Output: 2
Explanation: The longest repeating subsequence is “f f” {f m f f, f m f f}. Please note the second last character is shared in LRS.

---------------------------DP ------------------------------------------------------------------------
/*
SOLUTION: 
和 longest common subsequence 几乎一样 https://leetcode.com/problems/longest-common-subsequence/ 
Since we want to match all the subsequences of the given two strings, we can use a two-dimensional array to store our results. The lengths of the two strings will define the size of the array’s two dimensions. So for every index ‘i’ in string ‘s1’ and ‘j’ in string ‘s2’, we will choose one of the following two options:

If the character s1[i] matches s2[j], the length of the common subsequence would be one plus the length of the common subsequence till the i-1 and j-1 indexes in the two respective strings.
If the character s1[i] does not match s2[j], we will take the longest subsequence by either skipping ith or jth character from the respective strings.

if s1[i] == s2[j] 
  dp[i][j] = 1 + dp[i-1][j-1]
else 
  dp[i][j] = max(dp[i-1][j], dp[i][j-1])
*/

public class LRS {
    public int findLRSLength(String str) {
        int[][] dp = new int[2][str.length()+1];
        for (int i = 1; i <= str.length(); i++) {
            for (int j = 1; j <= str.length(); j++) {
                if (i != j && str.charAt(i-1) == str.charAt(j-1)) {
                    dp[i%2][j] = 1 + dp[(i-1)%2][j-1];
                }
                else {
                    dp[i%2][j] = Math.max(dp[(i-1)%2][j], dp[i%2][j-1]);
                }
            }
        }
        return dp[str.length()%2][str.length()];
  }

  public static void main(String[] args) {
    LRS lrs = new LRS();
    System.out.println(lrs.findLRSLength("tomorrow"));
    System.out.println(lrs.findLRSLength("aabdbcec"));
    System.out.println(lrs.findLRSLength("fmff"));
  }
}

