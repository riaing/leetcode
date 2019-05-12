There are a row of n houses, each house can be painted with one of the k colors. The cost of painting each house with a certain color is different. You have to paint all the houses such that no two adjacent houses have the same color.

The cost of painting each house with a certain color is represented by a n x k cost matrix. For example, costs[0][0] is the cost of painting house 0 with color 0; costs[1][2] is the cost of painting house 1 with color 2, and so on... Find the minimum cost to paint all houses.

Note:
All costs are positive integers.

Example:

Input: [[1,5,3],[2,9,4]]
Output: 5
Explanation: Paint house 0 into color 0, paint house 1 into color 2. Minimum cost: 1 + 4 = 5; 
             Or paint house 0 into color 2, paint house 1 into color 0. Minimum cost: 3 + 2 = 5. 
Follow up:
Could you solve it in O(nk) runtime?
  
  ------------思路都来自大神的解法 https://github.com/awangdev/LintCode/blob/master/Java/Paint%20House%20II.java ------

------------------------普通DP， o(n*k^2), n -> num of houses, k -> number of colors -----------------------------------
m[i][j] = 第i个房子选j颜色的总min cost
m[i][j] = costs[i][j] + min{m[i-1][k], where k != j }, 循环k时是O(k) 
m[0][0...j] = costs[0][0...j]
return max{m[length-1][j]} 

class Solution {
    public int minCost(int[][] costs) {
        if (costs == null || costs.length == 0) {
            return 0; 
        }
        int[][] calCost = new int[costs.length][costs[0].length];

        for (int i = 0; i < costs.length; i++) {
            for (int j = 0; j <costs[0].length; j++) {
                if (i == 0) {
                    calCost[i][j] = costs[i][j];
                    continue;
                }
                int preMin = Integer.MAX_VALUE;
                for (int k = 0; k < costs[0].length; k++) {
                    if (k != j) {
                        preMin = Math.min(preMin, calCost[i-1][k]);
                    }
                }
                calCost[i][j] = costs[i][j] + preMin;
            }
        }
        
        int res = Integer.MAX_VALUE;
        for (int color = 0; color < costs[0].length; color++) {
            res = Math.min(res, calCost[costs.length-1][color]);
        }
        return res; 
    }
}

--------------------------------优化----------------------------------------
```
#### Optimization Solution
- Time: O(NK)
- 如果已知每次都要从cost里面选两个不同的最小cost,那么先把最小两个挑出来, 就不必有第三个for loop 找 min
- 每次在数列里面找: 除去自己之外的最小值, 利用最小值/次小值的思想
- 维持2个最值: 最小值/次小值. 
- 计算的时候, 如果除掉的不是最小值的index, 就给出最小值; 如果除掉的是最小值的index, 就给出次小值.
- Every loop: 1. calculate the two min vlaues for each i; 2. calcualte dp[i][j]
- 如何想到优化: 把表达式写出来, 然后看哪里可以优化
- 另外, 还是可以rolling array, reduce space complexity to O(K)

```
/**
Time: O(n*2k); n is the number of houses, k is the number of colors. 
*/
class Solution {
    public int minCostII(int[][] costs) {
        if (costs == null || costs.length == 0) {
            return 0; 
        }
        int[][] calCost = new int[costs.length][costs[0].length];
        // start case 
        for (int j = 0; j <costs[0].length; j++) { 
            calCost[0][j] = costs[0][j];
        }
        
        for (int i = 1; i < costs.length; i++) {
            // find in the previous row, the smallest costs at which color and the second smallest costs at which color 
            int minIndex = -1;
            int secMinIndex = -1;
            for (int j = 0; j <costs[0].length; j++) {
                if (minIndex == -1 || calCost[i-1][j] < calCost[i-1][minIndex]) { 
                    secMinIndex = minIndex;
                    minIndex = j;
                } 
                else if (secMinIndex == -1 || calCost[i-1][j] < calCost[i-1][secMinIndex]){
                    secMinIndex = j;
                }
            
            }
            
            // DP process 
             for (int j = 0; j <costs[0].length; j++) {
                 if (j == minIndex) { //  // if color at minIndex is chosen for dp[i], then the remaining min is at minSecIndex
                     calCost[i][j] = calCost[i-1][secMinIndex] + costs[i][j];
                 }
                 else {
                      calCost[i][j] = calCost[i-1][minIndex] + costs[i][j];
                 }
             }
        }
        
        int res = Integer.MAX_VALUE;
        for (int color = 0; color < costs[0].length; color++) {
            res = Math.min(res, calCost[costs.length-1][color]);
        }
        return res; 
    }
}
