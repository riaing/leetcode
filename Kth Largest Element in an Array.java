Find the kth largest element in an unsorted array. Note that it is the kth largest element in the sorted order, not the kth distinct element.

For example,
Given [3,2,1,5,6,4] and k = 2, return 5.

Note: 
You may assume k is always valid, 1 ≤ k ≤ array's length.

复杂度
时间 O(NlogK) 空间 O(K)

思路
遍历数组时将数字加入优先队列（堆），一旦堆的大小大于k就将堆顶元素去除，确保堆的大小为k。遍历完后堆顶就是返回值。



public class Solution {
    public int findKthLargest(int[] nums, int k) {
        PriorityQueue<Integer> q = new PriorityQueue<Integer>();
        int i = 0;
        while(i <nums.length){
            q.offer(nums[i]);
            if(q.size() >k){
                q.poll();
            }
            i++;
        }
        return q.poll();
    }
}
