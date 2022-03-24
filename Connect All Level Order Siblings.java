Connect All Level Order Siblings (medium)#
Given a binary tree, connect each node with its level order successor. The last node of each level should point to the first node of the next level.

Input: root = [1,2,3,4,5,null,7]
Output: [1,2,3,4,5,7,#]

--------- BFS, 不需要分层，先加左右子树在set next --------------------------------- 
import java.util.*;

class TreeNode {
  int val;
  TreeNode left;
  TreeNode right;
  TreeNode next;

  TreeNode(int x) {
    val = x;
    left = right = next = null;
  }
};

class ConnectAllSiblings {
  public static void connect(TreeNode root) {
     if (root == null) {
            return;
        }
        Queue<TreeNode> q = new LinkedList<TreeNode>(); 
        
        q.offer(root);
        while (q.size() != 0) {
            TreeNode cur = q.poll();

            if (cur.left != null) {
                q.offer(cur.left);
            }
            if (cur.right != null) {
                q.offer(cur.right);
            }
            cur.next = q.peek();
            
        }
        return; 
  }

  public static void main(String[] args) {
    TreeNode root = new TreeNode(12);
    root.left = new TreeNode(7);
    root.right = new TreeNode(1);
    root.left.left = new TreeNode(9);
    root.right.left = new TreeNode(10);
    root.right.right = new TreeNode(5);
    ConnectAllSiblings.connect(root);

    // level order traversal using 'next' pointer
    TreeNode current = root;
    System.out.println("Traversal using 'next' pointer: ");
    while (current != null) {
      System.out.print(current.val + " ");
      current = current.next;
    }
  }
}
