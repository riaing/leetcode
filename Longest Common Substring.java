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
		
-------- basic solution -------------------------

A basic brute-force solution could be to try all substrings of ‘s1’ and ‘s2’ to find the longest common one. We can start matching both the strings one character at a time, so we have two options at any step:

If the strings have a matching character, we can recursively match for the remaining lengths and keep a track of the current matching length.
If the strings don’t match, we start two new recursive calls by skipping one character separately from each string and reset the matching length.
The length of the Longest Common Substring (LCS) will be the maximum number returned by the three recurse calls in the above two options.
		
class LCS {

  public int findLCSLength(String s1, String s2) {
      return findLCSLengthRecursive(s1, s2, 0, 0, 0);
  }

  private int findLCSLengthRecursive(String s1, String s2, int i1, int i2, int count) {
    if(i1 == s1.length() || i2 == s2.length())
      return count;

    if(s1.charAt(i1) == s2.charAt(i2))
      count = findLCSLengthRecursive(s1, s2, i1+1, i2+1, count+1);

    int c1 = findLCSLengthRecursive(s1, s2, i1, i2+1, 0);
    int c2 = findLCSLengthRecursive(s1, s2, i1+1, i2, 0);

    return Math.max(count, Math.max(c1, c2));
  }

  public static void main(String[] args) {
    LCS lcs = new LCS();
    System.out.println(lcs.findLCSLength("abdca", "cbda"));
    System.out.println(lcs.findLCSLength("passport", "ppsspt"));
  }
}

-------------------3.5.2022 rotation array ---------------------------------------------------------------------------
class LCS {

  public int findLCSLength(String s1, String s2) {
    //dp[i][j]: 已 i 结尾的 s1，和已 j 结尾的 s2的 LCS 
    // dp[0][j] = 1 if s1(0) == s2(j),  dp[i][0] = 1 if s1(i) == s2(0)
    // return maxNum among dp[i][j]
    int[][] dp = new int[2][s2.length()];

    for (int j = 0; j < s2.length(); j++) {
      if (s2.charAt(j) == s1.charAt(0)) {
        dp[0][j] = 1; 
      }
    }

    int lsc = 0;
    for (int i = 1; i < s1.length(); i++) {
      if (s1.charAt(i) == s2.charAt(0)) {
        dp[i%2][0] = 1; 
      }
      for (int j = 1; j < s2.length(); j++) {
        dp[i%2][j] = 0; //清空上一行！！
        if (s1.charAt(i) == s2.charAt(j)) {
          int curLen = 1 + dp[(i-1)%2][j-1];
          dp[i%2][j] = curLen;
          lsc = Math.max(lsc, curLen);
        }
      }
    }

    return lsc;
  }

  public static void main(String[] args) {
    LCS lcs = new LCS();
    System.out.println(lcs.findLCSLength("abdca", "cbda"));
    System.out.println(lcs.findLCSLength("passport", "ppsspt"));
  }
}
	
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
