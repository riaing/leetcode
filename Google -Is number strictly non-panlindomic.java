import java.io.*;
import java.security.DrbgParameters.Reseed;
import java.util.*;

/*
 * F1: is order matter on performance? start from base 2->n-2, or n-2 -> 2? 
        A: 从尾开始，因为 高base的结果长度更短 -> palindorme check will be faster -> if check fails, function will abort faster 
   F2: function call will be frequent, how to improve? 
       A : 用memo map   
   F3: how to handle base 16 number? and what about base 12345?  
      A:  如果是base 16到更大，n transfer 完后可能一个数字多于1位。eg：transfer成10，13，14这样子。解决办法：transfer完后每个数字存在array中。transfer完后就变成[10,13,14]

 */

class Solution {
  public static void main(String[] args) {
      boolean res = strictlyNonPalindrome(7);
      System.out.println("final: " + res );
    
  }
  // Main function 
  public static boolean strictlyNonPalindrome(int n) {
    Map<String, Boolean> memo = new HashMap<>();
    for (int i = 2; i <= n-2; i++) {
      String cur = getRepresentive(n, i);
      boolean curRes = isNonPalindrome(cur, memo);
      if (curRes) { // 其中一位不是non-isNonPalindrome了
       System.out.println(" map: " + memo);
        return false;
      }
    }
    System.out.println(" map: " + memo);
    return true; 
  }

 // 将十进制换成 base 进制
  private static String getRepresentive(int n, int base) {
    // 方法一： java自带
    String test = Integer.toString(n, base);
  
    // 方法二（考点）：自己写。每次取余数，然后原数/base
    // int origin = n;
    // StringBuilder b = new StringBuilder();
    // while (n != 0) {
    //     int reminder = n % base; 
    //     int quotient = n / base;
    //     b.insert(0, reminder);
    //     n = quotient; 
    // }
    // System.out.println("number " + origin + " with base " + base + " : " + b.toString());
    // return b.toString();

    return test;
  }
  
  // 思考：本来想用一个map来存处理过的结果。但是其实s.substring()要copy出string，已经是O（n）了，用map反而不好。这里保留map code，实际中就之别暴力对比更好.可以在最开始用map查。
  // 此解法问题（followup3）如果是base 16到更大，n transfer 完后可能一个数字多于1位。eg：transfer成10，13，14这样子。解决办法：transfer完后每个数字存在array中。transfer完后就变成[10,13,14]
  private static boolean isNonPalindrome(String s, Map<String, Boolean> memo) {
    int right = s.length() - 1;
    int left = 0;
    while (left < right) {
      if (memo.containsKey(s.substring(left, right+1))) { // s.substring 需要copy，已经是O（n）了
        System.out.println("string " + s.substring(left, right+1) + " map res: " + memo.get(s.substring(left, right+1)));
        return memo.get(s.substring(left, right+1));
      }

      if (s.charAt(left) != s.charAt(right)) {
        memo.put(s, false);
        return false;
      }
      left ++;
      right--;
    }
    memo.put(s, true);
    return true;
  }

}
