Given an n-ary tree, return the preorder traversal of its nodes' values.

For example, given a 3-ary tree:

 



 

Return its preorder traversal as: [1,3,5,6,2,4].
--------------------------divide and conquer----------------------------------------------------------------------
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
    public List<Integer> preorder(Node root) {
        List<Integer> res = new ArrayList<Integer>();
        if (root == null) {
            return res;
        }
        res.add(root.val);
        for (int i = 0; i < root.children.size(); i++) {
            res.addAll(preorder(root.children.get(i)));
        }
        return res;
    }
}`1
