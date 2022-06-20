Given two strings s and t of lengths m and n respectively, return the minimum window substring of s such that every character in t (including duplicates) is included in the window. If there is no such substring, return the empty string "".

The testcases will be generated such that the answer is unique.

A substring is a contiguous sequence of characters within the string.

 

Example 1:

Input: s = "ADOBECODEBANC", t = "ABC"
Output: "BANC"
Explanation: The minimum window substring "BANC" includes 'A', 'B', and 'C' from string t.
Example 2:

Input: s = "a", t = "a"
Output: "a"
Explanation: The entire string s is the minimum window.
Example 3:

Input: s = "a", t = "aa"
Output: ""
Explanation: Both 'a's from t must be included in the window.
Since the largest window of s only has one 'a', return empty string.
 

Constraints:

m == s.length
n == t.length
1 <= m, n <= 105
s and t consist of uppercase and lowercase English letters.

------------------------- sliding window ----------------------------------------------------------------
/*
和https://leetcode.com/problems/find-all-anagrams-in-a-string/ 很像。同样是用map来记录pattern的frequency。
这里维持window的条件是是找到一个解（map的value都为0）后，在window内更新result。而上题，维持window的条件是window size= pattern size、

*/
class Solution {
    public String minWindow(String s, String t) {
        
        int start = 0; 
        int countMatch = 0;
        int subStringStart = s.length()-1; // 用来最后print结果
        int minLen = s.length() + 1;  // something that's unreachable 
        Map<Character, Integer> freq = new HashMap<Character, Integer>(); 
        for (char c : t.toCharArray()) {
            freq.put(c, freq.getOrDefault(c, 0) + 1);
        }
        
        for (int end = start; end < s.length(); end++) {
            char endChar = s.charAt(end);
            if (freq.containsKey(endChar)) { // decrement matching char 
                freq.put(endChar, freq.get(endChar) - 1); 
                if (freq.get(endChar) == 0) { // 等于0说明此char已经完全找到了，注意此value可能<0,就是说明找多了
                    countMatch++; 
                }
            }
            
            // maintain a window of 找到一个解
            while (countMatch == freq.size()) {
                //说明找到了一个解，跟新一下参数
                if(minLen > end - start + 1) {
                    minLen = end - start + 1;
                    subStringStart = start; 
                }
                // 把start往后移，并且更新countMatch
                char startChar = s.charAt(start); 
                if (freq.containsKey(startChar)) { 
                    if (freq.get(startChar) == 0) { //如果没移前此char刚好match pattern的count，移完start后就不match pattern了
                         countMatch--;
                    }
                     freq.put(startChar, freq.get(startChar) + 1);   
                }
                start++; 
            }
        }
        // return "" if cannot find a result 
        return minLen == s.length()+1 ? "" : s.substring(subStringStart, subStringStart + minLen); 
    }
}

------------------- 差不多的写法 -------------------------------------
 class Solution {
    public String minWindow(String s, String t) {
        Map<Character, Integer> countMap = new HashMap<>();
        for (char c : t.toCharArray()) {
            countMap.put(c, countMap.getOrDefault(c, 0) + 1);
        }
        
        int count = 0; 
        int minRes = s.length() + 1;
        String res = "";
        int start = 0;
        
        for (int i = start; i < s.length(); i++) {
            char c = s.charAt(i);
            if (countMap.containsKey(c)) {
                countMap.put(c, countMap.get(c) - 1);
                if (countMap.get(c) == 0) {// 找全了当前字母
                    count++; 
                }
            }

            while (count == countMap.size()) { // 找到了一个解
                if (minRes > i - start + 1) {
                    minRes = i - start + 1;
                    res = s.substring(start, i+1);
                }
                if (countMap.containsKey(s.charAt(start))) {
                    countMap.put(s.charAt(start), countMap.get(s.charAt(start)) + 1);
                    if (countMap.get(s.charAt(start)) > 0) {
                        count--;
                    }
                }
                 start++;
            }
        }
        return res; 
    }   
}
