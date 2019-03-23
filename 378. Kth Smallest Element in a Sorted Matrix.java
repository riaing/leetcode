Given a n x n matrix where each of the rows and columns are sorted in ascending order, find the kth smallest element in the matrix.

Note that it is the kth smallest element in the sorted order, not the kth distinct element.

Example:

matrix = [
   [ 1,  5,  9],
   [10, 11, 13],
   [12, 13, 15]
],
k = 8,

return 13.
Note: 
You may assume k is always valid, 1 ≤ k ≤ n2.

------------------------Priority Queue---------------------------------------
我们使用一个最大堆，然后遍历数组每一个元素，将其加入堆，根据最大堆的性质，大的元素会排到最前面，然后我们看当前堆中的元素个数是否大于k，
大于的话就将首元素去掉，循环结束后我们返回堆中的首元素即为所求:
Omn(logmn)

class Solution {
    public int kthSmallest(int[][] matrix, int k) {
        // create a comparator that will used to sort list in descending order 
        Comparator<Integer> comparator = new Comparator<Integer>() {
          @Override 
          public int compare(Integer a, Integer b) {
              return b.compareTo(a);
          }  
        };
    
    
        PriorityQueue<Integer> queue = new PriorityQueue<Integer>(comparator);

        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[0].length; j++) {
                queue.offer(matrix[i][j]);
                if (queue.size() > k) {
                    queue.poll();
                }
            }
        }
        return queue.poll();
    }
}
