Given two strings s1 and s2, return true if s2 contains a permutation of s1, or false otherwise.

In other words, return true if one of s1's permutations is the substring of s2.

 

Example 1:

Input: s1 = "ab", s2 = "eidbaooo"
Output: true
Explanation: s2 contains one permutation of s1 ("ba").
Example 2:

Input: s1 = "ab", s2 = "eidboaoo"
Output: false
 

Constraints:

1 <= s1.length, s2.length <= 104
s1 and s2 consist of lowercase English letters.

------------------------------------- Sliding window -------------------------------------------------------------
/*
1. s1建立 letter—— freqency map 
2. 维持一个size为s1大小的window，每次遇到s1中的letter时，相应frequency-1
3. 当window 超时，移动start指针，当碰到s1中的letter时，freqency+1 回来。
4.当map中都是0时，说明找到了完全match， return true
    - 为了避免每次扫一遍map来确定是否都为0，用一个变量记录即可 CountMatchedDistinctChar 
    
Time： 
The above algorithm’s time complexity will be O(N + M)
, where ‘N’ and ‘M’ are the number of characters in the input string and the pattern, respectively.
Space：
he algorithm’s space complexity is O(M)
 since, in the worst case, the whole pattern can have distinct characters that will go into the HashMap.
*/
class Solution {
    public boolean checkInclusion(String s1, String s2) {
        Map<Character, Integer> map = new HashMap<Character, Integer>(); 
        for (Character c : s1.toCharArray()) {
            map.put(c, map.getOrDefault(c, 0) + 1);
        }
        
        int start = 0; 
        int CountMatchedDistinctChar = 0; // 这其实就是记录map中有多少个0，都为0时则找到了match
        for (int end = 0; end < s2.length(); end++) {
            char curChar = s2.charAt(end);
            if (map.containsKey(curChar)) { // decrease the frequency of the map 
                map.put(curChar, map.get(curChar) - 1);
                if (map.get(curChar) == 0) { // this char has been completed matched
                    CountMatchedDistinctChar++;
                }
                
            }
            while (end - start + 1 > s1.length()) { //window size 得小于等于 pattern size
                // move start forward, and add frequency back if start is in s1 
                char startChar = s2.charAt(start);
                if (s1.contains((startChar + ""))) {
                    // before add back, if at this moment, map-startChar is 0, meaning it was completed match, so need to decrement 
                    if (map.get(startChar) == 0) {
                        CountMatchedDistinctChar--;
                    }
                    map.put(startChar, map.get(startChar) + 1);
                }
                start++;
            }
            
            if (CountMatchedDistinctChar == map.size()) { // 如果map都为0了
                return true;
            }
        }
        return false; 
    }
} 
