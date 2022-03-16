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
----------------------------------------------------
Map的做法大概是： 
O(N^2)把所有pair存入hash表，并且每个hash值下面可以跟一个list做成map， map[hashvalue] = list，每个list中的元素就是一个pair, 这个pair的和就
是这个hash值，那么接下来求4sum就变成了在所有的pair value中求 2sum，这个就成了线性算法了，注意这里的线性又是针对pair数量(N^2)的线性，所以整体上这
个算法是O(N^2)，而且因为我们挂了list, 所以只要符合4sum的我们都可以找到对应的是哪四个数字。
但是这个其实要仔细想清楚，因为比如说[1,2,3,4]这四个值，我们会拿【1,2】到map中找3，4； 又会拿[3，4]去ma里找【1，2】。

https://blog.51cto.com/kisuntech/1343607 可以看看这个写的
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

------------------------------------ 3.16.2022--------------------------------------------------------------------------
  /*
3 SUM 外多加一层for loop。 因为要sort，所以2 sum采用两指针的形式。 
time: O(n^3) 
space: O(n) for sort
*/
class Solution {
    public List<List<Integer>> fourSum(int[] nums, int target) {
        List<List<Integer>> res = new ArrayList<List<Integer>>(); 
        Arrays.sort(nums);
        for (int i = 0; i < nums.length - 3; i++) {
            if (i > 0 && nums[i] == nums[i-1]) { // remove dup at 4 sum 
                continue;
            }
            for (int j = i+1; j < nums.length - 2; j++) {
                if (j > i+1 && nums[j] == nums[j-1]) { // remove dup at 3 sum 
                    continue;
                }
                int twoSumTarget = target - nums[i] - nums[j];
                List<List<Integer>> twoSumRes = twoSum(nums, j+1, twoSumTarget); 
                // add 1st and 2nd numbers 
                for (List<Integer> oneRes : twoSumRes) {
                    oneRes.add(nums[i]);
                    oneRes.add(nums[j]);
                    res.add(oneRes); 
                }
             }
        }
        return res; 
    }
    
    private List<List<Integer>> twoSum(int[] nums, int start, int target) {
        List<List<Integer>> res = new ArrayList<List<Integer>>();
        Arrays.sort(nums);
        int end = nums.length - 1; 
        while (end > start) {
            if (nums[start] + nums[end] == target) {
                List<Integer> cur = new ArrayList<Integer>();
                cur.add(nums[start]);
                cur.add(nums[end]);
                res.add(cur);
                // 更新指针 & remove dup at 2 sum 
                do {
                    start++; 
                } while (end > start && nums[start] == nums[start-1]);
                do {
                    end--;
                } while (end > start && nums[end] == nums[end+1]); 
            }
            else if (nums[start] + nums[end] < target) {
                start++;
            }
            else {
                end--;
            }
        }
        return res; 
    }
}
  
