You are given an undirected weighted graph of n nodes (0-indexed), represented by an edge list where edges[i] = [a, b] is an undirected edge connecting the nodes a and b with a probability of success of traversing that edge succProb[i].

Given two nodes start and end, find the path with the maximum probability of success to go from start to end and return its success probability.

If there is no path from start to end, return 0. Your answer will be accepted if it differs from the correct answer by at most 1e-5.

 

Example 1:



Input: n = 3, edges = [[0,1],[1,2],[0,2]], succProb = [0.5,0.5,0.2], start = 0, end = 2
Output: 0.25000
Explanation: There are two paths from start to end, one having a probability of success = 0.2 and the other has 0.5 * 0.5 = 0.25.
Example 2:



Input: n = 3, edges = [[0,1],[1,2],[0,2]], succProb = [0.5,0.5,0.3], start = 0, end = 2
Output: 0.30000
Example 3:



Input: n = 3, edges = [[0,1]], succProb = [0.5], start = 0, end = 2
Output: 0.00000
Explanation: There is no path between 0 and 2.
 

Constraints:

2 <= n <= 10^4
0 <= start, end < n
start != end
0 <= a, b < n
a != b
0 <= succProb.length == edges.length <= 2*10^4
0 <= succProb[i] <= 1
There is at most one edge between every two nodes.


-------------- dyjkastra ---------------------------------------------
class Pair { // Node 到某个点的dist
    int node; 
    double dis;
    public Pair(int node, double dis) {
        this.node = node;
        this.dis = dis;
    }
}

class Solution {
    public double maxProbability(int n, int[][] edges, double[] succProb, int start, int end) {
        // 1. 构建graph
        Map<Integer, List<Pair>> graph = new HashMap<Integer, List<Pair>>();
        for (int i = 0; i < n; i++) {
            graph.put(i, new ArrayList<Pair>());
        }
        for (int i = 0; i < edges.length; i++) {
            int e1 = edges[i][0];
            int e2 = edges[i][1];
            graph.get(e1).add(new Pair(e2, succProb[i]));
            graph.get(e2).add(new Pair(e1, succProb[i]));
        }
        
        // 2. put into Queue and initialize 
        double[] disFromStart = new double[n];
        Arrays.fill(disFromStart, 0.0); // 一般初始化为一个取不到的最小值，本题放0没关系
        disFromStart[start] = 1.0;
        PriorityQueue<Pair> q = new PriorityQueue<Pair>((a,b) -> Double.compare(b.dis, a.dis)); // node 到start的max prob, probFromStart 较大的排在前面
        q.offer(new Pair(start, 1.0)); 
        
        // 3. go through each node and update its neibor
        while (!q.isEmpty()) {
            Pair cur = q.poll();
            double curDistToStart = cur.dis;
            int curNode = cur.node;
            if (curDistToStart < disFromStart[curNode]) { // 已经有一条概率更大的路径到达 curNode 节点了
                continue; 
            }
            // 本题与dyjkstra模板的不同: 遇到终点提前返回
            if (curNode == end) {
                return curDistToStart;
            }
            // 更新邻居
            for (Pair neibor : graph.get(curNode)) {
                if (neibor.node == curNode) { // 无向图就skip parent
                    continue; 
                }
                // 看看从 curNode 达到 nextNode 的概率是否会更大
                double neiborDistToStart = neibor.dis * curDistToStart;
                if (neiborDistToStart > disFromStart[neibor.node]) {
                    disFromStart[neibor.node] = neiborDistToStart;
                    q.offer(new Pair(neibor.node, neiborDistToStart));
                }
            }
        }
        return disFromStart[end]; 
    }
}
