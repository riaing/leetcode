Design a search autocomplete system for a search engine. Users may input a sentence (at least one word and end with a special character '#'). For each character they type except '#', you need to return the top 3 historical hot sentences that have prefix the same as the part of sentence already typed. Here are the specific rules:

The hot degree for a sentence is defined as the number of times a user typed the exactly same sentence before.
The returned top 3 hot sentences should be sorted by hot degree (The first is the hottest one). If several sentences have the same degree of hot, you need to use ASCII-code order (smaller one appears first).
If less than 3 hot sentences exist, then just return as many as you can.
When the input is a special character, it means the sentence ends, and in this case, you need to return an empty list.
Your job is to implement the following functions:

The constructor function:

AutocompleteSystem(String[] sentences, int[] times): This is the constructor. The input is historical data. Sentences is a string array consists of previously typed sentences. Times is the corresponding times a sentence has been typed. Your system should record these historical data.

Now, the user wants to input a new sentence. The following function will provide the next character the user types:

List<String> input(char c): The input c is the next character typed by the user. The character will only be lower-case letters ('a' to 'z'), blank space (' ') or a special character ('#'). Also, the previously typed sentence should be recorded in your system. The output will be the top 3 historical hot sentences that have prefix the same as the part of sentence already typed.


Example:
Operation: AutocompleteSystem(["i love you", "island","ironman", "i love leetcode"], [5,3,2,2]) 
The system have already tracked down the following sentences and their corresponding times: 
"i love you" : 5 times 
"island" : 3 times 
"ironman" : 2 times 
"i love leetcode" : 2 times 
Now, the user begins another search: 

Operation: input('i') 
Output: ["i love you", "island","i love leetcode"] 
Explanation: 
There are four sentences that have prefix "i". Among them, "ironman" and "i love leetcode" have same hot degree. Since ' ' has ASCII code 32 and 'r' has ASCII code 114, "i love leetcode" should be in front of "ironman". Also we only need to output top 3 hot sentences, so "ironman" will be ignored. 

Operation: input(' ') 
Output: ["i love you","i love leetcode"] 
Explanation: 
There are only two sentences that have prefix "i ". 

Operation: input('a') 
Output: [] 
Explanation: 
There are no sentences that have prefix "i a". 

Operation: input('#') 
Output: [] 
Explanation: 
The user finished the input, the sentence "i a" should be saved as a historical sentence in system. And the following input will be counted as a new search. 

Note:
The input sentence will always start with a letter and end with '#', and only one blank space will exist between two words.
The number of complete sentences that to be searched won't exceed 100. The length of each sentence including those in the historical data won't exceed 100.
Please use double-quote instead of single-quote when you write test cases even for a character input.
Please remember to RESET your class variables declared in class AutocompleteSystem, as static/class variables are persisted across multiple test cases. Please see here for more details

------------------------------------------------------------------------------------------------------------------------------------------------------
解法：和word square一样，关键在于怎么构造trie：prefix作为node，并且用map存这个prefix的String（key）和times（value）。在constructor中
insert predifine的String，并且用一个static variable来记录每次input后更新的string。当输入#时，insert当前的string进system，reset。通过
Priority Queue找到top 3的String。

难点：HashMap和priority Queue的combine运用，comparator的写法，StringA.compareTo(StringB)比的是第一个不一样的char的Ascii number。

以下comment部分是用pair来存（String，times）而非Map。但问题是当出现重复String并且要更新times时不好用。用Map更简单

class AutocompleteSystem {
    String s; 
    TrieNode root;
    
    private static List EMPTY_LIST = new ArrayList<>();
    private static int TOP = 3;

    public AutocompleteSystem(String[] sentences, int[] times) {
        s = "";
        root = new TrieNode();
        for (int i = 0; i < sentences.length; i++) {
            insert(root, sentences[i], times[i]);
        }
    }
    
    public List<String> input(char c) {
        if (c == '#') {
            // record the data into the system. 
            insert(root, s, 1);
            // reset the string
            s = "";
            return EMPTY_LIST; 
        }
        s += c;
        return searchKTop(s, root, TOP);   
    }
    
    private int getIndex(char c) {
        return c == ' ' ? 26 : c - 'a';
    }
    
    private List<String> searchKTop(String s, TrieNode root, int k) {
        TrieNode cur = root;
        for (char c: s.toCharArray()) {
            int index = getIndex(c);
            
            if (cur.children[index] == null) {
                return new ArrayList<>();
            }
            cur = cur.children[index];
        }
        
        // create priority queue, ordering map entries with respect to the frequency or string order if frequency is the same 
        PriorityQueue<Map.Entry<String, Integer>> queue = new PriorityQueue<Map.Entry<String, Integer>>(
        new Comparator<Map.Entry<String, Integer>>() {
            @Override 
            public int compare(
                Map.Entry<String, Integer> e1, Map.Entry<String, Integer> e2) {
                // first ordered by frequency, if frequency the same, then by string alphebetic order. 
             return e2.getValue() - e1.getValue() == 0 ? e1.getKey().compareTo(e2.getKey()) :  e2.getValue() - e1.getValue();
            } 
        });
        
        // insert in the queue
        for (Map.Entry entry : cur.startWith.entrySet()) {
            queue.offer(entry);
        }
        // poll the top k
         List<String> res = new ArrayList<>();
        for (int i = 0; i < Math.min(TOP, cur.startWith.size()); i++) {
            res.add(queue.poll().getKey());
        }
        return res; 
        
        // List<Pair> candidate = new ArrayList<>(cur.startWith);
        // // Now we need to sort the candidate pair by their times, or ASCII code if
        // // times are the same. 
        // Collections.sort(candidate, (a, b) -> 
        //                  (b.times != a.times 
        //                     ? b.times - a.times : a.str.compareTo(b.str)));
        // // If the list < 3(TOP), return as much as have 
        // List<String> res = new ArrayList<>();
        // for (int i = 0; i < Math.min(TOP, candidate.size()); i++) {
        //     res.add(candidate.get(i).str);
        // }
        // return res; 
    }
    
   
    private void insert(TrieNode root, String s, int times) {
        TrieNode cur = root;
        for (char c: s.toCharArray()) {
            int index = getIndex(c);
            if (cur.children[index] == null) {
                cur.children[index] = new TrieNode();
            }
            // 如果这个string已经存在，更新它的times
            cur.children[index].startWith.put(s, cur.children[index].startWith.getOrDefault(s, 0) + times);
            //上面这行等于以下写法
            // if (cur.children[index].containsKey(s)) {
            //     int oldVal = cur.children[index].startWith.get(s);
            //     cur.children[index].startWith.replace(s, oldVal +times);
            // }
            // else {
            //     cur.children[index].startWith.put(s, times));
            // }
            
            cur = cur.children[index]; 
        }
    }
	                                
