Given two words (beginWord and endWord), and a dictionary’s word list, find the length of shortest transformation sequence from beginWord to endWord, such that:

Only one letter can be changed at a time.
Each transformed word must exist in the word list. Note that beginWord is not a transformed word.
Note:

Return 0 if there is no such transformation sequence.
All words have the same length.
All words contain only lowercase alphabetic characters.
You may assume no duplicates in the word list.
You may assume beginWord and endWord are non-empty and are not the same.
Example 1:

Input:
beginWord = "hit",
endWord = "cog",
wordList = ["hot","dot","dog","lot","log","cog"]

Output: 5

Explanation: As one shortest transformation is "hit" -> "hot" -> "dot" -> "dog" -> "cog",
return its length 5.
Example 2:

Input:
beginWord = "hit"
endWord = "cog"
wordList = ["hot","dot","dog","lot","log"]

Output: 0

Explanation: The endWord "cog" is not in wordList, therefore no possible transformation.

--------------------------DFS解法，超时-------------------------------------------------------------
// k - word length; n - list length 
//time: 每一层有 26*k，总共n层， -> O(26*k)^n
class Solution {
    int res = Integer.MAX_VALUE;
    public int ladderLength(String beginWord, String endWord, List<String> wordList) {
        search(beginWord, endWord, new HashSet<String>(wordList), 0);
        return res == Integer.MAX_VALUE ? 0 : res + 1;
        
    }
    
    private void search(String beginWord, String endWord, Set<String> wordSet, int curRes) {
        if (beginWord.equals(endWord)) {
            res = Math.min(res, curRes);
            return;
        }
        
        for (int level = 0; level < beginWord.length(); level++) {
            for (char i = 'a'; i <= 'z'; i ++) {
                String newWord = beginWord.substring(0, level) + i + beginWord.substring(level+1);
            
                if (wordSet.contains(newWord)) {
                    wordSet.remove(newWord);
                    search(newWord, endWord, wordSet, curRes+1);
                    wordSet.add(newWord);
                }
            }
        }
     
    }
    
    ----------------------------------------BFS---------------------------------------------------------
/*
n - list length。 k - string length
Q最长为n，对Q的每个元素，call transferWord 找它的neibor：26*K 或者N 取小（这里假设26*k）小
所以总共N*26*k
*/
class Solution {
    public int ladderLength(String beginWord, String endWord, List<String> wordList) {
        Set<String> dic = new HashSet<>(wordList);
        Queue<String> q = new LinkedList<>();
        Set<String> visited = new HashSet<>();
        q.offer(beginWord);
        visited.add(beginWord);
        int step = 1; // 算上自己 
        if (beginWord.equals(endWord)) {
            return 0;
        }
        
        while (!q.isEmpty()) { // O(n) 访问每个node
            int size = q.size();
            for (int i = 0; i < size; i++) {
                String cur = q.poll();
                List<String> transferred = transfer(cur, dic,visited);
                for (String s :transferred) {
                    if (s.equals(endWord)) {
                        return ++step;
                    }
                    q.offer(s);
                }
            }
            step++;
        }
        return 0; 
    }
    
    // 改用找到所有A的transform word，查看哪些在wordset里也可以。那就是o (26*k)
    private List<String> transfer(String cur, Set<String> dic, Set<String> visited) {
        List<String> res = new ArrayList<>();
        for (int i = 0; i < cur.length(); i++) {
            for (char letter = 'a'; letter <= 'z'; letter++) {
                String cand = cur.substring(0, i) + letter + cur.substring(i+1);
               
                if (dic.contains(cand) && !visited.contains(cand)) {
                    visited.add(cand);
                     // System.out.println("cir " + cur + " " + cand);
                    res.add(cand);
                }
            }
        }
        return res; 
    }
}

--------------------- 2022 BFS 比上面复杂一写的解法，但时间一样- ----------------------------------------
    /*
n - list length。 k - string length
Q最长为n，对Q的每个元素，call transferWord 找它的neibor：26*K 或者N 取小（这里假设26*k）小
所以总共N*26*k
*/
class Solution {
    public int ladderLength(String beginWord, String endWord, List<String> wordList) {
       // 0. wordList to set for easier check 
        Set<String> wordListSet = new HashSet<String>(wordList);
       // 1. Map of index -> possible letters 
        Map<Integer, Set<Character>> map = new HashMap<Integer, Set<Character>>();
        for (int i = 0; i < beginWord.length(); i++) {
            map.put(i, new HashSet<Character>());
            for (String word : wordList) {
                char cur = word.charAt(i);
                Set<Character> curSet = map.get(i);
                curSet.add(cur);
            }
        }
        
        Set<String> visited = new HashSet<String>(); 
        visited.add(beginWord);
        Queue<String> q = new LinkedList<String>();
        q.offer(beginWord);
        int transferCnt = 1;// 最开始包括自己 
        while (q.size() != 0) {
            int size = q.size();
            transferCnt++; 
            for (int i = 0; i < size; i++) {
                String cur = q.poll(); 
                // transfer to possible words 
                List<String> transfered = transferWord(cur, wordListSet, map); 

                // 判断，并加进q
                for (String transfer : transfered) {
                    if (visited.contains(transfer)) {
                        continue;
                    }
                    if (transfer.equals(endWord)) {
                        return transferCnt; 
                    }
                    // 加进q
                    visited.add(transfer);
                    q.offer(transfer);
                }
            }
        }
        return 0; 
    }
    
    // O(26*k) k - string length 
    private List<String> transferWord(String cur, Set<String> wordListSet, Map<Integer, Set<Character>> map) {
        List<String> res = new ArrayList<String>();
        // transfer 每一位数,如果在wordList里，则是个成功的transfer
        for (int i = 0; i < cur.length(); i++) {
            char curChar = cur.charAt(i);
            for (Character potential : map.get(i)) {
                if (potential != curChar) {
                    String candidate = cur.substring(0, i) + potential + cur.substring(i+1);
                    if (wordListSet.contains(candidate)) {
                        res.add(candidate);   
                    }
                }
            }
        }
        return res; 
    }
}
