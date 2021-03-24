Given a string s, partition s such that every substring of the partition is a palindrome.

Return the minimum cuts needed for a palindrome partitioning of s.

Example:

Input: "aab"
Output: 1
Explanation: The palindrome partitioning ["aa","b"] could be produced using 1 cut.
    
    
 ------------ DP 常规想法，二维 --------------------------------------------------   
    /*
DP[i][j] = 以i, j 结尾的string 最少cut几次
初始化：长度为1的string cut为0 -> dp[i][i] = 0
dp[i][j] = 0, if string(i,j) is palindrome 
           min(dp[i][k], dp[k+1][j]) + 1, if string(i,j) is NOT palindrome
           就是分成两段，找出substring最小cut, 加上分割string的一次cut 
return dp[0][length - 1]

优化： 可以优化成一维DP！ 因为分成两段时，第二段的min cut肯定是第二段为回文时。 
所以可以变成如果第二段有回文时，min cut就等于 第一段的最短回文 + 1， 所以dp只要固定第一段就行
那么DP[j] = 从0开始到j结尾的string，最少cut几次
初始化：dp[0] = 0;
dp[j] = if string(0, j) is palindrome: 0
        if string(0, j) is NOT palindrome: 
            for  0<=k<=j
            if string(k+1, j) is palindrome: min(dp[0][k]) + 1
            if not: 说明 0->j之间切不出来，所以就得切 string(0,i) -1 次
            解法见下

*/
class Solution {
    public int minCut(String s) {
        boolean[][] isPalindrome = isPalindrome(s);
        
        
        int[][] dp = new int[s.length()][s.length()];
        // initialization 所有长度为1的string都不用cut
        for (int i = 0; i < s.length(); i++) {
            dp[i][i] = 0;
        }
        
        for (int len = 2; len <= s.length(); len++) {
            // 带不带等号：带个长度为3的string走一遍就知道了
            for (int i = 0; i + len <= s.length(); i++) {
                int end = i + len - 1; 
                int min = Integer.MAX_VALUE;
                if (isPalindrome[i][end]) {
                    min = 0;
                }
                for (int k = i; k < end; k++) {
                    int subMinCut = dp[i][k] + dp[k+1][end] + 1;
                    min = Math.min(min, subMinCut); 
                }
                dp[i][end] = min;
            }
        }
        return dp[0][s.length() -1];
    }
    
        
    private boolean[][] isPalindrome(String s) {
        boolean[][] dp = new boolean[s.length()][s.length()];
        
        // initilization : 单个字母必定是回文
        for (int i = 0; i < s.length(); i++) {
            dp[i][i] = true;
        }
        //这题要注意的就是for循环的设置，因为 dp[i][j] = dp[i+1][j-1] && char(i) == char(j), 走几个例子可知，长度为2的string depend on 长度为1的string，3的depend on 2的， 以此类推。所以for loop实际上是要先知道最短的string，然后慢慢展开。所以我们先找到长度为1的string，再去求长度为2的string。例子：cbbd -> 先求出cb, bb, dd -> cbb, bbd -> cbbd 
        for (int len = 1; len <= s.length(); len++) {
            // 起点从0开始
            for (int i = 0; i + len < s.length(); i++) {
                int j = i + len;
                dp[i][j] = s.charAt(i) == s.charAt(j);
                if (i + 1 < j - 1) {
                    dp[i][j] = dp[i+1][j-1] && dp[i][j];
                }   
            }
        }
        return dp;
    }
}

-------------------------------------------DP 优化成一维解法 --------------------------------------------------------------------------------
class Solution {  
    public int minCut(String s) { //O(N^2)
        int[] res = new int[s.length()];
         boolean[][] pal = isPal(s);
        
        res[0] = 0;
        for (int i = 1; i < s.length(); i++) {
            if (pal[0][i]) {
                res[i] = 0;
            }
            else {
                int minCut = s.length();
                for (int j = 0; j < i; j++) {
                    if (pal[j+1][i]) {
                        minCut = Math.min(minCut, res[j] + 1);
                    }   
                }
            res[i] = minCut; 
            }
        }
        return res[s.length()-1];
        
    }
    
    // 对于i开头，j结尾的string，dp写法：m[i,j] = m[i+1, j-1] && s[i] == s[j]; 
    // special case: 因为必须满足i<j，所以考虑当i== j时，i+1 ==j时，是否满足special case。
    // m[i,i] = true, m[i, i+1] = s[i] == s[i+1]
    // save all pairs i,j in string s that s.substring(i, j+1) is pal
    private  boolean[][] isPal(String s) { // o(N^2)
        boolean[][] pal = new boolean[s.length()][s.length()];
        for (int i = s.length() - 1; i>=0; i--) {
            for (int j = i; j < s.length(); j++) {
                // if (i == j) {
                //     pal[i][j] = true;
                // }
                // else if (j == i+1) {
                //     pal[i][j] = s.charAt(i) == s.charAt(j);
                // }
                // else {
                //    pal[i][j] = pal[i+1][j-1] && s.charAt(i) == s.charAt(j);
                // }
                // 简化以上写法
                if (j > i+1) {
                    pal[i][j] = pal[i+1][j-1];
                }
                else {
                     pal[i][j] = true;
                }
                pal[i][j] &= s.charAt(i) == s.charAt(j);
            }
        }
        return pal;
    }
}
