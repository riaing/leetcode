Given a non-empty string s and a dictionary wordDict containing a list of non-empty words, determine if s can be segmented into a space-separated sequence of one or more dictionary words.

Note:

The same word in the dictionary may be reused multiple times in the segmentation.
You may assume the dictionary does not contain duplicate words.
Example 1:

Input: s = "leetcode", wordDict = ["leet", "code"]
Output: true
Explanation: Return true because "leetcode" can be segmented as "leet code".
Example 2:

Input: s = "applepenapple", wordDict = ["apple", "pen"]
Output: true
Explanation: Return true because "applepenapple" can be segmented as "apple pen apple".
             Note that you are allowed to reuse a dictionary word.
Example 3:

Input: s = "catsandog", wordDict = ["cats", "dog", "sand", "and", "cat"]
Output: false
  
 ----------------- 03.37.2021 DP ------------------------------------------------------------------------------------------  
  /*
dp[i] = 0 -> i 能breakable
dp[i] = true  if wordDict[i]
        else 
            dp[k] && wordDict[k+1, i], 0<= k < i
            
time: O(N*2)            

*/
class Solution {
    public boolean wordBreak(String s, List<String> wordDict) {
        // i is the index, means 0 - i's substring is breakable 
        boolean[] dp = new boolean[s.length()]; 
        for (int i = 0; i <s.length(); i++) {
            if (wordDict.contains(s.substring(0, i+1))) {
                dp[i] = true;
                continue;
            }
            for (int k = 0; k < i; k++) {
                if (dp[k] && wordDict.contains(s.substring(k+1, i+1))) {
                    dp[i] = true;
                    System.out.println(i + " " +dp[i]);
                    break;
                }
            }
            
        }
        return dp[s.length()-1];
    }

}
  -------------------- 03.27.2021  Memorization ------------------------------------------------------------------
  class Solution {
    public boolean wordBreak(String s, List<String> wordDict) {
        // 0 to j is brakable 
        Map<Integer, Boolean> breakable = new HashMap<>();
        return wordBreakHelper(s, wordDict, s.length() - 1, breakable);
    }
    
    private boolean wordBreakHelper(String s, List<String> wordDict, int end, Map<Integer, Boolean> breakable) {
        if (breakable.containsKey(end)) {
            return breakable.get(end);
        } else if (wordDict.contains(s.substring(0, end + 1))) {
            return true;
        }
        for (int i = 1; i <= end; i++) {
            if (wordBreakHelper(s, wordDict, i - 1, breakable) && wordDict.contains(s.substring(i, end + 1))) {
                breakable.put(end, true);
                return true;
            }
        }
        breakable.put(end, false);
        return false;
    }
}
-------------------------------------------------------------------------------------------------------------
/**
m[i]: 以i 结尾能被break
m[i] = OR{m[j]} && i->j+1 在字典中， j <- [0, i-1]

m[0] -> 空集时设置为true

time: O(n^2)
*/

class Solution {
    public boolean wordBreak(String s, List<String> wordDict) {
        boolean[] res = new boolean[s.length()+1];
        int maxLen = findMaxLen(wordDict);
        
        for (int i = 0; i < s.length(); i++) {
            // 将 0->i 特殊处理
            res[i] = wordDict.contains(s.substring(0, i + 1));
            for (int j = i - 1; j >= 0 && i- (j+1) -1 <= maxLen; j--) { //注意检查的string不能超过dict的最大长度
                if (wordDict.contains(s.substring(j + 1, i + 1))) {                    
                    res[i] |= res[j];
                }
            }
        }
        return res[s.length() - 1]; 
    }
    
    // o(N)
    private int findMaxLen(List<String> wordDict) {
        int max = Integer.MIN_VALUE;
        for (String s: wordDict) {
            max = Math.max(max, s.length());
        }
        return max; 
    }
}


----------------------------------------------------------------------------------------------------
/**
多一个lement表示空集的方法

m[i]: 以i-1 结尾能被break
m[i] = OR{m[j]} && i-1->j 在字典中， j <- [0, i-1]

m[0] -> 空集时设置为true

time: O(n^2)
*/

class Solution {
    public boolean wordBreak(String s, List<String> wordDict) {
        boolean[] res = new boolean[s.length()+1];
        int maxLen = findMaxLen(wordDict);
        
        // base case 
        res[0] = true;
        for (int i = 1; i <= s.length(); i++) {
            // 到dic里查的string 长度为 i-1 -j + 1 = i-j 
            for (int j = i-1; j >=0 && i-j <=maxLen; j--) { //注意检查的string不能超过dict的最大长度
                if (wordDict.contains(s.substring(j, i))) {                    
                    res[i] |= res[j];
                }
            }
        }
        return res[s.length()]; 
    }
    
    // o(N)
    private int findMaxLen(List<String> wordDict) {
        int max = Integer.MIN_VALUE;
        for (String s: wordDict) {
            max = Math.max(max, s.length());
        }
        return max; 
    }
}
