Given the root of a binary search tree and a target value, return the value in the BST that is closest to the target.

 

Example 1:


Input: root = [4,2,5,1,3], target = 3.714286
Output: 4
Example 2:

Input: root = [1], target = 4.428571
Output: 1
 

Constraints:

The number of nodes in the tree is in the range [1, 104].
0 <= Node.val <= 109
-109 <= target <= 109


----------------------  bs ----------------------------------------------

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
就是bs。 如果target > mid， 丢掉mid左边， 往右子树找个值和当前mid比较
            target < mid, 丢掉mid右边
*/
class Solution {
    public int closestValue(TreeNode root, double target) {
        if (root == null) {
            return Integer.MAX_VALUE; 
        }
        int closest = root.val; 
        // 左右找一边
        if (target < root.val) {
            int leftClosest = closestValue(root.left, target);
            // 将左边的值和当前值对比取小
            if (root.val - target > Math.abs(leftClosest - target)) {
                closest = leftClosest; 
            }
        }
        else if (target > root.val) {
            int rightClosest = closestValue(root.right, target);
            if ((target - root.val) > Math.abs(rightClosest - target)) {
                closest = rightClosest; 
            }
            
        }
        return closest; 
    }
}

---------------------- Array找closest -------------
思考：1. 模板的特性，start可能超 index， end 可能< 0 
2. 此题的特性，最后一定要和当前元素对比一下
      如果用 start，最后 closest 可能是start，也可能是 start - 1
综上，while 出来后注意两点即可： 1. start 不能超范围 2. start 和 start-1 比一下

    private int findCloesetToK(int[] arr, int target) {
        int start = 0;
        int end = arr.length - 1;
        int closest = 0; 
        while (start <= end) {
            int mid = start + (end - start) / 2; 
            if (arr[mid] - target == 0) {
                closest = mid;
                return closest;
            }
            else if (arr[mid] - target > 0) { // 重点2： target和mid比大小
                end = mid - 1;
            }
            else {
                start = mid + 1; 
            }
        }
        // 出来时如果用 start，那 start 可能超范围了，所以判断一下
        if (start >= arr.length) {
            start--;
        }
        // 这时候有可能 start 前一个或者 start 是最 close 的。所以判断一下. eg: [1,2,3,4,8] target=5
        if (start > 0) {
            closest = Math.abs(arr[start] - target) < Math.abs(arr[start-1] - target) ? start : start - 1; 
        }
        
        return closest;
    }
