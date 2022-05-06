You are given a 0-indexed array of positive integers w where w[i] describes the weight of the ith index.

You need to implement the function pickIndex(), which randomly picks an index in the range [0, w.length - 1] (inclusive) and returns it. The probability of picking an index i is w[i] / sum(w).

For example, if w = [1, 3], the probability of picking index 0 is 1 / (1 + 3) = 0.25 (i.e., 25%), and the probability of picking index 1 is 3 / (1 + 3) = 0.75 (i.e., 75%).
 

Example 1:

Input
["Solution","pickIndex"]
[[[1]],[]]
Output
[null,0]

Explanation
Solution solution = new Solution([1]);
solution.pickIndex(); // return 0. The only option is to return 0 since there is only one element in w.
Example 2:

Input
["Solution","pickIndex","pickIndex","pickIndex","pickIndex","pickIndex"]
[[[1,3]],[],[],[],[],[]]
Output
[null,1,1,1,1,0]

Explanation
Solution solution = new Solution([1, 3]);
solution.pickIndex(); // return 1. It is returning the second element (index = 1) that has a probability of 3/4.
solution.pickIndex(); // return 1
solution.pickIndex(); // return 1
solution.pickIndex(); // return 1
solution.pickIndex(); // return 0. It is returning the first element (index = 0) that has a probability of 1/4.

Since this is a randomization problem, multiple answers are allowed.
All of the following outputs can be considered correct:
[null,1,1,1,1,0]
[null,1,1,1,1,1]
[null,1,1,1,0,0]
[null,1,1,1,0,1]
[null,1,0,1,0,0]
......
and so on.
 

Constraints:

1 <= w.length <= 104
1 <= w[i] <= 105
pickIndex will be called at most 104 times.


----------------------------------- preSum + binary search -----------------------------------------------------------------
/*
丢石子问题

1.根据权重数组 w 生成前缀和数组 preSum。
2、生成一个取值在 preSum 之内的随机数，用二分搜索算法寻找大于等于这个随机数的最小元素索引。
*/
class Solution {
        int[] preSum;
    public Solution(int[] w) {
        this.preSum = new int[w.length];
        preSum[0] = w[0];
        for (int i = 1; i < w.length; i++) {
            preSum[i] = preSum[i-1] + w[i];
        }
    }
    
    public int pickIndex() {
        // 2. random 生成一个target在preSum范围内
        Random rand = new Random(); 
        // function distributed int value between 0 (inclusive) and the specified value (exclusive),
        int max = preSum[preSum.length - 1];
        int min = preSum[0];
        // 这是最正规的办法，但这里code不让过。就用下面的好了
        // int target2 = rand.nextInt(max - min + 1) + min;  //(max - min + 1) + min. get random between [min and max]
        int target = rand.nextInt(max)+1;
        
        // 3. find the first value that is equal or larger than target - binary search 
        int potential = firstEqualOrLargerThenK(preSum, target);
        return potential < preSum.length ? potential : potential - 1; 
        
    }
    
    private int firstEqualOrLargerThenK(int[] nums, int k) {
        int start = 0; 
        int end = nums.length - 1; 
        while (start <= end) {
            int mid = start + (end - start) / 2;
            if (nums[mid] == k) {
                return mid;
            }
            else if (nums[mid] <= k) {
                start = start + 1;
            }
            else {
                end = end - 1;
            }
        }
        
        if (start - 1 >= 0 && nums[start-1] == k) {
            return start-1;
        }
        return start; // 可能出右边界
    }
}

/**
 * Your Solution object will be instantiated and called as such:
 * Solution obj = new Solution(w);
 * int param_1 = obj.pickIndex();
 */
