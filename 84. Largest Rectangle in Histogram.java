https://leetcode.com/problems/largest-rectangle-in-histogram/ 
Given n non-negative integers representing the histogram‘s bar height where the width of each bar is 1, find the area of largest rectangle in the histogram.


Above is a histogram where width of each bar is 1, given height = [2,1,5,6,2,3].

 
The largest rectangle is shown in the shaded area, which has area = 10 unit.

 

Example:

Input: [2,1,5,6,2,3]
Output: 10
 
stack botton always store the smallest element across the array. 
运用stack的性质，总是存储ascending/descending order element。
注意：循环时要循环到原列数+1的地方，我们用一个dummy var = 0。

Time complexity : O(n). nn numbers are pushed and popped.
Space complexity : O(n). Stack is used.

https://www.youtube.com/watch?v=ZmnqCZp9bBs 
http://www.cnblogs.com/lichen782/p/leetcode_Largest_Rectangle_in_Histogram.html 


class Solution {
     public int largestRectangleArea(int[] height) {
         int maxArea = 0; 
         // Stack to store the index of every ascending element  
         Deque<Integer> stack = new LinkedList<Integer>();
        //注意这里的列数为原列数加1，用于存储最后一个dummy var
         int[] h = Arrays.copyOf(height, height.length+1);
         int i = 0;
         
         while(i < h.length) {
             if (stack.isEmpty() || h[stack.peek()] < h[i]) {
                 stack.push(i);
                 i++;
             }
             else {
                 int cur = stack.pop();
                 maxArea = Math.max(maxArea, h[cur] * (stack.isEmpty() ? i : i - stack.peek() -1));
             }
         }
         return maxArea;
     }
} 
