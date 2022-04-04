/*
Given n strings with equal length m, check how many pairs of strings are there that differ in exactly one character? 
eg: asdf, bsdf, sadf -> asdf & bsdf only one pair 


重点是：对于每个string，每次改其一个字母，对比input中的其他string看是否match。
类似题：word ladder https://leetcode.com/problems/word-ladder/ 
Time： O（n*m*26) 如果只有26个字母
*/
class Solution {
    public static List<String> findPairWithOneLetterDifference(List<String> input) {
        // 1. calculate at every position, how many letters 
        // 2. put all string into a set.
        Map<Integer, Set<Character>> indexToChar = new HashMap<Integer, Set<Character>>();
        Set<String> inputSet = new HashSet<String>();
        for (int i = 0; i < input.get(0).length(); i++) {
            indexToChar.put(i, new HashSet<Character>());
            for (String s : input) {
                char curChar = s.charAt(i);
                indexToChar.get(i).add(curChar);
                inputSet.add(s);
            }
        }
        List<String> pairs = new ArrayList<String>();
        // 3. for every string, change one letter at every index, compare with the rest in set 
        for (String toChange : inputSet) { // O(n) total strings 
            for (int i = 0; i < toChange.length(); i++) { // O(m) string length 
                for (Character c : indexToChar.get(i)) { // O(26) if only have english lower case letters 
                    if (c != toChange.charAt(i)) {
                         StringBuilder changed = new StringBuilder(toChange);
                         changed.setCharAt(i, c);
                        
                        // check if changed string match with any string from input 
                        if (inputSet.contains(changed.toString())) {
                            // bug处：这时候直接加会cause duplicate result：比如AB-AC,走到AC时又会产生AC-AB，所以要去重
                            if (!pairs.contains(changed + "-" + toChange)) { // 优化可以将pairs改成set
                                  pairs.add(toChange + "-" + changed);
                            }
                        }
                    }
                }
            }
        }

        // System.out.println(indexToChar);
        
        return pairs;
    }
    
     public static void main(String[] args) {
         //List<String> test = new ArrayList<String>(Arrays.asList("asde", "asdf", "sadf", "badf", "saee"));
         List<String> test = new ArrayList<String>(Arrays.asList("asde","saee"));
         List<String> res = findPairWithOneLetterDifference(test);
         System.out.println(res);
     } 
}
