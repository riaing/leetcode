Given an integer array, adjust each integers so that the difference of every adjacent integers are not greater than a given number target.

If the array before adjustment is A, the array after adjustment is B, you should minimize the sum of |A[i]-B[i]|

Example
Example 1:
	Input:  [1,4,2,3], target=1
	Output:  2

Example 2:
	Input:  [3,5,4,7], target=2
	Output:  1
	
Notice
You can assume each number in the array is a positive integer and not greater than 100.


----------------------------------------------------------backpack problem --------------------------------------------
public class Solution {
    /*
    m[i][j]: 对前i个进行调整，把第i个int调整为j的最小代价
    m[i][j] = min{f[i-1][x]} +|A[i] - j|, x = {j-target, j+ target}
    
    start: f[0][...j] = 0; f[...i][0] = max MAX_VALUE
    return: f[length][0...j]最小值
    
    
    
     * @param A: An integer array
     * @param target: An integer
     * @return: An integer
     */
    public int MinAdjustmentCost(List<Integer> A, int target) {
        int[][] m = new int[A.size()+1][100+1];
        // m[0][...] = 0; m[...][0] = max; 
        for (int i = 0; i <= A.size(); i++) {
            for (int j = 0; j <=100; j++) {
                if (i == 0) {
                     m[i][j] = 0;
                }
                else {
                    m[i][j] = Integer.MAX_VALUE;
                }
            }
        }
        
       for (int i = 1; i <= A.size(); i++) {
            for (int j = 0; j <=100; j++) {
                int rangeMin = j - target < 0 ? 0 : j - target;
                int rangeMax = j + target > 100 ? 100 : j + target;
                int minLastChange = Integer.MAX_VALUE;
                for (int k = rangeMin; k <= rangeMax; k++) {
                    minLastChange = Math.min(minLastChange, m[i-1][k]);
                }
                m[i][j] = minLastChange + Math.abs(A.get(i-1) - j); 
            }
       }
       
       // 找最后一行的最小值
       int res = Integer.MAX_VALUE;
        for (int j = 0; j <=100; j++) {
             res = Math.min(res, m[A.size()][j]);
        }
        
        return res;
    }
}
