You have 4 cards each containing a number from 1 to 9. You need to judge whether they could operated through *, /, +, -, (, ) 
    to get the value of 24.

Example 1:
Input: [4, 1, 8, 7]
Output: True
Explanation: (8-4) * (7-1) = 24
Example 2:
Input: [1, 2, 1, 2]
Output: False
Note:
The division operator / represents real division, not integer division. For example, 4 / (1 - 2/3) = 12.
Every operation done is between two numbers. In particular, we cannot use - as a unary operator. For example, with [1, 1, 1, 1] as 
input, the expression -1 - 1 - 1 - 1 is not allowed.
You cannot concatenate numbers together. For example, if the input is [1, 2, 1, 2], we cannot write this as 12 + 12.

----------------------------------------------------------------------
/*
考虑回溯法，首先我们从集合A = {1 、2、3、4}中任意取出两个数，如取1、2，那么A = A - {1、2}，对取出来的两个数字分别进行不同的四则运算，1 + 2、1 - 2……，将结果加入A中，有{3、3、4}、{-1，3，4}等，通过这种方法，将四个数降为三个数，然后降为两个数……

扩展：并且printout其中一个解：用一个stringRes存每次的结果, stringRes和res做同样的运算
*/
class Solution {
    static double eps = 1e-4;
    boolean found = false;
    public boolean judgePoint24(int[] nums) {
        List<Double> res = new ArrayList<Double>();
        // 不用printout值时，去掉这一个
        List<String> stringRes = new ArrayList<String>();
        for (int i : nums) {
            res.add((double) i);
            stringRes.add(i+"");
        }
        
        boolean returnType = helper(res, stringRes);
        return returnType;   
    }
    
    private boolean helper(List<Double> res, List<String> stringRes) {
        if (res.size() == 1 && Math.abs(res.get(0) - 24) < eps) {
            System.out.println(stringRes.get(0));
            return true;
        }
        
        // get two numbers and do calculation 
        for (int i = 0; i < res.size(); i++) {
            for (int j = 0; j < i; j++) {
                double p = res.get(i);
                double q = res.get(j);
                String ps = stringRes.get(i);
                String qs = stringRes.get(j);
                List<Double> tmp = new ArrayList<Double>(Arrays.asList(p+q, p-q, q-p, q*p));
                 // 不用printout值时，去掉以下关于stringTmp这段
                String plus = "(" + ps +"+" + qs + ")";
                String minus1 = "(" + ps +"-" + qs + ")";
                String minus2 = "(" + qs +"-" + ps + ")";
                String times = "(" + qs +"*" + ps + ")";
                List<String> stringTmp = new ArrayList<String>(Arrays.asList(plus, minus1, minus2, times));
                // add dividends 
                if (p > eps) {
                    tmp.add(q/p);
                    stringTmp.add("(" + qs +"/" + ps + ")");
                }
                if (q > eps) {
                    tmp.add(p/q);
                    stringTmp.add("(" + ps +"/" + qs + ")");
                }
                res.remove(i);
                res.remove(j);
                stringRes.remove(i);
                stringRes.remove(j);
                
                for (int n = 0; n < tmp.size(); n++) {
                    res.add(tmp.get(n));
                    stringRes.add(stringTmp.get(n));
                    if (helper(res, stringRes)) {
                        return true;
                    }
                    res.remove(res.size()-1);
                    stringRes.remove(stringRes.size() - 1);
                }
                // 顺序重要
                res.add(j, q);
                res.add(i, p);
                stringRes.add(j, qs);
                stringRes.add(i, ps);
            }
        }
        return false;
    } 
}

--------------------------- backtracking -------------
    /*
这里的N = 4
时间：
第一层： N *N-1 * 4个选择
第二层： N-1 * N-2 * 4 
第三层： N-2 * N-3 * 4 
总共N层
所以N！*(N-1)!*4^N 

空间：
recursive stack = N
每层copy一个新array，大小为 N-1， N-2。。。2， 1 相加 = O（N^2)  
*/
class Solution {
    public boolean judgePoint24(int[] cards) {
        LinkedList<Double> input = new LinkedList<>();
        for (int i = 0; i < cards.length; i++) {
            input.add(cards[i] * 1.0);
        }
        return helper(input);
        
    }
    
    private boolean helper(LinkedList<Double> input) {
        if (input.size() == 1) {
            return  Math.abs(input.get(0) - 24) <= 0.00001; // Math.round(input.get(0)) == 24; 不能用round！ 24.4 算错
        }
        
        for (int i = 0; i < input.size(); i++) {
            for (int j = 0; j < input.size(); j++) {
                if (j == i) {
                    continue; 
                }
                double first = input.get(i);
                double second = input.get(j);
                LinkedList<Double> copy = new LinkedList<>(input);
                copy.remove(Double.valueOf(first));  copy.remove(Double.valueOf(second));
                char[] symbol = {'+', '-', '*', '/'};
                for (int k = 0; k < symbol.length; k++) {
                    double val = calculate(first, second, symbol[k]); 
                    copy.add(val);
                    if (helper(copy)) {
                        return true;
                    }
                    copy.removeLast();
                }
            }
        }
        return false; 
    }
    
    private double calculate(double first, double second, char symbol) {
        double res = 0; 
        if (symbol == '+') {
            res = first + second; 
        }
        else if (symbol == '-') {
            res = first - second;
        }
        else if (symbol == '*') {
            res = first * second;
        }
        else if (symbol == '/') {
            res = first / second;
        }
        return res; 
    }
}
