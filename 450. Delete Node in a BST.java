Given a root node reference of a BST and a key, delete the node with the given key in the BST. Return the root node reference (possibly updated) of the BST.

Basically, the deletion can be divided into two stages:

Search for a node to remove.
If the node is found, delete the node.
Note: Time complexity should be O(height of tree).

Example:

root = [5,3,6,2,4,null,7]
key = 3

    5
   / \
  3   6
 / \   \
2   4   7

Given key to delete is 3. So we find the node with value 3 and delete it.

One valid answer is [5,4,6,2,null,null,7], shown in the following BST.

    5
   / \
  4   6
 /     \
2       7

Another valid answer is [5,2,6,null,4,null,7].

    5
   / \
  2   6
   \   \
    4   7



解法reference： http://www.cnblogs.com/grandyang/p/6228252.html
--------下面来看一种对于二叉树通用的解法，适用于所有二叉树，所以并没有利用BST的性质，而是遍历了所有的节点，然后删掉和key值相同的节点，O(n)--------
delete node from a binary tree:
1, 判断是否等于root value，
2，如果是，那么找到右子树的最小值/左子树最小值，把它的value和root的value互换。
3，继续进行递归，最终将删掉那个值

/**
 * Definition for a binary tree node.
 * public class TreeNode {
 *     int val;
 *     TreeNode left;
 *     TreeNode right;
 *     TreeNode(int x) { val = x; }
 * }
 */
class Solution {
    public TreeNode deleteNode(TreeNode root, int key) {
        if (root == null) {
            return null;
        }
        //如果等于root，找到右边最小，swap value
        if (root.val == key) {
            //因为是拿右边开刀，所以先判断有没有右子树
            if (root.right == null) { // SOL2:如果是想和左子树最大值互换   if (root.left == null) {
                                       //                                     return root.right;
                                         //                               }
                return root.left;
            }
            // find the min in right subtree
            TreeNode cur = root.right; //  SOL2:如果是想和左子树最大值互换  TreeNode cur = root.left;
            while (cur.left != null) {   // cur.right != null
                cur = cur.left;           // cur = cur.right;  就是找的左的最大值
            }
            swapValue(root, cur); 
        }
        
        // 必须保证其左右子树皆继续保持binary tree的性质。
        root.left = deleteNode(root.left, key);
        root.right = deleteNode(root.right, key);
        return root;
    }
    private void swapValue(TreeNode n1, TreeNode n2) {
        int tmp = n1.val;
        n1.val = n2.val;
        n2.val = tmp;
    }
}

-------------利用了BST性质的解法，快速判断key在哪边。中心思想同样是swap root.val和右子树最大值value O(h)---------------------------------
/**
 * Definition for a binary tree node.
 * public class TreeNode {
 *     int val;
 *     TreeNode left;
 *     TreeNode right;
 *     TreeNode(int x) { val = x; }
 * }
 */
class Solution {
    public TreeNode deleteNode(TreeNode root, int key) {
        if (root == null) {
            return null;
        }
        // BST与普通树不同的点1：可以快速判断key所在位置
        if (root.val < key) {
            root.right = deleteNode(root.right, key);
        }
        else if (root.val > key) {
            root.left = deleteNode(root.left, key);
        }
        // 等于root.val时，和普通树一样，找右子树最小值进行互换值
        else {
            if (root.right == null) {
                return root.left; //or root = root.left;
            }
            TreeNode cur = root.right;
            while (cur.left != null) {
                cur = cur.left;
            }
            swapValue(root, cur); 
            // 必须继续遍历被modified的右子树使其保持BST的性质
            root.right = deleteNode(root.right, key); //  BST与普通树不同的点2: 我们在BST中因为有了快速定位key在哪一边，所以这里直接修整右边子树。而在普通树中，我们需要整理both左右子树，因为delete的点可能在下次递归中，在子树的左边。
        }
        return root; 
        
    }
        
    private void swapValue(TreeNode n1, TreeNode n2) {
        int tmp = n1.val;
        n1.val = n2.val;
        n2.val = tmp;
    }
}

