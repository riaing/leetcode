Suppose LeetCode will start its IPO soon. In order to sell a good price of its shares to Venture Capital, LeetCode would like to work on some projects to increase its capital before the IPO. Since it has limited resources, it can only finish at most k distinct projects before the IPO. Help LeetCode design the best way to maximize its total capital after finishing at most k distinct projects.

You are given n projects where the ith project has a pure profit profits[i] and a minimum capital of capital[i] is needed to start it.

Initially, you have w capital. When you finish a project, you will obtain its pure profit and the profit will be added to your total capital.

Pick a list of at most k distinct projects from given projects to maximize your final capital, and return the final maximized capital.

The answer is guaranteed to fit in a 32-bit signed integer.

 

Example 1:

Input: k = 2, w = 0, profits = [1,2,3], capital = [0,1,1]
Output: 4
Explanation: Since your initial capital is 0, you can only start the project indexed 0.
After finishing it you will obtain profit 1 and your capital becomes 1.
With capital 1, you can either start the project indexed 1 or the project indexed 2.
Since you can choose at most 2 projects, you need to finish the project indexed 2 to get the maximum capital.
Therefore, output the final maximized capital, which is 0 + 1 + 3 = 4.
Example 2:

Input: k = 3, w = 0, profits = [1,2,3], capital = [0,1,2]
Output: 6
 

Constraints:

1 <= k <= 105
0 <= w <= 109
n == profits.length
n == capital.length
1 <= n <= 105
0 <= profits[i] <= 104
0 <= capital[i] <= 109

------------------- two heap ---------------------------
/*
O（2* nlgn） 

特殊情况：当 w 很多时（w > all capital), 可以只用一个 k size 的 max heap 存 profits，从中取出 top k 即可。 O（n + nlogk） k size 的 heap 每次 opration 是 lgk，要把所有 element 放进去所以是 nlgk

*/
class Solution {
    public class CapNode implements Comparable<CapNode> {
        int index;
        int val; 
        CapNode(int index, int val) {
            this.index = index;
            this.val = val; 
        } 
        @Override 
        public int compareTo(CapNode b) {
            return this.val - b.val; 
        }
    }
    
    public int findMaximizedCapital(int k, int w, int[] profits, int[] capital) {
        Comparator<Integer> comparator = new Comparator<Integer>() {
            @Override
            public int compare(Integer a, Integer b) {
                return b - a; 
            }
        };
        
        PriorityQueue<CapNode> capitalHeap = new PriorityQueue<CapNode>(); // min heap 
        PriorityQueue<Integer> profitsHeap = new PriorityQueue<Integer>(comparator); // max heap 
        // 先把 captial 放到 min heap 中
        for (int i = 0; i < capital.length; i++) { // nlgn
            CapNode curCapital = new CapNode(i, capital[i]);
            capitalHeap.offer(curCapital);
        }
        
        for (int i = 0; i < k; i++) { // 总共 k 次中，最多把一个 heap 移到另一个 heap，所以加起来是 nlgn
            while (capitalHeap.size() != 0 && capitalHeap.peek().val <= w) {
                CapNode minCapNode = capitalHeap.peek();
                 minCapNode = capitalHeap.poll();
                 profitsHeap.add(profits[minCapNode.index]);
                
            }
            if (profitsHeap.size() != 0) { // 防止 k > n 
                int curProfit = profitsHeap.poll();
                w += curProfit;
            }
            else { // pruning
                break; 
            } 
        }
        return w;
    }
} 
