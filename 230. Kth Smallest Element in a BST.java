Given a binary search tree, write a function kthSmallest to find the kth smallest element in it.

Note: 
You may assume k is always valid, 1 ≤ k ≤ BST’s total elements.

Example 1:

Input: root = [3,1,4,null,2], k = 1
   3
  / \
 1   4
  \
   2
Output: 1
Example 2:

Input: root = [5,3,6,2,4,null,null,1], k = 3
       5
      / \
     3   6
    / \
   2   4
  /
 1
Output: 3
Follow up:
What if the BST is modified (insert/delete operations) often and you need to find the kth smallest frequently? 
How would you optimize the kthSmallest routine?


 
 https://www.youtube.com/watch?v=GpHezLtkfc8 
--------------inorder traversal + iteration + use pointer to count K, O(n)-------------------------------------------------------
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
inorder traversal，我们只要用一个计数器，每pop一个节点，计数器自增1，当计数器到达k时，返回当前结点值即可
*/
class Solution {
    public int kthSmallest(TreeNode root, int k) {
        Deque<TreeNode> s = new LinkedList<TreeNode>();
        s.push(root);
        int i = 0;
        TreeNode cur = root.left;
        while (!s.isEmpty() || cur != null) {
            while (cur != null) {
                s.push(cur);
                cur = cur.left;
            }
            TreeNode tmp = s.pop();
            i++;
            if (i == k) { // if find kth smallest element, return
                return tmp.val;
            }
            // else, iterate to the right 
            cur = tmp.right;
        }
        return -1; // 没有找到
    }
}

-----------------------------------inorder traversal + recursion 0(n) --------------------------------------------------- 
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
    int count = 0; 
    public int kthSmallest(TreeNode root, int k) {
       return  dfs(root, k);
    }
    private int dfs(TreeNode root, int k) {
        if (root != null) {
            int res = dfs(root.left, k);
            if (res != -1) {
                return res;
            }
            count++;
            if (count == k) {
                return root.val;
            }
            return  dfs(root.right, k);
        }
       return -1;
    }
}

-----------------          Bineary search 思想----------------------------------------------------------------------------
time complexity: O(N) best, O(N^2) worst
再来看一种分治法的思路，由于BST的性质，我们可以快速定位出第k小的元素是在左子树还是右子树，我们首先计算出左子树的结点个数总和cnt，
如果k小于等于左子树结点总和cnt，说明第k小的元素在左子树中，直接对左子结点调用递归即可。如果k大于cnt+1，说明目标值在右子树中，对右子结点调用递归函数，
注意此时的k应为k-cnt-1，应为已经减少了cnt+1个结点。如果k正好等于cnt+1，说明当前结点即为所求，返回当前结点值即可，

public int kthSmallest(TreeNode root, int k) {
      int count = countNodes(root.left);
      if (k <= count) {
          return kthSmallest(root.left, k);
      } else if (k > count + 1) {
          return kthSmallest(root.right, k-1-count); // 1 is counted as current node
      }
      
      return root.val;
  }
  
  public int countNodes(TreeNode n) {
      if (n == null) return 0;
      
      return 1 + countNodes(n.left) + countNodes(n.right);

  }
---------------------- 2022.6 inorder traversal的写法 -------------------------
   /*
follow up: 
想找到第 k 小的元素，或者说找到排名为 k 的元素，如果想达到对数级复杂度，关键也在于每个节点得知道他自己排第几。
-> 需要在二叉树节点中维护额外信息。每个节点需要记录，以自己为根的这棵二叉树有多少个节点。
当然，size 字段需要在增删元素的时候需要被正确维护，力扣提供的 TreeNode 是没有 size 这个字段的. 
需要1. build a new tree 2。 binary search找k -> 看下面code
*/
class Solution {
    int rank = 0;
    int res = 0;
    public int kthSmallest(TreeNode root, int k) {
        traverse(root, k);
        return res; 
    }
    
    private void traverse(TreeNode root, int k) {
       if (root == null) {
           return;
       }
        traverse(root.left, k);
        rank++; // 走到了自己，当前rank+1
        if (k == rank){
            res = root.val;
            return;
        }
        traverse(root.right, k); 
        
    }
}

------------- 2022.6 stack写法 --------------------------
   /**
 * Definition for a binary tree node.
 * public class TreeNode {
 *     int val;
 *     TreeNode left;
 *     TreeNode right;
 *     TreeNode() {}
 *     TreeNode(int val) { this.val = val; }
 *     TreeNode(int val, TreeNode left, TreeNode right) {
 *         this.val = val;
 *         this.left = left;
 *         this.right = right;
 *     }
 * }
 */
