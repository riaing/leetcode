Given two strings s and p, return an array of all the start indices of p's anagrams in s. You may return the answer in any order.

An Anagram is a word or phrase formed by rearranging the letters of a different word or phrase, typically using all the original letters exactly once.

 

Example 1:

Input: s = "cbaebabacd", p = "abc"
Output: [0,6]
Explanation:
The substring with start index = 0 is "cba", which is an anagram of "abc".
The substring with start index = 6 is "bac", which is an anagram of "abc".
Example 2:

Input: s = "abab", p = "ab"
Output: [0,1,2]
Explanation:
The substring with start index = 0 is "ab", which is an anagram of "ab".
The substring with start index = 1 is "ba", which is an anagram of "ab".
The substring with start index = 2 is "ab", which is an anagram of "ab".
 

Constraints:

1 <= s.length, p.length <= 3 * 104
s and p consist of lowercase English letters.

  
---------------- DP --------------------------------
  基础题的进一步： https://leetcode.com/problems/permutation-in-string/submissions/ 
  
  class Solution {
    public List<Integer> findAnagrams(String s, String p) {
         
        // 找anagram需要的参数
        int start = 0; 
        int CountMatchedDistinctChar = 0; // 这其实就是记录map中有多少个0，都为0时则找到了match
        int end = 0; 
        Map<Character, Integer> map = createMap(p);
        
        List<Integer> res = new ArrayList<Integer>(); 
        while (end < s.length()) {         
            char curChar = s.charAt(end);
            if (map.containsKey(curChar)) { // decrease the frequency of the map 
                map.put(curChar, map.get(curChar) - 1);
                if (map.get(curChar) == 0) { // this char has been completed matched
                    CountMatchedDistinctChar++;
                }
            }
            while (end - start + 1 > p.length()) { //window size 得小于等于 pattern size
                // move start forward, and add frequency back if start is in s1 
                char startChar = s.charAt(start);
                if (p.contains((startChar + ""))) {
                    // before add back, if at this moment, map-startChar is 0, meaning it was completed match, so need to decrement 
                    if (map.get(startChar) == 0) {
                        CountMatchedDistinctChar--;
                    }
                    map.put(startChar, map.get(startChar) + 1);
                }
                start++;
            }
            
            if (CountMatchedDistinctChar == map.size()) { // 如果map都为0了,则找到一个答案，这时候start++，更新Map和记录map 0的参数CountMatchedDistinctChar，并且end也++ 
                res.add(start);
                // 更新必要的参数
                map.put(s.charAt(start), 1);
                CountMatchedDistinctChar--;
                start++;
            }
            end++; 
        }
        
        return res; 
    }
    
    private Map<Character, Integer> createMap(String p) {
        Map<Character, Integer> map = new HashMap<Character, Integer>(); 
        for (Character c : p.toCharArray()) {
            map.put(c, map.getOrDefault(c, 0) + 1);
        }
        return map;
    }
}
 
 
