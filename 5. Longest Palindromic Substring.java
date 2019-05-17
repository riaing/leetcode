找到任意两点是不是pal。
m[i][j] = m[i+1][j-1] && a[i] == a[j], where i >= j
注意：matrix的左上到右下为分界线，所有下面的点（i >= j)都设为true
i要从后往前
time: o(n^2）
-----------------------------------------------------------
class Solution {
    public String longestPalindrome(String s) {
        if (s == null || s.length() <= 1) {
            return s;
        }
        boolean[][] m = new boolean[s.length()][s.length()];
        // preprocessing 
        for (int i = 0; i < s.length(); i++) {
            for (int j = 0; j <= i; j++) {
                m[i][j] = true;
            }
        }
        
        int maxLen = 0;
        String val = s.charAt(0)+""; //必须要给个值
        //注意这里i从后往前走
        for (int i = s.length()-2; i >= 0; i--) {
            for (int j = i+1; j < s.length(); j++) { //注意这里是j+1,因为我们的特殊情况cover了i==j的case
                m[i][j] = m[i+1][j-1] && s.charAt(i) == s.charAt(j);
             
                // update result 
                if (m[i][j] && j-i > maxLen) {
                    maxLen = j-i;
                    val = s.substring(i, j+1);
                } 
            }
        }
        return val; 
        
    }
}

----------------------确定base case的思路二，感觉这个更清楚
m[i][i] =true
m[i][i+1] = s[i][i+1]
所以我们也可以把限定条件写在code中
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
