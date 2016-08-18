Find the kth largest element in an unsorted array. Note that it is the kth largest element in the sorted order, not the kth distinct element.

For example,
Given [3,2,1,5,6,4] and k = 2, return 5.

Note: 
You may assume k is always valid, 1 ≤ k ≤ array's length.

--------------排序法
复杂度
时间 O(NlogN) 空间 O(1)

思路
将数组排序后，返回第k个元素。

代码
public class Solution {
    public int findKthLargest(int[] nums, int k) {
        Arrays.sort(nums);
        return nums[nums.length - k];
    }
}

---------------------Using Heap
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


---------------SelectSort
思路
跟快速排序一个思路。先取一个枢纽值，将数组中小于枢纽值的放在左边，大于枢纽值的放在右边，具体方法是用左右两个指针，如果他们小于枢纽值则将他们换到对面，一轮过后记得将枢纽值赋回分界点。如果这个分界点是k，说明分界点的数就是第k个数。否则，如果分界点大于k，则在左半边做同样的搜索。如果分界点小于k，则在右半边做同样的搜索。

注意
helper函数的k是k-1，因为我们下标从0开始的，我们要比较k和下标，来确定是否左半部分有k个数字。
互换左右时，也要先判断left <= right
