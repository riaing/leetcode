Shuffle a set of numbers without duplicates.

Example:

// Init an array with set 1, 2, and 3.
int[] nums = {1,2,3};
Solution solution = new Solution(nums);

// Shuffle the array [1,2,3] and return its result. Any permutation of [1,2,3] must equally likely to be returned.
solution.shuffle();

// Resets the array back to its original configuration [1,2,3].
solution.reset();

// Returns the random shuffling of array [1,2,3].
solution.shuffle();

由于reset()方法要时刻返回原始数组,所以我们设置了一个备份数组来保存原始数组;

shuffle()方法要返回数组里面元素重新排序之后的数组,采用的方法是用数组里面随机一个位置的数字和当前位置的数字交换


public class Solution {
    private int[] nums;
    private Random random;
    private int[] ori;
    
    public Solution(int[] nums) {
        this.nums = nums;
        this.random = new Random();
        this.ori = Arrays.copyOf(nums, nums.length);
    }
    
    /** Resets the array to its original configuration and return it. */
    public int[] reset() {
        return ori; 
    }
    
    /** Returns a random shuffling of the array. */
    public int[] shuffle() {
        for(int i = 0; i < nums.length; i ++){
            int index = random.nextInt(i+1);
            swap (nums, i , index);
        }
        return nums;
        
    }
    
    private void swap(int[] nums, int i , int j ){
        int tmp = nums[i];
        nums[i] = nums[j];
        nums[j]= tmp; 
    }
}


/**
 * Your Solution object will be instantiated and called as such:
 * Solution obj = new Solution(nums);
 * int[] param_1 = obj.reset();
 * int[] param_2 = obj.shuffle();
 */
