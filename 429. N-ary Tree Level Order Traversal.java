Given an n-ary tree, return the level order traversal of its nodes' values. (ie, from left to right, level by level).

For example, given a 3-ary tree:

 



 

We should return its level order traversal:

[
     [1],
     [3,2,4],
     [5,6]
]
 

Note:

The depth of the tree is at most 1000.
The total number of nodes is at most 5000.

-------------------------------------------BFS---------------------------------------------
/*
// Definition for a Node.
class Node {
    public int val;
    public List<Node> children;

    public Node() {}

    public Node(int _val,List<Node> _children) {
        val = _val;
        children = _children;
    }
};
*/
class Solution {
    public List<List<Integer>> levelOrder(Node root) {
       
        List<List<Integer>> results = new ArrayList<List<Integer>>();
        if (root == null) {
            return results;
        }
        Queue<Node> q = new LinkedList<Node>();
        q.offer(root);
        
        while (!q.isEmpty()) {
            List<Integer> result = new ArrayList<Integer>();
            int size = q.size();
            for (int i = 0; i < size; i++) {
                Node cur = q.poll();
                result.add(cur.val);
                for (Node child : cur.children) {
                    q.offer(child);
                }
            }
            results.add(result);
        }
        return results;
    }
}
