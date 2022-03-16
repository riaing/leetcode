You are given a string s and an array of strings words of the same length. Return all starting indices of substring(s) in s that is a concatenation of each word in words exactly once, in any order, and without any intervening characters.

You can return the answer in any order.

 

Example 1:

Input: s = "barfoothefoobarman", words = ["foo","bar"]
Output: [0,9]
Explanation: Substrings starting at index 0 and 9 are "barfoo" and "foobar" respectively.
The output order does not matter, returning [9,0] is fine too.
Example 2:

Input: s = "wordgoodgoodgoodbestword", words = ["word","good","best","word"]
Output: []
Example 3:

Input: s = "barfoofoobarthefoobarman", words = ["bar","foo","the"]
Output: [6,9,12]
 

Constraints:

1 <= s.length <= 104
s consists of lower-case English letters.
1 <= words.length <= 5000
1 <= words[i].length <= 30
words[i] consists of lower-case English letters.
Accepted
245,907
Submissions


------------------------- sliding window 有点浪费空间的写法 --------------------------------------------------------------
/*
start正向扫，每次构建一个size为result的window，
filter logic： 当window中不含pattern的word，或者含有pattern的word但是不match pattern的frequency时，放弃当前window，start++ 
当找到个window的size为result时，找到一个解

Time: 
e O(N * M * Len)
 where ‘N’ is the number of characters in the given string, ‘M’ is the total number of words, and ‘Len’ is the length of a word.
 
 Space: The space complexity of the algorithm is O(N* M), M’ is the total number of words. 每次循环start都需要建一次新map
 
*/
class Solution {
    public List<Integer> findSubstring(String s, String[] words) {
        int wordLen = words[0].length();
        int resultLen = wordLen * words.length; 

        List<Integer> res = new ArrayList<Integer>();
        for (int start = 0; start <= s.length() - resultLen; start++) { // O(s) s's length 
            Map<String, Integer> freq = new HashMap<String, Integer>(); //可优化：这里每次都要重建map。其实可以用另一个空Map来记录看到的word与其frequency
            
            for (String str : words) { 
                freq.put(str, freq.getOrDefault(str, 0) + 1);
            }
            int matchCount = 0;
            for (int end = start + wordLen - 1; end < s.length() && end - start + 1 <= resultLen; end = end + wordLen) { //O(words) words' size. 每次找一个word
                String endStr = s.substring(end - wordLen + 1, end+1); //O(word length) 
                if (!freq.containsKey(endStr) || freq.get(endStr) - 1 < 0) { //当前window不含pattern words或者window含有的words多于pattern frequency了
                    break; 
                }
                freq.put(endStr, freq.get(endStr) - 1);
                
                if (freq.get(endStr) == 0) {
                    matchCount++;
                }
                if (end - start + 1 == resultLen) { //如果能满足前面所有条件达到length，则说明找到一个。
                    res.add(start);
                    // System.out.println(s.substring(start, end + 1));
                }
                
            }
        }
        return res; 
    }
}

----------------- sliding window 优化，用空map来记录window是否valid -----------------------------------------------------------------

/*
相比以上的空间优化：用一个空map来记录当前window出现word的次数，看看是否match pattern的次数
start正向扫，每次构建一个size为result的window，
filter logic： 当window中不含pattern的word，或者含有pattern的word但是不match pattern的frequency时，放弃当前window，start++ 
当找到个window的size为result时，找到一个解

Time: 
e O(N * M * Len)
 where ‘N’ is the number of characters in the given string, ‘M’ is the total number of words, and ‘Len’ is the length of a word.
 
 Space: The space complexity of the algorithm is O(N* M), M’ is the total number of words. 每次循环start都需要建一次新map
 
*/
class Solution {
    public List<Integer> findSubstring(String s, String[] words) {
        int wordLen = words[0].length();
        int resultLen = wordLen * words.length; 
        Map<String, Integer> pattenFreq = new HashMap<String, Integer>(); //优化： 用另一个空Map来记录看到的word与其frequency
        for (String str : words) { 
            pattenFreq.put(str, pattenFreq.getOrDefault(str, 0) + 1);
        }

        List<Integer> res = new ArrayList<Integer>();
        for (int start = 0; start <= s.length() - resultLen; start++) { // O(s) s's length 
            Map<String, Integer> freq = new HashMap<String, Integer>(); //优化： 用另一个空Map来记录看到的word与其frequency
            for (int end = start + wordLen - 1; end < s.length() && end - start + 1 <= resultLen; end = end + wordLen) { //O(words) words' size. 每次找一个word
                String endStr = s.substring(end - wordLen + 1, end+1); //O(word length) 
                
                if (!pattenFreq.containsKey(endStr)) { //当前window不含pattern words
                    break; 
                }
                freq.put(endStr, freq.getOrDefault(endStr, 0) + 1);
                if (pattenFreq.get(endStr) < freq.get(endStr)) { //window含有的words多于pattern frequency了
                    break; 
                }
                if (end - start + 1 == resultLen) { //如果能满足前面所有条件达到length，则说明找到一个。
                    res.add(start);
                    // System.out.println(s.substring(start, end + 1));
                }
                
            }
        }
        return res; 
    }
}
