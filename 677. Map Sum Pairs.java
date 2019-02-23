Implement a MapSum class with insert, and sum methods.

For the method insert, you'll be given a pair of (string, integer). The string represents the key and the integer represents the value. 
If the key already existed, then the original key-value pair will be overridden to the new one.

For the method sum, you'll be given a string representing the prefix, and you need to return the sum of all the pairs' value whose key starts with the prefix.

Example 1:
Input: insert("apple", 3), Output: Null
Input: sum("ap"), Output: 3
Input: insert("app", 2), Output: Null
Input: sum("ap"), Output: 5

------------------trie：注意seudo root--------------------------------------------------------------------------
1,如何构建trieNode.让每一个node存目前为止的sum，每次insert一个词时，更新遍历过的node的sum
eg insert("apple", 3) -> a:3, p:3, p:3, l:3, e:3
insert("app", 2) ->a:3+2=5, p:5, p:5, l:3, e:3
求sum时，找到prefix最后一个字母所在的node，返回这个node上的sum即可
2，要注意的是 If the key already existed, then the original key-value pair will be overridden to the new one.
那么则需要我们用一个map来存每一次insert的pair,并且每次insert更新sum时，如果这个insert pair之前出现过，sum加的值应该为新val与老val的差（
也就是replace时，我们先从sum中减去老val的值再加上新val的值，也就是加上 delta = 新val - 老val）

eg insert("apple", 3) -> a:3, p:3, p:3, l:3, e:3
insert("app", 2) ->a:3+2=5, p:5, p:5, l:3, e:3
insert("apple", 4) -> 因为apple出现过，a:5+(4-3)=6, p:3+(4-3)=6, p:3+(4-3)=6, l:3+(4-3)=4, e:4

class MapSum {
    TrieNode root;
    // If the key already existed, then the original key-value pair will be overridden to the new one.
    HashMap<String, Integer> map;
    
    
    /** Initialize your data structure here. */
    public MapSum() {
        root = new TrieNode();
        map = new HashMap<String, Integer>(); 
    }
    
    public void insert(String key, int val) {
        // delta = val - map[key]
        int delta = val - map.getOrDefault(key, 0);
        map.put(key, val);
        
        TrieNode cur = root;
        for (char c : key.toCharArray()) {
            if (!cur.children.containsKey(c)) {
                cur.children.put(c, new TrieNode());
            }
            // update sum on the chlild node.
            cur.children.get(c).sum = cur.children.get(c).sum + delta;
            cur = cur.children.get(c);
        }
    }
    
    public int sum(String prefix) {
        TrieNode cur = root;
        for (char c : prefix.toCharArray()) {
            if (!cur.children.containsKey(c)) {
                return 0;
            }
            cur = cur.children.get(c);
        }
        return cur.sum;
    }
}

class TrieNode {
    int sum;
    Map<Character, TrieNode> children;
    
    TrieNode() {
        sum = 0;
        children = new HashMap<Character, TrieNode>();
    }
}

/**
 * Your MapSum object will be instantiated and called as such:
 * MapSum obj = new MapSum();
 * obj.insert(key,val);
 * int param_2 = obj.sum(prefix);
 */