    // class Pair {
    // String str;
    // int times;
    //     Pair(String s, int t) {
    //         str = s;
    //         times = t;
    //     }
    // }

    class TrieNode {
        TrieNode[] children;
        Map<String, Integer> startWith;

        public TrieNode() {
            children = new TrieNode[27];
            startWith = new HashMap<String, Integer>();
        }
    }                                       
}


    

/**
 * Your AutocompleteSystem object will be instantiated and called as such:
 * AutocompleteSystem obj = new AutocompleteSystem(sentences, times);
 * List<String> param_1 = obj.input(c);
 */

---------- 2022、6 标准trie ------------------------------
	/*
https://cheonhyangzhang.gitbooks.io/leetcode-solutions/content/642-design-search-autocomplete-system.html
本题的特点是：通过freqMap来记录每个input的freq，TrieNode上存所有的input。
*/
class TrieNode {
    boolean isLeaf; 
    HashMap<Character, TrieNode> children; // key是children的char，value是children的node
    List<String> cands; // 存prefix为当前的leave - 看题optional
    public TrieNode() {
        this.isLeaf = false; 
        this.children = new HashMap<Character, TrieNode>();
        this.cands = new ArrayList<>();
    }
}

class Trie {
    TrieNode root;
    public Trie() {
        this.root = new TrieNode();
    }
    
    // 从root开始，遍历word。 每次更新的是children
    public void insert(String word) {
        // 先拿到root
        TrieNode cur = root;
        for (int i = 0; i < word.length(); i++) {
            char c = word.charAt(i); 
            if (!cur.children.containsKey(c)) {
                cur.children.put(c, new TrieNode());
            }
            // 把当前string加到children的candiate list里，记住都是在children上操作
            cur.children.get(c).cands.add(word);
            // 更新leave 
            if (i == word.length() - 1) {
                cur.children.get(c).isLeaf = true;
            }
            // 移动到children
            cur = cur.children.get(c);
        }
    }
    
    // 遍历pre。从root中找
    public TrieNode search(String pre) {
        TrieNode cur = root; 
        for (int i = 0; i < pre.length(); i++) {
            char c = pre.charAt(i);
            if (!cur.children.containsKey(c)) {
                return null; 
            }
            // 往下移一个node
            cur = cur.children.get(c);
        }
        return cur; 
    }
}


class AutocompleteSystem {
    Trie trie = new Trie();
    Map<String, Integer> freqMap = new HashMap<>();  // 存每个input 的frequency
    String cur = ""; 
    public AutocompleteSystem(String[] sentences, int[] times) {
        // 1. 建立freqMap和Trie 
        for (int i = 0; i < sentences.length; i++) {
            freqMap.put(sentences[i], times[i]);
            trie.insert(sentences[i]); 
        }
    }
    
    public List<String> input(char c) {
        List<String> suggestions = new ArrayList<>(); 
        if (c == '#') {
            // 查看是不是新string
            if (!freqMap.containsKey(cur)) {
                freqMap.put(cur, 1);
                trie.insert(cur);
            }
            else {
                freqMap.put(cur, freqMap.get(cur) + 1);
            }
            // 复原search string
            cur = "";
        }
        else {
            cur += c; 
            // 1. 从trie中找到对应的candidate
            TrieNode node = trie.search(cur);
            // 2. 通过freqMap sort，找到top3
            suggestions = getSuggestion(node); 
        }
        return suggestions;
    }
    
    private List<String> getSuggestion(TrieNode node) {
        List<String> res = new ArrayList<>();
        if (node == null || node.cands.isEmpty()) {
            return res; 
        }
        List<String> candidates = node.cands; 
        // 1. 通过freq Map，sort 从大到小
        Comparator<String> com = new Comparator<String>() {
            @Override
            public int compare(String a, String b) {
                if (freqMap.get(a) == freqMap.get(b)) {
                    // 按照ascii 小的优先。利用string自身compareTo即可
                    return a.compareTo(b);
                }
                return freqMap.get(b) - freqMap.get(a); 
            }
        };
        Collections.sort(candidates, com); 
        // 2. 遍历拿到top 3 
        for (String s : candidates) {
            res.add(s);
            if (res.size() == 3) {
                break; 
            }
        }
        return res; 
    }
}

/**
 * Your AutocompleteSystem object will be instantiated and called as such:
 * AutocompleteSystem obj = new AutocompleteSystem(sentences, times);
 * List<String> param_1 = obj.input(c);
 */
