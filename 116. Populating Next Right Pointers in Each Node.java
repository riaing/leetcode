You are given a perfect binary tree where all leaves are on the same level, and every parent has two children. The binary tree has the following definition:

struct Node {
  int val;
  Node *left;
  Node *right;
  Node *next;
}
Populate each next pointer to point to its next right node. If there is no next right node, the next pointer should be set to NULL.

Initially, all next pointers are set to NULL.

 

Follow up:

You may only use constant extra space.
Recursive approach is fine, you may assume implicit stack space does not count as extra space for this problem.
 

Example 1:



Input: root = [1,2,3,4,5,6,7]
Output: [1,#,2,3,#,4,5,6,7,#]
Explanation: Given the above perfect binary tree (Figure A), your function should populate each next pointer to point to its next right node, just like in Figure B. The serialized output is in level order as connected by the next pointers, with '#' signifying the end of each level.
 

Constraints:

The number of nodes in the given tree is less than 4096.
-1000 <= node.val <= 1000




/*
// Definition for a Node.
class Node {
    public int val;
    public Node left;
    public Node right;
    public Node next;

    public Node() {}
    
    public Node(int _val) {
        val = _val;
    }

    public Node(int _val, Node _left, Node _right, Node _next) {
        val = _val;
        left = _left;
        right = _right;
        next = _next;
    }
};
*/

------------- 4/28/2021 BFS 基本功 -------------------------------------------------------------------------
class Solution {
    public Node connect(Node root) {
        if (root == null) {
            return root;
        }
        Queue<Node> q = new LinkedList<Node>();
        q.offer(root);
        while (!q.isEmpty()) {
            int size = q.size();
            for (int i = 1; i <= size; i++) {
                Node cur = q.poll(); 
                // if not the last node in this level. set it's next 
                if (i != size) {
                    cur.next = q.peek();
                }
                
                if (cur.left != null) {
                     q.offer(cur.left);
                }
                if (cur.right != null) {
                     q.offer(cur.right);
                }
            }
        }
        
        return root;
    }
}
------------------ constant space -----------------------------------------------------------------------------
  /*
// Definition for a Node.
class Node {
    public int val;
    public Node left;
    public Node right;
    public Node next;

    public Node() {}
    
    public Node(int _val) {
        val = _val;
    }

    public Node(int _val, Node _left, Node _right, Node _next) {
        val = _val;
        left = _left;
        right = _right;
        next = _next;
    }
};
*/

class Solution {
    public Node connect(Node root) {
        if (root == null) {
            return root;
        }
        // 因为每层是处理其左右子树，所以要有 null 判断
        // 1. assign value to left tree 
        if (root.left != null) {
             root.left.next = root.right; 
        }
      
        // 2. assign valut to the right tree 
        if (root.right != null && root.next != null) {
            root.right.next = root.next.left;
        }
        connect(root.left);
        connect(root.right);
        
        return root;
    }
}
