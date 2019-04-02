目为：有很多互相连接的房间，这些房间正好连成一棵二叉树的样子，小偷需要从这棵树的根结点房间出发开始偷东西。为了不被抓住，小偷不能偷相连的两个房间。

于是，有这么两个要求：

如果当前结点的父结点没有被偷，那么当前结点可偷可不偷
如果当前结点的父结点被偷了，那么当前结点不可偷
所以，当从根结点判断开始的时候，有：

如果偷当前结点，那么下面能偷的只能是自己的孙子结点
如果不偷当前结点，那么下面可以偷自己的孩子结点
代码如下：
----------------------------D&C without memorization----------------------------
/**
 * Definition for a binary tree node.
 * public class TreeNode {
 *     int val;
 *     TreeNode left;
 *     TreeNode right;
 *     TreeNode(int x) { val = x; }
 * }
 */
// Compare grandparent + max of grandchildren(l.l + l.r + r.l + r.r) vs max of children (l + r)
// Time: > o(N),具体没想清楚，有人说是O（n^2）
class Solution {
    public int rob(TreeNode root) {
        if (root == null) {
            return 0;
        }
        if (root.left == null && root.right == null) {
            return root.val;
        }
        int childrenSum = rob(root.left) + rob(root.right);
        int grandSumLeft = 0;
        int grandSumRight = 0;
        if (root.left != null) {
           grandSumLeft = rob(root.left.left) + rob(root.left.right);
        }
        if (root.right != null) {
           grandSumRight = rob(root.right.left) + rob(root.right.right);
        }
        int grandSum = root.val + grandSumLeft + grandSumRight;
        return Math.max(childrenSum, grandSum); 
    }
}
在这种方法中可以看到，从结点 node 有两条线，分别是走向 node 的子结点和孙子结点，这就导致一个问题：会产生重复计算。比如，对于从 node 
结点到它的孙子结点，可以有两种方式：node -> 孙子和node -> 孩子 -> 孩子。从两条路大到达同一结点，就是说会从两个递归函数分别进入 node 的孙子结点，
那么势必会计算两次这个结点的解。

所以需要采取一定的措施，使得重复进入同一结点的时候避免重复计算，即使用缓存：
---------------------D & C with memorization ----------------------------------------------------------------

public static int rob(TreeNode root) {
    return rob(root, new HashMap<>());
}
private static int rob(TreeNode node, Map<TreeNode, Integer> saved) {
    if (node == null) return 0;
    if (saved.containsKey(node)) return saved.get(node);
    int max = rob(node.left, saved) + rob(node.right, saved);
    int m = node.val;
    if (node.left != null) {
        m += rob(node.left.left, saved) + rob(node.left.right, saved);
    }
    if (node.right != null) {
        m += rob(node.right.left, saved) + rob(node.right.right, saved);
    }
    max = Math.max(max, m);
    saved.put(node, max);
    return max;
}

使用一个 HashMap 保存已经求解过的结点，就是一种缓存的思想。

再看本方法的缺陷，这是一种“跳跃式”的解法，从一个结点出发到另一个结点，会有多条路径，而多条路径会导致重复计算，那么如果能使它不再跳跃，
只能一步一步往下走的话，这个重复计算也就不复存在了。

如何改进？小偷对于每个结点有两种处理：偷或不偷。

如果小偷偷了当前结点，那么它的子结点不能偷
如果小偷不偷当前结点，那么子结点可以偷，也可以不偷（取其中较大的即可）
这次的条件好像与上面的条件一样，但是还是有区别的，那个涉及到了孙子结点，而这个，只涉及到孩子，如果上面那个是“跳跃式”的，那么这个就是“步进式”的。
当一个结点的解，只与它的孩子结点有关时，这个问题就变得简单了。如下：

-------------------------- best solution -------------------------------------------------------------------------------- 

函数rob返回了一个数组，数组的第一个数是偷了当前结点的解，第二个数是不偷当前结点的解，那么对于一个结点 node 来说，它只要知道了它的左右孩子结点的这样一个
数组，就可以根据上面那个条件求出当前结点的最优解，因为没有了跳跃访问，所有的结点只会访问到一遍。
这个视频也解释得很清楚：https://www.youtube.com/watch?v=-i2BFAU25Zk 
/**
 * Definition for a binary tree node.
 * public class TreeNode {
 *     int val;
 *     TreeNode left;
 *     TreeNode right;
 *     TreeNode(int x) { val = x; }
 * }
 */
// 
class Solution {
    public int rob(TreeNode root) {
        int[] res = helper(root);
        return Math.max(res[0], res[1]);
        
    }
    
    //[0]：max value if NOT rob the house
    //[1]: max value if ROB the house  
    private int[] helper(TreeNode root) {
        int[] res = new int[2];
        if (root == null) {
            return res; // return [0,0]
        }
        
        int[] left = helper(root.left);
        int[] right = helper(root.right);
        
        // conquer
        // 如果不偷当前node，那么可偷，可不偷下一层的node,返回下一层node的最大值，
        res[0] = Math.max(left[0], left[1]) + Math.max(right[0], right[1]);
        // 如果偷当前node，那么下一层的node肯定不能偷
        res[1] = root.val + left[0] + right[0];
        return res;
    }
}
