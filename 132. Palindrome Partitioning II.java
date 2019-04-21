Given a string s, partition s such that every substring of the partition is a palindrome.

Return the minimum cuts needed for a palindrome partitioning of s.

Example:

Input: "aab"
Output: 1
Explanation: The palindrome partitioning ["aa","b"] could be produced using 1 cut.

-------------------------------------------DP --------------------------------------------------------------------------------
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
