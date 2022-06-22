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

------------------- heap top k 有点brute force + pruning。 不算本题考察点 ------------------------------------------------------------
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

------------------------- 运用two sorted array 性质 ----------------------------------------------------
 /*  min heap存 pair index. 
扫k次两个array。规则：
- 每次栈顶为当前最小的sum，加入result中
- 如果栈顶是(i,j)， 那么下一个比他小的只可能是 (i+1, j) or (i, j+1)，把这两个入栈. 
- corner case: 如果 i,j 超边界了，则把另一个pair 入栈
- 注意排重：用visited array

Time O（k*lgk) 

*/


// space o(k) 
class Solution {
    public List<List<Integer>> kSmallestPairs(int[] nums1, int[] nums2, int k) {
        List<List<Integer>> res = new ArrayList<List<Integer>>();
        // heap 存index pair，sorted by sum's ascending order
        PriorityQueue<List<Integer>> q = new PriorityQueue<List<Integer>>((a,b) -> (nums1[a.get(0)] + nums2[a.get(1)]) - (nums1[b.get(0)] + nums2[b.get(1)])); // 维持个min heap
        
        int i = 0; 
        int j = 0; 
        q.offer(Arrays.asList(i, j)); // 这就是global最小sum
        boolean[][] visited = new boolean[nums1.length][nums2.length]; //注意这里会memory out of limit. 因为array可能会很大。可以换成set存string"i+j" 
        visited[i][j] = true;
        
        for (int n = 0; n < k; n++) { // 最多走k次
            if (q.size() == 0) {
                break; 
            }
            
            List<Integer> top = q.poll();
            i = top.get(0);
            j = top.get(1);
            res.add(Arrays.asList(nums1[i], nums2[j])); // 栈顶（当前最小）加入结果
            int topSum = top.get(0) + top.get(1);
  
            // 如果index都valid
            if (i+1 < nums1.length && !visited[i+1][j]) {
                q.offer(Arrays.asList(i+1, j));
                visited[i+1][j] = true; 
            }
            if (j+1 < nums2.length && !visited[i][j+1]) {
                q.offer(Arrays.asList(i, j+1));
                visited[i][j+1] = true; 
            }
        }
        return res; 
        
    }
}

----------------------比以上更清晰 ----------------------
 /*  min heap存 pair index. 
规则：如果栈顶是(i,j)， 那么下一个比他小的只可能是 (i+1, j) or (i, j+1)，把这两个入栈
注意去重：用visitedIndex 数组

Time O（k*lgk) 
space: o(k)

*/


class Solution {
    public List<List<Integer>> kSmallestPairs(int[] nums1, int[] nums2, int k) {
        PriorityQueue<int[]> pq = new PriorityQueue<int[]>((a, b) -> (nums1[a[0]] + nums2[a[1]]) - (nums1[b[0]] + nums2[b[1]]));
        Set<String> visitedIndex = new HashSet<>();
        pq.offer(new int[]{0,0});
        visitedIndex.add("0+0");
        List<List<Integer>> res = new ArrayList<>();
        
        while(res.size() < k && !pq.isEmpty()) { // 可能不够k个
            int[] pre = pq.poll();
            res.add(Arrays.asList(nums1[pre[0]], nums2[pre[1]]));
            // add the next 2 
            int next2 = pre[1] + 1;
            int next1 = pre[0] + 1; 
            String nums2move = pre[0] +"+" + next2;
            String nums1move = next1 + "+" + pre[1];
            
            if (pre[1] + 1 < nums2.length && !visitedIndex.contains(nums2move)) {
                visitedIndex.add(nums2move);
                pq.offer(new int[]{pre[0], pre[1] + 1});
            }
            if (pre[0] + 1 < nums1.length && !visitedIndex.contains(nums1move)) {
                visitedIndex.add(nums1move);
                pq.offer(new int[]{pre[0] + 1, pre[1]});
            }
        }
        return res; 
    }
}
