ou are given an array of strings equations that represent relationships between variables where each string equations[i] is of length 4 and takes one of two different forms: "xi==yi" or "xi!=yi".Here, xi and yi are lowercase letters (not necessarily different) that represent one-letter variable names.

Return true if it is possible to assign integers to variable names so as to satisfy all the given equations, or false otherwise.

 

Example 1:

Input: equations = ["a==b","b!=a"]
Output: false
Explanation: If we assign say, a = 1 and b = 1, then the first equation is satisfied, but not the second.
There is no way to assign the variables to satisfy both equations.
Example 2:

Input: equations = ["b==a","a==b"]
Output: true
Explanation: We could assign a = 1 and b = 1 to satisfy both equations.
 

Constraints:

1 <= equations.length <= 500
equations[i].length == 4
equations[i][0] is a lowercase letter.
equations[i][1] is either '=' or '!'.
equations[i][2] is '='.
equations[i][3] is a lowercase letter.

------------------------------------- Graph ----------------------------------------------
class Solution {
    public boolean equationsPossible(String[] equations) {
        List<char[]> unequals = new ArrayList<char[]>();
        Map<Character, Set<Character>> map = new HashMap<Character, Set<Character>>(); // 建一个graph的map
        for (String s : equations) {
            char fir = s.charAt(0); 
            char sec = s.charAt(3);
            char sym = s.charAt(1);
            if (sym == '=') {
                map.putIfAbsent(fir, new HashSet<Character>());
                map.get(fir).add(sec);
                map.putIfAbsent(sec, new HashSet<Character>());
                map.get(sec).add(fir);
            }
            else {
                // pruning 
                if (fir == sec) {
                    return false; 
                }
                unequals.add(new char[]{fir, sec});
            }
        }
        
        // 对每个不等式，查是否两个node相连（连的话return false）
        for (char[] unequal : unequals) {
            if (map.containsKey(unequal[0])) { // 任意一个到map查都
                Set<Character> seen = new HashSet<Character>();
                seen.add(unequal[0]);
                boolean connect = connected(unequal[0], unequal[1], map, seen); 
                if (connect) {
                    return false;
                }
            }
        }
        return true; 
        
        
    }
    private boolean connected(char A, char B, Map<Character, Set<Character>> map, Set<Character> seen) {
        if (A == B) {
            return true; 
        }
        if (!map.containsKey(A) || !map.containsKey(B)) {
            return false; 
        }
        
        for (char neibor : map.get(A)) {
            if (!seen.contains(neibor)) {
                seen.add(neibor);
                if (connected(neibor, B, map, seen)) {
                    return true; 
                }
            }
        }
        return false; 
    } 
}
