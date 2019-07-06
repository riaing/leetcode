Given n nodes labeled from 0 to n-1 and a list of undirected edges (each edge is a pair of nodes), write a function to check whether these edges make up a valid tree.

Example 1:

Input: n = 5, and edges = [[0,1], [0,2], [0,3], [1,4]]
Output: true
Example 2:

Input: n = 5, and edges = [[0,1], [1,2], [2,3], [1,3], [1,4]]
Output: false
Note: you can assume that no duplicate edges will appear in edges. Since all edges are undirected, [0,1] is the same as [1,0] and thus will not appear together in edges.

------------------------------------ DFS ---------------------------------------
/*
1, edge = n - 1
2, if above true, detect if cycle 

E  
*/
class Solution {
    public boolean validTree(int n, int[][] edges) {
        if (edges == null) {
            return false;
        }
        
        if (edges.length == 0) {
            return n == 1;
        }
        
        if (edges.length != n -1) {
            return false;
        }
        
        boolean[] used = new boolean[edges.length];
        Set<Integer> nodes = new HashSet<Integer>();
        for (int i = 0; i < edges.length; i++) {
            if (!used[i]) {
                used[i] = true;
                nodes.add(edges[i][0]);
                nodes.add(edges[i][1]);
                if (detectCycle(edges[i][1], edges, nodes, used)) {
                    return false;
                }
            }
        }
        return true;
    }
    
    private boolean detectCycle(int cur, int[][] edges, Set<Integer> nodes, boolean[] used) {
        for (int i = 0; i < edges.length; i++) {
            if (!used[i] && (edges[i][0] == cur || edges[i][1] == cur)) {
                int newCur = edges[i][0] == cur ? edges[i][1] : edges[i][0];
                used[i] = true;
                if (nodes.contains(newCur)) {
                    return true;
                }
                nodes.add(edges[i][0]);
                nodes.add(edges[i][1]);
                if (detectCycle(newCur, edges, nodes, used)) {
                    return true;
                }
            }
        }
        return false;
    }
}
