Design a class which receives a list of words in the constructor, and implements a method that takes two words word1 and word2 and return the shortest distance between these two words in the list. Your method will be called repeatedly many times with different parameters. 

Example:
Assume that words = ["practice", "makes", "perfect", "coding", "makes"].

Input: word1 = “coding”, word2 = “practice”
Output: 3
Input: word1 = "makes", word2 = "coding"
Output: 1
Note:
You may assume that word1 does not equal to word2, and word1 and word2 are both in the list.



-----------------------------------------------------------
/*
两sorted list，求min distance： 2 pointer 基础题
Time: build Map O（n）， shortest最差O（n） -> O（n）
space: build Map O(n)

Followup：
如果call很频繁怎么办？ 
原题是construct O(n) 然后每次callO(n)，但如果用2d hashmap是可以实现每次call O(1)的，只不过construct要O(n^2)
*/
class WordDistance {
    Map<String, List<Integer>> map; // map to word, index positions 
    int maxLen;

    public WordDistance(String[] wordsDict) {
        this.map = new HashMap<String, List<Integer>>();
        this.maxLen = wordsDict.length;
        for (int i = 0; i < wordsDict.length; i++) {
            map.putIfAbsent(wordsDict[i], new ArrayList<Integer>());
            List<Integer> indexes = map.get(wordsDict[i]);
            indexes.add(i);
        }
    }
    
    public int shortest(String word1, String word2) {
        // 因为index array是sorted的，所以 2 pointer找min distance
        List<Integer> word1Index = map.get(word1);
        List<Integer> word2Index = map.get(word2);
        
        int p1 = 0;
        int p2 = 0;
        int min = maxLen; 
        while (p1 < word1Index.size() && p2 < word2Index.size()) {
            int index2 = word2Index.get(p2);
            int index1 = word1Index.get(p1);
            min = Math.min(min, Math.abs(index1 - index2));
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

/**
 * Your WordDistance object will be instantiated and called as such:
 * WordDistance obj = new WordDistance(wordsDict);
 * int param_1 = obj.shortest(word1,word2);
 */

