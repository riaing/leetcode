https://leetcode.com/playground/AtWjFwpm 
面经： 面经：  https://productive-horse-bb0.notion.site/Roblox-Karat-2021-5-2022-2-9b07dcbba3634de080c3854c1293d0dc   

/*
麻将题：只有一对pair + 0或多对triple的组合时，返回true
Test cases: 
s 11133555
1 true
s 111333555
false
s 00000111
true
s 11122333
true
s 11223344555
false
s 99999999
true
s 999999999
false
s 9
false
s 99
1 true
s 000022
false
s 888889
false
s 889
false
s 44888888
true
s 77777777777777
true
s 1111111
false
s 1111122222
false
*/
/* 消消乐
While your players are waiting for a game, you've developed a solitaire game for the players to pass the time with.
The player is given an NxM board of tiles from 0 to 9 like this:
  4   4   4   4
  5   5   5   4
  2   5   7   5
The player selects one of these tiles, and that tile will disappear, along with any tiles with the same number that are connected with that tile (up, down, left, or right), and any tiles with the same number connected with those, and so on. For example, if the 4 in the upper left corner is selected, these five tiles disappear
 >4< >4< >4< >4<
  5   5   5  >4<
  2   5   7   5
If the 5 just below it is selected, these four tiles disappear. Note that tiles are not connected diagonally.
  4   4   4   4
 >5< >5< >5<  4
  2  >5<  7   5
Write a function that, given a grid of tiles and a selected row and column of a tile, returns how many tiles will disappear.
grid1 = [[4, 4, 4, 4],
         [5, 5, 5, 4],
         [2, 5, 7, 5]]
disappear(grid1, 0, 0)  => 5
disappear(grid1, 1, 1)  => 4
disappear(grid1, 1, 0)  => 4
This is the grid from above.

Additional Inputs
grid2 = [[0, 3, 3, 3, 3, 3, 3],
         [0, 1, 1, 1, 1, 1, 3],
         [0, 2, 2, 0, 2, 1, 4],
         [0, 1, 2, 2, 2, 1, 3],
         [0, 1, 1, 1, 1, 1, 3],
         [0, 0, 0, 0, 0, 0, 0]]

grid3 = [[0]]

grid4 = [[1, 1, 1],
         [1, 1, 1],
         [1, 1, 1]]

All Test Cases
disappear(grid1, 0, 0)  => 5
disappear(grid1, 1, 1)  => 4
disappear(grid1, 1, 0)  => 4
disappear(grid2, 0, 0)  => 12
disappear(grid2, 3, 0)  => 12
disappear(grid2, 1, 1)  => 13
disappear(grid2, 2, 2)  => 6
disappear(grid2, 0, 3)  => 7
disappear(grid3, 0, 0)  => 1
disappear(grid4, 0, 0)  => 9

N - Width of the grid
M - Height of the grid
*/
import java.io.*;
import java.util.*;

public class Solution {

 // 麻将题 
  public static boolean find(String s) {
    // 1. sort 
    char tmp[] = s.toCharArray(); 
    Arrays.sort(tmp); 
    s = new String(tmp); 

    int pair = 0; 
    int count =0; 
    for (int i = 0; i <= s.length(); i++) {
      if (i == s.length() || (i != 0 && s.charAt(i-1) != s.charAt(i))) {
        // System.out.println("count " + count); 
        // deal pair 
        int check = count % 3; 
        if (check == 1) {
          return false; 
        }
        if (check == 2) {
          pair++;
        }
        count = 1; 
      }
      else {
        count++;
      }
    }
    return pair == 1; 
  }
  
    // 消消乐
    public static int delete(int[][] grid, int row, int col) {
        int num = grid[row][col];
        Queue<int[]> q = new LinkedList<int[]>();
        int[] curlocal = {row, col};
        q.offer(curlocal); 
        int count = 1; 
        while (q.size() != 0) {
          int[] curIndex = q.poll();
          // check neibor 

          int[] rowNeibor = {1, -1, 0 ,0}; 
          int [] colNeibor = {0, 0, 1, -1};
          for (int i = 0; i < rowNeibor.length; i++) {
            for (int j = 0; j < colNeibor.length; j++) {
              int[] neibor = curIndex[0] + i, curIndex[1]
            }
          }
        }
  }

  public static void main(String[] argv) {
    // input problem 2
    int[][] grid1 = {{4, 4, 4, 4},
                    {5, 5, 5, 4},
                    {2, 5, 7, 5}};
    int[][] grid2 = {{0, 3, 3, 3, 3, 3, 3},
                    {0, 1, 1, 1, 1, 1, 3},
                    {0, 2, 2, 0, 2, 1, 4},
                    {0, 1, 2, 2, 2, 1, 3},
                    {0, 1, 1, 1, 1, 1, 3},
                    {0, 0, 0, 0, 0, 0, 0}};
    int[][] grid3 = {{0}};
    int[][] grid4 = {{1, 1, 1},
                    {1, 1, 1},
                    {1, 1, 1}};

    // input problem 1
    String tiles1 = "11133555"; // t
    String tiles2 = "111333555"; // f
    String tiles3 = "00000111"; // t 3 0 , 5 1 
    String tiles4 = "13233121"; // t
    String tiles5 = "11223344555"; // f
    String tiles6 = "99999999"; // t length = 8 
    String tiles7 = "999999999"; // f 
    String tiles8 = "9"; //f 
    String tiles9 = "99";
    String tiles10 = "000022";
    String tiles11 = "888889";
    String tiles12 = "889";
    String tiles13 = "88888844";
    String tiles14 = "77777777777777";
    String tiles15 = "1111111";
    String tiles16 = "1111122222";
    
    boolean checkRes2 = find(tiles1);
    System.out.println("1 " + checkRes2);

        checkRes2 = find(tiles2);
    System.out.println(checkRes2);

         checkRes2 = find(tiles3);
    System.out.println(checkRes2);

         checkRes2 = find(tiles4);
    System.out.println(checkRes2);

         checkRes2 = find(tiles5);
    System.out.println(checkRes2);

         checkRes2 = find(tiles6);
    System.out.println(checkRes2);

         checkRes2 = find(tiles7);
    System.out.println(checkRes2);

         checkRes2 = find(tiles8);
    System.out.println(checkRes2);

         checkRes2 = find(tiles9);
    System.out.println("1 " + checkRes2);

        checkRes2 = find(tiles10);
    System.out.println(checkRes2);

         checkRes2 = find(tiles11);
    System.out.println(checkRes2);

         checkRes2 = find(tiles12);
    System.out.println(checkRes2);

         checkRes2 = find(tiles13);
    System.out.println(checkRes2);

         checkRes2 = find(tiles14);
    System.out.println(checkRes2);

         checkRes2 = find(tiles15);
    System.out.println(checkRes2);

         checkRes2 = find(tiles16);
    System.out.println(checkRes2);

  }
}
