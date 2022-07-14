You are given an integer array values where values[i] represents the value of the ith sightseeing spot. Two sightseeing spots i and j have a distance j - i between them.

The score of a pair (i < j) of sightseeing spots is values[i] + values[j] + i - j: the sum of the values of the sightseeing spots, minus the distance between them.

Return the maximum score of a pair of sightseeing spots.

 

Example 1:

Input: values = [8,1,5,2,6]
Output: 11
Explanation: i = 0, j = 2, values[i] + values[j] + i - j = 8 + 5 + 0 - 2 = 11
Example 2:

Input: values = [1,2]
Output: 2
 

Constraints:

2 <= values.length <= 5 * 104
1 <= values[i] <= 1000

---------------------------------观察 ---------------------------------
/*
利用加法的分配律，可以得到 A[i] + i + A[j] - j，为了使这个表达式最大化，A[i] + i 自然是越大越好，
*/
class Solution {
    public int maxScoreSightseeingPair(int[] values) {
        int res = 0; int maxA = 0;
        for (int j = 0; j < values.length; j++) {
            res = Math.max(res, maxA + (values[j] - j));
            maxA = Math.max(maxA, values[j] + j);
         }
        return res; 
    }
}
