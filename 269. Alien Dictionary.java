https://leetcode.com/problems/alien-dictionary/submissions/ 

There is a new alien language that uses the English alphabet. However, the order among the letters is unknown to you.

You are given a list of strings words from the alien language's dictionary, where the strings in words are sorted lexicographically by the rules of this new language.

Return a string of the unique letters in the new alien language sorted in lexicographically increasing order by the new language's rules. If there is no solution, return "". If there are multiple solutions, return any of them.

A string s is lexicographically smaller than a string t if at the first letter where they differ, the letter in s comes before the letter in t in the alien language. If the first min(s.length, t.length) letters are the same, then s is smaller if and only if s.length < t.length.

 

Example 1:

Input: words = ["wrt","wrf","er","ett","rftt"]
Output: "wertf"
Example 2:

Input: words = ["z","x"]
Output: "zx"

Input: words = ["z","z"] //这里说明每个 node 都要加到 graph，尽管 indegree 是0
Output: "z"

["ab","adc"] //所有字母都要进 graph！
output: ""abcd" 

Example 3:

Input: words = ["z","x","z"]
Output: ""
Explanation: The order is invalid, so return "".
 

Constraints:

1 <= words.length <= 100
1 <= words[i].length <= 100
words[i] consists of only lowercase English letters.

----------------------------------------------- 
/*重点：1) 每个字母都应该加到 graph 里. 2) 先要建立graph！把每个 node 加到 graph 里，和把它的 degree 设为0 */
class Solution {
    public String alienOrder(String[] words) {
         // ["abc","ab"] 特殊情况，如果这种的话说明 dic 无效，直接return
        for (int i = 0; i < words.length - 1; i++) {
            String word1 = words[i];
            String word2 = words[i + 1];
            // / Check that word2 is not a prefix of word1.
            if (word1.length() > word2.length() && word1.startsWith(word2)) {
                return "";
            }
        }
        // 1. 建图，每两个 word 比较，找最高位不同的 letter 建立 edge
        Map<Character, Set<Character>> graph = new HashMap<Character, Set<Character>>();
        Map<Character, Integer> inDegrees = new HashMap<Character, Integer>();
        buildGraph(words, graph, inDegrees);
          System.out.println(graph);
        System.out.println(inDegrees);
        
        // topological sort 
        Queue<Character> sources = new LinkedList<Character>();
        for (Map.Entry<Character, Integer> entry : inDegrees.entrySet()) {
            if (entry.getValue() == 0) {
                sources.offer(entry.getKey());
            }
        }
        
        String order = "";
        while (sources.size() > 0) {
            char node = sources.poll();
            order += node;
            // reduce 1 degree of children; 
            System.out.println("node " + node);
            for (Character child : graph.get(node)) {
                inDegrees.put(child, inDegrees.get(child) - 1);
                if (inDegrees.get(child) == 0) {
                    sources.offer(child);
                }
            }
        }
        
        if (order.length() < inDegrees.size()) {
            return "";
        }
        return order;
    }
    
    
    private void buildGraph(String[] words, Map<Character, Set<Character>> graph, Map<Character, Integer> inDegrees) {
        // 1. 建立 graph，注意每个字母都要加进去
        for (String w : words) {
            for (char c : w.toCharArray()) {
                graph.putIfAbsent(c, new HashSet<Character>());
                inDegrees.putIfAbsent(c, 0);
            }
        }
        // 2. 相邻两 word 比较,建立 edge 
        for (int i = 0; i < words.length -1; i++) {
            int index = 0; 
            String w1 = words[i];
            String w2 = words[i+1];
            while (index < w1.length() && index < w2.length()) {
                char c1 = w1.charAt(index);
                char c2 = w2.charAt(index);
                if (c1 != c2) { 
                    graph.get(c1).add(c2);
                    break;
                }
                index++;
            }
        }
        
        // build inDegrees 
        for (Character key : graph.keySet()) {
            for (Character child : graph.get(key)) {
                inDegrees.put(child, inDegrees.getOrDefault(child, 0) + 1); 
            }
        }
        return;
    }
}
