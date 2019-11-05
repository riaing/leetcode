import java.io.*;
import java.util.*;

/*

Part 2: 
Given two strings where the transform are possible, implement a function 'is_temporary_needed(a, b)'. 
eg: is_temporary_needed('ab', 'ba') == True,  is_temporary_needed('ab', 'bc') == False. 
*/

class Solution {
  public static void main(String[] args) {
    List<String[]> input = new ArrayList<>();    
    input.add(new String[]{"abca", "dced"}); // f
    input.add(new String[]{"a", "a"});  // f 
    input.add(new String[]{"ab", "bc"}); // f
    // * Candidates will maintain a 'seen' set, but don't reset it when looking at new characters. (eg: is_temporary_needed('ac', 'ba'))
    input.add(new String[]{"ac", "ba"});  // f
    input.add(new String[]{"abc", "def"});  // f
  
    input.add(new String[]{"abc", "bad"}); // t
    input.add(new String[]{"abc", "bca"}); // t
    input.add(new String[]{"ab", "ba"}); // t
    input.add(new String[]{"badc", "abcd"}); // t 
    input.add(new String[]{"abc", "acb"}); // t 

    for (String[] i : input) {
        System.out.println(is_temporary_needed(i[0],i[1]) + " - " +  i[0] + "," + i[1]);
    }  

  }
    // O(n) - n is the number of lower case letters = 26
    public static boolean is_temporary_needed(String s, String t) {
      // construct 1-1 mapping 
      Map<Character, Character> charMap = new HashMap<Character, Character>();
      for (int i = 0; i < s.length(); i++) {
          // a-a doesn't put into map 
          if (s.charAt(i) != t.charAt(i) && !charMap.containsKey(s.charAt(i))) {
              charMap.put(s.charAt(i), t.charAt(i));
          }
      }

      // detect loop 
      Set<Character> seen = new HashSet<Character>();
      for (Character key : charMap.keySet()) {
          // if already shown in previous loop detection, then skip extra computing 
          if (seen.contains(key)) {
            continue;
          }
          // reset seen set if meet a new char, forget to do this will lead to ac - ba return true 
          seen = new HashSet<Character>(); 
          if (hasLoop(key, charMap, seen)) {
              return true;
          }
      }
      return false; 
  }
    
   /* 
  // way 1: recursion 
    private static boolean hasLoop(Character c, Map<Character, Character> charMap, Set<Character> seen) {
       if (!charMap.containsKey(c) || seen.contains(c)) {
           return false;
       }
        seen.add(c);
        char mappedChar = charMap.get(c);
        if (seen.contains(mappedChar)) {
            return true;
        }
        return hasLoop(mappedChar, charMap, seen);
    }
    */
  
    // way 2: iteration 
    private static boolean hasLoop(Character c, Map<Character, Character> charMap, Set<Character> seen) {
      seen.add(c);
      while (charMap.containsKey(c)) {
        char mappedChar = charMap.get(c);
        if (seen.contains(mappedChar)) {
            return true;
        }
        seen.add(mappedChar);
        c = mappedChar; 
      }
      return false;
    }
}
