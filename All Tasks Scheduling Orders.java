Problem Statement#
There are ‘N’ tasks, labeled from ‘0’ to ‘N-1’. Each task can have some prerequisite tasks which need to be completed before it can be scheduled. Given the number of tasks and a list of prerequisite pairs, write a method to print all possible ordering of tasks meeting all prerequisites.

Example 1:

Input: Tasks=3, Prerequisites=[0, 1], [1, 2]
Output: [0, 1, 2]
Explanation: There is only possible ordering of the tasks.
Example 2:

Input: Tasks=4, Prerequisites=[3, 2], [3, 0], [2, 0], [2, 1]
Output: 
1) [3, 2, 0, 1]
2) [3, 2, 1, 0]
Explanation: There are two possible orderings of the tasks meeting all prerequisites.
Example 3:

Input: Tasks=6, Prerequisites=[2, 5], [0, 5], [0, 4], [1, 4], [3, 2], [1, 3]
Output: 
1) [0, 1, 4, 3, 2, 5]
2) [0, 1, 3, 4, 2, 5]
3) [0, 1, 3, 2, 4, 5]
4) [0, 1, 3, 2, 5, 4]
5) [1, 0, 3, 4, 2, 5]
6) [1, 0, 3, 2, 4, 5]
7) [1, 0, 3, 2, 5, 4]
8) [1, 0, 4, 3, 2, 5]
9) [1, 3, 0, 2, 4, 5]
10) [1, 3, 0, 2, 5, 4]
11) [1, 3, 0, 4, 2, 5]
12) [1, 3, 2, 0, 5, 4]
13) [1, 3, 2, 0, 4, 5]

----------------------- Topological sort + DFS  ---------------------------
  class Solution {
    public int[] findOrder(int numCourses, int[][] prerequisites) {
        List<Integer> res = new ArrayList<Integer>();
        // 1. initialize node map and in-degree map 
        Map<Integer, List<Integer>> nodes = new HashMap<Integer,List<Integer>>();
        Map<Integer, Integer> inDegrees = new HashMap<Integer, Integer>();
        for (int i = 0; i < numCourses; i++) {
            nodes.put(i, new ArrayList<Integer>());
            inDegrees.put(i, 0);
        }
        
         // 2, build  
        for (int i = 0; i < prerequisites.length; i++) {
            int parent = prerequisites[i][0];
            int child = prerequisites[i][1];
            nodes.get(parent).add(child);
            inDegrees.put(child, inDegrees.get(child) + 1);
        }
        
        // 3. find sources (in-degree = 0)
        Queue<Integer> sources = new LinkedList<Integer>();
        for (Map.Entry<Integer, Integer> entry : inDegrees.entrySet()) {
            if (entry.getValue() == 0) {
                sources.offer(entry.getKey());
            }
        }
        
        /*进阶题：求出所有的可能情况 */
        List<List<Integer>> results = new ArrayList<List<Integer>>();
        List<Integer> curRes = new ArrayList<Integer>();
        helper(nodes, inDegrees, sources, numCourses, results, curRes);
        int cnt = 1;
        for (List<Integer> result : results) {
            System.out.println("res " + cnt + ": " + result);
            cnt ++; 
        }
        return results;
        
        /* 基础题：返回任意情况 
        //4. sort 
        while (sources.size() > 0) {
            int curSource = sources.poll();
            //1. add to list 
            res.add(curSource);
            //2. decrese children nodes's indegree 
            for (int child : nodes.get(curSource)) {
                inDegrees.put(child, inDegrees.get(child)-1);
                // 3. add next 0 degree node to queue 
                if (inDegrees.get(child) == 0) {
                    sources.offer(child);
                }
            }
        }
        // 5. detect cycle 
        // way 1:
        if (res.size() != numCourses){ // 或者查 indegree map 还有没有谁的 in degree > 0 
            return new int[0];
        }
        
        int[] arrayRes = new int[res.size()];
        for (int i = 0; i < res.size(); i++) {
            arrayRes[i] = res.get(i);
        }
        return arrayRes;  
        */
    }
    
    // 求出所有解，用递归
    private void helper(Map<Integer, List<Integer>> nodes, Map<Integer, Integer> inDegrees, Queue<Integer> sources, int numCources, List<List<Integer>> results, List<Integer> curRes){
        if (sources.size() == 0) {
            if (curRes.size() == numCources) {
                results.add(new ArrayList<Integer>(curRes));
            }
            return; // 否则就有 cycle，也要退出
        }
        
        for (Integer vertex : sources) {
            Queue<Integer> curQueue = cloneQueue(sources); //每次都得一个新的 queue
            curQueue.remove(vertex);
            curRes.add(vertex);
            // reduce degree of children, but use a list to record because need to backtrack later 
            List<Integer> curChildren = new ArrayList<Integer>();
            for (Integer child : nodes.get(vertex)) {
                curChildren.add(child);
                inDegrees.put(child, inDegrees.get(child) - 1);
                if (inDegrees.get(child) == 0) {
                    curQueue.offer(child);
                }
            }
            // go to next level 
            helper(nodes, inDegrees, curQueue, numCources, results, curRes);
            // 回溯 inDegress, curRes 
            for (Integer child : curChildren) {
                inDegrees.put(child, inDegrees.get(child) + 1);
            }
            curRes.remove(curRes.size()-1);
        }
    }
    
    private Queue<Integer> cloneQueue(Queue<Integer> sources) {
        Queue<Integer> clone = new LinkedList<Integer>();
        for (Integer vertex : sources) {
            clone.offer(vertex);
        }
        return clone; 
    }
}
