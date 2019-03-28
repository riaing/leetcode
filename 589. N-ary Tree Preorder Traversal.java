Given an n-ary tree, return the preorder traversal of its nodes’ values.

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
}

-------------------------------非递归stack---------------------------------------------------------------------
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
        Deque<Node> s = new LinkedList<Node>();
        s.push(root);
        while(!s.isEmpty()) {
            Node cur = s.pop();
            if (cur != null) {
                res.add(cur.val);
                for (int i = cur.children.size()-1; i >= 0; i--) { //题目中定义的是从左到右，所以这里反向加入stack
                    s.push(cur.children.get(i));
                }
            }
        }
        return res;
    }
}
