/*
The string chatOfPlayers represents a combination of the string "chat" from
different players. Any player can say "chat", and if multiple players say
"chat" at the same time, the letters of "chat" can overlap. Return the minimum
number of different players to finish all the "chat"s in the given string.
A valid "chat" means a player is printing 4 letters ‘c’, ’h’, ’a’, ’t’
sequentially. The players have to print all four letters to finish a
chat. If the given string is not a combination of valid "chat" return -1.
Example 1:
Input: chatOfPlayers = "chatchat"
Output: 1
Explanation: One player yelling "chat" twice.
Example 2:
Input: chatOfPlayers = "chcathat"
Output: 2
Explanation: The minimum number of players is two.
The first player could yell "CHcAThat".
The second player could yell later "chCatHAT".
Example 3:
Input: chatOfPlayers = "chatchht"
Output: -1
Explanation: The given string is ‍‌‍‍‌‍‍‌‍‍‌‍‌‍‍‌‌‍an invalid combination of "chat" from different players.

思路： 
1. O（N) 的方法可以维护一个chat count， 遇到 c 加一， 遇到 t 减一， 取这个count的最大值就是答案
2. 因为是chat sequentially，所以应该是在任何时候count('c') >= count('h') >= count('a') >= count('t')，否则就是invalid的
 */

import java.io.*;
import java.util.*;

/*
 * To execute Java, please define "static void main" on a class
 * named Solution.
 *
 * If you need more classes, simply define them inline.
 */

class Solution {

  public static int chatPlayer(String s) {
    Map<Character, Integer> map = new HashMap<>(); // c -> count
    int res = 0;
    int tmpCount = 0; // c +1, t -1 
    map.put('c', 0);
    map.put('h', 0);
    map.put('a', 0);
    map.put('t', 0);
    for (char c : s.toCharArray()) {
      map.put(c, map.getOrDefault(c, 0) + 1);
      // validation check 
      if (!(map.get('c') >= map.get('h') && map.get('h') >= map.get('a') && map.get('a') >= map.get('t'))) {
        return -1;
      }
      if (c == 'c') {
        tmpCount++;
      }
      else if(c == 't') {
        tmpCount--;
      }
      res = Math.max(res, tmpCount);
    }
    return res; 
  }
  public static void main(String[] args) {
    System.out.println(chatPlayer("chchatachatt")); // 

    // for (String string : strings) {
    //   System.out.println(string);
    // }
  }
}
