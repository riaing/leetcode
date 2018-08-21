Given a sorted integer array without duplicates, return the summary of its ranges.

Example 1:

Input:  [0,1,2,4,5,7]
Output: ["0->2","4->5","7"]
Explanation: 0,1,2 form a continuous range; 4,5 form a continuous range.
Example 2:

Input:  [0,2,3,4,6,8,9]
Output: ["0","2->4","6","8->9"]
Explanation: 2,3,4 form a continuous range; 8,9 form a continuous range. 

--------------solution 1, while loop with several pointer  -------------
class Solution {
    public List<String> summaryRanges(int[] nums) {
        List<String> results = new ArrayList<String>();
        if (nums == null || nums.length == 0) {
            return results; 
        }

        int start = 0;
        int end = start + 1;
        int temStart = start; 
        while(temStart < nums.length && end < nums.length) {
            long endNum = nums[end];
            long startNum = nums[temStart];
            if (endNum - startNum == 1) {
                System.out.println("here");
                end ++;
                temStart++;
                //break;
            }
            else if(endNum - startNum > 1) {
                System.out.println("here2" );
                if (start == temStart) {
                    results.add(Integer.toString(nums[start]));
                }
                else{
                    String range = nums[start] + "->" + nums[temStart];
                    results.add(range);
                }
                temStart = end;
                
                start = temStart;
              
                end ++;
            }
        }
        if (start == nums.length -1) {
            results.add(Integer.toString(nums[start]));
        }
        else{
            String range = nums[start] + "->" + nums[temStart];
                    results.add(range);
        }
        return results;
    }
}

-------------solustion 2: use for loop, 思路与上一样，都要在最后check最后的情况，但简单明了了多
class Solution {
    public List<String> summaryRanges(int[] nums) {
        List<String> results = new ArrayList<String>();
        if (nums == null || nums.length == 0) {
            return results; 
        }

        int start = 0;
        int i = 1; 
        for (; i < nums.length; i++) {
            if (nums[i] - nums[i-1] == 1) {
                continue;
            }
            if (start == i-1) {
                results.add(Integer.toString(nums[start]));
                start ++; 
            }
            else {
                String tmp = nums[start] + "->" + nums[i-1];
                results.add(tmp);
                start = i;
            }
        }
        if (start == i -1) {
            results.add(Integer.toString(nums[start]));
        }
        else {
             String tmp = nums[start] + "->" + nums[i-1];
                results.add(tmp);
        }
        return results;   
    }
}
