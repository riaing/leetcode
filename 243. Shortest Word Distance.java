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
/*
对于word1，和出现在它之前的最近的word2算距离：需要记录lastSeen word2
同理对word2，需要记录lastSeen word1. 
所以两者combine，对于w1/w2, 与lastSeen w2/w1算距离，同时跟新itself。扫一遍即可
*/
class Solution {
    public int shortestDistance(String[] wordsDict, String word1, String word2) {
        int lastSeenW1 = -1; 
        int lastSeenW2 = -1; 
        int minDis = wordsDict.length; 
        for (int i = 0; i < wordsDict.length; i++) {
            if (wordsDict[i].equals(word1)) {
                if (lastSeenW2 != -1) {
                    minDis = Math.min(minDis, i - lastSeenW2); // 和最后见到的2相比
                }
                lastSeenW1 = i; // 更新他自己的lastSeen
            }
            if (wordsDict[i].equals(word2)) {
                if (lastSeenW1 != -1) {
                    minDis = Math.min(minDis, i - lastSeenW1);
                }
                 lastSeenW2 = i;
            }
        }
        return minDis;
    }
    
}
-------------- 同样思路 写2 --------------------------
    /*
对于word1，和出现在它之前的最近的word2算距离：需要记录lastSeen word2
同理对word2，需要记录lastSeen word1. 
所以两者combine，对于w1/w2, 与lastSeen w2/w1算距离，同时跟新itself。扫一遍即可
*/
    
class Solution {
    public int shortestDistance(String[] wordsDict, String word1, String word2) {
        int lastSeen1 = -1;
        int lastSeen2 = -1;
        int minDis = wordsDict.length;
        for (int i = 0; i < wordsDict.length; i++) {
            String cur = wordsDict[i];
            if (cur.equals(word1)) {
                lastSeen1 = i;  
            }
            else if (cur.equals(word2)) {
                lastSeen2 = i; 
            }
            if (lastSeen1 != -1 && lastSeen2 != -1) {
                minDis = Math.min(minDis, Math.abs(lastSeen1 - lastSeen2));
            }
        }
        return minDis; 
    }
}
