Given an array of integers and an integer k, you need to find the total number of continuous subarrays whose sum equals to k.

Example 1:
Input:nums = [1,1,1], k = 2
Output: 2
Note:
The length of the array is in range [1, 20,000].
The range of numbers in the array is [-1000, 1000] and the range of the integer k is [-1e7, 1e7].

-------------------------------------------------map
/**
Solution 1. Brute force. We just need two loops (i, j) and test if SUM[i, j] = k. Time complexity O(n^2), Space complexity O(1). I bet this solution will TLE.

Solution 2. From solution 1, we know the key to solve this problem is SUM[i, j]. So if we know SUM[0, i - 1] and SUM[0, j], then we can easily get SUM[i, j]. To achieve this, we just need to go through the array, calculate the current sum and save number of all seen PreSum to a HashMap. Time complexity O(n), Space complexity O(n)
注意这里的特殊情况，当sum[0,j] = k时，也就是  SUM[0, j] - k = SUM[0, i - 1] = 0, 
所以map初始时要放入（0,1）pair
*/
class Solution {
    public int subarraySum(int[] nums, int k) {
        //前数组之和，value = 此和出现的次数
        Map<Integer, Integer> map = new HashMap<Integer, Integer>();
        //注意特殊情况，当sum[0,j] = k时，也就是sum[0,i-1] = 0； 所以map初始时放入sum=0，time=1的pair
        map.put(0,1);
        int res = 0; 
        int sum = 0; // this is the accumulated sum 
        for (int j = 0; j < nums.length; j++) {
            sum += nums[j]; // sum[0,j]
            if (map.containsKey(sum-k)) { // if sum[0, i-1] exists 注意题目中给了数组中的数都是大于0，否则得判断abs值
                res += map.get(sum-k);
            }
            map.put(sum, map.getOrDefault(sum, 0) + 1);
        }
        return res;
    }
}

---------------- preSum + Map O(n) ----------------------
    /*
preSum DP。 o(n^2) 
DP + Map: O(n) -> sum(i,j)=sum(0,j)-sum(0,i). 当遇到sum(0,j)时，通过map找有几个sum(0, i). 
*/
class Solution {
    public int subarraySum(int[] nums, int k) {
        int res = 0; 
        Map<Integer, Integer> countMap = new HashMap<>(); // preSum -> 对应的count
        int[] dp = new int[nums.length+1];
        dp[0] = 0; 
        countMap.put(0, 1); 
        
        for (int i = 1; i <= nums.length; i++) {
            // 1. 计算dp[i]
            dp[i] = dp[i-1] + nums[i-1];
            int need = dp[i] - k; 

            // 2. 找i前面有没有对应的dp[j]. 如果之前存在值为 need 的前缀和. 说明存在以 nums[i-1] 结尾的子数组的和为 k 
            if (countMap.containsKey(need)) {
                res += countMap.get(need);
            }
            // 3. 更新Map
            countMap.put(dp[i], countMap.getOrDefault(dp[i], 0) + 1);
        }
        

        return res; 
    }
}
