Given the root of a tree, you are asked to find the most frequent subtree sum. The subtree sum of a node is defined as the sum of all the node values formed by the subtree rooted at that node (including the node itself). So what is the most frequent subtree sum value? If there is a tie, return all the values with the highest frequency in any order.

Examples 1
Input:

  5
 /  \
2   -3
return [2, -3, 4], since all the values happen only once, return all of them in any order.
Examples 2
Input:

  5
 /  \
2   -5
return [2], since 2 happens twice, however -5 only occur once.
Note: You may assume the sum of values in any subtree is in the range of 32-bit signed integer.

--------------通过priority queue求出map中value最大的值 nlgn----------------------------------------------------
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
    Map<Integer, Integer> map;
    public int[] findFrequentTreeSum(TreeNode root) {
        if (root == null) {
            return new int[]{};
        }
        map = new HashMap<Integer, Integer>();
        sum(root);
       
        PriorityQueue<Map.Entry<Integer, Integer>> q = new PriorityQueue<Map.Entry<Integer, Integer>>(
            new Comparator<Map.Entry<Integer, Integer>>() {
                @Override
                public int compare(Map.Entry<Integer, Integer> e1, Map.Entry<Integer, Integer> e2) {
                    return e2.getValue() - e1.getValue();
                };
            }
        );
         
        for (Map.Entry<Integer, Integer> entry : map.entrySet()) {
            q.offer(entry);
        }
        List<Integer> x = new ArrayList<Integer>();
        
        int mostFreq = q.peek().getValue();
        while (!q.isEmpty()) {
            if (mostFreq != q.peek().getValue()) {
                break;
            }
            x.add(q.poll().getKey());
        }
        int[] res = new int[x.size()];
        for (int i = 0; i < x.size(); i++) {
            res[i] = x.get(i);
        }
        return res;
    }
    
    private int sum(TreeNode root) {
        int res; 
        if (root == null) {
            return 0;
        }
        
        res = root.val + sum(root.left) + sum(root.right);
        map.put(res, map.getOrDefault(res, 0) + 1); 
        return res;
    }
}

---------------不通过priority queue,直接遍历一遍map找到最大，更简洁---------------------------------------------------------------
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
    Map<Integer, Integer> map;
    public int[] findFrequentTreeSum(TreeNode root) {
        if (root == null) {
            return new int[]{};
        }
        map = new HashMap<Integer, Integer>();
        sum(root);
       
        List<Integer> x = new ArrayList<Integer>();
        int max = Integer.MIN_VALUE;
        
        for (Map.Entry<Integer, Integer> entry : map.entrySet()) {
            // 如果新的freq > 大于之前的，更新list和max，并把值加入清空后的list中。
            if (entry.getValue() > max) {
                max = entry.getValue();
                x.clear();
                x.add(entry.getKey());
            }
            else if (entry.getValue() == max){
                x.add(entry.getKey());
            }
        }
       
        int[] res = new int[x.size()];
        for (int i = 0; i < x.size(); i++) {
            res[i] = x.get(i);
        }
        return res;
    }
    
    private int sum(TreeNode root) {
        int res; 
        if (root == null) {
            return 0;
        }
        
        res = root.val + sum(root.left) + sum(root.right);
        map.put(res, map.getOrDefault(res, 0) + 1); 
        return res;
    }
}
