
Given a non-empty string s and a dictionary wordDict containing a list of non-empty words, add spaces in s to construct a sentence where each word is a valid dictionary word. Return all such possible sentences.

Note:

The same word in the dictionary may be reused multiple times in the segmentation.
You may assume the dictionary does not contain duplicate words.
Example 1:

Input:
s = "catsanddog"
wordDict = ["cat", "cats", "and", "sand", "dog"]
Output:
[
  "cats and dog",
  "cat sand dog"
]
Example 2:

Input:
s = "pineapplepenapple"
wordDict = ["apple", "pen", "applepen", "pine", "pineapple"]
Output:
[
  "pine apple pen apple",
  "pineapple pen apple",
  "pine applepen apple"
]
Explanation: Note that you are allowed to reuse a dictionary word.
Example 3:

Input:
s = "catsandog"
wordDict = ["cats", "dog", "sand", "and", "cat"]
Output:
[]
------------------------DFS + Memo -------------------------------------------------------------------------------------
  
  /*
brute force是o(2^n); 放隔板，n长的string可以放n-1个隔板，

memorization：每次从start开始，后面的string能组成的所有情况可能之前已经考虑过了，这题是将cur之后的所有组合存map。每次到达index start时，
看看map里是不是已经有值了，有的话直接return

the keykey used is the starting index of the string currently considered and the valuevalue contains all the sentences which can 
be formed using the substring from this starting index onwards. Thus, if we encounter the same starting index from different function 
calls, we can return the result directly from the hashmap rather than going for redundant function calls.

memorization用dp来想，m[i]存前i个数能组成的所有组合，
m[i] = m[0] + m[1] + ...m[j] if i~j+1在字典里。-> O(n^2)
操作时，拿出m[j]的值，加上substring(j+1, i+1)的值，再放回m[i]的值里 -> 遍历M[J]的值时也可能是2^n worst。所以总时间o(n^2 * 2^n)

*/

class Solution {
    public List<String> wordBreak(String s, List<String> wordDict) {
        Set<String> wordDicts = new HashSet<String>(wordDict);
        return helper(s, wordDicts, 0, new HashMap<Integer, List<String>>());
    }
  
    private List<String> helper(String s, Set<String> wordDicts, int start, HashMap<Integer, List<String>> map) {
        if (map.containsKey(start)) {
            return map.get(start);
        }
        List<String> res = new ArrayList<String>();
        if (start == s.length()) {
            // return res; 注意：这里不能直接return，因为返回到上一步时，会在此基础上加上上一层的值
            res.add(""); 
            return res;
        }
        for (int end = start; end < s.length(); end++) {
            String cur = s.substring(start, end+1);
            if (wordDicts.contains(cur)) {
               
                List<String> rest = helper(s, wordDicts, end+1, map);
                for (String r : rest) {
                    res.add(cur + (r.equals("") ? "" : " " + r)); //如果是最后一层返回上来，直接把当前的值加上
                }
            }
        }
        map.put(start, res);
        return res;
    }
}

----------------------------------_变种：只要求返回一个值时，时间会是O（n^2）------------------------------------------------------------------
  private String helper(String s, Set<String> wordDicts, int start, HashMap<Integer, String> map) {
        if (map.containsKey(start)) {
            return map.get(start);
        }
       String res = null; //必须为null或者任何可以与以下if 区分的值，因为如果这里res=""的话，会分不清到底是走到了最后返回“”,还是中间哪一步走不下去了返回“”
        if (start == s.length()) { 
            res = ""; // 注意：这里不能直接return，因为返回到上一步时，会在此基础上加上上一层的值
            return res;
        }
        for (int end = start; end < s.length(); end++) {
            String cur = s.substring(start, end+1);
            if (wordDicts.contains(cur)) {
                String rest = helper(s, wordDicts, end+1, map);
                // 注意：这里需要判断返回的值是不是valid的
                if (rest != null) {
                     res = cur + " " +  rest; //如果是最后一层返回上来，直接把当前的值加上
                }  
            }
        }
        map.put(start, res);
        return res;
    }
