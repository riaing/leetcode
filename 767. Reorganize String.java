https://www.educative.io/courses/grokking-the-coding-interview/xV7wx4o8ymB

Given a string s, rearrange the characters of s so that any two adjacent characters are not the same.

Return any possible rearrangement of s or return "" if not possible.

 

Example 1:

Input: s = "aab"
Output: "aba"
Example 2:

Input: s = "aaab"
Output: ""
 

Constraints:

1 <= s.length <= 500
s consists of lowercase English letters.

-------------------------------- heap ----------------------------------------------------------
/*
1. max heap of character - frequency 
2. build the string, add one the most frequent char, reduce frequency, NOT add back to heap but remember it 
 - if frequency becomes zero, set the char to ""
3. add the one of the next most frequent char, reduece its freqency now add back the previous char to heap (if the char != "")

Time complexity#
The time complexity of the above algorithm is O(N*logN)O(N∗logN) where ‘N’ is the number of characters in the input string.

Space complexity#
The space complexity will be O(N)O(N), as in the worst case, we need to store all the ‘N’ characters in the HashMap.
*/
class Solution {
    public String reorganizeString(String s) {
        // 1. build 
        Map<Character, Integer> map = new HashMap<Character, Integer>();
        for (int i = 0; i < s.length(); i++) {
            map.put(s.charAt(i), map.getOrDefault(s.charAt(i), 0) + 1);
        }
        PriorityQueue<Character> q = new PriorityQueue<Character>((a, b) -> map.get(b) - map.get(a));
        for (char c : map.keySet()) {
            q.offer(c);
        }
        
        // 
        StringBuilder builder = new StringBuilder();
        char pre = ' ';
        while (q.size() > 0) {
            char mostFreq = q.poll();
            builder.append(mostFreq);
            // 2. add back the char from the last time 
            if (pre != ' ') {
                q.offer(pre);
            }
            // 3. decrease frequency and update it to pre 
            map.put(mostFreq, map.get(mostFreq) - 1);
            if (map.get(mostFreq) == 0) {
                pre = ' ';
            }
            else {
                pre = mostFreq; 
            }
        }
        String finalRes = builder.toString();
        if (finalRes.length() != s.length()) {
            return "";
        }
        return builder.toString();
    }
}
