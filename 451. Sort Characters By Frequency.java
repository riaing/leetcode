Given a string s, sort it in decreasing order based on the frequency of the characters. The frequency of a character is the number of times it appears in the string.

Return the sorted string. If there are multiple answers, return any of them.

 

Example 1:

Input: s = "tree"
Output: "eert"
Explanation: 'e' appears twice while 'r' and 't' both appear once.
So 'e' must appear before both 'r' and 't'. Therefore "eetr" is also a valid answer.
Example 2:

Input: s = "cccaaa"
Output: "aaaccc"
Explanation: Both 'c' and 'a' appear three times, so both "cccaaa" and "aaaccc" are valid answers.
Note that "cacaca" is incorrect, as the same characters must be together.
Example 3:

Input: s = "Aabb"
Output: "bbAa"
Explanation: "bbaA" is also a valid answer, but "Aabb" is incorrect.
Note that 'A' and 'a' are treated as two different characters.
 

Constraints:

1 <= s.length <= 5 * 105
s consists of uppercase and lowercase English letters and digits.
  
  ------------------- heap ----------------------------------------------------
  class Solution {
    public String frequencySort(String s) {
         // num -> count
        Map<Character, Integer> count = new HashMap<Character, Integer>();
        for (int i = 0; i < s.length(); i++) {
            count.put(s.charAt(i), count.getOrDefault(s.charAt(i), 0) + 1); 
        }
        // max heap of the number which sorted by frequency 
        // 注意这个 comparator 可以不用 queue 里的东西
        PriorityQueue<Character> q = new PriorityQueue<Character>((n1, n2) -> count.get(n2) - count.get(n1));
        for (char c : count.keySet()) {
            q.offer(c);
        }
        
        StringBuilder b = new StringBuilder();
        while(q.size() > 0) {
            char c = q.poll();
            for (int i = 0; i < count.get(c); i++) {
                b.append(c);
            }
        }
        return b.toString();
    }
}
