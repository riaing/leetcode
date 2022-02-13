https://www.educative.io/courses/grokking-the-coding-interview/JPGWDNRx3w2 
https://leetcode.com/problems/smallest-range-covering-elements-from-k-lists/

You have k lists of sorted integers in non-decreasing order. Find the smallest range that includes at least one number from each of the k lists.

We define the range [a, b] is smaller than range [c, d] if b - a < d - c or a < c if b - a == d - c.

 

Example 1:

Input: nums = [[4,10,15,24,26],[0,9,12,20],[5,18,22,30]]
Output: [20,24]
Explanation: 
List 1: [4, 10, 15, 24,26], 24 is in range [20,24].
List 2: [0, 9, 12, 20], 20 is in range [20,24].
List 3: [5, 18, 22, 30], 22 is in range [20,24].
Example 2:

Input: nums = [[1,2,3],[1,2,3],[1,2,3]]
Output: [1,1]
 

Constraints:

nums.length == k
1 <= k <= 3500
1 <= nums[i].length <= 50
-105 <= nums[i][j] <= 105
nums[i] is sorted in non-decreasing order.

----------------- heap --------------------------
/*
每个 array 中取一个，其中的 min 和 max 的差就是 smallest range。
再每次增大 min/减小max 来确定新 range。不能减小 max，因为它已经是一个 array 的 min 了，所以我们用heap 来找 array 中的 min，用一个 var 来代表 max

具体看 solution 4 - heap
Time complexity#
Since, at most, we’ll be going through all the elements of all the arrays and will remove/add one element in the heap in each step, the time complexity of the above algorithm will be O(N*logM)O(N∗logM) where ‘N’ is the total number of elements in all the ‘M’ input arrays.

Space complexity#
The space complexity will be O(M)O(M) because, at any time, our min-heap will be store one number from all the ‘M’ input arrays.


*/

class Node {
  int elementIndex;
  int arrayIndex;

  Node(int arrayIndex, int elementIndex) {
    this.elementIndex = elementIndex;
    this.arrayIndex = arrayIndex;
  }
}

class Solution {
    public int[] smallestRange(List<List<Integer>> nums) {
        PriorityQueue<Node> q = new PriorityQueue<Node>(
        (a,b) -> nums.get(a.arrayIndex).get(a.elementIndex) - nums.get(b.arrayIndex).get(b.elementIndex));
        int max = Integer.MIN_VALUE;
        for (int i = 0; i < nums.size(); i++) {
            if (nums.get(i) != null) {
                Node cur = new Node(i, 0);
                q.offer(cur);
                max = Math.max(max, nums.get(i).get(0));
            }
        }

        int[] res = new int[2];
        int range = Integer.MAX_VALUE;
        while (q.size() >= nums.size()) { //q 只存每个 list 中的 min，所以当 size < list 的和时，说明有个 list 走到终点了，exit
            Node minNode = q.poll();
            int min = nums.get(minNode.arrayIndex).get(minNode.elementIndex);

            if (max - min < range) {
                range = max - min; 
                res[0] = min;
                res[1] = max;
            }
            
            // 得把min 所在 array 中的下一个加进去并更新 max
            minNode.elementIndex++;
            if (nums.get(minNode.arrayIndex).size() > minNode.elementIndex) {
                max = Math.max(max, nums.get(minNode.arrayIndex).get(minNode.elementIndex));
                q.offer(minNode);
            }
        }
        return res;
    }
}
