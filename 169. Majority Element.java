Given an array of size n, find the majority element. The majority element is the element that appears more than ⌊ n/2 ⌋ times.

You may assume that the array is non-empty and the majority element always exist in the array.

Example 1:

Input: [3,2,3]
Output: 3
Example 2:

Input: [2,2,1,1,1,2,2]
Output: 2
--------------------------------------衍生的kk抵消--------------------------------------------------------------
/**
KK抵消
Java 中删除哈希表时需要考虑线程安全
Time： 每个元素进表出表O（1）， 所以总共O（n）。空间O（k）
*/
class Solution {
    public int majorityElement(int[] nums) {
        // key = number, value = number的个数, 维护一个size等于k的map
        Map<Integer, Integer> map = new HashMap<Integer, Integer>();
        
        for(int i = 0; i < nums.length; i++) {
            map.put(nums[i], map.getOrDefault(nums[i], 0) + 1);
            if (map.size() == 2) {
                // solution 1: 
                for (Integer key : map.keySet()) {
                    map.put(key, map.get(key)-1);
                }
                // 移除count为零的entry 
                Iterator<Map.Entry<Integer, Integer>> it = map.entrySet().iterator();
                while (it.hasNext()) {
                    Map.Entry<Integer, Integer> entry = it.next();
                    if(entry.getValue() == 0) {
                        it.remove();
                    }
                }
                /* solution 2 */
                // List<Integer> removeList = new ArrayList<>();
                // for (int key : keySet) {
                //     hash.put(key, hash.get(key) - 1);
                //     if (hash.get(key) == 0) {
                //         removeList.add(key);
                //     }
                // }
                // for (Integer key : removeList) {
                //     hash.remove(key);
                // }
            }
            
        }
        
        int res = 0;
        for (Integer key : map.keySet()) {
           
               res = key;
             
        }
        return res; 
    }
}
