You are given a 0-indexed integer array nums and an integer k.

You are initially standing at index 0. In one move, you can jump at most k steps forward without going outside the boundaries of the array. That is, you can jump from index i to any index in the range [i + 1, min(n - 1, i + k)] inclusive.

You want to reach the last index of the array (index n - 1). Your score is the sum of all nums[j] for each index j you visited in the array.

Return the maximum score you can get.

 

Example 1:

Input: nums = [1,-1,-2,4,-7,3], k = 2
Output: 7
Explanation: You can choose your jumps forming the subsequence [1,-1,4,3] (underlined above). The sum is 7.
Example 2:

Input: nums = [10,-5,-2,4,0,3], k = 3
Output: 17
Explanation: You can choose your jumps forming the subsequence [10,4,3] (underlined above). The sum is 17.
Example 3:

Input: nums = [1,-5,-20,4,-1,3,-6,-3], k = 2
Output: 0
 

Constraints:

1 <= nums.length, k <= 105
-104 <= nums[i] <= 104


------------------ 普通DP， 超时---------------------------------------
/*
普通DP。 时间O（n*k) 超时
*/
class Solution {
    public int maxResult(int[] nums, int k) {
        int[] dp = new int[nums.length];
        dp[0] = nums[0];
        for (int i = 1; i < nums.length; i++) {
            dp[i] = Integer.MIN_VALUE;
             for (int n = 1; n <=k && i - n >= 0; n++) {
                dp[i] = Math.max(dp[i-n] + nums[i], dp[i]);
            }
        }
        return dp[nums.length - 1]; 
    }
} 

---------------------------------DP + monotonic queue 优化 --------------------------

/*
普通DP。 时间O（n*k) 超时。 
when calculating maximum, we do not need all score[i-k], ..., score[i-1]; we just need some large values. -> 
从 i-k个选择中找最大值时（第二个for loop），可通过monotonic queue来减少时间. 

In this case, we maintain a deque as a monotonically decreasing queue. Since it is monotonically decreasing, the maximum value is always at the top of the queue.
用一个size为k的quque来代表前k个dp的值，从大到小排。
In practice, since we store scores in score, we only need to store the index in the queue.

对于dp[i]， 就先把queue的size 更新为 i-k的范围内，然后top元素就是前k的最大值。

对于新产生的dp[i], 把他放到queue中正确的位置，使得queue仍然是decreasing的。从deque的尾部放起，值小于dp[i]的移出，因为下一个dp[i+1] 肯定不会用他们了

deque的特性：里面的元素值是decrease的

Time： O（n） since we need to iterate nums, and push and pop each element into the deque at most once. 
Space: O(n) 

*/
class Solution {
    public int maxResult(int[] nums, int k) {
        int[] dp = new int[nums.length];
        dp[0] = nums[0];
        Deque<Integer> deque = new LinkedList<>(); // 维持dp[i] decerase的deque
        deque.add(0);
        for (int i = 1; i < nums.length; i++) {
            dp[i] = Integer.MIN_VALUE;
            // 1. 维持window size为 i-k
            while (!deque.isEmpty() && i - deque.peek() > k) {
                deque.removeFirst();
            }
            // 2. 栈顶元素就是前 i-k个中的最大值dp
            dp[i] = nums[i] + dp[deque.peek()];
            
            // 3. 如果当前值大于栈尾了，要插入栈，也说明栈尾永远不会用了。可丢
            while (!deque.isEmpty() && dp[deque.peekLast()] < dp[i]) {
                deque.removeLast();
            }
            deque.addLast(i); 

        }
        return dp[nums.length - 1]; 
    }
}
