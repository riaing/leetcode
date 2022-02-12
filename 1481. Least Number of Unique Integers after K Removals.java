姊妹题： https://github.com/riaing/leetcode/blob/master/Maximum%20Distinct%20Elements.java 

Given an array of integers arr and an integer k. Find the least number of unique integers after removing exactly k elements.

 

Example 1:

Input: arr = [5,5,4], k = 1
Output: 1
Explanation: Remove the single 4, only 5 is left.
Example 2:
Input: arr = [4,3,1,1,3,3,2], k = 3
Output: 2
Explanation: Remove 4, 2 and either one of the two 1s or three 3s. 1 and 3 will be left.
 

Constraints:

1 <= arr.length <= 10^5
1 <= arr[i] <= 10^9
0 <= k <= arr.length 

----------------------------
/*
min heap, 将 frequency 降到1，数 distinct num
nlgn n is the distinct numbers. this happens when all numbers are unique in the array 
one way optimization: don't push count=1's number in queue, when we know most number are unique 
*/
class Solution {
    public int findLeastNumOfUniqueInts(int[] nums, int k) {
        //1, map of num -> count 
        Map<Integer, Integer> map = new HashMap<Integer, Integer>();
        for (int n : nums) {
            map.put(n, map.getOrDefault(n, 0) + 1);
        }
        // 2. min heap sort by nums.count 
        PriorityQueue<Integer> q = new PriorityQueue<Integer>((a, b) -> map.get(a) - map.get(b));
        for (int n : map.keySet()) {
            q.offer(n);
        }
        
        // 3. 从最少的开始移
        int unique = 0;
        while (k > 0 && q.size() > 0) {
            int num = q.poll();
            k = k - map.get(num);
            if (k >= 0) {
                unique++;
            }
        }
        return map.size() - unique;
        
    }
} 
