Given a sorted integer array arr, two integers k and x, return the k closest integers to x in the array. The result should also be sorted in ascending order.

An integer a is closer to x than an integer b if:

|a - x| < |b - x|, or
|a - x| == |b - x| and a < b
 

Example 1:

Input: arr = [1,2,3,4,5], k = 4, x = 3
Output: [1,2,3,4]
Example 2:

Input: arr = [1,2,3,4,5], k = 4, x = -1
Output: [1,2,3,4]
 

Constraints:

1 <= k <= arr.length
1 <= arr.length <= 104
arr is sorted in ascending order.
-104 <= arr[i], x <= 104
  
  -------- binary search + heap ------------------------
  /*
binary search o(lgn)
heap: 2klgk 

Space: The space complexity will be O(K)O(K), as we need to put a maximum of 2K2K numbers in the heap. 
*/
class Solution {
    public List<Integer> findClosestElements(int[] arr, int k, int x) {
        // 1. binary search to find the cloeset num 
       int closest = findCloesetToK(arr, x);
        
        // 2. 从 closest 向两边扫，分别放 k 个元素到 heap 里。
        Comparator<Integer> comp = new Comparator<Integer>() {
            @Override 
            public int compare(Integer n1, Integer n2) {
                int res = Math.abs(n2 - x) - Math.abs(n1 - x);
                if (res == 0) {
                    return n2 - n1; 
                }
                return res;
            }
        };
        PriorityQueue<Integer> q = new PriorityQueue<Integer>(comp); // max heap, compared by absolute value with the target AND number 大小
        
        // 3. 放closet 左右两遍
        int i = closest;
        int j = 0;
        for (; j < k && i < arr.length; i++, j++) { 
            q.offer(arr[i]);
        }
        // 放小的一边
        i = closest - 1;
        j = 0;
        for (; j < k && i >= 0; i--, j++) {
             q.offer(arr[i]);
            if (q.size() > k) {
                q.poll();
            }
        }
                
        // 4. print 结果 
        List<Integer> res = new ArrayList<Integer>();
        while (q.size() != 0) {
            res.add(q.poll());
        }
        
        Collections.sort(res);
        return res; 
    }
    
    
    private int findCloesetToK(int[] arr, int target) {
        int start = 0;
        int end = arr.length - 1;
        int closest = 0; 
        while (start <= end) {
            int mid = start + (end - start) / 2; 
            if (arr[mid] - target == 0) {
                closest = mid;
                return closest;
            }
            else if (arr[mid] - target > 0) {
                end = mid - 1;
            }
            else {
                start = mid + 1; 
            }
        }
        // 出来时如果用 start，那 start 可能超范围了，所以判断一下
        if (start >= arr.length) {
            start--;
        }
        // 这时候有可能 start 前一个或者 start 是最 close 的。所以判断一下. eg: [1,2,3,4,8] target=5
        if (start > 0) {
            closest = Math.abs(arr[start] - target) < Math.abs(arr[start-1] - target) ? start : start - 1; 
        }
        
        return closest;
    }
    
    
}
