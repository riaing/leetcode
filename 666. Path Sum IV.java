If the depth of a tree is smaller than 5, then this tree can be represented by an array of three-digit integers. For each integer in this array:

The hundreds digit represents the depth d of this node where 1 <= d <= 4.
The tens digit represents the position p of this node in the level it belongs to where 1 <= p <= 8. The position is the same as that in a full binary tree.
The units digit represents the value v of this node where 0 <= v <= 9.
Given an array of ascending three-digit integers nums representing a binary tree with a depth smaller than 5, return the sum of all paths from the root towards the leaves.

It is guaranteed that the given array represents a valid connected binary tree.

 

Example 1:


Input: nums = [113,215,221]
Output: 12
Explanation: The tree that the list represents is shown.
The path sum is (3 + 5) + (3 + 1) = 12.
Example 2:


Input: nums = [113,221]
Output: 4
Explanation: The tree that the list represents is shown. 
The path sum is (3 + 1) = 4.
 

Constraints:

1 <= nums.length <= 15
110 <= nums[i] <= 489
nums represents a valid binary tree with depth less than 5.

------------------------- recursion  -------------------------------
/*
for a given node, it's left node's position (at tens digit) is root's position * 2 - 1, right node's position is root's position * 2. In this way, we can get it's chidren's hundrem and tens digits

So need to quickly locate the child node by it's position - use map to store postin <-> value 

1. 用 map 来代替树 structure 
2.分析出左右子树和 root 的关系，概念：level - 百位上的数；position - 十位上的数
子树 level = root 的 level + 1
左子树 position = root 的 position * 2 -1
。。。
*/
class Solution {
    public int pathSum(int[] nums) {
        if (nums == null || nums.length == 0) {
            return 0; 
        }
        // map to construct the tree 
        Map<Integer, Integer> tree = new HashMap<Integer, Integer>();
        for (int num : nums) {
            tree.put(num / 10, num);
        }
        return helper(tree, nums[0], 0);
    }
    
    private int helper(Map<Integer, Integer> tree, int root, int curSum) {
        int sum = 0; 
        if (!tree.containsKey(root/10)) {
            return sum;
        }
        
        curSum = curSum + root % 10;
        
        // 拿到百位上的数
        int rootLevel = root / 100; 
        // 十位上的数 
        int rootPos = root % 100 /10; 
        //计算左右子树位置
        int leftPos = (rootLevel + 1) * 10  + (rootPos * 2 - 1);
        int rightPos = (rootLevel + 1) * 10 + rootPos * 2;
        if (!tree.containsKey(leftPos) && !tree.containsKey(rightPos)) {
            sum += curSum;
            return sum;
        }
        if (tree.containsKey(leftPos)) {
            sum += helper(tree, tree.get(leftPos), curSum);
        }
        if (tree.containsKey(rightPos)) {
            sum += helper(tree, tree.get(rightPos), curSum);
        }
        return sum;
    }
}
