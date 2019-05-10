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
class WordDistance {
    Map<String, List<Integer>> map;
    public WordDistance(String[] words) { // o(k) k is the length of words 
        map = new HashMap<String, List<Integer>>();
        for (int i = 0; i < words.length; i++) {
            if (!map.containsKey(words[i])) { // list保证了加入的顺序是asc
                map.put(words[i], new ArrayList<Integer>()); 
            }
            map.get(words[i]).add(i);
        }
    }
    
    // two pointer, 当前哪个小，就把他的指针往后移一位，去找最小值
    public int shortest(String word1, String word2) { //O(m+n), m -> how many indexes of word1, and n -> number of indexes of word2
        List<Integer> w1Indexes = map.get(word1);
        List<Integer> w2Indexes = map.get(word2);
        int i1 = 0;
        int i2 = 0;
        int res = Integer.MAX_VALUE; 
        while (i1 < w1Indexes.size() && i2 < w2Indexes.size()) {
            int w1Index = w1Indexes.get(i1);
            int w2Index =  w2Indexes.get(i2);
            res = Math.min(res, Math.abs(w1Index - w2Index));
            if (w1Index < w2Index) {
                i1++;
            }
            else {
                i2++;
            } 
        }
        return res;
        
    }
}

/**
 * Your WordDistance object will be instantiated and called as such:
 * WordDistance obj = new WordDistance(words);
 * int param_1 = obj.shortest(word1,word2);
 */
