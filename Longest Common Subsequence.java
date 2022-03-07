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

---------------------- 2022.3.6 滚动数组 + backtrack print 结果 ---------------------------------
/*
if s1[i] == s2[j] 
  dp[i][j] = 1 + dp[i-1][j-1]
else 
  dp[i][j] = max(dp[i-1][j], dp[i][j-1])
    
*/
class Solution {
    public int longestCommonSubsequence(String s1, String s2) {
        int[][] dp = new int[2][s2.length()];
        // 1 -> i-1,j-1; 2 -> i-1, j; 3 -> i, j-1; 0 ->没有路径
        int[][] from = new int[s1.length()][s2.length()]; 

        for (int j = 0; j < s2.length(); j++) {
              if (s2.charAt(j) == s1.charAt(0)) {
                  dp[0][j] = 1; 
                  from[0][j] = 1;
              }
              else if (j> 0 && dp[0][j-1] == 1) {
                  dp[0][j] = 1; 
                  from[0][j] = 3;
              }
        }

        for (int i = 1; i < s1.length(); i++) {
           if (s1.charAt(i) == s2.charAt(0)) {
                 dp[i%2][0] = 1;
              from[i][0] = 1;
           }
            else if (dp[(i-1)%2][0] == 1) {
                dp[i%2][0] = 1;
                from[i][0] = 2;
            }
        
          for (int j = 1; j < s2.length(); j++) {
            dp[i%2][j] = Math.max(dp[(i-1)%2][j], dp[i%2][j-1]); //清空上一行！！
            from[i][j] = dp[(i-1)%2][j] > dp[i%2][j-1] ? 2 : 3;
            if (s1.charAt(i) == s2.charAt(j)) { 
              dp[i%2][j] = 1 + dp[(i-1)%2][j-1];
              from[i][j] = 1; 
                
            }
          }
        }
        
        String lsc = "";
        int s1Index = s1.length() - 1;
        int s2Index = s2.length() - 1; 
        while (s1Index >= 0 && s2Index >=0 && (from[s1Index][s2Index] != 0)) {
            if (from[s1Index][s2Index] == 1) {
                lsc = s1.charAt(s1Index) + lsc; 
                s1Index--;
                s2Index--;
            }
            else if (from[s1Index][s2Index] == 2) {
                s1Index--;
            }
            else if (from[s1Index][s2Index] == 3) {
                s2Index--;
            }
        }
            
        System.out.println(lsc);
        return dp[(s1.length()-1)%2][s2.length()-1];

    }
}
	
	

------------------------------  这方法不对！删除这段。
"abdca"
"cbda" 会 print ada 
	--------------4.3.2021 DP + print具体值 ---------------------------------------------------------------------
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
