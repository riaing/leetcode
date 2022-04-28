/*
题：给一个dictionary里面有n多string，找出其中最长的string，string每次删一个字母之后的结果仍在dic里。直到剩一个字母。 eg: ("abz", "abc", "zbc", "ab", "zzbc", "bc", "c"); // zzbc

思路一：减法。对于dic的每个string，从每位上删除一个字母，看transform的string在不在dic里，在就继续直到只剩一个字母
DFS + memo 
Time: exponential - 对于最长string（len=m）来说，第一层有m个选择，第二层m-1...1 -> O(m!).总共N个词：O（N*M!)。注意：M！的情况是每条path都走的通。实际上的剪枝（transformed不在dictition里）会堵死那条path，就end as O(1)
加了memorization后：string每次转换后，只需要O（1）的判断时间：不在dic里V.S在memory里。所以最长String（m）
每个字母都删除后判断，总时间是O（m）。总共n个string ——> O（n*m) 

思路二：加法。类似 https://leetcode.com/problems/word-ladder/
先找到dic里所有长度为1的单词，看他们能否组成长度为2的，再继续组成len=3... 看组成的最长单词是多少
运用BFS 

*/
public class Main {
    static Set<String> validWords = new HashSet<String>(); // word can be transformed, 
    static Set<String> invalidWords = new HashSet<String>();// word CANNOT be transformed, 
    
    public static void main(String[] args) {
        List<String> input = Arrays.asList("i", "in", "sin", "sing", "sting", 
                                                           "string", "staring", "starling", "tarling"); // starling
      
        // List<String> input = Arrays.asList("abz", "abc", "zbc", "ab", "zzbc", "bc", "c"); // zzbc

        System.out.println("result " + longestWord(input));
        System.out.println("invalidSet " + invalidWords);
    }
    
    
    // assume input must be in dic 
    public static String longestWord(List<String> input) {
        Set<String> dic = new HashSet<String>(input);
        // for everyword, check if it's transformantion is in dic 
        String res = ""; 
        for (String s : input) {
            if (inDic(s, dic, validWords, invalidWords)) {
                if (s.length() > res.length()) {
                    res = s;
                }
            }
        }
        return res; 
    }
    
    private static boolean inDic(String word, Set<String> dic, Set<String> validWords, Set<String> invalidWords) {
        if ((word.length() == 1 && dic.contains(word)) || validWords.contains(word)) {
            return true;
        }
        if (invalidWords.contains(word)) {
            System.out.println("word in falseSet early return: "+ word);
            return false;
        }
        for (int i = 0; i < word.length(); i++) { // remove char at i
            String transformed = word.substring(0, i) + word.substring(i+1);
            if (dic.contains(transformed) && inDic(transformed, dic, validWords, invalidWords)) {
                validWords.add(word);
                return true;
            }
        }
        invalidWords.add(word);
        System.out.println("word " + word + " invalid. add to set: " + invalidWords);
        return false;
    }
}
