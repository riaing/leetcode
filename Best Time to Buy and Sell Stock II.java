public class Solution {
    public int maxProfit(int[] prices) {
        if( prices == null || prices.length < 2 ){
            return 0;
        }
        int maxP = 0;
        for(int i =1; i < prices.length; i ++ ){
            maxP = Math.max(maxP, maxP + prices[i]-prices[i-1]); //只要当天收益为正，就加入
        }
         return maxP;
    }
    
}
