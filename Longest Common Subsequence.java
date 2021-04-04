Given two strings, find the longest common subsequence (LCS).

Your code should return the length of LCS 

Example
Example 1:
	Input:  ABCD and EDCA
	Output:  1
	
	Explanation:
	LCS is A or D or C


Example 2:
	Input: ABCD and EACB
	Output:  2
	
	Explanation: 
	LCS is AC
Clarification
What‘s the definition of Longest Common Subsequence?

https://en.wikipedia.org/wiki/Longest_common_subsequence_problem
http://baike.baidu.com/view/2020307.htm
------------------DP 公式---------------------------
	
m[i][j] : 在a中以前i个字符，在b中以前j个字符的LCS
m[i][j] = m[i-1][j-1] if i==j ||
	max{m[i][j-1], m[i-1][j]} if i!=j
start: m[0][0...length] = 0
	m[0...length][0] = 0
end: m[a.length][b.length]

------------------------------  4.3.2021 DP + print具体值 ---------------------------------------------------------------------
class Solution {
    public int longestCommonSubsequence(String A, String B) {
        int[][] m = new int[A.length()+1][B.length()+1];
        
        List<Integer> indexInA = new ArrayList<Integer>();
        int lcsLength = 0; 
        for (int i = 1; i <= A.length(); i++) {
            for (int j = 1; j <= B.length(); j++) {
                if (A.charAt(i-1) == B.charAt(j-1)) {
                    m[i][j] = m[i-1][j-1] + 1;
                }
                else {
                     m[i][j] = Math.max(m[i][j-1], m[i-1][j]);
                }
                // 更新最长值和记录index以便之后print结果
                if (lcsLength < m[i][j]) {
                    lcsLength = m[i][j];
                    // 如果这时候A == B了，说明lcs中会包括目前的char
                    if (A.charAt(i-1) == B.charAt(j-1)) {
                        indexInA.add(i-1);
                    }
                }
            }
        }
        
        // backtrack 来print lcs的value
        String value = "";
        for (int i : indexInA) {
            value = value + A.charAt(i);
        }
        // System.out.println(value);
        return lcsLength;
        // return m[A.length()][B.length()]; //两种return方法都行
    }
}	

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
------------------follow up:记录任意一条最长的sub seq：用track array to record last visited path!-----------------------------------------
eg: Input: "ABCD" and "EACB", return AB or AC
public class Solution {
    /**
     * @param A: A string
     * @param B: A string
     * @return: The length of longest common subsequence of A and B
     */
    public int longestCommonSubsequence(String A, String B) {
        int[][] m = new int[2][B.length()+1];
	    // 记录当前点是从哪来 
        int[][][] trackLastPath = new int[A.length()+1][B.length()+1][2];
        int oldrow = 0; 
        for (int i = 1; i <= A.length(); i++) {
            int newRow = 1 - oldrow;
            for (int j = 1; j <= B.length(); j++) {
                if (A.charAt(i-1) == B.charAt(j-1)) {
                    m[newRow][j] = m[oldrow][j-1] + 1;
                    trackLastPath[i][j][0] = i-1;
                    trackLastPath[i][j][1] = j-1;
                }
                else {
                     m[newRow][j] = Math.max(m[newRow][j-1], m[oldrow][j]);
                     if ( m[newRow][j-1] >  m[oldrow][j] ) {
                         trackLastPath[i][j][0] = i;
                         trackLastPath[i][j][1] = j-1;
                     }
                     else {
                         trackLastPath[i][j][0] = i-1;
                         trackLastPath[i][j][1] = j;
                     }
                     
                }
            }
            oldrow = newRow;
        }
	    // 重点！backtrack到上一个点，看看当走过的两个点一样时，put into result -------------------------------
        int Aindex = A.length();
        int Bindex = B.length();
        String res = "";
        // 注意这里的track[0][0] is invalid
        while (Aindex >= 1 && Bindex >= 1) {
            if (A.charAt(Aindex-1) == B.charAt(Bindex-1)) {
                res = A.charAt(Aindex-1) + res;
            }
            int oldAindex = Aindex;
            System.out.println("oldA " + oldAindex);
            System.out.println("oldB " + Bindex);
            Aindex = trackLastPath[oldAindex][Bindex][0];
            Bindex = trackLastPath[oldAindex][Bindex][1];
            
            System.out.println("newA " + Aindex);
            System.out.println("newB " + Bindex);
            
        }
	// res就是最长的sub seq    
        System.out.println(res);        
        return m[oldrow][B.length()];
    }
}
