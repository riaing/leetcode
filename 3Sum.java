用MAP：可以return 其中一个数组的index，但return数组本身会有duplicates。 
O(n^2) 
ex:
Your input
[-1,0,1,2,-1,-4]

if return index : 
[[0,2,1],[0,4,3],[1,4,2]]

if return 数组本身： 
[[-1,1,0],[-1,-1,2],[0,-1,1]]。 其中[-1，1，0]为duplicates

public class Solution {
    public List<List<Integer>> threeSum(int[] nums) {
        List<List<Integer>> res = new ArrayList<List<Integer>>();
 
        if ( nums == null || nums.length < 3 ){
            return res; 
        }
        
        for (int i = 0; i < nums.length-1 ; i ++ ){
            Map< Integer, Integer> table = new HashMap<Integer, Integer>(); //每次循环都要renew map，key是数字，value是index
            int sum = 0 - nums[i]; 
            for ( int j = i +1; j < nums.length ; j ++ ){
            
                if (table.containsKey(sum - nums[j])){
                    List<Integer> cur = new ArrayList<Integer>(Arrays.asList(i, j, table.get(sum-nums[j])));
                  
                    res.add(cur);
                }
                else { 
                    table.put(nums[j], j ); 
                } 
            }
            
        }
       return res[0]; //只 return 其中一个的index。  
    }

}
用 2 pointer： 可以return无dup的数组，但不能return index， 因为sort。 
O(n^2) 
public class Solution {
    public List<List<Integer>> threeSum(int[] nums) {
         List<List<Integer>> triples = new ArrayList<List<Integer>>(); 
        if(nums == null || nums.length < 3){
            return triples;
        }
        Arrays.sort(nums);
        for(int i =0; i< nums.length-2; i++){
            if (i > 0 && nums[i] == nums[i -1]){ // to skip duplicate numbers; e.g [0,0,0,0]
                    continue; 
            }
            int sum = 0 - nums[i];
            int start = i+1; 
            int end = nums.length -1; 
            while(start < end ){
                if(nums[start]+nums[end] == sum){
                    List<Integer> triple = new ArrayList<Integer>(); 
                    triple.add(nums[i]);
                    triple.add(nums[start]);
                    triple.add(nums[end]);
                    triples.add(triple); 
                    start ++;
                    end --;
                    // to skip duplicates
                    while( start < end && nums[start] == nums[start-1]  ){start ++;}
                    while(end > start && nums[end] == nums[end+1] ){end --;}
                }
                else if (nums[start]+nums[end] < sum ){
                    start ++;
                }
                else{
                    end --;
                }
            }
        }
        return triples; 
    }
}
