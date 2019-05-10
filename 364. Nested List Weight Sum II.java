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
 ---------求maxdepth解法一，recursion------------------------
class Solution {
    int maxDepth;
    public int depthSumInverse(List<NestedInteger> nestedList) {
        maxDepth = 0;
        getMaxDepth(nestedList, 1);
        return maxDepth;
    }
    
    private void getMaxDepth(List<NestedInteger> nestedList, int curDepth) {
       maxDepth = Math.max(maxDepth, curDepth);
        for (NestedInteger n : nestedList) {
            if (!n.isInteger()) {
                getMaxDepth(n.getList(), curDepth+1);
            }
        }
    }
}
---------解法二， D & C ----------------------------------------
class Solution {
    public int depthSumInverse(List<NestedInteger> nestedList) {
        return getMaxDepth(nestedList);
    }
    
    private int getMaxDepth(List<NestedInteger> nestedList) {
       int maxDepth = 1; 
        for (NestedInteger n : nestedList) {
            if (!n.isInteger()) {
               maxDepth =  Math.max(maxDepth, 1+getMaxDepth(n.getList()));
            }
        } 
        return maxDepth;
    }
}
-----------------------------------------正式解题，求出最大depth后，和Nested List Weight Sum 1 一样--------------------
class Solution {
    public int depthSumInverse(List<NestedInteger> nestedList) {
        if (nestedList == null || nestedList.size() == 0) {
            return 0; 
        }
        int level =  getMaxDepth(nestedList);
        return helper(nestedList, level);
    }
    
    private int helper(List<NestedInteger> nestedList, int level) {
        int sum = 0;
         for (NestedInteger n : nestedList) {
            if (n.isInteger()) {
               sum += n.getInteger() * level;
            }
             else {
                 sum += helper(n.getList(), level-1);
             }
        }
        return sum;
    }
    
    private int getMaxDepth(List<NestedInteger> nestedList) {
       int maxDepth = 1; 
        for (NestedInteger n : nestedList) {
            if (!n.isInteger()) {
               maxDepth =  Math.max(maxDepth, 1+getMaxDepth(n.getList()));
            }
        } 
        return maxDepth;
    }
}
