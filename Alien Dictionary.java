http://www.cnblogs.com/grandyang/p/5250200.html 
There is a new alien language which uses the latin alphabet. However, the order among letters are unknown to you. You receive a list of words from the dictionary, where words are sorted lexicographically by the rules of this new language. Derive the order of letters in this language.

For example,
Given the following words in dictionary,

[
  "wrt",
  "wrf",
  "er",
  "ett",
  "rftt"
]
 

The correct order is: "wertf".

Note:

You may assume all letters are in lowercase.
If the order is invalid, return an empty string.
There may be multiple valid order of letters, return any one of them is fine.
 

这道题让给了我们一些按“字母顺序”排列的单词，但是这个字母顺序不是我们熟知的顺序，而是另类的顺序，让我们根据这些“有序”的单词来找出新的字母顺序，
这实际上是一道有向图的问题，跟之前的那两道Course Schedule II和Course Schedule的解法很类似，我们先来看BFS的解法，
我们需要一个set来保存我们可以推测出来的顺序关系，比如题目中给的例子，我们可以推出的顺序关系有：
t->f
w->e
r->t
e->r

那么set就用来保存这些pair，我们还需要另一个set来保存所有出现过的字母，需要一个一维数组in来保存每个字母的入度，另外还要一个queue来辅助拓扑遍历，
我们先遍历单词集，把所有字母先存入ch，然后我们每两个相邻的单词比较，找出顺序pair，然后我们根据这些pair来赋度，我们把ch中入度为0的字母都排入queue中，
然后开始遍历，如果字母在set中存在，则将其pair中对应的字母的入度减1，若此时入度减为0了，则将对应的字母排入queue中并且加入结果res中，直到遍历完成，
我们看结果res和ch中的元素个数是否相同，若不相同则说明可能有环存在，返回空字符串，参见代码如下：

public class Solution {
    public class Node {
        public int degree;
        public ArrayList<Integer> neighbour = new ArrayList<Integer>();
        void Node() {
            degree = 0;
        }
    }
    public String alienOrder(String[] words) {
        Node[] node = new Node[26];
        boolean[] happen = new boolean[26];
        for (int i = 0; i < 26; i++) {
            node[i] = new Node();
        }
        //Build the Graph
        for (int i = 0; i < words.length; i++) {
            int startPoint = 0, endPoint = 0;
            for (int j = 0; j < words[i].length(); j++) {
                happen[charToInt(words[i].charAt(j))] = true;
            }
            if (i != words.length - 1) {
                for (int j = 0; j < Math.min(words[i].length(), words[i + 1].length()); j++) {
                    if (words[i].charAt(j) != words[i + 1].charAt(j)) {
                        startPoint = charToInt(words[i].charAt(j));
                        endPoint = charToInt(words[i + 1].charAt(j));
                        break;
                    }
                }
            }
            if (startPoint != endPoint) {
                node[startPoint].neighbour.add(endPoint);
                node[endPoint].degree++;
            }
        }
        //Topological Sort
        Queue<Integer> queue = new LinkedList<Integer>();
        String ans = "";
        for (int i = 0; i < 26; i++) {
            if (node[i].degree == 0 && happen[i]) {
                queue.offer(i);
                ans = ans + intToChar(i);
            } 
        }
        while (!queue.isEmpty()) {
            int now = queue.poll();
            for (int i : node[now].neighbour) {
                node[i].degree--;
                if (node[i].degree == 0) {
                    queue.offer(i);
                    ans = ans + intToChar(i);
                }
            }
        }
        for (int i = 0; i < 26; i++) {
            if (node[i].degree != 0) {
                return "";
            }
        }
        return ans;
    }
    public char intToChar(int i) {
        return (char)('a' + i);
    }
    public int charToInt(char ch) {
        return ch - 'a';
    }
}
