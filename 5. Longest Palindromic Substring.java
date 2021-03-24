------------ 3.23.2021 Memorization --------------------------------
    /*
Thought: 
1. for i = 0; i < length; i++ 
     for j = length - 1; j >= 0; j--
        determine if substring i, j is a palindrome 
2. so need to know for any position i, j -> if string(i, j) is a palindrome => this is the main problem to solve 
3. 怎么定义palindrome？ 首尾相同并且中间是palindrome
4. 自然想到要用memorization来减少重复运算
5. 所以先一个function求出任意两点是不是palindrome， 得到result[][]后，再扫一遍找出最长的palindrome       
*/
class Solution {
    public String longestPalindrome(String s) {
        // initialization
        int[][] palindrome = new int[s.length()][s.length()];
        for (int i = 0; i < s.length(); i++) {
            for (int j = i; j < s.length(); j++) {
                 palindrome[i][j] = 2; 
            }
        }
          // for each position, log it string(i, j) is palindrome 
          for (int i = 0; i < s.length(); i++) {
            for (int j = i; j < s.length(); j++) {
                 int curResult = palindrome(s, i, j, palindrome);
            }
        }
        
        
        // find the longest palindromic substring
        int maxLen = Integer.MIN_VALUE;
        String result = "";
        // 优化1： start the longest substring, in this way once find a result, it's always the longest 
        // eg: babad -> 0, 4 babad -> 0, 3 baba -> 1, 4 abad -> 0, 2 bab -> 1, 3 aba ..... 
        for (int len = s.length(); len > 0; len--) {
            for (int start = 0; start <= s.length() - len; start++) {
                int end = start + len - 1;
                // this way once find a result, it's always the longest substring 
                if (palindrome[start][end] == 1) {
                    result = s.substring(start, start+ len);
                    return result;
                }
            }
        }
        
        return result;
    }
    
    // check if i,j is palindrome, result[i][j] = 1 -> true; 0 -> false; 2 -> haven't assigned value
    private int palindrome(String s, int start, int end, int[][] result) {
        if (start > end) {
            return 0; 
        }
        // memorization
        if (result[start][end] != 2) {
            return result[start][end];
        }
        // edge case 1: length = 1 
        if (start == end) {
            result[start][end] = 1;
            return 1; 
        }
        // edge case 2: length = 2
        if (start + 1 == end) {
            int tmp = s.charAt(start) == s.charAt(end) ? 1 : 0;
            result[start][end] = tmp;
            return tmp;
        }
        
        if (s.charAt(start) != s.charAt(end)) {
            result[start][end] = 0;
            return 0;
        }
        // if start == end, then depends on if inner string is palindrome 
        int inner = palindrome(s, start + 1, end - 1, result); 
        result[start][end] = inner;
        return inner;
    }
}

----------- 3.21.2021 DP ------------------------------------------
    /*
Thought: 
1. for i = 0; i < length; i++ 
     for j = length - 1; j >= 0; j--
        determine if substring i, j is a palindrome 
2. so need to know for any position i, j -> if string(i, j) is a palindrome => 问题转换成任意两点是否为回文，满足DP条件(false/true question + cannot sort)
3. DP 定义： DP[i][j] = string(i, j) is palindrome
      初始化： dp[i][i] = true
      公式：p[i][j] = dp[i+1][j-1] && char(i) == char(j) -> 如果首尾相同并且中间为回文，则此string为回文
      return：找到 i, j中最长的回文
4. 所以先一个function求出任意两点是不是palindrome， 得到result[][]后，再扫一遍找出最长的palindrome      
5. 难点： DP的for loop，要理解DP的sub question是什么：是由长度为1的string推出长度为2的string
*/
class Solution {
    public String longestPalindrome(String s) {
        // for each position, log it string(i, j) is palindrome 
        boolean[][] palindrome = palindrome2(s);
        
        // find the longest palindromic substring
        String result = "";
        // 优化1： start the longest substring, in this way once find a result, it's always the longest 
        // eg: babad -> 0, 4 babad -> 0, 3 baba -> 1, 4 abad -> 0, 2 bab -> 1, 3 aba ..... 
        for (int len = s.length(); len > 0; len--) {
            for (int start = 0; start <= s.length() - len; start++) {
                int end = start + len - 1;
                // this way once find a result, it's always the longest substring 
                if (palindrome[start][end]) {
                    result = s.substring(start, start+ len);
                    return result;
                }
            }
        }
        
        return result;
    }
    
    
    private boolean[][] palindrome2(String s) {
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

-----------------------------------------------------------
找到任意两点是不是pal。
m[i][j] = m[i+1][j-1] && a[i] == a[j], where i >= j
注意：matrix的左上到右下为分界线，所有下面的点（i >= j)都设为true
i要从后往前
time: o(n^2）

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

----------------------从中间向two sides expend. O(n) on average : special approach for such question ----------------------------------
        class Solution {
    int start;
    int end; 
    public String longestPalindrome(String s) {
        start = 0;
        end = 0;
        if (s == null || s.length() < 2) {
            return s;
        }
        for (int i = 0; i < s.length(); i++) {
            findPal(s, i, i);
            findPal(s, i, i+1);
        }
        return s.substring(start, end+1);
    }
    
    private void findPal(String s, int lo, int hi) {
        while (lo >= 0 && hi < s.length() && s.charAt(lo) == s.charAt(hi)) {
            if (end - start + 1 < hi - lo + 1) {
                end = hi;
                start = lo;
            }
            lo--;
            hi++;
        }
    }
}
        ----------变种： 只考虑大小写 不考虑空格, 等其他符号的回文数--------------------------------
class Solution {
    int start;
    int end; 
    int length;
    public String longestPalindrome(String s) {
        start = 0;
        end = 0;
        length = 0;
        if (s == null || s.length() < 2) {
            return s;
        }
        for (int i = 0; i < s.length(); i++) {
            findPal(s, i, i);
            findPal(s, i, i+1);
        }
        System.out.println("length " + length);
        return s.substring(start, end+1);
    }
    
    private void findPal(String s, int lo, int hi) {
        int symbol = 0;
        while (lo >= 0 && hi < s.length()) {
            if (!Character.isLetterOrDigit(s.charAt(lo))) {  // 就在遇到符号时跳过
                symbol++;
                lo--;
            }
            if (!Character.isLetterOrDigit(s.charAt(hi))) {
                symbol++;
                hi++;
            }    
            if (s.charAt(lo) == s.charAt(hi)) {
                if (end - start + 1 < hi - lo + 1 - symbol) { // 并且记录下符号一共出现了多少次
                end = hi;
                start = lo;
                length = hi - lo + 1 - symbol;
                }   
                lo--;
                hi++;
            } 
            else {
                break;
            }
        }
    
    }
}
