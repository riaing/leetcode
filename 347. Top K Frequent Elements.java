Given an integer array nums and an integer k, return the k most frequent elements. You may return the answer in any order.

 

Example 1:

Input: nums = [1,1,1,2,2,3], k = 2
Output: [1,2]
Example 2:

Input: nums = [1], k = 1
Output: [1]
 

Constraints:

1 <= nums.length <= 105
k is in the range [1, the number of unique elements in the array].
It is guaranteed that the answer is unique.
 

Follow up: Your algorithm's time complexity must be better than O(n log n), where n is the array's size.
 
  
  ------------------- heap ----------------------------------------
  class Solution {
    public int[] topKFrequent(int[] nums, int k) {
        // num -> count
        Map<Integer, Integer> count = new HashMap<Integer, Integer>();
        for (int n : nums) {
            count.put(n, count.getOrDefault(n, 0) + 1); 
        }
        // heap of the number which sorted by frequency 
        // 注意这个 comparator 可以不用 queue 里的东西
        PriorityQueue<Integer> q = new PriorityQueue<Integer>((n1, n2) -> count.get(n1) - count.get(n2));
        
        // 维持 k size 的 min heap，当 cur num > heap 头时，拿出并放入 cur num
        for (int num : count.keySet()) { //O(nlgk)
            q.offer(num);
            if (q.size() > k) {
                q.poll(); //拿出来的肯定是最小的。
            }
        }
        
        int[] res = new int[k];
        int i = 0;
        while (q.size() > 0) {
            res[i] = (q.poll());
            i++;
        }
        return res;
    }
}
