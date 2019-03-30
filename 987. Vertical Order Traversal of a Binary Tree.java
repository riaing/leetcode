
Given a binary tree, return the vertical order traversal of its nodes values.

For each node at position (X, Y), its left and right children respectively will be at positions (X-1, Y-1) and (X+1, Y-1).

Running a vertical line from X = -infinity to X = +infinity, whenever the vertical line touches some nodes, we report the values of the nodes in order from top to bottom (decreasing Y coordinates).

If two nodes have the same position, then the value of the node that is reported first is the value that is smaller.

Return an list of non-empty reports in order of X coordinate.  Every report will have a list of values of nodes.

 

Example 1:



Input: [3,9,20,null,null,15,7]
Output: [[9],[3,15],[20],[7]]
Explanation: 
Without loss of generality, we can assume the root node is at position (0, 0):
Then, the node with value 9 occurs at position (-1, -1);
The nodes with values 3 and 15 occur at positions (0, 0) and (0, -2);
The node with value 20 occurs at position (1, -1);
The node with value 7 occurs at position (2, -2).
Example 2:



Input: [1,2,3,4,5,6,7]
Output: [[4],[2],[1,5,6],[3],[7]]
Explanation: 
The node with value 5 and the node with value 6 have the same position according to the given scheme.
However, in the report "[1,5,6]", the node value of 5 comes first since 5 is smaller than 6.
 

Note:

The tree will have between 1 and 1000 nodes.
Each node’s value will be between 0 and 1000.

---------------------------------DFS + Priority queue + comparator-------------------------------------------------
先创建comparator指定好比较的规则， x ascending -> y descending -> val ascending 
在bfs/dfs遍历树，把node放入Pqueue O（nlogn）
最后对Pqueue的结果进行分组by x position，返回。 O（n）
所以time: O(nlgn), space O(n)

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
    class Position {
        int x;
        int y;
        int val;
        public Position(int x, int y, int val) {
            this.x = x;
            this.y = y;
            this.val = val;
        }
    }
    
    public List<List<Integer>> verticalTraversal(TreeNode root) {
    
        第一步：创建comparator 
        // sort by x ascending order, then y descending order, then value ascending order.
        Comparator<Position> comparator = new Comparator<Position>() {
            @Override
            --------------比较直观的写compare的方法-------------------------------------------
            public int compare(Position a, Position b) {
                if (a.x < b.x) {
                    return -1;
                }
                else if (a.x > b.x) {
                    return 1;
                }
                // we sort their y position by adding order, because parent always added before child, and
                // parent have larger y position then child, which means we sort y by desending order 
                else {
                    if (a.y < b.y) {
                        return 1;
                    }
                    else if (a.y > b.y) {
                        return -1;
                    }
                    else{
                        return Integer.compare(a.val, b.val); 
                    }
                }
            }
            ---------------------------接下来是整理后的写compare的方法，------------------------------------------------
            public int compare(Position a, Position b) {
                if (a.x != b.x) {
                    return Integer.compare(a.x, b.x);
                }
                else if (a.y != b.y) {
                    return Integer.compare(b.y, a.y); // y sorted by descending order, so here is b - a 
                }
                else {
                    return Integer.compare(a.val, b.val);  
                }
            }
            ------------------------------------完------------------------------------------------------------------          
        };
        
        第二步：遍历树，放入queue。这里也可以用bfs来遍历
        // traverse all nodes and store then in queue by defined order. 
        PriorityQueue<Position> q = new PriorityQueue<Position>(comparator);
        traversal(root, 0, 0, q);
        
        第三步：group queue中的结果by x position，返回答案
        // Get the nodes out and group answer by x position
        List<List<Integer>> results = new ArrayList<List<Integer>>();
        int prevX = Integer.MAX_VALUE; // because there are at most 1000 nodes, we get a x value that won't happen in this tree. 
        while (!q.isEmpty()) {
            // System.out.println(q.peek().val);
            // System.out.println("x " + q.peek().x);
            // System.out.println("y " + q.peek().y);
            Position cur = q.poll();
            if (cur.x != prevX) {
                //List<Integer> result = new ArrayList<Integer>();
                results.add(new ArrayList<Integer>());
            }
            results.get(results.size()-1).add(cur.val);
            prevX = cur.x;
        }
        
        return results;
    }
    
    private void traversal(TreeNode root, int x, int y, PriorityQueue<Position> q) {
        if (root != null) {
            q.offer(new Position(x, y, root.val));
            traversal(root.left, x-1, y-1, q);
            traversal(root.right, x+1, y-1, q);
        }
    }

}
