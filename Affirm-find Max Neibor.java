
# For each letter, which other letter(s) appear in the most words with that letter
# In: List<String> = ['abc', 'bcd', 'cde']
# Out:
#     a: b,c
#     b: c
#     c: b,d
#     d: c
#     e: c,d
*/

/*
a : b-1
Map<Char, Map<Char, Inter>> cntMap
Map<Char, Inter> maxMap 

Map<Char, List<Char>> output
*/

import java.io.*;
import java.util.*;

/*
 * To execute Java, please define "static void main" on a class
 * named Solution.
 *
 * If you need more classes, simply define them inline.
 */

// n words
// l is the max length of word
// alphabet of size k 

// k >> l? ->n * l^2
// k << l? -> n * k^2
// O(n*min(l, k)^2 + n*l);  -- this is the min because we have to find each pairs 

class Solution {
  public static void main(String[] args) {
    ArrayList<String> strings = new ArrayList<String>();
    strings.add("abc");
    strings.add("bcd");
    strings.add("cde");

    System.out.println(solution(strings));
    
  }
  
  public static Map<Character, List<Character>> solution(List<String> strs) {
    Map<Character, Map<Character, Integer>> cntMap = new HashMap<Character, Map<Character, Integer>>();
    Map<Character, Integer> maxMap = new HashMap<Character, Integer>();
    
    // o(n*l^2)
    for(String s : strs) { // o(n) 
      // remove dup
      Set<Character> stringSet = new HashSet<Character>();
      for(char c : s.toCharArray()) { // O(n*l)
        stringSet.add(c);
      }
      String cur = "";
      for (char c : stringSet) {
        cur += c;
      }
      
      // o(l^2 /2 ) 
      // go through letters 
      for (int i = 0; i < cur.length(); i++){ 
        if (!maxMap.containsKey(cur.charAt(i))) {
          maxMap.put(cur.charAt(i), 0);
        }
        for (int j = 0; j < i; j++) { 
          // having a put function 
          char ci = cur.charAt(i);
          char cj = cur.charAt(j);
          put(cntMap, ci, cj);
          
          updateMaxCnt(maxMap, ci, cntMap.get(ci).get(cj));
          put(cntMap, cj, ci);
          updateMaxCnt(maxMap, cj, cntMap.get(cj).get(ci));
        }
      }
    }
    
    // return the result 
    Map<Character, List<Character>> output = new HashMap<Character, List<Character>>();
    
    for (Character c : cntMap.keySet()) {
      int max = maxMap.get(c);
      for(Character neibor : cntMap.get(c).keySet()) {
        if (cntMap.get(c).get(neibor) == max) {
          if (!output.containsKey(c)) {
            output.put(c, new ArrayList<Character>());
          }
          output.get(c).add(neibor);
        }
      }
    }
    return output;
  }
  
  private static void updateMaxCnt(Map<Character, Integer> maxMap, char c, int cnt) {
    if (maxMap.get(c) < cnt) {
      maxMap.put(c, cnt);
    }
  }
  
  private static void put(Map<Character, Map<Character, Integer>> cntMap, char i, char j) {
    if (!cntMap.containsKey(i)) {
      cntMap.put(i, new HashMap<>());
      cntMap.get(i).put(j, 1);
    }
    else {
      if (!cntMap.get(i).containsKey(j)) {
        cntMap.get(i).put(j,1);
      }
      else {
        cntMap.get(i).put(j, cntMap.get(i).get(j)+1);
      }
    }
  }
}

