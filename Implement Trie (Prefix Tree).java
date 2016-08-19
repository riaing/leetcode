mplement a trie with insert, search, and startsWith methods.

// 用is_en来表示当前节点是否存储了一个单词。
// 一个map表示此node（character）的下一层
//val表示此node的值


class TrieNode {
      char val;
      Map<Character, TrieNode> children;
      boolean is_end;
    // Initialize your data structure here.
    public TrieNode() {
        this.val = val;
        //this.children = children;
        this.children = new HashMap<Character, TrieNode>(); 
        this.is_end = false;
    }
}

public class Trie {
    private TrieNode root;

    public Trie() {
        root = new TrieNode();
        
    }

    // Inserts a word into the trie.
    public void insert(String word) {
        if(word == null){
            return;
        }
        int length = word.length();
        TrieNode pointer = root; 
        for(int i = 0; i < length; i++){
            if(!pointer.children.containsKey(word.charAt(i))){
                TrieNode cur = new TrieNode();
                cur.val = word.charAt(i);
                pointer.children.put(word.charAt(i), cur);
            }
            pointer = pointer.children.get(word.charAt(i));
        }
        pointer.is_end = true;
    }

    // Returns if the word is in the trie.
    public boolean search(String word) {
        TrieNode pointer = root; 
        for(int i =0; i <word.length(); i ++ ){
            if(!pointer.children.containsKey(word.charAt(i))){ //map的性质，如果map is empty，仍可以
            //进行containskey(),不会throwexception
                return false;
            }
            pointer = pointer.children.get(word.charAt(i));
        }
        if(!pointer.is_end){ //must end at leave 
            return false;
        }
        return true; 
        
    }

    // Returns if there is any word in the trie
    // that starts with the given prefix.
    public boolean startsWith(String prefix) {
         TrieNode pointer = root; 
        for(int i =0; i <prefix.length(); i ++ ){
            if( !pointer.children.containsKey(prefix.charAt(i))){
                return false;
            }
            pointer = pointer.children.get(prefix.charAt(i));
        }
     
        return true; 
    }
}

// Your Trie object will be instantiated and called as such:
// Trie trie = new Trie();
// trie.insert("somestring");
// trie.search("key");
