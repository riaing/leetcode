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

------------------- DFS 更清楚的写法 ----------------
    class Solution {
    public boolean validTree(int n, int[][] edges) {
        // base: 构成图的条件是 V = E + 1 
        if (n != edges.length + 1) {
            return false; 
        }
        // create graph: O(N + E) N - number of node, E - edges 
        Map<Integer, Set<Integer>> neibor = new HashMap<Integer, Set<Integer>>(); 
        for (int i = 0; i < n; i++) { // O(n)
            neibor.put(i, new HashSet<Integer>());
        }
        for (int[] e : edges) { // o(E)
            int a = e[0];
            int b = e[1];
            neibor.get(a).add(b);
            neibor.get(b).add(a);
        }
        
        // create q and put 0 
        Set<Integer> visited = new HashSet<Integer>();
        for (int cur : neibor.keySet()) {
            if (!visited.contains(cur)) {
                if (!dfs(cur, -1, visited, neibor)) {
                    return false;
                }
            }
        }
        
        return true; 
    }
    
        // detect cycle 
    public boolean dfs(int node, int parent, Set<Integer> seen, Map<Integer, Set<Integer>> neibor) {
        if (seen.contains(node)) return false;
        seen.add(node);
        for (int neighbour : neibor.get(node)) {
            if (parent != neighbour) {
                boolean result = dfs(neighbour, node, seen, neibor);
                if (!result) return false;
            }
        }
        return true;
    }
}

--------------------2022 常规解法： BFS detect cycle + 查是否union ---------------------------------------
class Solution {
    public boolean validTree(int n, int[][] edges) {
        // create graph: O(N + E) N - number of node, E - edges 
        Map<Integer, Set<Integer>> neibor = new HashMap<Integer, Set<Integer>>(); 
        for (int i = 0; i < n; i++) { // O(n)
            neibor.put(i, new HashSet<Integer>());
        }
        for (int[] e : edges) { // o(E)
            int a = e[0];
            int b = e[1];
            neibor.get(a).add(b);
            neibor.get(b).add(a);
        }
        
        // create q and put 0 
        Set<Integer> visited = new HashSet<Integer>();
        Queue<Integer> q = new LinkedList<Integer>();
        visited.add(0);
        q.offer(0);
        
        //  For each of the N nodes, its adjacent edges is iterated over once.  In total, this means that all E edges are iterated over once by the inner loop. This, therefore, gives a total time complexity of O(N + E).
        while (!q.isEmpty()) {
            int cur = q.poll(); 
            for (Integer curNeibor : neibor.get(cur)) {
                if (visited.contains(curNeibor)) { // find cycle
                    return false;
                }
                q.offer(curNeibor);
                visited.add(curNeibor);
                // move the edge from neibor's set 
                if (neibor.get(curNeibor).contains(cur)) {
                    neibor.get(curNeibor).remove(cur);
                }
            }
        }
        
        return visited.size() == n; // 有可能有没有union起来的node
    }
}    
    
