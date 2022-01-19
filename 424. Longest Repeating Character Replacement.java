You are given a string s and an integer k. You can choose any character of the string and change it to any other uppercase English character. You can perform this operation at most k times.

Return the length of the longest substring containing the same letter you can get after performing the above operations.

 

Example 1:

Input: s = "ABAB", k = 2
Output: 4
Explanation: Replace the two 'A's with two 'B's or vice versa.
Example 2:

Input: s = "AABABBA", k = 1
Output: 4
Explanation: Replace the one 'A' in the middle with 'B' and form "AABBBBA".
The substring "BBBB" has the longest repeating letters, which is 4.
 

Constraints:

1 <= s.length <= 105
s consists of only uppercase English letters.
0 <= k <= s.length
-------------------------  sol 1 
/**
window 内要满足条件，想window里肯定要保留出现次数最多的 char，所以要个 map 存 letter -> frequency. 
然后移 window 的条件是window 里无法全是同样的字母了，那就是多数字母的个数 + k （可取代多少个）< window length时，window 无法满足条件了。得移 
Time: 扫 window 用 N， 每次扫 window 需要走遍 map，因为 map 存的是字母所以是常数 k =》 O(N*k)
Space: O(k) 
*/
class Solution {
    public int characterReplacement(String s, int k) {
        //TODO: add corner case
        int start = 0;
        int len =  0;
        Map<Character, Integer> freqMap = new HashMap<Character, Integer>();
        for (int end = 0; end < s.length(); end++) {
            char endChar = s.charAt(end);
            freqMap.put(endChar, freqMap.getOrDefault(endChar, 0) + 1);
            // 多数字母的个数 + k （可取代多少个）< window length时，window 无法满足条件了。得移 
            while (findMostFreqLetterCount(freqMap) + k < end - start + 1) {
                char startChar = s.charAt(start);
                freqMap.put(startChar, freqMap.get(startChar) - 1);
                if (freqMap.get(startChar) == 0) {
                    freqMap.remove(startChar);
                }
                start++;
            }
            len = Math.max(len, end - start + 1);
        }
        return len;
    }
    
    private int findMostFreqLetterCount(Map<Character, Integer> freqMap) {
        int returnVal = 0;
        for (Character key : freqMap.keySet()) {
            returnVal = Math.max(returnVal, freqMap.get(key));
        }
        return returnVal;
    }
} 
------------sol 2: 优化到 O（N），减少扫 map
有个 trick 要记，可从 O（Nk）降到 O（N）： https://www.youtube.com/watch?v=gqXU1UyA8pk&t=749s  
