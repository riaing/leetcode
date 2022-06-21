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

---------------2022.4 DFS ----------------------------------
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
 *     // Return empty list if this NestedInteger holds a single integer
 *     public List<NestedInteger> getList();
 * }
 */

/*
重点是求出number —> depth. 通过DFS来解。
Time: O(n)

*/
class Solution {
    int maxDepth = 0;
    
    public int depthSumInverse(List<NestedInteger> nestedList) { 
        // 重点是求出number —> depth 
        List<int[]> depth = findNumAndDepth(nestedList, 1);
        // depth.forEach(o -> System.out.println("num " + o[0] + " " + o[1]));
        // System.out.println("maxDepth " + maxDepth);
        
        int result = 0; 
        //follow the rule to do calculation 
        for(int[] pair : depth) {
            int num = pair[0];
            int weight = maxDepth - pair[1] + 1;
            result += num * weight;
        }
        
        return result;
        
    }
    
    private List<int[]> findNumAndDepth(List<NestedInteger> nestedList, int depth) {
        List<int[]> res = new ArrayList<int[]>();
        
        for (NestedInteger curN : nestedList) {
            if (curN.isInteger()) {
                maxDepth = Math.max(maxDepth, depth);
                res.add(new int[] {curN.getInteger(), depth});
            }
            else {
                res.addAll(findNumAndDepth(curN.getList(), depth+1));
            }
        }
        return res; 
    }
}

-------------------- 更省时的DFS --------------------
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
 *     // Return empty list if this NestedInteger holds a single integer
 *     public List<NestedInteger> getList();
 * }
 */

/*
递归找深度，类似于 https://leetcode.com/problems/find-leaves-of-binary-tree/ 是 叶子depth为0， 本题是root depth为0 
1、 先递归找到每个深度的sum， =》List<Integer> 
2，算完后 list的index+1 就是 depth，按公式算即可

Time O（n + 层数)
*/
class Solution {
    public int depthSum(List<NestedInteger> nestedList) {
        // 1. 重点是求出每层的sum。 
        List<Integer> sumPerLevel = new ArrayList<>(); 
        findSum(nestedList, 1, 0, sumPerLevel); // o(n)
        
         //2. follow the rule to do calculation， sumPerLevel的index就和depth相关
        int res = 0; 
        for (int i = 0; i < sumPerLevel.size(); i++) { // O(层数)
            int depth = i + 1;
            res += depth * sumPerLevel.get(i);
        }
        return res;
    }
    
    private void findSum(List<NestedInteger> nestedList, int depth, int curIndex, List<Integer> res) { 
        while (res.size() < depth) {
            res.add(0);
        }
        
        for (int i = curIndex; i < nestedList.size(); i++) {
            NestedInteger curN = nestedList.get(i);
      
            if (curN.isInteger()) {
                res.set(depth-1, res.get(depth-1) + curN.getInteger());
            }
            else {
                findSum(curN.getList(), depth+1, 0, res);
            }
        }
    }
}
------------------ 2022.4. BFS ----------------------------------
 
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
 *     // Return empty list if this NestedInteger holds a single integer
 *     public List<NestedInteger> getList();
 * }
 */

/*
重点是求出number —> depth. Bfs 需要个data structure来记录 NestedInteger和它的depth』
第一层：把nestedList加到queue里面去。
每次poll element: 如果是integer，找到。如果是个list，把list的elment加到queue里，并且dpeth + 1
Time: O(n*2)

*/
class Node {
    NestedInteger nestedInteger; 
    int depth;
    
    public Node( NestedInteger nestedInteger, int depth) {
        this.nestedInteger = nestedInteger;
        this.depth = depth;
    }
}


class Solution {
    
    public int depthSumInverse(List<NestedInteger> nestedList) {
        int maxDepth = 0;
        Queue<Node> q = new LinkedList<Node>();
        for (NestedInteger cur : nestedList) {
            q.offer(new Node(cur, 1));
        }
        // 重点是求出number —> depth 
        List<int[]> depth = new ArrayList<int[]>();
        while (!q.isEmpty()) {
            Node cur = q.poll();
            NestedInteger curInt = cur.nestedInteger;
            if (curInt.isInteger()) {
                depth.add(new int[] {curInt.getInteger(), cur.depth});
                maxDepth = Math.max(maxDepth, cur.depth);
            }
            else {
                for (NestedInteger next : curInt.getList()) {
                    q.offer(new Node(next, cur.depth + 1));
                }
            }
        }
        // depth.forEach(o -> System.out.println("num " + o[0] + " " + o[1]));
        // System.out.println("maxDepth " + maxDepth);
        
        int result = 0; 
        //follow the rule to do calculation 
        for(int[] pair : depth) {
            int num = pair[0];
            int weight = maxDepth - pair[1] + 1;
            result += num * weight;
        }
        
        return result;
        
    }
}
