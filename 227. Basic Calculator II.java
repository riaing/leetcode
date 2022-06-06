Given a string s which represents an expression, evaluate this expression and return its value. 

The integer division should truncate toward zero.

You may assume that the given expression is always valid. All intermediate results will be in the range of [-231, 231 - 1].

Note: You are not allowed to use any built-in function which evaluates strings as mathematical expressions, such as eval().

 

Example 1:

Input: s = "3+2*2"
Output: 7
Example 2:

Input: s = " 3/2 "
Output: 1
Example 3:

Input: s = " 3+5 / 2 "
Output: 5
 

Constraints:

1 <= s.length <= 3 * 105
s consists of integers and operators ('+', '-', '*', '/') separated by some number of spaces.
s represents a valid expression.
All the integers in the expression are non-negative integers in the range [0, 231 - 1].
The answer is guaranteed to fit in a 32-bit integer.

-------------------------------------------------------------------------------
/*
calculator 标准写法。
*/
class Solution {
    public int calculate(String s) {
        s = s.replaceAll("\\s", ""); // remove space 
        char sign = '+';
        int num = 0;
        Deque<Integer> stack = new LinkedList<Integer>();
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (isDigit(c)) {
                num = num*10 + (c - '0');
            }
            if (!isDigit(c) || i == s.length() -1) { //注意：一定要判断最后一个
                if (sign == '+') {
                    stack.push(num);   
                }
                if (sign == '-') {
                    stack.push(-num);
                }
                if (sign == '*') {
                    int pre = stack.pop();
                    stack.push(pre * num);
                }
                if (sign == '/') {
                    int pre = stack.pop();
                    stack.push(pre / num);
                }
                sign = c;
                num = 0;
            }
        }
        int res = 0;
        while (!stack.isEmpty()) {
            res += stack.pop();
        }
        return res; 
    }
    
    private boolean isDigit(char c) {
        if (c >= '0' &&  c <= '9') {
            return true;
        }
        return false;
    }
}
