The demons had captured the princess (P) and imprisoned her in the bottom-right corner of a dungeon. The dungeon consists of M x N rooms laid out in a 2D grid. Our valiant knight (K) was initially positioned in the top-left room and must fight his way through the dungeon to rescue the princess.

The knight has an initial health point represented by a positive integer. If at any point his health point drops to 0 or below, he dies immediately.

Some of the rooms are guarded by demons, so the knight loses health (negative integers) upon entering these rooms; other rooms are either empty (0's) or contain magic orbs that increase the knight's health (positive integers).

In order to reach the princess as quickly as possible, the knight decides to move only rightward or downward in each step.

 

Write a function to determine the knight's minimum initial health so that he is able to rescue the princess.

For example, given the dungeon below, the initial health of the knight must be at least 7 if he follows the optimal path RIGHT-> RIGHT -> DOWN -> DOWN.

-2 (K)	-3	3
-5	-10	1
10	30	-5 (P)
 

Note:

The knight's health has no upper bound.
Any room can contain threats or power-ups, even the first room the knight enters and the bottom-right room where the princess is imprisoned.
-----------------------------------------------
/*
注意：track是自己练习如何记录path

https://www.youtube.com/watch?v=pt-xIS6huIg 
从尾往前走，m[i][j] -> 在i，j的最小血值
因为dungeon[i][j] + x = min{m[i+1][j], m[i][j+1]}, 所以x当前所需的值就等于 min{m[i+1][j], m[i][j+1]} - dungeon[i][j]
当m[之后] - dungeon[i][j]为负数时，说明当前dungeon[i][j]为正数，那么当前最小血值只要是1就行
所以公式是m[i][j] = Math.max(Math.min(m[i+1][j], m[i][j+1]) - dungeon[i][j], 1); 
注意特殊条件：就是公主格子的右边和下面都设为1


*/
class Solution {
    public int calculateMinimumHP(int[][] dungeon) {
        int[][] m = new int[dungeon.length+1][dungeon[0].length+1];
        for (int i = 0; i <= dungeon.length; i++) {
            for (int j = 0; j <= dungeon[0].length; j++) {
                m[i][j] = Integer.MAX_VALUE;
            }
        }
        
        //start case 
        m[dungeon.length][dungeon[0].length-1] = 1;
        m[dungeon.length-1][dungeon[0].length] = 1;
        // 用来记录path
        char[][] track = new char[dungeon.length+1][dungeon[0].length+1];
        
        for (int i = dungeon.length-1; i >= 0; i--) {
            for (int j = dungeon[0].length-1; j >= 0; j--) {
           
                m[i][j] = Math.max(Math.min(m[i+1][j], m[i][j+1]) - dungeon[i][j], 1);   
                if (m[i+1][j] < m[i][j+1]) {
                    track[i][j] = 'D';
                }
                else {
                    track[i][j] = 'R';
                } 
            }
        }
        //如何记录path
        int x = 0;
        int y = 0;
        while (x < dungeon.length && y < dungeon[0].length) {
            System.out.println("x " + x + "- y " + y);
            if (track[x][y] == 'D') {
                x = x +1;
            }
            else {
                y = y + 1;
            }
        }
        
        return m[0][0];
    }
}
