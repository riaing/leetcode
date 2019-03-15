Given an array of integers nums and a positive integer k, find whether it's possible to divide this array into k non-empty subsets whose sums are all equal.

 

Example 1:

Input: nums = [4, 3, 2, 3, 5, 2, 1], k = 4
Output: True
Explanation: It's possible to divide it into 4 subsets (5), (1, 4), (2,3), (2,3) with equal sums.
 

Note:

1 <= k <= len(nums) <= 16.
0 < nums[i] < 10000.

-----------------------------基础版，理解递归-----------------------------------------------------------------------------------
/**
这道题我们可以用递归来做，首先我们还是求出数组的所有数字之和sum，首先判断sum是否能整除k，不能整除的话直接返回false。然后需要一个visited数组来记录哪
些数组已经被选中了，然后调用递归函数，我们的目标是组k个子集合，是的每个子集合之和为target = sum/k。我们还需要变量start，表示从数组的某个位置开始查找，
curSum为当前子集合之和，在递归函数中，如果k=1，说明此时只需要组一个子集合，那么当前的就是了，直接返回true。如果curSum等于target了，那么我们再次调用
递归，此时传入k-1，start和curSum都重置为0，因为我们当前又找到了一个和为target的子集合，要开始继续找下一个。否则的话就从start开始遍历数组，如果当前
数字已经访问过了则直接跳过，否则标记为已访问。然后调用递归函数，k保持不变，因为还在累加当前的子集合，start传入i+1，curSum传入curSum+nums[i]，因为
要累加当前的数字，如果递归函数返回true了，则直接返回true。否则就将当前数字重置为未访问的状态继续遍历

time: o(n* n^2)? 
**/
class Solution {
    public boolean canPartitionKSubsets(int[] nums, int k) {
        //find the average num of this list, all subsets must add up to this ave
        int sum = 0;
        for(int num : nums) {
            sum += num;
        }
        // if it's not divisible, return false; 
        if (sum % k != 0) {
            return false;
        }
        int ave = sum / k;
        
        boolean[] visited = new boolean[nums.length];
        return search(0, 0, ave, k, nums, visited);  
    }
    
    private boolean search(int curSum, int start, int target, int k, int[] nums, boolean[] visited) {
        if (k == 1) {
            return true;
        }
        // if we found we subset, we need to search for another subset, by reset curSum to 0, start to 0(means we will search from beginning again), and k-1(means we've already found one subset)
        if (curSum == target) {
            return search(0, 0, target, k-1, nums, visited);
        }
        //if we don't find one subset, we continue by adding numbers to current subset
        for (int i = start; i < nums.length; i++) {
            if (visited[i]) {
                continue;
            }
            visited[i] = true;
            if (search(curSum+nums[i], i+1, target, k, nums, visited)) {
               
                return true;
            }
            visited[i] = false;
        }
        return false;
    }
}
-------------基础版升级，排序加剪枝----------------------------------
 /**
比如先给数组按从大到小的顺序排个序，然后在递归函数中，我们可以直接判断，如果curSum大于target了，直接返回false，因为题目中限定了都是正数，并且我们也给数组排序了，后面的数字只能更大，这个剪枝操作大大的提高了运行速度

time: o(n* n^2)? 
**/
class Solution {
    public boolean canPartitionKSubsets(int[] nums, int k) {
        Arrays.sort(nums);
        //find the average num of this list, all subsets must add up to this ave
        int sum = 0;
        for(int num : nums) {
            sum += num;
        }
        // if it's not divisible, return false; 
        if (sum % k != 0) {
            return false;
        }
        int ave = sum / k;
        
        boolean[] visited = new boolean[nums.length];
        return search(0, 0, ave, k, nums, visited);  
    }
    
    private boolean search(int curSum, int start, int target, int k, int[] nums, boolean[] visited) {
        // prune
        if(curSum > target) {
            return false;
        }
        
        if (k == 1) {
            return true;
        }
     
        // if we found we subset, we need to search for another subset, by reset curSum to 0, start to 0(means we will search from beginning again), and k-1(means we've already found one subset)
        if (curSum == target) {
            return search(0, 0, target, k-1, nums, visited);
        }
        //if we don't find one subset, we continue by adding numbers to current subset
        for (int i = start; i < nums.length; i++) {
            if (visited[i]) {
                continue;
            }
            visited[i] = true;
            if (search(curSum+nums[i], i+1, target, k, nums, visited)) {
                return true;
            }
            visited[i] = false;
        }
        return false;
    }
}
