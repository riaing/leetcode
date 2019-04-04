Two elements of a binary search tree (BST) are swapped by mistake.

Recover the tree without changing its structure.

Example 1:

Input: [1,3,null,null,2]

   1
  /
 3
  \
   2

Output: [3,1,null,null,2]

   3
  /
 1
  \
   2
Example 2:

Input: [3,1,4,null,null,2]

  3
 / \
1   4
   /
  2

Output: [2,1,4,null,null,3]

  2
 / \
1   4
   /
  3
Follow up:

A solution using O(n) space is pretty straight forward.
Could you devise a constant space solution? -应该要用到morries traversal

--------------------最基本的 two list法，利用BST inorder的性质--------------------------------------------------------------
/**
 * Definition for a binary tree node.
 * public class TreeNode {
 *     int val;
 *     TreeNode left;
 *     TreeNode right;
 *     TreeNode(int x) { val = x; }
 * }
 */

/**

用中序遍历树，并将所有节点存到一个一维向量中，把所有节点值存到另一个一维向量中，然后对存节点值的一维向量排序，在将排好的数组按顺序赋给节点。这种最一般的解法可针对任意个数目的节点错乱的情况，
Time: O(nlngn) 
space: O(n)
*/
class Solution {
    public void recoverTree(TreeNode root) {
        List<TreeNode> nodes = new ArrayList<TreeNode>();
        List<Integer> values = new ArrayList<Integer>();
        inorder(root, nodes, values);
        //2, 对存节点值的一维向量排序
        Collections.sort(values);
        //3, 将排好的数组按顺序赋给节点
        for (int i = 0; i< nodes.size(); i++) {
            nodes.get(i).val = values.get(i);
        }
        
    }
    
    // 1, 将所有节点存到一个一维向量中，把所有节点值存到另一个一维向量中
    private void inorder(TreeNode root, List<TreeNode> nodes, List<Integer> values) {
        if (root == null) {
            return;
        }
        if (root.left != null) {
            inorder(root.left, nodes, values);
        }
        nodes.add(root);
        values.add(root.val);
        if (root.right != null) {
            inorder(root.right, nodes, values);
        }
    }
}

-------也是利用bst的性质，基本上就是inorder traversal tree，根据pre > cur，找到出错的两个点，交换值----------------------------
Time O(n), Space O(h) -> h = height 

1， recursion， Time O(n), Space O(h) -> h = height 
看到另一种是用双指针来代替一维向量的，但是这种方法用到了递归，也不是O(1)空间复杂度的解法，这里需要三个指针，first，second分别表示第一个和第二个错乱位
置的节点，pre指向当前节点的中序遍历的前一个节点。这里用传统的中序遍历递归来做，不过再应该输出节点值的地方，换成了判断pre和当前节点值的大小，如果pre
的大，若first为空，则将first指向pre指的节点，把second指向当前节点。这样中序遍历完整个树，若first和second都存在，则交换它们的节点值即可。这个算法的
空间复杂度仍为O(h)，h为树的高度

/**
 * Definition for a binary tree node.
 * public class TreeNode {
 *     int val;
 *     TreeNode left;
 *     TreeNode right;
 *     TreeNode(int x) { val = x; }
 * }
 */

/**

*/
class Solution {
    TreeNode pre;
    TreeNode first;
    TreeNode second; 
    public void recoverTree(TreeNode root) {
        inorder(root);
        // swap the two value 
        int tmp = first.val;
        first.val = second.val;
        second.val = tmp;
    }
    

    private void inorder(TreeNode root) {
        if (root == null) {
            return;
        }
        if (root.left != null) {
            inorder(root.left);
        }
        
        if (pre == null) {
            pre = root;
        }
        else {
            if (pre.val > root.val) {
                if (first == null) {
                    first = pre;
                }
                second = root; 
            }
            //再次更新pre 
            pre = root;
        }
        if (root.right != null) {
            inorder(root.right);
        }
    }
}


-------------------------------------------------------------------------------------------
2, iteration 
Time O(n), Space O(h) -> h = height  
class Solution {
public:
    void recoverTree(TreeNode* root) {
        TreeNode *pre = NULL, *first = NULL, *second = NULL, *p = root;
        stack<TreeNode*> st;
        while (p || !st.empty()) {
            while (p) {
                st.push(p);
                p = p->left;
            }
            p = st.top(); st.pop();
            if (pre) {
                if (pre->val > p->val) {
                    if (!first) first = pre;
                    second = p;
                }
            }
            pre = p;
            p = p->right;
        }
        swap(first->val, second->val);
    }
};





