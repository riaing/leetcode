Given a list of words and two words word1 and word2, return the shortest distance between these two words in the list.

Example:
Assume that words = ["practice", "makes", "perfect", "coding", "makes"].

Input: word1 = “coding”, word2 = “practice”
Output: 3
Input: word1 = "makes", word2 = "coding"
Output: 1
Note:
You may assume that word1 does not equal to word2, and word1 and word2 are both in the list.
-------------------------------------------------
/**
Two pointer, O(n)
*/
class Solution {
    public int shortestDistance(String[] words, String word1, String word2) {
        if (words == null || words.length == 0 || word1.length() == 0 || word2.length() == 0) {
            return 0;
        }
        int p1 = -1;
        int p2 = -1;
        int res = words.length;
        for (int i = 0; i < words.length; i++) {
            // branching to save time 
            if (!words[i].equals(word1) && !words[i].equals(word2)) {
                continue; 
            }
            if (words[i].equals(word1)) {
                p1 = i;
            }
            else if (words[i].equals(word2)) {
                p2 = i;
            }
            if (p1 != -1 && p2 != -1) {
                res = Math.min(res, Math.abs(p2 - p1));
            }
        }
        return res; 
    }
}
