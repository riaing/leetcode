问法1： 
Given a string, find the minimum number of characters that we can remove to make it a palindrome.

Example 1:

Input: "abdbca"
Output: 1
Explanation: By removing "c", we get a palindrome "abdba".
Example 2:

Input: = "cddpd"
Output: 2
Explanation: Deleting "cp", we get a palindrome "ddd".
Example 3:

Input: = "pqr"
Output: 2
Explanation: We have to remove any two characters to get a palindrome, e.g. if we 
remove "pq", we get palindrome "r".

问法二：insertion
Given a string s. In one step you can insert any character at any index of the string.
Return the minimum number of steps to make s palindrome.
https://leetcode.com/problems/minimum-insertion-steps-to-make-a-string-palindrome/

问法三： 2. Find if a string is K-Palindromic#
Any string will be called K-palindromic if it can be transformed into a palindrome by removing at most ‘K’ characters from it.

This problem can easily be converted to our base problem of finding the minimum deletions in a string to make it a palindrome. If the “minimum deletion count” is 
not more than ‘K’, the string will be K-Palindromic.
  
 
 想法一：适合作为 lPS的followup
 This problem can be easily converted to the Longest Palindromic Subsequence (LPS) problem. We can use the fact that LPS is the best subsequence we can have, so any character that is not part of LPS must be removed. Please note that it is ‘Longest Palindromic SubSequence’ and not ‘Longest Palindrome Substring’.

So, our solution for a given string ‘st’ will be:

    Minimum_deletions_to_make_palindrome = Length(st) - LPS(st)
      
  想法二：
  
  dp[i][j] : i j 之间最少remove的个数
  dp[i][j] = 1 + Min{dp[i+1][j], dp[i][j-1]} if char i != char j 
             dp[i+1][j-1] if char i == char j 
   dp[i][i] = 0
      
  
   ------------------- DP with find path sol 1 ------------------------ 
      
  class Solution {
    public int minInsertions(String s) {
        int[][] dp = new int[s.length()][s.length()];
        Map<String, List<Character>> track = new HashMap<String, List<Character>>(); // at index i+"|"+"j", the characers need to be removed 
        for (int i = 0; i < s.length(); i++) {
            dp[i][i] = 0;
        }
        
        for (int i = s.length() - 2; i >= 0; i--) {
            for (int j = i+1; j < s.length(); j++) {
                String curKey = i+"|"+j;
                
                if (s.charAt(i) == s.charAt(j)) {
                    dp[i][j] = dp[i+1][j-1];
                    // 找路径 code
                    track.put(curKey, new ArrayList<Character>());
                    if (track.containsKey((i+1)+"|"+(j-1))) {
                        track.get(curKey).addAll(track.get((i+1)+"|"+(j-1)));
                    }
                }
                else {
                    dp[i][j] = 1 + Math.min(dp[i+1][j], dp[i][j-1]);
                    
                    // 找路径 code
                    track.put(curKey, new ArrayList<Character>());
                    String sub = "";
                    if (dp[i+1][j] < dp[i][j-1]) {
                        track.get(curKey).add(s.charAt(i));
                        sub = i+1 + "|" + j;
                    }
                    else{
                        track.get(curKey).add(s.charAt(j));
                        sub = i + "|" + (j-1);
                    }
                    if (track.containsKey(sub)) {
                        track.get(curKey).addAll(track.get(sub)); 
                    } 
                }
                // System.out.println(curKey + " : " + track.get(i+"|" +j));
            }
        }
        
        // print 路径
        System.out.println("need remove: " + track.get(0 + "|" + (s.length()-1)));
        return dp[0][s.length()-1];
    }
}   

------------------- DP with find path sol 2 : 求path的general solution---------------------------------------------------------
  
class Solution {
    public int minInsertions(String s) {
        int[][] dp = new int[s.length()][s.length()];
        Map<String, List<Character>> track = new HashMap<String, List<Character>>(); // at index i+"|"+"j", the characers need to be removed 
        int[][] from = new int[s.length()][s.length()]; // 0: from i+1, j-1, 1: from i+1, j, 2: from i, j-1
        
        for (int i = 0; i < s.length(); i++) {
            dp[i][i] = 0;
        }
        
        for (int i = s.length() - 2; i >= 0; i--) {
            for (int j = i+1; j < s.length(); j++) {
                
                if (s.charAt(i) == s.charAt(j)) {
                    dp[i][j] = dp[i+1][j-1];
                    // 找路径 code
                    from[i][j] = 0;
                }
                else {
                    dp[i][j] = 1 + Math.min(dp[i+1][j], dp[i][j-1]);
                    
                    // 找路径 code
                    if (dp[i+1][j] < dp[i][j-1]) {
                        from[i][j] = 1;
                    }
                    else{
                        from[i][j] = 2;
                    }
                }
            }
        }
        
        // print 路径
        int i = 0; 
        int j = s.length()-1;
        while (i < s.length() && j >= 0) {
            if (from[i][j] == 0) {
                i++;
                j--;
            }
            else if (from[i][j] == 1) {
               System.out.println( s.charAt(i));
               i++; 
            }
            else {
                System.out.println( s.charAt(j));
                j--;
            }
        }
        return dp[0][s.length()-1];
    }
}

             
