Given an array of strings wordsDict and two strings that already exist in the array word1 and word2, return the shortest distance between these two words in the list.

Note that word1 and word2 may be the same. It is guaranteed that they represent two individual words in the list.

 

Example 1:

Input: wordsDict = ["practice", "makes", "perfect", "coding", "makes"], word1 = "makes", word2 = "coding"
Output: 1
Example 2:

Input: wordsDict = ["practice", "makes", "perfect", "coding", "makes"], word1 = "makes", word2 = "makes"
Output: 3
 

Constraints:

1 <= wordsDict.length <= 105
1 <= wordsDict[i].length <= 10
wordsDict[i] consists of lowercase English letters.
word1 and word2 are in wordsDict.

---------------------- 2 pointer ----------------------------------

/*
上题： https://github.com/riaing/leetcode/blob/master/244.%20Shortest%20Word%20Distance%20II.java 
还是2 pointer 找两sorted array的min distance
*/
class Solution {
    Map<String, List<Integer>> map; // map to word, index positions 
    int maxLen;
    public int shortestWordDistance(String[] wordsDict, String word1, String word2) {
        this.map = new HashMap<String, List<Integer>>();
        this.maxLen = wordsDict.length;
        for (int i = 0; i < wordsDict.length; i++) {
            map.putIfAbsent(wordsDict[i], new ArrayList<Integer>());
            List<Integer> indexes = map.get(wordsDict[i]);
            indexes.add(i);
        }
        return shortest(word1, word2); 
    }
    
     private int shortest(String word1, String word2) {
        // 因为index array是sorted的，所以 2 pointer找min distance
        List<Integer> word1Index = map.get(word1);
        List<Integer> word2Index = map.get(word2);
        
        int p1 = 0;
        int p2 = 0;
        int min = maxLen; 
        while (p1 < word1Index.size() && p2 < word2Index.size()) {
            int index2 = word2Index.get(p2);
            int index1 = word1Index.get(p1);
            if (index1 != index2) { // 与上题唯一的不同：避免拿到同一个word.
                  min = Math.min(min, Math.abs(index1 - index2));
            }
          
            if (index1 < index2) {
                p1++;
            }
            else {
                p2++;
            }
        }
        return min; 
    }
}
