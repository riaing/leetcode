Given an array of strings words (without duplicates), return all the concatenated words in the given list of words.

A concatenated word is defined as a string that is comprised entirely of at least two shorter words in the given array.


Example 1:

Input: words = ["cat","cats","catsdogcats","dog","dogcatsdog","hippopotamuses","rat","ratcatdogcat"]
Output: ["catsdogcats","dogcatsdog","ratcatdogcat"]
Explanation: "catsdogcats" can be concatenated by "cats", "dog" and "cats"; 
"dogcatsdog" can be concatenated by "dog", "cats" and "dog"; 
"ratcatdogcat" can be concatenated by "rat", "cat", "dog" and "cat".
Example 2:

Input: words = ["cat","dog","catdog"]
Output: ["catdog"]
 

Constraints:

1 <= words.length <= 104
0 <= words[i].length <= 30
words[i] consists of only lowercase English letters.
0 <= sum(words[i].length) <= 105 


---------------------- memo ---------------------------------------------------
/*
用memo来记录所有 words的共同substring 能否被分成两个或者以上。会出现的问题是当一个string已经被分了至少一次后，剩下的半截substring不能直接套memo。比如catdog的例子：拆到catdog的dog这里时，如果直接套memo，dog返回的是false（因为dog本身不能被拆>=2个了），所以要判断下  if (start > 0 && wordsSet.contains(word.substring(start)))
时间指数级，但不知道怎么算

方法二：套用word break。每个word都有个dp，然后循环words求解： https://www.youtube.com/watch?v=hoGGwExHnnQ。缺点是没用上这题的性质，比如catdogmouse，catdogdeer中，catdog在两string中会被分别dp，但方法一的global memo就可以记录
*/
class Solution {
    public List<String> findAllConcatenatedWordsInADict(String[] words) {
        List<String> res = new ArrayList<String>();
        
        Set<String> wordsSet = new HashSet<String>(); 
        for (String s : words) {
            wordsSet.add(s);
        }
        Map<String, Boolean> memo = new HashMap<String, Boolean>();  //存的是一个string能不能被分为两个或以上    
        
        for (int i = 0; i < words.length; i++) {
            if (helper(words[i], 0, memo, wordsSet)) {
                res.add(words[i]);
            }
        }
        return res; 
    }
    
    private boolean helper(String word, int start, Map<String, Boolean> memo, Set<String> wordsSet) {
        
        boolean res = false; 
        if (start > 0 && wordsSet.contains(word.substring(start))) { // 这是重点，后半的string在words里，说明找到了match。没有的话会错：dog，cat，catdog例子中，当走到catdog的dog时，memo中dog是false（代表一个string能否被拆成两个或以上）。 这里处理的是一个string已经被分了，后半部分在words中 
            return true;
        }
        if (memo.containsKey(word.substring(start))) { 
            return memo.get(word.substring(start));
        }
        
        for (int end = start; end < word.length(); end++) {
            if (wordsSet.contains(word.substring(start, end+1)) && end - start + 1 != word.length()) {
                if (helper(word, end + 1, memo, wordsSet)) {
                    res = true;
                    break;
                }
            }
        }
        memo.put(word.substring(start), res);
        return res; 
        
    }
}
