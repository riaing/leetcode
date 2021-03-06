Given two strings, find the longest common substring.

1. Return the length of it. 2. return the lCS

Example
Example 1:
	Input:  "ABCD" and "CBCE"
	Output:  2
	
	Explanation:
	Longest common substring is "BC"


Example 2:
	Input: "ABCD" and "EACB"
	Output:  1
	
	Explanation: 
	Longest common substring is 'A' or 'C' or 'B'
Challenge
O(n x m) time and memory.

Notice
The characters in substring should occur continuously in original string. This is different with subsequence.
		
----------- 4.3.2021 DP思路一样，learning是print结果时，用一个变量记录lcs的index，在dp过程中更新此值--------------------------------------
	

public class Solution {
    /**
     * @param A: A string
     * @param B: A string
     * @return: the length of the longest common substring.
     */
    public int longestCommonSubstring(String A, String B) {
        // dp[i][j] => A的第i个字符以及B的第j个字符结尾的LCS 
        int[][] dp = new int[A.length()+1][B.length()+1];
        
        int lcs = 0;
        int endIndex = 0; // 用一个变量记录最后的index，以便接下来通过lcs和此index print出lcs
        for (int i = 1; i <= A.length(); i++) {
   
            for (int j = 1; j <= B.length(); j++) {
                if (A.charAt(i-1) == B.charAt(j-1)) {//i,j代表第几个字符，所以index时要减1
                    dp[i][j] = dp[i-1][j-1] + 1;
                    // 这一步用于print最后的valude
                    if (lcs < dp[i][j]) {
                        lcs = dp[i][j];
                        endIndex = i - 1; // 因为要的是index所以减1 
                    }
                }
                else {
                    dp[i][j] = 0;
                }
               
            }
           
        }
        // get the value of LCS, 通过上面的lcs endIndex和长度来找 
        int startIndex = endIndex - lcs + 1;
        String theLCSValue = A.substring(startIndex, endIndex+1);
        System.out.println(theLCSValue);
        return lcs; 
    }
}
		
------------------------DP 思路---------------------------------------------------------

    m[i][j] : 在a中以第i个字符结尾，在b中以第j个字符结尾的LCS
    m[i][j] = m[i-1][j-1] if i==j || 0 if i!=j
    start: m[0][0...length] = 0
	          m[0...length][0] = 0
    end: max{m[0..a.length][0..b.length]} 需要搜索全局

------------------------------DP --------------------------------------------------------------------------------------
public class Solution {
    /**
     * @param A: A string
     * @param B: A string
     * @return: the length of the longest common substring.
     */
     

    public int longestCommonSubstring(String A, String B) {
        int[][] m  = new int[A.length()+1][B.length()+1]; // process 0, so one more space 
        // start: m[0..A.length()][0] = 0 
        //      m[0][0...B.length()] = 0
        
        // 因为m[i]表示第i个字符，所以这里从1开始
         for (int i = 1; i <= A.length(); i++) {
            for (int j = 1; j <= B.length(); j++) {
                if (A.charAt(i-1) == B.charAt(j-1)) {
                    m[i][j] = m[i-1][j-1] + 1;
                }
                else {
                     m[i][j] = 0;
                }
            }
        }
        
        // 搜索所有的结果
        int max = 0;
        for (int i = 1; i <= A.length(); i++) {
            for (int j = 1; j <= B.length(); j++) {
                max = Math.max(max, m[i][j]);
            }
        }
        return max;
    }
}
------------------------ DP + rotational array 优化space--------------------------------------------------------------
public class Solution {
    /**
     * @param A: A string
     * @param B: A string
     * @return: the length of the longest common substring.
     */
     
     /*
     m[i][j] : 在a中以第i个字符结尾，在b中以第j个字符结尾的LCS
    m[i][j] = m[i-1][j-1] if i==j || 0 if i!=j
    start: m[0][0...length] = 0
	m[0...length][0] = 0
    end: max{m[0..a.length][0..b.length]} 需要搜索全局
     */
    public int longestCommonSubstring(String A, String B) {
        int[][] m  = new int[2][B.length()+1]; // process 0, so one more space 
       
        int max = 0; 
        int oldRow = 0;
         for (int i = 1; i <= A.length(); i++) {
             int newRow = 1 - oldRow;
            for (int j = 1; j <= B.length(); j++) {
                if (A.charAt(i-1) == B.charAt(j-1)) {
                    m[newRow][j] = m[oldRow][j-1] + 1;
                    max = Math.max(max, m[newRow][j]);
                }
                else {
                     m[newRow][j] = 0;
                }
            }
            oldRow = newRow;
        }
        
        return max;
    }
}
