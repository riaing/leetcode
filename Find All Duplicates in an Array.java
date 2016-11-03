
Could you do it without extra space and in O(n) runtime?

Example:
Input:
[4,3,2,7,8,2,3,1]

Output:
[2,3]

//since the duplicate is exactly twice, use abs to record if it occurs before  
public class Solution {
    public List<Integer> findDuplicates(int[] nums) {
         List<Integer> result = new ArrayList<Integer>();
        if (nums == null ){
            return result; 
            
        }
       
        for ( int i = 0 ; i < nums.length; i ++ ){
            if  (nums[Math.abs(nums[i])-1] > 0){
                    nums[Math.abs(nums[i])-1] = -nums[Math.abs(nums[i])-1]; 
                    //the condition is the array is 1 ≤ a[i] ≤ n (n = size of array), means a[a[i]] will never be out of bound 
            } 
            else{
                result.add(Math.abs(nums[i]));
            }
        }
        return result; 
    }
}
