You are given an integer array cookies, where cookies[i] denotes the number of cookies in the ith bag. You are also given an integer k that denotes the number of children to distribute all the bags of cookies to. All the cookies in the same bag must go to the same child and cannot be split up.

The unfairness of a distribution is defined as the maximum total cookies obtained by a single child in the distribution.

Return the minimum unfairness of all distributions.

 

Example 1:

Input: cookies = [8,15,10,20,8], k = 2
Output: 31
Explanation: One optimal distribution is [8,15,8] and [10,20]
- The 1st child receives [8,15,8] which has a total of 8 + 15 + 8 = 31 cookies.
- The 2nd child receives [10,20] which has a total of 10 + 20 = 30 cookies.
The unfairness of the distribution is max(31,30) = 31.
It can be shown that there is no distribution with an unfairness less than 31.
Example 2:

Input: cookies = [6,1,3,2,2,4,1,2], k = 3
Output: 7
Explanation: One optimal distribution is [6,1], [3,2,2], and [4,1,2]
- The 1st child receives [6,1] which has a total of 6 + 1 = 7 cookies.
- The 2nd child receives [3,2,2] which has a total of 3 + 2 + 2 = 7 cookies.
- The 3rd child receives [4,1,2] which has a total of 4 + 1 + 2 = 7 cookies.
The unfairness of the distribution is max(7,7,7) = 7.
It can be shown that there is no distribution with an unfairness less than 7.
 

Constraints:

2 <= cookies.length <= 8
1 <= cookies[i] <= 105
2 <= k <= cookies.length


---------- 解法 -------------------------------------------------
/*
和1723题一模一样

1、 暴力（DFS）：K^N. 每个饼干有K个选择，总共N块饼干

2.1 剪枝：BS + DFS （on cookies）：二分，先猜一个答案，看能不能使每个人的总是小于猜的答案。如果能找到方案，再往下猜
BS给DFS提供了一个upperbound 

2.2 BS + DFS on 人： https://youtu.be/tAIzu_MWa8U?t=1549 

3，本题还可以状态压缩来解

*/


------------ 暴力dfs -------
class Solution {
    int max = Integer.MAX_VALUE; 
    public int distributeCookies(int[] cookies, int k) {
        int[] bags = new int[k];
        helper(cookies, 0, bags);
        return max; 
        
    }
    private void helper(int[] cookies, int index, int[] bags) {
        if (index == cookies.length) {
            int curMax = 0;
            for (int num : bags) {
                curMax = Math.max(curMax, num);
            }
            max = Math.min(curMax, max);
            return;
        }
        for (int i = 0; i < bags.length; i++) {
            int curCookie = cookies[index];
            int preNum = bags[i];
            bags[i] = bags[i] + curCookie;
            helper(cookies, index+1, bags);
            bags[i] = preNum;
        }
    }
}

--------- BS + DFS ------------
class Solution {
    int max = Integer.MAX_VALUE; 
    public int distributeCookies(int[] cookies, int k) {
        // 剪枝技巧1 ， 把cookie 从大到小排序，让尽早剪枝
        // Arrays.sort(cookies, reverse order)
        
        int[] bags = new int[k];
        int left = 0; int right = Integer.MAX_VALUE;
        while (left < right) {
            bags = new int[k];
            int mid = left + (right - left) / 2;
            if (helper(cookies, 0, bags, mid)) {
                right = mid;
            }
            else {
                left = mid + 1;
            }
        }
        return left; // 如果题目一定有解，return 这个 
        
    }
    
    
    private boolean helper(int[] cookies, int index, int[] bags, int limit) {
        if (index == cookies.length) {
            return true;
        }
        
        for (int i = 0; i < bags.length; i++) {
            int curCookie = cookies[index];
            if (curCookie + bags[i] > limit) {
                continue;
            }
            
            int preNum = bags[i];
            bags[i] = bags[i] + curCookie;
            if (helper(cookies, index+1, bags, limit)) { // 如果当前组合能找到方案
                return true; 
            }
            bags[i] = preNum;
        }
        return false; 
    }
}

