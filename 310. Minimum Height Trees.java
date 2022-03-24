A tree is an undirected graph in which any two vertices are connected by exactly one path. In other words, any connected graph without simple cycles is a tree.

Given a tree of n nodes labelled from 0 to n - 1, and an array of n - 1 edges where edges[i] = [ai, bi] indicates that there is an undirected edge between the two nodes ai and bi in the tree, you can choose any node of the tree as the root. When you select a node x as the root, the result tree has height h. Among all possible rooted trees, those with minimum height (i.e. min(h))  are called minimum height trees (MHTs).

Return a list of all MHTs' root labels. You can return the answer in any order.

The height of a rooted tree is the number of edges on the longest downward path between the root and a leaf.

 

Example 1:


Input: n = 4, edges = [[1,0],[1,2],[1,3]]
Output: [1]
Explanation: As shown, the height of the tree is 1 when the root is the node with label 1 which is the only MHT.
Example 2:


Input: n = 6, edges = [[3,0],[3,1],[3,2],[3,4],[5,4]]
Output: [3,4]
 

Constraints:

1 <= n <= 2 * 104
edges.length == n - 1
0 <= ai, bi < n
ai != bi
All the pairs (ai, bi) are distinct.
The given input is guaranteed to be a tree and there will be no repeated edges.

---------------------- Topo sort ------------------------------------------------- 

// time/space: O(V+2E)
class Solution {
    public List<Integer> findMinHeightTrees(int n, int[][] edges) {
        List<Integer> res = new ArrayList<Integer>();
        // corner case: 1,  [] (only one node 0)
        if (n == 1) {
            res.add(0);
            return res; 
        }
        if (n <= 0) {
            return res; 
        }
        
        // 1. construct indegree and neibor maps 
        Map<Integer, List<Integer>> neibor = new HashMap<Integer, List<Integer>>();
        Map<Integer, Integer> indegree = new HashMap<Integer, Integer>();
        for (int i = 0; i < edges.length; i++) {
            List<Integer> curNeibors =  neibor.getOrDefault(edges[i][0], new ArrayList<Integer>());
            curNeibors.add(edges[i][1]);
            neibor.put(edges[i][0], curNeibors);
            indegree.put(edges[i][0], indegree.getOrDefault(edges[i][0], 0) + 1);
            
            curNeibors =  neibor.getOrDefault(edges[i][1], new ArrayList<Integer>());
            curNeibors.add(edges[i][0]);
            neibor.put(edges[i][1], curNeibors);
            indegree.put(edges[i][1], indegree.getOrDefault(edges[i][1], 0) + 1);
        }
        
        // 2. add indegree = 1(leaves) into queue 
        Queue<Integer> q = new LinkedList<Integer>();
        for (Integer node : indegree.keySet()) {
            if (indegree.get(node) == 1) {
                q.offer(node);
            }
        }
        
        // Remove leaves level by level and subtract each leave's children's in-degrees.
        // Repeat this until we are left with 1 or 2 nodes, which will be our answer
        while (q.size() > 0 && n > 2) {
            int borderNodeSize = q.size();
            n -= borderNodeSize;
            // 删最外面一圈
            for (int i = 0; i < borderNodeSize; i++) {
                int border = q.poll();
                // reduce it's neibor indegree and (remove it from it's neibor：这里不remove neibor了，因为remove by index in array cost O(n)) 
                for (int curNeibor : neibor.get(border)) {
                    indegree.put(curNeibor, indegree.get(curNeibor) - 1);
                    if (indegree.get(curNeibor) == 1) {
                        q.offer(curNeibor);
                    }
                }
            }
        }
        
        // while (q.size() != 0) {
        //     res.add(q.poll()); 
        // }
        // 以上可以简化成：
        res.addAll(q);
        return res; 
    }
}
