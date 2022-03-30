You are given two integer arrays nums1 and nums2 sorted in ascending order and an integer k.

Define a pair (u, v) which consists of one element from the first array and one element from the second array.

Return the k pairs (u1, v1), (u2, v2), ..., (uk, vk) with the smallest sums.

 

Example 1:

Input: nums1 = [1,7,11], nums2 = [2,4,6], k = 3
Output: [[1,2],[1,4],[1,6]]
Explanation: The first 3 pairs are returned from the sequence: [1,2],[1,4],[1,6],[7,2],[7,4],[11,2],[7,6],[11,4],[11,6]
Example 2:

Input: nums1 = [1,1,2], nums2 = [1,2,3], k = 2
Output: [[1,1],[1,1]]
Explanation: The first 2 pairs are returned from the sequence: [1,1],[1,1],[1,2],[2,1],[1,2],[2,2],[1,3],[1,3],[2,3]
Example 3:

Input: nums1 = [1,2], nums2 = [3], k = 3
Output: [[1,3],[2,3]]
Explanation: All possible pairs are returned from the sequence: [1,3],[2,3]
 

Constraints:

1 <= nums1.length, nums2.length <= 105
-109 <= nums1[i], nums2[i] <= 109
nums1 and nums2 both are sorted in ascending order.
1 <= k <= 104 

------------------- heap top k ------------------------------------------------------------
// 用heap记录max。 每次和堆顶相比
// O（m*n*lgk)
// space o(k) 
class Solution {
    public List<List<Integer>> kSmallestPairs(int[] nums1, int[] nums2, int k) {
        PriorityQueue<List<Integer>> q = new PriorityQueue<List<Integer>>((a,b) -> (b.get(0) + b.get(1)) - (a.get(0) + a.get(1)));
        for (int i = 0; i < Math.min(k, nums1.length); i++) { // 优化1：永远只要考虑前k个
            for (int j = 0; j < Math.min(k, nums2.length); j++) {
                List<Integer> combo = new ArrayList<Integer>();
                combo.add(nums1[i]);
                combo.add(nums2[j]);
                if (q.size() < k) {
                    q.add(combo);
                    continue;
                }

                int curSum = nums1[i] + nums2[j]; 
                List<Integer> top = q.peek();
                int topSum = top.get(0) + top.get(1);
                if (curSum > topSum) { //优化2： 如果当前已经大于堆顶了，后面的只会更大（因为array是sort的），所以要及时止损
                    break;
                }
                if (curSum < topSum) {
                    q.poll();
                    q.add(combo);
                }
                
               
                // System.out.println(q.peek());
                // if (q.size() > k) {
                //     q.poll();
                // }
            }
        }
        
        List<List<Integer>> res = new ArrayList<List<Integer>>();
        res.addAll(q);
        return res; 
        
    }
}
