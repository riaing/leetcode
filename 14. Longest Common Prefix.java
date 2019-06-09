Write a function to find the longest common prefix string amongst an array of strings.

If there is no common prefix, return an empty string "".

Example 1:

Input: ["flower","flow","flight"]
Output: "fl"
Example 2:

Input: ["dog","racecar","car"]
Output: ""
Explanation: There is no common prefix among the input strings.
Note:

All given inputs are in lowercase letters a-z.

-----------------------------1,一堆string找LCP 2，followup-给个input string,和一堆string找LCP 
/*
用trie,找到一条最深的path并且满足：
1， each node along the path must contain only one child element
2，  除了最后一个node，path上的其他node不能end = true

时间：o（s）, S -> all characters in the input, build tree needs o(S), search
may also take o(S)

follow up: Given a set of keys S, find the longest common prefix among a string q and S. This LCP query will be called frequently.
解：还是建造这样的trie，判断条件除了上两条，还要让path is the prefix of q 
见下 searchLongestPrefixWithGivenWord(String word)
时间是o（s）+ O（m） -> m is the length of q 
*/
class Solution {
    class TrieNode {
        Map<Character, TrieNode> children;
        boolean end;
        
        public TrieNode() {
            this.children = new HashMap<Character, TrieNode>();
            end = false;
        }
        
        public int getChildren() {
            return children.size();
        }   
    }
    
    class Trie {
        TrieNode root; //root总是一个空的node
        public Trie(String[] words) {
            root = new TrieNode();
            for (String word : words) {
                TrieNode cur = root;
                for (char c : word.toCharArray()) {
                    if (!cur.children.containsKey(c)) {
                        cur.children.put(c, new TrieNode());
                    }
                    cur = cur.children.get(c);
                }
                cur.end = true;
            }
        }
        
        // 题目要求，找一列string的LP
        private String findLP() {
            String res = "";
            TrieNode cur = root; 
            while (cur != null && cur.children.size() == 1 && !cur.end) {
                for (Character c : cur.children.keySet()) {
                    res = res + c;
                    cur = cur.children.get(c);
                }
            }
            return res; 
        }
        
        ------------------------this is follow up-------------------------------
        // follow up: 给一个input word，判断它与set中word的 LP 
        private String searchLongestPrefixWithGivenWord(String q) {
            TrieNode cur = root; 
            String res = "";
            // 遍历q,找它在trie上的最长path并且满足path上的每个node只有一个children， 并且
            for (int i = 0; i < q.length(); i++) {
                if (cur.children.containsKey(q.charAt(i)) && cur.children.size() == 1 && !cur.end) {
                    res += q.charAt(i);
                    cur = cur.children.get(q.charAt(i));
                }
                else {
                    return res; 
                }
            }
            return res; 
        }
    }
        
    public String longestCommonPrefix(String[] strs) {
        Trie trie = new Trie(strs);
        //return trie.findLP();
        return trie.searchLongestPrefixWithGivenWord("sdf");
    }
}
