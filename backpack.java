  https://blog.csdn.net/thousa_ho/article/details/78156678 
  https://segmentfault.com/a/1190000006325321
  
  
  
                                          背包问题一（最大放入重量）
Given n items with size Ai, an integer m denotes the size of a backpack. How full you can fill this backpack?

Example
Example 1:
	Input:  [3,4,8,5], backpack size=10
	Output:  9

Example 2:
	Input:  [2,3,5,7], backpack size=12
	Output:  12
	
Challenge
O(n x m) time and O(m) memory. 

----------------------------o(m*n) memory ---------------

二维数组dp[i][j](0<=i<A.length,0<=j<=m)表示在最大容量为j，可放入容量是A[0..i]的情况下可放入的最大容量，可以分为两种情况

当前背包最大容量小于物品的重量，可容纳的最大重量是dp[i-1][j]
当前背包最大容量小于物品重量，可容纳的最大重量是max(dp[i-1][j],dp[i-1][j-A[i]+A[i])

start: m[0][0...size] = 0; m[0...length][0] = 0
return: m[A.length][size]
------------------------------ 
public class Solution {
    /**
     * @param m: An integer m denotes the size of a backpack
     * @param A: Given n items with size A[i]
     * @return: The maximum size
     */
    public int backPack(int size, int[] A) {
        int[][] m = new int [A.length+1][size+1];
        
        for (int i = 1; i <= A.length; i++) {
            for (int j = 1; j <= size; j++) {
                if (A[i-1] <= j) { // 第i个能放进背包
                    m[i][j] = Math.max(m[i-1][j-A[i-1]] + A[i-1], m[i-1][j]);                    
                }
                else {
                    m[i][j] =  m[i-1][j];
                }
            }
        }
        return m[A.length][size];
    }
}
-------------O(m) memory -----------------------------------------
public int backPack(int[] A, int m) {
    int[] dp = new int[m + 1];
    for (int i = 0; i < A.length; i++) {
        for (int j = m; j > 0; j--) {
            if (j >= A[i]) {
                dp[j] = Math.max(dp[j - A[i]] + A[i], dp[j - 1]);
            }
        }
    }
    return dp[m];
}
----------------------------------------------------------------------------------------------------------------------------

                                                  背包问题二（最大放入价值）
                                                  
和BackPack I基本一致。依然是以背包空间为限制条件，所不同的是dp[j]取的是价值较大值，而非体积较大值。
所以只要把dp[j-A[i]]+A[i]换成dp[j-A[i]]+V[i]就可以了。

public class Solution {
    /**
     * @param m: An integer m denotes the size of a backpack
     * @param A: Given n items with size A[i]
      * @param V: Given n items with value V[i]
     * @return: The maximum size
     */
    public int backPack(int size, int[] A, int[] V) {
        int[][] m = new int [A.length+1][size+1];
        
        
        for (int i = 1; i <= A.length; i++) {
            for (int j = 1; j <= size; j++) {
                if (A[i-1] <= j) { // 第i个能放进背包
                    m[i][j] = Math.max(m[i-1][j-A[i-1]] + V[i], m[i-1][j]);                    
                }
                else {
                    m[i][j] =  m[i-1][j];
                }
            }
        }
        return m[A.length][size];
    }
}

------------------------------------------------------------------------------------------------------------------------------
