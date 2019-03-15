Given an array of integers nums and a positive integer k, find whether it's possible to divide this array into k non-empty subsets whose sums are all equal.

 

Example 1:

Input: nums = [4, 3, 2, 3, 5, 2, 1], k = 4
Output: True
Explanation: It's possible to divide it into 4 subsets (5), (1, 4), (2,3), (2,3) with equal sums.
 

Note:

1 <= k <= len(nums) <= 16.
0 < nums[i] < 10000.

 
 
 
 ------------------------------real bucket ------------------------------------------------------------------------------
 /**
https://www.youtube.com/watch?v=8XEcEYsG6Ck 

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
        if (nums[nums.length-1] > ave) {
            return false;
        }
        return search(new int[k], nums.length-1, ave, nums);  
    }
    
    
    private boolean search(int[] bucket, int index, int target, int[] nums) {
        if(index < 0) {
            return true;
        }

        for (int i = 0; i < bucket.length; i++) {
            if (bucket[i] + nums[index] > target) {
                continue;
            }
            // put this number into bucket
            bucket[i] += nums[index];
            if (search(bucket, index-1, target, nums)) {
                return true;
            }
            // we can't put this number into bucket, then we backtracking
            bucket[i] -= nums[index];
            if (bucket[i] == 0) { break;} //说明nums[index]不能放到任何一个empty的bucket里面。直接返回。
        }
        return false;
    }
}

-----------------------------virtual bucket，理解递归-----------------------------------------------------------------------------------
/**
这道题我们可以用递归来做，首先我们还是求出数组的所有数字之和sum，首先判断sum是否能整除k，不能整除的话直接返回false。
Virtual bucket: https://www.youtube.com/watch?v=qpgqhp_9d1s 

time: o(k^n)? 
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
        // 如果填满一个bucket, 我们接下来去填下一个bucket, by reset curSum to 0, start to 0(means we will search from beginning again), and k-1(means we've already found one subset)
        if (curSum == target) {
            return search(0, 0, target, k-1, nums, visited);
        }
        //if we don't find one subset, we continue by adding numbers to current subset
        for (int i = start; i < nums.length; i++) {
            if (visited[i]) {
                continue;
            }
          // 尝试把当前数放入bucket，看这条路行不行，不行就backtracking、
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

time: o(n！)? 
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
