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
}
