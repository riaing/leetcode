/**
 * // This is the interface that allows for creating nested lists.
 * // You should not implement it, or speculate about its implementation
 * public interface NestedInteger {
 *     // Constructor initializes an empty nested list.
 *     public NestedInteger();
 *
 *     // Constructor initializes a single integer.
 *     public NestedInteger(int value);
 *
 *     // @return true if this NestedInteger holds a single integer, rather than a nested list.
 *     public boolean isInteger();
 *
 *     // @return the single integer that this NestedInteger holds, if it holds a single integer
 *     // Return null if this NestedInteger holds a nested list
 *     public Integer getInteger();
 *
 *     // Set this NestedInteger to hold a single integer.
 *     public void setInteger(int value);
 *
 *     // Set this NestedInteger to hold a nested list and adds a nested integer to it.
 *     public void add(NestedInteger ni);
 *
 *     // @return the nested list that this NestedInteger holds, if it holds a nested list
 *     // Return null if this NestedInteger holds a single integer
 *     public List<NestedInteger> getList();
 * }
 */

/**
这道题定义了一种嵌套链表的结构，链表可以无限往里嵌套，规定每嵌套一层，深度加1，让我们求权重之和，就是每个数字乘以其权重，再求总和。

The algorithm takes O(N) time, where NN is the total number of nested elements in the input list. For example, the list [ [[[[1]]]], 2 ] contains 44 nested lists and 22 nested integers (11 and 22), so N=6N=6.

In terms of space, at most O(D) recursive calls are placed on the stack, where DD is the maximum level of nesting in the input. For example, D=2D=2 for the input [[1,1],2,[1,1]], and D=3D=3 for the input [1,[4,[6]]].
*/
class Solution {
    public int depthSum(List<NestedInteger> nestedList) {
        if (nestedList == null || nestedList.size() == 0) {
            return 0;
        }
           return helper(nestedList, 1);
    }
    
    private int helper(List<NestedInteger> nestedList, int depth) {
        int res = 0;
         for (int i = 0; i < nestedList.size(); i++) {
             NestedInteger cur = nestedList.get(i);
             if (cur.isInteger()) {
                 res = res + (depth * cur.getInteger());
             }
             else{
                 res += helper(cur.getList(), depth+1);
             }
         }
        return res; 
    }
}
