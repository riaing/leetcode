In an alien language, surprisingly, they also use English lowercase letters, but possibly in a different order. The order of the alphabet is some permutation of lowercase letters.

Given a sequence of words written in the alien language, and the order of the alphabet, return true if and only if the given words are sorted lexicographically in this alien language.

 

Example 1:

Input: words = ["hello","leetcode"], order = "hlabcdefgijkmnopqrstuvwxyz"
Output: true
Explanation: As 'h' comes before 'l' in this language, then the sequence is sorted.
Example 2:

Input: words = ["word","world","row"], order = "worldabcefghijkmnpqstuvxyz"
Output: false
Explanation: As 'd' comes after 'l' in this language, then words[0] > words[1], hence the sequence is unsorted.
Example 3:

Input: words = ["apple","app"], order = "abcdefghijklmnopqrstuvwxyz"
Output: false
Explanation: The first three characters "app" match, and the second string is shorter (in size.) According to lexicographical rules "apple" > "app", because 'l' > '∅', where '∅' is defined as the blank character which is less than any other character (More info).
 

Constraints:

1 <= words.length <= 100
1 <= words[i].length <= 20
order.length == 26
All characters in words[i] and order are English lowercase letters.

--------------------------------- 简单coding， 考读题 ------------------------------------------------------------
class Solution {
    public boolean isAlienSorted(String[] words, String order) {
        int[] letterOrder = new int[26]; 
        int i = 0; 
        for (char c : order.toCharArray()) {
            letterOrder[c - 'a'] = i; 
            i++;
        }
        
        for (i = 1; i < words.length; i++) {
            String pre = words[i-1];
            String cur = words[i];
            int index = 0; 
            while (index < pre.length()) { // 只定pre的index 
                if (index >= cur.length()) { // apple, app -> false 
                    return false; 
                }
                if (letterOrder[pre.charAt(index) - 'a'] < letterOrder[cur.charAt(index) - 'a']) {
                    break;  // 当前对比过了
                }
                else if (letterOrder[pre.charAt(index) - 'a'] > letterOrder[cur.charAt(index) - 'a']) {
                    return false; 
                }
                index++; 
            }

        }
        return true; 
    }
}
