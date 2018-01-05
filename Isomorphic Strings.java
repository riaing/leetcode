Given two strings s and t, determine if they are isomorphic.

Two strings are isomorphic if the characters in s can be replaced to get t.

All occurrences of a character must be replaced with another character while preserving the order of characters. No two characters may map to the same character but a character may map to itself.

For example,
Given "egg", "add", return true.

Given "foo", "bar", return false.

Given "paper", "title", return true.

Note:
You may assume both s and t have the same length.

class Solution {
    public boolean isIsomorphic(String s, String t) {
    //注意corner case！
        if (s == null || t == null) {
            return false;
        }
        if (s.length() != t.length()) {
            return false; 
        }
        return singleReflect(s, t) && singleReflect(t, s);
    }
    
    private boolean singleReflect(String s, String t) {
        Map<Character, Character> map = new HashMap<Character, Character>();
        for (int i =0; i < s.length(); i ++) {
            if (map.containsKey(s.charAt(i))) {
                if (map.get(s.charAt(i)) != t.charAt(i)) {
                    return false;
                }
            } 
            else {
                map.put(s.charAt(i), t.charAt(i));
            }
        }
        return true;
    }
}
