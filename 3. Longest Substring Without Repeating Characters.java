Given a string s, find the length of the longest substring without repeating characters.

 

Example 1:

Input: s = "abcabcbb"
Output: 3
Explanation: The answer is "abc", with the length of 3.
Example 2:

Input: s = "bbbbb"
Output: 1
Explanation: The answer is "b", with the length of 1.
Example 3:

Input: s = "pwwkew"
Output: 3
Explanation: The answer is "wke", with the length of 3.
Notice that the answer must be a substring, "pwke" is a subsequence and not a substring.
 

Constraints:

0 <= s.length <= 5 * 104
s consists of English letters, digits, symbols and spaces.
  
  --------- Sliding window -----------------
  https://www.educative.io/courses/grokking-the-coding-interview/YMzBx1gE5EO 

class Solution {
    // time O (n), space O(k) -> k is constant of the characters 
    public int lengthOfLongestSubstring(String str) {
    int start = 0; 
    int len = 0; 
    Set<Character> letters = new HashSet<Character>();
    for (int end = 0; end < str.length(); end++ ){
      char endLet = str.charAt(end);
      while (letters.contains(endLet) && start < end) {
          letters.remove(str.charAt(start));
          start++; 
      }
      letters.add(endLet);
      len = Math.max(len, end - start + 1);
    }
    return len;
    }
}
