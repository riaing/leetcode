Given two words (beginWord and endWord), and a dictionary's word list, find the length of shortest transformation sequence from beginWord to endWord, such that:

Only one letter can be changed at a time.
Each transformed word must exist in the word list. Note that beginWord is not a transformed word.
Note:

Return 0 if there is no such transformation sequence.
All words have the same length.
All words contain only lowercase alphabetic characters.
You may assume no duplicates in the word list.
You may assume beginWord and endWord are non-empty and are not the same.
Example 1:

Input:
beginWord = "hit",
endWord = "cog",
wordList = ["hot","dot","dog","lot","log","cog"]

Output: 5

Explanation: As one shortest transformation is "hit" -> "hot" -> "dot" -> "dog" -> "cog",
return its length 5.
Example 2:

Input:
beginWord = "hit"
endWord = "cog"
wordList = ["hot","dot","dog","lot","log"]

Output: 0

Explanation: The endWord "cog" is not in wordList, therefore no possible transformation.

--------------------------DFS解法，超时-------------------------------------------------------------
// k - word length; n - list length 
//time: 每一层有 26*k，总共n层， -> O(26*k)^n
class Solution {
    int res = Integer.MAX_VALUE;
    public int ladderLength(String beginWord, String endWord, List<String> wordList) {
        search(beginWord, endWord, new HashSet<String>(wordList), 0);
        return res == Integer.MAX_VALUE ? 0 : res + 1;
        
    }
    
    private void search(String beginWord, String endWord, Set<String> wordSet, int curRes) {
        if (beginWord.equals(endWord)) {
            res = Math.min(res, curRes);
            return;
        }
        
        for (int level = 0; level < beginWord.length(); level++) {
            for (char i = 'a'; i <= 'z'; i ++) {
                String newWord = beginWord.substring(0, level) + i + beginWord.substring(level+1);
            
                if (wordSet.contains(newWord)) {
                    wordSet.remove(newWord);
                    search(newWord, endWord, wordSet, curRes+1);
                    wordSet.add(newWord);
                }
            }
        }
     
    }
}
