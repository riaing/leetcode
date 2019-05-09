import java.io.*;
import java.util.*;

/*
We have some clickstream data that we gathered on our client's website. Using cookies, we collected snippets of users' anonymized URL
histories while they browsed the site. The histories are in chronological order and no URL was visited more than once per person.

Write a function that takes two users' browsing histories as input and returns the longest contiguous sequence of URLs that appears in both.

Sample input:

user0 = ["/start.html", "/pink.php", "/register.asp", "/orange.html", "/red.html"]
user1 = ["/start.html", "/green.html", "/blue.html", "/pink.php", "/register.asp", "/orange.html"]
user2 = ["/red.html", "/green.html", "/blue.html", "/pink.php", "/register.asp"]
user3 = ["/blue.html", "/logout.php"]

Sample output:

findContiguousHistory(user0, user1)
   /pink.php
   /register.asp
   /orange.html

findContiguousHistory(user1, user2)
   /green.html
   /blue.html
   /pink.php
   /register.asp

findContiguousHistory(user0, user3)
   (empty)

findContiguousHistory(user2, user3)
   /blue.html

findContiguousHistory(user3, user3)
   /blue.html
   /logout.php
 */
------------------------------------------- DP -------------------------------------------------------------------------------------
class Solution {
  public static void main(String[] args) {
      List<String> user0 = Arrays.asList("/start.html", "/pink.php", "/register.asp", "/orange.html", "/red.html" );
      List<String> user1 = Arrays.asList("/start.html", "/green.html", "/blue.html", "/pink.php", "/register.asp", "/orange.html");
      List<String> user2 = Arrays.asList("/red.html", "/green.html", "/blue.html", "/pink.php", "/register.asp");
      List<String> user3 = Arrays.asList("/blue.html", "/logout.php");

      Solution s = new Solution();
     
      System.out.println(s.longestCommon(user0, user1));
      }
      
  
  public String longestCommon (List<String> user1, List<String> user2) {
    / * 
    longest[i][j] = end on i and j 
    longest[i][j] = if user[i] == user[j], 1+ longest[i-1][j-1]
                    if user[i] != user[j], 0
    start: longest[0..i][j] = 0; 
    return: max - longest[i][j]
    */
    int[][] longest = new int[user1.size()+1][user2.size()+1]; 
    int longestRes = 0;
    int endIndex = 0; 
    
    for(int i = 0; i <= user1.size(); i++) {
      for(int j = 0; j <= user2.size(); j++) {
        // start case 
        if(i == 0 || j == 0) {
          longest[i][j] =0;
          continue;
        }
        
        if (user1.get(i-1).equals(user2.get(j-1))) {
          longest[i][j] = 1 + longest[i-1][j-1];
          if (longestRes < longest[i][j]) {
            longestRes =  longest[i][j];
            endIndex = i-1;
          }
        }
        else {
          longest[i][j] = 0;
        }
      }
    }
    
 
    String res = "";
    for (int i = endIndex - longestRes + 1; i <= endIndex; i++) {
      res = res + " " + user1.get(i);
    }
    
    return res; 
  }
}
