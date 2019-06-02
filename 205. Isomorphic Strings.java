/*
# Two words are called isomorphic if the letters in
# one word can be remapped to get the other word.
# Isomorphic Word Pairs
# mom <-> dad
# dad <-> lol
# dog <-> cad
# mdm <-> dad
# NOT Isomorphic Word Pairs
# mom <-> dog
# dog <-> dad
# hey <-> hi
*/


// Class name must be "Main"
// Libraries included:
// json simple, guava, apache commons lang3, junit, jmock

import java.util.Map;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;
    
// 
class Main {
    public static void main(String[] args) {
        boolean res = checkIsomorphic("dog", "dad");
        System.out.println(res);
        
        List<String> words = new ArrayList<String>();
        words.add("mom");
        words.add("dad");
        words.add("lok");
        words.add("kak");
        
        System.out.println("check list: " + checkIsomorphicList(words));
    }
    
    
    // O(m) -> o(m*n), m -> length of list, n -> length of words 
    public static boolean checkIsomorphicList(List<String> words) {
        if (words == null || words.size() == 0 || words.size() == 1) {
            return true;
        }
       
        for (int i = 1; i < words.size(); i++) {
            if (!checkIsomorphic(words.get(0), words.get(i))) {
                return false;
            }
        }
        return true;       
                
    }
    
    public static boolean checkIsomorphic(String a, String b) {
        if (a.length() != b.length()) {
            return false;
        }
        Map<Character, Character> map1 = new HashMap<Character, Character>();
        Map<Character, Character> map2 = new HashMap<Character, Character>();
        
        for(int i = 0; i < a.length(); i++) {
            char c1 = a.charAt(i);
            char c2 = b.charAt(i);
            
            if (map1.containsKey(c1)) {
                if (map1.get(c1) != c2) {
                    return false;
                }
            }
            else {
                if (map2.containsKey(c2)) { // //这时a没有出现在map中，说明a在字符串s中是第一次出现，那么字符b也应该是在字符串t中第一次出现，这才符合字符串s的横向模式

                    return false;
                }
                
                map1.put(c1, c2);
                map2.put(c2, c1);
            }
        }
        return true;
    }
}
