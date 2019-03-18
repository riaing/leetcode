Given a string of numbers and operators, return all possible results from computing all the different possible ways to group numbers and operators. The valid operators are +, - and *.

Example 1:

Input: "2-1-1"
Output: [0, 2]
Explanation: 
((2-1)-1) = 0 
(2-(1-1)) = 2
Example 2:

Input: "2*3-4*5"
Output: [-34, -14, -10, -10, 10]
Explanation: 
(2*(3-(4*5))) = -34 
((2*3)-(4*5)) = -14 
((2*(3-4))*5) = -10 
(2*((3-4)*5)) = -10 
(((2*3)-4)*5) = 10


-------------------------recursion-------------------------------------------------
/**
将符号看做分割点，循环数组，碰到符号时，递归符号左右两边，得到所有可能的解，再组合运算加到result中
time：http://people.math.sc.edu/howard/Classes/554b/catalan.pdf 是个阶乘
**/
class Solution {
    public List<Integer> diffWaysToCompute(String input) {
    
        return dfs(input);
    }
    
    //面试中要考虑用set进行去重
    private List<Integer> dfs(String input) {
         List<Integer> res = new ArrayList<Integer>();
      
        for (int i = 0; i < input.length(); i++) {
            //如果找到符号，分别递归符号左边和右边，找到所有返回的值，再根据这个符号运算，加到返回的集合中
            if (isSymbol(input.charAt(i))) {
                List<Integer> right = dfs(input.substring(0, i)); 
                List<Integer> left = dfs(input.substring(i+1));
 
                //这里拿到左右两边返回的值，进行组合运算
                for (int r = 0; r < right.size(); r++){
                    for (int l = 0; l < left.size(); l++) {
                        int rightValue = right.get(r);
                        int leftValue = left.get(l);
                        if (input.charAt(i) == '+') {
                             res.add(rightValue + leftValue);
                        }
                        if (input.charAt(i) == '-') {
                            res.add(rightValue - leftValue);
                            
                        }
                        if (input.charAt(i) == '*') {
                            res.add(rightValue * leftValue);
                        }
                    }
                }
            }
        }
         //这里其实是一个base case，就是说当找不到符号时，说明input是一个数字，我们直接把数字加到返回集合里。这个数字可能是多位数，所以我们把这个base case放到找符号的loop后处理。
         if (res.isEmpty()) {
            res.add(Integer.parseInt(input));
        }
        return res;
    }
    
    private boolean isSymbol(char c) {
        return c - '0' < 0 || c - '0' > 9; 
    }
}
