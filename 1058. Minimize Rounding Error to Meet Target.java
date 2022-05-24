Given an array of prices [p1,p2...,pn] and a target, round each price pi to Roundi(pi) so that the rounded array [Round1(p1),Round2(p2)...,Roundn(pn)] sums to the given target. Each operation Roundi(pi) could be either Floor(pi) or Ceil(pi).

Return the string "-1" if the rounded array is impossible to sum to target. Otherwise, return the smallest rounding error, which is defined as Σ |Roundi(pi) - (pi)| for i from 1 to n, as a string with three places after the decimal.

 

Example 1:

Input: prices = ["0.700","2.800","4.900"], target = 8
Output: "1.000"
Explanation:
Use Floor, Ceil and Ceil operations to get (0.7 - 0) + (3 - 2.8) + (5 - 4.9) = 0.7 + 0.2 + 0.1 = 1.0 .
Example 2:

Input: prices = ["1.500","2.500","3.500"], target = 10
Output: "-1"
Explanation: It is impossible to meet the target.
Example 3:

Input: prices = ["1.500","2.500","3.500"], target = 9
Output: "1.500"
 

Constraints:

1 <= prices.length <= 500
Each string prices[i] represents a real number in the range [0.0, 1000.0] and has exactly 3 decimal places.
0 <= target <= 106

------------------------- Greedy ------------------------------------------------------------
class Pair {
    int index;
    double decimal; 
    
    public Pair(int index, double decimal) {
        this.index = index;
        this.decimal = decimal;
    }
}

/*
Greedy 解法
第二问： print rounded array： https://leetcode.com/discuss/interview-question/algorithms/125001/airbnb-phone-screen-minimize-rounding-error-to-meet-target 

时间： sort O（nlgn）
*/
class Solution {
    public String minimizeError(String[] prices, int target) {
        // 1. 取出整数sum，把小数放到个list里
        int integerSum = 0; 
        List<Pair> decimals = new ArrayList<Pair>(); // 第二问：print round后的数组
        for (int i = 0; i < prices.length; i++) {
            String s = prices[i];
            String[] cur = s.split("\\."); // split by dot. need to escape 
            integerSum += Integer.parseInt(cur[0]); 
            double toAdd = Double.parseDouble(cur[1]) /1000;
            decimals.add(new Pair(i, toAdd));
        }
        // 1. 判断一下，全部round down还 > target, 或全部round up还 < target, 则 -1 
        if (integerSum > target || (integerSum + decimals.size() < target)) {
            return "-1"; 
        }
        // 2. find how many 1 need to add to reach target 
        int oneToAdd = target - integerSum; 
        // 3. sort the decimal part, and round the decimal part to 1 by oneToAdd times, from the end 
        Collections.sort(decimals, (a, b) -> Double.compare(a.decimal, b.decimal));// 从小到大sort
        double res = 0.0; 
        double[] roundedResult = new double[prices.length]; // 第二问：print round后的数组
        for (int i = decimals.size() - 1; i>= 0; i--) {
            int itsIndex = decimals.get(i).index;
            double itsDesimal = decimals.get(i).decimal;
            if (oneToAdd > 0) { // 说明当前decimal部分应该round up
                if (itsDesimal == 0) { // 说明原数是 2.000 这种没小数，无法round up
                    return "-1"; 
                }
                res += (1 - itsDesimal);
                roundedResult[itsIndex] = Math.ceil(Double.parseDouble(prices[itsIndex]));
                oneToAdd--;
            }
            else {
                res += itsDesimal; // 当前round down
                roundedResult[itsIndex] = Math.floor(Double.parseDouble(prices[itsIndex]));
            }
        }
        System.out.println(Arrays.toString(roundedResult));
        return String.format("%.3f", res);
    }
}
