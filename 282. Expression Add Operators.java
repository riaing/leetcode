Given a string that contains only digits 0-9 and a target value, return all possibilities to add binary operators (not unary) +, -, or * between the digits so they evaluate to the target value.

Example 1:

Input: num = "123", target = 6
Output: ["1+2+3", "1*2*3"] 
Example 2:

Input: num = "232", target = 8
Output: ["2*3+2", "2+3*2"]
Example 3:

Input: num = "105", target = 5
Output: ["1*0+5","10-5"]
Example 4:

Input: num = "00", target = 0
Output: ["0+0", "0-0", "0*0"]
Example 5:

Input: num = "3456237490", target = 9191
Output: []


-----------------------------recursion-------------------------------------------------------------------

/**
首先我们想怎么列出所有的可能式子：因为有三个运算符，所以我们对每个operand会有三次递归。比如1+2，1—2，1*2
再来考虑operand，每个operand可以是一位或者几位数，比如，1+2+3，12+3，所以综上，我们循环这个input num，每次给之前的operand加一位数来产生一个operand，然后对于每个operand，递归三种运算符
可见https://github.com/riaing/leetcode/blob/master/282.%20Expression%20Add%20Operators%20---%20partial%20question.java 

recursion(index，num, list<> res, curString) {
    when (index == num.length()) ->return;
    for (int i = index, i < num.length; i++) {
        // new operand is created by increasing one digit, eg, 1,12,123...
        operand = num.substring(indiex, i+1);
        recursion(i+1) 
    }
}

时间：其实可以想成有四种recursion的办法：三种运算符，和 simply extends the current_operand by the current digit and moves ahead，所以对于n的string，time 4^(n-1) -> 4^n
空间：最坏4^n中可能都是valid，然后string长度为n，所以 space n*(4^n)


这里注意的是如果对于当前operand，我们是把运算符加在它前面的话(previous string + 符号 + 当前operand)，那我们需要特殊处理最开始的operand，因为最开始不需要加运算符

然后我们考虑在递归时就算值，于是在递归方程中引入value，每次产生新的operand时，根据运算符更新value。加号，减号都很简单，直接val+/- curOperand

考虑乘法时，先看几个例子： 2-4*3， 2+4*3, 2*4*3 
会发现当遇到*时，因为计算机此时的val会是-2，6，8，所以我们这时候其实就是先要倒退一部，把前一步处理的2-4，2+4，2*4中4的运算去掉，拿到2。然后再进行乘法运算：
eg: 2+4*3的例子中,一步步来看
curOperand: 2， val: 2
curOperand：4， val: 2+4 = 6
curOpreand:3,遇到了*， val：6-4 + 4*3.其中6-4就是倒退一步，用val-previous operand，然后得的值再加上previous operand*curOperand。
由此得知，这题的递归需要记录上一次的previousOperand。



**/
class Solution {
    private String[] operator = {"+", "-", "*"};
    private int target; 
    
    public List<String> addOperators(String num, int input) {
       target = input; 
        List<String> res = new ArrayList<String>();
        // 单独处理最开始的operand
        for (int i = 0; i < num.length(); i++) {
            long val = Long.parseLong(num.substring(0, i+1));
            // skip cases like 1 + 05 or 1*05 since 05 is not a valid operand.
            if(num.substring(0, i+1).length() != String.valueOf(val).length()) {
                continue;
            }
            dfs(num, res, num.substring(0, i+1), i + 1, val, val);
        }
        return res;
    }
    
    // when meet *, val = val - lastOperand + lastOperand*currentOperand 
    private void dfs(String num, List<String> res, String cur, int index, long value, long previousOperand) {
        if (index == num.length() && value == target) {
             res.add(cur);
             return;
         }
        
        for (int i = index; i<num.length(); i++) {
            String curOperand = num.substring(index, i+1);
             long curOperandVal = Long.parseLong(curOperand);
            // skip cases like 1 + 05 or 1*05 since 05 is not a valid operand. 
            if(curOperand.length() != String.valueOf(curOperandVal).length()) {
                continue;
            }
                for (int j = 0; j< operator.length; j++) {
                    if (operator[j] == "+") {
                        dfs(num, res, cur + operator[j] + curOperand, i+1, value + curOperandVal, curOperandVal);
                    }
                    if (operator[j] == "-") {
                        dfs(num, res, cur + operator[j] + curOperand, i+1, value - curOperandVal, -curOperandVal);
                    }
                     if (operator[j] == "*") {
                         long newValue = value - previousOperand + previousOperand * curOperandVal;
                         // previousOperand must updated to curOperandVal*previousOperand, for continous multiplication like 2*4*8*...
                        dfs(num, res, cur + operator[j] + curOperand, i+1, newValue, curOperandVal*previousOperand);
                    }
                }
        }
    }
}
