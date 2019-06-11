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
