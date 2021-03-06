Given a list of daily temperatures, produce a list that, for each day in the input, tells you how many days you would have to wait until a warmer temperature. If there is no future day for which this is possible, put 0 instead.

For example, given the list temperatures = [73, 74, 75, 71, 69, 72, 76, 73], your output should be [1, 1, 4, 2, 1, 1, 0, 0].

Note: The length of temperatures will be in the range [1, 30000]. Each temperature will be an integer in the range [30, 100].

http://www.cnblogs.com/grandyang/p/8097513.html 
这道题给了我们一个数组，让我们找下一个比当前数字大的数字的距离，我们研究一下题目中给的例子，发现数组是无序的，所以没法用二分法快速定位下一个大的数字，那么最先考虑的方法就是暴力搜索了，写起来没有什么难度，但是OJ并不答应。
实际上这道题应该使用递减栈Descending Stack来做，栈里只有递减元素，思路是这样的，我们遍历数组，如果栈不空，且当前数字大于栈顶元素，那么如果直接入栈的话就不是递减栈了，所以我们取出栈顶元素，那么由于当前数字大于栈顶元素的数字，
而且一定是第一个大于栈顶元素的数，那么我们直接求出下标差就是二者的距离了，然后继续看新的栈顶元素，直到当前数字小于等于栈顶元素停止，然后将数字入栈，这样就可以一直保持递减栈，且每个数字和第一个大于它的数的距离也可以算出来了，
参见代码如下：

class Solution {
    public int[] dailyTemperatures(int[] temperatures) {
        // Initialize an array with element = 0; 
        int[] res = new int[temperatures.length];
        
        // Stack to record the index 
        Deque<Integer> stack = new LinkedList<Integer>();
        
        for (int i = 0; i < temperatures.length; i++) {
            while (!stack.isEmpty() && temperatures[i] > temperatures[stack.peek()]) {        
                    int lastNumSmallerThanCurrentNum = stack.pop();
                    res[lastNumSmallerThanCurrentNum] = i - lastNumSmallerThanCurrentNum; 
                
            }
             stack.push(i);
        }
        return res; 
    }
}

Time Complexity: O(N)O(N), where NN is the length of T and WW is the number of allowed values for T[i]. Each index gets pushed and popped at most once from the stack.

Space Complexity: O(W)O(W). The size of the stack is bounded as it represents strictly increasing temperatures.

 
    -----------------solution 2 思路一样，换了写法，这个写法match别的类似的题而已-----------------------------------
    类似题：Largest Rectangle in Histogram 
    class Solution {
    public int[] dailyTemperatures(int[] temperatures) {
        // Initialize an array with element = 0; 
        int[] res = new int[temperatures.length];
        
        // Stack to record the index 
        Deque<Integer> stack = new LinkedList<Integer>();
        
        int i = 0;
        while (i < temperatures.length) {
            if (stack.isEmpty() || temperatures[i] <= temperatures[stack.peek()]) {
                stack.push(i);
                i++;
            }
            else {
                int cur = stack.pop();
                res[cur] = i - cur;
                
            }
        }
        // 这个其实不需要因为initialize res的时候自动放的0.
        while (!stack.isEmpty()) {
            int cur = stack.pop();
            res[cur] = 0;
        }
        //以上不需要
        
        return res;
    }
}
