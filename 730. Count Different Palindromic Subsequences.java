Given a string s, return the number of different non-empty palindromic subsequences in s. Since the answer may be very large, return it modulo 109 + 7.

A subsequence of a string is obtained by deleting zero or more characters from the string.

A sequence is palindromic if it is equal to the sequence reversed.

Two sequences a1, a2, ... and b1, b2, ... are different if there is some i for which ai != bi.

 

Example 1:

Input: s = "bccb"
Output: 6
Explanation: The 6 different non-empty palindromic subsequences are 'b', 'c', 'bb', 'cc', 'bcb', 'bccb'.
Note that 'bcb' is counted only once, even though it occurs twice.
Example 2:

Input: s = "abcdabcdabcdabcdabcdabcdabcdabcddcbadcbadcbadcbadcbadcbadcbadcba"
Output: 104860361
Explanation: There are 3104860382 different non-empty palindromic subsequences, which is 104860361 modulo 109 + 7.
 

Constraints:

1 <= s.length <= 1000
s[i] is either 'a', 'b', 'c', or 'd'. 

Follow up： print 所有解

------------------------------------------ DP ---------------------------------------------------
/*

看blog https://zxi.mytechroad.com/blog/?s=730 

1000 说明算法只能O(n^2)
重点：怎么去重

如果首尾一样，
 -如果中间部分不包含首尾字符：
                        count(bccb) = 2 * count(cc) + 2 : *2是因为对子问题的每一个解，可以append bb，所以子问题解double (cc, c -> bccb, bcb, cc, c)； +2因为这两可以组成b, bb 
                        base: count(x) = 1 if x = a/b/c/d; = 0 if empty 
 - 若中间包含了首尾字符：会有重复解。
    - 如果中间有一个字母和首尾一样： count(bbb) = 2 * count(b) + 1
    - 如果中间有>1个字母和首尾一样： count(bbcbb) = 2 * count(bcb) - count(c) 

如果首尾不一样： 
count(bcca) = count(bcc) + count(cca) - count(cc) => dp[i][j] = dp[i+1][j] + dp[i][j-1] - dp[i+1][j-1]
eg: count(bc) = count(b) + count(c) - count("") = 1 + 1 - 0 = 2

dp[i][j] count of different palindromic subseq of s[i] ~ s[j], inclusive 
dp[i][j] = 
if (i != j) 
 - dp[i+1][j] + dp[i][j-1] - dp[i+1][j-1] 
if (i == j) 
    dp[i+1][j-1] * 2 + 2 -> if (中间有0个n[i]) = l > r
    dp[i+1][j-1] * 2 + 1 -> if (中间有1个n[i]) = l = r
    dp[i+1][j-1] * 2 - dp[l+1][r-1] -> if (中间有2个n[i]),  if n[i] = b, l,r定义为内层b的里面： b..b(l...r)b...b = l < r


 

*/
class Solution {
    public int countPalindromicSubsequences(String s) {
        long modulo = (long) Math.pow(10, 9) + 7;
        int n = s.length(); 
        long[][] dp = new long[n][n];
        // base 
        for (int i = 0; i < n; i++) {
            dp[i][i] = 1;
        }
        for (int i = n -2; i >=0; i--) {
            for (int j = i+1; j < n; j++) { // i,j总差一位
                if (s.charAt(i) != s.charAt(j)) {
                    dp[i][j] = dp[i+1][j] + dp[i][j-1] - dp[i+1][j-1];
                    continue;
                }
                // i == j 
                int l = i + 1; // 找中间的相同字母位子
                int r = j - 1;
                while (l <= r && s.charAt(l) != s.charAt(i)) {
                    l++;
                }
                while (l <= r && s.charAt(r) != s.charAt(i)) {
                    r--;
                }
                if (l > r) { //中间有0个n[i]
                    dp[i][j] = dp[i+1][j-1] * 2 + 2; // bcb = c*2 + 2
                    
                }
                else if (l < r) {
                     dp[i][j] = dp[i+1][j-1] * 2 - dp[l+1][r-1]; // bbcbb = bcb*2 - c
                }
                else {
                      dp[i][j] = dp[i+1][j-1] * 2 + 1; // bbb = b * 2 + 1 
                }
                // if (dp[i][j] < 0) {
                //      System.out.println("i " + i + ".j: " + j + " dp " + dp[i][j] + " + module " + (dp[i][j] + modulo));
                // }
               
                // 注意overflow
                dp[i][j] = (dp[i][j] + modulo) % modulo; // dp[i][j] 可能已经overflow变负数了。记吧。感觉也不会考
            }
        }
        
        // long t = 3104860382L;
        // System.out.println(t % modulo);
        return (int) dp[0][n-1];
    }
}

