Evaluate the value of an arithmetic expression in Reverse Polish Notation.

Valid operators are +, -, *, /. Each operand may be an integer or another expression.

Note:

Division between two integers should truncate toward zero.
The given RPN expression is always valid. That means the expression would always evaluate to a result and there won't be any divide by zero operation.
Example 1:

Input: ["2", "1", "+", "3", "*"]
Output: 9
Explanation: ((2 + 1) * 3) = 9
Example 2:

Input: ["4", "13", "5", "/", "+"]
Output: 6
Explanation: (4 + (13 / 5)) = 6
Example 3:

Input: ["10", "6", "9", "3", "+", "-11", "*", "/", "*", "17", "+", "5", "+"]
Output: 22
Explanation: 
  ((10 * (6 / ((9 + 3) * -11))) + 17) + 5
= ((10 * (6 / (12 * -11))) + 17) + 5
= ((10 * (6 / -132)) + 17) + 5
= ((10 * 0) + 17) + 5
= (0 + 17) + 5
= 17 + 5
= 22

--------stack, 遇到符号取出，算出值后再放入------------------------------------
class Solution {
    public int evalRPN(String[] tokens) {
        Deque<Integer> stack = new LinkedList<Integer>();
        Set<String> operators = new HashSet<String>();
        operators.add("+");
        operators.add("-");
        operators.add("*");
        operators.add("/");
        for (int i = 0; i < tokens.length; i++) {
            if (operators.contains(tokens[i])) {
                int cur = calculate(stack.pop(), stack.pop(), tokens[i]);
                stack.push(cur);
                
            }
            else {
                
                stack.push(Integer.valueOf(tokens[i]));
            }
        }
        return stack.pop();
    }
    
    // always use operand2 as the first operand. Here we assume operand is always valid 
    private int calculate(int operand1, int operand2, String operand) {
        if (operand.equals("+")) {
            System.out.println(operand2 + operand1);
            return operand2 + operand1;
        }
        else if (operand.equals("-")) {
            return operand2 - operand1;
        }
        else if (operand.equals("*")) {
            return operand2 * operand1;
        }
        else {
            return operand2 / operand1; 
        }
    }
}
