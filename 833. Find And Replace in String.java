You are given a 0-indexed string s that you must perform k replacement operations on. The replacement operations are given as three 0-indexed parallel arrays, indices, sources, and targets, all of length k.

To complete the ith replacement operation:

Check if the substring sources[i] occurs at index indices[i] in the original string s.
If it does not occur, do nothing.
Otherwise if it does occur, replace that substring with targets[i].
For example, if s = "abcd", indices[i] = 0, sources[i] = "ab", and targets[i] = "eee", then the result of this replacement will be "eeecd".

All replacement operations must occur simultaneously, meaning the replacement operations should not affect the indexing of each other. The testcases will be generated such that the replacements will not overlap.

For example, a testcase with s = "abc", indices = [0, 1], and sources = ["ab","bc"] will not be generated because the "ab" and "bc" replacements overlap.
Return the resulting string after performing all replacement operations on s.

A substring is a contiguous sequence of characters in a string.

 

Example 1:


Input: s = "abcd", indices = [0, 2], sources = ["a", "cd"], targets = ["eee", "ffff"]
Output: "eeebffff"
Explanation:
"a" occurs at index 0 in s, so we replace it with "eee".
"cd" occurs at index 2 in s, so we replace it with "ffff".
Example 2:


Input: s = "abcd", indices = [0, 2], sources = ["ab","ec"], targets = ["eee","ffff"]
Output: "eeecd"
Explanation:
"ab" occurs at index 0 in s, so we replace it with "eee".
"ec" does not occur at index 2 in s, so we do nothing.
 

Constraints:

1 <= s.length <= 1000
k == indices.length == sources.length == targets.length
1 <= k <= 100
0 <= indexes[i] < s.length
1 <= sources[i].length, targets[i].length <= 50
s consists of only lowercase English letters.
sources[i] and targets[i] consist of only lowercase English letters.

-------------------------------直接解法， O（lgN), 除了新string space---------------------------------------
class Rule {
    int index;
    String source;
    String target; 
    
    public Rule(int index, String source, String target) {
        this.index = index; 
        this.source = source;
        this.target = target; 
    }  
}

/*
String 处理题，纯coding。一个个字母从前往后找，找到要replace的就replace

*/
class Solution {
    public String findReplaceString(String s, int[] indices, String[] sources, String[] targets) {
        // preprocessing。 Sort input by index  
        List<Rule> rule = new ArrayList<Rule>();
        for (int x = 0; x < indices.length; x++) {
            rule.add(new Rule(indices[x], sources[x], targets[x]));
        }
        Collections.sort(rule, (a, b) -> a.index - b.index);  // 注意要sort！  
        
        
        int i = 0; 
        String res = "";
        int index = 0; 
        while (i < s.length()) {
            // System.out.println("i " + i + " index " + index + " res " + res); 
            if (index >= indices.length || rule.get(index).index != i) {
                res += s.charAt(i);
                i++;
            }
            else {
                
                String replacePart = rule.get(index).source; 
                if (s.substring(i, Math.min(i + replacePart.length(), s.length())).equals(replacePart)) {
                    // replace 
                    res += rule.get(index).target;
                    index++;
                    i = i + replacePart.length();   
                 
                }
                
                else {
                   // not equal 
                    res += s.charAt(i);
                    i++;
                    index++;
                }
            }
            // System.out.println("after - i " + i + " index " + index + " res " + res); 
             
        } 
        return res; 
    }
}

---------------直接解法：O（n）， 除了新string用O（n）space on map ------
 /*
1. 把要改的index等放到map里
2. 遍历string， 要改的地方就查能不能replace

Time： O（n）
Space：O（n）
*/
class Solution {
    public String findReplaceString(String s, int[] indices, String[] sources, String[] targets) {
        // 重点 indice可能没sort
        Map<Integer, String[]> map = new HashMap<>(); // key: 要replace的index， val：0-sources， 1-target
        for (int i = 0; i < indices.length; i++) {
            map.put(indices[i], new String[]{sources[i], targets[i]});
        }
        
        StringBuilder b = new StringBuilder();
        for (int i = 0; i < s.length(); i++) {
            if (!map.containsKey(i)) {
                b.append(s.charAt(i));
                continue;
            }
            String source = map.get(i)[0];
            String target = map.get(i)[1];
            
            String toReplace = s.substring(i, i + source.length());
            if (toReplace.equals(source)) {
                b.append(target);
                i = i + source.length() - 1;
            }
            else {
                b.append(s.charAt(i)); 
            }
        }
        return b.toString();
    }
}
----------------------------从后往前，因为题目说了不会overlap所以直接在string上改，更intuitive . Time: o(lgN), 除了新string O（1）space --------------------

class Rule {
    int index;
    String source;
    String target; 
    
    public Rule(int index, String source, String target) {
        this.index = index; 
        this.source = source;
        this.target = target; 
    }       
}

/*
因为题目说了不会有overlap，所以就等于是把string切分，找到replace的那段，加上前后两段

*/
class Solution {
    public String findReplaceString(String s, int[] indices, String[] sources, String[] targets) {
        // preprocessing。 Sort input by index  
        List<Rule> rule = new ArrayList<Rule>();
        for (int x = 0; x < indices.length; x++) {
            rule.add(new Rule(indices[x], sources[x], targets[x]));
        }
        Collections.sort(rule, (a, b) -> b.index - a.index); // 从大到小sort， 从后处理起，保证不改变原string的index
        
        // 因为题目说了不会有overlap，所以就等于是把string切分，找到replace的那段，加上前后两段
        for(int i = 0; i < rule.size(); i++) {
            String sInSource = rule.get(i).source;
            int index = rule.get(i).index;
            String sInString = s.substring(index, Math.min(s.length(), sInSource.length() + index)); 
            if (sInString.equals(sInSource)) { // need replace 
                s = s.substring(0, index) + rule.get(i).target + s.substring(sInSource.length() + index);
            }
        }
        return s; 

    }
}
