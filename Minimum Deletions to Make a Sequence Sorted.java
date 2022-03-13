Given a number sequence, find the minimum number of elements that should be deleted to make the remaining sequence sorted.

Example 1:

Input: {4,2,3,6,10,1,12}
Output: 2
Explanation: We need to delete {4,1} to make the remaing sequence sorted {2,3,6,10,12}.
Example 2:

Input: {-4,10,3,7,15}
Output: 1
Explanation: We need to delete {10} to make the remaing sequence sorted {-4,3,7,15}.
Example 3:

Input: {3,2,1,0}
Output: 3
Explanation: Since the elements are in reverse order, we have to delete all except one to get a 
sorted sequence. Sorted sequences are {3}, {2}, {1}, and {0}


---------------------------------------------------dp ---------------------------------------------------
/* 
dp[i] 已 i 结尾的increaing sequece, 最少 delete 多少元素
dp[i] = min {
            i : 说明 i 前的元素全删掉
            if i > j， min{dp[j] + (i-j-1). 1 <= j <= i-1}  -> 表示选 j 做为 i immediate前面的数，之间的都删掉 
            
*/             
public class Main {
    public int findMinimumDeletions(int[] nums){
        int[] dp = new int[nums.length];
        dp[0] = 0; 
        int[] from = new int[nums.length]; // 记å录前一个是谁
        from[0] = -1; 
        for (int i = 1; i < nums.length; i++) {
            dp[i] = i; //前面一个都不取，全删
            
            //求 path 
            from[i] = -1; // i之前没有数
            for (int j = i-1; j>=0; j--) {
                if (nums[j] < nums[i]) { 
                    // 求 path 
                    if (dp[j] + (i-j-1) < dp[i]) {
                        from[i] = j; //说明i 前面的数就是 j
                    }
                    
                    dp[i] = Math.min(dp[i], dp[j] + (i-j-1)); // i-j-1是i，j 之间的所有数。表示选 j 做为 i immediate前面的数，之间的都删掉
                }
            }
        }
        
        int minDeletion = nums.length;
        // 求 path 
        int index = nums.length-1;  
        for (int i = nums.length - 1; i >= 0; i--) {
             // 求 path 
            if (dp[i] + nums.length - 1 - i < minDeletion) {
                index = i; //说明以 i 结尾就可以找到正确结果
            }
            
            minDeletion = Math.min(minDeletion, dp[i] + nums.length - 1 - i); //删掉所有尾到 i 的元素，如果取 i 为 sequence 的结尾的话
        }
        
        // 求 path 
        String path = "";
        while (index >= 0) {
            path = nums[index] + "->" + path; 
            index = from[index]; // 告诉 i 之前的数的 index 在哪
        } 
        System.out.println(path); 
    return minDeletion;
  }
    
    
    public static void main(String[] args) {
        Main mdss = new Main();
        int[] nums = {4,2,3,6,10,1,12};
        System.out.println(mdss.findMinimumDeletions(nums));
        System.out.println(" ");
        nums = new int[]{-4,10,3,7,15};
        System.out.println(mdss.findMinimumDeletions(nums));
        System.out.println(" ");
        nums = new int[]{3,2,1,0};
        System.out.println(mdss.findMinimumDeletions(nums));
    }
}
