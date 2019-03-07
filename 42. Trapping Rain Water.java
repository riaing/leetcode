https://leetcode.com/problems/trapping-rain-water/ 

Given n non-negative integers representing an elevation map where the width of each bar is 1, 
compute how much water it is able to trap after raining.


The above elevation map is represented by array [0,1,0,2,1,0,1,3,2,1,2,1]. In this case, 6 units of rain water 
(blue section) are being trapped. Thanks Marcos for contributing this image!

Example:
Input: [0,1,0,2,1,0,1,3,2,1,2,1]
Output: 6


--------------------------------------------------------------------------------------------------------------
遍历高度，如果此时栈为空，或者当前高度小于等于栈顶高度，则把当前高度的坐标压入栈，注意我们不直接把高度压入栈，而是把坐标压入栈，
这样方便我们在后来算水平距离。当我们遇到比栈顶高度大的时候，就说明有可能会有坑存在，可以装雨水。此时我们栈里至少有一个高度，如果只有一个的话，
那么不能形成坑，我们直接跳过，如果多余一个的话，那么此时把栈顶元素取出来当作坑，新的栈顶元素就是左边界，当前高度是右边界，只要取二者较小的，
减去坑的高度，长度就是右边界坐标减去左边界坐标再减1，二者相乘就是盛水量啦，参见代码如下：
class Solution {
    public int trap(int[] height) {
        Deque<Integer> stack = new LinkedList<Integer>();
        
        int area = 0; 
        for (int i = 0; i< height.length; i++){
            while(stack.size() !=0 && height[i] >height[stack.peek()]) {
                int bottomHeight = height[stack.pop()];
                // 如果只有一个元素的话，无法形成水池，跳过
                if (stack.isEmpty()) {
                    break;
                }
                int left = stack.peek();
                area = area + (i - left-1) * (Math.min(height[i], height[left]) - bottomHeight);
            }
            if (stack.isEmpty() || height[stack.peek()] >= height[i]) {
                stack.push(i);
            } 
        }
        return area;
        
    }
}