class Solution {
    public int kthSmallest(TreeNode root, int k) {
        Deque<TreeNode> stack = new LinkedList<TreeNode>(); 
        pushLeft(root, stack);
        while (k > 0) {
            TreeNode cur = stack.pop();
            k--;
            if (k == 0) {
                return cur.val;
            }
            pushLeft(cur.right, stack);
        }
        return -1; 
    }
    
    private void pushLeft(TreeNode cur, Deque<TreeNode> stack) {
        while (cur != null) {
            stack.push(cur);
            cur = cur.left;
        }   
    }
}
  
  --------------------**! answer to follow up question -----------------------------------------------------------------
  道题的Follow up中说假设该BST被修改的很频繁，而且查找第k小元素的操作也很频繁，问我们如何优化。其实最好的方法还是像上面的解法那样利用分治法来快速定位
  目标所在的位置，那么就是O（lgn），但是每个递归都遍历左子树所有结点来计算个数的操作并不高效，所以我们应该修改原树结点的结构，使其保存包括当前结点和
  其左右子树所有结点的个数，这样我们使用的时候就可以快速得到任何左子树结点总数来帮我们快速定位目标值了。定义了新结点结构体，然后就要生成新树，这步在
  现实中可以只有当modify tree的时候call。
  本题中，在求第k小元素的函数中，我们先生成新的树，然后调用binarySearch() on 新的带有count的node。在递归函数中，不能直接访问左子结点的count值，
  因为左子节结点不一定存在。如果左子树不存在的话，当此时k为1的时候，直接返回当前结点值，否则就对右子结点调用递归函数，k自减1，参见代码如下：
  
  
  比如说你让我查找排名为 k 的元素，当前节点知道自己排名第 m，那么我可以比较 m 和 k 的大小：
1、如果 m == k，显然就是找到了第 k 个元素，返回当前节点就行了。

2、如果 k < m，那说明排名第 k 的元素在左子树，所以可以去左子树搜索第 k 个元素。

3、如果 k > m，那说明排名第 k 的元素在右子树，所以可以去右子树搜索第 k - m - 1 个元素。
这样就可以将时间复杂度降到 O(logN) 了。

那么，如何让每一个节点知道自己的排名呢？

这就是我们之前说的，需要在二叉树节点中维护额外信息。每个节点需要记录，以自己为根的这棵二叉树有多少个节点。

也就是说，我们 TreeNode 中的字段应该如下：

class TreeNode {
    int val;
    // 以该节点为根的树的节点总数
    int size;
    TreeNode left;
    TreeNode right;
}
  

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
    //所以我们应该修改原树结点的结构，使其保存包括当前结点和其左右子树所有结点的个数
    class Node {
        int val;
        int count; // including itself, how many nodes contatins as this as root 
        Node left;
        Node right;
        
      Node(int x) { 
          val = x; 
      }
    }
    
    // This will only be called when modify the tree 
    public Node buildTree(TreeNode root) {
        if (root == null) {
            return null;
        }
        Node buildRoot = new Node(root.val);
        
        buildRoot.left = buildTree(root.left);
        buildRoot.right = buildTree(root.right);
        
        int curCount = 0;
        if (buildRoot.left != null) { //这里的null判断，是避免没有left时call count
            curCount +=  buildRoot.left.count;
        }
        if (buildRoot.right != null) {
            curCount += buildRoot.right.count;
        }
        buildRoot.count = 1 + curCount;
        return buildRoot;  
    }
    

    public int kthSmallest(TreeNode root, int k) {
        Node newRoot = buildTree(root); 
        return binarySearch(newRoot, k); 
    }
    
    // now Node contains the number of nodes at each node 
    private int binarySearch(Node root, int k) {
        if (root == null) {
            return -1; //找不到such a value 
        }
        // 不能直接访问左子结点的count值，因为左子节结点不一定存在
        if (root.left != null) {
            //如果k小于root.left含有的节点数，那么返回值肯定是在左边
            if (k <= root.left.count) {
                return binarySearch(root.left, k);
            }
            // 如果k在右边，那我们更新k
            else if (k > root.left.count+1) {
                return binarySearch(root.right, k - root.left.count - 1); //减1是减去root
            }
            else { // k == root.left.count +1, 那么就是root
                return root.val;
            }
        }
        //当左子树不存在时
        else {
            if (k == 1) {
                return root.val;
            }
            else {
                return binarySearch(root.right, k - 1);
            }
        }
    }
}
