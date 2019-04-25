Given two strings, find the longest common subsequence (LCS).

Your code should return the length of LCS.

Example
Example 1:
	Input:  "ABCD" and "EDCA"
	Output:  1
	
	Explanation:
	LCS is 'A' or  'D' or 'C'


Example 2:
	Input: "ABCD" and "EACB"
	Output:  2
	
	Explanation: 
	LCS is "AC"
Clarification
What's the definition of Longest Common Subsequence?

https://en.wikipedia.org/wiki/Longest_common_subsequence_problem
http://baike.baidu.com/view/2020307.htm
------------------DP 公式---------------------------
	
m[i][j] : 在a中以第i个字符结尾，在b中以第j个字符结尾的LCS
m[i][j] = m[i-1][j-1] if i==j ||
	max{m[i][j-1], m[i-1][j]} if i!=j
start: m[0][0...length] = 0
	m[0...length][0] = 0
end: m[a.length][b.length]


-----------------------DP----------------------------------------------------------------------------------------------
public class Solution {
    /**
     * @param A: A string
     * @param B: A string
     * @return: The length of longest common subsequence of A and B
     */
    public int longestCommonSubsequence(String A, String B) {
        int[][] m = new int[A.length()+1][B.length()+1];
        
        for (int i = 1; i <= A.length(); i++) {
            for (int j = 1; j <= B.length(); j++) {
                if (A.charAt(i-1) == B.charAt(j-1)) {
                    m[i][j] = m[i-1][j-1] + 1;
                }
                else {
                     m[i][j] = Math.max(m[i][j-1], m[i-1][j]);
                }
            }
        }
        return m[A.length()][B.length()];
    }
}

--------------DP with save space ----------------------------------------------------------------------------
	
因为只和i-1上一行相关，所以用rotational array的方法
public class Solution {
    /**
     * @param A: A string
     * @param B: A string
     * @return: The length of longest common subsequence of A and B
     */
    public int longestCommonSubsequence(String A, String B) {
        int[][] m = new int[2][B.length()+1];
        
        int oldrow = 0; 
        for (int i = 1; i <= A.length(); i++) {
            int newRow = 1 - oldrow;
            for (int j = 1; j <= B.length(); j++) {
                if (A.charAt(i-1) == B.charAt(j-1)) {
                    m[newRow][j] = m[oldrow][j-1] + 1;
                }
                else {å
                     m[newRow][j] = Math.max(m[newRow][j-1], m[oldrow][j]);
                }
            }
            oldrow = newRow;
        }
        return m[oldrow][B.length()];
    }
}
