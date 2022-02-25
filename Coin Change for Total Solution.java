Given an infinite supply of ‘n’ coin denominations and a total money amount, we are asked to find the total number of distinct ways to make up that amount.

Example:

Denominations: {1,2,3}
Total amount: 5
Output: 5
Explanation: There are five ways to make the change for '5', here are those ways:
  1. {1,1,1,1,1} 
  2. {1,1,1,2} 
  3. {1,2,2}
  4. {1,1,3}
  5. {2,3}
  
  
  ------------------------
  class CoinChange {

  public int countChange(int[] coins, int amount) {
      int[][] dp = new int[coins.length][amount+1];
        dp[0][0] = 1;
        
        for (int j = 1; j <= amount; j++) {
            if (j % coins[0] == 0) {
                dp[0][j] = 1;
            }
        }
        
        for (int i = 1; i < coins.length; i++) {
            dp[i][0] = 1;
            for (int j = 1; j <= amount; j++) {
                dp[i][j] = dp[i-1][j];
                if (j-coins[i] >= 0) {
                    dp[i][j] = dp[i-1][j] + dp[i][j-coins[i]]; //- dp[i-1][j-coins[i]];
                     System.out.println("i " + i + "j " + j + " " + dp[i][j]);
                }
            }
        }
        
       return dp[coins.length-1][amount]; 
  }
  
  public static void main(String[] args) {
    CoinChange cc = new CoinChange();
    int[] denominations = {1, 2, 3};
    System.out.println(cc.countChange(denominations, 5));
  }
}
