You are given a network of n nodes, labeled from 1 to n. You are also given times, a list of travel times as directed edges times[i] = (ui, vi, wi), where ui is the source node, vi is the target node, and wi is the time it takes for a signal to travel from source to target.

We will send a signal from a given node k. Return the time it takes for all the n nodes to receive the signal. If it is impossible for all the n nodes to receive the signal, return -1.

 

Example 1:


Input: times = [[2,1,1],[2,3,1],[3,4,1]], n = 4, k = 2
Output: 2
Example 2:

Input: times = [[1,2,1]], n = 2, k = 1
Output: 1
Example 3:

Input: times = [[1,2,1]], n = 2, k = 2
Output: -1
 

Constraints:

1 <= k <= n <= 100
1 <= times.length <= 6000
times[i].length == 3
1 <= ui, vi <= n
ui != vi
0 <= wi <= 100
All the pairs (ui, vi) are unique. (i.e., no multiple edges.)

----------------------------- Dijkstra, 有向图，有权重，求最短路径 ------------------------------------------------------------------------
/*
从节点 k 到其他所有节点的最短路径中，最长的那条最短路径距离是多少 -> 算从节点 k 出发到其他所有节点的最短路径，就是标准的 Dijkstra 算法。 

Time：O（n + ElgN) 
The maximum number of vertices that could be added to the priority queue is E. Thus, push and pop operations on the priority queue takeO(logE) time. 

The value of E can be at most N⋅(N−1). Therefore, O(logE) is equivalent to O(logN^2) = O(2⋅logN). Hence, the time complexity for priority queue operations equals O(logN).

Space: O(n + E) -> E = edgets = N* (N-1) 
*/

class State {
    int id;     // 图节点的 id
    int distFromStart;  // 从 start 节点到当前节点的距离 
    
    public State(int id, int distFromStart) {
        this.id = id;
        this.distFromStart = distFromStart; 
    }
}


class Solution {
    public int networkDelayTime(int[][] times, int n, int k) {
       // 0. construct neibor 
        Map<Integer, List<int[]>> graph = new HashMap<Integer, List<int[]>>(); 
        for (int i = 1; i <= n; i++) {
            graph.put(i, new ArrayList<int[]>());
        }
        for (int[] edge : times) {
            graph.get(edge[0]).add(new int[]{edge[1], edge[2]});
        }
        // System.out.println(graph);
        
        
        // 1. create DP list and initialize distance 
        int[] distTo = new int[n+1];
        Arrays.fill(distTo, Integer.MAX_VALUE);
        distTo[k] = 0; // start point 
        
        // 2. queue and put start point 
        PriorityQueue<State> q = new PriorityQueue<State>((a,b) -> a.distFromStart - b.distFromStart);  
        q.offer(new State(k, 0));
        
        // 3. add neibor and update distance 
        while (!q.isEmpty()) {
            State cur = q.poll();
            int curId = cur.id;
            int curDistanceFromStart = cur.distFromStart;
            
            if (curDistanceFromStart > distTo[curId]) {
                continue;
            }
            for (int[] neibor : graph.get(curId)) {
                int neiborDisToStart = curDistanceFromStart + neibor[1]; 
                if (neiborDisToStart < distTo[neibor[0]]) {
                    distTo[neibor[0]] = neiborDisToStart; 
                    q.offer(new State(neibor[0], neiborDisToStart));
                }
            }
        }
        
        // 4. check everyNode has been reached and find the max distance from start 
        int maxDistance = 0; 
        for (int i = 1; i < distTo.length; i++) { // 注意去除第一个
            int toStartDist = distTo[i];
            if (toStartDist == Integer.MAX_VALUE) {
                return -1; // node cannot be reached 
            }
            maxDistance = Math.max(maxDistance, toStartDist);
        }
        
        
        return maxDistance; 
        
    }
}
