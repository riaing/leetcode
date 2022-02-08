A word's generalized abbreviation can be constructed by taking any number of non-overlapping and non-adjacent substrings and replacing them with their respective lengths.

For example, "abcde" can be abbreviated into:
"a3e" ("bcd" turned into "3")
"1bcd1" ("a" and "e" both turned into "1")
"5" ("abcde" turned into "5")
"abcde" (no substrings replaced)
However, these abbreviations are invalid:
"23" ("ab" turned into "2" and "cde" turned into "3") is invalid as the substrings chosen are adjacent.
"22de" ("ab" turned into "2" and "bc" turned into "2") is invalid as the substring chosen overlap.
Given a string word, return a list of all the possible generalized abbreviations of word. Return the answer in any order.

 

Example 1:

Input: word = "word"
Output: ["4","3d","2r1","2rd","1o2","1o1d","1or1","1ord","w3","w2d","w1r1","w1rd","wo2","wo1d","wor1","word"]
Example 2:

Input: word = "a"
Output: ["1","a"]
 

Constraints:

1 <= word.length <= 15
word consists of only lowercase English letters.

------------------------------
/*
每层的选择：不 abbrev，abbrev 一个，两个，，，，所有
时间：每个字母可以 abbrev 或者不 abbrev。总共2*n ，加每次 create String n -》 O(N 2^N)。或者想：第一层 N， 第二层 N-1。。。 -> N！
*/
class Solution {
    public List<String> generateAbbreviations(String word) {
        List<String> results = new ArrayList<String>();
        helper(word, 0, "", results);
        return results;
          
    }
    
    private void helper(String word, int index, String cur, List<String> results) {
        if (index >= word.length()) {
            results.add(cur);
            return; 
        }
        
        helper(word, index + 1, cur + word.charAt(index), results); // not abbreviation 
        
        for (int i = 0; i + index < word.length(); i++) {
            String tmp = cur;
            cur = cur + (i+1);
            
            if (i + index < word.length() -1) {
                 cur = cur + word.charAt(index + i + 1); // add the next one to avoid adjacent 
            }
            helper(word, (i + 1) + index + 1, cur, results); // i+1 is to skip the next char to avoid adjacent. the next + 1 is to go the next char
            cur = tmp;
        }
        return;
    }
        
}
