Given a circular array (the next element of the last element is the first element of the array), print the Next Greater Number for every element. The Next Greater Number of a number x is the first greater number to its traversing-order next in the array, which means you could search circularly to find its next greater number. If it doesn't exist, output -1 for this number.

Example 1:
Input: [1,2,1]
Output: [2,-1,2]
Explanation: The first 1's next greater number is 2; 
The number 2 can't find next greater number; 
The second 1's next greater number needs to search circularly, which is also 2. 

/***
用stack存储每个元素的index,以便存入res中
遍历长度为2*n的array，则通过i%n可以拿到之前的元素
如果stack不为空并且stack的顶部小于当前值，说明找到了stack顶部元素的next great number。循环pop stack。
放元素进stack时，要判断是否i<n,因为超过n的部分我们只是为了给之前栈中的数字找较大值，所以不能压入栈

Time complexity : O(n)O(n). Only two traversals of the numsnums array are done. Further, atmost 2n elements are pushed and popped from the stack.

Space complexity : O(n)O(n). A stack of size nn is used. resres array of size nn is used. 
***/
class Solution {
    public int[] nextGreaterElements(int[] nums) {
        Deque<Integer> stack = new LinkedList<Integer>();
        int n = nums.length; 
        int[] res = new int[n];
        // 通过遍历两遍数组并取余来得到之前的数字
        for (int i = 0; i < n*2; i++) {
            while(!stack.isEmpty() && nums[stack.peek()] < nums[i%n]) {
                int index = stack.pop();
                res[index] = nums[i%n];
            }
            // 如果i小于n，则把i压入栈。因为res的长度必须是n，超过n的部分我们只是为了给之前栈中的数字找较大值，所以不能压入栈
            if (i < n) {
                stack.push(i);
            }
        }
        while(!stack.isEmpty()) {
            res[stack.pop()] = -1;
        }
        return res; 
    }
}
