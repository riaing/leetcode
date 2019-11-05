import java.io.*;
import java.util.*;


/* 
Part 3: this should only have  minor changes on part 2 
Implement  min_transformations(a, b) to calculate the minimum number of transformations required for a,b 
Eg: min_transformations(ab, bc) = 2, min_transformations(abc, def) = 2, min_transformations(ba, ab) = 3, min_transformations(abc, bad) = 4, min_transformations(abcd, badc) = 6

*/

class Solution {
  public static void main(String[] args) {
    
    List<String[]> input = new ArrayList<>();    
    input.add(new String[]{"abca", "dced"}); // f - 3
    input.add(new String[]{"a", "a"});  // f - 0
    input.add(new String[]{"ab", "bc"}); // f - 2
    // * Candidates will maintain a 'seen' set, but don't reset it when looking at new characters. (eg: is_temporary_needed('ac', 'ba'))
    input.add(new String[]{"ac", "ba"});  // f - 2
    input.add(new String[]{"abc", "def"});  // f - 3
  
    input.add(new String[]{"abcc", "badd"}); // t - 4
    input.add(new String[]{"abc", "bca"}); // t -4
    input.add(new String[]{"ab", "ba"}); // t -3
    input.add(new String[]{"badc", "abcd"}); // t -6  
    input.add(new String[]{"abc", "acb"}); // t - 3 
    input.add(new String[]{"acbd", "cbdc"}); // t - 5 

    
    for (String[] i : input) {
        System.out.println(min_transformations(i[0],i[1]) + " - " +  i[0] + "," + i[1]);
    }   
  }
   
  public static int min_transformations(String s, String t) {
    Set<String> differentPairs = new HashSet<String>(); 
    // find num of char differences at the same position 
    // construct 1-1 mapping 
      Map<Character, Character> charMap = new HashMap<Character, Character>();
      for (int i = 0; i < s.length(); i++) {
          // a-a doesn't put into map 
          if (s.charAt(i) != t.charAt(i) && !charMap.containsKey(s.charAt(i))) {
              charMap.put(s.charAt(i), t.charAt(i));
          }
         if (s.charAt(i) != t.charAt(i)) {
            differentPairs.add(s.charAt(i) + "" + t.charAt(i));
          }
      }
    return differentPairs.size() + numOfLoops(s, t, charMap);

  }
  
  private static int numOfLoops(String s, String t,  Map<Character, Character> charMap) {
    int num = 0; 
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
              num++;
          }
      }
      return num; 
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
