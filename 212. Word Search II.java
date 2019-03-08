Given a 2D board and a list of words from the dictionary, find all words in the board.

Each word must be constructed from letters of sequentially adjacent cell, where "adjacent" cells are those horizontally or vertically neighboring. The same letter cell may not be used more than once in a word.

Example:

Input: 
words = ["oath","pea","eat","rain"] and board =
[
  ['o','a','a','n'],
  ['e','t','a','e'],
  ['i','h','k','r'],
  ['i','f','l','v']
]

Output: ["eat","oath"]
Note:
You may assume that all inputs are consist of lowercase letters a-z. 

--------------------------------------------------------------------------------
hashmap solution： https://www.jiuzhang.com/solutions/word-search-ii/ 
-------------------------------trie solution---------------------------------------------------------------------------
  
take way: 
1, Backtracking时候, 在board[][] 上面mark就可以, 不需要开一个visited[][]
2，只dfs一遍board，看能找到几个string，直接对board DFS:   
- 每一层,都会有个up-to-this-point的string. 在Trie里面check它是不是存在。以此判断。   
- 若不存在，就不必继续DFS下去了。
解法：所以我们将每个单词insert到trie中，遍历board时，如果当前的string（其实就是单词的前缀）不属于trie中，说明当前路径组成的单词不会在words里，直接诶返回
注意：
1，trie的leave node存单词，如果某个node中有单词，说明找到了单词
2，因为可能有几条路径组成相同的单词，所以我们在找到单词加入res时要进行去重判断
time complexity： 递归：M(row)*N(col）* 4^k（word length） trie: n个word * k（word length）
space complexity: 如果有n个word并且word最长为k，trie就有n*k(字符数）个node。最后的nodes又存string，所以总空间 n*k*2

class Solution {
    class TrieNode {
        Map<Character, TrieNode> children;
        String word;
        public TrieNode() {
            children = new HashMap<Character, TrieNode>();
            word = null;
        }
    }
    
    class Trie {
        TrieNode root;
        public Trie(TrieNode root) {
            this.root = root; 
        }
        public void insert(String word) {
            TrieNode cur = root; 
            for (char c : word.toCharArray()) {
                if (!cur.children.containsKey(c)) {
                    cur.children.put(c, new TrieNode());
                }
                cur = cur.children.get(c);
            }
            cur.word = word;
        } 
    }
    
    
    public List<String> findWords(char[][] board, String[] words) {
        List<String> res = new ArrayList<String>();
        TrieNode root = new TrieNode();
        Trie trie = new Trie(root);
        for (int i = 0; i < words.length; i++) {
            trie.insert(words[i]);
        }
        for (int i = 0; i< board.length; i++) {
            for(int j = 0; j < board[0].length; j++) {
                dfs(board, i, j, res, root);
            }
        }
        
        return res;
    }
    
   
    
    private void dfs(char[][] board, int row, int col, List<String> res, TrieNode curNode) {
        if (row < 0 || row >= board.length || col < 0 || col >= board[0].length) {
            return;
        }
        if (!curNode.children.containsKey(board[row][col])) {
            return;
        }
        //说明走到了底，找到了一个string。
        if (curNode.children.get(board[row][col]).word != null) {
            //因为multiple路径可能都能形成同一个string，我们这里进行去重判断
            if (!res.contains(curNode.children.get(board[row][col]).word)) {
                 res.add(curNode.children.get(board[row][col]).word);
            }
        }
        
        if (row >= 0 && row < board.length && col >= 0 && col < board[0].length && board[row][col] != '0') {
            char tmp = board[row][col];
            board[row][col] = '0'; 
            int[] rowIndex = {1,-1,0,0};
            int[] colIndex = {0, 0, 1, -1};
            for (int i = 0; i < rowIndex.length; i++) {
                int newRow = row + rowIndex[i];
                int newCol = col + colIndex[i];
                dfs(board, newRow, newCol, res, curNode.children.get(tmp));
            }
            board[row][col] = tmp; 
        } 
    }
}
