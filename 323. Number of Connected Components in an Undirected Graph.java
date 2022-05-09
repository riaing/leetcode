You have a graph of n nodes. You are given an integer n and an array edges where edges[i] = [ai, bi] indicates that there is an edge between ai and bi in the graph.

Return the number of connected components in the graph.

 

Example 1:


Input: n = 5, edges = [[0,1],[1,2],[3,4]]
Output: 2
Example 2:


Input: n = 5, edges = [[0,1],[1,2],[2,3],[3,4]]
Output: 1
 

Constraints:

1 <= n <= 2000
1 <= edges.length <= 5000
edges[i].length == 2
0 <= ai <= bi < n
ai != bi
There are no repeated edges.


------------------------------ 染色题 ------------------------------------------
/*
染色题。 9min做出
Time O（E+V） 
*/
class Solution {
    public int countComponents(int n, int[][] edges) { 
        // build the graph 
        Map<Integer, Set<Integer>> graph = new HashMap<Integer, Set<Integer>>();
        for (int i = 0; i < n; i++) {
            graph.put(i, new HashSet<Integer>());
        }
        for (int[] e : edges) {
            graph.get(e[0]).add(e[1]);
            graph.get(e[1]).add(e[0]);
        }
        
        int res = 0; 
        Set<Integer> visited = new HashSet<Integer>();
        for (int i = 0; i < n; i++) {
            if (!visited.contains(i)) {
                res++; 
                dfs(i, visited, graph);
            }
        }
        return res; 
    }
    
    private void dfs(int node, Set<Integer> visited, Map<Integer, Set<Integer>> graph) {
        visited.add(node); 
        // add it's neibor
        for (Integer n : graph.get(node)) {
            if (!visited.contains(n)) {
                dfs(n, visited, graph);
            }
        }
        
    }
}
