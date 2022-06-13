There are several cards arranged in a row, and each card has an associated number of points. The points are given in the integer array cardPoints.

In one step, you can take one card from the beginning or from the end of the row. You have to take exactly k cards.

Your score is the sum of the points of the cards you have taken.

Given the integer array cardPoints and the integer k, return the maximum score you can obtain.

 

Example 1:

Input: cardPoints = [1,2,3,4,5,6,1], k = 3
Output: 12
Explanation: After the first step, your score will always be 1. However, choosing the rightmost card first will maximize your total score. The optimal strategy is to take the three cards on the right, giving a final score of 1 + 6 + 5 = 12.
Example 2:

Input: cardPoints = [2,2,2], k = 2
Output: 4
Explanation: Regardless of which two cards you take, your score will always be 4.
Example 3:

Input: cardPoints = [9,7,7,9,7,7,9], k = 7
Output: 55
Explanation: You have to take all the cards. Your score is the sum of points of all cards.
 

Constraints:

1 <= cardPoints.length <= 105
1 <= cardPoints[i] <= 104
1 <= k <= cardPoints.length

---------------------------------------- sliding window ------------------------------------------------------------
/*
sliding window 
1. 求总和
2. 维持一个大小为len - k的window，每次的值就是 总和-window和
Time : O(n), space O(1)

DP
维持prefixSum和suffixSum两个dp数组，大小为k+1 
通过循环，找两个数组的cmbination和为k
Time O(k), space (Ok)

*/
class Solution {
    public int maxScore(int[] cardPoints, int k) {   
        // 1. 找到总和
        int total = 0;
        for (int i : cardPoints) {
            total += i;
        }
        int windowSize = cardPoints.length - k; 
         // corner case, window size = 0 
        if (windowSize == 0) {
            return total; 
        }
        // 3. 维持window来找到解
        int start = 0;
        int windowSum = 0;
        int res = 0;
        for (int end = start; end < cardPoints.length; end++) {
            windowSum += cardPoints[end];
            if (end - start + 1 == windowSize) {
                res = Math.max(res, total - windowSum); 
                windowSum -= cardPoints[start]; 
                start++; 
            }
        }
        return res; 
    }
}
