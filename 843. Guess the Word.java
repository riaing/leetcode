This is an interactive problem.

You are given an array of unique strings wordlist where wordlist[i] is 6 letters long, and one word in this list is chosen as secret.

You may call Master.guess(word) to guess a word. The guessed word should have type string and must be from the original list with 6 lowercase letters.

This function returns an integer type, representing the number of exact matches (value and position) of your guess to the secret word. Also, if your guess is not in the given wordlist, it will return -1 instead.

For each test case, you have exactly 10 guesses to guess the word. At the end of any number of calls, if you have made 10 or fewer calls to Master.guess and at least one of these guesses was secret, then you pass the test case.

 

Example 1:

Input: secret = "acckzz", wordlist = ["acckzz","ccbazz","eiowzz","abcczz"], numguesses = 10
Output: You guessed the secret word correctly.
Explanation:
master.guess("aaaaaa") returns -1, because "aaaaaa" is not in wordlist.
master.guess("acckzz") returns 6, because "acckzz" is secret and has all 6 matches.
master.guess("ccbazz") returns 3, because "ccbazz" has 3 matches.
master.guess("eiowzz") returns 2, because "eiowzz" has 2 matches.
master.guess("abcczz") returns 4, because "abcczz" has 4 matches.
We made 5 calls to master.guess and one of them was the secret, so we pass the test case.
Example 2:

Input: secret = "hamada", wordlist = ["hamada","khaled"], numguesses = 10
Output: You guessed the secret word correctly.
 

Constraints:

1 <= wordlist.length <= 100
wordlist[i].length == 6
wordlist[i] consist of lowercase English letters.
All the strings of wordlist are unique.
secret exists in wordlist.
numguesses == 10
  
  ---------------------------- math ---------------
  /**
 * // This is the Master's API interface.
 * // You should not implement it, or speculate about its implementation
 * interface Master {
 *     public int guess(String word) {}
 * }
 */
/*
https://www.youtube.com/watch?v=8_86eMEoKeE

1. exact match是value和index都match！

if cur与secrete的similar = 3, 那么 list中与cursimilar != 3的都不是解
1. 随机选一个数来guess，返回K
2. 把list中和此数相似度不一样的剔除

*/
class Solution {
    public void findSecretWord(String[] wordlist, Master master) {
        List<String> list = Arrays.stream(wordlist).collect(Collectors.toList());
        
        Random random = new Random();
        for (int i = 0; i < 10; i++) { // o(10 *N* 6)
           int r = random.nextInt(list.size()); 
            String s = list.get(r); 
            int k = master.guess(s);
            if (k == 6) {
                return;
            }
            
            // 剔除例子中和当前相似度不一样的
            List<String> tmp = new ArrayList<>(); 
            for (int j = 0; j < list.size(); j++) { //O(N, list zise, max = 100)
                String cand = list.get(j);
                if (list.get(j).equals(s)) {
                    continue;
                }
                if (similarity(cand, s) == k) {
                    tmp.add(cand);
                }
            }
            list = tmp; 
        }
    }
     
    private int similarity(String s, String t) { // o(6)         // 说了长度为6 
        int similar = 0;
        for (int i = 0; i < s.length(); i++) {
            if (s.charAt(i) == t.charAt(i)) {
                similar++;
            }
        }
        return similar; 
    }
}
