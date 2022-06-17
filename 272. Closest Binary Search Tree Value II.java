Given the root of a binary search tree, a target value, and an integer k, return the k values in the BST that are closest to the target. You may return the answer in any order.

You are guaranteed to have only one unique set of k values in the BST that are closest to the target.

 

Example 1:


Input: root = [4,2,5,1,3], target = 3.714286, k = 2
Output: [4,3]
Example 2:

Input: root = [1], target = 0.000000, k = 1
Output: [1]
 

Constraints:

The number of nodes in the tree is n.
1 <= k <= n <= 104.
0 <= Node.val <= 109
-109 <= target <= 109
 

Follow up: Assume that the BST is balanced. Could you solve it in less than O(n) runtime (where n = total nodes)?

---------------------- in order traversal + sliding window 思路 ----------------------------------------------

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
/*
O(NlgK)的写法：in order traversal 先，然后把value 放到 q里 -> 经典heap的用法
O（lgN)的写法：用stack来实现在树上的traversal https://blog.csdn.net/qq508618087/article/details/50982007 

O(N)写法：in order traversal + 类似sliding window的思路
如果结果集合中元素还不到k个, 就把当前元素加到集合中去, 如果集合中的元素个数多于k了, 比较list头与target之差，和cur与target之差， 那么有二种情况:
1。 list头与target之差 < cur与target之差, 因为中序遍历是有序的, 最小元素就是第0个元素 -> 返回结果
2. 否则，说明当前结点比list头更靠近target： 丢掉头，继续往后找，直到我们无法再找到能够替换的元素,
*/
class Solution {
    LinkedList<TreeNode> list = new LinkedList<>();
    public List<Integer> closestKValues(TreeNode root, double target, int k) {
        List<Integer> res = new ArrayList<>();
        traversal(root, target, k);
        res = list.stream().map(o -> o.val).collect(Collectors.toList());
        return res; 
    }
    
    private void traversal(TreeNode root, double target, int k) {
        if (root == null) {
            return; 
        }
        traversal(root.left, target, k);
        
        list.addLast(root);
        if (list.size() > k) {
            TreeNode first = list.peekFirst();
            if (Math.abs(first.val * 1.0 - target) < Math.abs(root.val * 1.0 - target)) { // 如果当前值大于 list中最小。说明找到了解
                list.removeLast();
                return;    
            }
            else { // 丢掉list的头，继续往右边找
                list.removeFirst();
            }
        }
        traversal(root.right, target, k);
    }
}
