Given an array nums of n integers and an integer target, are there elements a, b, c, and d in nums such that a + b + c + d = target? Find all unique quadruplets in the array which gives the sum of target.

Note:

The solution set must not contain duplicate quadruplets.

Example:

Given array nums = [1, 0, -1, 0, -2, 2], and target = 0.

A solution set is:
[
  [-1,  0, 0, 1],
  [-2, -1, 1, 2],
  [-2,  0, 0, 2]
]
----------Map的方法太不好去重了。不写了-----------------------------------------------------------------------
/**
O（n^3）的思路，像3 sum一样，外面两重循环，再转换成2sum
去重：首先在两个for循环下可以各放一个，因为一旦当前的数字跟上面处理过的数字相同了，那么找下来肯定还是重复的。之后就是当sum等于target的时候了，我们在将四个数字加入结果res之后，left和right都需要去重复处理，分别像各自的方面遍历即可，
*/
public class Solution {
    public List<List<Integer>> fourSum(int[] nums, int target) {
        List<List<Integer>> results = new ArrayList<List<Integer>>();
        if(nums == null || nums.length == 0){
            return results;
        }
        Arrays.sort(nums);
        int start = 0;
        int end = nums.length -1;
        for(int i = 0;  i  <nums.length -3; i ++){
            if(i > 0 && nums[i] ==nums[i-1] ){ continue;} //去重
                for( int j = i+1; j  < nums.length-2; j ++ ){
                    if(  j > i+1 && nums[j] == nums[j-1] ){ continue;} //去重
                    int insum = target - nums[i] - nums[j];
                    int instart = j +1;
                    int inend = nums.length-1 ;
                    while(instart < inend){
                  
                    if(nums[instart] + nums[inend] == insum ){
                        List<Integer> result = new ArrayList<Integer>();
                        result.add(nums[i]);
                        result.add(nums[j]);
                        result.add(nums[instart]);
                        result.add(nums[inend]);
                        results.add(result); 
                        do{
                            instart ++;
                         }while(nums[instart] == nums[instart -1] && instart < inend);//去重
                        do{
                            inend --;
                        }while(nums[inend] == nums[inend+1] && instart < inend);//去重
                    }
                    else if(nums[instart] + nums[inend] < insum ){
                        instart ++;
                    }
                    else{
                        inend --;
                    }
                }
            }
        }
        return results; 
    }
}
