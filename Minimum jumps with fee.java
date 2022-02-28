Given a staircase with ‘n’ steps and an array of ‘n’ numbers representing the fee that you have to pay if you take the step. Implement a method to 
calculate the minimum fee required to reach the top of the staircase (beyond the top-most step). At every step, you have an option to take either 
1 step, 2 steps, or 3 steps. You should assume that you are standing at the first step.

Example 1:

Number of stairs (n) : 6
Fee: {1,2,5,2,1,2}
Output: 3
Explanation: Starting from index '0', we can reach the top through: 0->3->top
The total fee we have to pay will be (1+2).
Example 2:

Number of stairs (n): 4
Fee: {2,3,4,5}
Output: 5
Explanation: Starting from index '0', we can reach the top through: 0->1->top
The total fee we have to pay will be (2+3).

-------------- sequence dp  ----------------------------

class Staircase {

  public int findMinFee(int[] fee) {
    int[] dp = new int[fee.length+1]; //重点！是要求 n+1位的值
    dp[0] = 0;
    for (int i = 1; i <= fee.length; i++) {
      dp[i] = Integer.MAX_VALUE; 
      for (int j = 1; j <=3; j++) {
        if (i-j >= 0 && dp[i-j] != Integer.MAX_VALUE) {
          dp[i] = Math.min(dp[i], dp[i-j] + fee[i-j]); 
        }
      }
    }
    return dp[fee.length]; // 重点
  }
  
  public static void main(String[] args) {
    Staircase sc = new Staircase();
    int[] fee = {1,2,5,2,1,2};
    System.out.println(sc.findMinFee(fee));
    fee = new int[]{2,3,4,5};
    System.out.println(sc.findMinFee(fee));
  }
}
