Given two words word1 and word2, find the minimum number of operations required to convert word1 to word2.

You have the following 3 operations permitted on a word:

Insert a character
Delete a character
Replace a character
Example 1:

Input: word1 = "horse", word2 = "ros"
Output: 3
Explanation: 
horse -> rorse (replace 'h' with 'r')
rorse -> rose (remove 'r')
rose -> ros (remove 'e')
Example 2:

Input: word1 = "intention", word2 = "execution"
Output: 5
Explanation: 
intention -> inention (remove 't')
inention -> enention (replace 'i' with 'e')
enention -> exention (replace 'n' with 'x')
exention -> exection (replace 'n' with 'c')
exection -> execution (insert 'u')

----------------------------DP ---------------------------------------------------------------------------------------
/**
思想见evernote笔记 Time: O(m*n)
*/

class Solution {
    public int minDistance(String word1, String word2) {
         int[][] m = new int[word1.length()+1][word2.length()+1];
        
        // 从空到word2的前j个要几次操作
        for (int j = 0; j <= word2.length(); j++) {
            m[0][j] = j;
        }
        for (int i = 0; i <= word1.length(); i++) {
            m[i][0] = i;
        }
        
        for (int i = 1; i <= word1.length(); i++) {
            for (int j = 1; j <= word2.length(); j++) {
               m[i][j] = Math.min(m[i-1][j], m[i][j-1]) + 1;
               
                if (word1.charAt(i-1) == word2.charAt(j-1)) {
                    m[i][j] = Math.min(m[i][j], m[i-1][j-1]);
                }
                else {
                    m[i][j] = Math.min(m[i][j], m[i-1][j-1] + 1);
                }
            }
        }
        return m[word1.length()][word2.length()];
    }
}

----------------------DP with rotational array saving space -----------------------------------------------
/**
思想见evernote笔记 Time: O(m*n)， space（2*m）
*/

class Solution {
    public int minDistance(String word1, String word2) {
         int[][] m = new int[2][word2.length()+1];
        
        // 从空到word2的前j个要几次操作
        for (int j = 0; j <= word2.length(); j++) {
            m[0][j] = j;
        }
        
        // 初始化就放在了下面的loop里
        // for (int i = 0; i <2; i++) {
        //     m[i][0] = i;
        // }
        
        int oldRow = 0; 
        for (int i = 1; i <= word1.length(); i++) {
            int newRow = 1 - oldRow; 
            for (int j = 0; j <= word2.length(); j++) {
                //初始化
                if (j == 0) {
                    m[newRow][j] = i; 
                }
                else {
                    m[newRow][j] = Math.min(m[oldRow][j], m[newRow][j-1]) + 1;
                    if (word1.charAt(i-1) == word2.charAt(j-1)) {
                        m[newRow][j] = Math.min(m[newRow][j], m[oldRow][j-1]);
                    }
                    else {
                        m[newRow][j] = Math.min(m[newRow][j], m[oldRow][j-1] + 1);
                    }
                }
            }
            oldRow = newRow; 
        }
        return m[oldRow][word2.length()];
    }
}
---------------------2022.3.9 ------------------------------
    /*
dp[i][j] 前 i，j 个元素能变换的最小次数。0说明不取
dp[0][0] = 0; dp[0][j] = j 全删; dp[i][0] = i

dp[i][j] = 
            min (1+ min(dp[i-1][j], dp[i][j-1]), dp[i-1][j-1]) if i==j
            min (1+ min(dp[i-1][j], dp[i][j-1]), dp[i-1][j-1] + 1) if i!=j => 需要replace一下
            
*/
class Solution {
    public int minDistance(String word1, String word2) {
        int[][] dp = new int[2][word2.length()+1]; // 前 i，j 个元素能变换的最小次数。0说明不取
        for (int j = 1; j <= word2.length(); j++) {
            dp[0][j] = j; 
        }
        
        for (int i = 1; i <= word1.length(); i++) {
            dp[i%2][0] = i;
            for (int j = 1; j <= word2.length(); j++) {
                dp[i%2][j] = 1 + Math.min(dp[(i-1)%2][j], dp[i%2][j-1]); // 删除或增加的 action
                if (word1.charAt(i-1) == word2.charAt(j-1)) { // replace action: when i == j 
                    dp[i%2][j] = Math.min(dp[i%2][j], dp[(i-1)%2][j-1]); 
                }
                else { // replace action: when i != j 
                     dp[i%2][j] = Math.min(dp[i%2][j], 1 + dp[(i-1)%2][j-1]); 
                }
            }
        }
        return dp[word1.length()%2][word2.length()];
    }
}
