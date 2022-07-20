You are given an array of words where each word consists of lowercase English letters.

wordA is a predecessor of wordB if and only if we can insert exactly one letter anywhere in wordA without changing the order of the other characters to make it equal to wordB.

For example, "abc" is a predecessor of "abac", while "cba" is not a predecessor of "bcad".
A word chain is a sequence of words [word1, word2, ..., wordk] with k >= 1, where word1 is a predecessor of word2, word2 is a predecessor of word3, and so on. A single word is trivially a word chain with k == 1.

Return the length of the longest possible word chain with words chosen from the given list of words.

 

Example 1:

Input: words = ["a","b","ba","bca","bda","bdca"]
Output: 4
Explanation: One of the longest word chains is ["a","ba","bda","bdca"].
Example 2:

Input: words = ["xbc","pcxbcf","xb","cxbc","pcxbc"]
Output: 5
Explanation: All the words can be put in a word chain ["xb", "xbc", "cxbc", "pcxbc", "pcxbcf"].
Example 3:

Input: words = ["abcd","dbqca"]
Output: 1
Explanation: The trivial word chain ["abcd"] is one of the longest word chains.
["abcd","dbqca"] is not a valid word chain because the ordering of the letters is changed.
 

Constraints:

1 <= words.length <= 1000
1 <= words[i].length <= 16
words[i] only consists of lowercase English letters.


------------------------------ DFS + memo -------------------------
/*
bf时间：
n - 最长word 长度
每一次递归： (26*n)^n
words len 为k： k* (26*n)^n + klgk

memo时间：
k- list 有k个word
每个word是 （26*n），总共 k*26*n + klgk
Space：最差 26*n都在dic里，总共O(k*26*n)

注意：倒着来可省26这里：abc - bc/ab/ac. 不会遍历26次。 总time为 o(k*n)
*/
class Solution {
    int maxLen = 0; 
    Set<String> wordsSet;
    Map<String, Integer> memo = new HashMap<>();
    public int longestStrChain(String[] words) {
        this.wordsSet = Arrays.stream(words).collect(Collectors.toSet());
        Arrays.sort(words, (a, b) -> a.length() - b.length());
        for (int i = 0; i < words.length; i++) {
            dfs(words, words[i]);
        }
        return maxLen;
        
    }
    
    private int dfs(String[] words, String curWord) {
        int curLen = 1; 
        
        if (memo.containsKey(curWord)) {
            return memo.get(curWord);
        }
        
        List<String> nexts = newWord(curWord);
        for (String next : nexts) { // 26*n
            curLen = Math.max(curLen, 1 + dfs(words, next));
        }
        
        memo.put(curWord, curLen);
        maxLen = Math.max(maxLen, curLen); 
        return curLen;
    }
    
    
    private List<String> newWord(String s) { // n - word lengh. n* 26 
        List<String> res = new ArrayList<>();
        for (int i = 0; i <= s.length(); i++) {
            for (char j = 'a'; j <= 'z'; j++) {
                String newS = s.substring(0, i) + j + s.substring(i);
                if (wordsSet.contains(newS)) {
                    res.add(newS);
                }
            }
        }
        return res;
    }
}
