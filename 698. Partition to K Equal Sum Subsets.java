Given an array of integers nums and a positive integer k, find whether it's possible to divide this array into k non-empty subsets whose sums are all equal.

 

Example 1:

Input: nums = [4, 3, 2, 3, 5, 2, 1], k = 4
Output: True
Explanation: It's possible to divide it into 4 subsets (5), (1, 4), (2,3), (2,3) with equal sums.
 

Note:

1 <= k <= len(nums) <= 16.
0 < nums[i] < 10000.

 
 
 
 -----------------------------对bucket进行循环 ------------------------------------------------------------------------------
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

-----------------------------对数字进行循环（perferred）超时 -----------------------------------------------------------------------------------
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
-------------对数字进行循环+ prune 超时 ----------------------------------
 /**
比如先给数组按从大到小的顺序排个序，然后在递归函数中，我们可以直接判断，如果curSum大于target了，直接返回false，因为题目中限定了都是正数，并且我们也给数组排序了，后面的数字只能更大，这个剪枝操作大大的提高了运行速度

time: o(k^n) -> k个隔板，每段有n种选择
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

---------------- 2022.2.15 自己直接写的，对桶进行循环 没用 memorization ------------------------------------------
 /*
time: O(k^n *k) 有 k 个选择（放哪个 bucket），然后每个选择假设都可以，那么退出时有 O（k）
*/
class Solution {
    public boolean canPartitionKSubsets(int[] nums, int k) {
       int sum = 0;
        for (int n : nums) {
            sum += n;
        }
        int target = sum / k; 
        if (sum % k != 0) {
            return false; 
        }
        return helper(nums, k, 0, new ArrayList<Integer>(), target);
    }
    
    private boolean helper(int[] nums, int k, int index, List<Integer> bucketSum, int target) {
        if (index == nums.length) { //O(k)
            for (int sum : bucketSum) {
                if (sum != target) {
                    return false;
                }
            }
            return true;
        }
        
        boolean res = false;
        if (bucketSum.size() < k) {
            bucketSum.add(nums[index]); // 新开一bucket
            res = helper(nums, k, index+1, bucketSum, target); 
            if (res) {
                return true; 
            }
            bucketSum.remove(bucketSum.size() - 1);
        }
        
        // 不新开的话，可以加到前面任意 bucket 中
        for (int i = 0; i < bucketSum.size(); i++) {
            int curSum = bucketSum.get(i);
            if (curSum + nums[index] <= target) { // 一定要是正数才需要这个条件
                bucketSum.set(i, curSum + nums[index]);
                res = res || helper(nums, k, index+1, bucketSum, target);
                if (res) {
                    return true;
                }
                bucketSum.set(i, curSum);
            }
        }
        return res; 
    } 
}
 
------------------- 2022.4.29 对数字循环，没用memo超时 ---------------------------------------------
 
// Java program to illustrate copyof method
import java.util.Arrays;

class Solution {
    public boolean canPartitionKSubsets(int[] nums, int k) {
        Arrays.sort(nums);
        int sum = 0;
        for (int n : nums) {
            sum += n;
        }
        //System.out.println(sum);
        int target = sum / k; 
        if (sum % k != 0 || nums[nums.length-1] > target) {
            return false;
        }
        
        boolean[] visited = new boolean[nums.length];
        
        return helper(0, 0, nums, 0, visited, target, k);
        
    }
    
    private boolean helper(int bucketVal, int fullNumBucket, int[] nums,  int index, boolean[] visited, int target, int k) {
        if (fullNumBucket == k) { // 所有桶装完
            return true;
        }
   
        for (int i = index; i < nums.length; i++) {
            if (visited[i]) {
                continue;
            }
            
            int curVal = nums[i];
            if (curVal > target) {
                return false;
            }
            
            if (bucketVal + curVal <= target) {
                int newFullNumBucket = bucketVal + curVal == target ? fullNumBucket + 1 : fullNumBucket;
               
                int newbucketVal =  bucketVal + curVal == target  ? 0 :  bucketVal + curVal; 
                int newIndex = bucketVal + curVal == target  ? 0 : index+1;
                visited[i] = true; 
                if (helper(newbucketVal, newFullNumBucket, nums, newIndex, visited, target, k)) {
                    return true;
                }
                visited[i] = false;
            }
        }
        return false; 
    }
  
}

--------------2022.6 对桶循环 + memo -------------
 
/*
Time: 按照bucket traverse，对于每个bucket，要从头遍历，每个元素选 or 不选 ： O（2^n). 
    k个bucket： O（k* 2^n)
sapce: o(n) : visited数组    

memo: 只记录错的条件，说明用了xyz个元素后不管怎么放bucket根本组成不了解。
反之不行，用了xyz元素如果能组成解，他们放到不同bucket后说不定就错了，所以这是不能记录。
用了memo后就不用 visited[] 了。
*/
class Solution {
    int[] memo;
    public boolean canPartitionKSubsets(int[] nums, int k) {
        this.memo = new int[1<<16];   // 0 - 没用过， 1-true， 2：false
        Arrays.sort(nums); // 进一步优化：可以按end来sort，
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
        return search(0, 0, ave, k, nums, visited, 0);  
    }
    
    private boolean search(int curSum, int start, int target, int k, int[] nums, boolean[] visited, int state) {
        // 因为array是sorted的。 prune
        if(curSum > target) { // 这里不能memo，因为元素换个bucket说不定就行了
            return false;
        }
        
        if (k == 1) { // We made k - 1 subsets with target sum and last subset will also have target sum.
            return true;
        }
        if (memo[state] == 2) {
            return false;
        }
     
        // if we found we subset, we need to search for another subset, by reset curSum to 0, start to 0(means we will search from beginning again), and k-1(means we've already found one subset)
        if (curSum == target) {
            return search(0, 0, target, k-1, nums, visited, state);
        }
        //if we don't find one subset, we continue by adding numbers to current subset
        for (int i = start; i < nums.length; i++) {
            // 这段相当于 下个if 
            // if (visited[i]) {
            //     continue;
            // }
            if ((state & 1 << i) > 0) {
                continue; 
            }
            
            visited[i] = true;
            if (search(curSum+nums[i], i+1, target, k, nums, visited, (state | 1 << i))) {
                return true;
            }
            visited[i] = false;
        }
        memo[state] = 2; 
        return false;
    }
}