-------- follow  up: print 所有解 ---------------
 code： https://leetcode.com/playground/XmSGnDyz
 
 /* 730衍生，print所有palindromic subsequence 
面经： https://www.1point3acres.com/bbs/thread-800846-3-1.html 
解法： https://leetcode.com/discuss/interview-question/372459/

Set<String>[][] memo ： i,j 结尾的所有pal seq 
思路还是一样，1）这里不用去重 2）内部只有一个字母相同时加入string start+end
 - 首尾不一样：  memo(bc) = memo(b) + memo(c)
 - 首尾一样
    内部没相同字母： = memo[i+1, j-1] + "s[i]", "s[i]s[j]"
    内部1个字母： = memo[i+1, j-1] + "s[i]s[j]"  =》 重点！此时s[i] 已经包含了
    内部2个以上字母： = memo[i+1, j-1] =》 重点！不用去重

Time： O（n^2 * n 每轮copy最多用n)
*/
public class Main {
    public static void main(String[] args) {
        List<String> res = palindromes("bcbcb"); // [cc, bcb, bb, b, c, bbb, bcbcb, cbc, bccb] 
        System.out.println(res);
    }
    
    
    //  ------------解法2 因为用了set不用考虑去重。= (i+1,j) ; (i, j-1); (i+1,j-1); 给 (i+1,j-1)每个值append首尾 if i== j。 四个结果加起来就是解 -----------------------------
      private static List<String> palindromes(String s) {
        if (s == null || s.length() == 0) return Collections.emptyList();
        int len = s.length();
        
        Set<String>[][] dp = new Set[len][len]; // dp[i][j] denotes all solutions in s.substring(i,j+1);
        for (int i=0; i<len; i++) {
            dp[i][i] = new HashSet<>();
            dp[i][i].add(String.valueOf(s.charAt(i)));
            dp[i][i].add("");
        }
        for (int i=1; i<len; i++) {
            dp[i][i-1] = new HashSet<>();
            dp[i][i-1].add("");
        }
        
        for (int j=1; j<len; j++) {
            for (int i=j-1; i>=0; i--) {
                dp[i][j] = new HashSet<>();
                for (String p:   dp[i][j-1]) dp[i][j].add(p);
                for (String p:   dp[i+1][j]) dp[i][j].add(p);
                for (String p: dp[i+1][j-1]) dp[i][j].add(p);
                if (s.charAt(i) == s.charAt(j)) {
                    for (String p: dp[i+1][j-1]) dp[i][j].add(s.charAt(i) + p + s.charAt(j));
                }
            }
        }
        
        dp[0][len-1].remove("");
        return new ArrayList<>(dp[0][len-1]);
    }
    
    // ------------------------ 解法1, 按730的思路写的，其实不用，因为这里set直接去重，等于解决了730的难点 ----------------------------------------------- 
    public static Set<String> findAllPalindromicSubsequences(String input) {
        int n = input.length();
        Set<String>[][] memo = new Set[n][n]; //记！
        dfs(input, memo, 0, n-1); 
        return memo[0][n-1];
    }
    
    private static Set<String> dfs(String s, Set<String>[][] memo, int start, int end) {
        Set<String> curRes = new HashSet<>();
        if (start > end) {
            return curRes;
        }
        if (start == end) {
            curRes.add(s.charAt(start)+"");
            memo[start][end] = curRes;
            return curRes; 
        }
        if (memo[start][end] != null) {
            return memo[start][end];
        }
        
        if (s.charAt(start) != s.charAt(end)) { // dp[i][j] = dp[i+1][j] + dp[i][j-1] - dp[i+1][j-1] ； 这题因为是set不用remove dup 
            Set<String> r1 = dfs(s, memo, start + 1, end);
            Set<String> r2 = dfs(s, memo, start, end - 1);
            r1.addAll(r2);
            // 与计算个数的题不一样处： 
             // Set<String> r3 = dfs(s, memo, start+1, end-1);
            // r1.removeAll(r3); // 因为是set 不用remove dup
            curRes = r1; 
        }
        else {
            // 首先dp[i][j] = dp[i+1][j-1] * 2 
            curRes = dfs(s, memo, start + 1, end - 1);
            if (curRes.size() != 0) { // 对里面每个元素要append (start)xx(end)
                List<String> newStrings = new ArrayList<>();
                for (String curS : curRes) {
                    newStrings.add(s.charAt(start) + curS + s.charAt(end));
                }
                curRes.addAll(newStrings); 
            }
            
            // 找i+1， j-1中的相同字母的个数
            int i = start + 1; int j = end - 1;
            while (i < end && s.charAt(i) != s.charAt(start)) {
                i++;
            }
            while (j > start && s.charAt(j) != s.charAt(start)) {
                j--;
            }
            if (i > j) { // 0字母和start一样
                curRes.add(s.charAt(start)+"");
                curRes.add(s.charAt(start) + "" + s.charAt(start));  // 重点
            }
            else if (i == j) { //1个字母一样。得加上 start + end 
                curRes.add(s.charAt(start) + "" + s.charAt(start));
            }
             // 与计算个数的题不一样处： 用了set 所以不用remove dup
            // else { // 2个字母一样， 得减 dp[l+1][r-1], l,r are the first/last pos of s[i] in s[i+1, j-1]
            //     Set<String> remove = dfs(s, memo, i + 1, j -1);
            //     curRes.removeAll(remove);
            // }
        }
        memo[start][end] = curRes; 
        return curRes;
    }
    

}
