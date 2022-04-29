Given a string s and an integer k, return the length of the longest substring of s that contains at most k distinct characters.

 

Example 1:

Input: s = "eceba", k = 2
Output: 3
Explanation: The substring is "ece" with length 3.
Example 2:

Input: s = "aa", k = 1
Output: 2
Explanation: The substring is "aa" with length 2.
 

Constraints:

1 <= s.length <= 5 * 104
0 <= k <= 50

------------------------ sliding window --------------------------------------

Time: 
The above algorithm’s time complexity will be O(N), where N is the number of characters in the input string. The outer for loop runs for all characters, 
and the inner while loop processes each character only once; therefore, the time complexity of the algorithm will be O(N+N)
O(N+N), which is asymptotically equivalent to O(N)

Space: The algorithm’s space complexity is O(K)
O(K), as we will be storing a maximum of K+1 characters in the HashMap.

class Solution {
    public int lengthOfLongestSubstringKDistinct(String str, int k) {
        int start = 0; 
    int len = 0; 
    Map<Character, Integer> frequencyMap = new HashMap<Character, Integer>();
    for (int end = 0; end < str.length(); end++) {
      //2. establish the first window 
      char curLet = str.charAt(end);
      frequencyMap.put(curLet, frequencyMap.getOrDefault(curLet, 0) + 1);
      while (frequencyMap.size() > k) {
        char startLet = str.charAt(start);
        frequencyMap.put(startLet, frequencyMap.get(startLet) - 1);
        if (frequencyMap.get(startLet) == 0) {
          frequencyMap.remove(startLet);
        }
        start++;
      }
      len = Math.max(len, end - start + 1);
    }
    return len;
    }
}
