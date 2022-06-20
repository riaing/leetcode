There are n cities. Some of them are connected, while some are not. If city a is connected directly with city b, and city b is connected directly with city c, then city a is connected indirectly with city c.

A province is a group of directly or indirectly connected cities and no other cities outside of the group.

You are given an n x n matrix isConnected where isConnected[i][j] = 1 if the ith city and the jth city are directly connected, and isConnected[i][j] = 0 otherwise.

Return the total number of provinces.

 

Example 1:


Input: isConnected = [[1,1,0],[1,1,0],[0,0,1]]
Output: 2
Example 2:


Input: isConnected = [[1,0,0],[0,1,0],[0,0,1]]
Output: 3
 

Constraints:

1 <= n <= 200
n == isConnected.length
n == isConnected[i].length
isConnected[i][j] is 1 or 0.
isConnected[i][i] == 1
isConnected[i][j] == isConnected[j][i]

------------------------------------- DFS ---------------------------
/*
graph 染色题
已经给了matrix，都不用建立neibor map了
Time: O（n^2) 
Space： O（n） visited set
*/
class Solution {
    public int findCircleNum(int[][] isConnected) {
      
        // calculate group 
        int res = 0; 
        Set<Integer> visited = new HashSet<>();
        for (int i = 0; i < isConnected.length; i++) {
            if (!visited.contains(i)) {
                res++;
                visited.add(i);
                dfs(i, visited, isConnected);
            }
        }
        return res;
    }
    
    // 把邻居node全染色
    private void dfs(int cur, Set<Integer> visited, int[][] neiborMap) {
        for (int j = 0; j < neiborMap[0].length; j++) {
            if (!visited.contains(j) && neiborMap[cur][j] == 1) {
                visited.add(j);
                dfs(j, visited, neiborMap);
            }
        }
    }
}
