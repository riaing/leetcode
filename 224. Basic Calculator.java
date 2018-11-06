Implement a basic calculator to evaluate a simple expression string.

The expression string may contain open ( and closing parentheses ), the plus + or minus sign -, non-negative integers and empty spaces .

Example 1:

Input: "1 + 1"
Output: 2
Example 2:

Input: " 2-1 + 2 "
Output: 3
Example 3:

Input: "(1+(4+5+2)-3)+(6+8)"
Output: 23
Note:
You may assume that the given expression is always valid.
Do not use the eval built-in library function.


1， stack。用res来代表目前为止的值。
我们需要一个栈来辅助计算，用个变量sign来表示当前的符号，我们遍历给定的字符串s，如果遇到了数字，由于可能是个多位数，所以我们要用while循环把之后的数字都读进来，
然后用sign*num来更新结果res；如果遇到了加号，则sign赋为1，如果遇到了符号，则赋为-1；如果遇到了左括号，
则把当前结果res和符号sign压入栈，res重置为0，sign重置为1；如果遇到了右括号，结果res乘以栈顶的符号，栈顶元素出栈，结果res加上栈顶的数字，栈顶元素出栈。
class Solution {
    // Char to int: char - '0'; 
    public int calculate(String s) {
        Deque<Integer> stack = new LinkedList<Integer>();
        int res = 0;
        int sign = 1; 
        for (int i = 0; i < s.length(); i++) {
            if (s.charAt(i) == '(') {
                stack.push(res);
                stack.push(sign);
                // 这里必须复原，碰到括号连在一起的情况 eg: 1-((xx))
                res = 0;
                sign = 1;
            }
            else if (s.charAt(i) == '+') {
                sign = 1;
            }
            else if (s.charAt(i) == '-') {
                sign = -1;
            }
            else if (s.charAt(i) >= '0'){
                int num  = 0; 
                while (i < s.length() && s.charAt(i) >= '0') {
                    num = num *10 + s.charAt(i) - '0';
                    i++;
                }
                res = res + num * sign;
                // One step back for forloop 
                i --; 
            }
            else if(s.charAt(i) == ')') {
                res = res * stack.pop();
                res = res + stack.pop();
            }            
        }
        return res;
    }
}

-----------------stack 2: 非常直白但稍微耗时的方法，不推荐了就
Loop整个string，
1， == (， 入栈
2， == - ，入栈
3， == 数字，整个数字入栈（while loop确保几位数的数字被当成一个数字入栈）
4， 当喷到)时。取出栈中碰到第一个(的所有数。
  - 设置变量res， 当stack.peek() == ‘-‘时，res*（-1）
  - 当tack.peek() ==数字时，1， 判断此时stack.peek()是不是"-", 是的话数字* -1，不然直接res +=数字
  - 当 当stack.peek() ==‘(‘时，把当前res入栈

class Solution {
    // Char to int: char - '0'; 
    public int calculate(String s) {
        Deque<String> stack = new LinkedList<String>();
        
        for (int i = 0; i < s.length(); i++) {
            if (s.charAt(i) == '(') {
                stack.push("(");
            }
            else if (s.charAt(i) == '+') {
                continue;
            }
            else if (s.charAt(i) == '-') {
                stack.push("-");
            }
            else if (s.charAt(i) >= '0'){
                StringBuilder builder = new StringBuilder();
                while (i < s.length() && s.charAt(i) >= '0') {
                    builder.append(s.charAt(i));
                    i++;
                }
                stack.push(builder.toString());
                // One step back for forloop 
                i --; 
            }
            else if(s.charAt(i) == ')') {
                int num = 0; 
                String cur = stack.pop();
                while(cur != "(") {
                    if (cur == "-") {
                        num = num * -1;
                    }
                    else {
                        int curInt = Integer.parseInt(cur);
                        if (stack.peek() == "-") {
                            curInt = curInt * -1; 
                            stack.pop();
                        }
                        num = num + curInt;
                    }
                    cur = stack.pop();
                }
                stack.push(Integer.toString(num)); 
            }            
        }
        int res = 0; 
        while (!stack.isEmpty()) {
            String curString = stack.pop();
            if (curString == "-") {
                res = res * -1;
            }
            else {
                int curInt = Integer.parseInt(curString);
                        if (stack.peek() == "-") {
                            curInt = curInt * -1; 
                            stack.pop();
                        }
                res = res + curInt;
            }
        }
    return res;
    }
}
