Given a set of words (without duplicates), find all word squares you can build from them.

A sequence of words forms a valid word square if the kth row and column read the exact same string, where 0 ≤ k < max(numRows, numColumns).

For example, the word sequence ["ball","area","lead","lady"] forms a word square because each word reads the same both horizontally and vertically.

b a l l
a r e a
l e a d
l a d y
Note:
There are at least 1 and at most 1000 words.
All words will have the exact same length.
Word length is at least 1 and at most 5.
Each word contains only lowercase English alphabet a-z.
Example 1:

Input:
["area","lead","wall","lady","ball"]

Output:
[
  [ "wall",
    "area",
    "lead",
    "lady"
  ],
  [ "ball",
    "area",
    "lead",
    "lady"
  ]
]

Explanation:
The output consists of two word squares. The order of output does not matter (just the order of words in each word square matters).
Example 2:

Input:
["abat","baba","atan","atal"]

Output:
[
  [ "baba",
    "abat",
    "baba",
    "atan"
  ],
  [ "baba",
    "abat",
    "baba",
    "atal"
  ]
]

Explanation:
The output consists of two word squares. The order of output does not matter (just the order of words in each word square matters).
------------------------------hashmap 解法 -------------------------------------------------------------------------------------
思路：一行行的填词，第n行的词的prefix必须和前面所有行n列一样。eg: 第0行为baba，那么第一行的第一个字母必须是a。所以我们把单词按照prefix存到map里，
在填下一行的词时，先确定prefix，然后到map里找是否有相应的word，再每个word进行递归。eg：
第0行："area","lead","wall","lady","ball"  ->比如此时是 "wall"。此时的matrix为{wall}
第1行：在map中找以a（前几行的第1列）为key的string list：{area} -> 把area填在此行。此时的matrix为{wall，area}
第2行：确定此时的prefix（前几行的第2列）：le。在map中找le为key的list：{lead} -> 把lead填进去，此时的matrix为{wall，area，lead}
第3行：此时的prefix应为前几行的第3列：lad。在map中找到的list}为{lady}，填入matrix。此时的matrix为{wall，area，lead，lady}，
进行下一次递归：发现此时行数为4，等于word的长度，则返回matrix。

  
  class Solution {
    // Mapping for prefix to each word. 
    private Map<String, List<String>> getPrefixMap(String[] words) {
        Map<String, List<String>> prefixMap = new HashMap<String, List<String>>();
        for (String word : words) {
            prefixMap.putIfAbsent("", new ArrayList<String>());
            prefixMap.get("").add(word);
            String prefix = "";
            for (char c : word.toCharArray()) {
                prefix += c; 
                prefixMap.putIfAbsent(prefix, new ArrayList<String>());
                prefixMap.get(prefix).add(word);
            }
        }
        return prefixMap;
    }
    
    private void dfs(Map<String, List<String>> prefixMap, List<String> curMat, int row, int wordLen, List<List<String>> result) {
        if (wordLen == row) {
            result.add(new ArrayList<String>(curMat));
            return;
        }
        String curPrefix = "";
        for (int i = 0; i < row; i++) {
            curPrefix += curMat.get(i).charAt(row);
        }
        if (!prefixMap.containsKey(curPrefix)) {
            return;
        }
        for (String item : prefixMap.get(curPrefix)) {
            curMat.add(item);
            dfs(prefixMap, curMat, row + 1, wordLen, result);
            curMat.remove(curMat.size() - 1);
        }   
    }
    
    public List<List<String>> wordSquares(String[] words) {
        List<List<String>> result = new ArrayList<List<String>>(); 
        if (words == null || words.length == 0) {
            return result;
        }
        
        Map<String, List<String>> prefixMap = getPrefixMap(words);
        dfs(prefixMap, new ArrayList<String>(), 0, words[0].length(), result);
        return result;
    }
    
    -----------------------------Trie----------------------------------------------------------------------------
    主要是想清楚trie的构造：每个trienode有一堆children（List<String>），并且每个trieNode都要对应到单词。
    class Solution {
    class TrieNode {
        List<String> startWith;
        TrieNode[] children; 
        
        TrieNode() {
            startWith = new ArrayList<>();
            children = new TrieNode[26];
        }
    }
    
    class Trie {
        TrieNode root;
        
        Trie(String[] words) {
            root = new TrieNode();
            for (String word : words) {
                TrieNode cur = root;
                for (char c : word.toCharArray()) {
                    int index = c - 'a';
                    if (cur.children[index] == null) {
                        cur.children[index] = new TrieNode();
                    }
                    cur.children[index].startWith.add(word);
                    cur = cur.children[index];
                }
            }
        }
        
        List<String> findByPrefix(String prefix) {
            List<String> result = new ArrayList<>();
            TrieNode cur = root;
            for (char c : prefix.toCharArray()) {
                int index = c -'a';
                if (cur.children[index] == null) {
                    return result;
                }
                cur = cur.children[index];
            }
            result.addAll(cur.startWith);
            return result;
        }
    }
    
     public List<List<String>> wordSquares(String[] words) {
        List<List<String>> results = new ArrayList<List<String>>();
        List<String> square = new ArrayList<String>();
         // Similar to hashmap, put prefix into the trie. 
         Trie tri = new Trie(words); 
        for (String word : words) {
            square.add(word);
            dfs(tri, square, results, words[0].length());
            square.remove(word);
        }
         return results;          
    }
    
    private void dfs(Trie tri, List<String> square, List<List<String>> results, int len) {
        if (square.size() == len) {
            results.add(new ArrayList<>(square));
            return;
        }
        
        int row = square.size();
        // get current prefix from matrix;
        String prefix = "";
        for (int i = 0; i < square.size(); i++){
            prefix += square.get(i).charAt(row);
        }
        for (String item : tri.findByPrefix(prefix)) {
            square.add(item);
            dfs(tri, square, results, len);
            square.remove(square.size() -1);
        }
    }
}
}
